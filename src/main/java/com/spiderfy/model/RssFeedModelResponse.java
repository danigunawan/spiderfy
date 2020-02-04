package com.spiderfy.model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RssFeedModelResponse {
    private String elapsedTime;
    private String resource;
    private Integer numberOfRecords;
    List<RssFeedModel> results;
}
