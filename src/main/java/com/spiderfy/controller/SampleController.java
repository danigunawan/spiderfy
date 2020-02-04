package com.spiderfy.controller;

import com.spiderfy.model.AudiophileModelResponse;
import com.spiderfy.model.UrlModelResponse;
import com.spiderfy.service.CrawlService;
import com.spiderfy.service.SampleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SampleController {

    @Autowired
    SampleService service;

    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/sample/getAudiophileInfos/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain all links ", notes = "Ex: https://www.audiophile.org/Kategori/Amplifikatorler/")
    @ResponseBody
    public List<AudiophileModelResponse> getAudiophileInfos(@RequestBody String url , String offset, String categoryId, String parentCategoryId) throws Exception {

        return service.getAudiophileInfos(url,offset,categoryId,parentCategoryId);

    }

}
