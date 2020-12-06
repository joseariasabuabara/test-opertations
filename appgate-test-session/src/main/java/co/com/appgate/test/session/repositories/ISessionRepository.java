package co.com.appgate.test.session.repositories;

import co.com.appgate.test.session.model.SessionResponse;

public interface ISessionRepository {

    void saveSession(final SessionResponse session);

    SessionResponse findByTransactionId(final String transactionId);
}
