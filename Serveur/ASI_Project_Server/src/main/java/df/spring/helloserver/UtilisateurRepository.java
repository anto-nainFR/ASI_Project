package df.spring.helloserver;

import jdk.jshell.execution.Util;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "utilisateur", path = "utilisateurs")
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {
    List<Utilisateur> findByNom(@Param("nom") String nom);
    List<Utilisateur> findByMailAndPassword(@Param("mail") String mail, @Param("password") String password);
    //http://localhost:8080/utilisateurs/search/findByMailAndPassword?mail=Antonin@gmail.com&password=password


}
