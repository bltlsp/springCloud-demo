package com.yhb.dao.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name="item")
public class Item extends BaseEntity {
	@Column(name="name")
    @ApiModelProperty(value = "名称")
	private String name;
	
	@Column(name="price")
    @ApiModelProperty(value = "价格")
	private BigDecimal price;
	
}
