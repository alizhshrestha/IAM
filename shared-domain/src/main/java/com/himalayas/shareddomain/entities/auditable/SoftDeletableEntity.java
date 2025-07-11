package com.himalayas.shareddomain.entities.auditable;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SoftDeletableEntity {

  @Column(name = "deleted", nullable = false)
  protected boolean deleted = false;

  public void markDeleted(){
    this.deleted = true;
  }

  public boolean isDeleted(){
    return deleted;
  }
}
