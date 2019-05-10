package com.ybkj.common.initialize;

import com.ybkj.enums.IStatusMessage;
import com.ybkj.gun.model.GunUser;
import com.ybkj.gun.service.GunUserService;
import com.ybkj.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/12/6 9:18
 * @修改时间：2018/12/6 9:18
 * @version：1.0
 */
@SuppressWarnings("all")
@Slf4j
@RestController
@RequestMapping("/operation")
public class Operation {

    @Autowired
    private GunUserService gunUserService;

    /**
     * @Description:  功能描述（清空数据，初始化到出库）
     * @Author:       刘家义
     * @CreateDate:   2018/12/6 9:21
     * 1、需要将 worehouse_records 所以不是 state=4 的数据改成 4
     * 2、    将 gun_user 所有状态不是2的数据，改成 2
     * 3、    将 app_gun_user 状态不是 0 的数据，改成 0
    */
    @RequestMapping(value = "/outboundInitialization",method = RequestMethod.PUT)
    public BaseModel outboundInitialization(){
        BaseModel baseModel=new BaseModel();
        log.debug("-------------- 清空数据，初始化到出库 ------------" );
        try {
            baseModel=gunUserService.updateOutboundInitialization();
        } catch (Exception e) {
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage("清空数据，初始化到出库！异常");
            e.printStackTrace();
            log.error("清空数据，初始化到出库！异常！", e);
        }
        return baseModel;
    }
}
