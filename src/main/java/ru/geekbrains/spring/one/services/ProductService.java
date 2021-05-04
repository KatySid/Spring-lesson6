package ru.geekbrains.spring.one.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.one.model.Category;
import ru.geekbrains.spring.one.model.Product;
import ru.geekbrains.spring.one.repositories.CategoryRepository;
import ru.geekbrains.spring.one.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<Product> findAll(int page, int size) {

        return productRepository.findAll(PageRequest.of(page,size));
    }

    public Optional<Product> findOneById(Long id) {
        return productRepository.findById(id);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findByCategory(Long id) {
        Category category = categoryRepository.findById(id).get();
        return productRepository.findAllByCategory(category); }

    public Page<Product> findAllByPriceBetweenAndTitle(int minPrice,
                                                       int maxPrice,
                                                       String partOfTitle,
                                                       int page, int size){
        return productRepository.findAllByPriceBetweenAndTitleLike(minPrice, maxPrice, partOfTitle,
                                                                    PageRequest.of(page,size));
    }

}
