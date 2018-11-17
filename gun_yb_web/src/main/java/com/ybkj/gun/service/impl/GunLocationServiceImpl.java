package com.ybkj.gun.service.impl;

import com.ybkj.common.entity.GunLocationVO;
import com.ybkj.gun.mapper.AppMapper;
import com.ybkj.gun.mapper.GunLocationMapper;
import com.ybkj.gun.model.GunLocation;
import com.ybkj.gun.service.GunLocationService;
import com.ybkj.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @项目名称：
 * @类名称：
 * @类描述：枪支地理位置，业务逻辑具体实现
 * @创建人：liujiayi
 * @创建时间：2018/11/12 14:19
 * @修改时间：2018/11/12 14:19
 * @version：1.0
 */
@Service
@Transactional
@Slf4j
@SuppressWarnings("all")
public class GunLocationServiceImpl implements GunLocationService{

    @Autowired
    private GunLocationMapper gunLocationMapper;
    @Autowired
    private AppMapper appMapper;

    /**
     * @Description:  功能描述（枪支动态地理位置）
     * @Author:       刘家义
     * @CreateDate:   2018/11/12 14:20
     * @param gunId
     * @param appId
     * @return
     * @throws Exception
    */
    @Override
    public List<GunLocationVO> findGunDynamic(String gunId, String appName) throws Exception {
        return gunLocationMapper.selectGunDynamic(gunId,appName);
    }
}
