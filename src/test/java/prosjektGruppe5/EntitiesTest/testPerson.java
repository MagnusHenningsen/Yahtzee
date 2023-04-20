package prosjektGruppe5.EntitiesTest;


import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import prosjektGruppe5.Entities.Person;

public class testPerson {



    @BeforeEach

    public void setup() {
        Person person = new Person(4, "tester", "Test User", "password123", "salt123", "testuser@example.com");

    }

    @Test

    public void testPerson() {

        Person spiller = new Person(5, "testuser", "test user", "password123", "salt123", "testUse@exampl.com");


    }

}
