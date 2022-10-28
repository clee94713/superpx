package com.tmax.cloud.superpx.data.primary.repository;

import com.tmax.cloud.superpx.data.primary.model.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
}
