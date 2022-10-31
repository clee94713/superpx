package com.tmax.cloud.superpx.data.primary.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "COMMIT_PARENT", schema = "SUPERPX")
public class CommitParentEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "COMMIT_PARENT_SEQ_GENERATOR", sequenceName = "COMMIT_PARENT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMIT_PARENT_SEQ_GENERATOR")
    private Long id;
    @Column(name = "commit_id")
    private Long commitId;
    @Column(name = "parent_id")
    private Long parentId;
}
