package com.himalayas.shareddomain.enums;

public enum WeekDays {
  SUNDAY("Sunday"), MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"), THURSDAY("Thursday"), FRIDAY("Friday"), SATURDAY("Saturday");

  private final String value;

  WeekDays(String value){
    this.value = value;
  }

  public String getValue(){
    return value;
  }
}
