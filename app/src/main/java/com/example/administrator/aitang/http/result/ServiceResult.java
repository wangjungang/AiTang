package com.example.administrator.aitang.http.result;

import com.example.administrator.aitang.http.error.ErrorDes;

/**
 * Created by wangzexin on 2017/2/14.
 */
public class ServiceResult extends CommonResult {

    public ServiceResult(String code, String desc) {
        super(code, desc);
    }

    public static ServiceResult resultWithCode(String code) {
        return new ServiceResult(code, ErrorDes.getErrorDesc(code));
    }
}
