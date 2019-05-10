package com.ybkj.common.util;

import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.WebUser;
import com.ybkj.model.BaseModel;
import jdk.nashorn.internal.ir.BaseNode;
import org.apache.shiro.SecurityUtils;

/**
 * @项目名称：
 * @类名称：
 * @类描述：获取当前用户
 * @创建人：liujiayi
 * @创建时间：2018/11/1 15:57
 * @修改时间：2018/11/1 15:57
 * @version：1.0
 */
@SuppressWarnings("all")
public class ActiveUser {

    /**
     * @Description:  功能描述（判断当前用户是否在线）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 16:05
    */
    public static Boolean isActiveUser(){
        WebUser webUser =(WebUser) SecurityUtils.getSubject().getPrincipal();
        if(null!=webUser){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @Description:  功能描述（判断当前用户是否在线）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 16:05
     */
    public static BaseModel isActiveUserResponseResult(){
        BaseModel baseModel=new BaseModel();
        WebUser webUser =(WebUser) SecurityUtils.getSubject().getPrincipal();
        if(null==webUser){
            baseModel.setStatus(IStatusMessage.SystemStatus.ERROR.getCode());
            baseModel.setErrorMessage("您已经下线，请重新登录");
        }
        return baseModel;
    }

    /**
     * @Description:  功能描述（获取当前用户信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 16:05
    */
    public static WebUser getActiveUser(){
        WebUser webUser =(WebUser) SecurityUtils.getSubject().getPrincipal();
        if(null!=webUser){
            return webUser;
        }else{
            return null;
        }
    }


    /**
     * @Description:  功能描述（先判断是否存在，在获取用户信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 16:08
    */
    /*public static WebUser isGetActionUser(){
        WebUser existWebUser =(WebUser) SecurityUtils.getSubject().getPrincipal();
        if(null==existWebUser){

        }
        return null;
    }*/
}
