package com.tmax.cloud.superpx.data.primary.repository;

import com.tmax.cloud.superpx.data.primary.model.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
