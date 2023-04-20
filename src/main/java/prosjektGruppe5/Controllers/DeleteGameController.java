package prosjektGruppe5.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.DAO.GameDAO;
import prosjektGruppe5.Entities.Game;
import prosjektGruppe5.Utilities.SessionUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
@Controller
@RequestMapping("/deleteGame")
public class DeleteGameController {
    @Autowired
    private GameDAO gameDAO;

    /**
     * This method handles the HTTP POST request for deleting a game from the database.
     */
    @PostMapping()
    public String deleteGameFromDataBase(@RequestParam("gameId") Integer deleteGameWithThisId,
                                         HttpServletRequest httpRequest,
                                         RedirectAttributes redirectAttributes,
                                         Model model, HttpServletResponse httpResponse){
        // Recieve the selected game_id.
        deleteGameById(deleteGameWithThisId);
        return "redirect:admin";
    }


    //--------------------------------------------------------------------------------------
    // Useful functions

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * This method deletes a game with a given ID from the database.
     * @param gameId The ID of the game to delete.
     */
    public void deleteGameById(Integer gameId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // Get the game that has the gameID selected.
        Game gameToBeRemoved = gameDAO.findGameByGameId(gameId);
        // Check if there are any games linked to this gameId
        // After, check the game linked to this gameId
        try {
            entityManager.getTransaction().begin();
            Game gameToBeRemovedButMerged = entityManager.merge(gameToBeRemoved);
            entityManager.remove(gameToBeRemovedButMerged);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}

