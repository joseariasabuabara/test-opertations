package co.com.appgate.test.session.services;

import co.com.appgate.test.session.model.SessionRequest;
import co.com.appgate.test.session.model.SessionResponse;
import java.util.Map;
import java.util.Optional;

public interface ISessionService {

    Optional<SessionResponse> generateTransaction(Map<String, String> headers,
        SessionRequest request);

    boolean validSession(String sessionId, String customerId);
}
