package com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.input.EmployeeUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto.EmployeeDTORegister;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto.EmployeeLoginDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto.ResidentDTORegister;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto.ResidentUpdateDTO;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class ResidentControllerUpdateTest {


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private EmployeeUseCaseInputPort employeeUseCaseInputPort;

  private String employeeToken;
  private String residentToken;
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
      mockMvc.perform(post("/api/v1/employees").content(objectMapper.writeValueAsString(employee)).contentType(
        MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
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
      residentUseCaseInputPort.getResidentByEmail("email@email.com");
    } catch (Exception e) {
      mockMvc.perform(post("/api/v1/residents").content(objectMapper.writeValueAsString(resident)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
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


    ResidentDTORegister resident2 = new ResidentDTORegister();
    resident2.setName("Morador Teste2");
    resident2.setEmail("email2@email.com");
    resident2.setPassword("senhaSegura123");
    resident2.setPhone("11999999992");
    resident2.setBirthDate("20/05/1997");
    resident2.setActive(true);
    resident2.setApartment("102");

    try{
      residentUseCaseInputPort.getResidentByEmail("email2@email.com");
    } catch (Exception e) {
      mockMvc.perform(post("/api/v1/residents").content(objectMapper.writeValueAsString(resident2)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }
  }

  @Test
  void deveAtualizarResidenteComSucesso() throws Exception {
    ResidentUpdateDTO dto = new ResidentUpdateDTO();
    dto.setName("Morador Atualizado");
    dto.setPhone("11988888888");
    dto.setApartment("101");
    dto.setBirthDate("20/05/1998");
    dto.setEmail("email@email.com");
    dto.setActive(true);

    Long residentId = residentUseCaseInputPort.getResidentByEmail("email@email.com").getId();

    mockMvc.perform(put("/api/v1/residents/{id}", residentId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto))
        .header("Authorization", residentToken))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(residentId));
  }

  @Test
  void deveRetornarUnauthorizedAoAtualizarOutroResidente() throws Exception {

    ResidentUpdateDTO dto = new ResidentUpdateDTO();
    dto.setName("Outro Morador");
    dto.setPhone("11988888888");
    dto.setApartment("101");
    dto.setBirthDate("20/05/1998");
    dto.setEmail("email3@email.com");
    dto.setActive(true);

    // Tenta atualizar o residente com o token do funcionário (não autorizado)
    Long residentId = residentUseCaseInputPort.getResidentByEmail("email2@email.com").getId();

    mockMvc.perform(put("/api/v1/residents/{id}", residentId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto))
        .header("Authorization", residentToken))
      .andExpect(status().isUnauthorized());
  }

  @Test
  void deveRetornarBadRequestParaDadosInvalidos() throws Exception {
    ResidentUpdateDTO dto = new ResidentUpdateDTO();
    // Não preenche campos obrigatórios

    Long residentId = residentUseCaseInputPort.getResidentByEmail("email@email.com").getId();

    mockMvc.perform(put("/api/v1/residents/{id}", residentId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto))
        .header("Authorization", residentToken))
      .andExpect(status().isBadRequest());
  }
}
