// package com.app.service;

// import com.app.model.Category;
// import com.app.repository.CategoryRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class CategoryService {
//     @Autowired
//     private CategoryRepository categoryRepository;

//     public List<Category> findAll() {
//         return categoryRepository.findAll();
//     }

//     public Optional<Category> findById(Long id) {
//         return categoryRepository.findById(id);
//     }

//     public Category save(Category category) {
//         return categoryRepository.save(category);
//     }

//     public void delete(Long id) {
//         categoryRepository.deleteById(id);
//     }
// }
