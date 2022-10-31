package com.tmax.cloud.superpx.data.primary.model.entity;

import com.tmax.cloud.superpx.data.primary.model.enums.ReferenceType;
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
@Table(name = "REFERENCE", schema = "SUPERPX")
public class ReferenceEntity {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "REFERENCE_SEQ_GENERATOR", sequenceName = "REFERENCE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REFERENCE_SEQ_GENERATOR")
    private Long id;
    @Column(name = "project_id")
    private Long projectId;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private ReferenceType type;
}
