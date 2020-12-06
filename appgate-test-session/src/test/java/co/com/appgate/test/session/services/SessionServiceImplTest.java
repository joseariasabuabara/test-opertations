package co.com.appgate.test.session.services;

import co.com.appgate.test.session.model.SessionRequest;
import co.com.appgate.test.session.model.SessionResponse;
import co.com.appgate.test.session.repositories.ISessionRepository;
import co.com.appgate.test.session.services.SessionServiceImpl;
import java.util.HashMap;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SessionServiceImplTest {

    @Mock
    private ISessionRepository repository;

    @InjectMocks
    private SessionServiceImpl service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void generateTransactionTest() {
        //setup

        Mockito.doNothing().when(repository).saveSession(Mockito.any());

        //execution
        final Optional<SessionResponse> sessionResponse =
            service
                .generateTransaction(new HashMap<>(),
                    SessionRequest.builder().customerId("user_1").build());

        //assertions
        Assert.assertNotNull(sessionResponse);
        Assert.assertNotNull(sessionResponse.get().getTransactionId());

        Assert.assertEquals("user_1", sessionResponse.get().getCustomerId());
    }

    @Test
    public void generateTransactionBadRequestTest() {
        //setup

        //execution
        final Optional<SessionResponse> sessionResponse =
            service
                .generateTransaction(new HashMap<>(),
                    SessionRequest.builder().build());

        //assertions
        Assert.assertNotNull(sessionResponse);
        Assert.assertEquals(false, sessionResponse.isPresent());

    }


    @Test(expected = RuntimeException.class)
    public void failSaveSessionTest() {
        //setup
        Mockito.doThrow(new RuntimeException("Wrong")).when(repository).saveSession(Mockito.any());

        //execution
        final Optional<SessionResponse> sessionResponse =
            service
                .generateTransaction(new HashMap<>(),
                    SessionRequest.builder().customerId("user_1").build());

        //assertions
        Assert.assertNotNull(sessionResponse);
        Assert.assertEquals(false, sessionResponse.isPresent());

    }
}
