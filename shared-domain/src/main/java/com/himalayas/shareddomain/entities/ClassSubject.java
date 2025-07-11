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

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "class_subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassSubject extends AuditableEntity {
  @Id
  private String id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "class_id", nullable = false)
  private ClassEntity classId;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subject_id", nullable = false)
  private SubjectEntity subjectId;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_id", nullable = false)
  private User teacherId;

  @OneToMany(mappedBy = "classSubject", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Assignments> assignments = new HashSet<>();

  @OneToOne(mappedBy = "classSubject", cascade = CascadeType.ALL, orphanRemoval = true)
  private Exam exam;

  @OneToMany(mappedBy = "classSubject", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Resources> classSubject = new HashSet<>();
}
