package com.tmax.cloud.superpx.data.primary.repository;

import com.tmax.cloud.superpx.data.primary.model.entity.ReferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferenceRepository extends JpaRepository<ReferenceEntity, Long> {
}
