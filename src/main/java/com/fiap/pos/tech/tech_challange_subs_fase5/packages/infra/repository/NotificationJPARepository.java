package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationJPARepository extends JpaRepository<NotificationEntity, Long> {

}
