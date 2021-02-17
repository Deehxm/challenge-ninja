package townsq.challenge.ninja.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import townsq.challenge.ninja.api.model.UserModel;

import java.util.List;

public interface UserRepository extends CrudRepository<UserModel, String> {

    @Query("select u from user u where u.data like 'Usuario;%'")
    List<UserModel> findAllUsers();

    @Query("select u from user u where u.data like 'Grupo;%'")
    List<UserModel> findAllGroups();

}
