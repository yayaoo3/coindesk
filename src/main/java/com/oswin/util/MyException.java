package com.oswin.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MyException extends RuntimeException {

    private String errMsg;

    //API回應自定義的exception信息
    public MyException(String errMsg) {
       this.errMsg = errMsg;
    }


    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}