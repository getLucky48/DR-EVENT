package events.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

public class Handler implements Listener, CommandExecutor{

	private Events plugin;
	
	public Handler(Events plugin) { this.plugin = plugin; }
	
	SettingsManager settings = SettingsManager.getInstance();	
	
	ArrayList<Player> cooldown = new ArrayList<Player>();
	
	int array_x [] = {54, 29, 8, 11, 24, 37, 10, 42, 13, 16, 35, 20, 50, 33, 44, 19, 22, 37, 3, 40, 52, -25, 38, 15, 8, 6, 46, 49, 13, -4, 34, 34, 57, 40, 34, 47, -7, 46, 8, 18, 2, 20, 39, -13, 47, 39, 52, 32, 16, -20};
	int array_y [] = {127,97,94,74,67,63,68,75,68,77,68,70,64,81,66,96,64,71,65,92,63,72,90,92,85,80,78,50,77,80,66,96,63,64,74,77,81,86,91,73,92,92,116,77,63,56,61,54,64,68};
	int array_z [] = {206,192,171,206,190,194,203,176,190,169,174,181,152,217,170,202,219,173,127,197,194,115,206,198,201,192,180,170,175,149,199,182,172,229,172,217,192,171,160,208,184,197,186,155,190,199,172,220,193,205};

	public void checker(Player p, int tx, int ty, int tz, PlayerInteractEvent e) {
		
		int amount = settings.getData().getInt(p.getName() + ".amount");
		
		/*�������� ������� ����� �������. ������*/
		for (int i = 0; i < amount; i++) {
			
			String check = settings.getData().getString(p.getName() + ".history." + i);
			
			String array_check [] = check.trim().split(" ");
			
			int x = Integer.valueOf(array_check[0]);
			
			int y = Integer.valueOf(array_check[1]);
			
			int z = Integer.valueOf(array_check[2]);
			
			if ((x == tx) && (y == ty) && (z == tz)) {
				
				p.sendMessage(ChatColor.YELLOW + "[�����]" + ChatColor.WHITE + " ��� ������ �� ��� �����.");
				
				p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
				
				return;
				
			}
			
		}
		/*�������� ������� ����� �������. �����*/
		
		/*�������� ����� ����� ������. ������*/
		for (int i = 0; i < 50; i++) {
			
			if ((array_x[i] == tx) && (array_y[i] == ty) && (array_z[i] == tz)) {
				
				p.sendMessage(ChatColor.YELLOW + "[�����]" + ChatColor.WHITE + " ������ �������!");
				
				if(p.hasPermission("dr.events.start") && (amount+1 <= 50)) {
					
					String message = "&a&l������� �������: " + (amount+1) + " �� 50";
					
					message = message.replace("&","\u00a7");
					
					ActionBarAPI.sendActionBar(p, message);
					
				}
				
				p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
				
				settings.getData().set(p.getName() + ".history." + amount, array_x[i] + " " + array_y[i] + " " + array_z[i]);
				
				settings.getData().set(p.getName() + ".amount", amount + 1);
				
				settings.saveData();
				
			}
			
		}
		/*�������� ����� ����� ������. �����*/
		
	}	
	
