package com.himalayas.shareddomain.entities;

import com.himalayas.shareddomain.entities.auditable.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resources extends AuditableEntity {
  @Id
  private String id;
  @Column(nullable = false)
  private String title;
  private String fileUrl;
  private String type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "class_subject_id")
  private ClassSubject classSubject;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "uploaded_by")
  private User uploadedBy;
}
