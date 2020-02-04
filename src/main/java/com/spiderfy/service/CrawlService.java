package com.spiderfy.service;


import com.spiderfy.model.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Component
public class CrawlService {

    @Value("${uname}")
    private String uname;

    @Value("${pw}")
    private String pw;

    static WebDriver driver;

    private final String SITEMAP_FILE_NAME="/sitemap.xml";

    public UrlModelResponse getLinks(String url) throws IOException {

        UrlModelResponse response = new UrlModelResponse();
        List<UrlModel> items = new ArrayList< UrlModel>();
        long start = System.currentTimeMillis();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("a[href]");


      for (Element link : links) {
            UrlModel item = new UrlModel();
            item.setLink(link.attr("href").startsWith("http")?link.attr("href"):(url+link.attr("href")));
            item.setText(link.text());
            items.add(item);
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        response.setElapsedTime(String.valueOf(timeElapsed)+"ms");
        response.setResults(items);



        return response;
    }

<<<<<<< HEAD
    public UrlModelResponse getAudiophileInfos(String url,String offset,String categoryId,String parentCategoryId) throws IOException {
=======
     public UrlModelResponse getAudiophileInfos(String url,String offset,String categoryId,String parentCategoryId) throws IOException {
>>>>>>> 21ebb4b3e1701ab1f5f7f1ba8d8179bac3c513c8
        RestTemplate restTemplate = new RestTemplate();
        String result="";
        Connection.Response res = Jsoup.connect("https://www.audiophile.org/GirisYap")
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

        ResponseEntity<List<AudiophileModel>>  responseAdvertList = restTemplate.exchange("https://www.audiophile" +
                ".org/Category/GetAdvertsList" , HttpMethod.POST,request , new ParameterizedTypeReference<List<AudiophileModel>>() {});

       List<AudiophileModel> resultAdvert= responseAdvertList.getBody();

        UrlModelResponse response = new UrlModelResponse();
        List<UrlModel> items = new ArrayList< UrlModel>();
        long start = System.currentTimeMillis();
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


               String subUrl = "https://www.audiophile.org/"+advert.getUrl();


               Document subdoc = Jsoup.connect(subUrl).cookies(loginCookies).get();

               Elements email = subdoc.select("#urundetay-altsayfa > div > div.urun-content > div > div.div-block-9.div-sag > div:nth-child(8) > a");
               Elements telephone= subdoc.select("#urundetay-altsayfa > div > div.urun-content > div > div.div-block-9.div-sag > div:nth-child(9) > a");
               UrlModel item = new UrlModel();
               item.setLink(subUrl);
               item.setText(email.attr("title"));
               item.setThumbnail(telephone.attr("title"));
               items.add(item);



               links.replace("tr:nth-child("+String.valueOf(index)+")","tr:nth-child(__INDEX__)");

               System.out.println("Sites crawled:"+String.valueOf(index));
               index++;
           }



        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        response.setElapsedTime(String.valueOf(timeElapsed)+"ms");
        response.setResults(items);

        return response;

    }

    public UrlModelResponse getLinksWithThumbnail(String url) throws IOException {
        UrlModelResponse response = new UrlModelResponse();
        List<UrlModel> items = new ArrayList< UrlModel>();
        long start = System.currentTimeMillis();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            UrlModel item = new UrlModel();
            item.setLink(link.attr("href").startsWith("http")?link.attr("href"):(url+link.attr("href")));
            item.setText(link.text());
            item.setThumbnail(takeScreenShot(item.getLink()).getBody().getResults().get(0).getImage_base64());
            items.add(item);
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        response.setElapsedTime(String.valueOf(timeElapsed)+"ms");
        response.setResults(items);



        return response;
    }

    public MetaTagsModelResponse getSiteInfo(String url) {
        MetaTagsModelResponse response = new MetaTagsModelResponse();
        List<MetaTagsModel> items = new ArrayList<MetaTagsModel>();
        long start = System.currentTimeMillis();

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.getElementsByTag("meta");
        for (Element link : links) {
            MetaTagsModel item = new MetaTagsModel();

            item.setName(link.attr("name"));
            item.setProperty(link.attr("property"));
            item.setContent(link.attr("content"));
            items.add(item);

        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        response.setElapsedTime(String.valueOf(timeElapsed)+"ms");
        response.setResults(items);
        return response;
    }

    public SitemapModelResponse defaultsitemap(String siteMapUrl) {
        SitemapModelResponse response = new SitemapModelResponse();
        List<SitemapModel> items = new ArrayList<SitemapModel>();
        long start = System.currentTimeMillis();
        Document doc = null;
        try {
            doc = Jsoup.connect(siteMapUrl+SITEMAP_FILE_NAME).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements links = doc.getElementsByTag("loc");
        for (Element link : links) {
            SitemapModel item = new SitemapModel();
            item.setLoc(link.attr("loc"));
            item.setText(link.text());
            items.add(item);

        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        response.setElapsedTime(String.valueOf(timeElapsed)+"ms");
        response.setResults(items);
        return response;
    }

    public SitemapModelResponse sitemap(String siteMapUrl) {
        SitemapModelResponse response = new SitemapModelResponse();
        List<SitemapModel> items = new ArrayList<SitemapModel>();
        long start = System.currentTimeMillis();
        Document doc = null;
        try {
            doc = Jsoup.connect(siteMapUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements links = doc.getElementsByTag("loc");
        for (Element link : links) {
            SitemapModel item = new SitemapModel();
            item.setLoc(link.attr("loc"));
            item.setText(link.text());
            items.add(item);

        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        response.setElapsedTime(String.valueOf(timeElapsed)+"ms");
        response.setResults(items);
        return response;
    }

    public ImageModelResponse getImages(String url) {
        ImageModelResponse response = new ImageModelResponse();
        List<ImageModel> items = new ArrayList<ImageModel>();
        long start = System.currentTimeMillis();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements imgs = doc.getElementsByTag("img");
        for (Element img : imgs) {
            ImageModel item = new ImageModel();
            item.setSrc(img.attr("src").startsWith("http")?img.attr("src"):(url+img.attr("src")));
            item.setAlt(img.attr("alt"));
            item.setWidth(img.attr("width"));
            item.setHeight(img.attr("height"));

            items.add(item);
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        response.setElapsedTime(String.valueOf(timeElapsed)+"ms");
        response.setResults(items);
        return response;
    }


    public TextModelResponse getText(String url) {
        TextModelResponse response = new TextModelResponse();
        List<TextModel> items = new ArrayList<TextModel>();
        long start = System.currentTimeMillis();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();

            String body = doc.body().text();
            String html = doc.html();
            TextModel item = new TextModel();
            item.setAllText(body);
            item.setAllHtml(html);
            items.add(item);

            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;

            response.setElapsedTime(String.valueOf(timeElapsed)+"ms");
            response.setResults(items);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }


    public ResponseEntity<String> changeUserAgent(String url) throws IOException {

        Random rand = new Random();
        InputStream inputStream = new URL("https://raw.githubusercontent.com/fatihyildizli/spiderfy/master/src/main/resources/user-agents").openStream();
        byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
        String data = new String(bdata, StandardCharsets.UTF_8);
        String[] userAgents = data.split("\n");
        //randomize
        int rand_index = rand.nextInt(userAgents.length - 1) + 1;

        Document doc = null;
        try {

           doc = Jsoup.connect(url).userAgent(userAgents[rand_index]).get();


           return ResponseEntity.status(HttpStatus.OK).body("User-Agent is changed. User-agent:"+ userAgents[rand_index] +"\n"+ "body:" +  doc.body().text());

        } catch (IOException e) {
            e.printStackTrace();
        }
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error..");
    }

    public ResponseEntity<String> getAllUserAgents() throws IOException {

        try {

            InputStream inputStream = new URL("https://raw.githubusercontent.com/fatihyildizli/spiderfy/master/src/main/resources/user-agents").openStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String data = new String(bdata, StandardCharsets.UTF_8);


            return ResponseEntity.status(HttpStatus.OK)
                    .body(data);


        } catch (IOException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error." + e.getMessage());
        }

    }


    public ResponseEntity<ScreenShootModelResponse> takeScreenShot(String url) throws IOException {
      try
      {

          long start = System.currentTimeMillis();
          ScreenShootModelResponse response = new ScreenShootModelResponse();
          List<ScreenShootModel> items = new ArrayList<ScreenShootModel>();
          ScreenShootModel item = new ScreenShootModel();
          WebDriverManager.chromedriver().setup();
          driver = new ChromeDriver();
          driver.get(url);
          String base64Data  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
          item.setUrl(url);
          item.setImage_base64("data:image/png;base64,"+base64Data);
          items.add(item);
          driver.quit();
          long finish = System.currentTimeMillis();
          long timeElapsed = finish - start;

          response.setElapsedTime(String.valueOf(timeElapsed)+"ms");
          response.setResults(items);

          return ResponseEntity.status(HttpStatus.OK).body(response);
      }
      catch (Exception ex)
      {
          ex.printStackTrace();
      }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ScreenShootModelResponse());
    }

    }







