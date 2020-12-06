package co.com.appgate.test.operations.repositories;

import co.com.appgate.test.operations.model.Operand;
import java.math.BigDecimal;
import java.util.List;

public interface IOperandRepository {

    void saveOperand(Operand operand);

    List<BigDecimal> getOperandsBySessionId(String sessionId);
}
