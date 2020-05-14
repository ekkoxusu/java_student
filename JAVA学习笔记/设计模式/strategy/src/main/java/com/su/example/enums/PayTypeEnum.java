package com.su.example.enums;

/**
 * @author su
 */

public enum PayTypeEnum {

    /**
     * 1-阿里
     */
    ALI_PAY(1,"阿里支付"),
    /**
     * 2-微信
     */
    WEIXIN_PAY(2,"微信支付"),
    /**
     * 3-银联
     */
    UNION_PAY(3,"银联"),
    ;
    private Integer code;
    private String  msg;

    PayTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
