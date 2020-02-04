package com.spiderfy.service;

import com.spiderfy.model.AudiophileModel;
import com.spiderfy.model.AudiophileModelResponse;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SampleService {

    @Value("${sample_audiophile_uname}")
    private String uname;

    @Value("${sample_audiophile_pw}")
    private String pw;

    @Value("${sample_audiophile_url}")
    private String rootUrl;

    @Value("${sample_audiophile_login_url}")
    private String loginUrl;

    @Value("${sample_audiophile_api_url}")
    private String apiUrl;


    public List<AudiophileModelResponse> getAudiophileInfos(String url, String offset, String categoryId,
                                                            String parentCategoryId) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        Connection.Response res = Jsoup.connect(loginUrl)
                .data("username", uname, "password", pw)
                .method(Connection.Method.POST)
                .execute();

        Map<String, String> loginCookies = res.cookies();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("offset", offset);
        map.add("categoryId",categoryId);
        map.add("parentCategoryId",parentCategoryId);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<List<AudiophileModel>> responseAdvertList = restTemplate.exchange(apiUrl , HttpMethod.POST,request
                , new ParameterizedTypeReference<List<AudiophileModel>>() {});

        List<AudiophileModel> resultAdvert= responseAdvertList.getBody();

        List<AudiophileModelResponse> response = new ArrayList<AudiophileModelResponse>();

        Document doc = null;
        try {
            doc = Jsoup.connect(url).cookies(loginCookies).get();

        } catch (IOException e) {
            e.printStackTrace();
        }

        int index=1;
        for(AudiophileModel advert:resultAdvert)
        {


            String links="#tableCategories > tbody > tr:nth-child(__INDEX__) > td:nth-child(3) > a";
            Elements addresses=doc.select(links.replace("tr:nth-child(__INDEX__)",
                    "tr:nth-child("+String.valueOf(index)+")"));

            String subUrl = rootUrl+advert.getUrl();
            Document subDoc = Jsoup.connect(subUrl).cookies(loginCookies).get();

            Elements email = subDoc.select("#urundetay-altsayfa > div > div.urun-content > div > div.div-block-9.div-sag > div:nth-child(8) > a");
            Elements telephone= subDoc.select("#urundetay-altsayfa > div > div.urun-content > div > div.div-block-9.div-sag > div:nth-child(9) > a");
            AudiophileModelResponse item = new AudiophileModelResponse();
            item.setLink(subUrl);
            item.setEmail(email.attr("title"));
            item.setTelephone(telephone.attr("title"));
            response.add(item);



            links.replace("tr:nth-child("+String.valueOf(index)+")","tr:nth-child(__INDEX__)");

            System.out.println("Sites crawled:"+String.valueOf(index));
            index++;
        }


        return response;

    }

}
