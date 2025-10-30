package manager;

import java.util.ArrayList;
import java.util.List;

import model.GardenItem;
import model.StoreItem;
import datacontroller.StoreDataController;

public class StoreManager {

	private static List<StoreItem> storeItems;
	private static int money;

	public static void init() {
		storeItems = StoreDataController.loadStoreItems(); // load from file
		if (storeItems == null) {
			storeItems = new ArrayList<>(); // if no file, create new 
		}
	}

	private static List<StoreItem> getAllStoreItems() {
		return null;
	}

	/**
	 * Buy an item from the shop Deducts coins and adds item to garden
	 * 
	 * @param item The item to purchase
	 * @return boolean true if purchase successful, false if not enough coins
	 */
	public static boolean canBuy(GardenItem item) {
		if ( item.getPrice() <= money) return true;
		else return false;

	}
	
	public static int getMoney() {
		return money;
	}
	
	public static void setMoney(int moneyToAdd) {
		money = moneyToAdd;
	}

}
