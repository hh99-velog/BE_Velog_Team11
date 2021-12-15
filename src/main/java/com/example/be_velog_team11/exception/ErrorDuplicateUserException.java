package com.example.be_velog_team11.exception;

import com.example.be_velog_team11.exception.ErrorUtils.CustomException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;

public class ErrorDuplicateUserException extends CustomException {
    public ErrorDuplicateUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
