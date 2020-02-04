package com.spiderfy.controller;
import com.spiderfy.model.AnalyticModelResponse;
import com.spiderfy.service.AnalyticService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AnalyticController {

    @Autowired
    AnalyticService service;
    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/analytic/alexa/turkeytop50/", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieve Alexa top 50 Turkey sites" ,notes = "Based on: https://www.alexa.com/topsites/countries/TR")
    @ResponseBody
    public List<AnalyticModelResponse> getAlexaTop50() {

        return service.getAlexaTop50();
    }
    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/analytic/similarweb/turkeytop50/", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieve Similarweb top 50 Turkey sites" , notes = "Based on: https://www.similarweb.com/top-websites/turkey")
    @ResponseBody
    public List<AnalyticModelResponse>  getSimilarWebTop50()
    {
      return service.getSimilarWebTop50();
    }

}
