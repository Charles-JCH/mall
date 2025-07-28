package com.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.entity.ProductInfo;

import java.util.List;

public interface IProductService extends IService<ProductInfo> {
    Page<ProductInfo> getAllProduct(Integer page, Integer size);

    ProductInfo getProductById(Integer id);

    List<ProductInfo> getProductByName(String name);

    boolean addProduct(ProductInfo productInfo);

    boolean updateProductById(Integer id, ProductInfo product);

    boolean deleteProductById(Integer id);
}
