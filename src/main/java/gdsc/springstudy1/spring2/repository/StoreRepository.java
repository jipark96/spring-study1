package gdsc.springstudy1.spring2.repository;

import gdsc.springstudy1.spring2.entity.Store;
import gdsc.springstudy1.spring2.mapping.OnlyLatLng;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {
    List<OnlyLatLng> findAllBy();
}
