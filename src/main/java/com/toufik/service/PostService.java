package com.toufik.service;

import com.toufik.dto.PostRequest;
import com.toufik.dto.PostResponse;
import com.toufik.mapper.PostMapper;
import com.toufik.model.Post;
import com.toufik.model.Subreddit;
import com.toufik.model.User;
import com.toufik.repository.PostRepository;
import com.toufik.repository.SubredditRepository;
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
  private final SubredditRepository subredditRepository;
  private final UserRepository userRepository;
  private final AuthService authService;
  private final PostMapper postMapper;

  public void save(PostRequest postRequest) {
    Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
        .orElseThrow(() -> new RuntimeException(postRequest.getSubredditName()));
    postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
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
  public List<PostResponse> getPostsBySubreddit(Long subredditId) {
    Subreddit subreddit = subredditRepository.findById(subredditId)
        .orElseThrow(() -> new RuntimeException(subredditId.toString()));
    List<Post> posts = postRepository.findAllBySubreddit(subreddit);
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