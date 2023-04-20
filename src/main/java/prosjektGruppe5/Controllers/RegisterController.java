package prosjektGruppe5.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.DAO.PersonDAO;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Utilities.EncryptionUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private PersonDAO personDao;
    @Autowired
    private EncryptionUtil encryptionUtil;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private JavaMailSender mailSender;


    /**
     * Displays the registration view.
     * @param req the HTTP request
     * @return the name of the registration view
     */

    /* Regex criteria for creating username and password */
    @GetMapping()
    public String showRegisterView(HttpServletRequest req, Model model) {
        model.addAttribute("usernameCriteria", "Username can only contain letters and numbers and must be between 3-15 characters.");
        model.addAttribute("passwordCriteria", "Password must be between 8-64 characters and contain at least one lowercase letter, one uppercase letter, one digit, and one special character.");
        return "RegisterView";
    }


    /*
     * Registers a new user.
     * @param userName the username of the new user
     * @param passwordPrehash the unhashed password of the new user
     * @param fullName the full name of the new user
     * @param email the email of the new user
     * @param httpRequest the HTTP request
     * @param model the model
     * @param redirectAttributes the redirect attributes
     * @return the name of the login view if the user is successfully registered, the name of the registration view otherwise
     */
    @PostMapping("")
    public String register(@RequestParam("username") String userName ,
                           @RequestParam("password") String passwordPrehash,
                           @RequestParam("fullName") String fullName,
                           @RequestParam("email") String email,
                           HttpServletRequest httpRequest,
                           RedirectAttributes redirectAttributes) {
        Person newPerson = new Person();
        String salt = encryptionUtil.getSalt();
        String hashedPass = encryptionUtil.hashPassword(passwordPrehash, salt);

        newPerson.setUserName(userName);
        newPerson.setSalt(salt);
        newPerson.setPaswordHash(hashedPass);
        newPerson.setFullName(fullName);
        newPerson.setEmail(email);

        // Check if username and email are already taken
        if (personDao.findPersonByuserName(newPerson.getUserName()) == null &&
                personDao.findPersonByemail(newPerson.getEmail()) == null &&
                isValidUsername(newPerson.getUserName()) &&
                isValidPassword(passwordPrehash)) {
            createUser(newPerson);
            sendRegistrationEmail(newPerson);
            return "redirect:login";
        } else {
            if (!isValidUsername(newPerson.getUserName())) {
                redirectAttributes.addFlashAttribute("registerError", "Username can only contain letters and <br>numbers and must be between 3-15 characters.");
            } else if (!isValidPassword(passwordPrehash)) {
                redirectAttributes.addFlashAttribute("registerError", "Password must be between 8-64 characters <br>and contain at least one uppercase letter, one lowercase letter, <br>one number, and one special character.");
            } else {
                redirectAttributes.addFlashAttribute("registerError", "Username or email is already in use.");
            }
            return "redirect:register";
        }
    }



    /**
     * Checks if a username is valid according to the specified regex.
     *
     * @param username the username to check
     * @return true if the username is valid, false otherwise
     */
    public boolean isValidUsername(String username) {
        String regex = "^[a-zA-z0-9]{3,15}$";
        return Pattern.matches(regex, username);
    }

    /**
     * Checks if a password is valid according to the specified regex.
     *
     * @param password the password to check
     * @return true if the password is valid, false otherwise
     */
    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[*.!@$%^&(){}[\\]]:;<>,.?/~_+-=|\\\\])[A-Za-z\\d@$!%*?&]{8,64}$";
        return Pattern.matches(regex, password);
    }


    //-----------------------------------------------------------------------------------
    // Useful Functions


    /**
     * Creates a new user in the database.
     * @param newPerson the Person object representing the new user
     */
    public void createUser(Person newPerson) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(newPerson);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }


    /* Function that sends email to the persons. --> Does not work yet because of gmail restrictions
     */
    public void sendRegistrationEmail(Person newPerson) {
        String to = newPerson.getEmail();
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Velkommen " + newPerson.getUserName() + "!");
        msg.setText("Hei, " +newPerson.getUserName()+"\nDu er nå registrert og kan spille yahtzee.");
        mailSender.send(msg);

    }

}
