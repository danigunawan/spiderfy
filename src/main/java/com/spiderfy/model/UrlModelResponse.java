package com.spiderfy.model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UrlModelResponse {
    private String elapsedTime;
    List<UrlModel> results;

}
