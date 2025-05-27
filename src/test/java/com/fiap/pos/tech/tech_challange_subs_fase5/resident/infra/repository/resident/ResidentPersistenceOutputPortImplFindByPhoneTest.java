package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.repository.resident;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({ResidentPersistenceOutputPortImpl.class, ResidentEntityMapperImpl.class})
class ResidentPersistenceOutputPortImplFindByPhoneTest {

  @Autowired
  ResidentPersistenceOutputPortImpl persistenceOutputPort;

  @Test
  void findByPhone_notFound() {
    Optional<Resident> found = persistenceOutputPort.findByPhone("00000000000");
    assertTrue(found.isEmpty());
  }
}