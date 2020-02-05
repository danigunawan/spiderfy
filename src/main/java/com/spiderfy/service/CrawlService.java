package com.spiderfy.service;

import com.spiderfy.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
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


@Component
public class CrawlService {

    static WebDriver driver;

    private final String SITEMAP_FILE_NAME = "/sitemap.xml";

    public UrlModelResponse getLinks(String url) throws IOException {

        UrlModelResponse response = new UrlModelResponse();
        List<UrlModel> items = new ArrayList<UrlModel>();
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
            if (!link.attr("abs:href").equals("")) {
                item.setLink(link.attr("abs:href"));
                item.setText(link.text());
                items.add(item);
            }
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        response.setElapsedTime(String.valueOf(timeElapsed) + "ms");
        response.setResults(items);


        return response;
    }

    public UrlModelResponse getLinksWithThumbnail(String url) throws IOException {
        UrlModelResponse response = new UrlModelResponse();
        List<UrlModel> items = new ArrayList<UrlModel>();
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
            if (!link.attr("abs:href").equals("")) {
                item.setLink(link.attr("abs:href"));
                item.setText(link.text());
                item.setThumbnail(takeScreenShot(item.getLink()).getBody().getResults().get(0).getImage_base64());
                items.add(item);
            }
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        response.setElapsedTime(String.valueOf(timeElapsed) + "ms");
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

        response.setElapsedTime(String.valueOf(timeElapsed) + "ms");
        response.setResults(items);
        return response;
    }

