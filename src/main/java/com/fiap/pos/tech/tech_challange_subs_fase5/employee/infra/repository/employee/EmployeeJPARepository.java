package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeJPARepository extends JpaRepository<EmployeeJPAEntity, Long> {
  Optional<EmployeeJPAEntity> findByEmail(String email);

  Optional<EmployeeJPAEntity> findByPhone(String phone);

  Optional<EmployeeJPAEntity> findById(Long id);


}
