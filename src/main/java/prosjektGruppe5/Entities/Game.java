package prosjektGruppe5.Entities;
import javax.persistence.*;
import prosjektGruppe5.Utilities.gameStatus;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "Game", schema = "dat109p1")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gameid")
    private int gameId;
    @Column(name = "gamestatus")
    private String gameStatus;

    //-----------------------------------------------------------------------------------
    // Relationships
    @ManyToOne
    @JoinColumn(name = "adminid")
    private Person adminId;

    //-----------------------------------------------------------------------------------
    // Constructors

    public Game() {
    }

    public Game(Person adminId, gameStatus gameStatus) {
        this.adminId = adminId;
        this.gameStatus = gameStatus.toString();
    }

    //----------------------------------------------------------------------------------------
    // Functions


    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Person getAdminId() {
        return adminId;
    }

    public void setAdminId(Person adminId) {
        this.adminId = adminId;
    }
}
