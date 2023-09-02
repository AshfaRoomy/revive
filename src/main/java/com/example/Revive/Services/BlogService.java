package com.example.Revive.Services;


import com.example.Revive.Models.Product;
import com.example.Revive.Repositories.BlogRepository;
import com.example.Revive.Response.MessageResponse;
import com.example.Revive.Models.Blog;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    private BlogRepository blogRepository;
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

//    add blog
    public ResponseEntity<?> addBlog(Blog newBlog) {
//        if (blogRepository.existsByBlogTitle(newBlog.getBlogTitle())) {
//            return ResponseEntity.badRequest().body("Blog is already there!");
//        } else {
            Blog blog = new Blog();
            blog.setImage(newBlog.getImage());
            blog.setBlogTitle(newBlog.getBlogTitle());
            blog.setBlogContent(newBlog.getBlogContent());
            blogRepository.save(blog);
        System.out.println("new blog:"+newBlog);
            return ResponseEntity.ok(new MessageResponse("Successfully added a New Blog"));

    }

    //delete blog by the given id
    public ResponseEntity<?> deleteBlog(Integer blogId) {
        if (blogRepository.existsById(blogId)) {
            blogRepository.deleteById(blogId);
            return ResponseEntity.ok("Successfully Deleted the blog");
        } else {
            return ResponseEntity.ok("There is no such blog id");
        }
    }

    //get all category
    public List<Blog> getAllBlog() {
        System.out.println(blogRepository.findAll());
        return blogRepository.findAll();
    }

    public ResponseEntity<?> getBlogById(Integer blogId){
        if(blogRepository.existsById(blogId)){
            return ResponseEntity.ok(blogRepository.findById(blogId).get());
        }
        return  ResponseEntity.ok("There is no such blog");
    }

    public ResponseEntity<?> updateBlogByBlogId(Integer blogId, Blog updateBlog) {
        if (blogRepository.existsById(blogId)){
            Blog blog = blogRepository.findById(blogId).get();
            System.out.println(blog);
            blog.setBlogTitle(updateBlog.getBlogTitle());
            blog.setImage(updateBlog.getImage());
            blog.setBlogContent(updateBlog.getBlogContent());
            blogRepository.save(blog);
            return ResponseEntity.ok().body(blog);
        } else {
            return ResponseEntity.ok().body(new MessageResponse("Product not available"));
        }
    }
//    public List<Category>onGetAllBlogByTitle(String blogTitle){
//        System.out.println("sidra2: "+blogTitle);
//        return blogRepository.findByBlogTitle(blogTitle);
//    }


}