    public SitemapModelResponse defaultsitemap(String siteMapUrl) {
        SitemapModelResponse response = new SitemapModelResponse();
        List<SitemapModel> items = new ArrayList<SitemapModel>();
        long start = System.currentTimeMillis();
        Document doc = null;
        try {
            doc = Jsoup.connect(siteMapUrl + SITEMAP_FILE_NAME).get();
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

        response.setElapsedTime(String.valueOf(timeElapsed) + "ms");
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
            if (!link.attr("loc").equals("")) {
                item.setLoc(link.attr("loc"));
                item.setText(link.text());
                items.add(item);
            }
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        response.setElapsedTime(String.valueOf(timeElapsed) + "ms");
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
            if (!img.attr("abs:src").equals("")) {
                item.setSrc(img.attr("abs:src"));
                item.setAlt(img.attr("alt"));
                item.setWidth(img.attr("width"));
                item.setHeight(img.attr("height"));

                items.add(item);
            }
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        response.setElapsedTime(String.valueOf(timeElapsed) + "ms");
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

            response.setElapsedTime(String.valueOf(timeElapsed) + "ms");
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


            return ResponseEntity.status(HttpStatus.OK).body("User-Agent is changed. User-agent:" + userAgents[rand_index] + "\n" + "body:" + doc.body().text());

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
        try {

            long start = System.currentTimeMillis();
            ScreenShootModelResponse response = new ScreenShootModelResponse();
            List<ScreenShootModel> items = new ArrayList<ScreenShootModel>();
            ScreenShootModel item = new ScreenShootModel();
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.get(url);
            String base64Data = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            item.setUrl(url);
            item.setImage_base64("data:image/png;base64," + base64Data);
            items.add(item);
            driver.quit();
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;

            response.setElapsedTime(String.valueOf(timeElapsed) + "ms");
            response.setResults(items);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ScreenShootModelResponse());
    }


    public RssFeedModelResponse getRssFeed(String rssUrl) {


        RssFeedModelResponse response = new RssFeedModelResponse();
        List<RssFeedModel> items = new ArrayList<RssFeedModel>();
        long start = System.currentTimeMillis();
        Document doc = null;
        try {
            doc = Jsoup.connect(rssUrl).get();


        } catch (IOException e) {
            e.printStackTrace();
            return new RssFeedModelResponse();
        }

        try {
            Elements links = doc.select("item");
            for (Element link : links) {

                RssFeedModel item = new RssFeedModel();
                if (!(link.select("link").text()).equals("")) {
                    item.setGuid(link.select("guid").text());
                    item.setLink(link.select("link").text());
                    item.setDescription(link.select("description").text());
                    item.setTitle(link.select("title").text());
                    item.setPublishDate(link.select("pubDate").text());
                    item.setRelatedImage(link.select("enclosure").attr("url"));
                    items.add(item);
                }
            }
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;

            response.setNumberOfRecords(items.size());
            response.setResource(rssUrl);
            response.setElapsedTime(String.valueOf(timeElapsed) + "ms");
            response.setResults(items);

            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new RssFeedModelResponse();
        }
    }


    public List<RssFeedModelResponse> getAllRssFeed(Integer offset, Integer numberOfNewsSite) {
        List<RssFeedModelResponse> response = new ArrayList<RssFeedModelResponse>();


        String[] rssList = {
                "http://www.anayurtgazetesi.com/sondakika.xml",
                "http://www.balkangunlugu.com/feed",
                "https://www.birgun.net/feed",
                "http://www.cumhuriyet.com.tr/rss/son_dakika.xml",
                "https://www.dunya.com/rss",
                "http://www.evrensel.net/rss/haber.xml",
                "http://www.gercekgundem.com/rss",
                "http://www.hurriyet.com.tr/rss/anasayfa",
                "http://www.ilk-kursun.com/feed",
                "http://www.milatgazetesi.com/rss.php",
                "http://www.milliyet.com.tr/rss/rssNew/gundemRss.xml",
                "http://www.oncevatan.com.tr/rss.php",
                "https://www.sabah.com.tr/rss/anasayfa.xml",
                "http://www.stargazete.com/rss/rss.asp",
                "https://www.takvim.com.tr/rss/anasayfa.xml",
                "http://www.turkiyegazetesi.com.tr/rss/rss.xml",
                "https://www.yeniakit.com.tr/rss/haber/gundem",
                "http://www.yenicaggazetesi.com.tr/rss",
                "http://www.yenimesaj.com.tr/rss.php",
                "http://yenisafak.com.tr/Rss", "http://www.yurtgazetesi.com.tr/rss.php",
                "http://www.fotomac.com.tr/rss/anasayfa.xml",
                "http://www.ahaber.com.tr/rss/AnaSayfa.xml",
                "http://www.bloomberght.com/rss",
                "http://www.cnnturk.com/feed/rss/news",
                "http://feeds.feedburner.com/euronews/tr/home",
                "http://www.haberturk.com/rss",
                "https://www.ntv.com.tr/gundem.rss",
                "http://www.tgrthaber.com.tr/feed/sondakika/index.rss",
                "http://www.trt.net.tr/rss/gundem.rss",
                "http://www.ulusalkanal.com.tr/rss.php",
                "http://www.acunmagazin.com/feeds/posts/default"
                , "http://www.acikgazete.com/feed"
                , "http://www.ajanshaber.com/rss"
                , "http://www.aktifhaber.com/rss"
                , "http://aljazeera.com.tr/rss.xml"
                , "http://feeds.feedburner.com/amerikabulteni"
                , "http://www.ankarareview.com/feed"
                , "https://www.aydinbuyuksehir.com/rss.xml"
                , "https://www.aydinpost.com/rss/"
                , "http://www.sesgazetesi.com.tr/rss"
                , "http://www.aygazete.com/rss/gundem-haberleri"
                , "http://www.basnews.com/index.php/tr/detail/content/195-basnews-tr?format=feed&type=rss"
                , "http://www.baskahaber.org/feeds/posts/default"
                , "http://www.bbc.co.uk/turkce/index.xml"
                , "http://www.bianet.org/bianet.rss"
                , "http://www.canlihaber.com/rss/"
                , "http://www.dipnot.tv/feed/"
                , "http://www.diken.com.tr/feed/"
                , "http://rss.dw.com/rdf/rss-tur-all"
                , "http://www.ensonhaber.com/rss/ensonhaber.xml"
                , "http://www.eurovizyon.co.uk/rss.php"
                , "http://www.f5haber.com/rss/haberler.xml"
                , "https://www.futbolsayfasi.net/feed"
                , "http://www.gazeteduvar.com.tr/feed"
                , "http://gazetekarinca.com/feed"
                , "https://www.gazetenehaber.com/rss"
                , "http://www.gazeteci.tv/rss.xml"
                , "http://www.gazeteciler.com/sondakika.rss"
                , "https://www.gencduyu.com/rss"
                , "http://www.gezipress.com/rss.xml"
                , "http://www.girisimhaber.com/rss.xml"
                , "http://grihat.com.tr/rss/"
                , "http://www.haberegit.net/feed"
                , "http://www.haberdar.com/rss"
                , "http://www.haberasir.com/rss.xml"
                , "http://www.haberettik.com/rss"
                , "http://www.haberkulesi.com/rss.asp"
                , "http://www.haberturk.com/rss"
                , "http://www.haberyudum.com/rss"
                , "http://www.habervaktim.com/sondakika.xml"
                , "http://rss.haberler.com/rss.asp?kategori=sondakika"
                , "http://www.haberx.com/haberx.rss"
                , "http://sondakika.haber7.com/sondakika.rss"
                , "http://www.halkinhabercisi.com/feed"
                , "http://ilerihaber.org/rss.xml"
                , "http://inadinahaber.org/feed"
                , "http://www.internethaber.com/rss/last_min.xml"
                , "http://www.kampushaber.com/rss.xml"
                , "http://www.karsigazete.com.tr/rss.php"
                , "http://kayseriolay.com/rss.php"
                , "http://www.mansethaber.com/rss.xml"
                , "http://www.medyapusula.com/rss.xml"
                , "https://seninmedyan.org/feed"
                , "http://millirefleks.com/rss.xml"
                , "http://www.mynet.com/haber/rss/son-dakika"
                , "http://www.nationalturk.com/feed"
                , "https://www.newstr.net/feed/"
                , "http://www.objektifhaber.com/sondakika.rss"
                , "http://www.odatv.com/rss.php"
                , "http://www.pirha.net/feed"
                , "http://platform24.org/rss"
                , "http://politikkedi.com/feed"
                , "http://www.presshaber.com/feed"
                , "http://www.pressturk.com/rss.xml"
                , "http://rss.sondakika.com/rss_standart.asp"
                , "http://www.sonsayfa.com/rss.xml"
                , "https://https://www.sondakikaizmir.com/feed"
                , "http://sonsoz.com.tr/feed/"
                , "http://www.spothaber.com/rss"
                , "http://tr.sputniknews.com/export/rss2/archive/index.xml"
                , "https://superkulup.com/feed"
                , "http://www.taraftarhaber.com/rss.xml"
                , "http://ticarihaber.net/feed"
                , "http://www.turizmekstra.com/rss.xml"
                , "http://turansesi.com/rss.xml"
                , "http://www.turkiyehaberajansi.com/rss.xml"
                , "http://www.urfanatik.com/sitemap.xml"
                , "http://www.ulkehaber.com/rss/sondakika.xml"
                , "http://www.gazetevatan.com/rss/gundem.xml"
                , "http://www.ydh.com.tr/rss.xml"
                , "http://www.yakinplan.com/feed"
                , "http://yenidunya.org/rss.xml"
                , "http://yeniikdam.com/rss.xml"
        };

        try {

            for (String rssFeed : java.util.Arrays.copyOfRange(rssList, offset, numberOfNewsSite)) {
                System.out.println("Start : " + rssFeed);

                RssFeedModelResponse item;


                item = getRssFeed(rssFeed);
                response.add(item);

                System.out.println("Done : " + rssFeed);
            }

            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }




}







