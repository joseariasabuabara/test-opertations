/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
package co.com.appgate.test.session.repositories;

import co.com.appgate.test.session.model.SessionResponse;
import co.com.appgate.test.session.repositories.SessionRepositoryImpl;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SessionRepositoryImplTest {

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @InjectMocks
    private SessionRepositoryImpl repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void generateTransactionTest() {
        //setup
        Mockito.doNothing().when(dynamoDBMapper).save(Mockito.any());

        //execution
        repository.saveSession(SessionResponse.builder().transactionId("12434").build());

        //assertions
        Mockito.verify(dynamoDBMapper, Mockito.times(1)).save(Mockito.any());
    }
}
