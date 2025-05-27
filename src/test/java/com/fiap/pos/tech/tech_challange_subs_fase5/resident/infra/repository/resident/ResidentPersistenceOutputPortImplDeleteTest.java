package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.repository.resident;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({ResidentPersistenceOutputPortImpl.class, ResidentEntityMapperImpl.class})
class ResidentPersistenceOutputPortImplDeleteTest {

  @Autowired
  ResidentPersistenceOutputPortImpl persistenceOutputPort;

  @Test
  void deleteById_success() {
    Resident resident = new Resident();
    resident.setName("João Teste");
    resident.setEmail("joao.teste@email.com");
    Resident saved = persistenceOutputPort.save(resident);

    boolean deleted = persistenceOutputPort.deleteById(saved.getId());
    assertTrue(deleted);
    assertTrue(persistenceOutputPort.findById(saved.getId()).isEmpty());
  }

  @Test
  void deleteById_notFound() {
    boolean deleted = persistenceOutputPort.deleteById(99999L);
    assertTrue(deleted);
  }
}