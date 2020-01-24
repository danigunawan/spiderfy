package com.spiderfy.service;

import com.spiderfy.model.AnalyticModelResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnalyticService {

    private final String ALEXA_TOP_50_CSS_SELECT_QUERY="#alx-content > div > section > div.row-fluid.TopSites.Alexarest > section.page-product-content.summary > span > span > div > div > div.listings.table > div:nth-child(__INDEX__) > div.td.DescriptionCell > p > a";
    private final String ALEXA_TOP_50_URL="https://www.alexa.com/topsites/countries/TR";
    private final String SIMILARWEB_TOP_50_URL="https://www.similarweb.com/top-websites/turkey";
    private final String SIMILARWEB_TOP_50_CSS_SELECT_QUERY="  body > div.wrapperBody--topRanking.wrapper-body.js-wrapperBody > main > div > section.topRankingSection > div > div.topWebsites-table > div.topRankingGrid > table > tbody > tr:nth-child(__INDEX__) > td.topRankingGrid-cell.topWebsitesGrid-cellWebsite.showInMobile > div > a.topRankingGrid-title.js-tooltipTarget";
    private final String SIMILARWEB_INFO_PREFIX="https://www.similarweb.com";
    private final String ALEXA_INFO_PREFIX="https://www.alexa.com";

    public List<AnalyticModelResponse> getAlexaTop50()
    {
        List<AnalyticModelResponse> response= new ArrayList<AnalyticModelResponse>();

        Document doc = null;
        try {
            doc = Jsoup.connect(ALEXA_TOP_50_URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int index = 2; index <= 51; index++) {

            Elements links = doc.select(ALEXA_TOP_50_CSS_SELECT_QUERY.replace("__INDEX__",String.valueOf(index)));
            for (Element link : links) {
                AnalyticModelResponse responseItem= new AnalyticModelResponse();
                responseItem.setRank(String.valueOf(index-1));
                responseItem.setSiteInfo(link.attr("href")==null ? "null": String.format(ALEXA_INFO_PREFIX+link.attr("href")));
                responseItem.setLink(link.text()==null ?"null":link.text());
                responseItem.setFavicon(link.getElementsByTag("img").attr("src"));
                response.add(responseItem);
                ALEXA_TOP_50_CSS_SELECT_QUERY.replace(String.valueOf(index),"__INDEX__");
            }

        }
        return response;
    }

    public List<AnalyticModelResponse> getSimilarWebTop50()
    {
        List<AnalyticModelResponse> response= new ArrayList<AnalyticModelResponse>();
        Document doc = null;
        try {
            doc = Jsoup.connect(SIMILARWEB_TOP_50_URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int index = 1; index <= 51; index++) {
            Elements links = doc.select(SIMILARWEB_TOP_50_CSS_SELECT_QUERY.replace("__INDEX__",String.valueOf(index)));

            for (Element link : links) {
                AnalyticModelResponse responseItem= new AnalyticModelResponse();

                responseItem.setRank(String.valueOf(index));
                responseItem.setSiteInfo(link.attr("href")==null ? "null":String.format(SIMILARWEB_INFO_PREFIX+link.attr("href")));
                responseItem.setLink(link.text()==null ?"null":link.text());
                responseItem.setFavicon(link.getElementsByTag("img").attr("src"));
                response.add(responseItem);
                SIMILARWEB_TOP_50_CSS_SELECT_QUERY.replace(String.valueOf(index),"__INDEX__");
            }

        }
        return response;
    }
}
