/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
package co.com.appgate.test.operations.controller;

import co.com.appgate.test.operations.model.Operation;
import co.com.appgate.test.operations.model.OperatorRequest;
import co.com.appgate.test.operations.model.OperatorResponse;
import co.com.appgate.test.operations.service.IOperatorService;
import co.com.appgate.test.operations.util.StatusInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class OperatorsControllerTest {

    @Mock
    private IOperatorService service;

    @InjectMocks
    private OperatorsController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void executeTest() {
        //setup
        Mockito.when(service.execute(Mockito.any(), Mockito.any()))
            .thenReturn(Optional
                .of(OperatorResponse.builder().statusCode(StatusInfo.SUCCESFULLY.getCode())
                    .status("ok").build()));

        //execution
        final ResponseEntity<OperatorResponse> responseEntity =
            controller
                .execute(new HttpHeaders(),
                    OperatorRequest.builder().build());

        //assertions
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals("0", responseEntity.getBody().getStatusCode());

    }

    @Test
    public void executeFailTest() {
        //setup
        Mockito.when(service.execute(Mockito.any(), Mockito.any()))
            .thenReturn(Optional
                .of(OperatorResponse.builder().statusCode(StatusInfo.NOT_AUTHORIZED.getCode())
                    .status("ok").build()));

        //execution
        final ResponseEntity<OperatorResponse> responseEntity =
            controller
                .execute(new HttpHeaders(),
                    OperatorRequest.builder().build());

        //assertions
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(401, responseEntity.getStatusCodeValue());
        Assert.assertEquals("303", responseEntity.getBody().getStatusCode());


    }

    @Test
    public void auditOperationsTest() {
        //setup
        Mockito.when(service.findOperations(Mockito.any(), Mockito.any()))
            .thenReturn(Optional
                .of(new ArrayList<>()));

        //execution
        final ResponseEntity<List<Operation>> responseEntity =
            controller
                .auditOperations(new HttpHeaders(),
                    "User1");

        //assertions
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

    }
}
