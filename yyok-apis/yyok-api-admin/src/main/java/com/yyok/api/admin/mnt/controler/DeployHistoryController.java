package com.yyok.api.admin.mnt.controler;

import com.yyok.share.admin.logging.annotation.Log;
import com.yyok.share.admin.mnt.service.dto.DeployHistoryQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@RestController
@RequiredArgsConstructor
@Api(tags = "运维：部署历史管理")
@RequestMapping("/api/deployHistory")
public class DeployHistoryController {

   // private final IDeployHistoryService deployhistoryService;

    @ApiOperation("导出部署历史数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('deployHistory:list')")
    public void exportDeployHistory(HttpServletResponse response, DeployHistoryQueryCriteria criteria) throws IOException {
       // deployhistoryService.download(deployhistoryService.queryAll(criteria), response);
    }

    @ApiOperation(value = "查询部署历史")
    @GetMapping
	@PreAuthorize("@el.check('deployHistory:list')")
    public ResponseEntity<Object> queryDeployHistory(DeployHistoryQueryCriteria criteria, Pageable pageable){
        return null;//new ResponseEntity<>(deployhistoryService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("删除DeployHistory")
    @ApiOperation(value = "删除部署历史")
	@DeleteMapping
    @PreAuthorize("@el.check('deployHistory:del')")
    public ResponseEntity<Object> deleteDeployHistory(@RequestBody Set<String> ids){
        //deployhistoryService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
