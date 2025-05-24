package ao.com.wundu.infrastructure.repository;

import ao.com.wundu.domain.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
