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
class ResidentPersistenceOutputPortImplFindByEmailTest {

  @Autowired
  ResidentPersistenceOutputPortImpl persistenceOutputPort;

  @Test
  void findByEmail_success() {
    Resident resident = new Resident();
    resident.setName("Ana Souza");
    resident.setEmail("ana@email.com");
    persistenceOutputPort.save(resident);

    Optional<Resident> found = persistenceOutputPort.findByEmail("ana@email.com");
    assertTrue(found.isPresent());
    assertEquals("Ana Souza", found.get().getName());
  }

  @Test
  void findByEmail_notFound() {
    Optional<Resident> found = persistenceOutputPort.findByEmail("naoexiste@email.com");
    assertTrue(found.isEmpty());
  }
}