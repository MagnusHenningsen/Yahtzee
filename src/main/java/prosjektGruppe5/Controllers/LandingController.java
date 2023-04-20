package prosjektGruppe5.Controllers;
import org.apache.catalina.User;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.DAO.GameDAO;
import prosjektGruppe5.DAO.PersonDAO;
import prosjektGruppe5.DAO.RoundDAO;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Entities.Round;
import prosjektGruppe5.Utilities.SessionUtil;
import prosjektGruppe5.Entities.Game;
import prosjektGruppe5.Utilities.gameStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class represents the controller for the landing page of the application. It handles requests and responses
 * for the landing page, including creating a new game and displaying the list of available games.
 */
@Controller
@RequestMapping("/landing")
public class LandingController {



    //Trenger til Testen -Vebjørn
    @Autowired
    public LandingController(EntityManagerFactory emf, SessionUtil sessionUtil, GameDAO gameDAO) {
    this.emf = emf;
    this.sessionUtil = sessionUtil;
    this.gameDAO = gameDAO;
}


    /**
     * The session utility object used to manage sessions.
     */
   // @Autowired
    private SessionUtil sessionUtil;

    /**
     * The data access object for accessing the database of users.
     */
    @Autowired
    private PersonDAO personDAO;

    /**
     * The data access object for accessing the database of games.
     */
    // @Autowired
    private GameDAO gameDAO;

    /**
     * The current status of the game.
     */
    private gameStatus statusOfTheGame;

    /**
     * The properties object used to store admin username.
     */
    private final Properties properties = new Properties();

    /**
     * The admin username obtained from the properties file.
     */
    String adminUsername = properties.getProperty("admin.username");

    /**
     * Displays the landing view page.
     *
     * @param httpRequest the HTTP request object.
     * @param redirectAttributes the redirect attributes object.
     * @param model the model object.
     * @param httpResponse the HTTP response object.
     * @return the name of the landing view page.
     */

    @GetMapping()
    public String showLandingView(HttpServletRequest httpRequest, RedirectAttributes redirectAttributes,
                                  Model model, HttpServletResponse httpResponse)  {
        if (sessionUtil.ValidateSession(httpRequest.getSession())) {
            HttpSession session = httpRequest.getSession();
            Person loggedInUser = (Person) session.getAttribute("user");
            List<Game> listOfGames = gameDAO.findAll();
            session.setAttribute("game",listOfGames);
            model.addAttribute("game",listOfGames);
            return "LandingView";
        } else {
            redirectAttributes.addFlashAttribute("error", "This page is only available for logged in users");
            return "redirect:/login";
        }
    }

    /**
     * Creates a new game in the database.
     *
     * @param adminUsername the username of the admin user for the new game.
     * @param httpRequest the HTTP request object.
     * @param redirectAttributes the redirect attributes object.
     * @param model the model object.
     * @param httpResponse the HTTP response object.
     * @throws IOException if an I/O error occurs.
     * @return the name of the landing view page.
     */
    @PostMapping()
    public String createNewGameInDatabase(@RequestParam("adminId") String adminUsername, HttpServletRequest httpRequest,
                                          RedirectAttributes redirectAttributes, Model model, HttpServletResponse httpResponse)
            throws IOException {
        Game newGame = new Game();
        Person gameAdmin = personDAO.findPersonByuserName(adminUsername); // Remember test123 is the admin
        newGame.setAdminId(gameAdmin);
        newGame.setGameStatus(String.valueOf(statusOfTheGame.VENTER_PÅ_SPILLERE));
        System.out.println("Admind ID: "+adminUsername);
        System.out.println("gameAdmin is null" + (gameAdmin == null));
        createGame(newGame);
        // Update Session information
        List<Game> listOfGames = gameDAO.findGameByGameStatus(String.valueOf(statusOfTheGame.VENTER_PÅ_SPILLERE));
        HttpSession session = httpRequest.getSession();
        session.setAttribute("game",listOfGames);
        model.addAttribute("game",listOfGames);

        session.setAttribute("listOfRoundsPlayed",null);
        model.addAttribute("listOfRoundsPlayed",null);

        return "redirect:/landing";
    }

    /**
     * Creates a new game in the database.
     *
     * @param newGame the new game to be created.
     */

    private EntityManagerFactory emf;
    public void createGame(Game newGame) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            entityManager.persist(newGame);
            tx.commit();
        } finally {
            entityManager.close();
        }
    }
}
