package application.tools;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import application.items.*;

@Service
public class ItemService {

	
	/**
	 * 
	 */
	private ItemRepository iRepo;
	
	/**
	 * 
	 * @param uRepo
	 */
	public ItemService (ItemRepository uRepo) { this.iRepo = uRepo; }
	
	/**
	 * 
	 * @return
	 */
	public List<ItemsAbs> getItems() { return iRepo.findAll(); }
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<ItemsAbs> getItem(int id) { return iRepo.findById(id); }
}
