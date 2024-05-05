package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.TagListResponse;
import com.gabrielgua.realworld.domain.model.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public TagListResponse toCollectionResponse(List<Tag> tags) {
        var tagNames = tags.stream().map(Tag::getName).toList();
        return TagListResponse.builder()
                .tags(tagNames)
                .build();
    }
}
