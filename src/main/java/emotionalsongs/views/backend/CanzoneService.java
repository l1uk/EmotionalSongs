package emotionalsongs.views.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanzoneService {
    @Autowired
    private final CanzoneRepository songRepository;

    public CanzoneService(CanzoneRepository songRepository) {
        this.songRepository = songRepository;
    }

    public long countCanzoni() {
        return songRepository.count();
    }

    public List<Canzone> findAllCanzoni(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return songRepository.findAll();
        } else {
            return songRepository.search(stringFilter);
        }
    }

    public void deleteCanzone(Canzone canzone) {
        songRepository.delete(canzone);
    }

    public void saveCanzone(Canzone canzone) {
        if (canzone == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        songRepository.save(canzone);
    }
}
