package prosjektGruppe5.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.DAO.PersonDAO;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Utilities.SessionUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/*
 The DeleteUserController class handles HTTP requests for deleting a user from the database.
 */
@Controller
@RequestMapping("/deleteUser")
public class DeleteUserController {
    @Autowired
    public DeleteUserController(PersonDAO personDAO, EntityManagerFactory entityManagerFactory) {
        this.personDAO = personDAO;
        this.entityManagerFactory = entityManagerFactory;
    }

    private PersonDAO personDAO;

    /**
     * This method handles the HTTP POST request for deleting a user from the database.
     * @param deleteUserWithThisId The ID of the user to delete from the database.
     * @param httpRequest The HTTP request sent by the user.
     * @param redirectAttributes The redirect attributes for the request.
     * @param model The model to add attributes to.
     * @param httpResponse The HTTP response sent to the user.
     * @return A redirect to the admin page.
     */
    @PostMapping()
    public String deleteUserFromDataBase(@RequestParam("userId") Integer deleteUserWithThisId,
                                         HttpServletRequest httpRequest,
                                         RedirectAttributes redirectAttributes,
                                         Model model, HttpServletResponse httpResponse){
        // Recieve the selected user_id.
        deletePersonById(deleteUserWithThisId);
        return "redirect:admin";
    }


    //--------------------------------------------------------------------------------------
    // Useful functions


    private EntityManagerFactory entityManagerFactory;

    /**
     * This method deletes a person with a given ID from the database.
     * @param userId The ID of the person to delete.
     */
    public void deletePersonById(Integer userId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // Get the person that has the personID selected.
        Person personToBeRemoved = personDAO.findPersonByUserId(userId);
        // Check if there are any games linked to this personID
        // After, check the player linked to this personID
        try {
            entityManager.getTransaction().begin();
            Person personToBeRemovedButMerged = entityManager.merge(personToBeRemoved);
            entityManager.remove(personToBeRemovedButMerged);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
