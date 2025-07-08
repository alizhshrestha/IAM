package com.himalayas.schoolservice.mapper;

import com.himalayas.shareddomain.entities.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SchoolMapper {

  @Select("SELECT * FROM schools WHERE tenant_id = #{tenantId}")
  List<School> findByTenantId(@Param("tenantId") String tenantId);
}
