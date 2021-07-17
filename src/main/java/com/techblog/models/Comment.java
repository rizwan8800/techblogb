package com.techblog.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "Comments")
public class Comment {


	@Id
    @Column(length = 64)
	private String commentUuid = UUID.randomUUID().toString();
	
	@Column(nullable = false)
	private String commentedBy;
	
	
	@Lob
	private String content;
	
	@Column
	private LocalDateTime commentedAt;
	
	@ManyToOne
	@JsonIgnore
	private Blog blog;
	
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> replies;
    
	private int likesCount = 0;
	private int dislikesCount = 0;
    
	private boolean isReplyToComment = false;
	
}
