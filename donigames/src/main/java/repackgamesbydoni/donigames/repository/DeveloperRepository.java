package repackgamesbydoni.donigames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repackgamesbydoni.donigames.company.Developers;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DeveloperRepository extends JpaRepository<Developers,Long> {
}
