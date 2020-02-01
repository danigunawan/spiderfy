package com.spiderfy.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.spiderfy.model.OcrModel;
import com.spiderfy.service.OcrService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

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