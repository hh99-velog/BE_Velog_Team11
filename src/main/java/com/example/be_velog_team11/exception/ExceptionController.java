package com.example.be_velog_team11.exception;

import com.example.be_velog_team11.exception.ErrorUtils.CustomException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ErrorNotFoundBoardException.class)
    public ErrorResponse BoardException(CustomException ex) {
        return ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getErrorCode().getMessage())
                .status(ex.getErrorCode().getStatus())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ErrorNotFoundUserException.class)
    public ErrorResponse UserException(CustomException ex) {
        return ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND)
                .message(ex.getErrorCode().getMessage())
                .status(ex.getErrorCode().getStatus())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            return ErrorResponse.builder()
                    .code(HttpStatus.BAD_REQUEST)
                    .message(fieldError.getDefaultMessage())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(LocalDateTime.now())
                    .build();
        }

        return ErrorResponse.builder().code(HttpStatus.BAD_REQUEST).build();
    }
}
