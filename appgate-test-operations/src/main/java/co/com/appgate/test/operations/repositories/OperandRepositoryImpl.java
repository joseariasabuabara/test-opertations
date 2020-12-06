package co.com.appgate.test.operations.repositories;

import co.com.appgate.test.operations.model.Operand;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperandRepositoryImpl implements IOperandRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperandRepositoryImpl.class);

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public OperandRepositoryImpl(final DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }


    @Override
    public void saveOperand(final Operand operand) {
        LOGGER.info("save operand {}", operand);
        dynamoDBMapper.save(operand);
    }

    @Override
    public List<BigDecimal> getOperandsBySessionId(final String sessionId) {
        LOGGER.info("Init query getOperandsBySessionId DB");

        final HashMap<String, AttributeValue> eav = new HashMap<>();
        eav.put(":sessionId", new AttributeValue().withS(sessionId));

        final DynamoDBQueryExpression<Operand> queryExpression =
            new DynamoDBQueryExpression<Operand>()
                .withIndexName("sessionId-index")
                .withConsistentRead(false)
                .withKeyConditionExpression(
                    "sessionId = :sessionId")
                .withExpressionAttributeValues(eav);

        final PaginatedQueryList<Operand> iList =
            this.dynamoDBMapper.query(Operand.class, queryExpression);

        return iList.stream().map(operand -> operand.getValue()).collect(Collectors.toList());

    }
}
