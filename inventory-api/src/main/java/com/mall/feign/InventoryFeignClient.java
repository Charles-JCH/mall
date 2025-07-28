package com.mall.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "${api-name.inventory-service}")
public interface InventoryFeignClient {
}
