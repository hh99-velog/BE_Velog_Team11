package com.example.be_velog_team11.exception;

import com.example.be_velog_team11.exception.ErrorUtils.CustomException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;

public class ErrorNotFoundLikeException extends CustomException {
    public ErrorNotFoundLikeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
