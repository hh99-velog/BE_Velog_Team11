package com.example.be_velog_team11.exception.ErrorUtils;


public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
