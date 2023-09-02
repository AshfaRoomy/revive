package com.example.Revive.Controllers;

import com.example.Revive.Models.Product;
import com.example.Revive.Services.ProductService;
import com.example.Revive.Services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = ("*"))
@RequestMapping("api/product")
@RestController
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<?>addProduct(@RequestBody Product newProduct, HttpServletRequest httpServletRequest){
        return productService.addProduct(newProduct);
    }

    @RequestMapping(value="/productAll")
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @RequestMapping(value="/product/{productId}")
    public ResponseEntity<?> getProductByProductId(@PathVariable Integer productId)
    {
        return productService.getProductById(productId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value="/update-product/{productId}")
    public ResponseEntity<?> updateProductByProductId(@PathVariable Integer productId,@RequestBody Product updateProduct,HttpServletRequest httpServletRequest)
    {
        return productService.updateProductByProductId(productId,updateProduct);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping (value = "delete-product/{productId}")
    public ResponseEntity<?> deleteProductByProductId(@PathVariable Integer productId, HttpServletRequest httpServletRequest)
    {
        return productService.deleteProductByProductId(productId);
    }

//        @PreAuthorize("hasAuthority('ADMIN') || hasAut/hority('CUSTOMER')")
    @GetMapping (value = "category-product/{categoryType}")
    public List<Product> getAllProductsByCategoryName(@PathVariable String categoryType)
    {
        return productService.getAllProductsByCategoryName(categoryType);
    }


}
