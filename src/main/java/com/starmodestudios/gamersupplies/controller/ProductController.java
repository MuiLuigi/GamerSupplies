package com.starmodestudios.gamersupplies.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.starmodestudios.gamersupplies.model.Product;
import com.starmodestudios.gamersupplies.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

   
    private static final List<String> CATEGORIES = List.of("Consoles", "Accessories", "Games");

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
@GetMapping
    public String listProducts(
            @RequestParam(required = false) String category,      // FILTER 1
            @RequestParam(required = false) Double maxPrice,        // FILTER 2
            @RequestParam(required = false, defaultValue = "name") String sortBy,    // SORT 1
            @RequestParam(required = false, defaultValue = "asc") String sortOrder,  // SORT 2
            Model model) {
        
        List<Product> products;
        
        // Apply filters
        if (category != null && !category.isEmpty() && maxPrice != null) {
            products = productService.findByCategoryAndMaxPrice(category, maxPrice);
        } else if (category != null && !category.isEmpty()) {
            products = productService.findByCategory(category);
        } else if (maxPrice != null) {
            products = productService.findByMaxPrice(maxPrice);
        } else {
            products = productService.findAll();
        }
        
        // Apply sorting
        Comparator<Product> comparator = switch (sortBy) {
            case "price" -> Comparator.comparing(Product::getPrice, Comparator.nullsLast(Comparator.naturalOrder()));
            case "quantity" -> Comparator.comparing(Product::getQuantity, Comparator.nullsLast(Comparator.naturalOrder()));
            default -> Comparator.comparing(p -> p.getName() != null ? p.getName().toLowerCase() : "");
        };
        
        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }
        
        products = products.stream().sorted(comparator).collect(Collectors.toList());
        
        // Add to model
        model.addAttribute("products", products);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("productCount", products.size());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedMaxPrice", maxPrice);
        model.addAttribute("selectedSortBy", sortBy);
        model.addAttribute("selectedSortOrder", sortOrder);
        model.addAttribute("pageTitle", "Products");
        
        return "products";
    }

    // GET /products/add — show empty add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("pageTitle", "Add Product");
        return "add-product";
    }

    // POST /products/add — handle form submission with validation
    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Re-populate the category list and return the form with errors
            model.addAttribute("categories", CATEGORIES);
            model.addAttribute("pageTitle", "Add Product");
            return "add-product";
        }

        productService.save(product);
        redirectAttributes.addFlashAttribute("successMessage",
                "Product \"" + product.getName() + "\" was added successfully!");
        return "redirect:/products";
    }
}
