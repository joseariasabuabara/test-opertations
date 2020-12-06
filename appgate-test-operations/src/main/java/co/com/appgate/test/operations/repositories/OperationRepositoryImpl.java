package co.com.appgate.test.operations.repositories;

import co.com.appgate.test.operations.model.Operation;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperationRepositoryImpl implements IOperationRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationRepositoryImpl.class);

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public OperationRepositoryImpl(final DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }


    @Override
    public void saveOperation(final Operation operation) {
        LOGGER.info("save operation {}", operation);
        dynamoDBMapper.save(operation);
    }

    @Override
    public Optional<List<Operation>> findOperations(final String customerId) {
        LOGGER.info("Init query findOperations DB {}", customerId);

        final HashMap<String, AttributeValue> eav = new HashMap<>();
        eav.put(":customerId", new AttributeValue().withS(customerId));

        final DynamoDBQueryExpression<Operation> queryExpression =
            new DynamoDBQueryExpression<Operation>()
                .withIndexName("customerId-index")
                .withConsistentRead(false)
                .withKeyConditionExpression(
                    "customerId = :customerId")
                .withExpressionAttributeValues(eav);

        final PaginatedQueryList<Operation> iList =
            this.dynamoDBMapper.query(Operation.class, queryExpression);

        return Optional.ofNullable(iList.isEmpty() ? null : iList);
    }

}
