package com.spiderfy.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ImageModelResponse {

    private String elapsedTime;
    List<ImageModel> results;
}
