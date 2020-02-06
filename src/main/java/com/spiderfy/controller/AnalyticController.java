package com.spiderfy.controller;

import com.itextpdf.text.DocumentException;
import com.spiderfy.model.AnalyticModelResponse;
import com.spiderfy.model.JsInfoModelResponse;
import com.spiderfy.service.AnalyticService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@RestController
public class AnalyticController {

    @Autowired
    AnalyticService service;

    @CrossOrigin(origins = {"*"})
    @RequestMapping(path = "/analytic/alexa/turkeytop50/", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieve Alexa top 50 Turkey sites", notes = "Based on: https://www.alexa.com/topsites/countries/TR")
    @ResponseBody
    public List<AnalyticModelResponse> getAlexaTop50() {

        return service.getAlexaTop50();
    }

    @CrossOrigin(origins = {"*"})
    @RequestMapping(path = "/analytic/similarweb/turkeytop50/", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieve Similarweb top 50 Turkey sites", notes = "Based on: https://www.similarweb.com/top-websites/turkey")
    @ResponseBody
    public List<AnalyticModelResponse> getSimilarWebTop50() {
        return service.getSimilarWebTop50();
    }

    @CrossOrigin(origins = {"*"})
    @RequestMapping(path = "/analytic/getAllJsFileSummary/", method = RequestMethod.GET)
    @ApiOperation(value = "Get All JS files and sizes.", notes = "Offset = 0, limit = 0 for getting all datas.")
    @ResponseBody
    public JsInfoModelResponse getAllJsFileSummary(@RequestParam String url, @RequestParam Integer offset,
                                                   @RequestParam Integer limit) {

        return service.getAllJsFileSummary(url, offset, limit);
    }


    @CrossOrigin(origins = {"*"})
    @RequestMapping(path = "/analytic/generateJsFileSummaryPdf/", method = RequestMethod.GET, produces = "application" +
            "/pdf")
    @ApiOperation(value = "Generate PDF.", notes = "Generate summary of site PDF.")
    @ResponseBody
    public void generatePdf(@RequestParam String url, HttpServletResponse response) throws IOException, DocumentException {

        if (service.generatePdf(url).toByteArray().length > 0) {
            response.setHeader("Content-Disposition", "attachment;filename=" + url.replace(
                    "https://",
                    "").replace(
                    "http://", "").replace(".", "") + ".pdf");
            response.setContentType("application/pdf");
            response.setContentLength(service.generatePdf(url).toByteArray().length);
            response.getOutputStream().write(service.generatePdf(url).toByteArray());
        }

    }


}
