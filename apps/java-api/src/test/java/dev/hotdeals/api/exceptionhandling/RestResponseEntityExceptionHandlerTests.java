/*
    Uses ExceptionController to trigger various exceptions and validates the output
 */

package dev.hotdeals.api.exceptionhandling;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ExceptionController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class RestResponseEntityExceptionHandlerTests
{
    @Autowired
    private MockMvc mvc;

    @Test
    void handleBadRequestConstraintViolationException() throws Exception
    {
        String exceptionName = "ConstraintViolationException";
        mvc.perform(post("/exception")
                .param("exceptionName", exceptionName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
                .andExpect(content().string("Bad Request - Constraint Violation"));
    }

    @Test
    void handleBadRequestConstraintViolationExceptionCustom() throws Exception
    {
        String exceptionName = "ConstraintViolationException";
        String customMessage = "Bad Request - Constraint Violation has occurred";
        mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("exceptionName", exceptionName)
                .param("customMessage", customMessage))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException))
                .andExpect(content().string(customMessage));
    }

    @Test
    void handleBadRequestDataIntegrityViolationException() throws Exception
    {
        String exceptionName = "DataIntegrityViolationException";
        mvc.perform(post("/exception")
                .param("exceptionName", exceptionName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegrityViolationException))
                .andExpect(content().string("Bad  Request - Data Integrity Violation"));
    }

    @Test
    void handleBadRequestDataIntegrityViolationExceptionCustom() throws Exception
    {
        String exceptionName = "DataIntegrityViolationException";
        String customMessage = "Bad  Request - Data Integrity Violation has occurred";
        mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("exceptionName", exceptionName)
                .param("customMessage", customMessage))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegrityViolationException))
                .andExpect(content().string(customMessage));
    }

    @Test
    void handleHttpMessageNotReadable() throws Exception
    {
        String exceptionName = "HttpMessageNotReadableException";
        mvc.perform(post("/exception")
                .param("exceptionName", exceptionName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andExpect(content().string("Http Message Not Readable"));
    }
    @Test
    void handleHttpMessageNotReadableCustom() throws Exception
    {
        String exceptionName = "HttpMessageNotReadableException";
        String customMessage = "Http Message Not Readable Exception has occurred";
        mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("exceptionName", exceptionName)
                .param("customMessage", customMessage))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andExpect(content().string(customMessage));
    }

    @Test
    void handleMethodArgumentNotValid() throws Exception
    {
        String exceptionName = "MethodArgumentNotValidException";
        mvc.perform(post("/exception")
                .param("exceptionName", exceptionName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string("Method Argument Not Valid"));
    }

    @Test
    void handleMethodArgumentNotValidCustom() throws Exception
    {
        String exceptionName = "MethodArgumentNotValidException";
        String customMessage = "Method Argument Not Valid Exception has occurred";
        mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("exceptionName", exceptionName)
                .param("customMessage", customMessage))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(content().string(customMessage));
    }
    @Test
    void handleResourceNotFound() throws Exception
    {
        String exceptionName = "ResourceNotFoundException";
        mvc.perform(post("/exception")
                .param("exceptionName", exceptionName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(content().string("Resource Not Found"));
    }

    @Test
    void handleResourceNotFoundCustom() throws Exception
    {
        String exceptionName = "ResourceNotFoundException";
        String customMessage = "Failed to find a resource";
        mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("exceptionName", exceptionName)
                .param("customMessage", customMessage))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(content().string(customMessage));
    }

    @Test
    void handleHttpRequestMethodNotSupported() throws Exception
    {
        String exceptionName = "HttpRequestMethodNotSupportedException";
        mvc.perform(post("/exception")
                .param("exceptionName", exceptionName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andExpect(content().string("Request Method Not Supported"));
    }

    @Test
    void handleHttpRequestMethodNotSupportedCustom() throws Exception
    {
        String exceptionName = "HttpRequestMethodNotSupportedException";
        String customMessage = "Request Method Not Supported Exception has occurred";
        mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("exceptionName", exceptionName)
                .param("customMessage", customMessage))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andExpect(content().string(customMessage));
    }

    @Test
    void handleConflictInvalidDataAccessApiUsageException() throws Exception
    {
        String exceptionName = "InvalidDataAccessApiUsageException";
        mvc.perform(post("/exception")
                .param("exceptionName", exceptionName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidDataAccessApiUsageException))
                .andExpect(content().string("Conflict Error - Invalid Data Access Api Usage"));
    }

    @Test
    void handleConflictInvalidDataAccessApiUsageExceptionCustom() throws Exception
    {
        String exceptionName = "InvalidDataAccessApiUsageException";
        String customMessage = "Conflict Error - Invalid DataAccess Api Usage Exception has occurred";
        mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("exceptionName", exceptionName)
                .param("customMessage", customMessage))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidDataAccessApiUsageException))
                .andExpect(content().string(customMessage));
    }

    @Test
    void handleInternalNullPointerException() throws Exception
    {
        String exceptionName = "NullPointerException";
        mvc.perform(post("/exception")
                .param("exceptionName", exceptionName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NullPointerException))
                .andExpect(content().string("Internal Error - NullPointerException"));
    }

    @Test
    void handleInternalNullPointerExceptionCustom() throws Exception
    {
        String exceptionName = "NullPointerException";
        String customMessage = "Internal Error - Null Pointer Exception has occurred";
        mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("exceptionName", exceptionName)
                .param("customMessage", customMessage))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NullPointerException))
                .andExpect(content().string(customMessage));
    }

    @Test
    void handleInternalIllegalArgumentException() throws Exception
    {
        String exceptionName = "IllegalArgumentException";
        mvc.perform(post("/exception")
                .param("exceptionName", exceptionName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andExpect(content().string("Internal Error - IllegalArgumentException"));
    }

    @Test
    void handleInternalIllegalArgumentExceptionCustom() throws Exception
    {
        String exceptionName = "IllegalArgumentException";
        String customMessage = "Internal Error - Illegal Argument Exception has occurred";
        mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("exceptionName", exceptionName)
                .param("customMessage", customMessage))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andExpect(content().string(customMessage));
    }

    @Test
    void handleInternalIllegalStateException() throws Exception
    {
        String exceptionName = "IllegalStateException";
        mvc.perform(post("/exception")
                .param("exceptionName", exceptionName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalStateException))
                .andExpect(content().string("Internal Error - IllegalStateException"));
    }

    @Test
    void handleInternalIllegalStateExceptionCustom() throws Exception
    {
        String exceptionName = "IllegalStateException";
        String customMessage = "Internal Error - Illegal State Exception has occurred";
        mvc.perform(post("/exception")
                .contentType(MediaType.APPLICATION_JSON)
                .param("exceptionName", exceptionName)
                .param("customMessage", customMessage))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalStateException))
                .andExpect(content().string(customMessage));
    }
}
