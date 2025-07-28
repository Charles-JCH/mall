package org.example.fallback;

import org.example.dto.ProductDto;
import org.example.feign.IProductFeign;
import org.example.vo.R;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceFeignFallback implements IProductFeign {
    @Override
    public R<ProductDto> getProductById(Integer id) {
        return R.error(500, "Product service error");
    }
}
