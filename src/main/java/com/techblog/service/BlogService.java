package com.techblog.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.techblog.dto.BlogDto;
import com.techblog.models.Blog;
import com.techblog.models.Comment;
import com.techblog.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
	
	@Autowired
	private final BlogRepository blogRepository;
	@Autowired
	private final ServletContext context;

	public List<Blog> getAllBlogs() {
		return blogRepository.findAll();
	}

	public Blog getBlogById(String blogUuid) {
		return blogRepository.findByUuid(blogUuid);
	}

	public List<Blog> getBlogByCategory(String category) {
		return blogRepository.findAllByCategoryContaining(category);
	}

	public Blog addNewBlog(BlogDto blogDto) {
		
		Blog blog = new Blog();
		
		blog.setCategory(blogDto.getCategory());
		blog.setPostedBy(blogDto.getPostedBy());
		blog.setPostedAt(LocalDateTime.now());
		blog.setComments(new ArrayList<Comment>());
		blog.setContent(blogDto.getContent());
		blog.setHeading(blogDto.getHeading());
		blog.setImageLinks(getlinks(blogDto.getFiles()));
		
		return blogRepository.save(blog);
	}

	public List<Blog> searchBlog(String key) {
		return blogRepository.findDistinctByCategoryContainingOrContentContainingOrHeadingContaining(key,key, key);
	}

	public int findLatestCount(LocalDateTime timestamp) {
		return blogRepository.countByPostedAtAfter(timestamp);
	}
	
	
	private Set<String> getlinks(List<MultipartFile> files) {
		
		Set<String> links = new HashSet<>();
		if(files.size() != 0) {
			for(int i=0;i <files.size(); i++) {
				MultipartFile file = files.get(i);
				String filename = file.getOriginalFilename();
				String destFileName = UUID.randomUUID().toString()+filename;
				
				
				try {
					String  BASE_DIR = new ClassPathResource("").getFile().getAbsolutePath();					
					Path p = Path.of(BASE_DIR+"/image/"+destFileName);
					System.out.println(p.toString());
					File f = new File(p.toString());
					f.createNewFile();
					Files.copy(file.getInputStream(), p, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("file saved successfully");
					String link = ServletUriComponentsBuilder.fromCurrentContextPath().path("/blog/v1/image/"+destFileName).toUriString();
					links.add(link);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return links;
	}

	public void likeBlog(String blogUuid, int value) {
		Blog b = blogRepository.findByUuid(blogUuid);
		if(value == 1) {
			b.setLikesCount(b.getLikesCount()+1);
		}else if(value == 2){
			b.setLikesCount(b.getLikesCount()-1);
		}else if (value == -1) {
			b.setDislikesCount(b.getDislikesCount()+1);
		}else if(value == -2) {
			b.setDislikesCount(b.getDislikesCount()-1);
		}
		blogRepository.save(b);
	}

	public List<String> categoriesList() {
		return blogRepository.findAllCategories();
	}

	public Blog deleteBlogByUuid(String id) {
		Blog b = blogRepository.findByUuid(id);
		blogRepository.delete(b);
		return b;
	}


}
