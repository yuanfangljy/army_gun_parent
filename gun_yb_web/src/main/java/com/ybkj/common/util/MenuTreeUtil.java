package com.ybkj.common.util;

import com.ybkj.common.entity.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/11/27 16:13
 * @修改时间：2018/11/27 16:13
 * @version：1.0
 */
@Component
public class MenuTreeUtil {

    /**
     * @param rootMenu  数据库查询出来的所有机构集合
     * @param pid      父id
     */
    public List<PermissionVo> orgRecursion(List<PermissionVo> rootMenu, Integer pid) {
        List<PermissionVo> childList = new ArrayList<>();//存放直接子菜单
        /**
         *开始遍历二级菜单以及它的直接子菜单
         */
        for (PermissionVo menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId()!=null) {//导入org.apache.commons.lang3.StringUtils;
                if (pid==menu.getParentId()) {//尽量让id 在前面，因为他不会为空（数据库设计为主键），parentId 不一定都有值。
                    childList.add(menu);//相等的话说明这些使它（id）的直接子节点,加入childList
                }
            }
        }//这时候已经将一级菜单以及一级的直接子孩子遍历出来了。
        /**
         * 把子菜单的直接子菜单再循环一遍
         * 这时候就是从Menu的直接子菜单中获得需要遍历的菜单也就是childList
         */
        for (PermissionVo menu : childList) {
            if (StringUtils.isBlank(menu.getPage())) {//这个判断的意思是 如果url 不为空说明是最后一个节点，为空说明他不是最后一个子节点，这时候就需要去遍历
                menu.setChildren(orgRecursion( rootMenu,menu.getMid()));//递归
            }
        }
        if (childList.size() == 0) {// 递归退出条件（走到这里childList 大小等于0 说明该节点就是最后一个）
            return null;
        }
        return childList;
    }
}
