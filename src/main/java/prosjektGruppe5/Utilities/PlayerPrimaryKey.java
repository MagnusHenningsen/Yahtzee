package prosjektGruppe5.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import prosjektGruppe5.Entities.Game;
import prosjektGruppe5.Entities.Person;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Embeddable
public class PlayerPrimaryKey implements Serializable {


    @ManyToOne
    @JoinColumn(name = "playerid", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "gameid")
    private Game game;

    // Add constructors, getters, and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerPrimaryKey playerId = (PlayerPrimaryKey) o;
        return Objects.equals(person, playerId.person) &&
                Objects.equals(game, playerId.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, game);
    }

    //--------------------------------------------------------------------------
    // Constructors

    public PlayerPrimaryKey() {
    }

    public PlayerPrimaryKey(Person person, Game game) {
        this.person = person;
        this.game = game;
    }

    //-----------------------------------------------------------------------------
    public Integer getPersonId() {
        return person.getUserId();
    }
    public Person getPerson() {
        return person;
    }
    public Integer getGameId() {
        return game.getGameId();
    }
    public Game getGame() {
        return game;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}

