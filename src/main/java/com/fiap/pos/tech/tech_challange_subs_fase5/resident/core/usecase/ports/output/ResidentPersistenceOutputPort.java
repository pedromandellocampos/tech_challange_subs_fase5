package com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.output;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;

import java.util.List;
import java.util.Optional;

public interface ResidentPersistenceOutputPort {

  Resident save(Resident Resident);
  boolean delete(Resident Resident);
  boolean deleteById(Long id);
  List<Resident>  listAll(int page, int size);
  Optional<Resident> findById(Long id);
  Optional<Resident> findByEmail(String email);
  Optional<Resident> findByPhone(String phone);
  List<Resident> findByUnity(String unity);

}
