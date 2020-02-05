package com.spiderfy.service;

import com.spiderfy.model.AnalyticModelResponse;
import com.spiderfy.model.JsInfoModel;
import com.spiderfy.model.JsInfoModelResponse;
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

    private final String ALEXA_TOP_50_CSS_SELECT_QUERY = "#alx-content > div > section > div.row-fluid.TopSites.Alexarest > section.page-product-content.summary > span > span > div > div > div.listings.table > div:nth-child(__INDEX__) > div.td.DescriptionCell > p > a";
    private final String ALEXA_TOP_50_URL = "https://www.alexa.com/topsites/countries/TR";
    private final String SIMILARWEB_TOP_50_URL = "https://www.similarweb.com/top-websites/turkey";
    private final String SIMILARWEB_TOP_50_CSS_SELECT_QUERY = "  body > div.wrapperBody--topRanking.wrapper-body.js-wrapperBody > main > div > section.topRankingSection > div > div.topWebsites-table > div.topRankingGrid > table > tbody > tr:nth-child(__INDEX__) > td.topRankingGrid-cell.topWebsitesGrid-cellWebsite.showInMobile > div > a.topRankingGrid-title.js-tooltipTarget";
    private final String SIMILARWEB_INFO_PREFIX = "https://www.similarweb.com";
    private final String ALEXA_INFO_PREFIX = "https://www.alexa.com";

    public List<AnalyticModelResponse> getAlexaTop50() {
        List<AnalyticModelResponse> response = new ArrayList<AnalyticModelResponse>();

        Document doc = null;
        try {
            doc = Jsoup.connect(ALEXA_TOP_50_URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int index = 2; index <= 51; index++) {

            Elements links = doc.select(ALEXA_TOP_50_CSS_SELECT_QUERY.replace("__INDEX__", String.valueOf(index)));
            for (Element link : links) {
                AnalyticModelResponse responseItem = new AnalyticModelResponse();
                responseItem.setRank(String.valueOf(index - 1));
                responseItem.setSiteInfo(link.attr("href") == null ? "null" : String.format(ALEXA_INFO_PREFIX + link.attr("href")));
                responseItem.setLink(link.text() == null ? "null" : link.text());
                responseItem.setFavicon(link.getElementsByTag("img").attr("src"));
                response.add(responseItem);
                ALEXA_TOP_50_CSS_SELECT_QUERY.replace(String.valueOf(index), "__INDEX__");
            }

        }
        return response;
    }

    public List<AnalyticModelResponse> getSimilarWebTop50() {
        List<AnalyticModelResponse> response = new ArrayList<AnalyticModelResponse>();
        Document doc = null;
        try {
            doc = Jsoup.connect(SIMILARWEB_TOP_50_URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int index = 1; index <= 51; index++) {
            Elements links = doc.select(SIMILARWEB_TOP_50_CSS_SELECT_QUERY.replace("__INDEX__", String.valueOf(index)));

            for (Element link : links) {
                AnalyticModelResponse responseItem = new AnalyticModelResponse();

                responseItem.setRank(String.valueOf(index));
                responseItem.setSiteInfo(link.attr("href") == null ? "null" : String.format(SIMILARWEB_INFO_PREFIX + link.attr("href")));
                responseItem.setLink(link.text() == null ? "null" : link.text());
                responseItem.setFavicon(link.getElementsByTag("img").attr("src"));
                response.add(responseItem);
                SIMILARWEB_TOP_50_CSS_SELECT_QUERY.replace(String.valueOf(index), "__INDEX__");
            }

        }
        return response;
    }

    public JsInfoModelResponse getAllJsFileSummary(String url, Integer offset, Integer limit) {

        JsInfoModelResponse response = new JsInfoModelResponse();
        List<JsInfoModel> modelList = new ArrayList<JsInfoModel>();
        long start = System.currentTimeMillis();
        Document doc = null;
        String fileSize = "";
        String fileUrl = "";
        Integer documentCount;

        try {
            doc = Jsoup.connect(url).get();

            documentCount = doc.select("script").size();

            List<Element> links =  (offset.equals(0) && limit.equals(0))? doc.select("script"): doc.select("script").subList(offset, limit);
            List<Element> linkRel = doc.select("link");

            for (Element link : links) {
                JsInfoModel item = new JsInfoModel();
                if (!link.attr("abs:src").isEmpty())
                {

                    // Condition is for //example.com/js
                    fileUrl = link.attr("abs:src").startsWith("//") ? ("http://" + link.attr("abs:src").replace(url +
                            "//", "")) :
                            link.attr("abs:src");


                    // Condition is for /example.com/js
                    if(link.attr("abs:src").substring(0,1).equals("/") && !link.attr("abs:src").substring(1,2).equals(
                            "/"))
                    {

                        fileUrl = url +link.attr("abs:src");

                    }

                    item.setJsFileAddress(fileUrl);
                    fileSize = String.format("%.4f",
                            ((double) Math.round(Double.valueOf(Jsoup.connect(fileUrl).ignoreContentType(true).get().text().getBytes().length) / (1024D * 1024D) * 10000d) / 10000d));

                    item.setJsFileSize(fileSize + " mb");

                    item.setJsContentLength(Jsoup.connect(fileUrl).ignoreContentType(true).get().text().length());
                    modelList.add(item);
                }
            }

            for (Element link : linkRel) {
                JsInfoModel item = new JsInfoModel();
                if (!link.attr("as").isEmpty() && link.attr("as").equals("script"))
                {

                    // Condition is for //example.com/js
                    fileUrl = link.attr("abs:href").startsWith("//") ? ("http://" + link.attr("abs:href").replace(url +
                            "//", "")) :
                            link.attr("abs:href");


                    // Condition is for /example.com/js
                    if(link.attr("abs:href").substring(0,1).equals("/") && !link.attr("abs:href").substring(1,2).equals(
                            "/"))
                    {

                        fileUrl = url +link.attr("abs:href");

                    }

                    item.setJsFileAddress(fileUrl);
                    fileSize = String.format("%.4f",
                            ((double) Math.round(Double.valueOf(Jsoup.connect(fileUrl).ignoreContentType(true).get().text().getBytes().length) / (1024D * 1024D) * 10000d) / 10000d));

                    item.setJsFileSize(fileSize + " mb");

                    item.setJsContentLength(Jsoup.connect(fileUrl).ignoreContentType(true).get().text().length());
                    modelList.add(item);
                    documentCount++;
                }
            }

            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            response.setResults(modelList);
            response.setDocumentCount(documentCount);
            response.setElapsedTime(String.valueOf(timeElapsed) + "ms");

            return response;
        } catch (IOException e) {
            e.printStackTrace();


            return null;
        }
    }

}
