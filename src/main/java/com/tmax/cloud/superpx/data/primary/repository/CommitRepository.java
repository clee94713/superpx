package com.tmax.cloud.superpx.data.primary.repository;

import com.tmax.cloud.superpx.data.primary.model.entity.CommitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitRepository extends JpaRepository<CommitEntity, Long> {
}
