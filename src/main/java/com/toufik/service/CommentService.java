package com.toufik.service;

import com.toufik.dto.CommentsDto;
import com.toufik.mapper.CommentMapper;
import com.toufik.model.Comment;
import com.toufik.model.NotificationEmail;
import com.toufik.model.Post;
import com.toufik.model.User;
import com.toufik.repository.CommentRepository;
import com.toufik.repository.PostRepository;
import com.toufik.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
  private static final String POST_URL = "";
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final AuthService authService;
  private final CommentMapper commentMapper;
  private final CommentRepository commentRepository;
  private final MailContentBuilder mailContentBuilder;
  private final MailService mailService;

  public void save(CommentsDto commentsDto) {
    Post post = postRepository.findById(commentsDto.getPostId())
        .orElseThrow(() -> new RuntimeException(commentsDto.getPostId().toString()));
    Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
    commentRepository.save(comment);

    String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
    sendCommentNotification(message, post.getUser());
  }

  private void sendCommentNotification(String message, User user) {
    mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
  }

  public List<CommentsDto> getAllCommentsForPost(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException(postId.toString()));
    return commentRepository.findByPost(post)
        .stream()
        .map(commentMapper::mapToDto).toList();
  }

  public List<CommentsDto> getAllCommentsForUser(String userName) {
    User user = userRepository.findByUsername(userName)
        .orElseThrow(() -> new UsernameNotFoundException(userName));
    return commentRepository.findAllByUser(user)
        .stream()
        .map(commentMapper::mapToDto)
        .toList();
  }

  public boolean containsSwearWords(String comment) {
    if (comment.contains("shit")) {
      throw new RuntimeException("Comments contains unacceptable language");
    }
    return false;
  }
}