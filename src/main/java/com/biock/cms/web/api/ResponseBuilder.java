package com.biock.cms.web.api;

import com.biock.cms.i18n.Messages;
import com.biock.cms.web.api.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@Service
public class ResponseBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseBuilder.class);

    private final Messages messages;

    public ResponseBuilder(final Messages messages) {

        this.messages = messages;
    }

    public <T, D> ResponseEntity<ResponseDTO<D>> build(
            final Supplier<Optional<T>> payloadSupplier,
            final Function<T, D> payloadMapper) {

        return build(
                payloadSupplier,
                payloadMapper,
                this.messages.supplyMessage("general.entity.not_found"));
    }

    public <T, D> ResponseEntity<ResponseDTO<D>> build(
            final Supplier<Optional<T>> payloadSupplier,
            final Function<T, D> payloadMapper,
            final Supplier<String> notFoundMessageSupplier) {

        final ResponseDTO<D> dto = new ResponseDTO<>();
        final Optional<T> payload = payloadSupplier.get();
        return payload.map(statusOk(dto, payloadMapper))
                .orElseGet(statusNotFound(dto, notFoundMessageSupplier.get()));
    }

//    public <T, D> ResponseEntity<ResponseDTO<D>> build(
//            final Supplier<T> payloadSupplier,
//            final Function<T, D> payloadMapper,
//            final Function<Exception, List<String>> exceptionMapper) {
//
//        try {
//            return statusOk(new ResponseDTO<>(), payloadMapper).apply(payloadSupplier.get());
//        } catch (final Exception e) {
//            LOG.error("API error: {}", e.getMessage(), e);
//
//        }
//    }

    public <T> ResponseEntity<ResponseDTO<T>> build(final BindingResult bindingResult) {

        return ResponseEntity.badRequest()
                .body(new ResponseDTO<T>()
                        .setMessages(bindingResult.getAllErrors()
                                .stream()
                                .map(this.messages::getMessage)
                                .collect(toList())));
    }

    private <T, D> Function<T, ResponseEntity<ResponseDTO<D>>> statusOk(
            final ResponseDTO<D> dto,
            final Function<T, D> payloadMapper) {

        return payload -> ResponseEntity.ok(dto.setSuccess(true).setPayload(payloadMapper.apply(payload)));
    }

    private <D> Supplier<ResponseEntity<ResponseDTO<D>>> statusNotFound(
            final ResponseDTO<D> dto,
            final String message) {

        return () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto.addMessage(message));
    }
}
