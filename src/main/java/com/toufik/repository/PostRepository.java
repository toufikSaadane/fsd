package com.toufik.repository;

import com.toufik.model.Post;
import com.toufik.model.Subreddit;
import com.toufik.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllBySubreddit(Subreddit subreddit);
  List<Post> findByUser(User user);
}
