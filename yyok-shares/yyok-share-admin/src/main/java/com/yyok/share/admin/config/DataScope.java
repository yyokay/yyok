package com.yyok.share.admin.config;

import com.yyok.share.admin.info.domain.Dept;
import com.yyok.share.admin.info.service.IDeptService;
import com.yyok.share.admin.system.domain.Account;
import com.yyok.share.admin.system.service.IAccountService;
import com.yyok.share.admin.system.service.IRoleService;
import com.yyok.share.admin.system.service.dto.RoleSmallDto;
import com.yyok.share.framework.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据权限配置
 *
 * @author yyok
 * @date 2022-05-1
 */
@Component
@AllArgsConstructor
public class DataScope {

    private final String[] scopeType = {"全部", "本级", "自定义"};
    private final IAccountService accountService;

    private final IRoleService roleService;

    private final IDeptService deptService;

    public Set<String> getDeptCodes() {

        Account account = accountService.findByCode(SecurityUtils.getAccountCode());

        // 用于存储部门id
        Set<String> deptCodes = new HashSet<>();

        // 查询用户角色
        List<RoleSmallDto> roleSet = roleService.findByAccountsCode(SecurityUtils.getAccountCode());

        for (RoleSmallDto role : roleSet) {

            if (scopeType[0].equals(role.getDataScope())) {
                return new HashSet<>();
            }

            // 存储本级的数据权限
            if (scopeType[1].equals(role.getDataScope())) {
                deptCodes.add(account.getUserCode());
            }

            // 存储自定义的数据权限
            if (scopeType[2].equals(role.getDataScope())) {
                Set<Dept> depts = deptService.findByRoleCodes(role.getCoder());
                for (Dept dept : depts) {
                    deptCodes.add(dept.getCoder());
                    List<Dept> deptChildren = deptService.findByPcode(dept.getCoder());
                    if (deptChildren != null && deptChildren.size() != 0) {
                        deptCodes.addAll(getDeptChildren(deptChildren));
                    }
                }
            }
        }
        return deptCodes;
    }


    public List<String> getDeptChildren(List<Dept> deptList) {
        List<String> list = new ArrayList<>();
        deptList.forEach(dept -> {
                    if (dept != null && dept.getEnabled() == 1) {
                        List<Dept> depts = deptService.findByPcode(dept.getCoder());
                        if (deptList.size() != 0) {
                            list.addAll(getDeptChildren(depts));
                        }
                        list.add(dept.getCoder());
                    }
                }
        );
        return list;
    }


}
