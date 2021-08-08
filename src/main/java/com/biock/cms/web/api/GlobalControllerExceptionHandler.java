package com.biock.cms.web.api;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ValidationResultDTO> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
//
//        final ValidationResultDTO result = new ValidationResultDTO();
//
//        result.setErrors(e.getBindingResult()
//                .getFieldErrors()
//                .stream().map(this::toValidationErrorDTO)
//                .collect(toList()));
//
//        return ResponseEntity.badRequest().body(result);
//    }
//
//    private ValidationErrorDTO toValidationErrorDTO(final FieldError fieldError) {
//
//        return new ValidationErrorDTO()
//                .setObjectName(fieldError.getObjectName())
//                .setField(fieldError.getField())
//                .setRejectedValue(ObjectUtils.nullSafeToString(fieldError.getRejectedValue()))
//                .setMessageCodes(fieldError.getCodes() == null ? emptyList() : List.of(fieldError.getCodes()))
//                .setDefaultMessage(fieldError.getDefaultMessage());
//    }
}
