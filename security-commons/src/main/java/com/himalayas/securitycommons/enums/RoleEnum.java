package com.himalayas.securitycommons.enums;

public enum RoleEnum {
  ROLE_ADMIN("admin"), ROLE_TEACHER("teacher"), ROLE_STUDENT("student");

  public final String name;

  RoleEnum(String name){
    this.name = name;
  }
}
