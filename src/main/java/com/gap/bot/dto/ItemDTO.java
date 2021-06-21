package com.gap.bot.dto;

import com.gap.bot.model.Item;

import lombok.Data;

@Data
public class ItemDTO {
	
	private Float discount;
	private Long quantity;
	private Item item;
}
