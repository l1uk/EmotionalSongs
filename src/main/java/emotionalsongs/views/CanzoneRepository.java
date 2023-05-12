package emotionalsongs.views;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanzoneRepository extends JpaRepository<Canzone, Integer> {
    @Query("select c from Canzone c " +
            "where lower(c.titolo) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.artista) like lower(concat('%', :searchTerm, '%'))")
    List<Canzone> search(@Param("searchTerm") String searchTerm);
}
