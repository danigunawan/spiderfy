package com.spiderfy.service;

import com.spiderfy.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrawlService {

    public UrlModelResponse getLinks(String url) {
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
            item.setLink(link.attr("href"));
            item.setText(link.text());
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

    public SitemapModelResponse sitemap(String siteMapUrl) {
        SitemapModelResponse response = new SitemapModelResponse();
        List<SitemapModel> items = new ArrayList<SitemapModel>();
        long start = System.currentTimeMillis();
        String result = "";
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
        String result = "";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements imgs = doc.getElementsByTag("img");
        for (Element img : imgs) {
            ImageModel item = new ImageModel();
            item.setSrc(img.attr("src"));
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
}
