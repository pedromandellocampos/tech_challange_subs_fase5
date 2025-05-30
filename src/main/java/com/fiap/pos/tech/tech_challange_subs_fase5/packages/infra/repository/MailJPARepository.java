package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailJPARepository extends JpaRepository<MailEntity, Long> {
  List<MailEntity> findMailEntitiesByUnity(String unity);


}
