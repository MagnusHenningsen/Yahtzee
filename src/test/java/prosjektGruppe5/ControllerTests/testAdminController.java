package prosjektGruppe5.ControllerTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import prosjektGruppe5.Controllers.AdminController;
import prosjektGruppe5.DAO.GameDAO;
import prosjektGruppe5.DAO.PersonDAO;
import prosjektGruppe5.Entities.Game;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Utilities.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class testAdminController {

    @Mock
    private SessionUtil sessionUtil;
    @Mock
    private PersonDAO personDAO;
    @Mock
    private GameDAO gameDAO;
    @Mock
    private HttpServletRequest httpRequest;
    @Mock
    private RedirectAttributes redirectAttributes;
    @Mock
    private Model model;
    @Mock
    private HttpServletResponse httpResponse;
    @Mock
    private List<Game> listOfAllGames;
    @Mock
    private List<Person> listOfAllUsers;
    @InjectMocks
    private AdminController adminController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        listOfAllGames = new ArrayList<>();
        listOfAllUsers = new ArrayList<>();
    }

    @Test
    public void testAdminViewWithValidSession() {
        HttpSession session = mock(HttpSession.class);
        when(httpRequest.getSession()).thenReturn(session);
        when(sessionUtil.ValidateSession(session)).thenReturn(true);
        when(personDAO.findAll()).thenReturn(listOfAllUsers);
        when(gameDAO.findAll()).thenReturn(listOfAllGames);

        String viewName = adminController.showAdminView(httpRequest, redirectAttributes, model, httpResponse);

        verify(sessionUtil, times(1)).ValidateSession(session);
        verify(model, times(1)).addAttribute(eq("adminViewOfGames"), eq(listOfAllGames));
        verify(model, times(1)).addAttribute(eq("adminViewOfRegisteredUsers"), eq(listOfAllUsers));


        assertEquals("AdminView", viewName);

    }

    @Test
    public void testAdminViewWithInvalidSession() {
        HttpSession session = mock(HttpSession.class);
        when(httpRequest.getSession()).thenReturn(session);
        when(sessionUtil.ValidateSession(session)).thenReturn(false);

        String viewName = adminController.showAdminView(httpRequest, redirectAttributes, model, httpResponse);

        assertEquals("redirect:/login", viewName);
    }
}
