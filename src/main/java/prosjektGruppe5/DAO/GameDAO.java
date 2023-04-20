package prosjektGruppe5.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import prosjektGruppe5.Entities.Game;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface GameDAO extends JpaRepository<Game, Long>{

    List<Game> findAll();

    List<Game> findGameByAdminId(Integer adminId);

    List<Game> findGameByGameStatus(String gameStatus);

    Game findGameByGameId(Integer gameId);




}
