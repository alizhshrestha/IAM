//package com.himalayas.shareddomain.entities;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.Date;
//
//@Entity
//@Table(name = "user_profiles")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserProfiles {
//
//  @Id
//  private String id;
//  private String phoneNumber;
//  private String gender;
//  private Date dob;
//  private String profilePic;
//  private String address;
//  private String bio;
//
//  @OneToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
//  private User user;
//}
