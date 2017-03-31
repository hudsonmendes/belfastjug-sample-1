package com.hudsonmendes.microservice1;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

public class VisitorControllerTest {
    private List<VisitorEntity> database;
    private VisitorController rest;
    private MockMvc endpoint;
    private VisitorRepository repo;

    @Before
    public void setup() {
        database = new ArrayList<>();
        repo = mock(VisitorRepository.class);
        rest = new VisitorController(repo);
        endpoint = standaloneSetup(rest).build();
    }

    @Test
    public void create_ok() throws Exception {
        doAnswer(i -> {
            final VisitorEntity entity = i.getArgumentAt(0, VisitorEntity.class);
            database.add(entity);
            return entity;
        }).when(repo).save(any(VisitorEntity.class));

        doAnswer(i -> database).when(repo).findAll();

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
