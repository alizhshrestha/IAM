package com.himalayas.shareddomain.entities;

import com.himalayas.shareddomain.entities.auditable.AuditableEntity;
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
@Table(name = "tenants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tenant extends AuditableEntity {
  @Id
  private String id;
  private String name;
  private String domain;

  @OneToMany(mappedBy = "tenant",cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ClassEntity> classes = new HashSet<>();

  @OneToMany(mappedBy = "tenant",cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<SubjectEntity> subjects = new HashSet<>();
}