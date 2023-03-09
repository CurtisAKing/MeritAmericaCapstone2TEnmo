package com.techelevator.tenmo.tenmo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.tenmo.tenmo.controller.AccountController;
import com.techelevator.tenmo.tenmo.services.AccountService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

	@Autowired
	private MockMvcPrint mockMvc;
	@MockBean
	private AccountService accountService;

	@Autowired
	private ObjectMapper objectMapper;


}
