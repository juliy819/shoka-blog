package com.juliy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上传策略枚举
 * @author juliy
 * @date 2023/3/29 13:06
 */
@Getter
@AllArgsConstructor
public enum UploadModeEnum {

    /**
     * 本地
     */
    LOCAL("local", "LocalUploadStrategyImpl"),

    /**
     * 七牛云
     */
    QINIU("qiniu", "QiniuUploadStrategyImpl");

    /**
     * 模式
     */
    private final String mode;

    /**
     * 策略
     */
    private final String strategy;

    /**
     * 获取策略
     * @param mode 模式
     * @return 策略
     */
    public static String getStrategy(String mode) {
        for (UploadModeEnum value : UploadModeEnum.values()) {
            if (value.getMode().equals(mode)) {
                return value.getStrategy();
            }
        }
        return null;
    }

}
