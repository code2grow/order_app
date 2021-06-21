package com.gap.bot.repository;



import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gap.bot.model.Item; 


public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByItemIdIn(Collection<Long> itemIds);
}
