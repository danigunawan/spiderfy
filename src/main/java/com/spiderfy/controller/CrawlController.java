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


    @RequestMapping(path = "/crawl/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain all links ")
    @ResponseBody
    public String root(@RequestBody String url) {
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

        return  "Time elapsed:" +String.valueOf(timeElapsed) +"ms \n"+ result;
    }

    @RequestMapping(path = "/crawl/sitemap/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain all sitemap nodes. ")
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

        return  "Time elapsed:" +String.valueOf(timeElapsed) +"ms \n"+ result;
    }







}
