//package com.himalayas.schoolservice.entity;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Entity
//@Table(name = "schools")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class School {
//
//  @Id
//  private String id;
//
//  @Column(nullable = false)
//  private String name;
//
//  private String logoUrl;
//
//  private String address;
//
//  private String academicYear;
//
//  @Column(nullable = false, unique = true)
//  private String tenantId; // corresponds to tenant identifier
//
//  @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
//  private List<AppUser> users = new ArrayList<>();
//}
//
