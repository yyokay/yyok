package com.yyok.share.admin.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yyok.share.admin.monitor.domain.Visits;
import com.yyok.share.admin.monitor.service.IVisitsService;
import com.yyok.share.admin.monitor.service.mapper.IVisitsMapper;
import com.yyok.share.framework.mapper.common.service.impl.BaseServiceImpl;
import com.yyok.share.framework.utils.StringUtils;
import com.yyok.share.admin.logging.service.mapper.ILogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yyok
 * @date 2022-12-13
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VisitsServiceImpl extends BaseServiceImpl<IVisitsMapper, Visits> implements IVisitsService {


    private final ILogMapper logMapper;

    private final IVisitsMapper visitsMapper;

    public VisitsServiceImpl(ILogMapper logMapper, IVisitsMapper visitsMapper) {
        this.logMapper = logMapper;
        this.visitsMapper = visitsMapper;
    }

    @Override
    public void save() {
        LocalDate localDate = LocalDate.now();
        List<Visits> visitList = visitsMapper.selectList(new LambdaQueryWrapper<Visits>()
                .eq(Visits::getDate, localDate.toString()));
        Visits visits = null;
        if (visitList.size() >= 1)
            visits = visitList.get(0);
        if (visits == null) {
            visits = new Visits();
            visits.setWeekDay(StringUtils.getWeekDay());
            visits.setPvCounts(1L);
            visits.setIpCounts(1L);
            visits.setDate(localDate.toString());
            this.save(visits);
        }
    }

    @Override
    public void count(HttpServletRequest request) {
        LocalDate localDate = LocalDate.now();
        List<Visits> aa = visitsMapper.selectList(new LambdaQueryWrapper<Visits>()
                .eq(Visits::getDate, localDate.toString()));
        Visits visits = null;
        if (aa.size() >= 1)
            visits = aa.get(0);
        if (visits == null) {
            visits = new Visits();
            visits.setPvCounts(1L);
        } else {
            visits.setPvCounts(visits.getPvCounts() + 1);
        }
        long ipCounts = logMapper.findIp(localDate.toString(), localDate.plusDays(1).toString());
        visits.setIpCounts(ipCounts);
        this.saveOrUpdate(visits);
    }

    @Override
    public Object get() {
        Map<String, Object> map = new HashMap<>(4);
        LocalDate localDate = LocalDate.now();
        Visits visits = this.getOne(new LambdaQueryWrapper<Visits>()
                .eq(Visits::getDate, localDate.toString()));
        List<Visits> list = visitsMapper.findAllVisits(localDate.minusDays(6).toString(), localDate.plusDays(1).toString());

        long recentVisits = 0, recentIp = 0;
        for (Visits data : list) {
            recentVisits += data.getPvCounts();
            recentIp += data.getIpCounts();
        }
        map.put("newVisits", visits.getPvCounts());
        map.put("newIp", visits.getIpCounts());
        map.put("recentVisits", recentVisits);
        map.put("recentIp", recentIp);
        return map;
    }

    @Override
    public Object getChartData() {
        Map<String, Object> map = new HashMap<>(3);
//        LocalDate localDate = LocalDate.now();
//        List<Visits> list = visitsRepository.findAllVisits(localDate.minusDays(6).toString(),localDate.plusDays(1).toString());
//        map.put("weekDays",list.stream().map(Visits::getWeekDay).collect(Collectors.toList()));
//        map.put("visitsData",list.stream().map(Visits::getPvCounts).collect(Collectors.toList()));
//        map.put("ipData",list.stream().map(Visits::getIpCounts).collect(Collectors.toList()));
        return map;
    }

    @Override
    public Visits findByName(String name) {
        return null;
    }

    @Override
    public boolean updateByCode(Visits coder) {
        return false;
    }
}
