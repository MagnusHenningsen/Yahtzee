package prosjektGruppe5.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import prosjektGruppe5.Entities.Player;
import prosjektGruppe5.Utilities.PlayerPrimaryKey;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface PlayerDAO extends JpaRepository<Player, Long> {

    @NotNull List<Player> findAll();

    List<Player> findPlayerByPlayerPrimaryKey_PersonUserId(Integer searchedPersonId);

    List<Player> findPlayerByPlayerPrimaryKey_GameGameId(Integer searchedGameId);

    Player findByPlayerPrimaryKey(PlayerPrimaryKey playerPrimaryKey);

}
