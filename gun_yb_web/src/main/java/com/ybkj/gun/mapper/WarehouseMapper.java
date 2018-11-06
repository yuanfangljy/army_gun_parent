package com.ybkj.gun.mapper;

import com.ybkj.gun.model.Warehouse;
import com.ybkj.gun.model.WarehouseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WarehouseMapper {
    long countByExample(WarehouseExample example);

    int deleteByExample(WarehouseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Warehouse record);

    int insertSelective(Warehouse record);

    List<Warehouse> selectByExample(WarehouseExample example);

    Warehouse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Warehouse record, @Param("example") WarehouseExample example);

    int updateByExample(@Param("record") Warehouse record, @Param("example") WarehouseExample example);

    int updateByPrimaryKeySelective(Warehouse record);

    int updateByPrimaryKey(Warehouse record);


    /**
     * @Description:  功能描述（根据库室名称，查询库室信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 16:21
     */
    Warehouse selectWareHouseByName(String name);


    /**
     * @Description:  功能描述（查询库室信息）
     * @Author:       刘家义
     * @CreateDate:   2018/11/1 16:21
     */
    List<Warehouse> selectWareHouses();
}