package com.tmax.cloud.superpx.data.primary.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CodePointLength;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "COMMIT_IN_REFERENCE", schema = "SUPERPX")
public class CommitInReferenceEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "COMMIT_IN_REFERENCE_SEQ_GENERATOR", sequenceName = "COMMIT_IN_REFERENCE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMIT_IN_REFERENCE_SEQ_GENERATOR")
    private Long id;
    @Column(name = "reference_id")
    private Long referenceId;
    @Column(name = "commit_id")
    private Long commitId;
}
