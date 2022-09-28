package com.oswin.handler;

import com.oswin.controller.CoinDeskController;
import com.oswin.util.MyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionHandler.class);




    /**
     * Handle add coin exception
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ResponseEntity myException(MyException e) {
        return new ResponseEntity(e.getErrMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
