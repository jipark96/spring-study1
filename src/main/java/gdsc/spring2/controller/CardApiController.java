package gdsc.spring2.controller;

import gdsc.spring2.DTO.StoreDTO;
import gdsc.spring2.entity.Store;
import gdsc.spring2.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CardApiController {
    private final StoreRepository storeRepository;

    @GetMapping("cardApi")
    public Long cardApiParsing() throws ParseException {

        // https://api.odcloud.kr/api/15088598/v1/uddi:f9e392fd-09bb-4812-b065-d6529bd4ce60
        // ?page=1
        // &perPage=10
        // &serviceKey=W1JyGAIPvtZh8YdGpcrGcoqTK2KecXzHCYDZzE3iQ5SDWwZL35oD6Xw4UsVOU9mChW0VypRaQBMFrlbyj%2Bkx8Q%3D%3D

        String serviceKey ="=W1JyGAIPvtZh8YdGpcrGcoqTK2KecXzHCYDZzE3iQ5SDWwZL35oD6Xw4UsVOU9mChW0VypRaQBMFrlbyj%2Bkx8Q%3D%3D";
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
            connection.setRequestMethod("GET");
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

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
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
        return 1L;
    }
}
