package com.gabrielgua.realworld.api.assembler;

import com.gabrielgua.realworld.api.model.comment.CommentWrapper;
import com.gabrielgua.realworld.api.model.comment.CommentRegister;
import com.gabrielgua.realworld.api.model.comment.CommentResponse;
import com.gabrielgua.realworld.domain.model.Comment;
import com.gabrielgua.realworld.domain.model.User;
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

    public CommentResponse toResponse(User user, Comment comment) {
        var response = toResponse(comment);

        if (user.getFollowing().contains(comment.getAuthor())) {
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

    public CommentWrapper toCollectionResponse(User user, List<Comment> comments) {
        var content = comments.stream()
                .map(c -> toResponse(user, c))
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
