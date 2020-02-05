package com.spiderfy.model;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JsInfoModelResponse {
    private String elapsedTime;
    private Integer documentCount;
    List<JsInfoModel> results;
}
