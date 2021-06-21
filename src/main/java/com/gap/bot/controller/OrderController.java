package com.gap.bot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gap.bot.dto.OrderRequest;
import com.gap.bot.dto.OrderResponseDTO;
import com.gap.bot.service.OrderService;

@RestController	
@RequestMapping(path="/api") 
@Validated
public class OrderController {
	
	@Autowired
	OrderService orderSerivce;

	@PostMapping(path="/order")
	public @ResponseBody Long createOrder(@RequestBody OrderRequest orderRequest) {
		return orderSerivce.createService(orderRequest);
	}
	
	@GetMapping(path="/order/{custId}")
    public @ResponseBody List<OrderResponseDTO> getOrderByCustomer(@PathVariable  Long custId) {
		return orderSerivce.getOrdersByCustomerid(custId);
	}
	
	@PostMapping(path="/order/{custId}")
    public @ResponseBody List<OrderResponseDTO> getOrderByCustomer(@RequestBody List<Long> orders,@PathVariable Long custId) {
		return orderSerivce.getOrdersByCustomeridAndOrders(custId, orders);
	}
	
	
}
