package com.techblog.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BlogDto {
	
	
	private String category;
	private String postedBy;
	private String heading;
	private String content;
	@ApiModelProperty(value = "Accepts only .jpg files")
	private List<MultipartFile> files;

}
