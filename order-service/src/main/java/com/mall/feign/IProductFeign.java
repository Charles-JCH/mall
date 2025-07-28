package com.mall.feign;

import com.mall.api.R;
import com.mall.dto.ProductDto;
import com.mall.feign.fallback.ProductServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", fallback = ProductServiceFeignFallback.class)
@Primary
public interface IProductFeign {

    @GetMapping("/product/{id}")
    R<ProductDto> getProductById(@PathVariable("id") Integer id);
}
