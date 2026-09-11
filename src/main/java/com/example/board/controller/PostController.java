package com.example.board.controller;

import com.example.board.dto.NoticeResponse;
import com.example.board.dto.PostCreateRequest;
import com.example.board.dto.PostResponse;
import com.example.board.service.PostService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponse> create(
            @RequestBody PostCreateRequest request
    ) {
        PostResponse post = postService.create(request);
        return ResponseEntity.created(URI.create("/posts/" + post.getId())).body(post);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> findById(
            @PathVariable Long id
    ) {
        log.info("컨트롤러 실행: findById({})", id);
        return ResponseEntity.ok(postService.findById(id));
    }

    @GetMapping("/posts/public/notice")
    public ResponseEntity<NoticeResponse> notice() {
        log.info("컨트롤러 실행: notice()");
        return ResponseEntity.ok(new NoticeResponse("공지입니다."));
    }
}
