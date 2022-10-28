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
@Table(name = "PROJECT", schema = "SUPERPX")
public class ProjectEntity {

    @Id
    @SequenceGenerator(name = "PROJECT_SEQ_GENERATOR", sequenceName = "PROJECT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_SEQ_GENERATOR")
    private Long id;
    private String name;
}
