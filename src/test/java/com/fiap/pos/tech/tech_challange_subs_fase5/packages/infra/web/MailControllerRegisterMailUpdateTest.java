package com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.input.EmployeeUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto.EmployeeDTORegister;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto.EmployeeLoginDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto.CreateMailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.packages.infra.web.dto.UpdateMailDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.dto.ResidentDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto.ResidentDTORegister;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MailControllerRegisterMailUpdateTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private EmployeeUseCaseInputPort employeeUseCaseInputPort;

  private String employeeToken;
  private String residentToken;

  private Long residentId;
  @Autowired
  private ResidentUseCaseInputPort residentUseCaseInputPort;

  @BeforeEach
  void setUp() {
    try{
      createAndLoginEmployee();
      createAndLoginResident();
    } catch (Exception e) {
      this.employeeToken = null;
      this.residentToken = null;
    }
  }

  public void createAndLoginEmployee() throws  Exception{

    // Criação de um funcionário para testes
    EmployeeDTORegister employee = new EmployeeDTORegister();
    employee.setName("Funcionário Teste");
    employee.setEmail("funcionario@email.com");
    employee.setPassword("senhaSegura123");
    employee.setActive(true);
    employee.setPhone("11999999999");
    employee.setDateOfBirth("20/05/1997");
    employee.setHireDate("20/05/1997");
    try{
      employeeUseCaseInputPort.getEmployeeByEmail("funcionario@email.com");
    } catch (Exception e) {
      mockMvc.perform(post("/api/v1/employees").content(objectMapper.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    String loginJson = objectMapper.writeValueAsString(
      new EmployeeLoginDTO(employee.getEmail(), employee.getPassword())
    );

    String token = mockMvc.perform(post("/api/v1/employees/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginJson))
      .andExpect(status().isOk())
      .andReturn()
      .getResponse()
      .getContentAsString();

    String jwtToken = JsonPath.read(token, "$.token");
    this.employeeToken = jwtToken;
  }

  public void createAndLoginResident() throws  Exception{

    ResidentDTORegister resident = new ResidentDTORegister();
    resident.setName("Morador Teste");
    resident.setEmail("email@email.com");
    resident.setPassword("senhaSegura123");
    resident.setPhone("11999999999");
    resident.setBirthDate("20/05/1997");
    resident.setActive(true);
    resident.setApartment("101");

    try{
      ResidentDTO residentDTO = residentUseCaseInputPort.getResidentByEmail("email@email.com");
      this.residentId = residentDTO.getId();
    } catch (Exception e) {
      mockMvc.perform(post("/api/v1/residents").content(objectMapper.writeValueAsString(resident)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
      ResidentDTO residentDTO = residentUseCaseInputPort.getResidentByEmail("email@email.com");
      this.residentId = residentDTO.getId();
    }

    String loginJson = objectMapper.writeValueAsString(
      new EmployeeLoginDTO(resident.getEmail(), resident.getPassword())
    );

    String token = mockMvc.perform(post("/api/v1/residents/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(loginJson))
      .andExpect(status().isOk())
      .andReturn()
      .getResponse()
      .getContentAsString();

    String jwtToken = JsonPath.read(token, "$.token");
    this.residentToken = jwtToken;
  }


  @Test
  void updateMail_success() throws Exception {
    // Cria uma correspondência
    CreateMailDTO createMailDTO = new CreateMailDTO(
      "Encomenda Amazon",
      "João da Silva",
      "20/05/2020",
      "101"
    );

    String response = mockMvc.perform(post("/api/v1/mail")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createMailDTO))
        .header("Authorization", employeeToken))
      .andExpect(status().isOk())
      .andReturn()
      .getResponse()
      .getContentAsString();

    Integer mailId = JsonPath.read(response, "$.id");

    UpdateMailDTO updateMailDTO = new UpdateMailDTO(
      this.residentId, // residentAcknowledgedById
      "21/05/2024", // acknowledgmentTimestamp (formato dd/MM/yyyy)
      true // receivedByResident
    );

    mockMvc.perform(put("/api/v1/mail/" + mailId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updateMailDTO))
        .header("Authorization", employeeToken))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.unity").value("101"))
      .andExpect(jsonPath("$.residentAcknowledgedById").value(1));
  }



}