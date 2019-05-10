package com.ybkj.untils;

import com.ybkj.enums.IStatusMessage;
import com.ybkj.model.BaseModel;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：对请求的数据，进行后台校验
 * @创建人：liujiayi
 * @创建时间：2018/10/16 11:07
 * @修改时间：2018/10/16 11:07
 * @version：1.0
 */
public class ValidatorRequestParam {

    /**
     * @param obj
     * @param baseModel
     * @return
     * @描述：校验请求参数
     */
    public static boolean validatorRequestParam(Object obj, BaseModel baseModel) {
        boolean flag = false;
        Validator validator = new Validator();
        List<ConstraintViolation> ret = validator.validate(obj);
        if (ret.size() > 0) {
            // 校验参数有误
            baseModel.setStatus(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
            baseModel.setErrorMessage(ret.get(0).getMessageTemplate());
        } else {
            flag = true;
        }
        return flag;
    }
}
