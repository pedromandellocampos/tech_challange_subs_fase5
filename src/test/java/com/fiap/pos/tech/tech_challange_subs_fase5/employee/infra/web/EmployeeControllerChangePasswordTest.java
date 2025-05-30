package com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.Dto.EmployeeDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.core.usecases.ports.input.EmployeeUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto.ChangePasswordDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto.EmployeeDTORegister;
import com.fiap.pos.tech.tech_challange_subs_fase5.employee.infra.web.dto.EmployeeLoginDTO;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.core.usecase.ports.input.ResidentUseCaseInputPort;
import com.fiap.pos.tech.tech_challange_subs_fase5.resident.infra.web.dto.ResidentDTORegister;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerChangePasswordTest {


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
  private Long loggedEmployeeId;

  @BeforeEach
  void setUp() {
    try{
      createAndLoginEmployee();
      createAndLoginResident();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println("AQUI 123 321");
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
      EmployeeDTO employeeDTO = employeeUseCaseInputPort.getEmployeeByEmail("funcionario@email.com");
      System.out.println("AQUI EMPLOYEE -> " + employeeDTO);
      this.loggedEmployeeId = employeeDTO.getId();
    } catch (Exception e) {
       mockMvc.perform(post("/api/v1/employees").content(objectMapper.writeValueAsString(employee)).contentType(
        MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
      // Buscar novamente após criar
      EmployeeDTO employeeDTO = employeeUseCaseInputPort.getEmployeeByEmail("funcionario@email.com");
      this.loggedEmployeeId = employeeDTO.getId();

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

    ResidentDTORegister resident2 = new ResidentDTORegister();
    resident2.setName("Morador Teste");
    resident2.setEmail("email2@email.com");
    resident2.setPassword("senhaSegura123");
    resident2.setPhone("11999999999");
    resident2.setBirthDate("20/05/1997");
    resident2.setActive(true);
    resident2.setApartment("101");

    try{
      residentUseCaseInputPort.getResidentByEmail("email2@email.com");
    } catch (Exception e) {
      mockMvc.perform(post("/api/v1/residents").content(objectMapper.writeValueAsString(resident2)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }



  }

  @Test
  @Order(2)
  void deveAlterarSenhaComSucesso() throws Exception {

    ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("senhaSegura123");
    String changePasswordJson = objectMapper.writeValueAsString(changePasswordDTO);

    mockMvc.perform(put("/api/v1/employees/change-password/" + this.loggedEmployeeId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(changePasswordJson)
        .header("Authorization", employeeToken))
      .andExpect(status().isOk());
  }

  @Test
  @Order(1)
  void deveRetornarBadRequestComCamposInvalidos() throws Exception {

    ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("123");
    String changePasswordJson = objectMapper.writeValueAsString(changePasswordDTO);

    mockMvc.perform(put("/api/v1/employees/change-password/" + this.loggedEmployeeId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(changePasswordJson)
        .header("Authorization", employeeToken))
      .andExpect(status().isBadRequest());
  }



}