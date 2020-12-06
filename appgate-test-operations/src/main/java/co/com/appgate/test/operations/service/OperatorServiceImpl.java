package co.com.appgate.test.operations.service;

import co.com.appgate.test.operations.model.Operand;
import co.com.appgate.test.operations.model.Operation;
import co.com.appgate.test.operations.model.OperatorRequest;
import co.com.appgate.test.operations.model.OperatorResponse;
import co.com.appgate.test.operations.repositories.IOperandRepository;
import co.com.appgate.test.operations.repositories.IOperationRepository;
import co.com.appgate.test.operations.rules.OperationFact;
import co.com.appgate.test.operations.util.OperationtsMath;
import co.com.appgate.test.operations.util.StatusInfo;
import co.com.appgate.test.operations.util.UtilHeader;
import co.com.appgate.test.session.services.ISessionService;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.Result;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import io.vavr.control.Try;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorServiceImpl implements IOperatorService {

    private static final Logger LOGGER = LogManager.getLogger(OperatorServiceImpl.class);
    private static final String MESSAGE_ERROR = "technical errors, Ups something wrong";

    private final IOperandRepository repository;
    private final IOperationRepository operationRepository;
    private final ISessionService sessionService;
    private final IOperandsService operandsService;
    private final RuleBook ruleBook;

    @Autowired
    public OperatorServiceImpl(final IOperandRepository repository,
        final ISessionService sessionService,
        final IOperationRepository operationRepository,
        final IOperandsService operandsService,
        final RuleBook ruleBook) {
        this.repository = repository;
        this.sessionService = sessionService;
        this.operationRepository = operationRepository;
        this.operandsService = operandsService;
        this.ruleBook = ruleBook;
    }

    @Override
    public Optional<OperatorResponse> execute(final Map<String, String> headers,
        final OperatorRequest request) {

        LOGGER.info("execute operation service: headers {} request {} ", headers, request);

        if (!validateRequest(request)) {
            return Optional.empty();
        }

        if (!validSession(headers)) {
            return Optional
                .of(OperatorResponse.builder().statusCode(StatusInfo.NOT_AUTHORIZED.getCode())
                    .status(StatusInfo.NOT_AUTHORIZED.getMessage())
                    .build());
        }

        if (!validOperation(request.getOperation())) {
            return Optional
                .of(OperatorResponse.builder().statusCode(StatusInfo.NOT_ALLOWED.getCode())
                    .status(StatusInfo.NOT_ALLOWED.getMessage())
                    .build());
        }

        final List<BigDecimal> operands = getOperands(
            headers.get(UtilHeader.HEADER_APPGATE_SESSION_ID));

        if (operands.size() <= 1) {
            return Optional
                .of(OperatorResponse.builder().statusCode(StatusInfo.MISSING_OPERAND.getCode())
                    .status(StatusInfo.MISSING_OPERAND.getMessage())
                    .build());
        }
        final BigDecimal result = runOperation(request.getOperation(), operands);

        LOGGER.info("operation {} result {} ", request.getOperation(), result);

        saveOperation(request.getOperation(), headers, result);

        if (request.isResultAddOperand()) {
            CompletableFuture.runAsync(() -> saveOperand(result, headers));
        }

        return Optional
            .of(OperatorResponse.builder().statusCode(StatusInfo.SUCCESFULLY.getCode())
                .status(StatusInfo.SUCCESFULLY.getMessage()).result(result).build());
    }

    @Override
    public Optional<List<Operation>> findOperations(final Map<String, String> headers,
        final String userId) {
        return this.operationRepository.findOperations(userId);
    }

    private List<BigDecimal> getOperands(final String sessionId) {
        return operandsService.getOperandsBySessionId(new HashMap<>(), sessionId);
    }

    private boolean validOperation(final String operations) {
        return Arrays.stream(OperationtsMath.values())
            .anyMatch(operationtsMath -> operationtsMath.getMessage().equalsIgnoreCase(operations));
    }

    private BigDecimal runOperation(final String operation, final List<BigDecimal> values) {
        final NameValueReferableMap<OperationFact> facts =
            buildFacts(operation, values);

        return getResultRules(facts);
    }

    private NameValueReferableMap<OperationFact> buildFacts(
        final String operation, final List<BigDecimal> values) {
        final OperationFact fact =
            OperationFact.builder()
                .operation(operation)
                .values(values)
                .build();
        final NameValueReferableMap<OperationFact> facts = new FactMap<>();
        facts.setValue("fact", fact);
        return facts;
    }

    private BigDecimal getResultRules(final NameValueReferableMap<OperationFact> facts) {
        ruleBook.setDefaultResult(new BigDecimal(0));
        ruleBook.run(facts);
        final Optional<Result> result = ruleBook.getResult();
        return new BigDecimal(result.get().getValue().toString());

    }

    private boolean validateRequest(final OperatorRequest request) {
        return (request != null && request.getOperation() != null && !request.getOperation()
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

    private void saveOperation(final String operation, final Map<String, String> headers,
        final BigDecimal result) {
        final Operation operationExecuted = Operation.builder()
            .transactionId(UUID.randomUUID().toString())
            .sessionId(headers.get(UtilHeader.HEADER_APPGATE_SESSION_ID))
            .customerId(headers.get(UtilHeader.HEADER_APPGATE_CUSTOMER_ID))
            .date(new Date())
            .result(result)
            .operation(operation)
            .build();

        Try.of(
            () -> {
                operationRepository.saveOperation(operationExecuted);
                return null;
            }).onFailure(throwable -> {
            LOGGER.error(MESSAGE_ERROR + throwable.getMessage());
            throw new RuntimeException(MESSAGE_ERROR);
        });

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
