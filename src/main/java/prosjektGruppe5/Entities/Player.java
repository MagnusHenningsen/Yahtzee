package prosjektGruppe5.Entities;
import prosjektGruppe5.Utilities.PlayerPrimaryKey;
import prosjektGruppe5.Entities.Game;
import javax.persistence.*;
import java.util.List;
import java.io.Serializable;

@Entity
@Table(name = "Player", schema = "dat109p1")
public class Player {

    @EmbeddedId
    private PlayerPrimaryKey playerPrimaryKey;

    //-----------------------------------------------------------------------------------
    // Constructors
    public Player(){};

    public Player(Person person, Game game) {
        this.playerPrimaryKey = new PlayerPrimaryKey(person,game);
    }

    //-----------------------------------------------------------------------------------
    // Functions

    public PlayerPrimaryKey getPlayerPrimaryKey() {
        return playerPrimaryKey;
    }

    public void setPlayerPrimaryKey(PlayerPrimaryKey playerPrimaryKey) {
        this.playerPrimaryKey = playerPrimaryKey;
    }

    public Person getPerson() {
        return this.playerPrimaryKey.getPerson();
    }

    public Game getGame() {
        return this.playerPrimaryKey.getGame();
    }

    public void setPerson(Person person){
        this.playerPrimaryKey.setPerson(person);
    }

    public void setGame(Game game) {
        this.playerPrimaryKey.setGame(game);
    }



    //-----------------------------------------------------------------------------------
    //Relationships

}
