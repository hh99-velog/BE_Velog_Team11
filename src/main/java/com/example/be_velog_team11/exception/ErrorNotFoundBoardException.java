package com.example.be_velog_team11.exception;


import com.example.be_velog_team11.exception.ErrorUtils.CustomException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;

public class ErrorNotFoundBoardException extends CustomException {

    public ErrorNotFoundBoardException(ErrorCode errorCode) {
        super(errorCode);
    }
}
