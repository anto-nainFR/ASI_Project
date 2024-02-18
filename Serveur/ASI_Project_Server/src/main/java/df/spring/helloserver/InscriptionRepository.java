package df.spring.helloserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "inscription", path = "inscription")
public interface InscriptionRepository extends JpaRepository<Inscription,Integer> {

    void removeByIduserAndIdcours(@Param("iduser") int iduser, @Param("idcours") int idcours);
    List<Inscription> findInscriptionsByIdcours(@Param("idcours") int idcours);

    List<Inscription> findInscriptionsByIduser(@Param("iduser") int iduser);

    int countInscriptionByIduser(@Param("iduser") int iduser);

    int countInscriptionByIdcours(@Param("idcours") int idcours);

    int countInscriptionByIdcoursAndIduser(@Param("idcours") int idcours, @Param("iduser") int iduser);
}
