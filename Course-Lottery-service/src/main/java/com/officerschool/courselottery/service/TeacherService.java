package com.officerschool.courselottery.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.officerschool.courselottery.dao.dataobject.TeacherDO;
import com.officerschool.courselottery.dao.mapper.TeacherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2024/1/3
 */
@Service
public class TeacherService extends ServiceImpl<TeacherMapper, TeacherDO> {

    private final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    public boolean importExcel(MultipartFile file) {
        try {
            List<TeacherDO> result = ExcelImportUtil.importExcel(file.getInputStream(), TeacherDO.class, new ImportParams());
            return saveBatch(result);
        } catch (Exception e) {
            logger.error("TeacherService#importExcel error: ", e);
            return false;
        }
    }
}
