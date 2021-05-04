package ru.geekbrains.spring.one.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.one.model.Category;
import ru.geekbrains.spring.one.model.Product;
import ru.geekbrains.spring.one.services.CategoryService;
import ru.geekbrains.spring.one.services.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String showAllProductsPage(Model model,
                                      @RequestParam (name = "minPrice", defaultValue = "0") int minPrice,
                                      @RequestParam (name = "p", defaultValue = "1") int pageIndex ,
                                      @RequestParam(name = "maxPrice", defaultValue =(Integer.MAX_VALUE)+"") int maxPrice,
                                      @RequestParam (name = "partOfTitle", defaultValue = "%") String partOfTitle) {
        if(pageIndex<1){
            pageIndex=1;
        }
       Page<Product> page = productService.findAllByPriceBetweenAndTitle(minPrice, maxPrice, "%"+partOfTitle+"%",pageIndex-1,10);
       model.addAttribute("page", page);
        return "index";
    }

    @GetMapping ("/category/{id}")
    public String showProductsByCategory(@PathVariable (name = "id") Long id, Model model) {
        List<Product> products = productService.findByCategory(id);
        model.addAttribute("products", products);
        model.addAttribute("category_id", products.get(0).getCategory().getTitle());
        return "products_by_category_info";
    }

    @GetMapping("/products/{id}")
    public String showProductInfo(@PathVariable(name = "id") Long id, Model model) {
        productService.findOneById(id).ifPresent(p -> model.addAttribute("product", p));
        return "product_info";
    }

    @GetMapping("/products/add")
    public String showCreateProductForm() {
        return "create_product_form";
    }

    @PostMapping("/products/add")
    public String createNewProduct(@RequestParam String title, @RequestParam int price, @RequestParam Long categoryId) {
       Product product = new Product();
       product.setTitle(title);
       product.setPrice(price);
       Category category1 = categoryService.findOneById(categoryId).get();
       product.setCategory(category1);
       productService.save(product);
        return "redirect:/";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/";
    }
}
