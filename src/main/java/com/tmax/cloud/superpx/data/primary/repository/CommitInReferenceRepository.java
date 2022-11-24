package com.tmax.cloud.superpx.data.primary.repository;

import com.tmax.cloud.superpx.data.primary.model.entity.CommitInReferenceEntity;
import com.tmax.cloud.superpx.data.primary.model.entity.ReferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommitInReferenceRepository extends JpaRepository<CommitInReferenceEntity, Long> {
    List<CommitInReferenceEntity> findAllByReferenceId(Long id);
    List<CommitInReferenceEntity> findAllByCommitId(Long commitId);
    Optional<CommitInReferenceEntity> findByCommitId(Long commitId);
}
