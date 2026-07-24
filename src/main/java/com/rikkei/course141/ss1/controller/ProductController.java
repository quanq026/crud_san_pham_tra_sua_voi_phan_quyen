package com.rikkei.course141.ss1.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.rikkei.course141.ss1.dto.response.ApiResponse;
import com.rikkei.course141.ss1.model.Product;
import com.rikkei.course141.ss1.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository productRepository;
    public ProductController(ProductRepository productRepository) { this.productRepository = productRepository; }

    @GetMapping public ResponseEntity<ApiResponse<List<Product>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(productRepository.findAll()));
    }

    @PostMapping public ResponseEntity<ApiResponse<Product>> create(@Valid @RequestBody Product dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(productRepository.save(dto)));
    }

    @PutMapping("/{id}") public ResponseEntity<ApiResponse<Product>> update(@PathVariable Long id, @Valid @RequestBody Product dto) {
        Product p = productRepository.findById(id).orElseThrow();
        p.setName(dto.getName()); p.setDescription(dto.getDescription()); p.setPrice(dto.getPrice());
        p.setSize(dto.getSize()); p.setToppings(dto.getToppings());
        return ResponseEntity.ok(ApiResponse.success(productRepository.save(p)));
    }

    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted"));
    }
}
