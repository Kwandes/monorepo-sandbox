/*
    A helper class used by the exception handler testing for the purposes of mocking
    Throws various exceptions based of the input
 */

package dev.hotdeals.api.exceptionhandling;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Objects;

@RestController
public class ExceptionController
{
    @PostMapping("/exception")
    public void getSpecificException(@RequestParam(name = "exceptionName") String exceptionName,
                                     @RequestParam(required = false, name = "customMessage") String customMessage) throws Exception
    {
        switch (exceptionName)
        {
            case "ResourceNotFoundException":
                if (customMessage != null)
                    throw new ResourceNotFoundException(customMessage);
                else
                    throw new ResourceNotFoundException();
            case "ConstraintViolationException":
                if (customMessage == null) customMessage = "Bad Request - Constraint Violation";
                throw new ConstraintViolationException(customMessage, new SQLException(), customMessage);
            case "DataIntegrityViolationException":
                // This syntax is equivalent of if-else found in ResourceNotFoundException case
                throw new DataIntegrityViolationException(Objects.requireNonNullElse(customMessage,
                        "Bad  Request - Data Integrity Violation"));
            case "HttpMessageNotReadableException":
                throw new HttpMessageNotReadableException(Objects.requireNonNullElse(customMessage,
                        "Http Message Not Readable"));
            case "MethodArgumentNotValidException":
                String message = customMessage == null ? "Method Argument Not Valid" : customMessage;
                throw new MethodArgumentNotValidException(
                        new MethodParameter(this.getClass().getDeclaredMethod("getSpecificException", String.class, String.class), 0),
                        new BeanPropertyBindingResult(message, "message"));
            case "HttpRequestMethodNotSupportedException":
                throw new HttpRequestMethodNotSupportedException("", Objects.requireNonNullElse(customMessage,
                        "Request Method Not Supported"));
            case "InvalidDataAccessApiUsageException":
                throw new InvalidDataAccessApiUsageException(Objects.requireNonNullElse(customMessage,
                        "Conflict Error - Invalid Data Access Api Usage"));
            case "NullPointerException":
                throw new NullPointerException(Objects.requireNonNullElse(customMessage,
                        "Internal Error - NullPointerException"));
            case "IllegalArgumentException":
                throw new IllegalArgumentException(Objects.requireNonNullElse(customMessage,
                        "Internal Error - IllegalArgumentException"));
            case "IllegalStateException":
                throw new IllegalStateException(Objects.requireNonNullElse(customMessage,
                        "Internal Error - IllegalStateException"));
        }
    }
}
