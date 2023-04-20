package prosjektGruppe5.Entities;

import prosjektGruppe5.Utilities.PlayerPrimaryKey;
import prosjektGruppe5.Utilities.RoundPrimaryKey;

import javax.persistence.*;

@Entity
@Table(name = "Round", schema="dat109p1")
public class Round {
    @Id
    @Column(name = "roundid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roundId;

    @ManyToOne
    @JoinColumn(name = "playerid", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "gameid")
    private Game game;

    private Integer points;

    //----------------------------------------------------------------------------
    // Constructors
    public Round() {
    }

    public Round(Person person, Game game, Integer points) {
        this.person = person;
        this.game = game;
        this.points = points;
    }

    //------------------------------------------------------------------------------
    // Functions

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
