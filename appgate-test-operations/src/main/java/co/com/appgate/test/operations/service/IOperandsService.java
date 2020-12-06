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
