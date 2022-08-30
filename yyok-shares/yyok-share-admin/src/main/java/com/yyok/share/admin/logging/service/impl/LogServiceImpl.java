package com.yyok.share.admin.logging.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yyok.share.admin.logging.domain.Log;
import com.yyok.share.admin.logging.service.ILogService;
import com.yyok.share.admin.logging.service.mapper.ILogMapper;
import com.yyok.share.framework.dozer.service.IGenerator;
import com.yyok.share.framework.enums.LogTypeEnum;
import com.yyok.share.framework.enums.YesNoEnum;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import com.yyok.share.framework.mapper.common.utils.QueryHelpPlus;
import com.yyok.share.framework.utils.FileUtil;
import com.yyok.share.framework.utils.SecurityUtils;
import com.yyok.share.framework.utils.StringUtils;
import com.yyok.share.framework.utils.ValidationUtil;
import com.yyok.share.admin.logging.service.dto.LogErrorDTO;
import com.yyok.share.admin.logging.service.dto.LogQueryCriteria;
import com.yyok.share.admin.logging.service.dto.LogSmallDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yyok
 * @date 2022-05-24
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl extends BaseServiceImpl<ILogMapper, Log> implements ILogService {

    private final ILogMapper logMapper;

    private final IGenerator generator;

    public LogServiceImpl(ILogMapper logMapper, IGenerator generator) {
        this.logMapper = logMapper;
        this.generator = generator;
    }

    @Override
    public Object findAllByPageable(LogQueryCriteria criteria, Pageable pageable) {
        criteria.setLogType(LogTypeEnum.INFO.getDesc());
        criteria.setType(YesNoEnum.YES_NO_1.getValue());
        getPage(pageable);
        PageInfo<Log> page = new PageInfo(logMapper.findAllByPageable(criteria.getBlurry()));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", page.getList());
        map.put("totalElements", page.getTotal());
        return map;
    }

    @Override
    public Object queryAll(LogQueryCriteria criteria, Pageable pageable) {
        if (Objects.equals(criteria.getType(), YesNoEnum.YES_NO_0.getValue())) {
            criteria.setLogType(LogTypeEnum.INFO.getDesc());
            criteria.setType(YesNoEnum.YES_NO_0.getValue());
        } else {
            criteria.setLogType(LogTypeEnum.ERROR.getDesc());
        }
        getPage(pageable);
        PageInfo<Log> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        String status = LogTypeEnum.ERROR.getDesc();
        if (status.equals(criteria.getLogType())) {
            map.put("content", generator.convert(page.getList(), LogErrorDTO.class));
            map.put("totalElements", page.getTotal());
        }
        map.put("content", page.getList());
        map.put("totalElements", page.getTotal());
        return map;
    }

    @Override
    public List<Log> queryAll(LogQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(Log.class, criteria));
    }

    @Override
    public Object queryAllByUser(LogQueryCriteria criteria, Pageable pageable) {
        criteria.setLogType(LogTypeEnum.INFO.getDesc());
        criteria.setBlurry(SecurityUtils.getAccountName());
        getPage(pageable);
        PageInfo<Log> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), LogSmallDTO.class));
        map.put("totalElements", page.getTotal());
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String ip, ProceedingJoinPoint joinPoint,
                     Log log, String accountCode) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.yyok.share.admin.logging.annotation.Log aopLog = method.getAnnotation(com.yyok.share.admin.logging.annotation.Log.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        if (argValues != null) {
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        // 描述
        if (log != null) {
            log.setRemark(aopLog.value());
        }
        //类型 0-后台 1-前台
        log.setTyper(aopLog.type());
        if (accountCode != null) {
            log.setAccountCode(accountCode);
            log.setCreateBy(accountCode);
        }
        assert log != null;
        log.setRequestIp(ip);

        String loginPath = "login";
        if (loginPath.equals(signature.getName())) {
            try {
                assert argValues != null;
                username = new JSONObject(argValues[0]).get("username").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setAccountCode(accountCode);
        log.setParams(params + " }");
        this.save(log);
    }

    @Override
    public Object findByErrDetail(String coder) {
        Log log = this.findByCode(coder);
        ValidationUtil.isNull(log.getCoder(), "Log", "coder", coder);
        byte[] details = log.getExceptionDetail();
        return Dict.create().set("exception", new String(ObjectUtil.isNotNull(details) ? details : "".getBytes()));
    }

    @Override
    public void download(List<Log> logs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Log log : logs) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", log.getUsername());
            map.put("IP", log.getRequestIp());
            map.put("IP来源", log.getAddress());
            map.put("描述", log.getRemark());
            map.put("浏览器", log.getBrowser());
            map.put("请求耗时/毫秒", log.getTime());
            map.put("异常详情", new String(ObjectUtil.isNotNull(log.getExceptionDetail()) ? log.getExceptionDetail() : "".getBytes()));
            map.put("创建日期", log.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByError() {
        logMapper.deleteByLogType(LogTypeEnum.ERROR.getDesc());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByInfo() {
        logMapper.deleteByLogType(LogTypeEnum.INFO.getDesc());
    }

    @Override
    public Log findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(Log coder) {
        return false;
    }
}
