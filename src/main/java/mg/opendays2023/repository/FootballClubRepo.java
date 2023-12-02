package mg.opendays2023.repository;

import mg.opendays2023.model.FootballClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballClubRepo extends JpaRepository<FootballClub,Long> {
}