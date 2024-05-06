package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.comment.CommentRegister;
import com.gabrielgua.realworld.api.model.comment.CommentResponse;
import com.gabrielgua.realworld.api.model.comment.CommentWrapper;
import com.gabrielgua.realworld.domain.model.Comment;
import com.gabrielgua.realworld.domain.model.Profile;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentAssembler {

    private final ModelMapper modelMapper;

    public CommentResponse toResponse(Comment comment) {
        return modelMapper.map(comment, CommentResponse.class);
    }

    public CommentResponse toResponse(Profile profile, Comment comment) {
        var response = toResponse(comment);

        if (profile.getProfiles().contains(comment.getAuthor())) {
            response.getAuthor().setFollowing(true);
        }

        return response;
    }

    public CommentWrapper toCollectionResponse(List<Comment> comments) {
        var content = comments.stream()
                .map(this::toResponse)
                .toList();

        return buildResponse(content);
    }

    public CommentWrapper toCollectionResponse(Profile profile, List<Comment> comments) {
        var content = comments.stream()
                .map(c -> toResponse(profile, c))
                .toList();

        return buildResponse(content);
    }

    private CommentWrapper buildResponse(List<CommentResponse> comments) {
        return CommentWrapper.builder()
                .comments(comments)
                .build();
    }

    public Comment toEntity(CommentRegister register) {
        return modelMapper.map(register, Comment.class);
    }
}
