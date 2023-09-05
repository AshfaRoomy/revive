package com.example.Revive.Services;

import com.example.Revive.Repositories.CategoryRepository;
import com.example.Revive.Response.MessageResponse;
import com.example.Revive.Models.Product;
//import com.example.Revive.Repositories.CartRepository;
import com.example.Revive.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
//    private CartRepository cartRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;

    }

    //add new Product
    public ResponseEntity<?> addProduct(Product newProduct) {
        if (productRepository.existsByProductName(newProduct.getProductName())) {
            return ResponseEntity.badRequest().body("Product already exist!!!");
        }
        Product product = new Product();
        product.setProductName(newProduct.getProductName());
        product.setBrand(newProduct.getBrand());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        product.setQuantity(newProduct.getQuantity());
        product.setCategory(newProduct.getCategory());
        product.setImage(newProduct.getImage());
        productRepository.save(product);

        return ResponseEntity.ok().body(new MessageResponse("Successfully Product Added"));
    }

    //delete a Product
    public ResponseEntity<?> deleteProductByProductId(Integer id) {
        if (productRepository.existsById(id)) {
            System.out.println("Product id:"+ id);
            productRepository.deleteById(id);

            return ResponseEntity.ok().body(new MessageResponse("Product deleted successfully"));
//        } else if (cartRepository.ex) {

        }
        return ResponseEntity.badRequest().body("Sorry couldn't delete product!");

    }



    //get all Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // get product by id
    public ResponseEntity<?> getProductById(Integer id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.ok().body("Product not available!!!");
        } else {
            Product product = (Product) productRepository.findById(id).get();
            return ResponseEntity.ok().body(product);
        }
    }

    public ResponseEntity<?> updateProductByProductId(Integer productId, Product updateProduct) {
        if (productRepository.existsById(productId)){
            Product product = productRepository.findById(productId).get();
            System.out.println(product);
            product.setProductName(updateProduct.getProductName());
            product.setBrand(updateProduct.getBrand());
            product.setDescription(updateProduct.getDescription());
            product.setPrice(updateProduct.getPrice());
            product.setQuantity(updateProduct.getQuantity());
            product.setImage(updateProduct.getImage());
            product.setCategory(updateProduct.getCategory());
            productRepository.save(product);
            return ResponseEntity.ok().body(product);
        } else {
            return ResponseEntity.ok().body(new MessageResponse("Product not available"));
        }
    }

    public List<Product> getAllProductsByCategoryName(String categoryType) {
        System.out.println(categoryType+" here");
        return productRepository.findByCategory(categoryType);
    }


}