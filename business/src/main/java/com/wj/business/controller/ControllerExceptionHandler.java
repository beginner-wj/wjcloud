package com.wj.business.controller;


import com.wj.server.dto.ResponseDto;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler  {
    private  static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    public ResponseDto validationExceptionHandler(ValidationException validationException){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        LOG.debug("请求参数异常:"+validationException.getMessage());
        responseDto.setMessage("请求参数错误");
        return responseDto;
    }
}
