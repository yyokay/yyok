<template>
  <el-row :gutter="10" class="panel-group">
    <el-col :xs="16" :sm="16" :lg="8" class="card-panel-col">
      <div class="card-panel">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="font-weight: bolder">项目信息</span>
          </div>
          <div class="text item">
            <span style="font-weight: bolder">YYOK大数据系统：</span>
            SpringBoot2+MybatisPlus+SpringSecurity+aouth2+Lombok+swagger
          </div>
          <div class="text item">
            <span style="font-weight: bolder">技术选型：</span>
            SpringBoot2、mybatis、MyBatis-Plus
            、SpringSecurity、JPA 、Druid 、Slf4j 、Fastjson
            、JWT 、Redis 、Quartz 、Mysql 、swagger 、WxJava
            、Lombok 、Hutool
            、Mapstruct 、Redisson 、Rocketmq
          </div>
          <div class="text item">
            <span style="font-weight: bolder">QQ群：</span>112868186
          </div>
          <div class="text item">
            <span style="font-weight: bolder">gitee：</span><a target="_blank" href="https://gitee.com/yyokay/mall">访问码云</a>
          </div>
          <div class="text item">
            <span style="font-weight: bolder">github：</span><a target="_blank" href="https://github.com/yyokay/mall">访问github</a>
          </div>
          <div class="text item">
            <span style="font-weight: bolder">公众号演示：</span><img width="100" height="100" src="https://image.yyokay.com/5e79f9ff4c694.jpg">
          </div>
          <div class="text item">
            <span style="font-weight: bolder">小程序演示：</span>关注上面公众号之后，选择yyok小程序体验即可
          </div>
        </el-card>
      </div>
    </el-col>
    <el-col :xs="16" :sm="16" :lg="8" class="card-panel-col">
      <div class="card-panel">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="font-weight: bolder">开发+VIP说明</span>
          </div>
          <div class="text item">
            <generator />
          </div>
          <div class="text item">

          </div>
          <div class="text item">

          </div>
          <div class="text item">

          </div>
          <div class="text item">

          </div>
          <div class="text item">

          </div>
          <div class="text item">

          </div>
        </el-card>
      </div>
    </el-col>

  </el-row>
</template>
<script>
import { generator } from '@/views/generator/index'
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .text {
    font-size: 12px;
  }

  .item {
    margin-bottom: 10px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

.panel-group {
  margin-top: 18px;
  .card-panel-col{
    margin-bottom: 32px;
  }
  .card-panel {
    height: auto;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
    .icon-people {
      color: #40c9c6;
    }
    .icon-message {
      color: #36a3f7;
    }
    .icon-money {
      color: #f4516c;
    }
    .icon-shopping {
      color: #34bfa3
    }
    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }
    .card-panel-icon {
      float: left;
      font-size: 48px;
    }
    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;
      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }
      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}
</style>
