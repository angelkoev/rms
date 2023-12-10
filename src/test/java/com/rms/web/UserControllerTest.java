package com.rms.web;

import com.rms.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    public void showLoginForm_ShouldReturnLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth-login"));
    }

    @Test
    public void onFailure_ShouldReturnLoginPageWithError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/login-error"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth-login"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    public void register_ShouldReturnRegisterPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth-register"));
    }

//    @Test
//    void login() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/login"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("auth-login"))
//                .andExpect(redirectedUrl(null));
//    }
//
//    @Test
//    void register() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/users/register"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("auth-register"))
//                .andExpect(redirectedUrl(null));
//    }

//    @Test
//    void registerNewUser_ValidUser_ShouldRedirectToHome() throws Exception {
//        RegisterDTO registerDTO = createValidRegisterDTO();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
//                        .flashAttr("registerDTO", registerDTO))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/home"));
//    }
//
//    @Test
//    void registerNewUser_InvalidUser_ShouldRedirectToRegisterWithError() throws Exception {
//        RegisterDTO registerDTO = createInvalidRegisterDTO();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
//                        .flashAttr("registerDTO", registerDTO))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/users/register"))
//                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.registerDTO"));
//    }

//    private RegisterDTO createValidRegisterDTO() {
//        RegisterDTO registerDTO = new RegisterDTO();
//        // Set valid user data
//        return registerDTO;
//    }
//
//    private RegisterDTO createInvalidRegisterDTO() {
//        RegisterDTO registerDTO = new RegisterDTO();
//        // Set invalid user data
//        return registerDTO;
//    }
}