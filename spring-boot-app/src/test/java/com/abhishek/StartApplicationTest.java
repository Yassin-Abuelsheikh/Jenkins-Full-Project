package com.abhishek;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StartApplication.class)
class StartApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnIndexPageWithModelAttributes() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attributeExists("msg"))
                .andExpect(model().attribute("title",
                        "I have successfuly built a sprint boot application using Maven"))
                .andExpect(model().attribute("msg",
                        "This application is deployed on to Kubernetes using Argo CD"));
    }
}
