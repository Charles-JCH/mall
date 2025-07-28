package com.mall.feign.fallback;

import com.mall.api.R;
import com.mall.dto.ProductDto;
import com.mall.feign.IProductFeign;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceFeignFallback implements IProductFeign {
    @Override
    public R<ProductDto> getProductById(Integer id) {
        return R.error(500, "Product service error");
    }
}
