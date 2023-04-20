package prosjektGruppe5.ControllerTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prosjektGruppe5.Controllers.RegisterController;
import prosjektGruppe5.Entities.Person;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class testRegisterController {


    @InjectMocks
    private RegisterController reg;





    @Test
    public void testGetRegisterView() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        Model model = mock(Model.class);
        Assertions.assertEquals("RegisterView", reg.showRegisterView(req,model));
    }



    @Test
    public void testRegisterUser(){
        Model model = mock(Model.class);
        RedirectAttributes ra = mock(RedirectAttributes.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletRequest req2 = mock(HttpServletRequest.class);
        RegisterController reg = mock(RegisterController.class);




        when(reg.register("sofjeld","123","Sondre Fjeld","sofjeld123@gmail.com", req, ra)).thenReturn("redirect:register");
        when(reg.register("sofjeld1","Passord1!","Sondre Fjeld","sofjeld123@gmail.com", req2, ra)).thenReturn("redirect:login");

        when(reg.isValidUsername("sofjeld")).thenReturn(true);
        when(reg.isValidPassword("123")).thenReturn(false);
        when(reg.isValidUsername("sofjeld1")).thenReturn(true);
        when(reg.isValidPassword("Passord1!")).thenReturn(true);

        //valid username, unvalid password
        Assertions.assertEquals("redirect:register", reg.register("sofjeld","123","Sondre Fjeld","sofjeld123@gmail.com", req,  ra));
       //valid username, valid password
        Assertions.assertEquals("redirect:login", reg.register("sofjeld1","Passord1!","Sondre Fjeld","sofjeld123@gmail.com", req2, ra));


        //Vil teste at du går tilbake til register når brukernavn og email allerede finnes -Vebjørn
        reg.register("sofjeld1","Passord1!","Sondre Fjeld","sofjeld123@gmail.com", req2, ra);
        when(reg.register("sofjeld1","Passord1!","Sondre Fjeld","sofjeld123@gmail.com", req2, ra)).thenReturn("redirect:register");



    }
}
