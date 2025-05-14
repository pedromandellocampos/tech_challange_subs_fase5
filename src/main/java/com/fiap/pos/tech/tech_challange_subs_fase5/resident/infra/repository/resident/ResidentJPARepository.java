package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.repository.resident;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResidentJPARepository extends JpaRepository<ResidentEntity, Long> {

  @Override
  Optional<ResidentEntity> findById(Long aLong);
  Optional<ResidentEntity> findByEmail(String email);

}
