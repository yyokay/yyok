package com.yyok.api.admin.linux.controller;

import com.yyok.share.framework.annotation.ForbidSubmit;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.linux.ShellExecutorUtil;
import com.yyok.share.admin.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author yyok
 * @date 2022-05-23
 */
@Api(tags = "系统：Centos7管理")
@RestController
@RequestMapping("/api/sys/centos")
public class CentosController {

    private final IGenerator generator;

    public CentosController(IGenerator generator) {
        this.generator = generator;
    }

    @ForbidSubmit
    @Log("执行命令")
    @ApiOperation("执行命令")
    @PostMapping
    public ResponseEntity<Object> exec(@Validated @RequestBody HashMap hm) throws Exception {
        String ip=String.valueOf(hm.get("ip"));
        int port=Integer.valueOf(String.valueOf(hm.get("port")));
        String usr=String.valueOf(hm.get("usr"));
        String pasword=String.valueOf(hm.get("pasword"));
        String commond=String.valueOf(hm.get("commond"));
        ShellExecutorUtil shellExecutorUtil = new ShellExecutorUtil(ip,port,usr,pasword);
        return new ResponseEntity<>(shellExecutorUtil.exec(commond),HttpStatus.OK);
    }




}
