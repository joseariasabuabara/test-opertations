/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
package co.com.appgate.test.session.services;

import co.com.appgate.test.session.model.SessionRequest;
import co.com.appgate.test.session.model.SessionResponse;
import co.com.appgate.test.session.repositories.ISessionRepository;
import io.vavr.control.Try;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements ISessionService {

    private static final Logger LOGGER = LogManager.getLogger(SessionServiceImpl.class);
    private static final String MESSAGE_SUCCESSFUL = "Transaction ok";
    private static final int EXPIRATION_TRANSACTION_MS = 10000;
    private static final String MESSAGE_ERROR = "technical errors, Ups something wrong";
    private final ISessionRepository repository;

    @Autowired
    public SessionServiceImpl(final ISessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<SessionResponse> generateTransaction(final Map<String, String> headers,
        final SessionRequest request) {

        LOGGER.info("generateTransaction Service: headers {} request {}", headers, request);

        if (!validateRequest(request)) {
            return Optional.empty();
        }

        final Optional<SessionResponse> sessionResponse = buildSessionResponse(request);

        saveSession(sessionResponse.get());

        return sessionResponse;
    }

    @Override
    public boolean validSession(final String sessionId, final String customerId) {
        final SessionResponse sessionResponse = repository.findByTransactionId(sessionId);

        return Optional.ofNullable(sessionResponse)
            .filter(session -> session.getCustomerId().equals(customerId))
            .isPresent();

    }

    private boolean validateRequest(final SessionRequest request) {
        return (request != null && request.getCustomerId() != null && !request.getCustomerId()
            .isEmpty());
    }

    private void saveSession(final SessionResponse sessionResponse) {
        Try.of(
            () -> {
                repository.saveSession(sessionResponse);
                return null;
            }).onFailure(throwable -> {
            LOGGER.error(MESSAGE_ERROR + throwable.getMessage());
            throw new RuntimeException(MESSAGE_ERROR);
        });
    }

    private Optional<SessionResponse> buildSessionResponse(final SessionRequest request) {
        return Optional
            .of(SessionResponse.builder().status(MESSAGE_SUCCESSFUL).transactionId(
                UUID.randomUUID().toString()).customerId(request.getCustomerId())
                .expirationTransaction(
                    EXPIRATION_TRANSACTION_MS).date(new Date()).build());
    }
}
