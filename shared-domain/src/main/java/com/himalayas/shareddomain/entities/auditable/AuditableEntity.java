package com.himalayas.shareddomain.entities.auditable;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
public abstract class AuditableEntity extends SoftDeletableEntity{

  @Column(name = "created_at", nullable = false, updatable = false)
  protected Instant createdAt;

  @Column(name = "updated_at")
  protected Instant updatedAt;

  @Column(name = "created_by", updatable = false)
  protected String createdBy;

  @Column(name = "updated_by")
  protected String updatedBy;

  @PrePersist
  protected void onCreate() {
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
    // createdBy = fetch from context if available
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = Instant.now();
    // updatedBy = fetch from context if available
  }


}
