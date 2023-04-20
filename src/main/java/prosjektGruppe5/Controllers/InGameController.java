package prosjektGruppe5.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.DAO.GameDAO;
import prosjektGruppe5.DAO.PersonDAO;
import prosjektGruppe5.DAO.PlayerDAO;
import prosjektGruppe5.DAO.RoundDAO;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Entities.Player;
import prosjektGruppe5.Entities.Round;
import prosjektGruppe5.Utilities.SessionUtil;
import prosjektGruppe5.Entities.Game;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 The InGameController class is responsible for handling all HTTP requests related to the GameView page.
 (Mainly to handle the finish-round button)
 */

@Controller
@RequestMapping("/landing/game")
public class InGameController {


    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private RoundDAO roundDAO;


    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private PlayerDAO playerDAO;


    @Autowired
    private SessionUtil sessionUtil;

    /**
     This method returns the GameView page if the user is logged in. It also retrieves the list of rounds
     played from the session and adds it to the model.
     @param httpRequest the HTTP request received
     @param redirectAttributes attributes used for redirection
     @param model the model used to populate data to the view
     @return the GameView page if the user is logged in, otherwise redirects to the login page
     */
    @GetMapping()
    public String geInGameView(HttpServletRequest httpRequest, RedirectAttributes redirectAttributes, Model model) {
        boolean isLoggedIn = sessionUtil.ValidateSession(httpRequest.getSession());
        if (!isLoggedIn && httpRequest.getSession().getAttribute("username") == null) {
            redirectAttributes.addFlashAttribute("error", "This page is reserved for logged in users");
            return "redirect:/login";
        }


        // Add the calculated data to the model
        HttpSession session = httpRequest.getSession();

        List<Round> listOfRoundsPlayed = (List<Round>) session.getAttribute("listOfRoundsPlayed");
        Integer mySum = (Integer) session.getAttribute("mySum");
        Integer myBonus = (Integer) session.getAttribute("myBonus");
        Integer myTotal = (Integer) session.getAttribute("myTotal");


        session.setAttribute("listOfRoundsPlayed", listOfRoundsPlayed);
        model.addAttribute("listOfRoundsPlayed", listOfRoundsPlayed);

        session.setAttribute("myTotal",myTotal);
        model.addAttribute("myTotal",myTotal);
        session.setAttribute("myBonus",myBonus);
        model.addAttribute("myBonus",myBonus);
        session.setAttribute("mySum",mySum);
        model.addAttribute("mySum",mySum);

        return "GameView";  // If its not logged in redirect, else gameview
    }

    /**
     This method registers the user as a player in the selected game and redirects to the GameView page.
     @param gameId the ID of the game to register the player in
     @param httpRequest the HTTP request received
     @param redirectAttributes attributes used for redirection
     @param model the model used to populate data to the view
     @param httpResponse the HTTP response to send
     @return the GameView page
     */
    @PostMapping()
    public String showGameView(@RequestParam("gameId") Integer gameId, HttpServletRequest httpRequest, RedirectAttributes redirectAttributes, Model model, HttpServletResponse httpResponse)
    {

        HttpSession session = httpRequest.getSession();
        String currentUser = session.getAttribute("username").toString();
        Person currentPerson = personDAO.findPersonByuserName(currentUser);
        Game currentGame = gameDAO.findGameByGameId(gameId);

        createPlayer(currentPerson,currentGame); //Creates a player if a player doesnt exists already

        //After registration, load pages

        List<Player> playersInThisGame = playerDAO.findPlayerByPlayerPrimaryKey_GameGameId(gameId); //Check what are the players currently in that game

        //Do i have rounds already played in this game?
        List<Round> listOfMyPlayedRounds = roundDAO.findRoundByPersonAndGame(currentPerson,currentGame);


        //Update Session:
        // Update Session information
        session.setAttribute("playersInThisGame",playersInThisGame);
        model.addAttribute("playersInThisGame",playersInThisGame);

        session.setAttribute("currentGame",currentGame.getGameId());
        model.addAttribute("currentGame",currentGame.getGameId());

        session.setAttribute("listOfRoundsPlayed",listOfMyPlayedRounds);
        model.addAttribute("listOfRoundsPlayed",listOfMyPlayedRounds);

        return "GameView";
    }

    //-----------------------------------------------------------------------------------'
    // Useful Functions


    /**
     This method checks if the player being added already exists in the database.
     @param playerToBeAdded the player to check for
     @return true if the player exists, false otherwise
     */
    public boolean doesPlayerExistsAlready(Player playerToBeAdded)
    {
        Player playerGameList = playerDAO.findByPlayerPrimaryKey(playerToBeAdded.getPlayerPrimaryKey());
        if (playerGameList == null)
            return false;
        else
            return true;
    }
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    public void createPlayer(Person currentPerson,Game game) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Player playerToBeAdded = new Player(currentPerson,game);

        //Check if the Player already exists
        if(!doesPlayerExistsAlready(playerToBeAdded))//If not, register player in this specific game
        {
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(playerToBeAdded);
                entityManager.getTransaction().commit();
            } finally {
                entityManager.close();

            }
        }
    }
}
