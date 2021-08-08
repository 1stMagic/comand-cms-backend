package com.biock.cms.web.api;

import com.biock.cms.i18n.Messages;
import com.biock.cms.web.api.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class ResponseBuilder {

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
