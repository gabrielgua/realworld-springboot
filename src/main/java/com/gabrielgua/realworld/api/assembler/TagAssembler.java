package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.tag.TagListResponse;
import com.gabrielgua.realworld.domain.model.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagAssembler {
    public TagListResponse toCollectionResponse(List<Tag> tags) {
        var tagNames = tags.stream().map(Tag::getName).toList();
        return TagListResponse.builder()
                .tags(tagNames)
                .build();
    }
}
