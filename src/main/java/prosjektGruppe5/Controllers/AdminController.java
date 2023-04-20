package prosjektGruppe5.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.DAO.GameDAO;
import prosjektGruppe5.DAO.PersonDAO;
import prosjektGruppe5.Entities.Game;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Utilities.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 The AdminController class handles HTTP requests related to the admin view of the application.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private GameDAO gameDAO;
    @GetMapping()
    public String showAdminView(HttpServletRequest httpRequest, RedirectAttributes redirectAttributes, Model model, HttpServletResponse httpResponse) {
        if (sessionUtil.ValidateSession(httpRequest.getSession())) {
            List<Person> listOfAllUsers = createListOfAllUsers(httpRequest.getSession());
            List<Game> listOfAllGames = createListOfAllGames(httpRequest.getSession());
            model.addAttribute("adminViewOfGames", listOfAllGames);
            model.addAttribute("adminViewOfRegisteredUsers",listOfAllUsers);
            return "AdminView";
        } else {
            redirectAttributes.addFlashAttribute("error", "This page is only available for logged in users");
            return "redirect:/login";
        }
    }
    //This function creates a list of all users registered in our database + saves it in a Model
    // so we can access in the jsp later
    public List<Person> createListOfAllUsers(HttpSession session)
    {
        List<Person> listOfRegisteredUsers = personDAO.findAll();
        session.setAttribute("adminViewOfRegisteredUsers",listOfRegisteredUsers);
        return listOfRegisteredUsers;
    }

    //This function creates a list of all games registered in our database + saves it in a Model
    // so we can access in the jsp later
    public List<Game> createListOfAllGames(HttpSession session)
    {
        List<Game> listOfRegisteredGames = gameDAO.findAll();
        session.setAttribute("adminViewOfGames",listOfRegisteredGames);
        return listOfRegisteredGames;
    }

}
