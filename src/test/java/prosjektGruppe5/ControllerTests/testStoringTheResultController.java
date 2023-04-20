package prosjektGruppe5.ControllerTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.Controllers.StoringTheResultController;
import prosjektGruppe5.DAO.GameDAO;
import prosjektGruppe5.DAO.PersonDAO;
import prosjektGruppe5.DAO.RoundDAO;
import prosjektGruppe5.Entities.Game;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Utilities.PointsCalculatingUtil;
import prosjektGruppe5.Utilities.SessionUtil;
import prosjektGruppe5.Utilities.gameStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class testStoringTheResultController {


    @Mock
    private Model model;

    @InjectMocks
    private StoringTheResultController storingTheResultController;



    @Test
    public void testStoringTheResults() {
        GameDAO gd = mock(GameDAO.class);
        PersonDAO pd = mock(PersonDAO.class);
        RoundDAO rd = mock(RoundDAO.class);
        PointsCalculatingUtil pointsCalculatingUtil = new PointsCalculatingUtil();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession s = mock(HttpSession.class);
        when(req.getSession()).thenReturn(s);
        when(s.getAttribute("username")).thenReturn("admin");
        EntityManagerFactory emf = mock(EntityManagerFactory.class);
        RedirectAttributes ra = mock(RedirectAttributes.class);
        Model model = mock(Model.class);
        StoringTheResultController controller = new StoringTheResultController(rd, gd, pd, pointsCalculatingUtil, emf);
        String diceValues = "[6,4,3,5,6]";
        Person p = new Person();
        p.setUserId(1);
        p.setUserName("admin");
        p.setFullName("admin adminson");
        p.setPaswordHash("pass");
        p.setSalt("pass");
        p.setEmail("admin@gmail.com");
        Game game = new Game(p, gameStatus.VENTER_PÅ_SPILLERE);
        game.setGameId(1);
        when(gd.findGameByGameId(1)).thenReturn(game);
        EntityManager em = mock(EntityManager.class);
        when(emf.createEntityManager()).thenReturn(em);
        SessionUtil su = mock(SessionUtil.class);
        controller.su = su;
        when(su.ValidateSession(req.getSession())).thenReturn(true);
        assertEquals("redirect:landing/game", controller.storingTheResults(game.getGameId(), diceValues, req, ra, model));
        when(su.ValidateSession(req.getSession())).thenReturn(false);
        assertEquals("redirect:/login", controller.storingTheResults(game.getGameId(), diceValues, req, ra, model));
    }

}
