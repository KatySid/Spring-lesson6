package ru.geekbrains.spring.one.repositories;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.one.model.Category;
import ru.geekbrains.spring.one.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
     List<Product> findAllByCategory(Category category);
     Page<Product> findAllByPriceBetweenAndTitleLike(int minPrice, int maxPrice, String partOfTitle, Pageable pagable);



}
