package com.gap.bot.dto;

import java.util.List;

import lombok.Data;
@Data
public class OrderRequest {

	private Long customerId;
	private String  deliveryType;
	private String  address;
	private List<ItemDTO> items;
	
}
