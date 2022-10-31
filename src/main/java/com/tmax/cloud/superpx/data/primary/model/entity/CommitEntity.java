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
@Table(name = "COMMIT", schema = "SUPERPX")
public class CommitEntity {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "COMMIT_SEQ_GENERATOR", sequenceName = "COMMIT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMIT_SEQ_GENERATOR")
    private Long id;
    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "message")
    private String message;
    @Column(name = "author_id")
    private Long authorId;
}
