package com.wj.system.controller;


import com.wj.server.dto.ResponseDto;
import com.wj.server.exception.BusinessException;
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

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseDto businessExceptionHandler(BusinessException e){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        LOG.error("请求参数异常:"+e.getCode().getDesc());
        LOG.error("请求参数异常code:"+e.getCode());
        responseDto.setMessage("请求错误:" + e.getCode().getDesc());
        return responseDto;
    }
}
