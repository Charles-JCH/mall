package com.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.api.R;
import com.mall.entity.ProductInfo;
import com.mall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public R<List<ProductInfo>> getAllProduct(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<ProductInfo> productInfoIPage = productService.getAllProduct(page, size);
        if (productInfoIPage == null) {
            return R.error(400, "Product not found");
        }
        return R.ok(productInfoIPage.getRecords());
    }

    @GetMapping("/{id}")
    public R<ProductInfo> getProductById(@PathVariable Integer id) {
        ProductInfo product = productService.getProductById(id);
        if (product == null) {
            return R.error(400, "Product not found");
        }
        return R.ok(product);
    }

    @GetMapping("/name")
    public R<List<ProductInfo>> getProductByName(@RequestParam String name) {
        List<ProductInfo> product = productService.getProductByName(name);
        if (product == null) {
            return R.error(400, "Product not found");
        }
        return R.ok(product);
    }

    @PostMapping("/add")
    public R<String> addProduct(@RequestBody ProductInfo productInfo) {
        boolean result = productService.addProduct(productInfo);
        if (!result) {
            return R.error(400, "Error adding product");
        }
        return R.ok("Product added");
    }

    @PutMapping("/{id}")
    public R<String> updateProductById(@PathVariable Integer id, @RequestBody ProductInfo productInfo) {
        boolean result = productService.updateProductById(id, productInfo);
        if (!result) {
            return R.error(400, "Error updating product");
        }
        return R.ok("Product updated");
    }

    @DeleteMapping("/{id}")
    public R<String> deleteProductById(@PathVariable Integer id) {
        boolean result = productService.deleteProductById(id);
        if (!result) {
            return R.error(400, "Error deleting product");
        }
        return R.ok("Product deleted");
    }
}
