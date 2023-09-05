package com.example.Revive.Controllers;

import com.example.Revive.Models.Blog;
import com.example.Revive.Models.Product;
import com.example.Revive.Services.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = ("*"))
@RequestMapping("api/blog")
@RestController
public class BlogController {
    private BlogService blogService;

    public BlogController( BlogService blogService){
        this.blogService = blogService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/addBlog")
    public ResponseEntity<?> addBlog(@RequestBody Blog newBlog, HttpServletRequest httpServletRequest){
        return blogService.addBlog(newBlog);
    }
    @GetMapping(value = "/all")
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlog();
    }


    //    delete product
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/deleteBlog/{blogId}")
    public ResponseEntity<?> deleteBlog(@PathVariable Integer blogId,HttpServletRequest httpServletRequest)
    {
       return blogService.deleteBlog(blogId);
    }

    @GetMapping(value="/blog/{blogId}")
    public ResponseEntity<?> getBlogById (@PathVariable Integer blogId){

        return blogService.getBlogById(blogId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value="/update-blog/{blogId}")
    public ResponseEntity<?> updateBlogByBlogId(@PathVariable Integer blogId, @RequestBody Blog updateBlog, HttpServletRequest httpServletRequest)
    {
        return blogService.updateBlogByBlogId(blogId,updateBlog);
    }


}
