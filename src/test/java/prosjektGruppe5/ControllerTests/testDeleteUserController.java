package prosjektGruppe5.ControllerTests;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.Controllers.DeleteGameController;
import prosjektGruppe5.Controllers.DeleteUserController;
import prosjektGruppe5.DAO.GameDAO;
import prosjektGruppe5.DAO.PersonDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class testDeleteUserController {
    @Mock
    private HttpServletRequest request = mock(HttpServletRequest.class);
    @Mock
    private RedirectAttributes ra = mock(RedirectAttributes.class);
    @Mock
    private Model model = mock(Model.class);
    @Mock
    private HttpServletResponse hsr = mock(HttpServletResponse.class);

    private Integer rp = 1;

    @Mock
    private PersonDAO pd = mock(PersonDAO.class);
    @Mock
    private EntityManagerFactory emf = mock(EntityManagerFactory.class);
    @Mock
    private EntityManager em = mock(EntityManager.class);
    @Mock
    private EntityTransaction tx = mock(EntityTransaction.class);
    @InjectMocks
    private DeleteUserController deleteUserController = new DeleteUserController(pd, emf);
    @Test
    public void testDeleteUserFromDataBase(){
        when(emf.createEntityManager()).thenReturn(em);
        when(em.getTransaction()).thenReturn(tx);
        Assert.assertEquals("redirect:admin",deleteUserController.deleteUserFromDataBase(rp,request,ra,model,hsr));
    }
}
