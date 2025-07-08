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
import java.util.List;

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
  private List<User> users = new ArrayList<>();
}