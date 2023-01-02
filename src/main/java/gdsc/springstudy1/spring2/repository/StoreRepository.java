package gdsc.springstudy1.spring2.repository;

import gdsc.springstudy1.spring2.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {
}
