package repackgamesbydoni.donigames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repackgamesbydoni.donigames.company.Game;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface GameRepository extends JpaRepository<Game,Long> {
}
