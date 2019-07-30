package com.yhb.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {
	/**
	 * 主键
	 */
	@Id
	@Column(name = "id")
	//@GeneratedValue(generator = "UUID", strategy = GenerationType.IDENTITY)
	private String id;
	
	/**
	 * 修改时间
	 */
	@Column(name = "update_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	
	/**
	 * 修改人
	 */
	@Column(name = "update_by")
	private String updateBy;
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 创建人
	 */
	@Column(name = "create_by")
	private String createBy;
	/**
	 * 删除状态  0 正常 1 删除
	 */
	@Column(name = "is_del")
	private Integer isDel;
}
