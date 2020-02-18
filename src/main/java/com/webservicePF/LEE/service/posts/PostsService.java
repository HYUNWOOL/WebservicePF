package com.webservicePF.LEE.service.posts;

import com.webservicePF.LEE.domain.posts.Posts;
import com.webservicePF.LEE.domain.posts.PostsRepository;
import com.webservicePF.LEE.web.dto.PostsListResponseDto;
import com.webservicePF.LEE.web.dto.PostsResponseDto;
import com.webservicePF.LEE.web.dto.PostsSaveRequestDto;
import com.webservicePF.LEE.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }
    @Transactional
    public long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당게시글이 없습니다. id ="+id));
        posts.update(requestDto.getTitle(),requestDto.getContent());
        return id;
    }
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당게시글이 없습니다. id ="+id));
        return new PostsResponseDto(entity);
    }
    @Transactional
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }
}
