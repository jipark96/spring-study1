package gdsc.springstudy1.spring2.service;

import gdsc.springstudy1.spring2.DTO.StoreDTO;
import gdsc.springstudy1.spring2.entity.Store;
import gdsc.springstudy1.spring2.mapping.OnlyLatLng;
import gdsc.springstudy1.spring2.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CardService {

    private final StoreRepository storeRepository;

    // 가게 저장 메서드
    @Transactional
    public Long saveStore() {
        // https://api.odcloud.kr/api/15088598/v1/uddi:f9e392fd-09bb-4812-b065-d6529bd4ce60
        // ?page=1
        // &perPage=10
        // &serviceKey=W1JyGAIPvtZh8YdGpcrGcoqTK2KecXzHCYDZzE3iQ5SDWwZL35oD6Xw4UsVOU9mChW0VypRaQBMFrlbyj%2Bkx8Q%3D%3D

        String serviceKey ="=W1JyGAIPvtZh8YdGpcrGcoqTK2KecXzHCYDZzE3iQ5SDWwZL35oD6Xw4UsVOU9mChW0VypRaQBMFrlbyj%2Bkx8Q%3D%3D";

        StringBuilder sb =castStringBuilder(serviceKey);

        castEntity(sb);
        return 1L;
    }

    //가게 조회 메서드
    @Transactional
    public List<Store> loadAll() {
        return storeRepository.findAll();
    }

    @Transactional
    public List<OnlyLatLng> loadAllLatLng() {
        return storeRepository.findAllBy();
    }

    @Transactional
    public Long deleteAll() {
        storeRepository.deleteAll();
        return 1L;
    }

    @Transactional
    public List<Store> deleteOne(Long storeId) {
        storeRepository.deleteById(storeId);
        return storeRepository.findAll();
    }

    @Transactional
    public List<Store> deleteOne(Map<String, Long> map) {
        Long storeId = map.get("storeId");
        storeRepository.deleteById(storeId);
        return storeRepository.findAll();
    }

    // Open API StringBulder에 넣는 메서드
    private StringBuilder castStringBuilder(String serviceKey) {
        StringBuilder sb = new StringBuilder();
        JSONObject result = null;

        try {
            StringBuilder urlBuilder = new StringBuilder("https://api.odcloud.kr/api/15088598/v1/uddi:f9e392fd-09bb-4812-b065-d6529bd4ce60");
            urlBuilder.append("?");
            urlBuilder.append(URLEncoder.encode("page","UTF-8")+ "=" + URLEncoder.encode("1","UTF-8"));
            urlBuilder.append("&");
            urlBuilder.append(URLEncoder.encode("perPage","UTF-8")+ "=" + URLEncoder.encode("10","UTF-8"));
            urlBuilder.append("&");
            urlBuilder.append(URLEncoder.encode("serviceKey","UTF-8")+ serviceKey );

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            BufferedReader br;

            if(connection.getResponseCode() >=200 && connection.getResponseCode() <=300) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

            br.close();
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }

    //JSON객체를 DTO와 Entity
    private void castEntity(StringBuilder stringBuilder) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(stringBuilder.toString());
            JSONArray data = (JSONArray) jsonObject.get("data");

            for(int i=0; i< data.size(); i++) {
                JSONObject object = (JSONObject) data.get(i);

                if (object.get("위도") == null || object.get("경도") == null) {
                    continue;
                }

                String storeName = (String) object.get("가맹점명");
                String storeAddress = (String) object.get("가맹점주소");
                Double storeLat = Double.parseDouble((String) object.get("위도"));
                Double storeLong = Double.parseDouble((String) object.get("경도"));
                String storeSector = (String) object.get("업종명");

                StoreDTO storeDTO = StoreDTO.builder()
                        .name(storeName)
                        .address(storeAddress)
                        .latitude(storeLat)
                        .longitude(storeLong)
                        .sector(storeSector)
                        .build();

                Store store = Store.builder()
                        .storeDTO(storeDTO)
                        .build();

                storeRepository.save(store);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
