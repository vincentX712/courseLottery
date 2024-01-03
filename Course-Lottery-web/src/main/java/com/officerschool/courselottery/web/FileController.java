package com.officerschool.courselottery.web;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.officerschool.courselottery.common.enums.ErrorCodeEnum;
import com.officerschool.courselottery.common.models.CommonResult;
import com.officerschool.courselottery.dao.dataobject.ExpertExcelDO;
import com.officerschool.courselottery.service.ExpertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author : create by anyuxin
 * @version : v1.0
 * @date : 2024/1/2
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Resource
    private ExpertService expertService;

    @RequestMapping(value = "/excelImport/experts", method = RequestMethod.POST)
    public CommonResult excelImportExperts(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null)
                return CommonResult.fail(ErrorCodeEnum.REQUEST_PARAM_NULL);
            // 验证文件格式
            String fileName = file.getOriginalFilename();
            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx"))
                return CommonResult.fail(ErrorCodeEnum.FILE_FORMAT_ERROR);

            return CommonResult.createOK(expertService.importExcel(file));
        } catch (Exception e) {
            logger.error("excelImport error", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    private static boolean verifyExpertExcelHeadLine() {

        return true;
    }

    @RequestMapping(value = "/excelExport", method = RequestMethod.POST)
    public CommonResult excelExport() {
        try {
            return CommonResult.createOK();
        } catch (Exception e) {
            logger.error("excelExport error", e);
            return CommonResult.fail(ErrorCodeEnum.SERVER_ERROR);
        }
    }
}
