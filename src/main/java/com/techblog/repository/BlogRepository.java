package com.techblog.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techblog.models.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, String> {

	Blog findByUuid(String blogUuid);

	List<Blog> findAllByCategoryContaining(String category);
	
	List<Blog> findAllByCategoryContainingOrContentContainingOrHeadingContaining(String key1, String key2, String key3);

	int countByPostedAtAfter(LocalDateTime timestamp);

	@Query("SELECT DISTINCT(b.category) FROM Blog b")
	List<String> findAllCategories();
	
}
