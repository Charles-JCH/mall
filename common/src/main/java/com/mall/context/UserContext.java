package com.mall.context;

public class UserContext {

    // /**
    //  * 获取当前用户ID
    //  */
    // public static Long getUserId() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
    //         return null;
    //     }
    //     Jwt jwt = (Jwt) authentication.getPrincipal();
    //     Object userIdObj = jwt.getClaim("userId");
    //     if (userIdObj instanceof Long) {
    //         return (Long) userIdObj;
    //     } else if (userIdObj instanceof Integer) {
    //         return ((Integer) userIdObj).longValue();
    //     } else {
    //         return Long.valueOf(String.valueOf(userIdObj));
    //     }
    // }
    //
    // /**
    //  * 获取当前用户名
    //  */
    // public static String getUsername() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication == null) return null;
    //     return authentication.getName(); // 对应 JWT 的 sub 字段
    // }

}