	@EventHandler
	public void move(PlayerMoveEvent e) {

		Player p = e.getPlayer();
		
		if (p.hasPermission("dr.events.rewardoff")) { return; }
		
		int X = (int) p.getLocation().getX();
		
		int Y = (int) p.getLocation().getY();
		
		int Z = (int) p.getLocation().getZ();
		
		String world = p.getWorld().getName();

		if ((world.compareToIgnoreCase("world") == 0)){
			
			/*����� ������� ����� ����� ������. ���� ���� ����� � ��� �������. ������*/
			if ((p.hasPermission("dr.events.start")) && (!p.hasPermission("dr.events.rewardoff") && (((X >= 14) && (X <= 24)) && ((Y >= 95) && (Y <= 105)) && ((Z >= 193) && (Z <= 203))) && (!cooldown.contains(p)))) {
				
				int value = settings.getData().getInt(p.getName() + ".amount");
				
					if (value >= 50) {
						
						p.sendMessage(ChatColor.GRAY + "----------------------------------------------------------");
						p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "�, ������. ��� � ��������������. ������, ��� ����.");
						p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "1 | ��� ��������� �����");						
						p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "2 | ��� ��������� ������������");
						p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "3 | 36 ������ ��������� ������");
						p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "������� ���-�� ����.");
						p.sendMessage(ChatColor.GRAY + "----------------------------------------------------------");
						p.sendMessage(ChatColor.YELLOW + "[�����] " + ChatColor.WHITE + "����� ������� �����, �������" + ChatColor.BLUE +" /event armor");
						p.sendMessage(ChatColor.YELLOW + "[�����] " + ChatColor.WHITE + "����� ������� �����������, �������" + ChatColor.GREEN +" /event tools");
						p.sendMessage(ChatColor.YELLOW + "[�����] " + ChatColor.WHITE + "����� ������� �����, �������" + ChatColor.GRAY +" /event blocks");
						p.sendMessage(ChatColor.GRAY + "----------------------------------------------------------");
						
						cooldown.add(p);
						
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							
							public void run() { cooldown.remove(p); }
							
						}, 120);
						
						return;
						
					}
					
			}
			/*����� ������� ����� ����� ������. ���� ���� ����� � ��� �������. �����*/
			
			/*����� - ������ ������*/
			if (!p.hasPermission("dr.events.start") && ( ((X >= 14) && (X <= 24)) && ((Y >= 95) && (Y <= 105)) && ((Z >= 193) && (Z <= 203))) && (!cooldown.contains(p))) {	
				
				Random rand = new Random();
				
				int value = rand.nextInt(5);
				
				if (value == 0) {
					
					p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��, ��!");
					p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��� ����� ���� ������.");
					p.sendMessage(ChatColor.YELLOW + "[�����] " + ChatColor.GRAY + "������ /event, ����� ������ �����");
					
				}
				
				if (value == 1) {
					
					p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "������!");
					p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "�� ��� �� �� ��� ������?.");
					p.sendMessage(ChatColor.YELLOW + "[�����] " + ChatColor.GRAY + "������ /event, ����� ������ �����");
					
				}
				
				if (value == 2) {
					
					p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��...");
					p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��� �� ������?!");
					p.sendMessage(ChatColor.YELLOW + "[�����] " + ChatColor.GRAY + "������ /event, ����� ������ �����");
					
				}
				
				if (value == 3) {
					
					p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "�, ������!");
					p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "�������� ��� � ����� �����?");
					p.sendMessage(ChatColor.YELLOW + "[�����] " + ChatColor.GRAY + "������ /event, ����� ������ �����");
					
				}
				
				if (value == 4) {
					
					p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "�� ��*��!");
					p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��*��� ���������!");
					p.sendMessage(ChatColor.YELLOW + "[�����] " + ChatColor.GRAY + "������ /event, ����� ������ �����");
					
				}

				cooldown.add(p);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					
					public void run() { cooldown.remove(p); }
					
				}, 120);
				
			}
			/*����� - ������ �����*/
			
			/*��������� ����� ������ �������. ������*/
			if (p.hasPermission("dr.events.start") && ( ((X >= 14) && (X <= 24)) && ((Y >= 95) && (Y <= 105)) && ((Z >= 193) && (Z <= 203))) && (!cooldown.contains(p))) {
				
				int amount = settings.getData().getInt(p.getName() + ".amount");
				
				/*���� ������ ��������. ������*/
				if ((amount >= 50) && (p.hasPermission("dr.events.rewardoff"))) {
					
					Random rand = new Random();
					
					int value = rand.nextInt(5);
					
					if (value <= 0) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "������� �� ������.");}
					
					if (value == 1) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "������ �������! ������� ��� ���.");}
					
					if (value == 2) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "���� �� ��, ��� ������ � ���� ����������.");}
					
					if (value == 3) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��� �� ��� ������ �����.");}
					
					if (value == 4) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��� ��� �������.");}
					
					cooldown.add(p);
					
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						
						public void run() { cooldown.remove(p); }
						
					}, 120);

					
				}
				/*���� ������ ��������. �����*/

				/*���� ������ ������������. ������*/
				if ((amount < 50) && (!p.hasPermission("dr.events.rewardoff"))) {
					
					Random rand = new Random();
					
					int value = rand.nextInt(5);
					
					if (value <= 0) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��� ������?");}
					
					if (value == 1) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��� ���� ����?");}
					
					if (value == 2) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��... �� �����?");}
					
					if (value == 3) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��� �� � ���� ����� �����...");}
					
					if (value >= 4) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "� ������� �� �� ����� ��� ��� ������.");}
					
					cooldown.add(p);
					
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						
						public void run() { cooldown.remove(p); }
						
					}, 120);
					
					return;
					
				}
				/*���� ������ ������������. �����*/
				
			}
			/*��������� ����� ������ �������. �����*/
			
		}

	}

	@EventHandler
	public void click(PlayerInteractEvent e) {

		int value = settings.getData().getInt(e.getPlayer().getName() + ".amount");
		
		Player p = e.getPlayer();
		
		/*�������� ����� ���� ����� ������� ��� �� �����. ������*/
		if (value >= 50) {return;}	
		
		if (!p.hasPermission("dr.events.start")) {return;}
		/*�������� ����� ���� ����� ������� ��� �� �����. �����*/
		
		/*��������� �����, ���� ��������� ��� � ����������. ������*/
		if(e.getHand() == EquipmentSlot.OFF_HAND) {
			
			String world = e.getPlayer().getWorld().getName();
			
			if ((world.compareToIgnoreCase("world") == 0)){
				
				if(e.getClickedBlock() == null) {return;}
				
				int tx = e.getClickedBlock().getX();
				
				int ty = e.getClickedBlock().getY();
				
				int tz = e.getClickedBlock().getZ();
				
				checker(p, tx, ty, tz, e);
					
			}

		}
		/*��������� �����, ���� ��������� ��� � ����������. �����*/
		
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		
		/*����� ActionBar, ���� ������������ �����. ���� ������ �� �������, �� �� ��������� ����������. ������*/
		Player p = e.getPlayer();
		
		if(!p.hasPermission("dr.events.start")) {return;}
		
		int amount = settings.getData().getInt(p.getName() + ".amount");
		
		if(p.hasPermission("dr.events.start") && (amount < 50)) {
			
			String message = "&a&l������� �������: " + amount + " �� 50";
			
			message = message.replace("&","\u00a7");
			
			ActionBarAPI.sendActionBar(p, message);
			
		}
		/*����� ActionBar, ���� ������������ �����. ���� ������ �� �������, �� �� ��������� ����������. �����*/
		
	}
		
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = Bukkit.getPlayer(sender.getName());
		
		String world = p.getWorld().getName();
		
		int X = (int) p.getLocation().getX();
		
		int Y = (int) p.getLocation().getY();
		
		int Z = (int) p.getLocation().getZ();		
		
		/*������� EVENT. ����� �� �����. ������*/
		if (cmd.getName().equals("event") && (args.length == 0)) {
			
			/*������� EVENT. ������*/
			
			/*������� EVENT. ���� ����� � NPC. ������*/
				if ((((X >= 14) && (X <= 24)) && ((Y >= 95) && (Y <= 105)) && ((Z >= 193) && (Z <= 203)))){
					
					if (world.compareToIgnoreCase("world") != 0) {return true;}
					
					if(!p.hasPermission("dr.events.start")) {
						
						p.sendMessage(ChatColor.GREEN + p.getName()+ ": " + ChatColor.WHITE + "������. ����������� � ��� ��������.");
						
						String mes = "�, ����������, �������� ����� ���������. ��������� ���, ��� ��������� �������� �������� ��������. ������� ����������."
								+ 	 " ���������� ������������, ��� ������, ����������."
								+ 	 " �� ������� ����� ����� ������. ������� ����� �������� �� ���� 31 �����."
								+ 	 " ��� � �������, ������ ���������� � ���� ������������. ���� ����� �� �����."
				   				+  	 " �� �����, ������, ����� ������� �����... ������� ������: ���� ����!";
						
						p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + mes);
					
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + " " + p.getName() + " " + "add" + " " + "dr.events.start");
							
						settings.getData().set(p.getName() + ".amount", 0);
						
						settings.saveData();
						
						Bukkit.getConsoleSender().sendMessage("����� " + p.getName() + " ����� �����!");
						
						return true;
						
					}
					else {
						
						int amount = settings.getData().getInt(p.getName() + ".amount");
						
						String mess = "";
						
						if(amount % 10 == 1){mess=" ������ �� 50";}
						
						if(amount % 10 == 2){mess=" ������ �� 50";}
						
						if(amount % 10 == 3){mess=" ������ �� 50";}
						
						if(amount % 10 == 4){mess=" ������ �� 50";}
						
						if((amount % 10 >= 5) || (amount % 10 == 0)){mess=" ������� �� 50";}
						
						p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
						
						p.sendMessage(ChatColor.GRAY + "[�����]" + " ��������: " + ChatColor.WHITE + "��������� ������ ������!");
						
						p.sendMessage(ChatColor.GRAY + "[�����]" + " ��������: " + ChatColor.WHITE + "���� �� ������ " + amount + mess);
						
						p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");		
						
					}
					
				}				


				/*������� EVENT. ���� ����� � NPC. �����*/
			
				/*������� EVENT. ���� ������ �� NPC. ������*/
				else {
					
					/*������� EVENT. ���� ���� PEX. ������*/
					if (p.hasPermission("dr.events.start")) {
						
						int amount = settings.getData().getInt(p.getName() + ".amount");
						
						/*������� EVENT. ���� ������� ��� ������. ������*/
						if((amount >= 50) && (!p.hasPermission("dr.events.rewardoff"))) {
							
							p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
							
							p.sendMessage(ChatColor.GRAY + "[�����]" + " ��������: " + ChatColor.WHITE + "�, ������! ������� �� ��������!");
							
							p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
							
							return true;
							
						}
						
						if(amount >= 50){				
							
							p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
							
							p.sendMessage(ChatColor.GRAY + "[�����]" + " ��������: " + ChatColor.WHITE + "�, ������! ������� ��� ��� �� ������!");
							
							p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
							
							return true;
							
						}
						/*������� EVENT. ���� ������� ��� ������. �����*/
						
						/*������� EVENT. ���� �� ������� ������. ������*/
						String mess = "";
						
						if(amount % 10 == 1){mess=" ������ �� 50";}
						
						if(amount % 10 == 2){mess=" ������ �� 50";}
						
						if(amount % 10 == 3){mess=" ������ �� 50";}
						
						if(amount % 10 == 4){mess=" ������ �� 50";}
						
						if((amount % 10 >= 5) || (amount % 10 == 0)){mess=" ������� �� 50";}
						
						p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
						
						p.sendMessage(ChatColor.GRAY + "[�����]" + " ��������: " + ChatColor.WHITE + "��������� ������ ������!");
						
						p.sendMessage(ChatColor.GRAY + "[�����]" + " ��������: " + ChatColor.WHITE + "���� �� ������ " + amount + mess);
						
						p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
						
						/*������� EVENT. ���� �� ������� ������. �����*/
						
					}				
					/*������� EVENT. ���� ���� PEX. �����*/
					
					/*������� EVENT. ���� ��� PEX. ������*/
					if(!p.hasPermission("dr.events.start")) {
						
						p.sendMessage(ChatColor.YELLOW + "[�����]" + ChatColor.WHITE + " ����� ������ ������, ����� ����� ��������� ���������.");
						
						return true;
						
					}
					/*������� EVENT. ���� ��� PEX. �����*/
	
				}
				/*������� EVENT. ���� ������ �� NPC. �����*/
			

				/*������� EVENT. ������ ������. �����*/
				
				return true;
				
		}
		
			/*������� EVENT. �����*/
		
		int amount = settings.getData().getInt(p.getName() + ".amount");
		
		/*�������. ������*/
		
		/*������� �����. ������*/
		if (cmd.getName().equals("event") && (args.length == 1) && (args[0].compareToIgnoreCase("armor") == 0) && (amount >= 50) && (!p.hasPermission("dr.events.rewardoff"))) {
			
			//�������� ���������
			PlayerInventory pi = p.getInventory();
			
			int value = 0;
			
			for (int i = 0; i < (pi.getSize() - 5); i++) {
				
				ItemStack is = pi.getItem(i);
				
				if (is == null) { value = value + 1; }
			
			}
			
			X = (int) p.getLocation().getX();
			
			Y = (int) p.getLocation().getY();
			
			Z = (int) p.getLocation().getZ();
			
			if (((X >= 14) && (X <= 24)) && ((Y >= 95) && (Y <= 105)) && ((Z >= 193) && (Z <= 203))) {
				
				if (value < 5) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��... ���� �� �������� ���������� ����? �������� ���������."); return true;}
			
			}
			else{
				
				p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
				
				p.sendMessage(ChatColor.GRAY + "[�����]" + " ��������: " + ChatColor.WHITE + "�� �� � ��������. ��� � ���� �� ������� ������� ����?");
				
				p.sendMessage(ChatColor.GRAY + "[�����]" + " ��������: " + ChatColor.WHITE + "� �������� ����-���� �� ���������. ������� �� ��� � ������ ��.");
				
				p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
				
				return true;
				
			}
			//�������� ���������
			
			//�����		
			ItemStack item = new ItemStack(Material.DIAMOND_HELMET, 2);
			
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			item.addEnchantment(Enchantment.PROTECTION_FIRE, 4);
			item.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
			item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
			item.addEnchantment(Enchantment.MENDING, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			
			item.addEnchantment(Enchantment.OXYGEN, 3);
			item.addEnchantment(Enchantment.WATER_WORKER, 1);
			item.addEnchantment(Enchantment.THORNS, 3);
			
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.BLUE + "�����");
			List<String> lore = new ArrayList<String>();
			
			lore.add(" ");
			lore.add(" ");
			lore.add(ChatColor.WHITE + "��� ����� ������� ���� ������ �� ������� �����������.");
			lore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			lore.add(" ");
			lore.add(" ");
			lore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			lore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			
			itemmeta.setLore(lore);
			
			item.setItemMeta(itemmeta);
			
			pi.addItem(item);
			
			//�����	
			
			//����������
			item = new ItemStack(Material.DIAMOND_CHESTPLATE, 2);
			
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			item.addEnchantment(Enchantment.PROTECTION_FIRE, 4);
			item.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
			item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
			item.addEnchantment(Enchantment.MENDING, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			
			itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.BLUE + "����������");
			List<String> itemlore = new ArrayList<String>();
			
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "���� ���������� �������� �� ������-������� ���.");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			//����������
			
			//�����
			item = new ItemStack(Material.DIAMOND_LEGGINGS, 2);
			
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			item.addEnchantment(Enchantment.PROTECTION_FIRE, 4);
			item.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
			item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
			item.addEnchantment(Enchantment.MENDING, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			
			itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.BLUE + "�����");
			itemlore = new ArrayList<String>();
			
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "��� ����� ��� ����������. �� � �� �����?");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);			
			//�����
			
			//�����
			item = new ItemStack(Material.DIAMOND_BOOTS, 2);
			
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			item.addEnchantment(Enchantment.PROTECTION_FIRE, 4);
			item.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
			item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
			item.addEnchantment(Enchantment.MENDING, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			
			item.addEnchantment(Enchantment.PROTECTION_FALL, 4);
			item.addEnchantment(Enchantment.DEPTH_STRIDER, 3);
			item.addEnchantment(Enchantment.FROST_WALKER, 1);
			
			itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.BLUE + "�����");
			itemlore = new ArrayList<String>();
			
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "������ ������� ����� ������ ����� �����������!");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			
			itemmeta.setLore(itemlore);
			
			item.setItemMeta(itemmeta);
			
			pi.addItem(item);	
			
			//�����
			
			//������
			item = new ItemStack(Material.ELYTRA, 2);

			item.addEnchantment(Enchantment.MENDING, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			
			itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.BLUE + "������");
			itemlore = new ArrayList<String>();
			
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "���� �� ������? ��-�� ������ ������!");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			
			itemmeta.setLore(itemlore);
			
			item.setItemMeta(itemmeta);
			
			pi.addItem(item);	
			
			//������
			
			p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "������� �����. ����� � ��������� �� �����������.");
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + " " + p.getName() + " " + "add" + " " + "dr.events.rewardoff");
			
			return true;
			
		}
		/*������� �����. �����*/
		
		/*������� �����. ������*/
		if (cmd.getName().equals("event") && (args.length == 1) && (args[0].compareToIgnoreCase("blocks") == 0) && (amount >= 50) && (!p.hasPermission("dr.events.rewardoff"))) {
			
			//��������
			PlayerInventory pi = p.getInventory();
			
			int value = 0;
			
			for (int i = 0; i < (pi.getSize() - 5); i++) {
				
				ItemStack is = pi.getItem(i);
				
				if (is == null) { value = value + 1; }
			
			}
			
			X = (int) p.getLocation().getX();
			
			Y = (int) p.getLocation().getY();
			
			Z = (int) p.getLocation().getZ();
			
			if (((X >= 14) && (X <= 24)) && ((Y >= 95) && (Y <= 105)) && ((Z >= 193) && (Z <= 203)) && (world.compareToIgnoreCase("world") == 0)) {
				
				if (value < 36) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��... ���� �� �������� ���������� ����? �������� ���������."); return true;}
			
			}
			else{
				
				p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
				
				p.sendMessage(ChatColor.GRAY + "[�����]" + " ��������: " + ChatColor.WHITE + "�� �� � ��������. ��� � ���� �� ������� ������� ����?");
				
				p.sendMessage(ChatColor.GRAY + "[�����]" + " ��������: " + ChatColor.WHITE + "� �������� ����-���� �� ���������. ������� �� ��� � ������ ��.");
				
				p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
				
				return true;
				
			}
			
			//��������
			
			//�����
			ItemStack item = new ItemStack(Material.IRON_ORE, 128);
			ItemMeta itemmeta = item.getItemMeta();
			List<String> itemlore = new ArrayList<String>();
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);

			item = new ItemStack (Material.GOLD_ORE, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.COAL_ORE, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.LAPIS_ORE, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.QUARTZ, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.REDSTONE_ORE, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.SPONGE, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.WHITE_WOOL, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.BOOKSHELF, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.SEA_LANTERN, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.HAY_BLOCK, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.WHITE_SHULKER_BOX, 16);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.CLAY, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.GRASS, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.SLIME_BLOCK, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.BIRCH_LOG, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.BRICK, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.OBSIDIAN, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.BONE_BLOCK, 64);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			//�����
			
			p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "������� �����. ����� � ��������� �� �����.");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + " " + p.getName() + " " + "add" + " " + "dr.events.rewardoff");
			return true;
		}
		/*������� �����. �����*/
		
		/*������� �����������. ������*/		
		if (cmd.getName().equals("event") && (args.length == 1) && (args[0].compareToIgnoreCase("tools") == 0) && (amount >= 50) && (!p.hasPermission("dr.events.rewardoff"))) {
			//��������
			PlayerInventory pi = p.getInventory();
			int value = 0;
			
			for (int i = 0; i < (pi.getSize() - 5); i++) {
				
				ItemStack is = pi.getItem(i);
				if (is == null) {
					value = value + 1;
				  }
			
			}
			X = (int) p.getLocation().getX();
			Y = (int) p.getLocation().getY();
			Z = (int) p.getLocation().getZ();
			if (((X >= 14) && (X <= 24)) && ((Y >= 95) && (Y <= 105)) && ((Z >= 193) && (Z <= 203))) {
				if (value < 6) {p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "��... ���� �� �������� ���������� ����? �������� ���������."); return true;}
			}
			else{
				p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
				p.sendMessage(ChatColor.GRAY + "[�����]" + " ��������: " + ChatColor.WHITE + "�� �� � ��������. ��� � ���� �� ������� ������� ����?");
				p.sendMessage(ChatColor.GRAY + "[�����]" + " ��������: " + ChatColor.WHITE + "� �������� ����-���� �� ���������. ������� �� ��� � ������ ��.");
				p.sendMessage(ChatColor.GRAY + "[�����]" + " ...");
				return true;
			}
			//��������
			
			//���
			ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 2);

			item.addEnchantment(Enchantment.MENDING, 1);
			item.addEnchantment(Enchantment.DAMAGE_ALL, 5);
			item.addEnchantment(Enchantment.DAMAGE_ARTHROPODS, 5);
			item.addEnchantment(Enchantment.DAMAGE_UNDEAD, 5);
			item.addEnchantment(Enchantment.KNOCKBACK, 2);
			item.addEnchantment(Enchantment.FIRE_ASPECT, 2);
			item.addEnchantment(Enchantment.LOOT_BONUS_MOBS, 3);
			item.addEnchantment(Enchantment.SWEEPING_EDGE, 3);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.BLUE + "������");
			List<String> itemlore = new ArrayList<String>();
			
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "���������� ������");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);					
			//���
			
			item = new ItemStack(Material.DIAMOND_PICKAXE, 2);

			item.addEnchantment(Enchantment.MENDING, 1);
			item.addEnchantment(Enchantment.DIG_SPEED, 5);
			item.addEnchantment(Enchantment.SILK_TOUCH, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
			item.addEnchantment(Enchantment.DIG_SPEED, 5);
			     
			itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.BLUE + "��������� �����");
			itemlore = new ArrayList<String>();
			     
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "� ����� ������ � � ����� ������ �� ������!");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			     
		    itemmeta.setLore(itemlore);
		    item.setItemMeta(itemmeta);
		    pi.addItem(item);
			     

			     
		    item = new ItemStack(Material.DIAMOND_AXE, 2);
		    item.addEnchantment(Enchantment.MENDING, 1);
		    item.addEnchantment(Enchantment.DAMAGE_ALL, 5);
		    item.addEnchantment(Enchantment.DAMAGE_UNDEAD, 5);
		    item.addEnchantment(Enchantment.DAMAGE_ARTHROPODS, 5);
		    item.addEnchantment(Enchantment.DIG_SPEED, 1);
		    item.addEnchantment(Enchantment.SILK_TOUCH, 1);
		    item.addEnchantment(Enchantment.DURABILITY, 3);
		    item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
		      
		    itemmeta = item.getItemMeta();
		    itemmeta.setDisplayName(ChatColor.BLUE + "����� ���������");
		    itemlore = new ArrayList<String>();
		      
		    itemlore.add(" ");
		    itemlore.add(" ");
		    itemlore.add(ChatColor.WHITE + "����� ��� �����...");
		    itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
		    itemlore.add(" ");
		    itemlore.add(" ");
		    itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
		    itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			      
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			      
			item = new ItemStack(Material.DIAMOND_SHOVEL, 2);

			item.addEnchantment(Enchantment.DIG_SPEED, 1);
			item.addEnchantment(Enchantment.SILK_TOUCH, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
			       
			itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.BLUE + "������");
			itemlore = new ArrayList<String>();
			       
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "������: Digger Online Edition");
			itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
		       
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			       
			item = new ItemStack(Material.DIAMOND_HOE, 2);

	        item.addEnchantment(Enchantment.MENDING, 1);
	        item.addEnchantment(Enchantment.DURABILITY, 3);
			        
	        itemmeta = item.getItemMeta();
	        itemmeta.setDisplayName(ChatColor.BLUE + "������");
	        itemlore = new ArrayList<String>();
			        
	        itemlore.add(" ");
	        itemlore.add(" ");
	        itemlore.add(ChatColor.WHITE + "����������� ���� xD");
	        itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
	        itemlore.add(" ");
	        itemlore.add(" ");
	        itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
	        itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
	        
	        itemmeta.setLore(itemlore);
	        item.setItemMeta(itemmeta);
	        pi.addItem(item);
	  
	        item = new ItemStack(Material.FISHING_ROD, 2);
			      
	        item.addEnchantment(Enchantment.DURABILITY, 3);
	      	item.addEnchantment(Enchantment.LUCK, 3);
	      	item.addEnchantment(Enchantment.LURE, 3);
			         
	        itemmeta = item.getItemMeta();
	        itemmeta.setDisplayName(ChatColor.BLUE + "�������");
	        itemlore = new ArrayList<String>();
			         
	        itemlore.add(" ");
	        itemlore.add(" ");
	        itemlore.add(ChatColor.WHITE + "������������ �������");
	        itemlore.add(ChatColor.GOLD + "������� �� ��������� ��������� ������ " + p.getName() + ".");
	        itemlore.add(" ");
	        itemlore.add(" ");
         	itemlore.add(ChatColor.DARK_RED + "�����: \"��� �����, ������� ��������!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			         
			itemmeta.setLore(itemlore);
			
         	item.setItemMeta(itemmeta);
         	
         	pi.addItem(item);
         	
         	p.sendMessage(ChatColor.DARK_AQUA + "��������: " + ChatColor.WHITE + "������� �����. ����� � ��������� �� �����.");

			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + " " + p.getName() + " " + "add" + " " + "dr.events.rewardoff");
			
			return true;
			
		}
		/*������� �����������. ������*/
		
		/*�������. �����*/
		
		/*�������������. ������*/
		if (cmd.getName().equals("event") && (args.length == 1) && (args[0].compareToIgnoreCase("armor") == 0) && (amount >= 50) && (p.hasPermission("dr.events.rewardoff"))) {
			
			p.sendMessage(ChatColor.YELLOW + "[�����]" + " " + ChatColor.WHITE + "�� ��� ������� �������.");
			
			return true;
		
		}
		
		if (cmd.getName().equals("event") && (args.length == 1) && (args[0].compareToIgnoreCase("blocks") == 0) && (amount >= 50) && (p.hasPermission("dr.events.rewardoff"))) {
			
			p.sendMessage(ChatColor.YELLOW + "[�����]" + " " + ChatColor.WHITE + "�� ��� ������� �������.");
			
			return true;
		
		}
		
		if (cmd.getName().equals("event") && (args.length == 1) && (args[0].compareToIgnoreCase("tools") == 0) && (amount >= 50) && (p.hasPermission("dr.events.rewardoff"))) {
			
			p.sendMessage(ChatColor.YELLOW + "[�����]" + " " + ChatColor.WHITE + "�� ��� ������� �������.");
			
			return true;
		
		}
		
		if (cmd.getName().equals("eventreload") && (p.hasPermission("eventadmin"))) {
			
			settings.reloadConfig();
			
			settings.reloadData();
			
			return true;
		
		}
		
		/*�������������. �����*/
		
		return false;
	
	}
	
}
