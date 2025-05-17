package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.repository.residentialunity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentialUnityJPARepository extends JpaRepository<ResidentialUnityEntity, Long> {
}
