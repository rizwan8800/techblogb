package com.techblog.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Blog")
public class Blog {
	

	@Id
    @Column(length = 64)
	private String uuid = UUID.randomUUID().toString();
	  
    @Column(nullable = false)
	private String category;
    
    @Column(nullable = false)
	private String postedBy;
    
	private LocalDateTime postedAt;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "blog")
	private List<Comment> comments;
	
	private String heading;
	
	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String content;
	private int likesCount = 0;
	private int dislikesCount = 0;
	@ElementCollection(targetClass = String.class)
	private Set<String> imageLinks;
	

}
