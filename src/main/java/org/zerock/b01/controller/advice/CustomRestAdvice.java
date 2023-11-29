package org.zerock.b01.controller.advice;


import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
//컨트롤러에서 발생하느 ㄴ예외에 대해 JSON과 같은 순수한 응답 메시지를 생성해서 보낼 수 있음.
@Log4j2
public class CustomRestAdvice {

    //-ExceptionHandler를 이해하기 위한 스키마
    //Spring에서 예외처리 순서는,
    //Dispatcher Servlet > HandlerInterceptor > Controller 예외발생 >
    //HandlerExceptionResolver
    // (스프링은HandlerExceptionResolver빈을 등록하여 예외를 해결하는데, @ExceptionHandler
    //애너테이션을 통해 해당 빈으로 등록할 수도 있음.) > ControllerAdvice(예외를 처리하는
    //ExceptionHandler 애너테이션 메소드를 찾아 해결) > DefaultHandlerExcetptionResolver





    @ExceptionHandler(BindException.class)
    //웹앱 내에서 BindException 로 던져지는 예외에 관한 핸들러 메소드임을 명시
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleBindException(BindException e) {
    //컨트롤러에서 BindException이 던져지는 경우, 이를 이용해 JSON메시지와 400error BAD Request를 전송하게 함.
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        if(e.hasErrors()){

            BindingResult bindingResult = e.getBindingResult();

            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getCode());
            });
        }

        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleFKException(Exception e) {

        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("time", ""+System.currentTimeMillis());
        errorMap.put("msg",  "constraint fails");
        return ResponseEntity.badRequest().body(errorMap);
    }


//    @ExceptionHandler(NoSuchElementException.class)
//    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
//    public ResponseEntity<Map<String, String>> handleNoSuchElement(Exception e) {
//
//        log.error(e);
//
//        Map<String, String> errorMap = new HashMap<>();
//
//        errorMap.put("time", ""+System.currentTimeMillis());
//        errorMap.put("msg",  "No Such Element Exception");
//        return ResponseEntity.badRequest().body(errorMap);
//    }

    @ExceptionHandler({
            NoSuchElementException.class,
            EmptyResultDataAccessException.class }) //추가
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleNoSuchElement(Exception e) {

        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("time", ""+System.currentTimeMillis());
        errorMap.put("msg",  "No Such Element Exception");
        return ResponseEntity.badRequest().body(errorMap);
    }

}
