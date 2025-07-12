package com.himalayas.securitycommons.tenant;

import com.himalayas.shareddomain.dto.response.TenantResponseDto;
import com.himalayas.shareddomain.entities.Tenant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TenantService {
    Optional<TenantResponseDto> findByIssuer(String issuer);
}
