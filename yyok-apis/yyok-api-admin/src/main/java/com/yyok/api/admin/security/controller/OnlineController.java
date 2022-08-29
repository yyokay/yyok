package com.yyok.api.admin.security.controller;

import com.yyok.share.admin.security.service.OnlineAccountService;
import com.yyok.share.framework.annotation.ForbidSubmit;
import com.yyok.share.admin.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author yyok
 */
@RestController
@RequestMapping("/sys/auth/online")
@Api(tags = "系统：在线用户管理")
public class OnlineController {

    private final OnlineAccountService onlineAccountService;

    public OnlineController(OnlineAccountService onlineAccountService) {
        this.onlineAccountService = onlineAccountService;
    }

    @ApiOperation("查询在线用户")
    @GetMapping
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> getAll(@RequestParam(value = "filter", defaultValue = "") String filter,
                                         @RequestParam(value = "type", defaultValue = "0") int type,
                                         Pageable pageable) {
        return new ResponseEntity<>(onlineAccountService.getAll(filter, type, pageable), HttpStatus.OK);
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check()")
    public void download(HttpServletResponse response,
                         @RequestParam(value = "filter", defaultValue = "") String filter,
                         @RequestParam(value = "type", defaultValue = "0") int type) throws IOException {
        onlineAccountService.download(onlineAccountService.getAll(filter, type), response);
    }

    @ForbidSubmit
    @ApiOperation("踢出用户")
    @DeleteMapping
    @PreAuthorize("@el.check()")
    public ResponseEntity<Object> delete(@RequestBody Set<String> keys) throws Exception {

        for (String key : keys) {
            onlineAccountService.kickOut(key,"");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
