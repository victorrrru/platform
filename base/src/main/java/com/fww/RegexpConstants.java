package com.fww;

/**
 * @author 范文武
 * @date 2018/04/27 15:44
 */
public class RegexpConstants {
    public static String[] REGEXP_WORD = new String[]{"^[\\w\\-]+$", "大小写英文字母、数字、下划线"};
    public static String[] REGEXP_LETTER = new String[]{"^[A-Za-z]+$", "大小写英文字母"};
    public static String[] REGEXP_CHINESE = new String[]{"^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$", "中文"};
    public static String[] REGEXP_CHINESE_AND_WORD = new String[]{"^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w\\-]+$", "中文、大小写字母、数字、下划线"};
    public static String[] REGEXP_INTEGER = new String[]{"^-?[1-9]\\d*$", "正负整数"};
    public static String[] REGEXP_NUM = new String[]{"^([+-]?)\\d*\\.?\\d+$", "数字格式：可以是正负数、小数"};
    public static String[] REGEXP_EMAIL = new String[]{"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$", "电子邮箱格式：只允许大小写字母、数字、下划线"};
    public static String[] REGEXP_MOBILE = new String[]{"^1[34578]\\d{9}$", "手机号码格式：13、14、15、17、18开头的11位号码"};
    public static String[] REGEXP_TEL = new String[]{"^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$", "固定电话格式：区号-电话号码"};
    public static String[] REGEXP_POSTCODE = new String[]{"^\\d{6}$", "邮政编码格式：6位整数"};
    public static String[] REGEXP_DATE = new String[]{"^\\d{4}\\-\\d{2}\\-\\d{2}$", "日期格式：YYYY-MM-DD"};
    public static String[] PRIMARY_KEY_ID;
    public static String[] VALIDATE_ENTITY_ID;
    public static String[] VALIDATE_USER_LOGIN_ACCOUNT;
    public static String[] VALIDATE_PHONE_LOGIN_ACCOUNT;
    public static String[] VALIDATE_USER_LOGIN_PASSWORD;
    public static String[] VALIDATE_BIND_ACCOUNT;
    public static String[] VALIDATE_REGISTER_PASSWORD;
    public static String[] VALIDATE_PASSWORD_OLD;
    public static String[] VALIDATE_PASSWORD_NEW;
    public static String[] VALIDATE_EMAIL;
    public static String[] VALIDATE_ADDRESS_PHONE;
    public static String[] VALIDATE_ADDRESS_POSTCODE;
    public static String[] VALIDATE_ADDRESS_DETAIL;
    public static String[] VALIDATE_ADDRESS_PROVINCE;
    public static String[] VALIDATE_ADDRESS_CITY;
    public static String[] VALIDATE_ADDRESS_DISTRICT;
    public static String[] VALIDATE_USER_ID;
    public static String[] VALIDATE_FEEBACK_CONTENT;
    public static String[] VALIDATE_FEEBACK_CONTACT;
    public static String[] VALIDATE_VERSION_CODE;
    public static String[] VALIDATE_VERSION_TYPE;
    public static String[] VALIDATE_UPLOAD_FILE_SIZE;
    public static String[] VALIDATE_UPLOAD_FILE_CATEGORY;
    public static String[] VALIDATE_UPLOAD_FILE_NAME;

    public RegexpConstants() {
    }

    static {
        PRIMARY_KEY_ID = new String[]{"操作对象", "1", "19", REGEXP_INTEGER[0], REGEXP_INTEGER[1]};
        VALIDATE_ENTITY_ID = new String[]{"操作对象", "1", "19", REGEXP_INTEGER[0], REGEXP_INTEGER[1]};
        VALIDATE_USER_LOGIN_ACCOUNT = new String[]{"登录账号", "4", "16", REGEXP_WORD[0], REGEXP_WORD[1]};
        VALIDATE_PHONE_LOGIN_ACCOUNT = new String[]{"登录账号", "11", "11", REGEXP_MOBILE[0], REGEXP_MOBILE[1]};
        VALIDATE_USER_LOGIN_PASSWORD = new String[]{"登录密码", "6", "16", null, null};
        VALIDATE_BIND_ACCOUNT = new String[]{"绑定账号", "6", "64", REGEXP_WORD[0], REGEXP_WORD[1]};
        VALIDATE_REGISTER_PASSWORD = new String[]{"注册密码", "6", "16", null, null};
        VALIDATE_PASSWORD_OLD = new String[]{"旧密码", "6", "16", null, null};
        VALIDATE_PASSWORD_NEW = new String[]{"新密码", "6", "16", null, null};
        VALIDATE_EMAIL = new String[]{"电子邮箱", "6", "64", REGEXP_EMAIL[0], REGEXP_EMAIL[1]};
        VALIDATE_ADDRESS_PHONE = new String[]{"手机号码", "11", "11", REGEXP_MOBILE[0], REGEXP_MOBILE[1]};
        VALIDATE_ADDRESS_POSTCODE = new String[]{"邮政编码", "6", "6", REGEXP_POSTCODE[0], REGEXP_POSTCODE[1]};
        VALIDATE_ADDRESS_DETAIL = new String[]{"详细街道地址", "1", "64", null, null};
        VALIDATE_ADDRESS_PROVINCE = new String[]{"省份", "2", "6", REGEXP_CHINESE[0], REGEXP_CHINESE[1]};
        VALIDATE_ADDRESS_CITY = new String[]{"城市", "2", "6", REGEXP_CHINESE[0], REGEXP_CHINESE[1]};
        VALIDATE_ADDRESS_DISTRICT = new String[]{"区县", "2", "16", REGEXP_CHINESE[0], REGEXP_CHINESE[1]};
        VALIDATE_USER_ID = new String[]{"用户ID", "1", "19", REGEXP_INTEGER[0], REGEXP_INTEGER[1]};
        VALIDATE_FEEBACK_CONTENT = new String[]{"反馈内容", "1", "500", null, null};
        VALIDATE_FEEBACK_CONTACT = new String[]{"联系方式", "0", "32", null, null};
        VALIDATE_VERSION_CODE = new String[]{"版本号", "1", "19", REGEXP_INTEGER[0], REGEXP_INTEGER[1]};
        VALIDATE_VERSION_TYPE = new String[]{"版本类型", "1", "1", REGEXP_INTEGER[0], REGEXP_INTEGER[1]};
        VALIDATE_UPLOAD_FILE_SIZE = new String[]{"上传文件大小", "1", "19", REGEXP_INTEGER[0], REGEXP_INTEGER[1]};
        VALIDATE_UPLOAD_FILE_CATEGORY = new String[]{"上传文件分类", "1", "19", REGEXP_INTEGER[0], REGEXP_INTEGER[1]};
        VALIDATE_UPLOAD_FILE_NAME = new String[]{"上传文件名称", "1", "64", null, null};
    }
}
