package prosjektGruppe5.Controllers;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.DAO.GameDAO;
import prosjektGruppe5.DAO.PersonDAO;
import prosjektGruppe5.Entities.Game;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Utilities.EncryptionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 The LoginController class is responsible for handling the login process.
 This controller consists of two methods: a GET method and a POST method.
 The GET method is responsible for displaying the login page while the POST method is responsible for processing the user's login request.
 */

@Controller
@RequestMapping({"/login", "/"})
public class LogInController {

    private EncryptionUtil encryptionUtil;

    private PersonDAO personDAO;


    private GameDAO gameDAO;

    private int max_inactive = 900;
    @Autowired
    public LogInController(EncryptionUtil encryptionUtil, PersonDAO personDAO, GameDAO gameDAO) {
        this.encryptionUtil = encryptionUtil;
        this.personDAO = personDAO;
        this.gameDAO = gameDAO;
    }
    /**
     The getLoginView method is a GET method that displays the login page.
     If there are no registered users, the method redirects the user to the RegisterView page.
     If the user is already logged in, the method logs them out first before displaying the login page.
     @param httpRequest the HttpServletRequest object
     @return the name of the view to be displayed (i.e., LoginView or RegisterView)
     */
    @GetMapping()
    public String getLoginView(HttpServletRequest httpRequest) {
        signOut(httpRequest.getSession());
        if(personDAO.findAll().size() == 0)
        {
            return "RegisterView";
        }
        return "LoginView";
    }

    /**
     The logIn method is a POST method that processes the user's login request.
     If the username and password entered by the user are valid, the method logs the user in and redirects them to the LandingView page.
     If the username and password entered by the user are invalid, the method displays an error message and redirects the user to the login page.
     @param userName the username entered by the user
     @param passwordPrehash the password entered by the user
     @param adminInfo the admin information entered by the user
     @param httpRequest the HttpServletRequest object
     @param model the Model object
     @param redirectAttributes the RedirectAttributes object
     @return the name of the view to be displayed (i.e., LoginView or LandingView)
     */
    @PostMapping("")
    public String logIn(@RequestParam("username") String userName ,
                        @RequestParam("password") String passwordPrehash,@RequestParam("adminInfo") String adminInfo,
                        HttpServletRequest httpRequest,
                        Model model, RedirectAttributes redirectAttributes) {

        Person personFound = personDAO.findPersonByuserName(userName);

       if(userIsRegistered(personFound,passwordPrehash))
       {
            logIn(httpRequest,max_inactive,personFound);
            model.addAttribute("username",userName);
            httpRequest.getSession().setAttribute("adminUsername",adminInfo);
            model.addAttribute("adminUsername",adminInfo);
            return "redirect:landing";

       }
        redirectAttributes.addFlashAttribute("error", "Log In Invalid");
        return "redirect:login";
    }

//--------------------------------------------------------------------------------
// Useful functions

    /**
     The userIsRegistered method checks if the user is registered in the database.
     @param personToCheck the person to check if registered in the database
     @param passwordPosted the password entered by the user
     @return true if the user is registered in the database and the password entered is correct, false otherwise
     */
    public boolean userIsRegistered(Person personToCheck, String passwordPosted)
    {
        if((personToCheck != null
                &&
                (personToCheck.getPaswordHash().equals(encryptionUtil.hashPassword(passwordPosted, personToCheck.getSalt())))))
            return true;
        else
            return false;
    }

    /**
     The signOut method logs the user out by invalidating the current session.
     @param httpSession the HttpSession object representing the user's session
     */
    public void signOut(HttpSession httpSession) {httpSession.invalidate();}

    public void logIn(HttpServletRequest httpRequest, int max_inactive, Person personToLogIn)
    {
        HttpSession session = httpRequest.getSession();
        displayAllGamesPage(session,max_inactive,personToLogIn);
    }

    public void displayAllGamesPage(HttpSession session,int max_inactive,Person personToLogIn){
        List<Game> listOfGamesRegisteredInThisUser = gameDAO.findAll();
        session.setAttribute("game", listOfGamesRegisteredInThisUser);
        listOfGamesRegisteredInThisUser.forEach(game -> {
            System.out.println(game.toString());
        });
        session.setAttribute("user", personToLogIn);
        session.setAttribute("username", personToLogIn.getUserName());
        session.setMaxInactiveInterval(max_inactive);
    }


}
