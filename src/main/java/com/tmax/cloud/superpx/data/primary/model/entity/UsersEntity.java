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
@Table(name = "USERS", schema = "SUPERPX")
public class UsersEntity {

    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
}
