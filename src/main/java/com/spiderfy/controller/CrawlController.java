package com.spiderfy.controller;

import io.swagger.annotations.ApiOperation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class CrawlController {


    @RequestMapping(path = "/crawl/urls/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain all links ", notes = "Ex: https://www.lipsum.com/")
    @ResponseBody
    public String getLinks(@RequestBody String url) {
        long start = System.currentTimeMillis();
        String result = "";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("a[href]");
        for (
                Element link : links) {
            result += ("\nlink : " + link.attr("href"));
            result += ("  text : " + link.text());
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        return "Time elapsed:" + String.valueOf(timeElapsed) + "ms \n" + result;
    }
    @RequestMapping(path = "/crawl/metatags/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain site metatags ", notes = "Ex: https://www.lipsum.com/")
    @ResponseBody
    public String getSiteInfo(@RequestBody String url) {
        long start = System.currentTimeMillis();
        String result = "";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.getElementsByTag("meta");
        for (
                Element link : links) {
            result += ("\nmeta name : " + link.attr("name"));
            result += ("  property : " + link.attr("property"));
            result += ("  content : " + link.attr("content"));

        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        return "Time elapsed:" + String.valueOf(timeElapsed) + "ms \n" + result;
    }

    @RequestMapping(path = "/crawl/sitemap/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain all sitemap nodes. " ,notes = "Ex : https://www.turkcell.com.tr/sitemap.xml , http://www.hurriyet.com.tr/sitemaps/posts.xml, http://www.hurriyet.com.tr/sitemaps/posts-2020-01.xml")
    @ResponseBody
    public String sitemap(@RequestBody String siteMapUrl) {
        long start = System.currentTimeMillis();
        String result = "";
        Document doc = null;
        try {
            doc = Jsoup.connect(siteMapUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.getElementsByTag("loc");
        for (
                Element link : links) {
            result += ("\nloc : " + link.attr("loc"));
            result += ("  text : " + link.text());
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        return "Time elapsed:" + String.valueOf(timeElapsed) + "ms \n" + result;
    }


    @RequestMapping(path = "/crawl/images/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain all images", notes = "Ex: https://www.lipsum.com/")
    @ResponseBody
    public String getImages(@RequestBody String url) {
        long start = System.currentTimeMillis();
        String result = "";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements imgs = doc.getElementsByTag("img");
        for (
                Element img : imgs) {
            result += ("\nsrc : " + img.attr("src"));
            result += ("  alt : " + img.attr("alt"));
            result += ("  width : " + img.attr("width"));
            result += ("  height : " + img.attr("height"));
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        return "Time elapsed:" + String.valueOf(timeElapsed) + "ms \n" + result;
    }


}






