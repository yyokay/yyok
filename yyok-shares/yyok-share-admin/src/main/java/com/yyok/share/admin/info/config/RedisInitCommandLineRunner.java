package com.yyok.share.admin.info.config;

import com.yyok.share.admin.info.service.IDeptService;
import com.yyok.share.admin.system.service.IAccountService;
import com.yyok.share.admin.system.service.IResourceService;
import com.yyok.share.admin.system.service.IRoleService;
import com.yyok.share.framework.utils.RedisStaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RedisInitCommandLineRunner implements CommandLineRunner {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IDeptService deptService;

    //@Autowired
    //private IGroupService groupService;



    public void redisInitInfoCommandLineRunner() {
        List<Map<String, Object>> accountList = accountService.listMaps();
        for (Map<String, Object> m : accountList) {
            RedisStaticUtil.set("sys_account_" + m.get("coder"), m);
        }

        List<Map<String, Object>> resourceList = resourceService.listMaps();
        for (Map<String, Object> m : resourceList) {
            RedisStaticUtil.set("sys_resource_" + m.get("coder"), m);
        }
        List<Map<String, Object>> roleList = roleService.listMaps();
        for (Map<String, Object> m : roleList) {
            RedisStaticUtil.set("sys_role_" + m.get("coder"), m);
        }

        List<Map<String, Object>> deptList = deptService.listMaps();
        for (Map<String, Object> m : deptList) {
            RedisStaticUtil.set("info_dept_" + m.get("coder"), m);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        redisInitInfoCommandLineRunner();
    }
}