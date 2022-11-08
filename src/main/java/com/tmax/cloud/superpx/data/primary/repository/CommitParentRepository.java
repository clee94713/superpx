package com.tmax.cloud.superpx.data.primary.repository;

import com.tmax.cloud.superpx.data.primary.model.entity.CommitParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommitParentRepository extends JpaRepository<CommitParentEntity, Long> {
    List<CommitParentEntity> findAllByParentId(Long parentId);
    Optional<CommitParentEntity> findByCommitId(Long commitId);
}
