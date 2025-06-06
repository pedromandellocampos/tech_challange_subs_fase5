package com.fiap.pos.tech.tech_challange_subs_fase5.notification.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationEntityJPARepository extends JpaRepository<NotificationEntity, Long> {
}
