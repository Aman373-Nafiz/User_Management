package com.example.demo.controller;

import com.example.demo.model.ChildModel;
import com.example.demo.model.ParentModel;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateParent() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        ParentModel parent = new ParentModel();
        parent.setFirstName("John");
        parent.setLastName("Doe");

        when(userService.createParent(any(ParentModel.class))).thenReturn(parent);

        mockMvc.perform(post("/users/parent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));

        verify(userService, times(1)).createParent(any(ParentModel.class));
    }

    @Test
    void testCreateChild() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        ChildModel child = new ChildModel();
        child.setFirstName("Alice");

        when(userService.createChild(eq(1L), any(ChildModel.class))).thenReturn(child);

        mockMvc.perform(post("/users/child/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(child)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"));

        verify(userService, times(1)).createChild(eq(1L), any(ChildModel.class));
    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        ParentModel parent = new ParentModel();
        parent.setFirstName("John");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(parent));

        mockMvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));

        verify(userService, times(1)).getAllUsers();
    }
}
