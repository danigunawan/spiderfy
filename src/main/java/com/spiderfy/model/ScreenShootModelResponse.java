package com.spiderfy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScreenShootModelResponse {
    private String elapsedTime;
    List<ScreenShootModel> results;
}
