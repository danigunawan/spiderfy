package com.spiderfy.controller;

import com.spiderfy.model.*;
import com.spiderfy.service.CrawlService;
import io.swagger.annotations.ApiOperation;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
public class CrawlController {
@Autowired
CrawlService service;


    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/crawl/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain all links ", notes = "Ex: https://www.lipsum.com/")
    @ResponseBody
    public UrlModelResponse getLinks(@RequestBody String url) throws Exception {

       return service.getLinks(url);
    }


    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/crawl/metatags/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain site metatags ", notes = "Ex: https://www.lipsum.com/")
    @ResponseBody
    public MetaTagsModelResponse getSiteInfo(@RequestBody String url) {

        return service.getSiteInfo(url);
    }
    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/crawl/defaultsitemap/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain all sitemap nodes. " ,notes = "Ex : URL/sitemap.xml ")
    @ResponseBody
    public SitemapModelResponse defaultsitemap(@RequestBody String siteMapUrl) {

        return service.defaultsitemap(siteMapUrl);
    }

    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/crawl/sitemap/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain all sitemap nodes. " ,notes = "Ex : https://www.turkcell.com.tr/sitemap.xml , http://www.hurriyet.com.tr/sitemaps/posts.xml, http://www.hurriyet.com.tr/sitemaps/posts-2020-01.xml")
    @ResponseBody
    public SitemapModelResponse sitemap(@RequestBody String siteMapUrl) {

        return service.sitemap(siteMapUrl);
    }

    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/crawl/images/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain all images", notes = "Ex: https://www.lipsum.com/")
    @ResponseBody
    public ImageModelResponse getImages(@RequestBody String url) {

        return service.getImages(url);
    }



    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/crawl/text/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain get title and html.")
    @ResponseBody
    public TextModelResponse getText(@RequestBody String url) {

        return service.getText(url);
    }



    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/crawl/changeuseragent/", method = RequestMethod.POST)
    @ApiOperation(value = "Obtain get title and html.")
    @ResponseBody
    public ResponseEntity<String> changeUserAgent(@RequestBody String url) throws IOException {

        return service.changeUserAgent(url);
    }



    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/crawl/getAllUserAgents/", method = RequestMethod.GET)
    @ApiOperation(value = "List all user agents.")
    @ResponseBody
    public ResponseEntity<String> getAllUserAgents() throws IOException {

        return service.getAllUserAgents();
    }


    @CrossOrigin(origins = { "*" })
    @RequestMapping(path = "/crawl/takescreenshoot/", method = RequestMethod.POST)
    @ApiOperation(value = "Take screenshoot from given url.", notes = "Base64 format , Control : https://codebeautify.org/base64-to-image-converter")
    @ResponseBody
    public ResponseEntity<ScreenShootModelResponse> takeScreenShoot(@RequestBody String url) throws IOException {

        return service.takeScreenShot(url);
    }

}






