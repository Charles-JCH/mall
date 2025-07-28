package org.example.feign;

import org.example.dto.ProductDto;
import org.example.fallback.ProductServiceFeignFallback;
import org.example.vo.R;
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
