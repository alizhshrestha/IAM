package com.himalayas.shareddomain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schools")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class School {
  @Id
  private String id;

  private String name;
  private String logoUrl;
  private String address;
  private String academicYear;
  private String tenantId;

  @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<User> users = new HashSet<>();

  @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ClassEntity> classes = new HashSet<>();

  @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<SubjectEntity> subjects = new HashSet<>();
}