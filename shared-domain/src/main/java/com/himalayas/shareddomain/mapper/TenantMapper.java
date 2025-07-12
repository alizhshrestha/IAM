package com.himalayas.shareddomain.mapper;

import com.himalayas.shareddomain.dto.response.TenantResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface TenantMapper {

  @Select("""
          SELECT t.id AS id, t.name AS name, t.domain AS domain
          FROM tenants t
          WHERE t.domain = #{domain};
          """)
  Optional<TenantResponseDto> findByDomain(@Param("domain") String domain);
}
