package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.exception.TagNotFoundException;
import com.gabrielgua.realworld.domain.model.Tag;
import com.gabrielgua.realworld.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;


    @Transactional(readOnly = true)
    public List<Tag> listAll() {
        return repository.findAll();
    }

    @Transactional
    public Tag getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TagNotFoundException(id));
    }

    @Transactional
    public Tag getByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new TagNotFoundException(name));
    }

    @Transactional
    public Tag save(Tag tag) {
        var existingTag = repository.findByName(tag.getName());
        return existingTag.map(repository::save).orElseGet(() -> repository.save(tag));
    }

    public List<Tag> saveAll(List<String> tags) {
        return tags.stream()
                .map(t -> {
                    var tag = toEntity(t);
                    return save(tag);
                })
                .toList();
    }

    private Tag toEntity(String name) {
        return Tag.builder()
                .name(name)
                .build();
    }

    private boolean existsByName(String name) {
        return repository.existsByName(name);
    }
}
