package com.himalayas.shareddomain.entities;

import com.himalayas.shareddomain.entities.auditable.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "exams")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exam extends AuditableEntity {
  @Id
  private String id;
  @Column(nullable = false)
  private String title;
  private String description;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "class_subject_id", nullable = false)
  private ClassSubject classSubject;
  private Date scheduledAt;
}
