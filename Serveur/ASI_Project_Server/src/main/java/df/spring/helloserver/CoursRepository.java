package df.spring.helloserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "cours", path = "cours")
public interface CoursRepository extends JpaRepository<Cours,Integer> {

    Cours findById(@Param("id") int id);
    Cours findCoursById(@Param("id") int id);

    Cours findBySport(@Param("sport") String sport);
    List<Cours> findByProfId(@Param("id") int id);
    // http://localhost:8080/cours/search/findByProfId?id=2

}
