package repackgamesbydoni.donigames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repackgamesbydoni.donigames.model.Roles;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Roles,Long> {

    Roles findByRole(String role);
}
