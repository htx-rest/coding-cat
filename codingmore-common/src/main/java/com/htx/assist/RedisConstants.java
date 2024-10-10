package com.htx.assist;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/9 23:14
 * @Desc: Redis常量类
 */
public class RedisConstants {
    private RedisConstants(){

    }

    private static final String REDIS_WEB_DATABASE = "codingmore-web-redis";

    private static final String PAGE_VIEW = "pageView";

    private static final String POST_LIKE_COUNT = "likeCount";

    private static final String REDIS_ADMIN_DATABASE = "codingmore-admin-redis";

    private static final String REDIS_KEY_USER = "user";

    private static final String REDIS_KEY_RESOURCE_LIST = "resource";

    public static final Long REDIS_EXPIRE = 86400L;

    public static String getAdminUserKey(String lastKey) {
        return REDIS_ADMIN_DATABASE + ":" + REDIS_KEY_USER + ":" + lastKey;
    }

    public static String getAdminResourceKey(Long lastKey) {
        return REDIS_ADMIN_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + lastKey;
    }

    public static String getWebPageViewKey(String lastKey) {
        return REDIS_WEB_DATABASE + ":" + PAGE_VIEW + ":" + lastKey;
    }

    public static String getWebPostLikeKey(String lastKey) {
        return REDIS_WEB_DATABASE + ":" + POST_LIKE_COUNT + ":" + lastKey;
    }
}
