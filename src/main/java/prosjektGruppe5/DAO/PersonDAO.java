package prosjektGruppe5.DAO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import prosjektGruppe5.Entities.Person;

import java.util.List;

public interface PersonDAO extends JpaRepository<Person, Long>  {
    @NotNull List<Person> findAll();
    Person findPersonByuserName(String userName);
    Person findPersonByemail(String email);
    Person findPersonByUserId(Integer userId);
}


