package com.techblog.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techblog.dto.BlogDto;
import com.techblog.models.Blog;
import com.techblog.service.BlogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/blog/v1")
@RequiredArgsConstructor
@Api(value = "BLOG", description = "Blog Operations", tags = {"BLOG"})
@CrossOrigin(origins = "*")
public class BlogController {
	
	@Autowired
	private final BlogService blogService;
	
	
	
	@ApiOperation(value = "Returns List Of Blogs")
	@GetMapping("/blogs")
	public List<Blog> getAllBlogs(){
		return blogService.getAllBlogs();
	}
	
	@ApiOperation(value = "Returns Unique Blog By UUID")
	@GetMapping("/blog/{uuid}")
	public Blog getBlogById(@PathVariable("uuid") String blogUuid) {
		return blogService.getBlogById(blogUuid);
	}
	
	@ApiOperation(value = "Returns List Of Blogs By Category specified")
	@GetMapping("/blog/category/{category}")
	public List<Blog> getBlogByCategory(@PathVariable("category") String category) {
		return blogService.getBlogByCategory(category);
	}
	
	@ApiOperation(value = "Accepts a blog data as FORM_DATA")
	@PostMapping(value = "/blog", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Blog createBlog(@ModelAttribute BlogDto blogDto ) {
		return blogService.addNewBlog(blogDto);
	}
	
	@ApiOperation(value = "Returns List of blogs With matching key in heading, content or category")
	@GetMapping("/blog/search/{key}")
	public List<Blog> findBlogs(@PathVariable("key") String key){
		return blogService.searchBlog(key);
	}
	
	@ApiOperation(value = "Returns Count of blogs uploaded after certain timestamp")
	@GetMapping("/blog/latest")
	public int findCountOfLatestBlogs(@PathParam("timestamp") LocalDateTime timestamp) {
		return blogService.findLatestCount(timestamp);
	}
	
	@ApiOperation(value = "Increment blog like value by 1")
	@GetMapping("/blog/like/{uuid}")
	public void  likeBlog(@PathVariable("uuid") String blogUuid) {
		blogService.likeBlog(blogUuid, 1);
	}
	@ApiOperation(value = "decrement blog like value by 1")
	@GetMapping("/blog/unlike/{uuid}")
	public void  unlikeBlog(@PathVariable("uuid") String blogUuid) {
		blogService.likeBlog(blogUuid, 2);
	}
	
	@ApiOperation(value = "Increment blog dislike value by 1")
	@GetMapping("/blog/dislike/{uuid}")
	public void  dislikeBlog(@PathVariable("uuid") String blogUuid) {
		blogService.likeBlog(blogUuid, -1);
	}
	@ApiOperation(value = "decrement blog dislike value by 1")
	@GetMapping("/blog/undislike/{uuid}")
	public void  undislikeBlog(@PathVariable("uuid") String blogUuid) {
		blogService.likeBlog(blogUuid, -2);
	}
	
	@ApiOperation(value = "Returns List of unique Categories ")
	@GetMapping("/blog/categories")
	public List<String> findBlogCategories(){
		return blogService.categoriesList();
	}

	
	@ApiOperation(value = "Returns image with specified filename")
	@GetMapping(value = "/image/{filename}")
	 public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) throws IOException {
		 byte[] image = new byte[0];
		 try {
			  String FILE_PATH_ROOT = new ClassPathResource("").getFile().getAbsolutePath();
	            image = FileUtils.readFileToByteArray(new File(FILE_PATH_ROOT+"/image/"+filename));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
	 
	 @ApiOperation(value = "Deletes a blog by specified uuid and return the same")
	 @DeleteMapping("/blog/{uuid}")
	 public Blog deleteBlogById(@PathVariable("uuid") String blogId) {
		 return blogService.deleteBlogByUuid(blogId);
	 }
	

}


