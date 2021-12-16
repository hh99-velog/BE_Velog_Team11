package com.example.be_velog_team11.exception;

import com.example.be_velog_team11.exception.ErrorUtils.CustomException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;

public class ErrorNotFoundCommentException extends CustomException {

    public ErrorNotFoundCommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
