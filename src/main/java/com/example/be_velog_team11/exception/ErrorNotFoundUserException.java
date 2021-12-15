package com.example.be_velog_team11.exception;


import com.example.be_velog_team11.exception.ErrorUtils.CustomException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;

public class ErrorNotFoundUserException extends CustomException {

    public ErrorNotFoundUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
