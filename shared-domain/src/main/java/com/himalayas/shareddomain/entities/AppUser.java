package com.himalayas.shareddomain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "app_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
  @Id
  private String id;

  private String username;
  private String password;
  private Boolean enabled;
  private String tenantId;
  private Boolean isSuperAdmin;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "app_user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "role")
  private Set<String> roles = new HashSet<>();

  @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<User> users = new ArrayList<>();
}