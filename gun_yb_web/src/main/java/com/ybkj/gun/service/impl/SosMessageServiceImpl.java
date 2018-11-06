package com.ybkj.gun.service.impl;

import com.ybkj.gun.mapper.SosMessageMapper;
import com.ybkj.gun.model.SosMessage;
import com.ybkj.gun.service.SosMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：
 * @创建人：liujiayi
 * @创建时间：2018/11/3 16:08
 * @修改时间：2018/11/3 16:08
 * @version：1.0
 */

@SuppressWarnings("all")
@Service
@Transactional
@Slf4j
public class SosMessageServiceImpl implements SosMessageService{

    @Autowired
    private SosMessageMapper sosMessageMapper;
    /**
     * @Description:  功能描述（查询警告信息列表:枪号）
     * @Author:       刘家义
     * @CreateDate:   2018/11/3 16:47
    */
    @Override
    public List<SosMessage> findSosMessages(String gunCode,String appCode) throws Exception {
        return sosMessageMapper.selectSosMessageAll(gunCode,appCode);
    }
}
