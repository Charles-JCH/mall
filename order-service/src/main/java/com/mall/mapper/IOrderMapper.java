package com.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.entiry.OrderInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderMapper extends BaseMapper<OrderInfo> {
}
