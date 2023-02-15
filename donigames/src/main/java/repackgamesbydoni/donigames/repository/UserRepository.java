package repackgamesbydoni.donigames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repackgamesbydoni.donigames.model.Users;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByEmail(String email);
}
