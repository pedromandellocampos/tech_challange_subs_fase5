package com.fiap.pos.tech.tech_challange_subs_fase5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.Modulithic;

@SpringBootApplication
@Modulithic
public class TechChallangeSubsFase5Application {

  public static void main(String[] args) {
    SpringApplication.run(TechChallangeSubsFase5Application.class, args);

}

}
