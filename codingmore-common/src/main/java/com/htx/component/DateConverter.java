package com.htx.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/10 21:29
 * @Desc: 日期转换类:将标准日期、标准日期时间、时间戳转换成Date类型
 */
@Slf4j
public class DateConverter implements Converter<String, Date> {
    private static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String SHORTDATEFORMAT = "yyyy-MM-dd";

    private static final String TIMESTAMPFORMAT = "^\\d+$";

    @Override
    public Date convert(String value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }

        value = value.trim();

        try{
            if(value.contains("-")){
                SimpleDateFormat format;
                if(value.contains(":")){
                    format = new SimpleDateFormat(DATEFORMAT);
                }else{
                    format = new SimpleDateFormat(SHORTDATEFORMAT);
                }
                return format.parse(value);
            }else if(value.matches(TIMESTAMPFORMAT)){
                Long lDate = Long.valueOf(value);
                return new Date(lDate);
            }
        } catch (Exception e) {
            log.warn("时间解析失败：{}，异常信息：{}", e.getMessage(), e);
        }
        return null;
    }
}
