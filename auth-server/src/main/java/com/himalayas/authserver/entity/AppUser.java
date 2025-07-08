//package com.himalayas.authserver.entity;
//
//import jakarta.persistence.CollectionTable;
//import jakarta.persistence.Column;
//import jakarta.persistence.ElementCollection;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Set;
//
//@Entity
//@Table(name = "app_users")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class AppUser {
//  @Id
//  private String id;
//  private String tenantId;
//  private String username;
//  private String password;
//  private boolean enabled;
//
//  @ElementCollection(fetch = FetchType.EAGER)
//  @CollectionTable(name = "app_user_roles", joinColumns = @JoinColumn(name = "user_id"))
//  @Column(name = "role")
//  private Set<String> roles;
//}
