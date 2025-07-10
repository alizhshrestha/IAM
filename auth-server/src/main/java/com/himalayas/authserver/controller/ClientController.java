package com.himalayas.authserver.controller;

import com.himalayas.authserver.dto.ClientDto;
import com.himalayas.authserver.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/api/clients")
@RequiredArgsConstructor
public class ClientController {

  private final ClientMapper clientMapper;

  @GetMapping("/tenant")
  public List<ClientDto> getAllClientsFromTenant(@RequestParam String tenantId) {
    return clientMapper.findClientsByTenantId(tenantId);
  }

}
