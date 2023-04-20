package prosjektGruppe5.Controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.DAO.GameDAO;
import prosjektGruppe5.DAO.PersonDAO;
import prosjektGruppe5.DAO.RoundDAO;
import prosjektGruppe5.Entities.Game;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Entities.Round;
import prosjektGruppe5.Utilities.PointsCalculatingUtil;
import prosjektGruppe5.Utilities.SessionUtil;
import prosjektGruppe5.Utilities.gameStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/roundController")
public class StoringTheResultController {

    @Autowired
    public StoringTheResultController(RoundDAO roundDAO, GameDAO gameDAO, PersonDAO personDAO, PointsCalculatingUtil pointsCalculatingUtil, EntityManagerFactory entityManagerFactory) {
        this.gameDAO = gameDAO;
        this.personDAO = personDAO;
        this.roundDAO = roundDAO;
        this.pointsCalculatingUtil = pointsCalculatingUtil;
        this.entityManagerFactory = entityManagerFactory;
    }
    public SessionUtil su = new SessionUtil();
    private RoundDAO roundDAO;

    private GameDAO gameDAO;

    private PersonDAO personDAO;

    private PointsCalculatingUtil pointsCalculatingUtil;

    @PostMapping()
    public String storingTheResults(@RequestParam("currentGame") Integer currentGameId, @RequestParam("diceValues") String diceValuesJson, HttpServletRequest httpRequest, RedirectAttributes redirectAttributes, Model model) {

        if (!su.ValidateSession(httpRequest.getSession())) {
            redirectAttributes.addFlashAttribute("error", "This page is only available for logged in users");
            return "redirect:/login";
        }
        //Getting the values from the JavaScript
        // Parse the JSON string back to an array of values
        int[] diceValues = new Gson().fromJson(diceValuesJson, int[].class);

        //Figuring out which Round is the player playing
        //Which Game am i a part of? currentGameId
        Game gameIAmPlaying = gameDAO.findGameByGameId(currentGameId);
        // Who am i?
        String myUserName = httpRequest.getSession().getAttribute("username").toString();
        Person myPersonInstance = personDAO.findPersonByuserName(myUserName);
        // Which round am i playing?
        Integer roundNumbers = peakAtTheAmountOfRounds(myPersonInstance, gameIAmPlaying);
        // Change gamestate if it's set to waiting
        if (gameIAmPlaying.getGameStatus().equals(gameStatus.VENTER_PÅ_SPILLERE.toString())) {
            gameIAmPlaying.setGameStatus(gameStatus.SPILL_STARTET.toString());
            gameDAO.save(gameIAmPlaying);
        }
        // Calculate the total score first (before checking if the game is over)
        if (roundNumbers == 15) {
            List<Round> listOfRoundsPlayed = roundDAO.findRoundByPersonAndGame(myPersonInstance, gameIAmPlaying);

            int sum = 0;
            for (int iterator = 0; iterator <= 5; iterator++) {
                sum = sum + listOfRoundsPlayed.get(iterator).getPoints();
            }

            int bonus = 0;
            if (sum > 63) {
                bonus = 50;
            }

            int total = 0;
            for (int iterator = 0; iterator < listOfRoundsPlayed.size(); iterator++) {
                total = total + listOfRoundsPlayed.get(iterator).getPoints();
            }

            total = total + bonus;

            HttpSession mySession = httpRequest.getSession();
            mySession.setAttribute("mySum", sum);
            model.addAttribute("mySum", sum);
            mySession.setAttribute("myBonus", bonus);
            model.addAttribute("myBonus", bonus);
            mySession.setAttribute("myTotal", total);
            model.addAttribute("myTotal", total);
        }

        // Check if the game is over and display the message
        if (roundNumbers == 15) {
            redirectAttributes.addFlashAttribute("message", "Spillet er over! :)");
            return "redirect:landing/game";
        }

        // ... (code below remains the same)

        if (roundNumbers < 15) //How many columns is my user allowed to save
        {
            int myPoints = pointsCalculatingUtil.getRightAmountOfPoints(diceValues, roundNumbers);
            Round newRoundtoBeSaved = new Round(myPersonInstance, gameIAmPlaying, myPoints);
            saveARoundInDatabase(newRoundtoBeSaved);

            List<Round> listOfRoundsPlayed = roundDAO.findRoundByPersonAndGame(myPersonInstance, gameIAmPlaying);

            HttpSession mySession = httpRequest.getSession();
            mySession.setAttribute("listOfRoundsPlayed", listOfRoundsPlayed);
            model.addAttribute("listOfRoundsPlayed", listOfRoundsPlayed);

            mySession.setAttribute("mySum", null);
            model.addAttribute("mySum", null);
            mySession.setAttribute("myBonus", null);
            model.addAttribute("myBonus", null);
            mySession.setAttribute("myTotal", null);
            model.addAttribute("myTotal", null);


        } else {
            List<Round> listOfRoundsPlayed = roundDAO.findRoundByPersonAndGame(myPersonInstance, gameIAmPlaying);

            HttpSession mySession = httpRequest.getSession();
            mySession.setAttribute("listOfRoundsPlayed", listOfRoundsPlayed);
            model.addAttribute("listOfRoundsPlayed", listOfRoundsPlayed);

            // Yahtze Rules
            int sum = 0;
            for (int iterator = 0; iterator <= 5; iterator++) {
                sum = sum + listOfRoundsPlayed.get(iterator).getPoints();
            }

            int bonus = 0;
            if (sum > 63) {
                bonus = 50;
            }

            int total = 0;
            for (int iterator = 0; iterator < listOfRoundsPlayed.size(); iterator++) {
                total = total + listOfRoundsPlayed.get(iterator).getPoints();
            }

            total = total + bonus;

            mySession.setAttribute("mySum", sum);
            model.addAttribute("mySum", sum);
            mySession.setAttribute("myBonus", bonus);
            model.addAttribute("myBonus", bonus);
            mySession.setAttribute("myTotal", total);
            model.addAttribute("myTotal", total);
        }

            return "redirect:landing/game";

        }



    private EntityManagerFactory entityManagerFactory;

    public String saveARoundInDatabase(Round roundToBeSaved) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(roundToBeSaved);
            entityManager.getTransaction().commit();
            return "Successfully added the record.";
        } catch (Exception e) {
            if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            // Check if the exception message contains the trigger constraint message
            if (e.getMessage().contains("Cannot insert more than 15 instances for the same person and game combination")) {
                return "Error: You have reached the maximum limit of 15 instances for the same person and game combination.";
            } else {
                return "Error: Unable to add the record.";
            }
        } finally {
            entityManager.close();

        }

    }


    private Integer peakAtTheAmountOfRounds(Person person,Game game)
    {
        List<Round> listOfRoundsIPlayedInThisGame = roundDAO.findRoundByPersonAndGame(person,game);
        return listOfRoundsIPlayedInThisGame.size();
    }

}