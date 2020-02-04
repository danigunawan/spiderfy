package com.spiderfy.controller;


import com.spiderfy.model.ElasticModel;
import com.spiderfy.service.ElasticService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ElasticController {
    @Autowired
    ElasticService service;

    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/elastic/", method = RequestMethod.POST)
    @ApiOperation(value = "Generate Elastic data ")
    @ResponseBody
    public ElasticModel generateElasticModel(@RequestBody String url) throws Exception {

        return service.generateElasticModel(url);
    }


}
