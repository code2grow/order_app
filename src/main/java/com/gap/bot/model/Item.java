package com.gap.bot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue
	private Long itemId;
	private float  price;
	private String description;
	private String name;
    private boolean inStock;
}
