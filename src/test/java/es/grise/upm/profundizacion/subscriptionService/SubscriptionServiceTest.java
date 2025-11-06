package es.grise.upm.profundizacion.subscriptionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.grise.upm.profundizacion.exceptions.ExistingUserException;
import es.grise.upm.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;
import es.grise.upm.profundizacion.exceptions.NullUserException;

class SubscriptionServiceTest {

    private SubscriptionService service;
    private User user;

    @BeforeEach
    void setUp() {
        Delivery deliveryMock = mock(Delivery.class);
        service = new SubscriptionService(deliveryMock);
        user = mock(User.class);
    }

    @Test
    void noDeberiaAceptarUsuarioNulo() {
        assertThrows(NullUserException.class, () -> service.addSubscriber(null));
    }

    @Test
    void deberiaAgregarUsuarioValido() throws Exception {
        when(user.getDelivery()).thenReturn(Delivery.REMOTE);
        when(user.getEmail()).thenReturn("ejemplo@ejemplo.com");

        service.addSubscriber(user);

        Collection<User> subscriptores = service.getSubscribers();

        assertTrue(subscriptores.contains(user));
        assertEquals(1, subscriptores.size());
    }

    @Test
    void noDeberiaPermitirUsuarioDuplicado() throws Exception {
        when(user.getDelivery()).thenReturn(Delivery.REMOTE);
        when(user.getEmail()).thenReturn("correo@ejemplo.com");

        service.addSubscriber(user);

        assertThrows(ExistingUserException.class, () -> service.addSubscriber(user));
    }

    @Test
    void usuarioLocalConEmailDebeFallar() {
        when(user.getDelivery()).thenReturn(Delivery.LOCAL);
        when(user.getEmail()).thenReturn("local@ejemplo.com");

        assertThrows(LocalUserDoesNotHaveNullEmailException.class, () -> service.addSubscriber(user));
    }

    @Test
    void usuarioLocalConEmailNullDebeSerValido() throws Exception {
        when(user.getDelivery()).thenReturn(Delivery.LOCAL);
        when(user.getEmail()).thenReturn(null);

        service.addSubscriber(user);

        assertTrue(service.getSubscribers().contains(user));
        assertEquals(1, service.getSubscribers().size());
    }
}