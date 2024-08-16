package com.toufik.repository;

import com.toufik.model.Comment;
import com.toufik.model.Post;
import com.toufik.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByPost(Post post);

  List<Comment> findAllByUser(User user);
}
