/**
 * Grupo Aval Acciones y Valores S.A. CONFIDENTIAL
 *
 * <p>Copyright (c) 2018 . All Rights Reserved.
 *
 * <p>NOTICE: This file is subject to the terms and conditions defined in file 'LICENSE', which is
 * part of this source code package.
 */
package co.com.appgate.test.operations.service;

import co.com.appgate.test.operations.model.OperandsRequest;
import co.com.appgate.test.operations.model.OperandsResponse;
import co.com.appgate.test.operations.repositories.IOperandRepository;
import co.com.appgate.test.operations.service.OperandsServiceImpl;
import co.com.appgate.test.operations.util.StatusInfo;
import co.com.appgate.test.operations.util.UtilHeader;
import co.com.appgate.test.session.services.ISessionService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class OperandsServiceImplTest {

    @Mock
    private IOperandRepository repository;

    @Mock
    private ISessionService sessionService;


    @InjectMocks
    private OperandsServiceImpl service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void servicetest() {
        //setup

        Mockito.doNothing().when(repository).saveOperand(Mockito.any());

        Mockito.when(
            sessionService.validSession(Mockito.any(), Mockito.any())).thenReturn(true);
        final Map<String, String> map = new HashMap();
        map.put(UtilHeader.HEADER_APPGATE_SESSION_ID, "1234");
        map.put(UtilHeader.HEADER_APPGATE_CUSTOMER_ID, "1234");

        final List<BigDecimal> values = new ArrayList<>();
        values.add(new BigDecimal(0));

        //execution
        final Optional<OperandsResponse> response =
            service
                .addOperand(map,
                    OperandsRequest.builder().operandsValue(values).build());

        //assertions
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.get().getStatusCode());

        Assert.assertEquals(StatusInfo.SUCCESFULLY.getCode(), response.get().getStatusCode());
    }


}
