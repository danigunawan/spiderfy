package com.spiderfy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MetaTagsModelResponse {
    private String elapsedTime;
    List<MetaTagsModel> results;

}