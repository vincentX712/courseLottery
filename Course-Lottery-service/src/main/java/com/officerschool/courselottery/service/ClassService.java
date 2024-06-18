package com.officerschool.courselottery.service;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.officerschool.courselottery.common.models.req.*;
import com.officerschool.courselottery.common.models.res.*;
import com.officerschool.courselottery.dao.dataobject.ClassDO;
import com.officerschool.courselottery.dao.dataobject.ScoreDO;
import com.officerschool.courselottery.dao.dataobject.TeacherDO;
import com.officerschool.courselottery.dao.mapper.ClassMapper;
import com.officerschool.courselottery.dao.mapper.ScoreMapper;
import com.officerschool.courselottery.dao.mapper.TeacherMapper;
import com.officerschool.courselottery.service.utils.PageUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClassService extends ServiceImpl<ScoreMapper, ScoreDO> {
    private final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    @Resource
    ClassMapper classMapper;
    @Resource
    TeacherMapper teacherMapper;
    @Resource
    ScoreMapper scoreMapper;

    public PageInfo<ClassRes> getClasses(PageReq req){
        int pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
        int pageSize = req.getPageSize() == null ? 10 : req.getPageSize();

        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<ClassDO> queryWrapper = new QueryWrapper<>();
        if(req.getStatus()!=null){
            queryWrapper.eq("status",req.getStatus());
        }
        List<ClassDO> classDOList = classMapper.selectList(queryWrapper);
        PageInfo<ClassDO> pageInfo = new PageInfo<>(classDOList);
        PageInfo<ClassRes> resPage = PageUtil.convertPageInfo2PageInfoVo(pageInfo);
        List<ClassRes> resList = new ArrayList<>();
        for (ClassDO classDO : classDOList) {
            ClassRes res = new ClassRes();
            res.setId(classDO.getId());
            res.setName(classDO.getName());
            res.setStatus(classDO.getStatus());
            res.setSchoolTerm(classDO.getSchoolTerm());
            res.setSchoolYear(classDO.getSchoolYear());
            resList.add(res);
        }
        resPage.setList(resList);
        return resPage;
    }

    public ClassRes getOneClass(Integer classId){
        QueryWrapper<ClassDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",classId);
        QueryWrapper<TeacherDO> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("class_id",classId);
        int teacherNum = teacherMapper.selectCount(queryWrapper1).intValue();
        int standardExcellent = computeHalfUp(String.valueOf(teacherNum*0.3));
        int standardPass = computeHalfUp(String.valueOf(teacherNum*0.1));
        int standardGood = getGoodNum(teacherNum);
        ClassDO classDO = classMapper.selectOne(queryWrapper);
        ClassRes res = new ClassRes();
        res.setId(classDO.getId());
        res.setName(classDO.getName());
        res.setStatus(classDO.getStatus());
        res.setExcellentNum(standardExcellent);
        res.setGoodNum(standardGood);
        res.setPassNum(standardPass);
        res.setTeacherNum(teacherNum);
        res.setSchoolTerm(classDO.getSchoolTerm());
        res.setSchoolYear(classDO.getSchoolYear());
        return res;
    }
    public ModifyRes classInsert(ClassReq req){
        ModifyRes res = new ModifyRes();
        if(req.getName()==null || req.getSchoolYear()==null || req.getSchoolTerm()==null){
            res.setRes(0);
            res.setMsg("缺少参数，请重新确认");
            return res;
        }
        ClassDO classDO = new ClassDO();
        classDO.setName(req.getName());
        classDO.setSchoolTerm(req.getSchoolTerm());
        classDO.setSchoolYear(req.getSchoolYear());
        if(req.getStatus()!=null){
            classDO.setStatus(req.getStatus());
        }
        res.setRes(classMapper.insert(classDO));
        res.setMsg("成功");
        return res;
    }

    public List<TeacherRes> getClassTeachersScore(Integer classId){
        QueryWrapper<TeacherDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id",classId);
        List<TeacherDO> teacherDOList = teacherMapper.selectList(queryWrapper);
        List<TeacherRes> resList = new ArrayList<>();
        for (TeacherDO teacherDO : teacherDOList) {
            TeacherRes res = new TeacherRes();
            QueryWrapper<ScoreDO> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("teacher_id",teacherDO.getId());
            List<ScoreDO> scoreDOList = scoreMapper.selectList(queryWrapper1);
            int sum = scoreDOList.stream().mapToInt(ScoreDO::getOverallScore).sum();
            res.setId(teacherDO.getId());
            res.setName(teacherDO.getName());
            res.setClassId(teacherDO.getClassId());
            res.setTotalScore(sum);
            res.setTotalNum(scoreDOList.size());
            resList.add(res);
        }
        return resList;
    }
    public List<TeacherRes> getClassTeachers(Integer classId){
        QueryWrapper<TeacherDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id",classId);
        List<TeacherDO> teacherDOList = teacherMapper.selectList(queryWrapper);
        List<TeacherRes> resList = new ArrayList<>();
        for (TeacherDO teacherDO : teacherDOList) {
            TeacherRes res = new TeacherRes();
            res.setId(teacherDO.getId());
            res.setName(teacherDO.getName());
            res.setClassId(teacherDO.getClassId());
            resList.add(res);
        }
        return resList;
    }

    public ModifyRes teacherInsert(TeacherReq req){
        QueryWrapper<ClassDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",req.getClassId());
        ModifyRes res = new ModifyRes();
        if(classMapper.selectOne(queryWrapper)==null){
            res.setMsg("班次不存在，请检查");
            res.setRes(0);
            return res;
        }
        TeacherDO teacherDO = new TeacherDO();
        teacherDO.setName(req.getName());
        teacherDO.setClassId(req.getClassId());

        res.setRes(teacherMapper.insert(teacherDO));
        res.setMsg("成功");
        return res;
    }

    public ModifyRes scoreCommit(ScoreCommitReq req){
        JSONArray studentScoreList = JSON.parseArray(req.getScore());
        List<ScoreDO> scoreDOList = new ArrayList<>();
        ModifyRes res = new ModifyRes();
        QueryWrapper<TeacherDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id",req.getClassId());
        int teacherNum = teacherMapper.selectCount(queryWrapper).intValue();
        int standardExcellent = computeHalfUp(String.valueOf(teacherNum*0.3));
        int standardPass = computeHalfUp(String.valueOf(teacherNum*0.1));
        int standardGood = getGoodNum(teacherNum);
        String commitId = UUID.randomUUID().toString().replace("-", "");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        boolean numCheck = true;
        boolean paramsCheck=true;
        int excellentNum=0;
        int goodNum=0;
        int passNum=0;
        for (int i = 0; i < studentScoreList.size(); i++) {
            JSONObject object = studentScoreList.getJSONObject(i);
            Integer teacherId = object.getInteger("id");
            Integer teachingAttitude = object.getInteger("teachingAttitude");
            Integer teachingContent = object.getInteger("teachingContent");
            Integer teachingDesign = object.getInteger("teachingDesign");
            Integer teachingMethod = object.getInteger("teachingMethod");
            Integer learningEffect = object.getInteger("learningEffect");
            Integer overallScore = object.getInteger("totalScore");
            if(teacherId==null || teachingAttitude==null || teachingDesign==null || teachingMethod==null || teachingContent==null || learningEffect==null || overallScore==null
                    || teachingAttitude==0 || teachingDesign==0 || teachingMethod==0 || teachingContent==0
                    || learningEffect==0 || overallScore==0){
                paramsCheck=false;
                break;
            }
            if (overallScore>=90){
                excellentNum++;
            }else if(overallScore>=80){
                goodNum++;
            }else if (overallScore>=60){
                passNum++;
            }
            if (excellentNum<=standardExcellent && goodNum<=standardGood && passNum<=standardPass){
                ScoreDO scoreDO = new ScoreDO();
                scoreDO.setCommitId(commitId);
                scoreDO.setOverallScore(overallScore);
                scoreDO.setCommitTime(now);
                scoreDO.setTeacher_id(teacherId);
                scoreDO.setTeachingContent(teachingContent);
                scoreDO.setTeachingAttitude(teachingAttitude);
                scoreDO.setTeachingMethod(teachingMethod);
                scoreDO.setTeachingDesign(teachingDesign);
                scoreDO.setLearningEffect(learningEffect);
                scoreDOList.add(scoreDO);
            }else {
                numCheck=false;
                break;
            }
        }
        if (numCheck&&paramsCheck){
            if(saveBatch(scoreDOList)){
                res.setRes(1);
                res.setMsg("成功");
            }else {
                res.setRes(0);
                res.setMsg("写入失败，请稍候");
            }
        } else if(!numCheck) {
            res.setRes(0);
            res.setMsg("综合评分不符合比例要求，请重新打分");
        }else if(!paramsCheck) {
            res.setRes(0);
            res.setMsg("所有教员的每个指标均需打分，请重新打分");
        }

        return res;
    }
    public void scoreExport(HttpServletResponse response, Integer classId){
        try {
            List<TeacherRes> teacherResList = getClassTeachersScore(classId);
            ClassRes classRes = getOneClass(classId);
            //设置信息头，告诉浏览器内容为excel类型
            response.setHeader("content-Type", "application/vnd.ms-excel");
            //文件名称
            String fileName = classRes.getName()+"_"+classRes.getSchoolYear()+classRes.getSchoolTerm()+"_"+"_评教评分表.xlsx";
            //sheet名称
            String sheetName = "打分表";
            fileName = new String(fileName.getBytes(), "ISO-8859-1");

            //设置下载名称
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            //字节流输出
            ServletOutputStream out = response.getOutputStream();
            //设置excel参数
            ExportParams params = new ExportParams();
            //设置sheet名
            params.setSheetName(sheetName);
            //设置标题
//        params.setTitle("听课表");

            //转成对应的类型
//        List<ScheduleDO> exportUsers = changeType(users);
            //导入excel
            Workbook workbook = ExcelExportUtil.exportExcel(params, TeacherRes.class, teacherResList);
            //写入
            workbook.write(out);
        } catch (Exception e) {
            logger.error("ClassService#exportToExcel error: ", e);
        }
    }
    private Integer getGoodNum(Integer totalNum){
        Integer standardGood = computeHalfUp(String.valueOf(totalNum*0.6));
        switch (totalNum){
            case 4:
            case 6: {
                standardGood=3;
                break;
            }
            case 5:{
                standardGood=2;
                break;
            }
            case 14:
            case 16:{
                standardGood=9;
                break;
            }
            case 15:{
                standardGood=8;
                break;
            }
            case 24:
            case 26:{
                standardGood=15;
                break;
            }
            case 25:{
                standardGood=14;
                break;
            }
            case 34:
                standardGood=21;
        }
        return standardGood;
    }
    private Integer computeHalfUp(String num){
        BigDecimal product = new BigDecimal(num);
        // 四舍五入
        BigDecimal roundedProduct = product.setScale(0, RoundingMode.HALF_UP);

        return roundedProduct.intValue();
    }
}
