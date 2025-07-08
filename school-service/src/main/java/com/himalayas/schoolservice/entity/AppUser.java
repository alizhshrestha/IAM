//package com.himalayas.schoolservice.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinTable;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.UUID;
//
//@Entity
//@Table(name = "users")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class AppUser {
//
//  @Id
//  private String id;
//
//  @Column(nullable = false)
//  private String fullName;
//
//  @Column(unique = true, nullable = false)
//  private String email;
//
//  @Column(nullable = false)
//  private String password;
//
//  private Boolean active = true;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "school_id", nullable = false)
//  private School school;
//
//  @ManyToMany(fetch = FetchType.LAZY)
//  @JoinTable(
//          name = "user_role",
//          joinColumns = @JoinColumn(name = "user_id"),
//          inverseJoinColumns = @JoinColumn(name = "role_id")
//  )
//  private Set<Role> roles = new HashSet<>();
//}
//
