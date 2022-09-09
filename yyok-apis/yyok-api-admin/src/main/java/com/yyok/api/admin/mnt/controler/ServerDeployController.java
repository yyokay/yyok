package com.yyok.api.admin.mnt.controler;

import com.yyok.share.admin.logging.annotation.Log;
import com.yyok.share.admin.mnt.domain.ServerDeploy;
import com.yyok.share.admin.mnt.service.IServerDeployService;
import com.yyok.share.admin.mnt.service.dto.DeployQueryCriteria;
import com.yyok.share.admin.mnt.service.dto.ServerDeployQueryCriteria;
import com.yyok.share.framework.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@RestController
@Api(tags = "运维：服务器管理")
@RequiredArgsConstructor
@RequestMapping("/api/server/deploy")
public class ServerDeployController {

    private final IServerDeployService serverDeployService;

    @ApiOperation("导出服务器数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('serverDeploy:list')")
    public void exportServerDeploy(HttpServletResponse response, ServerDeployQueryCriteria criteria) throws IOException {
        serverDeployService.download(serverDeployService.queryAll(criteria), response);
    }

    @ApiOperation(value = "查询服务器")
    @GetMapping
	@PreAuthorize("@el.check('serverDeploy:list')")
    public ResponseEntity<Object> queryServerDeploy(ServerDeployQueryCriteria criteria, Pageable pageable){
    	return new ResponseEntity<>(serverDeployService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增服务器")
    @ApiOperation(value = "新增服务器")
    @PostMapping
	@PreAuthorize("@el.check('serverDeploy:add')")
    public ResponseEntity<Object> createServerDeploy(@Validated @RequestBody ServerDeploy resources){
        serverDeployService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改服务器")
    @ApiOperation(value = "修改服务器")
    @PutMapping
	@PreAuthorize("@el.check('serverDeploy:edit')")
    public ResponseEntity<Object> updateServerDeploy(@Validated @RequestBody ServerDeploy resources){
        serverDeployService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除服务器")
    @ApiOperation(value = "删除Server")
	@DeleteMapping
	@PreAuthorize("@el.check('serverDeploy:del')")
    public ResponseEntity<Object> deleteServerDeploy(@RequestBody Set<Long> ids){
        serverDeployService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

	@Log("测试连接服务器")
	@ApiOperation(value = "测试连接服务器")
	@PostMapping("/testConnect")
	@PreAuthorize("@el.check('serverDeploy:add')")
	public ResponseEntity<Object> testConnectServerDeploy(@Validated @RequestBody ServerDeploy resources){
		return new ResponseEntity<>(serverDeployService.testConnect(resources),HttpStatus.CREATED);
	}

    @ApiOperation("导出部署数据")
    @GetMapping(value = "/downloads")
    @PreAuthorize("@el.check('database:list')")
    public void exportDeployData(HttpServletResponse response, DeployQueryCriteria criteria) throws IOException {
        //serverDeployService.download(serverDeployService.queryAll(criteria), response);
    }

    @ApiOperation(value = "查询部署")
    @GetMapping("queryDeployData")
    @PreAuthorize("@el.check('deploy:list')")
    public ResponseEntity<Object> queryDeployData(DeployQueryCriteria criteria, Pageable pageable){
        return null;//new ResponseEntity<>(serverDeployService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增部署")
    @ApiOperation(value = "新增部署")
    @PostMapping("createDeploy")
    @PreAuthorize("@el.check('deploy:add')")
    public ResponseEntity<Object> createDeploy(@Validated @RequestBody ServerDeploy resources){
        serverDeployService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改部署")
    @ApiOperation(value = "修改部署")
    @PutMapping("updateDeploy")
    @PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> updateDeploy(@Validated @RequestBody ServerDeploy resources){
        serverDeployService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除部署")
    @ApiOperation(value = "删除部署")
    @DeleteMapping("deleteDeploy")
    @PreAuthorize("@el.check('deploy:del')")
    public ResponseEntity<Object> deleteDeploy(@RequestBody Set<Long> ids){
        serverDeployService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("上传文件部署")
    @ApiOperation(value = "上传文件部署")
    @PostMapping(value = "/upload")
    @PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> uploadDeploy(@RequestBody MultipartFile file, HttpServletRequest request)throws Exception{
        Long id = Long.valueOf(request.getParameter("id"));
        String fileName = "";
        if(file != null){
            fileName = file.getOriginalFilename();
            File deployFile = new File(fileName);//fileSavePath+
            FileUtil.del(deployFile);
            file.transferTo(deployFile);
            //文件下一步要根据文件名字来
            //serverDeployService.deploy(fileName ,id);//fileSavePath+
        }else{
            System.out.println("没有找到相对应的文件");
        }
        System.out.println("文件上传的原名称为:"+ Objects.requireNonNull(file).getOriginalFilename());
        Map<String,Object> map = new HashMap<>(2);
        map.put("errno",0);
        map.put("id",fileName);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
    @Log("系统还原")
    @ApiOperation(value = "系统还原")
    @PostMapping(value = "/serverReduction")
    @PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> serverReduction(@Validated @RequestBody ServerDeploy resources){
        String result = "";//serverDeployService.serverReduction(resources);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @Log("服务运行状态")
    @ApiOperation(value = "服务运行状态")
    @PostMapping(value = "/serverStatus")
    @PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> serverStatus(@Validated @RequestBody ServerDeploy resources){
        String result = "";//serverDeployService.serverStatus(resources);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @Log("启动服务")
    @ApiOperation(value = "启动服务")
    @PostMapping(value = "/startServer")
    @PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> startServer(@Validated @RequestBody ServerDeploy resources){
        String result = "";//serverDeployService.startServer(resources);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @Log("停止服务")
    @ApiOperation(value = "停止服务")
    @PostMapping(value = "/stopServer")
    @PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> stopServer(@Validated @RequestBody ServerDeploy resources){
        String result = "";//serverDeployService.stopServer(resources);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
