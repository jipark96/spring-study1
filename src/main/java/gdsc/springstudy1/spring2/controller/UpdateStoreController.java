package gdsc.springstudy1.spring2.controller;

import gdsc.springstudy1.spring2.DTO.UpdateStoreNameDTO;
import gdsc.springstudy1.spring2.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UpdateStoreController {

    /*
    Restful API
        -GET : 값을 가져올 때
        -POST : 값을 넣을 때
        -PUT : 값을 수정할 때(전부)
        -DELETE : 값을 삭제할 때
        -PATCH : 값을 수정할 때(일부분)
     */

    private final StoreService storeService;

    @PutMapping("/store/{id}") // /api/store/{id}
    public Long updateStore(@PathVariable Long id,
                            @RequestBody UpdateStoreNameDTO updateStoreNameDTO) {
        return storeService.updateStore(id, updateStoreNameDTO);
    }
}
