package com.officerschool.courselottery.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.officerschool.courselottery.common.models.req.DeleteReq;
import com.officerschool.courselottery.common.models.req.ExpertReq;
import com.officerschool.courselottery.common.models.req.ExpertsPageReq;
import com.officerschool.courselottery.common.models.res.DeleteRes;
import com.officerschool.courselottery.common.models.res.ExpertRes;
import com.officerschool.courselottery.common.models.res.ModifyExpertRes;
import com.officerschool.courselottery.dao.dataobject.ExpertDO;
import com.officerschool.courselottery.dao.dataobject.ScheduleDO;
import com.officerschool.courselottery.dao.mapper.ExpertMapper;
import com.officerschool.courselottery.dao.mapper.ScheduleMapper;
import com.officerschool.courselottery.service.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
@Service
public class ExpertService extends ServiceImpl<ExpertMapper, ExpertDO> {

    private final Logger logger = LoggerFactory.getLogger(ExpertService.class);

    @Resource
    private ExpertMapper expertMapper;

    @Resource
    private ScheduleMapper scheduleMapper;
    public PageInfo<ExpertRes> getExpertList(ExpertsPageReq req) {
        int pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
        int pageSize = req.getPageSize() == null ? 10 : req.getPageSize();

        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<ExpertDO> queryWrapper = new QueryWrapper<>();
        if(req.getStatus()!=null){
            queryWrapper.eq("status",req.getStatus());
        }
        queryWrapper.orderByDesc("status");
        queryWrapper.orderByAsc("priority");
        List<ExpertDO> expertList = expertMapper.selectList(queryWrapper);
        PageInfo<ExpertDO> pageInfo = new PageInfo<>(expertList);
        PageInfo<ExpertRes> resPage = PageUtil.convertPageInfo2PageInfoVo(pageInfo);

        List<ExpertRes> resList = new ArrayList<>();
        for (ExpertDO expertDO : expertList) {
            ExpertRes res = new ExpertRes();
            res.setId(expertDO.getId());
            res.setName(expertDO.getName());
            res.setPriority(expertDO.getPriority());
            res.setStatus(expertDO.getStatus());
            res.setRelateMajor(expertDO.getRelateMajor());
            resList.add(res);
        }
        resPage.setList(resList);
        return resPage;
    }
    public ModifyExpertRes expertInsert(ExpertReq req){
        ExpertDO expert = new ExpertDO();
        if(req.getName()!=null){
            expert.setName(req.getName());
        }
        if(req.getStatus()!=null){
            expert.setStatus(req.getStatus());
        }
        if(req.getPriority()!=null){
            expert.setPriority(req.getPriority());
        }
        if(req.getRelateMajor()!=null){
            expert.setRelateMajor(req.getRelateMajor());
        }
        ModifyExpertRes res = new ModifyExpertRes();
        res.setRes(expertMapper.insert(expert));
        res.setMsg("成功");
        return res;
    }
    public ModifyExpertRes expertModify(ExpertReq req){
        QueryWrapper<ExpertDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", req.getId());
        ExpertDO expert = expertMapper.selectOne(queryWrapper);
        if(req.getName()!=null){
            expert.setName(req.getName());
        }
        if(req.getStatus()!=null){
            expert.setStatus(req.getStatus());
        }
        if(req.getPriority()!=null){
            expert.setPriority(req.getPriority());
        }
        if(req.getRelateMajor()!=null){
            expert.setRelateMajor(req.getRelateMajor());
        }
        ModifyExpertRes res = new ModifyExpertRes();
        res.setRes(expertMapper.updateById(expert));
        res.setMsg("成功");
        return res;
    }
    public boolean importExcel(MultipartFile file) {
        try {
            List<ExpertDO> result = ExcelImportUtil.importExcel(file.getInputStream(), ExpertDO.class, new ImportParams());
            return saveBatch(result);
        } catch (Exception e) {
            logger.error("ExpertService#importExcel error: ", e);
            return false;
        }
    }
    public DeleteRes deleteExpert(DeleteReq req){
        QueryWrapper<ExpertDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", req.getId());
        DeleteRes res = new DeleteRes();
        QueryWrapper<ScheduleDO> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("expert_id", req.getId());
        if(expertMapper.selectCount(queryWrapper)==0){
            res.setRes(false);
            res.setMsg("专家不存在，请重试！");
        }else if (scheduleMapper.selectCount(queryWrapper1)>0){
            res.setRes(false);
            res.setMsg("该专家已抽取，不可删除！");
        }else{
            res.setRes(expertMapper.delete(queryWrapper) > 0);
            res.setMsg("成功");
        }
        return res;
    }
}
