package com.tmax.cloud.superpx.data.primary.repository;

import com.tmax.cloud.superpx.data.primary.model.entity.ReferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.lang.ref.Reference;
import java.util.List;
import java.util.Optional;

public interface ReferenceRepository extends JpaRepository<ReferenceEntity, Long> {
    List<ReferenceEntity> findAllByProjectId(Long projectId);
    Optional<ReferenceEntity> findByIdAndProjectId(Long id, Long projectId);
}
