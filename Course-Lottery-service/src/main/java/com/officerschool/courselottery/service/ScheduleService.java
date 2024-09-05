package com.officerschool.courselottery.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.officerschool.courselottery.common.models.req.DeleteReq;
import com.officerschool.courselottery.common.models.req.SchedulesPageReq;
import com.officerschool.courselottery.common.models.res.DeleteRes;
import com.officerschool.courselottery.common.models.res.SchedulesRes;
import com.officerschool.courselottery.common.models.res.TeacherLotteriedRes;
import com.officerschool.courselottery.dao.dataobject.CourseDO;
import com.officerschool.courselottery.dao.dataobject.ScheduleDO;
import com.officerschool.courselottery.dao.mapper.CourseMapper;
import com.officerschool.courselottery.dao.mapper.ScheduleMapper;
import com.officerschool.courselottery.service.utils.PageUtil;
import com.officerschool.courselottery.service.utils.ScheduleUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/25
 */

@Service
public class ScheduleService extends ServiceImpl<ScheduleMapper, ScheduleDO> {

    private final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private CourseMapper courseMapper;

    public PageInfo<SchedulesRes> getSchedules(SchedulesPageReq req) {
        int pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
        int pageSize = req.getPageSize() == null ? 20 : req.getPageSize();

        PageHelper.startPage(pageNum, pageSize);

        String sql = new ScheduleUtil().getSchedulesSql(req);
        List<Map<String, Object>> list = scheduleMapper.getScheduleList(sql);

        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        PageInfo<SchedulesRes> resPage = PageUtil.convertPageInfo2PageInfoVo(pageInfo);

        List<SchedulesRes> resList = new ArrayList<>();
        for (Map<String, Object> mapItem : list) {
            SchedulesRes res = setSchedule(mapItem);
            resList.add(res);
        }
        resPage.setList(resList);

        return resPage;
    }

    public void exportToExcel(HttpServletResponse response,SchedulesPageReq req){
        try {
            List<SchedulesRes> scheduleResList = getSchedulesExecel(req);
            //设置信息头，告诉浏览器内容为excel类型
            response.setHeader("content-Type", "application/vnd.ms-excel");
            //文件名称
            String fileName = "听课表.xlsx";
            //sheet名称
            String sheetName = "听课表";
            if(req.getDate()!=null){
                fileName="听课表"+req.getDate()+".xlsx";
                sheetName=req.getDate();
            }
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
            Workbook workbook = ExcelExportUtil.exportExcel(params, SchedulesRes.class, scheduleResList);
            //写入
            workbook.write(out);
        } catch (Exception e) {
            logger.error("ScheduleService#exportToExcel error: ", e);
        }
    }

    private List<SchedulesRes> getSchedulesExecel(SchedulesPageReq req) {
        String sql = new ScheduleUtil().getSchedulesSql(req);
        List<Map<String, Object>> list = scheduleMapper.getScheduleList(sql);

        List<SchedulesRes> resList = new ArrayList<>();
        for (Map<String, Object> mapItem : list) {
            SchedulesRes res = setSchedule(mapItem);
            resList.add(res);
        }
        return resList;
    }

    public DeleteRes deleteSchedule(DeleteReq req){
        QueryWrapper<ScheduleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", req.getId());
        DeleteRes res = new DeleteRes();
        if(scheduleMapper.selectCount(queryWrapper)>0){
            res.setRes(scheduleMapper.delete(queryWrapper) > 0);
            res.setMsg("成功");
        }else{
            res.setRes(false);
            res.setMsg("计划不存在，请重试！");
        }
        return res;
    }
    public boolean importExcel(MultipartFile file) {
        try {
            List<ScheduleDO> result = ExcelImportUtil.importExcel(file.getInputStream(), ScheduleDO.class, new ImportParams());
            return saveBatch(result);
        } catch (Exception e) {
            logger.error("CourseService#importExcel error: ", e);
            return false;
        }
    }

    public TeacherLotteriedRes isCourseTeacherHasLotteried(Integer courseId) {
        TeacherLotteriedRes res = new TeacherLotteriedRes();
        SchedulesPageReq req = new SchedulesPageReq();
        QueryWrapper<CourseDO> courseDOQueryWrapper = new QueryWrapper<>();
        courseDOQueryWrapper.eq("id", courseId);
        CourseDO courseDO=courseMapper.selectOne(courseDOQueryWrapper);
        req.setTeacherName(courseDO.getTeacherName());
        String sql = new ScheduleUtil().getSchedulesSql(req);
        List<Map<String, Object>> list = scheduleMapper.getScheduleList(sql);
        if(list.isEmpty()){
            res.setIsLotteried(false);
            res.setLotteriedCount(0);
            return res;
        }
        res.setIsLotteried(true);
        List<SchedulesRes> resList = new ArrayList<>();
        for (Map<String, Object> mapItem : list) {
            SchedulesRes schedulesRes = setSchedule(mapItem);
            resList.add(schedulesRes);
        }
        res.setLotteriedCount(resList.size());
        res.setSchedulesRes(resList);
        return res;
    }
    private SchedulesRes setSchedule(Map<String, Object> mapItem){
        SchedulesRes res = new SchedulesRes();
        res.setId(Integer.valueOf(mapItem.get("id").toString()));
        res.setCourseId(Integer.valueOf(mapItem.get("course_id").toString()));
        res.setExpertId(Integer.valueOf(mapItem.get("expert_id").toString()));
//            res.setTeacherId(Integer.valueOf(mapItem.get("teacher_id").toString()));
        res.setEvaluation(mapItem.get("evaluation") == null ? "" : mapItem.get("evaluation").toString());
        res.setDate(mapItem.get("date") == null ? "" : mapItem.get("date").toString());
        res.setWeek(mapItem.get("week") == null ? "" : mapItem.get("week").toString());
        res.setLesson(mapItem.get("lesson").toString());
        res.setMajor(mapItem.get("major").toString());
        res.setCampusName(mapItem.get("campus_name").toString());
        res.setClassroom(mapItem.get("classroom").toString());
        res.setTeacherName(mapItem.get("teacher_name").toString());
        res.setTitle(mapItem.get("teacher_title").toString());
        res.setEducation(mapItem.get("teacher_education").toString());
        res.setAge(mapItem.get("teacher_age").toString());
        res.setExpertName(mapItem.get("e_name").toString());
        res.setNodeId(Integer.valueOf(mapItem.get("node_id").toString()));
        if(mapItem.get("notes")!=null){
            res.setNotes(mapItem.get("notes").toString());
        }
        if(mapItem.get("lesson_attribute")!=null){
            res.setLessonAttribute(mapItem.get("lesson_attribute").toString());
        }
        if(mapItem.get("ctime")!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            // 使用SimpleDateFormat的format方法将Timestamp格式化为字符串
            String formattedDate = dateFormat.format(mapItem.get("ctime"));
            res.setCtime(formattedDate);
        }
        res.setIsPolitics(ScheduleUtil.politicsLesson.contains(mapItem.get("lesson").toString()));
        return res;
    }
}
