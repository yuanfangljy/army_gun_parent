package com.ybkj.gun.mapper;

import com.ybkj.gun.model.SysSerialNumber;
import com.ybkj.gun.model.SysSerialNumberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysSerialNumberMapper {
    long countByExample(SysSerialNumberExample example);

    int deleteByExample(SysSerialNumberExample example);

    int insert(SysSerialNumber record);

    int insertSelective(SysSerialNumber record);

    List<SysSerialNumber> selectByExample(SysSerialNumberExample example);

    int updateByExampleSelective(@Param("record") SysSerialNumber record, @Param("example") SysSerialNumberExample example);

    int updateByExample(@Param("record") SysSerialNumber record, @Param("example") SysSerialNumberExample example);
}