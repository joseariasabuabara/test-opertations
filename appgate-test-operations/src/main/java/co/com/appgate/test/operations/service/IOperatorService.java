/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
package co.com.appgate.test.operations.service;

import co.com.appgate.test.operations.model.Operation;
import co.com.appgate.test.operations.model.OperatorRequest;
import co.com.appgate.test.operations.model.OperatorResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IOperatorService {

    Optional<OperatorResponse> execute(Map<String, String> headers, OperatorRequest request);

    Optional<List<Operation>> findOperations(Map<String, String> headers, String userId);
}
