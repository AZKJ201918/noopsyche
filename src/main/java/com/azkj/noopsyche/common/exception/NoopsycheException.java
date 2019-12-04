package com.azkj.noopsyche.common.exception;

public class NoopsycheException extends Exception{

    private  int OVERALL_SITUATTION=500;

    public NoopsycheException(String message) {
        super(message);
    }

    public NoopsycheException(Integer code, String message) {
        super(message);
        this.OVERALL_SITUATTION=code;
    }


    public int getStatusCode() {
        return OVERALL_SITUATTION;
    }
}
