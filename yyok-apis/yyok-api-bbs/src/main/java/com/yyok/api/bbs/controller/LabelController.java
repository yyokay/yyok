package com.yyok.api.bbs.controller;

import com.github.pagehelper.PageInfo;
import com.yyok.api.bbs.article.facade.dto.LabelDTO;
import com.yyok.api.bbs.article.facade.dto.LabelSearchDTO;
import com.yyok.api.bbs.article.facade.server.LabelService;
import com.yyok.api.bbs.rest.config.login.NoNeedLogin;
import com.yyok.api.bbs.rest.config.swagger.ApiVersion;
import com.yyok.api.bbs.rest.config.swagger.ApiVersionConstant;
import com.yyok.api.bbs.rest.utils.FileLengthUtils;
import com.liang.nansheng.common.auth.UserContextUtils;
import com.liang.nansheng.common.auth.UserSsoDTO;
import com.liang.nansheng.common.enums.ResponseCode;
import com.liang.nansheng.common.utils.CommonUtils;
import com.liang.nansheng.common.web.basic.ResponseResult;
import com.liang.nansheng.common.web.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/bbs/label/")
@Api(tags = "标签接口")
public class LabelController {
    @Reference
    private LabelService labelService;

    @Autowired
    private FileLengthUtils fileLengthUtils;

    @PostMapping("create")
    @ApiOperation(value = "新增标签")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> create(@RequestBody LabelDTO labelDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(labelService.create(labelDTO, currentUser));
    }

    @PostMapping("/uploadLabelLogo")
    @ApiOperation(value = "上传标签logo")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<String> uploadLabelLogo(@RequestParam(value = "logo", required = false) MultipartFile logo) throws IOException {
        if (fileLengthUtils.isFileNotTooBig(logo.getBytes())) {
            return ResponseResult.success(labelService.uploadLabelLogo(logo.getBytes(), logo.getOriginalFilename()));
        } else {
            throw BusinessException.build(ResponseCode.EXCEED_THE_MAX, "请上传不超过 " +
                    CommonUtils.byteConversion(fileLengthUtils.getFileMaxLength()) + " 的图片!");
        }
    }

    @PostMapping("update")
    @ApiOperation(value = "更新标签")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> update(@RequestBody LabelDTO labelDTO) {
        UserSsoDTO currentUser = UserContextUtils.currentUser();
        return ResponseResult.success(labelService.update(labelDTO, currentUser));
    }

    @NoNeedLogin
    @GetMapping("getList")
    @ApiOperation(value = "获取标签")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<PageInfo<LabelDTO>> getList(LabelSearchDTO labelSearchDTO) {
        return ResponseResult.success(labelService.getList(labelSearchDTO));
    }

    @PostMapping("delete/{id}")
    @ApiOperation(value = "删除标签")
    @ApiVersion(group = ApiVersionConstant.V_300)
    public ResponseResult<Boolean> delete(@PathVariable Integer id) {
        return ResponseResult.success(labelService.delete(id));
    }

}
