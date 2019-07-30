package com.yhb.dao.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name="`order`")
public class Order extends BaseEntity {
	@Column(name="order_code")
    @ApiModelProperty(value = "订单编号")
	private String orderCode;
	
	@Column(name="order_name")
    @ApiModelProperty(value = "订单名称")
	private String orderName;
	
	@Transient
	@ApiModelProperty(value = "商品详情")
	private List<Item> itemList;
}
