package com.gap.bot.dto;

import java.util.List;

import com.gap.bot.model.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderResponseDTO {
	private Long orderId;
	private String  deliveryType;
	private String address;
	private float totalAmount;
	private List<ItemDTO> items;
}
