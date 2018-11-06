package com.ybkj.gun.controller;

import com.ybkj.gun.service.MissionService;
import com.ybkj.model.BaseModel;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @项目名称：
 * @类名称：
 * @类描述：协助查找功能模块
 * @创建人：liujiayi
 * @创建时间：2018/11/4 14:35
 * @修改时间：2018/11/4 14:35
 * @version：1.0
 */
@SuppressWarnings("all")
@RestController
@Slf4j
@Api(value = "/",description = "协助查找功能")
@RequestMapping("/mission")
public class MissionController {

    @Autowired
    private MissionService missionService;

    /**
     * 问题：需求不明确
     *     1、是怎么样发送，协助查询，不需要指定发送给某一个腕表吗？
     *
     * @Description:  功能描述（协助查找）
     * @Author:       刘家义
     * @CreateDate:   2018/11/4 14:44
     * 1、判断丢失的枪，是否已经连接上
     * 2、再将创建协助查找消息，进行推送消息
    */
    public BaseModel insertMission(){

        return null;
    }
}
