package dev.hotdeals.api;

import dev.hotdeals.api.supervisor.Supervisor;
import dev.hotdeals.api.supervisor.SupervisorController;
import dev.hotdeals.api.supervisor.SupervisorRepo;
import dev.hotdeals.api.supervisor.SupervisorUml;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = SupervisorController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class SupervisorControllerTests
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private SupervisorRepo supervisorRepo;

    @Autowired
    private SupervisorController supervisorController;

    @Test
    void controllerIsNotNull()
    {
        assertThat(supervisorController).isNotNull();
    }

    //region findAll Tests

    @Test
    @DisplayName("GET /supervisor should return status code 200")
    void findAllStatusCodeTest() throws Exception
    {
        given(supervisorRepo.findAll()).willReturn(new ArrayList<>());

        mvc.perform(get("/supervisor").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(supervisorRepo, VerificationModeFactory.times(1)).findAll();
        reset(supervisorRepo);
    }

    @Test
    @DisplayName("GET /supervisor should return a list of supervisor")
    void findAllTest() throws Exception
    {
        var alex = new Supervisor("Alex Maccnyan", "fake@email.test");
        var cris = new Supervisor("Cris Purcia", "fake@email.test");
        var teo = new Supervisor("Teo Dane", "fake@email.test");

        var allSupervisors = Arrays.asList(alex, cris, teo);

        given(supervisorRepo.findAll()).willReturn(allSupervisors);

        mvc.perform(get("/supervisor").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Alex Maccnyan")))
                .andExpect(jsonPath("$[0].email", is("fake@email.test")))
                .andExpect(jsonPath("$[1].name", is("Cris Purcia")))
                .andExpect(jsonPath("$[1].email", is("fake@email.test")))
                .andExpect(jsonPath("$[2].name", is("Teo Dane")))
                .andExpect(jsonPath("$[2].email", is("fake@email.test")));
        verify(supervisorRepo, VerificationModeFactory.times(1)).findAll();
        reset(supervisorRepo);
    }

    //endregion

    //region Fine One tests

    @Test
    @DisplayName("GET /supervisor/{id} with a valid ID should return status code 200")
    void findOneStatusCodeTest() throws Exception
    {
        var alex = new Supervisor("Alex Maccnyan", "fake@email.test");
        alex.setId(1);

        when(supervisorRepo.findById(alex.getId())).thenReturn(Optional.of(alex));

        mvc.perform(get("/supervisor/" + alex.getId())).andExpect(status().isOk());
        verify(supervisorRepo, VerificationModeFactory.times(1)).findById(Mockito.any());
        reset(supervisorRepo);
    }

    @Test
    @DisplayName("GET /supervisor/{id} with a valid id should return an supervisor")
    void findOneTest() throws Exception
    {
        var alex = new Supervisor("Alex Maccnyan", "fake@email.test");
        alex.setId(1);

        when(supervisorRepo.findById(1L)).thenReturn(Optional.of(alex));

        mvc.perform(get("/supervisor/1").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(jsonPath("$.name", is("Alex Maccnyan")))
                .andExpect(jsonPath("$.email", is("fake@email.test")));
        verify(supervisorRepo, VerificationModeFactory.times(1)).findById(Mockito.any());
        reset(supervisorRepo);
    }

    @Test
    @DisplayName("GET /supervisor/{id} with an invalid ID should return status code 404")
    void findOneInvalidStatusCodeTest() throws Exception
    {
        var alex = new Supervisor("Alex Maccnyan", "fake@email.test");
        alex.setId(1);

        // The id doesn't match on purpose so it fails to find the resource
        when(supervisorRepo.findById(0L)).thenReturn(Optional.of(alex));

        mvc.perform(get("/supervisor/1")).andExpect(status().isNotFound());
        verify(supervisorRepo, VerificationModeFactory.times(1)).findById(Mockito.any());
        reset(supervisorRepo);
    }

    @Test
    @DisplayName("GET /supervisor/{id} with an invalid id should return Resource not Found message")
    void findOneInvalidTest() throws Exception
    {
        var alex = new Supervisor("Alex Maccnyan", "fake@email.test");
        alex.setId(1);

        // The id doesn't match on purpose so it fails to find the resource
        when(supervisorRepo.findById(0L)).thenReturn(Optional.of(alex));

        mvc.perform(get("/supervisor/1").contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(content().string("Failed to find an supervisor with an ID of 1"));
        verify(supervisorRepo, VerificationModeFactory.times(1)).findById(Mockito.any());
        reset(supervisorRepo);
    }

    //endregion

    //region create Tests

    @Test
    @DisplayName("POST /supervisor should return status code 201")
    void createStatusCodeTest() throws Exception
    {
        var newSupervisor = new Supervisor("Alex Maccnyan", "fake@email.test");
        given(supervisorRepo.save(Mockito.any())).willReturn(newSupervisor);

        mvc.perform(post("/supervisor").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(newSupervisor)))
                .andExpect(status().isCreated());
        verify(supervisorRepo, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(supervisorRepo);
    }

    @Test
    @DisplayName("POST /supervisor should return created Supervisor")
    void createTest() throws Exception
    {
        Supervisor alex = new Supervisor("Alex Maccnyan", "fake@email.test");
        given(supervisorRepo.save(Mockito.any())).willReturn(alex);

        mvc.perform(post("/supervisor").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(jsonPath("$.name", is("Alex Maccnyan")))
                .andExpect(jsonPath("$.email", is("fake@email.test")));
        verify(supervisorRepo, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(supervisorRepo);
    }

    //endregion

    //region update Tests

    @Test
    @DisplayName("PUT /supervisor/{id} with a valid ID should return status code 202")
    void updateStatusCodeTest() throws Exception
    {
        var alex = new Supervisor("Alex Maccnyan", "fake@email.test");
        long id = 1;

        when(supervisorRepo.save(Mockito.any())).thenReturn(alex);

        mvc.perform(put("/supervisor/" + id).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(status().isAccepted());
        verify(supervisorRepo, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(supervisorRepo);
    }

    @Test
    @DisplayName("PUT /supervisor/{id} with a valid ID should return an updated supervisor")
    void updateTest() throws Exception
    {
        var oldAlex = new Supervisor("Alex The old", "fake@email.test");
        var alex = new Supervisor("Alex Maccnyan", "fake@email.test");
        long id = 1;
        when(supervisorRepo.save(Mockito.any())).thenReturn(alex);

        when(supervisorRepo.findById(Mockito.any())).thenReturn(Optional.of(oldAlex));


        mvc.perform(put("/supervisor/" + id).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(jsonPath("$.name", is("Alex Maccnyan")))
                .andExpect(jsonPath("$.email", is("fake@email.test")));
        verify(supervisorRepo, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(supervisorRepo);
    }

    @Test
    @DisplayName("PUT /supervisor/{id} with a not-matching ID should return a created supervisor")
    void updateSuccessfulTest() throws Exception
    {
        var supervisor = new Supervisor("Alex Maccnyan", "fake@email.test");
        var updatedSupervisor = new Supervisor("Alex Maccnyan", "fake@email.test");
        updatedSupervisor.setId(1);
        long id = 99;
        when(supervisorRepo.save(Mockito.any())).thenReturn(updatedSupervisor);

        mvc.perform(put("/supervisor/" + id).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(supervisor)))
                .andExpect(jsonPath("$.name", is("Alex Maccnyan")))
                .andExpect(jsonPath("$.email", is("fake@email.test")));
        verify(supervisorRepo, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(supervisorRepo);
    }

    @Test
    @DisplayName("PUT /supervisor should return status code 405")
    void updateStatusCodeInvalidTest() throws Exception
    {
        var alex = new Supervisor("Alex Maccnyan", "fake@email.test");

        // We still mock the repository just in case the method goes through and tries to save
        when(supervisorRepo.save(Mockito.any())).thenReturn(alex);

        mvc.perform(put("/supervisor").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(status().isMethodNotAllowed());
        reset(supervisorRepo);
    }

    //endregion

    //region Delete Tests

    @Test
    @DisplayName("DELETE /supervisor/{id} should return status code 20½")
    void deleteStatusCodeTest() throws Exception
    {
        given(supervisorRepo.existsById(Mockito.any())).willReturn(true);

        mvc.perform(delete("/supervisor/1"))
                .andExpect(status().isAccepted());
        verify(supervisorRepo, VerificationModeFactory.times(1)).existsById(Mockito.any());
        reset(supervisorRepo);
    }

    @Test
    @DisplayName("DELETE /supervisor/{id} with a non-existent ID should return status code 404")
    void deleteInvalidIdStatusCodeTest() throws Exception
    {
        given(supervisorRepo.existsById(Mockito.any())).willReturn(false);

        mvc.perform(delete("/supervisor/99"))
                .andExpect(status().isNotFound());
        verify(supervisorRepo, VerificationModeFactory.times(1)).existsById(Mockito.any());
        reset(supervisorRepo);
    }

    @Test
    @DisplayName("DELETE /supervisor/{id} should return an empty body")
    void deleteTest() throws Exception
    {
        given(supervisorRepo.existsById(Mockito.any())).willReturn(true);

        mvc.perform(delete("/supervisor/1"))
                .andExpect(content().string(""));
        verify(supervisorRepo, VerificationModeFactory.times(1)).existsById(Mockito.any());
        reset(supervisorRepo);
    }

    @Test
    @DisplayName("DELETE /supervisor should return status code 405")
    void updateInvalidStatusCodeTest() throws Exception
    {
        var alex = new Supervisor("Alex Maccnyan", "fake@email.test");

        // We still mock the repository just in case the method goes through and tries to save
        when(supervisorRepo.save(Mockito.any())).thenReturn(alex);

        mvc.perform(delete("/supervisor").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(status().isMethodNotAllowed());
        reset(supervisorRepo);
    }

    //endregion
    //region Uml Tests
    @Test
    @DisplayName("GET /supervisor/uml should return status code 418")
    void umlStatusCodeTest() throws Exception
    {
        mvc.perform(get("/supervisor/uml")).andExpect(status().isIAmATeapot());
        reset(supervisorRepo);
    }

    @Test
    @DisplayName("PUT /student/uml should return uml for the Student Entity")
    void umlTest() throws Exception
    {
        String uml = new SupervisorUml().toString();

        mvc.perform(get("/supervisor/uml")).andExpect(content().string(uml));
        reset(supervisorRepo);
    }
    //endregion
}
