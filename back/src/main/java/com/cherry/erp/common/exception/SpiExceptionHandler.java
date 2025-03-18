package com.cherry.erp.common.exception;

import com.cherry.erp.common.exception.dto.SpiExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.*;

@ControllerAdvice
@Slf4j
public class SpiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SpiException.class})
    protected ResponseEntity<Object> spiExceptionHandler(SpiException ex, WebRequest request) {
        log.error("SPI Exception : ", ex);
        SpiExceptionDto spiExceptionDto = new SpiExceptionDto(ex.getHttpStatus(), ex.getCode(), ex.getCode());
        return handleExceptionInternal(ex, spiExceptionDto, header(), spiExceptionDto.getStatus(), request);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> generalExceptionHandler(Exception ex, WebRequest request) {
        log.error("Exception : ", ex);
        SpiExceptionDto spiExceptionDto = new SpiExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_SERVER_ERROR.getCode(), UNEXPECTED_SERVER_ERROR.getCode());
        return handleExceptionInternal(ex, spiExceptionDto, header(), spiExceptionDto.getStatus(), request);
    }

    @ExceptionHandler({BadCredentialsException.class})
    protected ResponseEntity<Object> badCredentialsExceptionHandler(Exception ex, WebRequest request) {
        SpiExceptionDto spiExceptionDto = new SpiExceptionDto(HttpStatus.UNAUTHORIZED, BAD_CREDENTIALS.getCode(), BAD_CREDENTIALS.getCode());
        return handleExceptionInternal(ex, spiExceptionDto, header(), spiExceptionDto.getStatus(), request);
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<Object> accessDeniedExceptionHandler(Exception ex, WebRequest request) {
        SpiExceptionDto spiExceptionDto = new SpiExceptionDto(HttpStatus.FORBIDDEN, ACCESS_DENIED.getCode(), ACCESS_DENIED.getCode());
        return handleExceptionInternal(ex, spiExceptionDto, header(), spiExceptionDto.getStatus(), request);
    }

    private HttpHeaders header() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        SpiExceptionDto spiExceptionDto =
                new SpiExceptionDto(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, spiExceptionDto, headers, spiExceptionDto.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        SpiExceptionDto spiExceptionDto =
                new SpiExceptionDto(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(
                spiExceptionDto, new HttpHeaders(), spiExceptionDto.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }

        SpiExceptionDto spiExceptionDto =
                new SpiExceptionDto(HttpStatus.BAD_REQUEST, VALIDATION_ERROR.getCode(), errors);
        return new ResponseEntity<>(
                spiExceptionDto, new HttpHeaders(), spiExceptionDto.getStatus());
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<Object> handleTransactionSystemException(TransactionSystemException ex, WebRequest request) {
        SpiExceptionDto spiExceptionDto =
                new SpiExceptionDto(HttpStatus.BAD_REQUEST, TRANSACTION_ERROR.getCode(), TRANSACTION_ERROR.getCode());
        if (ex.getCause().getCause() instanceof ConstraintViolationException) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<?> violation : ((ConstraintViolationException) ex.getCause().getCause()).getConstraintViolations()) {
                errors.add(violation.getMessage());
            }
            spiExceptionDto.setMessage(VALIDATION_ERROR.getCode());
            spiExceptionDto.setErrors(errors);
        }
        return handleExceptionInternal(ex, spiExceptionDto, header(), spiExceptionDto.getStatus(), request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        SpiExceptionDto spiExceptionDto =
                new SpiExceptionDto(HttpStatus.BAD_REQUEST, DATA_INTEGRITY_ERROR.getCode(), DATA_INTEGRITY_ERROR.getCode());
        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            org.hibernate.exception.ConstraintViolationException e = (org.hibernate.exception.ConstraintViolationException) ex.getCause();
            String message = e.getCause().getMessage();
            spiExceptionDto.setMessage(VALIDATION_ERROR.getCode());
            spiExceptionDto.setErrors(Collections.singletonList(message));
        }
        return handleExceptionInternal(ex, spiExceptionDto, header(), spiExceptionDto.getStatus(), request);
    }

}