/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
package co.com.appgate.test.operations.service;

import co.com.appgate.test.operations.model.OperandsRequest;
import co.com.appgate.test.operations.model.OperandsResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IOperandsService {

    Optional<OperandsResponse> addOperand(Map<String, String> headers, OperandsRequest request);

    List<BigDecimal> getOperandsBySessionId(Map<String, String> headers, String sessionId);
}
