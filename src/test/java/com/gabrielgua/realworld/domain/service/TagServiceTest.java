package com.gabrielgua.realworld.domain.service;

import com.gabrielgua.realworld.domain.model.Tag;
import com.gabrielgua.realworld.domain.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class TagServiceTest {

    @Mock private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    private final List<Tag> tags = new ArrayList<>();

    private Tag tagDragons = Tag.builder().id(1L).name("dragons").build();
    private Tag tagCode = Tag.builder().id(2L).name("code").build();

    @BeforeEach
    void setUp() {
        openMocks(this);

        tags.add(tagCode);
        tags.add(tagDragons);
    }


    @Test
    public void should_return_all_tags_successfully() {
        when(tagRepository.findAll()).thenReturn(tags);

        var allTags = tagService.listAll();

        assertThat(allTags.size()).isEqualTo(tags.size());

        verify(tagRepository).findAll();
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    public void should_return_empty_list() {
        when(tagRepository.findAll()).thenReturn(new ArrayList<Tag>());

        var allTags = tagService.listAll();

        assertThat(allTags.size()).isEqualTo(0);

        verify(tagRepository).findAll();
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    public void should_save_new_tag_successfully() {
        var dragonsCreate = Tag.builder().name("dragons").build();
        when(tagRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(tagRepository.save(any(Tag.class))).thenReturn(tagDragons);

        var savedTag = tagService.save(dragonsCreate);

        assertThat(savedTag).isNotNull();
        assertThat(savedTag.getId()).isEqualTo(tagDragons.getId());
        assertThat(savedTag.getName()).isEqualTo(tagDragons.getName());


        verify(tagRepository, times(1)).save(any(Tag.class));
    }











}