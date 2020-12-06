/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
package co.com.appgate.test.session.repositories;

import co.com.appgate.test.session.model.SessionResponse;

public interface ISessionRepository {

    void saveSession(final SessionResponse session);

    SessionResponse findByTransactionId(final String transactionId);
}
