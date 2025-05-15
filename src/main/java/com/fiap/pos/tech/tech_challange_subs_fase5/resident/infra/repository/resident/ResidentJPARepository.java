package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.repository.resident;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentJPARepository extends JpaRepository<ResidentEntity, Long> {

  @Override
  Optional<ResidentEntity> findById(Long aLong);
  Optional<ResidentEntity> findByEmail(String email);

}
