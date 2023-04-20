package prosjektGruppe5.ControllerTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.Controllers.LandingController;
import prosjektGruppe5.DAO.GameDAO;
import prosjektGruppe5.Entities.Game;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Utilities.EncryptionUtil;
import prosjektGruppe5.Utilities.SessionUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class testLandingController {
    @Test
    public void testRedirectOnInvalidSession() {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        RedirectAttributes ra = Mockito.mock(RedirectAttributes.class);
        Model model = Mockito.mock(Model.class);
        HttpServletResponse sr = Mockito.mock(HttpServletResponse.class);
        SessionUtil seu = new SessionUtil();
        GameDAO gd = mock(GameDAO.class);
        HttpSession s = Mockito.mock(HttpSession.class);
        when(seu.ValidateSession(s)).thenReturn(false);
        when(req.getSession()).thenReturn(s);
        LandingController lc  = new LandingController(Mockito.mock(EntityManagerFactory.class),seu, gd);

        s = null;
        when(req.getSession()).thenReturn(s);

        assertEquals("redirect:/login", lc.showLandingView(req,ra,model,sr));
    }

    @Test
    public void testGetLandingViewOnValidSession() {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        RedirectAttributes ra = Mockito.mock(RedirectAttributes.class);
        Model model = Mockito.mock(Model.class);
        HttpServletResponse sr = Mockito.mock(HttpServletResponse.class);
        SessionUtil seu = new SessionUtil();
        GameDAO gd = mock(GameDAO.class);
        HttpSession s = Mockito.mock(HttpSession.class);
        when(seu.ValidateSession(s)).thenReturn(false);
        when(req.getSession()).thenReturn(s);
        LandingController lc  = new LandingController(Mockito.mock(EntityManagerFactory.class),seu, gd);
        Person p = new Person();
        p.setUserName("Testuser");
        p.setFullName("Test Testerson");
        EncryptionUtil eu = new EncryptionUtil();
        p.setSalt(eu.getSalt());
        p.setPaswordHash(eu.hashPassword("password", p.getSalt()));
        p.setEmail("Test@Gmail.com");
        when(s.getAttribute("user")).thenReturn(p);
        assertEquals("LandingView", lc.showLandingView(req, ra, model, sr));
    }

    @Test
    public void testCreateGame() {
        EntityManagerFactory emf = mock(EntityManagerFactory.class);
        EntityManager em = mock(EntityManager.class);
        EntityTransaction tx = mock(EntityTransaction.class);

        when(emf.createEntityManager()).thenReturn(em);
        when(em.getTransaction()).thenReturn(tx);
        Game newGame = new Game();
        GameDAO gd = mock(GameDAO.class);
        SessionUtil seu = new SessionUtil();
        LandingController lc = new LandingController(emf, seu, gd);
        lc.createGame(newGame);

        Mockito.verify(emf).createEntityManager();
        Mockito.verify(em).getTransaction();
        Mockito.verify(em).persist(newGame);
        Mockito.verify(tx).commit();
        Mockito.verify(em).close();

    }





}
