package com.itmuch.contentcenter.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * SpringMVC 提供的全局异常处理
 * @author 何林冲
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionErrorHandler {


    /**
     * 重构 SecurityException 异常返回体
     * @param e
     * @return
     */
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorBody> error(SecurityException e){
        log.warn("发生SecurityException异常",e);
        return new ResponseEntity<ErrorBody>(
                ErrorBody.builder()
                        .body(e.getMessage())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );

    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ErrorBody{
    private String body;
    private int status;
}
