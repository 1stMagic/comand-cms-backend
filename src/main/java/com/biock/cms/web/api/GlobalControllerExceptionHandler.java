package com.biock.cms.web.api;

import com.biock.cms.shared.ValidationErrorDTO;
import com.biock.cms.shared.ValidationResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResultDTO> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {

        final ValidationResultDTO result = new ValidationResultDTO();

        result.setErrors(e.getBindingResult()
                .getFieldErrors()
                .stream().map(this::toValidationErrorDTO)
                .collect(toList()));

        return ResponseEntity.badRequest().body(result);
    }

    private ValidationErrorDTO toValidationErrorDTO(final FieldError fieldError) {

        return new ValidationErrorDTO()
                .setObjectName(fieldError.getObjectName())
                .setField(fieldError.getField())
                .setRejectedValue(ObjectUtils.nullSafeToString(fieldError.getRejectedValue()))
                .setMessageCodes(fieldError.getCodes() == null ? emptyList() : List.of(fieldError.getCodes()))
                .setDefaultMessage(fieldError.getDefaultMessage());
    }
}
