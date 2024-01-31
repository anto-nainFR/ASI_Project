package df.spring.helloserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "utilisateur", path = "utilisateurs")
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {
    List<Utilisateur> findByNom(@Param("nom") String nom);
    Optional<Utilisateur> findById(@Param("id") int id);

    List<Utilisateur> findAll();
}
