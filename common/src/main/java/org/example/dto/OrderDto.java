package org.example.dto;

import lombok.Data;

@Data
public class OrderDto {
    /**
     * 订单id
     */
    private String id;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 订单状态
     */
    private Integer status;
}
