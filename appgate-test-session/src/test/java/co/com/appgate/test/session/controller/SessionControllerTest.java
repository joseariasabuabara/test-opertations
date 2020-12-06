package co.com.appgate.test.session.controller;

import co.com.appgate.test.session.controller.SessionController;
import co.com.appgate.test.session.model.SessionRequest;
import co.com.appgate.test.session.model.SessionResponse;
import co.com.appgate.test.session.services.ISessionService;
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

public class SessionControllerTest {

    @Mock
    private ISessionService service;

    @InjectMocks
    private SessionController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void generateTransactionTest() {
        //setup
        Mockito.when(service.generateTransaction(Mockito.anyMap(), Mockito.any()))
            .thenReturn(Optional
                .of(SessionResponse.builder().customerId("1234").transactionId("ee124").build()));

        //execution
        final ResponseEntity<SessionResponse> authenticate =
            controller
                .generateTransaction(new HttpHeaders(),
                    SessionRequest.builder().customerId("user_1").build());

        //assertions
        Assert.assertNotNull(authenticate);
        Assert.assertEquals(200, authenticate.getStatusCodeValue());
        Assert.assertEquals("1234", authenticate.getBody().getCustomerId());
        Assert.assertEquals("ee124", authenticate.getBody().getTransactionId());
    }
}
