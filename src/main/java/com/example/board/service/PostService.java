package com.example.board.service;

import com.example.board.dto.PostCreateRequest;
import com.example.board.dto.PostResponse;
import com.example.board.entity.Post;
import com.example.board.exception.PostNotFoundException;
import com.example.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public PostResponse findById(
            Long id
    ) {
        log.info("서비스 실행: findById({})", id);
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return new PostResponse(post.getId(), post.getTitle());
    }

    @Transactional
    public PostResponse create(
            PostCreateRequest request
    ) {
        if (!StringUtils.hasText(request.getTitle())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Post post = postRepository.save(new Post(request.getTitle()));
        return new PostResponse(post.getId(), post.getTitle());
    }
}
