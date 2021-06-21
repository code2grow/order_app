package com.gap.bot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Immutable
@Data
@Builder
@Table(name = "cart_items")
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
	private Long orderId;
	private Long itemId;
	private Long quantity;
	private float discount; 
	
}
