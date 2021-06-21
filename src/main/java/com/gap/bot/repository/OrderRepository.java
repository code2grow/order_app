package com.gap.bot.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gap.bot.model.Order; 


public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	List<Order> findByCustomerId(Long cusomterId);
	List<Order> findByCustomerIdAndOrderId(Long cusomterId,Long OrderId);
	List<Order> findByCustomerIdAndOrderIdIn(Long cusomterId,List<Long> orders);
	Order findByOrderId(Long OrderId);
	
	
	
}
