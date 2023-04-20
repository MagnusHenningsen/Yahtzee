package prosjektGruppe5.EntitiesTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import prosjektGruppe5.Entities.Game;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Entities.Player;
import prosjektGruppe5.Utilities.PlayerPrimaryKey;
import prosjektGruppe5.Utilities.gameStatus;

public class testPlayer {

    private Player player;
    private Person person;

    private Game game;

    @BeforeEach

    public void setup() {
        person = new Person();
        person.setUserName("testuser");
        person.setFullName("Test User");
        person.setPaswordHash("password123");
        person.setSalt("salt123");
        person.setEmail("testuser@example.com");

        Game game = new Game(person, gameStatus.FINISHED);

    }


        @Test

        public void testPlayer() {

            Game spill = game;

            player = new Player(person, spill);

        }

        @Test

        public void testGetPrimaryKey() {

        Game spill = game;

        player = new Player(person, spill);

        PlayerPrimaryKey pk;
            pk = player.getPlayerPrimaryKey();

        Assert.assertEquals(pk, player.getPlayerPrimaryKey());


        }

        @Test

        public void testSetPrimaryKey()  {

            Game spill = game;

            PlayerPrimaryKey pk = new PlayerPrimaryKey();

            player = new Player(person, spill);
            player.setPlayerPrimaryKey(pk);

            Assertions.assertEquals(pk, player.getPlayerPrimaryKey());


        }

        @Test

        public void testGetPerson() {
        Game spill = game;

        player = new Player(person, spill);
        PlayerPrimaryKey pk = new PlayerPrimaryKey();
        player.setPlayerPrimaryKey(pk);

        Person spiller = player.getPerson();

        Assertions.assertEquals(spiller, person);


        }

        @Test

        public void testGetGame() {


        Game spill = game;
        Player spiller = new Player(person, spill);

            PlayerPrimaryKey pk = new PlayerPrimaryKey();
            spiller.setPlayerPrimaryKey(pk);

        pk.setGame(spill);

        Assertions.assertEquals(spiller.getGame(), spill);

    }








    }


