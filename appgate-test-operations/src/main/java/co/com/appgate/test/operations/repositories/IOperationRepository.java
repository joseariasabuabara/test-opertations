/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
package co.com.appgate.test.operations.repositories;

import co.com.appgate.test.operations.model.Operation;
import java.util.List;
import java.util.Optional;

public interface IOperationRepository {

    void saveOperation(Operation operation);

    Optional<List<Operation>> findOperations(String userId);
}
