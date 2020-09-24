package payHistory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gamingmesh.jobs.api.JobsPaymentEvent;

import io.lumine.xikage.mythicmobs.MythicMobs;

public class PayHistoryMain extends JavaPlugin {
	Plugin plugin;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new PayListener(), this);
		this.getCommand("ph").setExecutor(new HistoryCommand());
		this.plugin = this;
	
		
	}

	private HashMap<UUID, PlayerData> players = new HashMap<UUID, PlayerData>();
	ItemStack luckyItem = null; 
	String itemName = "";
	HashMap<UUID, ItemStack> playersThatGetLoot = new HashMap<UUID, ItemStack>();
	HashMap<UUID, String> names = new HashMap<UUID, String>();
	public class PayListener implements Listener
	{
		@SuppressWarnings("deprecation")
		@EventHandler
		public void onPlayerPaid(JobsPaymentEvent event) {
			PlayerData data;
			if (players.containsKey(event.getPlayer().getUniqueId())) {
				data = players.get(event.getPlayer().getUniqueId());
			}
			else {
				data = new PlayerData();
			}
			double payment = event.getAmount();
			double boost = 0;
			double tempPayment1 = 0;
			double tempPayment2 = 0;
			if (Bukkit.getPlayer(event.getPlayer().getUniqueId()) == null) {
				return;
			}

			Player player = Bukkit.getPlayer(event.getPlayer().getUniqueId());
			double base = 0.1;
			double added = 0;
			double donor = 0;
			if (player.hasPermission("CrunchJobs.05")) {
				donor = 0.05;
			}
			if (player.hasPermission("CrunchJobs.10")) {
				donor = 0.1;
			}
			if (player.hasPermission("CrunchJobs.15")) {
				donor = 0.15;
			}
			if (player.hasPermission("CrunchJobs.20")) {
				donor = 0.2;
			}
			if (player.hasPermission("CrunchJobs.25")) {
				donor = 0.25;
			}
			if (player.hasPermission("CrunchJobs.30")) {
				donor = 0.3;
			}
			if (player.hasPermission("CrunchRelics.10")) {
				added = 0.1;
			}
			if (player.hasPermission("CrunchRelics.20")) {
				added = 0.2;
			}
			if (player.hasPermission("CrunchRelics.30")) {
				added = 0.3;
			}
			if (player.hasPermission("CrunchRelics.40")) {
				added = 0.4;
			}
			if (player.hasPermission("CrunchRelics.50")) {
				added = 0.4;
			}
			if (Bukkit.getPlayer(event.getPlayer().getUniqueId()).getInventory().getItemInMainHand() != null) {
				//do checks for bonus moneys
				boost = 0;
				ItemStack item = Bukkit.getPlayer(event.getPlayer().getUniqueId()).getInventory().getItemInMainHand();
				if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
					for (String s : item.getItemMeta().getLore()) {
						switch (s.toLowerCase()) {
						default:
							break;
						case "§55% jobs money boost":
							boost += 0.05;
							break;
						case "§510% jobs money boost":
							boost += 0.1;
							break;
						case "§515% jobs money boost":
							boost += 0.15;
							break;
						case "§520% jobs money boost":
							boost += 0.2;
							break;
						case "§525% jobs money boost":
							boost += 0.25;
							break;
						case "§550% jobs money boost":
							boost += 0.5;
							break;
						case "5% jobs money boost":
							boost += 0.05;
							break;
						case "10% jobs money boost":
							boost += 0.1;
							break;
						case "15% jobs money boost":
							boost += 0.15;
							break;
						case "20% jobs money boost":
							boost += 0.2;
							break;
						case "25% jobs money boost":
							boost += 0.25;
							break;
						case "50% jobs money boost":
							boost += 0.5;
							break;
						case "§55% jobs money boost while in offhand":
							boost += 0.05;
							break;
						case "§510% jobs money boost while in offhand":
							boost += 0.1;
							break;
						case "§515% jobs money boost while in offhand":
							boost += 0.15;
							break;
						case "§520% jobs money boost while in offhand":
							boost += 0.2;
							break;
						case "§525% jobs money boost while in offhand":
							boost += 0.25;
							break;
						case "§550% jobs money boost while in offhand":
							boost += 0.5;
							break;
						}
					}

				}
				if (boost > 0) {
					tempPayment1 = payment * boost;
				}
			}
			if (Bukkit.getPlayer(event.getPlayer().getUniqueId()).getInventory().getItemInOffHand() != null) {
				//do checks for bonus moneys
				ItemStack item = Bukkit.getPlayer(event.getPlayer().getUniqueId()).getInventory().getItemInOffHand();
				boost = 0;
				if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
					for (String s : item.getItemMeta().getLore()) {
						switch (s) {
						default:
							break;
						case "§55% jobs money boost":
							boost += 0.05;
							break;
						case "§510% jobs money boost":
							boost += 0.1;
							break;
						case "§515% jobs money boost":
							boost += 0.15;
							break;
						case "§520% jobs money boost":
							boost += 0.2;
							break;
						case "§525% jobs money boost":
							boost += 0.25;
							break;
						case "§550% jobs money boost":
							boost += 0.5;
							break;
						case "5% jobs money boost":
							boost += 0.05;
							break;
						case "10% jobs money boost":
							boost += 0.1;
							break;
						case "15% jobs money boost":
							boost += 0.15;
							break;
						case "20% jobs money boost":
							boost += 0.2;
							break;
						case "25% jobs money boost":
							boost += 0.25;
							break;
						case "50% jobs money boost":
							boost += 0.5;
							break;
						case "§55% jobs money boost while in offhand":
							boost += 0.05;
							break;
						case "§510% jobs money boost while in offhand":
							boost += 0.1;
							break;
						case "§515% jobs money boost while in offhand":
							boost += 0.15;
							break;
						case "§520% jobs money boost while in offhand":
							boost += 0.2;
							break;
						case "§525% jobs money boost while in offhand":
							boost += 0.25;
							break;
						case "§550% jobs money boost while in offhand":
							boost += 0.5;
							break;
						}

					}
				}
				if (boost > 0) {
					tempPayment2 = payment * boost;
				}
			}	
			double randDouble = Math.random();

			if (donor > 0) {
				payment = payment + (payment * donor);
			}
			payment = payment + tempPayment1 + tempPayment2;
	
			data.addToHistory(payment);
			players.put(event.getPlayer().getUniqueId(), data);
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String numberAsString = decimalFormat.format(tempPayment1 + tempPayment2);
			String baseAmnt = decimalFormat.format(event.getAmount());
			String donorAmnt = decimalFormat.format(event.getAmount() * donor);
			event.setAmount(payment);
			Bukkit.getPlayer(event.getPlayer().getUniqueId()).sendMessage("");
			Bukkit.getPlayer(event.getPlayer().getUniqueId()).sendMessage("§7Jobs payment : §6" + baseAmnt + " " + "§7Item Bonus : §6" + numberAsString);
			if (donor > 0) {
				Bukkit.getPlayer(event.getPlayer().getUniqueId()).sendMessage("§7Premium Bonus : §6" + donorAmnt);
			}
	
			Bukkit.getPlayer(event.getPlayer().getUniqueId()).sendMessage("");

			boolean rewarded = false;

			base += added;
			
			if(randDouble <= base) {
				//10% chance of getting an item
				System.out.println(base);
				randDouble = Math.random();
			
				if(randDouble <= 0.1) {
					if (rewarded) {
						return;
					}
					rewarded = true;

				
					Double randDouble2 = Math.random();

					if(randDouble2 <= 0.2) {
						if (rewarded) {
							return;
						}
						else {
							luckyItem = new ItemStack(Material.EMERALD, 1);
							ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.EMERALD);
							meta.setCustomModelData(2);
							meta.setDisplayName("§dRusty Key§f");
							luckyItem.setDurability((short) 0);
							ArrayList<String> lore = new ArrayList<String>();
							lore.add("§6Right click to open");
							meta.setLore(lore);
							meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
							luckyItem.setItemMeta(meta);
							luckyItem.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 10);
							
							itemName = ("§6§lRusty Key");
							playersThatGetLoot.put(event.getPlayer().getUniqueId(), luckyItem);
							names.put(event.getPlayer().getUniqueId(), itemName);
						}
					}
					else {
					luckyItem =	new ItemStack(Material.SLIME_BALL, 2);
					ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.SLIME_BALL);
					meta.setCustomModelData(2);
					meta.setDisplayName("§6Silver Relic");
					ArrayList<String> lore = new ArrayList<String>();
					lore.add("§3Tier 2");
					meta.setLore(lore);
					luckyItem.setItemMeta(meta);
					luckyItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					
					itemName = ("§6§l2 Silver Relics");
					playersThatGetLoot.put(event.getPlayer().getUniqueId(), luckyItem);
					names.put(event.getPlayer().getUniqueId(), itemName);
					}
				}
				if(randDouble <= 1) {
					randDouble = Math.random();
				if(randDouble <= 0.5) {
					if (rewarded) {
						return;
					}
					rewarded = true;
				

				
					luckyItem =	new ItemStack(Material.SLIME_BALL, 5);
					ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.SLIME_BALL);
					meta.setCustomModelData(1);
					meta.setDisplayName("§6Bronze Relic");
					ArrayList<String> lore = new ArrayList<String>();
					lore.add("§3Tier 1");
					meta.setLore(lore);
					luckyItem.setItemMeta(meta);
					luckyItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
					itemName = ("§6§l5 Bronze Relics");
					playersThatGetLoot.put(event.getPlayer().getUniqueId(), luckyItem);
					names.put(event.getPlayer().getUniqueId(), itemName);
					
				 }
				else {
					if (rewarded) {
						return;
					}
					rewarded = true;
						

						itemName = ("§6§lA Repair Scroll");
						luckyItem = new ItemStack(Material.PAPER, 1);
						ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.PAPER);
						meta.setDisplayName("§3§lRepair Scroll");
						ArrayList<String> lore = new ArrayList<String>();
						lore.add("§6Drag onto an item to repair");
						lore.add("§6it for 400 durability.");
						lore.add("§6Consumed on use.");
						lore.add("§6§l");
						meta.setLore(lore);
						luckyItem.setItemMeta(meta);
						luckyItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
						playersThatGetLoot.put(event.getPlayer().getUniqueId(), luckyItem);
						names.put(event.getPlayer().getUniqueId(), itemName);
					}
				}
				}
				
				
			
			if (playersThatGetLoot.containsKey(event.getPlayer().getUniqueId())) {
			new BukkitRunnable() {

				@Override
				public void run() {
		
						ItemStack item = playersThatGetLoot.get(event.getPlayer().getUniqueId());
						String name = names.get(event.getPlayer().getUniqueId());
//						if (player.getInventory().firstEmpty() > -1) {
//							player.getInventory().addItem(item);
//						}
//						else {
//							Bukkit.getPlayer(event.getPlayer().getUniqueId()).getWorld().dropItem(player.getLocation(), item);
//						}
					  	  HashMap<Integer, ItemStack> remainingItems = player.getInventory().addItem(item);
			        	  for (Integer key : remainingItems.keySet()) {
			        		    // ...
			        		  Bukkit.getPlayer(event.getPlayer().getUniqueId()).getWorld().dropItem(player.getLocation(), remainingItems.get(key));
			        		}
					Bukkit.getPlayer(event.getPlayer().getUniqueId()).sendMessage("");
					Bukkit.getPlayer(event.getPlayer().getUniqueId()).sendMessage("§3You were lucky and found " + name);
					Bukkit.getPlayer(event.getPlayer().getUniqueId()).sendMessage("");
					System.out.println(event.getPlayer().getName() + " " + name);			
					playersThatGetLoot.remove(event.getPlayer().getUniqueId());
		
					names.remove(event.getPlayer().getUniqueId());
				}
			}.runTask(plugin);
			}
		}
		


	}
	public class HistoryCommand implements CommandExecutor {

		// This method is called, when somebody uses our command
		@Override
		@EventHandler
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if (sender instanceof Player) {
				if (sender.isOp() && args != null && args.length > 0) {
				     if (Bukkit.getPlayer(args[0]) != null) {
			            	String name = Bukkit.getPlayer(args[0]).getName();
			            	UUID id = Bukkit.getPlayer(args[0]).getUniqueId();
			    			if (players.containsKey(id)) {
								PlayerData data = players.get(id);
								sender.sendMessage("§7****************************************");

								LinkedHashMap<String, String> history = new LinkedHashMap<String, String>();
								history = data.getHistory();
								for (Entry<String, String> entry : history.entrySet()) {
									String key = entry.getKey();
									Object value = entry.getValue();
									sender.sendMessage("§7" + key + " : §6" + value);
								}
								DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
								String numberAsString = decimalFormat.format(data.getAmountEarnt());
								sender.sendMessage("§7Total : §6" + numberAsString);
								sender.sendMessage("§7****************************************");
							}
							else {
								sender.sendMessage("§7No history to display yet.");
							}
			            }
			            else {
			            	sender.sendMessage("Cant find that player");
			            }
				     return true;
				}
				if (players.containsKey(((Player) sender).getUniqueId())) {
					PlayerData data = players.get(((Player) sender).getUniqueId());
					sender.sendMessage("§7****************************************");

					LinkedHashMap<String, String> history = new LinkedHashMap<String, String>();
					history = data.getHistory();
					for (Entry<String, String> entry : history.entrySet()) {
						String key = entry.getKey();
						Object value = entry.getValue();
						sender.sendMessage("§7" + key + " : §6" + value);
					}
					DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
					String numberAsString = decimalFormat.format(data.getAmountEarnt());
					sender.sendMessage("§7Total : §6" + numberAsString);
					sender.sendMessage("§7****************************************");
				}
				else {
					sender.sendMessage("§7No history to display yet.");
				}
			}
			return true;
		}
	}
}
