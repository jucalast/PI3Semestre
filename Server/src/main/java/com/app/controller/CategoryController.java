// package com.app.controller;

// import com.app.model.Category;
// import com.app.service.CategoryService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/categories")
// public class CategoryController {
//     @Autowired
//     private CategoryService categoryService;

//     @GetMapping
//     public List<Category> getAllCategories() {
//         return categoryService.findAll();
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
//         Optional<Category> category = categoryService.findById(id);
//         return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//     }

//     @PostMapping
//     public Category createCategory(@RequestBody Category category) {
//         return categoryService.save(category);
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
//         if (categoryService.findById(id).isPresent()) {
//             category.setId(id);
//             return ResponseEntity.ok(categoryService.save(category));
//         }
//         return ResponseEntity.notFound().build();
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
//         if (categoryService.findById(id).isPresent()) {
//             categoryService.delete(id);
//             return ResponseEntity.noContent().build();
//         }
//         return ResponseEntity.notFound().build();
//     }
// }
