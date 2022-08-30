package com.yyok.share.framework.constant;

/**
 * yyok统一常量
 *
 * @author yyok
 * @since 2020-02-27
 */
public interface IConstants {

    /**
     * 订单自动取消时间（分钟）
     */
    long ORDER_OUTTIME_UNPAY = 30;
    /**
     * 订单自动收货时间（天）
     */
    long ORDER_OUTTIME_UNCONFIRM = 7;
    /**
     * redis订单未付款key
     */
    String REDIS_ORDER_OUTTIME_UNPAY = "order:unpay:";
    /**
     * redis订单收货key
     */
    String REDIS_ORDER_OUTTIME_UNCONFIRM = "order:unconfirm:";

    /**
     * redis拼团key
     */
    String REDIS_PINK_CANCEL_KEY = "pink:cancel:";

    /**
     * 微信支付service
     */
    String YYOK_WEIXIN_PAY_SERVICE = "yyok_weixin_pay_service";

    /**
     * 微信支付小程序service
     */
    String YYOK_WEIXIN_MINI_PAY_SERVICE = "yyok_weixin_mini_pay_service";

    /**
     * 微信支付app service
     */
    String YYOK_WEIXIN_APP_PAY_SERVICE = "yyok_weixin_app_pay_service";

    /**
     * 微信公众号service
     */
    String YYOK_WEIXIN_MP_SERVICE = "yyok_weixin_mp_service";


    /**
     * yyok默认密码
     */
    String YYOK_DEFAULT_PWD = "123456";

    /**
     * yyok默认注册图片
     */
    String YYOK_DEFAULT_AVATAR = "https://image.dayouqiantu.cn/5e79f6cfd33b6.png";

    /**
     * 腾讯地图地址解析
     */
    String QQ_MAP_URL = "https://apis.map.qq.com/ws/geocoder/v1/";

    /**
     * redis首页键
     */
    String YYOK_REDIS_INDEX_KEY = "yyok:index_data";

    /**
     * 充值方案
     */
    String YYOK_RECHARGE_PRICE_WAYS = "yyok_recharge_price_ways";
    /**
     * 首页banner
     */
    String YYOK_HOME_BANNER = "yyok_home_banner";
    /**
     * 首页菜单
     */
    String YYOK_HOME_RESOURCES = "yyok_home_resources";
    /**
     * 首页滚动新闻
     */
    String YYOK_HOME_ROLL_NEWS = "yyok_home_roll_news";
    /**
     * 热门搜索
     */
    String YYOK_HOT_SEARCH = "yyok_hot_search";
    /**
     * 个人中心菜单
     */
    String YYOK_MY_RESOURCES = "yyok_my_resources";
    /**
     * 秒杀时间段
     */
    String YYOK_SECKILL_TIME = "yyok_seckill_time";
    /**
     * 签到天数
     */
    String YYOK_SIGN_DAY_NUM = "yyok_sign_day_num";

    /**
     * 打印机配置
     */
    String YYOK_ORDER_PRINT_COUNT = "order_print_count";
    /**
     * 飞蛾用户信息
     */
    String YYOK_FEI_E_USER = "fei_e_user";
    /**
     * 飞蛾用户密钥
     */
    String YYOK_FEI_E_UKEY = "fei_e_ukey";

    /**
     * 打印机配置
     */
    String YYOK_ORDER_PRINT_COUNT_DETAIL = "order_print_count_detail";

    /**
     * 微信菜单
     */
    String WECHAT_MENUS = "wechat_menus";

}
