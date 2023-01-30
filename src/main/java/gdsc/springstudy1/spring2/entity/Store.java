package gdsc.springstudy1.spring2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gdsc.springstudy1.spring2.DTO.StoreDTO;
import gdsc.springstudy1.spring2.DTO.UpdateStoreNameDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity // DB에서는 행
@Getter
@ToString
@RequiredArgsConstructor
@Table // 얘네의 속성으로 테이블을 만들게
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(name = "store_name")
    private String name;

    @Column(name = "store_address")
    private String address;

    @Column(name = "store_lat")
    private Double latitude;

    @Column(name = "store_long")
    private Double longitude;

    @Column(name = "store_sector")
    private String sector;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "store")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Store(StoreDTO storeDTO) {
        this.address = storeDTO.getAddress();
        this.name = storeDTO.getName();
        this.latitude = storeDTO.getLatitude();
        this.longitude = storeDTO.getLongitude();
        this.sector = storeDTO.getSector();
    }

    public void update(UpdateStoreNameDTO updateStoreNameDTO) {
        this.name = updateStoreNameDTO.getName();
    }
}
