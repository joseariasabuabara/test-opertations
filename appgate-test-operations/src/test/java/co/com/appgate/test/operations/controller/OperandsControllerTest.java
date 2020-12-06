package co.com.appgate.test.operations.controller;

import co.com.appgate.test.operations.controller.OperandsController;
import co.com.appgate.test.operations.model.OperandsRequest;
import co.com.appgate.test.operations.model.OperandsResponse;
import co.com.appgate.test.operations.service.IOperandsService;
import co.com.appgate.test.operations.util.StatusInfo;
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

public class OperandsControllerTest {

    @Mock
    private IOperandsService service;

    @InjectMocks
    private OperandsController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void addOperandsTest() {
        //setup
        Mockito.when(service.addOperand(Mockito.any(), Mockito.any()))
            .thenReturn(Optional
                .of(OperandsResponse.builder().statusCode(StatusInfo.SUCCESFULLY.getCode())
                    .status("ok").build()));

        //execution
        final ResponseEntity<OperandsResponse> authenticate =
            controller
                .addOperands(new HttpHeaders(),
                    OperandsRequest.builder().build());

        //assertions
        Assert.assertNotNull(authenticate);
        Assert.assertEquals(200, authenticate.getStatusCodeValue());
        Assert.assertEquals("0", authenticate.getBody().getStatusCode());

    }

    @Test
    public void addOperandsFailTest() {
        //setup
        Mockito.when(service.addOperand(Mockito.any(), Mockito.any()))
            .thenReturn(Optional
                .of(OperandsResponse.builder().statusCode(StatusInfo.NOT_AUTHORIZED.getCode())
                    .status("fail").build()));

        //execution
        final ResponseEntity<OperandsResponse> authenticate =
            controller
                .addOperands(new HttpHeaders(),
                    OperandsRequest.builder().build());

        //assertions
        Assert.assertNotNull(authenticate);
        Assert.assertEquals(401, authenticate.getStatusCodeValue());
        Assert.assertEquals("303", authenticate.getBody().getStatusCode());

    }
}
