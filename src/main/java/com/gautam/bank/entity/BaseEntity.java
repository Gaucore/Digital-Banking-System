package com.gautam.bank.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    // Step 1 - Created Date
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Step 2 - Updated Date
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Step 3 - Set Created & Updated Date Before Insert
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Step 4 - Update Modified Date Before Update
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}