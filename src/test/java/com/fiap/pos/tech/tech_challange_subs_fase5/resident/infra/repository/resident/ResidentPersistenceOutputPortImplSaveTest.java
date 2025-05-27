package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.repository.resident;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;
import jakarta.validation.UnexpectedTypeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({ResidentPersistenceOutputPortImpl.class, ResidentEntityMapperImpl.class})
class ResidentPersistenceOutputPortImplSaveTest {

  @Autowired
  ResidentPersistenceOutputPortImpl persistenceOutputPort;

  @Test
  void save_success() {
    Resident resident = new Resident();
    resident.setName("Maria Oliveira");
    resident.setEmail("maria@email.com");
    // preencha outros campos obrigatórios

    Resident saved = persistenceOutputPort.save(resident);

    assertNotNull(saved.getId());
    assertEquals("Maria Oliveira", saved.getName());
    assertEquals("maria@email.com", saved.getEmail());
  }

  @Test
  void save_failure_duplicateEmail() {
    Resident resident1 = new Resident();
    resident1.setName("Carlos Silva");
    resident1.setEmail("carlos@email.com");
    // preencha outros campos obrigatórios

    Resident resident2 = new Resident();
    resident2.setName("Carlos Silva 2");
    resident2.setEmail("carlos@email.com");
    // preencha outros campos obrigatórios

    persistenceOutputPort.save(resident1);

    assertThrows(DataIntegrityViolationException.class, () -> {
      persistenceOutputPort.save(resident2);
    });
  }
}