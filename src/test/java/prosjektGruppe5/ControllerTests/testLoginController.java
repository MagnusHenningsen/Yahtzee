package prosjektGruppe5.ControllerTests;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import prosjektGruppe5.Controllers.LogInController;
import prosjektGruppe5.DAO.GameDAO;
import prosjektGruppe5.DAO.PersonDAO;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Utilities.EncryptionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class testLoginController {


    @Test
    public void testCorrectViewOnGet() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        PersonDAO pd = mock(PersonDAO.class);
        HttpSession s = mock(HttpSession.class);
        when(req.getSession()).thenReturn(s);
        EncryptionUtil eu = new EncryptionUtil();
        GameDAO gd = mock(GameDAO.class);
        LogInController loginController = new LogInController(eu, pd, gd);
        assertEquals("RegisterView", loginController.getLoginView(req));
        Person p = new Person();

        List<Person> ppl = new ArrayList<Person>();
        ppl.add(p);
        when(pd.findAll()).thenReturn(ppl);
        assertEquals("LoginView", loginController.getLoginView(req));
    }

}
