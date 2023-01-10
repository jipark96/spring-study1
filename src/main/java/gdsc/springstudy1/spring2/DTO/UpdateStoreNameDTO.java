package gdsc.springstudy1.spring2.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class UpdateStoreNameDTO {

    private String name;

    @Builder
    public UpdateStoreNameDTO(String name) {
        this.name = name;
    }

}