package co.com.appgate.test.session.repositories;

import co.com.appgate.test.session.model.SessionResponse;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionRepositoryImpl implements ISessionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryImpl.class);
    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public SessionRepositoryImpl(final DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public void saveSession(final SessionResponse session) {
        LOGGER.info("save Session {}", session);
        dynamoDBMapper.save(session);
    }

    @Override
    public SessionResponse findByTransactionId(final String transactionId) {
        LOGGER.info("find Session {}", transactionId);
        return dynamoDBMapper.load(SessionResponse.class, transactionId);
    }
}
