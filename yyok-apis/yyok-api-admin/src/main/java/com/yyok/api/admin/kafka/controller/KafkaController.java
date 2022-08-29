package com.yyok.api.admin.kafka.controller;

import com.yyok.share.admin.kafka.service.dto.KafkaQueryCriteria;
import com.yyok.share.admin.logging.annotation.Log;
import com.yyok.share.admin.logging.service.ILogService;
import com.yyok.share.admin.logging.service.dto.LogQueryCriteria;
import com.yyok.share.framework.enums.LogTypeEnum;
import com.yyok.share.framework.perproty.KafkaPerproty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yyok
 * @date 2022-05-24
 */
@RestController
@RequestMapping("/api/sys/kafka")
@Api(tags = "监控： Kafka管理")
@SuppressWarnings("unchecked")
public class KafkaController {

    private final ILogService logService;
    private final KafkaTemplate kafkaTemplate;

    public KafkaController(ILogService logService, KafkaTemplate kafkaTemplate) {
        this.logService = logService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Log("Kafka Producer")
    @ApiOperation("Kafka Producer")
    @GetMapping(value = "/send")
    public void send(HttpServletResponse response, KafkaQueryCriteria criteria) throws IOException {
        kafkaTemplate.send(criteria.getTopic(),criteria.getMsg());

    }

    @Log("Kafka Recieve")
    @ApiOperation("Kafka Recieve")
    @GetMapping(value = "/recieve")
    public void recieve(HttpServletResponse response, KafkaQueryCriteria criteria) throws IOException {
        kafkaTemplate.receive(criteria.getTopic(),1,1);

    }

    @GetMapping
    @ApiOperation("日志查询")
    @PreAuthorize("@el.check('admin','log:list')")
    public ResponseEntity<Object> getLogs(LogQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(logService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/mlogs")
    @PreAuthorize("@el.check('admin','log:list')")
    public ResponseEntity getApiLogs(LogQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(logService.findAllByPageable(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    @ApiOperation("用户日志查询")
    public ResponseEntity<Object> getUserLogs(LogQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(logService.queryAllByUser(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error")
    @ApiOperation("错误日志查询")
    @PreAuthorize("@el.check('admin','logError:list')")
    public ResponseEntity<Object> getErrorLogs(LogQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(logService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/error/{id}")
    @ApiOperation("日志异常详情查询")
    @PreAuthorize("@el.check('admin','logError:detail')")
    public ResponseEntity<Object> getErrorLogs(@PathVariable String coder) {
        return new ResponseEntity<>(logService.findByErrDetail(coder), HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/error")
    @Log("删除所有ERROR日志")
    @ApiOperation("删除所有ERROR日志")
    @PreAuthorize("@el.check('admin','logError:remove')")
    public ResponseEntity<Object> delAllByError() {
        logService.delAllByError();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/del/info")
    @Log("删除所有INFO日志")
    @ApiOperation("删除所有INFO日志")
    @PreAuthorize("@el.check('admin','logInfo:remove')")
    public ResponseEntity<Object> delAllByInfo() {
        logService.delAllByInfo();
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
