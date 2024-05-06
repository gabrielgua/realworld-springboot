package com.gabrielgua.realworld.api.model.tag;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TagListResponse {

    private List<String> tags;

}
