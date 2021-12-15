package com.example.be_velog_team11.exception.ErrorUtils;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorResponse {

    private final HttpStatus code;
    private final LocalDateTime timestamp;
    private final String message;
    private final int status;
}
