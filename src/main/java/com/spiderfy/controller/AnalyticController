package com.spiderfy.controller;
import io.swagger.annotations.ApiOperation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AnalyticController {

    @RequestMapping(path = "/analytic/alexa/turkeytop50/", method = RequestMethod.GET)
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

    @RequestMapping(path = "/analytic/similarweb/turkeytop50/", method = RequestMethod.GET)
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
