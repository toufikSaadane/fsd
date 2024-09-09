package com.toufik.controller;

import com.toufik.dto.PostRequest;
import com.toufik.dto.PostResponse;
import com.toufik.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class PostController {

  private final PostService postService;

  @PostMapping
  public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
    postService.save(postRequest);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<PostResponse>> getAllPosts() {
    return status(HttpStatus.OK).body(postService.getAllPosts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
    return status(HttpStatus.OK).body(postService.getPost(id));
  }

  @GetMapping(params = "categoryId")
  public ResponseEntity<List<PostResponse>> getPostsBycategory(@RequestParam Long categoryId) {
    return status(HttpStatus.OK).body(postService.getPostsByCategory(categoryId));
  }

  @GetMapping(params = "username")
  public ResponseEntity<List<PostResponse>> getPostsByUsername(@RequestParam String username) {
    return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
  }
}
