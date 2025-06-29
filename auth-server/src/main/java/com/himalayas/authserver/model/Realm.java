package com.himalayas.authserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "realm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Realm {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(length = 36)
  private String id;

  @Column(unique = true, nullable = false)
  private String name;

  private String description;

  @Column(nullable = false)
  private boolean enabled = true;
}

