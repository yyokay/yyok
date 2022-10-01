package com.yyok.api.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yyok.acquisite.datax.client.entity.JobProject;
import com.yyok.acquisite.datax.client.service.IJobProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobProject")
@Api(tags = "项目管理模块")
public class JobProjectController extends BaseController {

    @Autowired
    private IJobProjectService IJobProjectService;


    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation("分页查询所有数据")
    public IPage<JobProject> selectAll(@RequestParam(value = "searchVal", required = false) String searchVal,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("pageNo") Integer pageNo) {

        return IJobProjectService.getProjectListPaging(pageSize, pageNo, searchVal);
    }

    /**
     * Get all project
     *
     * @return
     */
    @ApiOperation("获取所有数据")
    @GetMapping("/list")
    public List<JobProject> selectList() {
        QueryWrapper<JobProject> query = new QueryWrapper();
        query.eq("flag", true);
        return IJobProjectService.list(query);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public JobProject selectOne(@PathVariable Serializable id) {
        return this.IJobProjectService.getById(id);
    }

    /**
     * 新增数据
     *
     * @param entity 实体对象
     * @return 新增结果
     */
    @ApiOperation("新增数据")
    @PostMapping
    public boolean insert(HttpServletRequest request, @RequestBody JobProject entity) {
        entity.setUserId(getCurrentUserId(request));
        return this.IJobProjectService.save(entity);
    }


    /**
     * 修改数据
     *
     * @param entity 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation("修改数据")
    public boolean update(@RequestBody JobProject entity) {
        JobProject project = IJobProjectService.getById(entity.getId());
        project.setName(entity.getName());
        project.setDescription(entity.getDescription());
        return this.IJobProjectService.updateById(entity);
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiOperation("删除数据")
    public boolean delete(@RequestParam("idList") List<Long> idList) {
        return this.IJobProjectService.removeByIds(idList);
    }
}