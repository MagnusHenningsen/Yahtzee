package prosjektGruppe5.DAOTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import prosjektGruppe5.DAO.PersonDAO;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Utilities.EncryptionUtil;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonDAOTest {

    private PersonDAO personDAO;
    private Person testBruker;

    private EncryptionUtil eu = new EncryptionUtil();

    @BeforeEach
    void setUp() {
        // Opprett en mock-instans av BrukerDAO
        personDAO = Mockito.mock(PersonDAO.class);

        // Opprett testbrukeren
        testBruker = new Person();
        testBruker.setFullName("Test Testerson");
        testBruker.setEmail("example@gmail.com");
        testBruker.setUserName("Brukernavn");
        String salt = eu.getSalt();
        String hashedPass = eu.hashPassword("password", salt);
        testBruker.setPaswordHash(hashedPass);
        testBruker.setSalt(salt);


        // Definer oppførselen til mock-objektet
        when(personDAO.findPersonByuserName("Brukernavn")).thenReturn(testBruker);
        when(personDAO.findAll()).thenReturn(Collections.singletonList(testBruker));
    }

    @Test
    public void testFindByBrukernavn() {
        // Kall metoden du vil teste
        Person foundBruker = personDAO.findPersonByuserName("Brukernavn");

        // Verifiser at mock-objektet ble kalt, og kontroller eventuelle forhold du trenger
        verify(personDAO, times(1)).findPersonByuserName("Brukernavn");
        assertNotNull(foundBruker);
        assertEquals(testBruker, foundBruker);
    }

    @Test
    public void testFindAll() {
        // Kall metoden du vil teste
        List<Person> brukere = personDAO.findAll();

        // Verifiser at mock-objektet ble kalt, og kontroller eventuelle forhold du trenger
        verify(personDAO, times(1)).findAll();
        assertNotNull(brukere);
        assertTrue(brukere.size() > 0);
    }
}
