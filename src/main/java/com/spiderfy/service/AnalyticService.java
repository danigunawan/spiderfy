package com.spiderfy.service;

import com.itextpdf.text.*;
import com.spiderfy.model.AnalyticModelResponse;
import com.spiderfy.model.JsInfoModel;
import com.spiderfy.model.JsInfoModelResponse;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import com.itextpdf.text.pdf.PdfWriter;

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

        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.connect(ALEXA_TOP_50_URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int index = 2; index <= 51; index++) {

            Elements links = doc.select(ALEXA_TOP_50_CSS_SELECT_QUERY.replace("__INDEX__", String.valueOf(index)));
            for (org.jsoup.nodes.Element link : links) {
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
        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.connect(SIMILARWEB_TOP_50_URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int index = 1; index <= 51; index++) {
            Elements links = doc.select(SIMILARWEB_TOP_50_CSS_SELECT_QUERY.replace("__INDEX__", String.valueOf(index)));

            for (org.jsoup.nodes.Element link : links) {
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
        org.jsoup.nodes.Document doc = null;
        String fileSize = "";
        String fileUrl = "";
        Integer documentCount = 0;

        try {
            doc = Jsoup.connect(url).get();

            List<org.jsoup.nodes.Element> links = (offset.equals(0) && limit.equals(0)) ? doc.select("script") : doc.select("script").subList(offset, limit);
            List<org.jsoup.nodes.Element> linkRel = doc.select("link");

            for (org.jsoup.nodes.Element link : links) {
                JsInfoModel item = new JsInfoModel();
                if (!link.attr("abs:src").isEmpty()) {
                    documentCount++;
                    // Condition is for //example.com/js
                    fileUrl = link.attr("abs:src").startsWith("//") ? ("http://" + link.attr("abs:src").replace(url +
                            "//", "")) :
                            link.attr("abs:src");


                    // Condition is for /example.com/js
                    if (link.attr("abs:src").substring(0, 1).equals("/") && !link.attr("abs:src").substring(1, 2).equals(
                            "/")) {

                        fileUrl = url + link.attr("abs:src");

                    }

                    item.setJsFileAddress(fileUrl);
                    fileSize = String.format("%.4f",
                            ((double) Math.round(Double.valueOf(Jsoup.connect(fileUrl).ignoreContentType(true).get().text().getBytes().length) / (1024D * 1024D) * 10000d) / 10000d));

                    item.setJsFileSize(fileSize + " mb");

                    item.setJsContentLength(Jsoup.connect(fileUrl).ignoreContentType(true).get().text().length());
                    modelList.add(item);
                }
            }

            for (org.jsoup.nodes.Element link : linkRel) {
                JsInfoModel item = new JsInfoModel();
                if (!link.attr("as").isEmpty() && link.attr("as").equals("script")) {

                    // Condition is for //example.com/js
                    fileUrl = link.attr("abs:href").startsWith("//") ? ("http://" + link.attr("abs:href").replace(url +
                            "//", "")) :
                            link.attr("abs:href");


                    // Condition is for /example.com/js
                    if (link.attr("abs:href").substring(0, 1).equals("/") && !link.attr("abs:href").substring(1, 2).equals(
                            "/")) {

                        fileUrl = url + link.attr("abs:href");

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


    public ByteArrayOutputStream generatePdf(String url) throws IOException, DocumentException {

        try {
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);

            PdfPTable table = new PdfPTable(3);

            PdfPCell c1 = new PdfPCell(new Phrase("Javascript File Url"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Javascript File Size"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Javascript Content Length"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.setHeaderRows(1);
            JsInfoModelResponse items = getAllJsFileSummary(url, 0, 0);
            for (JsInfoModel item : items.getResults()) {

                table.addCell(String.valueOf(new Chunk(item.getJsFileAddress(), font)));
                table.addCell(String.valueOf(new Chunk(item.getJsFileSize(), font)));
                table.addCell(String.valueOf(new Chunk(String.valueOf(item.getJsContentLength()), font)));

            }

            document.add(table);
            document.close();
            return baos;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            return new ByteArrayOutputStream();
        }

    }


}
