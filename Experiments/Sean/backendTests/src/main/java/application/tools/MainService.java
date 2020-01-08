package application.tools;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import application.items.*;
import application.users.*;

@Service
public class MainService {
	
	/**
	 * 
	 */
	private UserRepository uRepo;
	
	/**
	 * 
	 */
	private ItemRepository iRepo;
	
	/**
	 * 
	 * @param uRepo
	 */
	public MainService (UserRepository uRepo, ItemRepository iRepo) {
		
		this.uRepo = uRepo;
		this.iRepo = iRepo;
	}
}
