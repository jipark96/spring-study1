package gdsc.spring2.entity;

import gdsc.spring2.DTO.StoreDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@RequiredArgsConstructor
@Table(name="location") //속성으로 테이블을 만들기
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store.id")
    private Long id;

    @Column(name = "store.name")
    private String name;
    @Column(name="store.address")
    private String address;
    @Column(name="lat")
    private Double latitude;
    @Column(name = "long")
    private Double longitude;
    @Column(name = "store.sector")
    private String sector;

    @Builder
    public Store(StoreDTO storeDTO) {
        this.id=storeDTO.getId();
        this.address=storeDTO.getAddress();
        this.name=storeDTO.getName();
        this.longitude=storeDTO.getLongitude();
        this.latitude=storeDTO.getLatitude();
        this.sector=storeDTO.getSector();
    }
}
