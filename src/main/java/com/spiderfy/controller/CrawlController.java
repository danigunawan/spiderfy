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
    @ApiOperation(value = "Obtain all links ", notes = "Ex: http://hurriyet.com.tr")
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

        return "Time elapsed:" + String.valueOf(timeElapsed) + "ms \n" + result;
    }
    @RequestMapping(path = "/crawl/metatags/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain site metatags ", notes = "Ex: http://hurriyet.com.tr")
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

    @RequestMapping(path = "/alexa/turkeytop50/", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieve Alexa top 50 Turkey sites" ,notes = "Based on: https://www.alexa.com/topsites/countries/TR")
    @ResponseBody
    public String alexaTop50() {
        long start = System.currentTimeMillis();
        String result = "";
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.alexa.com/topsites/countries/TR").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int index = 2; index <= 51; index++) {
            Elements links = doc.select("#alx-content > div > section > div.row-fluid.TopSites.Alexarest > section.page-product-content.summary > span > span > div > div > div.listings.table > div:nth-child(" + String.valueOf(index) + ") > div.td.DescriptionCell > p > a");
            for (
                    Element link : links) {
                result += ("\nSiteinfo : " + link.attr("href"));
                result += ("  link : " + link.text());
            }

        }
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;

            return "Time elapsed:" + String.valueOf(timeElapsed) + "ms \n" + result;
        }

    @RequestMapping(path = "/similarweb/turkeytop50/", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieve Similarweb top 50 Turkey sites" , notes = "Based on: https://www.similarweb.com/top-websites/turkey")
    @ResponseBody
    public String  similarWebTop50()
    {long start = System.currentTimeMillis();
    String result = "";
    Document doc = null;
        try {
        doc = Jsoup.connect("https://www.similarweb.com/top-websites/turkey").get();
    } catch (IOException e) {
        e.printStackTrace();
    }

        for (int index = 1; index <= 51; index++) {
        Elements links = doc.select("  body > div.wrapperBody--topRanking.wrapper-body.js-wrapperBody > main > div > section.topRankingSection > div > div.topWebsites-table > div.topRankingGrid > table > tbody > tr:nth-child("+String.valueOf(index)+") > td.topRankingGrid-cell.topWebsitesGrid-cellWebsite.showInMobile > div > a.topRankingGrid-title.js-tooltipTarget");

        for (
                Element link : links) {
            result += ("\nSiteinfo : " + link.attr("href"));
            result += ("  link : " + link.text());

        }

    }
    long finish = System.currentTimeMillis();
    long timeElapsed = finish - start;

            return "Time elapsed:" + String.valueOf(timeElapsed) + "ms \n" + result;
}

}






