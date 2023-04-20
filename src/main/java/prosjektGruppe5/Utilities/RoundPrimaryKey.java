package prosjektGruppe5.Utilities;

import prosjektGruppe5.Entities.Game;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Entities.Player;

import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.List;
@Embeddable
public class RoundPrimaryKey implements Serializable {

    @OneToOne(optional = false)
    @JoinColumn(name = "playerId", nullable = false)
    private Person player;

    @OneToOne(optional = false)
    @JoinColumn(name = "gameId", nullable = false)
    private Game game;

    @Column(name = "roundId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roundId;

    //-----------------------------------------------------------------------------------
    // Constructors


    public RoundPrimaryKey() {
    }

    public RoundPrimaryKey(Person player, Game game) {
        this.player = player;
        this.game = game;
    }

    //----------------------------------------------------------------------------------
    // Useful functions


    public Person getPlayer() {
        return player;
    }

    public void setPlayer(Person player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}



