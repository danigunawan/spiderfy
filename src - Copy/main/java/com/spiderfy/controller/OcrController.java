package com.spiderfy.controller;

import com.spiderfy.model.OcrModel;
import com.spiderfy.service.OcrService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OcrController {
    @Autowired
    OcrService service;

    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/ocr/execute", method = RequestMethod.POST)
    @ApiOperation(value = "base64 to OCR." , notes = "Only support English [eng] and Turkish [tur] ")
    @ResponseBody
    public String imageToText(@RequestBody OcrModel request)  {

       return service.imageToText(request);
    }


}