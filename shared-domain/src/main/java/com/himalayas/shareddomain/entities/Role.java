package com.himalayas.shareddomain.entities;

import com.himalayas.shareddomain.entities.auditable.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AuditableEntity {
  @Id
  private String id;
  private String name;
  private String description;
}