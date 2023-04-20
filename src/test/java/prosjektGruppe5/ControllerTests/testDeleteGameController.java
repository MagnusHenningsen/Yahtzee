package prosjektGruppe5.ControllerTests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.Controllers.DeleteGameController;
import prosjektGruppe5.DAO.GameDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class testDeleteGameController {

    @Mock
    private GameDAO gameDAO = mock(GameDAO.class);
    @Mock
    private HttpServletRequest request = mock(HttpServletRequest.class);
    @Mock
    private RedirectAttributes ra =mock(RedirectAttributes.class);
    @Mock
    private Model model = mock(Model.class);
    @Mock
    private HttpServletResponse hsr = mock(HttpServletResponse.class);
    @Mock
    private EntityManagerFactory emf = mock(EntityManagerFactory.class);
    @Mock
    private EntityManager em = mock(EntityManager.class);
    @Mock
    private EntityTransaction tx = mock(EntityTransaction.class);

    @InjectMocks
    private DeleteGameController dgc = new DeleteGameController();

    @Test
    public void testDeleteGameFromDataBase(){
        when(emf.createEntityManager()).thenReturn(em);
        when(em.getTransaction()).thenReturn(tx);
        Integer rp = 1;
        String expected = "redirect:admin";
        String actual = dgc.deleteGameFromDataBase(rp,request,ra,model,hsr);
        Assert.assertEquals(expected,actual);
    }

}
