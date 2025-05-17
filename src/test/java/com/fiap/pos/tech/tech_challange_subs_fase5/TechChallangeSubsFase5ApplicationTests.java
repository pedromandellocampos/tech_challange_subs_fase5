package com.fiap.pos.tech.tech_challange_subs_fase5;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class TechChallangeSubsFase5ApplicationTests {


  @Test
  void contextLoads() {
    var modules = ApplicationModules.of(TechChallangeSubsFase5Application.class);
    System.out.println(modules);
  }

}
