package dev.hotdeals.api.exceptionhandling;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
    public RestResponseEntityExceptionHandler()
    {
        super();
    }

    // API

    //region 400

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request)
    {
        final String bodyOfResponse = ex.getMessage() == null ? "Bad Request - Constraint Violation" : ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request)
    {
        final String bodyOfResponse = ex.getMessage() == null ? "Bad  Request - Data Integrity Violation" : ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request)
    {
        final String bodyOfResponse = ex.getMessage() == null ? "Http Message Not Readable" : ex.getMessage();
        // ex.getCause() instanceof JsonMappingException, JsonParseException // for additional information later on
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request)
    {
        final String bodyOfResponse = (ex.getBindingResult().getTarget() != null) ? ex.getBindingResult().getTarget().toString() : "Method Argument Not Valid";
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
    }


    // 404

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFound(final ResourceNotFoundException ex, final WebRequest request)
    {
        final String bodyOfResponse = ex.getMessage() == null ? "Resource Not Found" : ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 405

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
                                                                         final HttpHeaders headers, final HttpStatus status,
                                                                         final WebRequest request)
    {
        final String bodyOfResponse = ex.getMessage() == null ? "Request Method Not Supported" : ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.METHOD_NOT_ALLOWED, request);
    }

    // 409

    @ExceptionHandler({InvalidDataAccessApiUsageException.class, DataAccessException.class})
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request)
    {
        final String bodyOfResponse = ex.getMessage() == null ? "Conflict Error - " + ex.getClass().getSimpleName() : ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    //endregion

    // 500

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class})
    /*500*/ public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request)
    {
        logger.error("500 Status Code", ex);
        final String bodyOfResponse = ex.getMessage() == null ? "Internal Error - " + ex.getClass().getSimpleName() : ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
