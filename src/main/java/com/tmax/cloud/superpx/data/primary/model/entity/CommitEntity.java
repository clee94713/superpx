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
@Table(name = "COMMIT")
public class CommitEntity {

    @Id
    @SequenceGenerator(name = "COMMIT_SEQ_GENERATOR", sequenceName = "COMMIT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMIT_SEQ_GENERATOR")
    private Long id;
    private Long projectId;
    private String message;
    private Long authorId;
}
