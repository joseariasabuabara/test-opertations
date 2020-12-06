package co.com.appgate.test.operations.service;

import co.com.appgate.test.operations.model.Operand;
import co.com.appgate.test.operations.model.OperandsRequest;
import co.com.appgate.test.operations.model.OperandsResponse;
import co.com.appgate.test.operations.repositories.IOperandRepository;
import co.com.appgate.test.operations.util.StatusInfo;
import co.com.appgate.test.operations.util.UtilHeader;
import co.com.appgate.test.session.services.ISessionService;
import io.vavr.control.Try;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperandsServiceImpl implements IOperandsService {

    private static final Logger LOGGER = LogManager.getLogger(OperandsServiceImpl.class);
    private static final String MESSAGE_ERROR = "technical errors, Ups something wrong";

    private final IOperandRepository repository;
    private final ISessionService sessionService;

    @Autowired
    public OperandsServiceImpl(final IOperandRepository repository,
        final ISessionService sessionService) {
        this.repository = repository;
        this.sessionService = sessionService;
    }

    @Override
    public Optional<OperandsResponse> addOperand(final Map<String, String> headers,
        final OperandsRequest request) {

        LOGGER.info("addOperand service: headers {} request {} ", headers, request);

        if (!validateRequest(request)) {
            return Optional.empty();
        }

        if (!validSession(headers)) {
            return Optional
                .of(OperandsResponse.builder().statusCode(StatusInfo.NOT_AUTHORIZED.getCode())
                    .status(StatusInfo.NOT_AUTHORIZED.getMessage())
                    .build());
        }

        request.getOperandsValue().stream().forEach(value -> saveOperand(value, headers));

        return Optional
            .of(OperandsResponse.builder().statusCode(StatusInfo.SUCCESFULLY.getCode())
                .status(StatusInfo.SUCCESFULLY.getMessage()).build());
    }

    @Override
    public List<BigDecimal> getOperandsBySessionId(final Map<String, String> headers,
        final String sessionId) {
        final List<BigDecimal> values = this.repository.getOperandsBySessionId(sessionId);
        LOGGER.info("Operands found: {}", values);
        return values;
    }

    private boolean validateRequest(final OperandsRequest request) {
        return (request != null && request.getOperandsValue() != null && !request.getOperandsValue()
            .isEmpty());
    }

    private boolean validSession(final Map<String, String> headers) {
        if (headers.get(UtilHeader.HEADER_APPGATE_SESSION_ID) == null
            || headers.get(UtilHeader.HEADER_APPGATE_CUSTOMER_ID) == null) {
            return false;
        }

        return this.sessionService.validSession(headers.get(UtilHeader.HEADER_APPGATE_SESSION_ID),
            headers.get(UtilHeader.HEADER_APPGATE_CUSTOMER_ID));
    }

    private void saveOperand(final BigDecimal value, final Map<String, String> headers) {
        final Operand operand = Operand.builder().transactionId(UUID.randomUUID().toString())
            .sessionId(headers.get(UtilHeader.HEADER_APPGATE_SESSION_ID))
            .customerId(headers.get(UtilHeader.HEADER_APPGATE_CUSTOMER_ID))
            .date(new Date())
            .value(value)
            .build();

        Try.of(
            () -> {
                repository.saveOperand(operand);
                return null;
            }).onFailure(throwable -> {
            LOGGER.error(MESSAGE_ERROR + throwable.getMessage());
            throw new RuntimeException(MESSAGE_ERROR);
        });

    }
}
