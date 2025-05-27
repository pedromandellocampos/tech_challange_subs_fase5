package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.repository.resident;

import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.model.Resident;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({ResidentPersistenceOutputPortImpl.class, ResidentEntityMapperImpl.class})
class ResidentPersistenceOutputPortImplListAllTest {

  @Autowired
  ResidentPersistenceOutputPortImpl persistenceOutputPort;

  @Test
  void listAll_returnsPagedResidents() {
    for (int i = 1; i <= 5; i++) {
      Resident resident = new Resident();
      resident.setName("Resident " + i);
      resident.setEmail("resident" + i + "@email.com");
      persistenceOutputPort.save(resident);
    }

    List<Resident> residentsPage1 = persistenceOutputPort.listAll(0, 3);
    List<Resident> residentsPage2 = persistenceOutputPort.listAll(1, 3);

    assertEquals(3, residentsPage1.size());
    assertEquals(2, residentsPage2.size());
    assertNotEquals(residentsPage1.get(0).getEmail(), residentsPage2.get(0).getEmail());
  }
}