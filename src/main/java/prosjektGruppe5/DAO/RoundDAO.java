package prosjektGruppe5.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import prosjektGruppe5.Entities.Game;
import prosjektGruppe5.Entities.Person;
import prosjektGruppe5.Entities.Round;

import java.util.List;

public interface RoundDAO extends JpaRepository<Round, Long> {
    List<Round> findAll();

    List<Round> findRoundByPersonAndGame(Person personPlayingTheGame, Game gameBeingPlayed);
}
