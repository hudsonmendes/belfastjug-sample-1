package com.hudsonmendes.microservice1;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

public class AllInOneControllerTest {
    private AllInOneController rest;
    private MockMvc endpoint;

    @Before
    public void setup() {
        rest = new AllInOneController();
        endpoint = standaloneSetup(rest).build();
    }

    @Test
    public void create_ok() throws Exception {
        endpoint
            .perform(post("/visitors").contentType(APPLICATION_JSON).content("{\"name\": \"Hudson Mendes\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(hasSize(1)))
            .andExpect(jsonPath("$[0]").value("Hudson Mendes"));

        endpoint
            .perform(post("/visitors").contentType(APPLICATION_JSON).content("{\"name\": \"Bruna Almeida\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(hasSize(2)))
            .andExpect(jsonPath("$[0]").value("Hudson Mendes"))
            .andExpect(jsonPath("$[1]").value("Bruna Almeida"));
    }

    @Test
    public void create_badRequest() throws Exception {
        endpoint
            .perform(post("/visitors").contentType(APPLICATION_JSON).content("{\"typoName\": \"Hudson Mendes\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("'name' is required."));
    }
}
