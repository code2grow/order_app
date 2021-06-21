package com.gap.bot.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.gap.bot.model.OrderItem; 


public interface OrderItemsMapping extends JpaRepository<OrderItem, Integer> {
	
//	@Query(value="select distinct item_id from cart_items where order_id = ?1",nativeQuery = true)
//	List<Long> findItemIdsByOrderId(Long orderId);
	
	List<OrderItem> findByOrderId(Long orderId);
}
