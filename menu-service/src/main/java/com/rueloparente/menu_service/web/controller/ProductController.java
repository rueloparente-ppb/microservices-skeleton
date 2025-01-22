package com.rueloparente.menu_service.web.controller;

import com.rueloparente.menu_service.domain.ProductService;
import com.rueloparente.menu_service.domain.exception.ProductNotFoundException;
import com.rueloparente.menu_service.dto.PagedResult;
import com.rueloparente.menu_service.dto.ProductWeb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<ProductWeb> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        return productService.findAll(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<ProductWeb> getProductByCode(@PathVariable("code") String code) {
        return productService
                .getProductByBode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
