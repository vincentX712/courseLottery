package com.officerschool.courselottery.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.officerschool.courselottery.common.models.req.ExpertsPageReq;
import com.officerschool.courselottery.common.models.res.ExpertRes;
import com.officerschool.courselottery.dao.dataobject.ExpertDO;
import com.officerschool.courselottery.dao.mapper.ExpertMapper;
import com.officerschool.courselottery.service.utils.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2023/12/20
 */
@Service
public class ExpertService {

    private final Logger logger = LoggerFactory.getLogger(ExpertService.class);

    @Resource
    private ExpertMapper expertMapper;

    public PageInfo<ExpertRes> getExpertList(ExpertsPageReq req) {
        int pageNum = req.getPageNum() == null ? 1 : req.getPageNum();
        int pageSize = req.getPageSize() == null ? 10 : req.getPageSize();

        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<ExpertDO> queryWrapper = new QueryWrapper<>();

        List<ExpertDO> expertList = expertMapper.selectList(queryWrapper);
        PageInfo<ExpertDO> pageInfo = new PageInfo<>(expertList);
        PageInfo<ExpertRes> resPage = PageUtil.convertPageInfo2PageInfoVo(pageInfo);

        List<ExpertRes> resList = new ArrayList<>();
        for (ExpertDO expertDO : expertList) {
            ExpertRes res = new ExpertRes();
            res.setId(expertDO.getId());
            res.setName(expertDO.getName());
            resList.add(res);
        }
        resPage.setList(resList);
        return resPage;
    }
}