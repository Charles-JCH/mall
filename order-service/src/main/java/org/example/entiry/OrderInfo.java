package org.example.entiry;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("orders")
public class OrderInfo {
    /**
     * 订单token
     */
    private String orderToken;

    /**
     * 订单id
     */
    private String id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品单价
     */
    private BigDecimal price;

    /**
     * 订单总价
     */
    private BigDecimal totalPrice;

    /**
     * 订单状态
     */
    private Integer status;
}
