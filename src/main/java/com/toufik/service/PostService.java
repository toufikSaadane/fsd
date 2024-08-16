package com.toufik.service;

import com.toufik.dto.PostRequest;
import com.toufik.dto.PostResponse;
import com.toufik.mapper.PostMapper;
import com.toufik.model.Category;
import com.toufik.model.Post;
import com.toufik.model.User;
import com.toufik.repository.CategoryRepository;
import com.toufik.repository.PostRepository;
import com.toufik.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

  private final PostRepository postRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;
  private final AuthService authService;
  private final PostMapper postMapper;

  public void save(PostRequest postRequest) {
    Category category = categoryRepository.findByName(postRequest.getCategoryName())
        .orElseThrow(() -> new RuntimeException(postRequest.getCategoryName()));
    postRepository.save(postMapper.map(postRequest, category, authService.getCurrentUser()));
  }

  @Transactional(readOnly = true)
  public PostResponse getPost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(id.toString()));
    return postMapper.mapToDto(post);
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts() {
    return postRepository.findAll()
        .stream()
        .map(postMapper::mapToDto)
        .collect(toList());
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new RuntimeException(categoryId.toString()));
    List<Post> posts = postRepository.findAllByCategory(category);
    return posts.stream().map(postMapper::mapToDto).collect(toList());
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
    return postRepository.findByUser(user)
        .stream()
        .map(postMapper::mapToDto)
        .collect(toList());
  }
}