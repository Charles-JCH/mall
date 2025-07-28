package com.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.entity.ProductInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IProductMapper extends BaseMapper<ProductInfo> {

}
