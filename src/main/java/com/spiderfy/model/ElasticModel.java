package com.spiderfy.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElasticModel {

    private String elapsedTime;
    private TextModelResponse text;
    private UrlModelResponse url;
    private String timestamp;
    private String fileSize;
    private String nextCrawlTime;
    private MetaTagsModelResponse metaTags;

}
