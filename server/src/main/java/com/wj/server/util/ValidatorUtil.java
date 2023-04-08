package com.wj.server.util;


import net.sf.jsqlparser.util.validation.ValidationException;
import org.springframework.util.StringUtils;

/***
 * 校验工具类
 */
public class ValidatorUtil {

    /**
     * 空字符串校验
     * @param str
     * @param fieldName
     */
     public static void require(Object str,String fieldName){
           if(StringUtils.isEmpty(str)){
               throw new ValidationException(fieldName+"不能为空");
           }
     }

    /***
     * 长度校验
     * @param str
     * @param fieldName
     * @param min
     * @param max
     */
      public static void length(String str,String fieldName,int min,int max){
          if(StringUtils.isEmpty(str)){
              return;
          }
         int length = 0;
         if(!StringUtils.isEmpty(str)){
              length = str.length();
         }
         if(length > max || length < min){
             throw new ValidationException(fieldName+"长度"+min+"~"+max+"位");
         }
      }

}
