package com.example.administrator.aitang.http.result;


import com.example.administrator.aitang.http.error.ErrorCode;
import com.example.administrator.aitang.http.error.ErrorDes;

/**
 * Created by wangzexin on 2017/2/14.
 */
public class CommonResult {
    private String code;
    private String desc;

    public CommonResult(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static CommonResult resultWithCode(String code) {
        return new CommonResult(code, ErrorDes.getErrorDesc(code));
    }

    public boolean isSuccess() {
        return ErrorCode.ERROR_CODE_SUCCESS .equals(code);
    }
}
