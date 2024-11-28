package com.lovemarker.global.exception;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.dto.ApiResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 400 BAD_REQUEST
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponseDto handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        return ApiResponseDto.error(ErrorCode.MISSING_REQUIRED_INFO_EXCEPTION, String.format("%s. (%s)", fieldError.getDefaultMessage(), fieldError.getField()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ApiResponseDto handleMissingRequestHeaderException(final MissingRequestHeaderException e) {
        return ApiResponseDto.error(ErrorCode.MISSING_REQUIRED_HEADER_EXCEPTION, String.format("%s. (%s)", ErrorCode.MISSING_REQUIRED_HEADER_EXCEPTION.getMessage(), e.getHeaderName()));
    }

    /**
     * 500 SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ApiResponseDto<Object> handleException(final Exception error, final HttpServletRequest request) {
        log.error(error.getMessage());
        log.error(Arrays.toString(error.getStackTrace()));
        return ApiResponseDto.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * CUSTOM_ERROR
     */
    @ExceptionHandler(BasicException.class)
    protected ResponseEntity<ApiResponseDto> handleBasicException(BasicException e) {
        return ResponseEntity.status(e.getErrorStatusCode())
            .body(ApiResponseDto.error(e.getErrorCode(), e.getMessage()));
    }
}
