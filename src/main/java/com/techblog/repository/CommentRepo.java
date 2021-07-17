package com.techblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techblog.models.Blog;
import com.techblog.models.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, String> {

	Comment findByCommentUuid(String commentId);

	List<Comment> findAllByBlog(Blog b);


}
