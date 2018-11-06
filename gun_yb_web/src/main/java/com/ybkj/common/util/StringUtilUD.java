package com.ybkj.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @项目名称：
 * @类名称：
 * @类描述：自定义字符串解码操作
 * @创建人：liujiayi
 * @创建时间：2018/11/5 10:25
 * @修改时间：2018/11/5 10:25
 * @version：1.0
 */
@SuppressWarnings("ALL")
@Slf4j
public class StringUtilUD {

    /**
     * @Description:  功能描述（分割已，分割的字符串）
     * @Author:       刘家义
     * @CreateDate:   2018/11/5 10:27
    */
    public static String[] slicerComma(String s){
        String[] result=s.split(",");
        log.debug("分割 “,” =result="+result.toString());
        return result;
    }
}
