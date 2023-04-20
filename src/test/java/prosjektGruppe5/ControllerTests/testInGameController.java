package prosjektGruppe5.ControllerTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.Controllers.InGameController;
import prosjektGruppe5.DAO.GameDAO;
import prosjektGruppe5.DAO.PersonDAO;
import prosjektGruppe5.DAO.PlayerDAO;
import prosjektGruppe5.DAO.RoundDAO;
import prosjektGruppe5.Utilities.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@MockitoSettings(strictness = Strictness.LENIENT)
public class testInGameController {
    @Mock
    private PersonDAO personDAO;
    @Mock
    private RoundDAO roundDAO;
    @Mock
    private GameDAO gameDAO;
    @Mock
    private PlayerDAO playerDAO;
    @Mock
    private SessionUtil sessionUtil;
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private RedirectAttributes ra;
    @Mock
    private Model model;
    @InjectMocks
    private InGameController inGameController;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
    }
    @Test
    public void testGetInGameViewWithInvalidSession() {
        when(sessionUtil.ValidateSession(session)).thenReturn(false);
        when(request.getSession().getAttribute("username")).thenReturn(null);
        Assert.assertEquals("redirect:/login", inGameController.geInGameView(request, ra, model));
    }

    @Test
    public void testGetInGameViewWithValidSession() {
        when(sessionUtil.ValidateSession(session)).thenReturn(true);
        when(request.getSession().getAttribute("username")).thenReturn(null);
        Assert.assertEquals("GameView", inGameController.geInGameView(request, ra, model));
    }
}
