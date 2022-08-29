package com.yyok.api.admin.monitor.controller;

import com.yyok.share.admin.monitor.domain.vo.RedisVo;
import com.yyok.share.admin.monitor.service.IRedisService;
import com.yyok.share.framework.annotation.ForbidSubmit;
import com.yyok.share.admin.logging.annotation.Log;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author yyok
 * @date 2022-12-10
 */
@Api(tags = "系统：redis缓存管理")
@RestController
@RequestMapping("api/sys/redis")
public class RedisController {

    private final IRedisService redisService;

    public RedisController(IRedisService redisService) {
        this.redisService = redisService;
    }

    @Log("查询Redis缓存")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_SELECT')")
    public ResponseEntity getRedis(String key, Pageable pageable) {
        return new ResponseEntity(redisService.findByKey(key, pageable), HttpStatus.OK);
    }

    @ForbidSubmit
    @Log("删除Redis缓存")
    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_DELETE')")
    public ResponseEntity delete(@RequestBody RedisVo resources) {

        redisService.delete(resources.getKey());
        return new ResponseEntity(HttpStatus.OK);
    }

    @ForbidSubmit
    @Log("清空Redis缓存")
    @DeleteMapping(value = "/all")
    @PreAuthorize("hasAnyRole('ADMIN','REDIS_ALL','REDIS_DELETE')")
    public ResponseEntity deleteAll() {

        redisService.flushdb();
        return new ResponseEntity(HttpStatus.OK);
    }
}
