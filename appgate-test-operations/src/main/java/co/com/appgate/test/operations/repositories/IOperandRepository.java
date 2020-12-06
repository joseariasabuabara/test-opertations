/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
package co.com.appgate.test.operations.repositories;

import co.com.appgate.test.operations.model.Operand;
import java.math.BigDecimal;
import java.util.List;

public interface IOperandRepository {

    void saveOperand(Operand operand);

    List<BigDecimal> getOperandsBySessionId(String sessionId);
}
