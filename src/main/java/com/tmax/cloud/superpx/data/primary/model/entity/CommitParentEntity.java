package com.tmax.cloud.superpx.data.primary.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "COMMIT_PARENT")
public class CommitParentEntity {

    private Long commitId;
    private Long parentId;
}
