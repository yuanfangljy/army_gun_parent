package com.ybkj.common.util;

import com.ybkj.common.entity.PermissionVo;
import com.ybkj.model.BaseModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/11/26 20:29
 * @修改时间：2018/11/26 20:29
 * @version：1.0
 */
@SuppressWarnings("all")
@Slf4j
@Component
public class Tree {

    /**
     * @Description:  功能描述（获取到菜单树）
     * @Author:       刘家义
     * @CreateDate:   2018/11/26 20:31
    */
    public  BaseModel getTreeMenu(List<PermissionVo> permissionVoList){
// 节点列表（散列表，用于临时存储节点对象）
        List<Node> nodeList = new ArrayList();
        // 根节点
        Node root = null;
        // 根据结果集构造节点列表（存入散列表）
        for (Iterator it = permissionVoList.iterator(); it.hasNext();) {
            PermissionVo dataRecord = (PermissionVo) it.next();
            Node node = new Node();
            node.mid = dataRecord.getMid();
            node.name =dataRecord.getName();
            node.parentId = dataRecord.getParentId();
            node.zindex = dataRecord.getZindex();
            node.istype = dataRecord.getIstype();
            node.descpt = dataRecord.getDescpt();
            node.code = dataRecord.getCode();
            node.icon = dataRecord.getIcon();
            node.page = dataRecord.getPage();
            node.enabled = dataRecord.getEnabled();
            node.childrens=dataRecord.getChildren();

            nodeList.add(node.mid, node);
        }
        // 构造无序的多叉树
        for (Iterator it = nodeList.iterator(); it.hasNext();) {
            Node node = (Node) ((Map.Entry) it.next()).getValue();
            if (node.parentId == null || node.parentId.equals("")) {
                root = node;
            } else {
                ((Node) nodeList.get(node.parentId)).addChild(node);
            }
        }
        // 输出无序的树形菜单的JSON字符串
        System.out.println(root.toString());
        // 对多叉树进行横向排序
        root.sortChildren();
        // 输出有序的树形菜单的JSON字符串
        System.out.println(root.toString());
        BaseModel baseModel=new BaseModel();
        baseModel.add("permissionlist",root);
        log.debug("------------------"+root.toString());
        return baseModel;
    }



    /**
     * 节点类
     */
    @Data
    public class Node {
        private Integer mid;
        private String name;
        private Integer parentId;
        private Integer zindex;
        private Integer istype;
        private String descpt;
        private String code;
        private String icon;
        private String page;
        private Integer enabled;
        private List<PermissionVo> childrens;
        private Children children = new Children();



        // 兄弟节点横向排序
        public void sortChildren() {
            if (children != null && children.getSize() != 0) {
                children.sortChildren();
            }
        }

        // 添加孩子节点
        public void addChild(Node node) {
            this.children.addChild(node);
        }
    }

    /**
     * 孩子列表类
     */
    class Children {
        private List list = new ArrayList();

        public int getSize() {
            return list.size();
        }

        public void addChild(Node node) {
            list.add(node);
        }

        // 拼接孩子节点的JSON字符串
        public String toString() {
            String result = "[";
            for (Iterator it = list.iterator(); it.hasNext();) {
                result += ((Node) it.next()).toString();
                result += ",";
            }
            result = result.substring(0, result.length() - 1);
            result += "]";
            return result;
        }

        // 孩子节点排序
        public void sortChildren() {
            // 对本层节点进行排序
            // 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器
            Collections.sort(list,new NodeIDComparator());
            // 对每个节点的下一层节点进行排序
            for (Iterator it = list.iterator(); it.hasNext();) {
                ((Node) it.next()).sortChildren();
            }
        }
    }

    /**
     * 节点比较器
     */
    public class NodeIDComparator implements Comparator {
        // 按照节点编号比较
        public int compare(Object o1, Object o2) {
            int j1 = ((Node)o1).mid;
            int j2 = ((Node)o2).mid;
            return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
        }
    }
}
