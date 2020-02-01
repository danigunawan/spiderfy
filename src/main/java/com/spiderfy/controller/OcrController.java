package com.spiderfy.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.spiderfy.model.OcrModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@RestController
public class OcrController {


    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/ocr/execute", method = RequestMethod.POST)
    @ApiOperation(value = "base64 to OCR." , notes = "Only support English [eng] and Turkish [tur] ")
    @ResponseBody
    public String imageToText(@RequestBody OcrModel request)  {

        // Only support language according to tessdata  [eng,tur]
        ITesseract instance = new Tesseract();

        try {

            String base64Image = request.getImage().split(",")[1];
            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
            BufferedImage in =  ImageIO.read(new ByteArrayInputStream(imageBytes));
            instance.setLanguage(request.getDestinationLanguage());
            instance.setDatapath(System.getProperty("user.dir")+"//tessdata");
            return instance.doOCR(in);

        }
        catch (TesseractException | IOException e) {
            System.err.println(e.getMessage());
            return "Error while reading image";
        }

    }


}