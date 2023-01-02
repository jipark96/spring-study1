package gdsc.springstudy1.spring2.controller;

import gdsc.springstudy1.spring2.entity.Store;
import gdsc.springstudy1.spring2.mapping.OnlyLatLng;
import gdsc.springstudy1.spring2.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CardApiController {
    private final CardService cardService;

    @PostMapping("/cardApi")
    public Long cardApiParsing() throws ParseException {
        return cardService.saveStore();
    }

    @GetMapping("/store/load")
    public List<Store> loadAll() {
        return cardService.loadAll();
    }

    @GetMapping("/store/load/latlng")
    public List<OnlyLatLng> loadAllLatLng() {
        return cardService.loadAllLatLng();
    }

    // 전부 다 삭제
    @DeleteMapping("/store")
    public Long deleteAll() {
        return cardService.deleteAll();
    }
    //PathValue
    @DeleteMapping("/store/{storeId}")
    public List<Store> deleteOne(@PathVariable Long storeId) {
        return cardService.deleteOne(storeId);
    }

    @DeleteMapping("/store/body")
    public List<Store> deleteOne(@RequestBody Map<String, Long> map) { //Long인지 모르면 Object
        return cardService.deleteOne(map);
    }
}
