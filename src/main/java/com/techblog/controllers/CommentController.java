package com.techblog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techblog.dto.CommentDto;
import com.techblog.models.Comment;
import com.techblog.repository.CommentRepo;
import com.techblog.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comment/v1")
@RequiredArgsConstructor
@Api(value = "COMMENT", description = "Comment Operations", tags = {"COMMENT"})
@CrossOrigin(origins = "*")
public class CommentController {

	@Autowired
	private final CommentService commentService;
	
	@ApiOperation(value = "Accepts Comment data As Json")
	@PostMapping("/comment/{blogId}")
	public Comment addCommentByBlogId(@PathVariable("blogId") String blogId, CommentDto commentDto) {
		return commentService.addComment(blogId, commentDto);
	}
	
	@ApiOperation(value = "Accepts Comment Reply data As Json with Comment id in url")
	@PostMapping("/reply/{commentId}")
	public Comment addCommentReply(@PathVariable("commentId") String commentId, CommentDto commentDto) {
		return commentService.addReply(commentId, commentDto);
	}

	@ApiOperation(value = "Returns List of Comments with specified blog id")
	@GetMapping("/comments/{blogId}")
	public List<Comment> getCommentsByBlogId(@PathVariable("BlogId") String blogId){
		return commentService.getCommentsByBlog(blogId);
	}
	
	@ApiOperation(value = "Returns List of Comment Replies with specified Comment id")
	@GetMapping("/replies/{commentId}")
	public List<Comment> getCommentRepliesById(@PathVariable("commentId") String commentId){
		return commentService.findAllRepliesByComment(commentId);
		}
	
	@ApiOperation(value = "Increment comment like value by 1")
	@GetMapping("comment/like/{commentId}")
	public void likeComment(@PathVariable("commentId") String commentId) {
		commentService.likeComment(commentId,1);
	}
	@ApiOperation(value = "decrement comment like value by 1")
	@GetMapping("comment/unlike/{commentId}")
	public void unlikeComment(@PathVariable("commentId") String commentId) {
		commentService.likeComment(commentId,2);
	}
	
	@ApiOperation(value = "Increment comment dislike value by 1")
	@GetMapping("comment/dislike/{commentId}")
	public void dislikeComment(@PathVariable("commentId") String commentId) {
		commentService.likeComment(commentId,-1);
	}
	@ApiOperation(value = "Decrement comment dislike value by 1")
	@GetMapping("comment/undislike/{commentId}")
	public void undislikeComment(@PathVariable("commentId") String commentId) {
		commentService.likeComment(commentId,-2);
	}

	@ApiOperation(value = "Deletes a comment By specified UUid and returns the same")
	@DeleteMapping("/comment/{commentId}")
	public Comment deleteComment(@PathVariable("commentId") String commentId) {
		return commentService.deleteComment(commentId);
	}
	
	@ApiOperation(value = "Deletes a comment reply By specified UUid and returns the same")
	@DeleteMapping("/reply/{replyId}")
	public Comment deleteReply(@PathVariable("replyId") String replyId) {
		return commentService.deleteComment(replyId);
	}
	
}
