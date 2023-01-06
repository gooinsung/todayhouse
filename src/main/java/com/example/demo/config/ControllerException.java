package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static com.example.demo.config.BaseResponseStatus.*;


@RestControllerAdvice
public class ControllerException {

    final Logger logger= LoggerFactory.getLogger(ControllerException.class);

    @ExceptionHandler(NullPointerException.class)
    public BaseResponse nullPointExceptionHandler(NullPointerException e){
        logger.error("Controller 내 NullPointerException 발생");
        return new BaseResponse(DATABASE_ERROR);
    }

    @ExceptionHandler(BaseException.class)
    public BaseResponse BaseExceptionHandler(BaseException e){
        logger.error("Controller 내 BaseException 발생");
        return new BaseResponse(DATABASE_ERROR);
    }


/*    @ExceptionHandler(IOException.class)
    public BaseResponse IOExceptionHandler(IOException e){
        logger.error("Controller 내 IOException 발생");
        return new BaseResponse(IO_ERROR);
    }*/
}
