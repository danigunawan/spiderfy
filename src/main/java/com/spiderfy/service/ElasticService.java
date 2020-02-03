package com.spiderfy.service;

import com.spiderfy.model.ElasticModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class ElasticService {
@Autowired
CrawlService service;

    public ElasticModel generateElasticModel(String url) throws IOException
    {
        long start = System.currentTimeMillis();
       ElasticModel response =new ElasticModel();
       response.setText(service.getText(url));
       response.setUrl(service.getLinksWithThumbnail(url));
       response.setTimestamp(String.valueOf(Instant.now()));
       response.setFileSize(String.valueOf(response.getText().toString().length()));
       response.setTimestamp(String.valueOf(Instant.now()));
       response.setNextCrawlTime(String.valueOf(Instant.now()));
       response.setMetaTags(service.getSiteInfo(url));
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        response.setElapsedTime(String.valueOf(timeElapsed)+"ms");

        return response;
    }
}
