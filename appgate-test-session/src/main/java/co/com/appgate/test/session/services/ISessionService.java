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
import java.util.Map;
import java.util.Optional;

public interface ISessionService {

    Optional<SessionResponse> generateTransaction(Map<String, String> headers,
        SessionRequest request);

    boolean validSession(String sessionId, String customerId);
}
