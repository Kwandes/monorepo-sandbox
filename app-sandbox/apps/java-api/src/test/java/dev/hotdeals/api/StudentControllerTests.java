package dev.hotdeals.api;

import dev.hotdeals.api.student.Student;
import dev.hotdeals.api.student.StudentController;
import dev.hotdeals.api.student.StudentRepo;
import dev.hotdeals.api.student.StudentUml;
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
@WebMvcTest(value = StudentController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class StudentControllerTests
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentRepo studentRepo;

    @Autowired
    private StudentController studentController;

    @Test
    void controllerIsNotNull()
    {
        assertThat(studentController).isNotNull();
    }

    //region findAll Tests

    @Test
    @DisplayName("GET /student should return status code 200")
    void findAllStatusCodeTest() throws Exception
    {
        given(studentRepo.findAll()).willReturn(new ArrayList<>());

        mvc.perform(get("/student").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(studentRepo, VerificationModeFactory.times(1)).findAll();
        reset(studentRepo);
    }

    @Test
    @DisplayName("GET /student should return a list of student")
    void findAllTest() throws Exception
    {
        var alex = new Student("Alex Maccnyan", "fake@email.test");
        var cris = new Student("Cris Purcia", "fake@email.test");
        var teo = new Student("Teo Dane", "fake@email.test");

        var allStudents = Arrays.asList(alex, cris, teo);

        given(studentRepo.findAll()).willReturn(allStudents);

        mvc.perform(get("/student").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Alex Maccnyan")))
                .andExpect(jsonPath("$[0].email", is("fake@email.test")))
                .andExpect(jsonPath("$[1].name", is("Cris Purcia")))
                .andExpect(jsonPath("$[1].email", is("fake@email.test")))
                .andExpect(jsonPath("$[2].name", is("Teo Dane")))
                .andExpect(jsonPath("$[2].email", is("fake@email.test")));
        verify(studentRepo, VerificationModeFactory.times(1)).findAll();
        reset(studentRepo);
    }

    //endregion

    //region Fine One tests

    @Test
    @DisplayName("GET /student/{id} with a valid ID should return status code 200")
    void findOneStatusCodeTest() throws Exception
    {
        var alex = new Student("Alex Maccnyan", "fake@email.test");
        alex.setId(1);

        when(studentRepo.findById(alex.getId())).thenReturn(Optional.of(alex));

        mvc.perform(get("/student/" + alex.getId())).andExpect(status().isOk());
        verify(studentRepo, VerificationModeFactory.times(1)).findById(Mockito.any());
        reset(studentRepo);
    }

    @Test
    @DisplayName("GET /student/{id} with a valid id should return an student")
    void findOneTest() throws Exception
    {
        var alex = new Student("Alex Maccnyan", "fake@email.test");
        alex.setId(1);

        when(studentRepo.findById(1L)).thenReturn(Optional.of(alex));

        mvc.perform(get("/student/1").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(jsonPath("$.name", is("Alex Maccnyan")))
                .andExpect(jsonPath("$.email", is("fake@email.test")));
        verify(studentRepo, VerificationModeFactory.times(1)).findById(Mockito.any());
        reset(studentRepo);
    }

    @Test
    @DisplayName("GET /student/{id} with an invalid ID should return status code 404")
    void findOneInvalidStatusCodeTest() throws Exception
    {
        var alex = new Student("Alex Maccnyan", "fake@email.test");
        alex.setId(1);

        // The id doesn't match on purpose so it fails to find the resource
        when(studentRepo.findById(0L)).thenReturn(Optional.of(alex));

        mvc.perform(get("/student/1")).andExpect(status().isNotFound());
        verify(studentRepo, VerificationModeFactory.times(1)).findById(Mockito.any());
        reset(studentRepo);
    }

    @Test
    @DisplayName("GET /student/{id} with an invalid id should return Resource not Found message")
    void findOneInvalidTest() throws Exception
    {
        var alex = new Student("Alex Maccnyan", "fake@email.test");
        alex.setId(1);

        // The id doesn't match on purpose so it fails to find the resource
        when(studentRepo.findById(0L)).thenReturn(Optional.of(alex));

        mvc.perform(get("/student/1").contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(content().string("Failed to find an student with an ID of 1"));
        verify(studentRepo, VerificationModeFactory.times(1)).findById(Mockito.any());
        reset(studentRepo);
    }

    //endregion

    //region create Tests

    @Test
    @DisplayName("POST /student should return status code 201")
    void createStatusCodeTest() throws Exception
    {
        var newStudent = new Student("Alex Maccnyan", "fake@email.test");
        given(studentRepo.save(Mockito.any())).willReturn(newStudent);

        mvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(newStudent)))
                .andExpect(status().isCreated());
        verify(studentRepo, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(studentRepo);
    }

    @Test
    @DisplayName("POST /student should return created Student")
    void createTest() throws Exception
    {
        Student alex = new Student("Alex Maccnyan", "fake@email.test");
        given(studentRepo.save(Mockito.any())).willReturn(alex);

        mvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(jsonPath("$.name", is("Alex Maccnyan")))
                .andExpect(jsonPath("$.email", is("fake@email.test")));
        verify(studentRepo, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(studentRepo);
    }

    //endregion

    //region update Tests

    @Test
    @DisplayName("PUT /student/{id} with a valid ID should return status code 202")
    void updateStatusCodeTest() throws Exception
    {
        var alex = new Student("Alex Maccnyan", "fake@email.test");
        long id = 1;

        when(studentRepo.save(Mockito.any())).thenReturn(alex);

        mvc.perform(put("/student/" + id).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(status().isAccepted());
        verify(studentRepo, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(studentRepo);
    }

    @Test
    @DisplayName("PUT /student/{id} with a valid ID should return an updated student")
    void updateTest() throws Exception
    {
        var oldAlex = new Student("Alex The old", "fake@email.test");
        var alex = new Student("Alex Maccnyan", "fake@email.test");
        long id = 1;
        when(studentRepo.save(Mockito.any())).thenReturn(alex);

        when(studentRepo.findById(Mockito.any())).thenReturn(Optional.of(oldAlex));


        mvc.perform(put("/student/" + id).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(jsonPath("$.name", is("Alex Maccnyan")))
                .andExpect(jsonPath("$.email", is("fake@email.test")));
        verify(studentRepo, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(studentRepo);
    }

    @Test
    @DisplayName("PUT /student/{id} with a not-matching ID should return a created student")
    void updateSuccessfulTest() throws Exception
    {
        var student = new Student("Alex Maccnyan", "fake@email.test");
        var updatedStudent = new Student("Alex Maccnyan", "fake@email.test");
        updatedStudent.setId(1);
        long id = 99;
        when(studentRepo.save(Mockito.any())).thenReturn(updatedStudent);

        mvc.perform(put("/student/" + id).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(student)))
                .andExpect(jsonPath("$.name", is("Alex Maccnyan")))
                .andExpect(jsonPath("$.email", is("fake@email.test")));
        verify(studentRepo, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(studentRepo);
    }

    @Test
    @DisplayName("PUT /student should return status code 405")
    void updateStatusCodeInvalidTest() throws Exception
    {
        var alex = new Student("Alex Maccnyan", "fake@email.test");

        // We still mock the repository just in case the method goes through and tries to save
        when(studentRepo.save(Mockito.any())).thenReturn(alex);

        mvc.perform(put("/student").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(status().isMethodNotAllowed());
        reset(studentRepo);
    }

    //endregion

    //region Delete Tests

    @Test
    @DisplayName("DELETE /student/{id} should return status code 20½")
    void deleteStatusCodeTest() throws Exception
    {
        given(studentRepo.existsById(Mockito.any())).willReturn(true);

        mvc.perform(delete("/student/1"))
                .andExpect(status().isAccepted());
        verify(studentRepo, VerificationModeFactory.times(1)).existsById(Mockito.any());
        reset(studentRepo);
    }

    @Test
    @DisplayName("DELETE /student/{id} with a non-existent ID should return status code 404")
    void deleteInvalidIdStatusCodeTest() throws Exception
    {
        given(studentRepo.existsById(Mockito.any())).willReturn(false);

        mvc.perform(delete("/student/99"))
                .andExpect(status().isNotFound());
        verify(studentRepo, VerificationModeFactory.times(1)).existsById(Mockito.any());
        reset(studentRepo);
    }

    @Test
    @DisplayName("DELETE /student/{id} should return an empty body")
    void deleteTest() throws Exception
    {
        given(studentRepo.existsById(Mockito.any())).willReturn(true);

        mvc.perform(delete("/student/1"))
                .andExpect(content().string(""));
        verify(studentRepo, VerificationModeFactory.times(1)).existsById(Mockito.any());
        reset(studentRepo);
    }

    @Test
    @DisplayName("DELETE /student should return status code 405")
    void updateInvalidStatusCodeTest() throws Exception
    {
        var alex = new Student("Alex Maccnyan", "fake@email.test");

        // We still mock the repository just in case the method goes through and tries to save
        when(studentRepo.save(Mockito.any())).thenReturn(alex);

        mvc.perform(delete("/student").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(alex)))
                .andExpect(status().isMethodNotAllowed());
        reset(studentRepo);
    }

    //endregion
    //region Uml Tests
    @Test
    @DisplayName("GET /student/uml should return status code 418")
    void umlStatusCodeTest() throws Exception
    {
        mvc.perform(get("/student/uml")).andExpect(status().isIAmATeapot());
        reset(studentRepo);
    }

    @Test
    @DisplayName("PUT /student/uml should return uml for the Student Entity")
    void umlTest() throws Exception
    {
        String uml = new StudentUml().toString();

        mvc.perform(get("/student/uml")).andExpect(content().string(uml));
        reset(studentRepo);
    }
    //endregion
}
