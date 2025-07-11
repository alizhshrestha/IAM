package com.himalayas.shareddomain.entities;

import com.himalayas.shareddomain.entities.auditable.AuditableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassEntity extends AuditableEntity {

  @Id
  private String id;

  @Column(nullable = false)
  private String name;

  private String section;

  private String grade;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "school_id", nullable = false)
  private School school;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id", nullable = false)
  private Tenant tenant;

  @OneToMany(mappedBy = "classId", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ClassSubject> classSubjects;

  @OneToOne(mappedBy = "classId", cascade = CascadeType.ALL, orphanRemoval = true)
  private Attendance attendance;
}
