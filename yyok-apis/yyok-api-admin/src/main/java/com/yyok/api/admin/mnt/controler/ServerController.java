package com.yyok.api.admin.mnt.controler;

import com.yyok.share.admin.logging.annotation.Log;
import com.yyok.share.admin.mnt.domain.Server;
import com.yyok.share.admin.mnt.service.IServerService;
import com.yyok.share.admin.mnt.service.dto.ServerQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Api(tags = "运维：应用管理")
@RequestMapping("/api/sys/server")
public class ServerController {

    private final IServerService appService;

    @ApiOperation("导出应用数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('app:list')")
    public void exportApp(HttpServletResponse response, ServerQueryCriteria criteria) throws IOException {
        //appService.download(appService.queryAll(criteria), response);
    }

    @ApiOperation(value = "查询应用")
    @GetMapping
	@PreAuthorize("@el.check('app:list')")
    public ResponseEntity<Object> queryApp(ServerQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(appService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增应用")
    @ApiOperation(value = "新增应用")
    @PostMapping
	@PreAuthorize("@el.check('app:add')")
    public ResponseEntity<Object> createApp(@Validated @RequestBody Server resources){
        appService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改应用")
    @ApiOperation(value = "修改应用")
    @PutMapping
	@PreAuthorize("@el.check('app:edit')")
    public ResponseEntity<Object> updateApp(@Validated @RequestBody Server resources){
        appService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除应用")
    @ApiOperation(value = "删除应用")
	@DeleteMapping
	@PreAuthorize("@el.check('app:del')")
    public ResponseEntity<Object> deleteApp(@RequestBody Set<Long> ids){
        appService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
