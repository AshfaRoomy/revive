package com.example.Revive.Repositories;


import com.example.Revive.Models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
    boolean existsByBlogTitle(String blogTitle);
//    List<Category> findByCategoryNameStartsWith (String categoryType);
}
