package com.techblog.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techblog.dto.CommentDto;
import com.techblog.models.Blog;
import com.techblog.models.Comment;
import com.techblog.repository.CommentRepo;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
	
	@Autowired
	private final CommentRepo commentRepo;
	@Autowired
	private final BlogService blogService;

	public Comment addComment(String blogId, CommentDto commentDto) {
		Comment comment = new Comment();
		comment.setCommentedAt(LocalDateTime.now());
		comment.setCommentedBy(commentDto.getCommentedBy());
		comment.setContent(commentDto.getContent());
		comment.setBlog(blogService.getBlogById(blogId));
		comment.setReplies(new ArrayList<Comment>());
		return commentRepo.save(comment );
	}
	

	public Comment addReply(String commentId, CommentDto commentDto) {
		Comment commentReply = new Comment();
		commentReply.setCommentedAt(LocalDateTime.now());
		commentReply.setCommentedBy(commentDto.getCommentedBy());
		commentReply.setContent(commentDto.getContent());
		commentReply.setBlog(null);
		commentReply.setReplyToComment(true);
		
		Comment c = commentRepo.findByCommentUuid(commentId);
		c.getReplies().add(commentReply);
		return commentRepo.save(c);
		 
	}


	public List<Comment> getCommentsByBlog(String blogId) {
		Blog b = blogService.getBlogById(blogId);
		return commentRepo.findAllByBlog(b);
		
	}


	public void likeComment(String commentId, int i) {
		Comment c = commentRepo.findByCommentUuid(commentId);
		if(i == 1) {
			c.setLikesCount(c.getLikesCount()+1);
		}else if(i == 2) {
			c.setLikesCount(c.getLikesCount()-1);
		}else if (i == -1) {
			c.setDislikesCount(c.getDislikesCount()+1);
		}else if (i == -2) {
			c.setDislikesCount(c.getDislikesCount()-1);
		}
		commentRepo.save(c);
	}


	public List<Comment> findAllRepliesByComment(String id) {
		return commentRepo.findByCommentUuid(id).getReplies();
	}


	public Comment deleteComment(String commentId) {
		Comment c = commentRepo.findByCommentUuid(commentId);
		commentRepo.delete(c);
		return c;
	}

}
