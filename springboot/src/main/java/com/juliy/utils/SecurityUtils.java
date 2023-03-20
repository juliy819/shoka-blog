package com.juliy.utils;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 密码加密
 * @author juliy
 * @date 2023/3/1 19:13
 */
public class SecurityUtils {

    /**
     * 校验密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否正确
     */
    public static boolean checkPassword(String oldPassword, String newPassword) {
        String encryptedPassword = sha256Encrypt(newPassword);
        return StrUtil.equals(encryptedPassword, oldPassword);
    }

    /**
     * sha256加密
     * @param password 密码
     * @return 加密后的密码
     */
    public static String sha256Encrypt(String password) {
        return SaSecureUtil.sha256(password);
    }

    /**
     * 是否为管理员
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(int userId) {
        return 1 == userId;
    }
}
