package com.gap.bot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gap.bot.dto.ItemDTO;
import com.gap.bot.dto.OrderRequest;
import com.gap.bot.dto.OrderResponseDTO;
import com.gap.bot.model.Item;
import com.gap.bot.model.Order;
import com.gap.bot.model.OrderItem;
import com.gap.bot.repository.ItemRepository;
import com.gap.bot.repository.OrderItemsMapping;
import com.gap.bot.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	OrderItemsMapping orderItemsMapping;
	
	public Long createService(OrderRequest request) {
		// save order
		Order order = Order.builder()
				        .deliveryType(request.getDeliveryType())
				        .totalAmount(getTotalAmount(request.getItems()))
				        .customerId(request.getCustomerId())
				        .address(request.getAddress())
				        .build();
		Order responseOrder = orderRepository.save(order); // SAVE IN order TABLE
		saveMappings(request, responseOrder.getOrderId()); // SAVE IN order_items TABLE
		return responseOrder.getOrderId();
	}
	
	public List<OrderResponseDTO> getOrdersByCustomeridAndOrders(Long customerId, List<Long> orders) {
		//validate orderIds throw error
		List<Order> ordersForCustomer = orderRepository.findByCustomerIdAndOrderIdIn(customerId,orders);
		if(ordersForCustomer.size()!=orders.size()) {
		     throw new RuntimeException("Provide Valid orderIds");
		}else {
			return getOrdersResponse(ordersForCustomer);
		}
		
	}
	
	public List<OrderResponseDTO> getOrdersByCustomerid(Long customerId) {
		List<Order> ordersForCustomer = orderRepository.findByCustomerId(customerId);
		return getOrdersResponse(ordersForCustomer);
	}
	
	public List<OrderResponseDTO> getOrdersResponse(List<Order> ordersForCustomer){
		return ordersForCustomer.stream().map(order -> {
			OrderResponseDTO dto = getCompleteOrder(order.getOrderId());
			return dto;
		}).collect(Collectors.toList());
	}
	
	
    public OrderResponseDTO getCompleteOrder(Long orderId) {
    	Order order = orderRepository.findByOrderId(orderId);
    	List<OrderItem> orderItems = orderItemsMapping.findByOrderId(orderId);
    	
    	List<ItemDTO> itemList = new ArrayList<ItemDTO>();
		List<Long> itemIds = orderItems.stream().map(orderItem -> {
			//set quanitty and discount to return itemDTO;
			ItemDTO outDto = new ItemDTO();
			outDto.setDiscount(orderItem.getDiscount());
			outDto.setQuantity(orderItem.getQuantity());
			itemList.add(outDto);
			return orderItem.getItemId();
		}).collect(Collectors.toList());
    	List<Item> items = itemRepository.findByItemIdIn(itemIds);
    	//set items to full IteamDTO
    	for(int i=0;i<items.size();i++) {
    		ItemDTO dtoItem = itemList.get(i);
    		dtoItem.setItem(items.get(i));
    	}
    	
    	OrderResponseDTO returnOrder = new OrderResponseDTO();
    	returnOrder.setOrderId(order.getOrderId());
    	returnOrder.setDeliveryType(order.getDeliveryType());
    	returnOrder.setAddress(order.getAddress());
    	returnOrder.setItems(itemList);// change this
    	returnOrder.setDeliveryType(order.getDeliveryType());
    	returnOrder.setTotalAmount(order.getTotalAmount());
    	
    	return returnOrder;
    }
	
	float getTotalAmount(List<ItemDTO> items) {
		Optional<Float> total = items.stream().map(item -> {
			Item newIteam = item.getItem();
				float cost_per_item = newIteam.getPrice() - (item.getDiscount() * newIteam.getPrice() / 100);
				float itemprice = cost_per_item * ((float) item.getQuantity());
				return itemprice;
		}).reduce(Float::sum);
		System.out.println(total);
		if (total.isPresent())
			return total.get();
		else
			return 0;
	}
	
	void saveMappings(OrderRequest orderRequest, Long orderId) {
		orderRequest.getItems().stream().forEach(itemDTO -> {
			Item ui_item = itemDTO.getItem();
			if (ui_item != null) {
				Item dbItem = Item.builder().price(ui_item.getPrice()).name(ui_item.getName())
						.description(ui_item.getDescription()).inStock(ui_item.isInStock()).build();
				Item savedItem=itemRepository.save(dbItem);
				OrderItem oi = OrderItem.builder().orderId(orderId).quantity(itemDTO.getQuantity()).itemId(savedItem.getItemId())
						.discount(itemDTO.getDiscount()).build();
				orderItemsMapping.save(oi);
			}

			

		});
	}
	
}
