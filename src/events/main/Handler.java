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
		
		/*Проверка истории сбора деталей. НАЧАЛО*/
		for (int i = 0; i < amount; i++) {
			
			String check = settings.getData().getString(p.getName() + ".history." + i);
			
			String array_check [] = check.trim().split(" ");
			
			int x = Integer.valueOf(array_check[0]);
			
			int y = Integer.valueOf(array_check[1]);
			
			int z = Integer.valueOf(array_check[2]);
			
			if ((x == tx) && (y == ty) && (z == tz)) {
				
				p.sendMessage(ChatColor.YELLOW + "[Ивент]" + ChatColor.WHITE + " Эту деталь ты уже нашел.");
				
				p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
				
				return;
				
			}
			
		}
		/*Проверка истории сбора деталей. КОНЕЦ*/
		
		/*Проверка сбора новой детали. НАЧАЛО*/
		for (int i = 0; i < 50; i++) {
			
			if ((array_x[i] == tx) && (array_y[i] == ty) && (array_z[i] == tz)) {
				
				p.sendMessage(ChatColor.YELLOW + "[Ивент]" + ChatColor.WHITE + " Деталь найдена!");
				
				if(p.hasPermission("dr.events.start") && (amount+1 <= 50)) {
					
					String message = "&a&lСобрано деталей: " + (amount+1) + " из 50";
					
					message = message.replace("&","\u00a7");
					
					ActionBarAPI.sendActionBar(p, message);
					
				}
				
				p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
				
				settings.getData().set(p.getName() + ".history." + amount, array_x[i] + " " + array_y[i] + " " + array_z[i]);
				
				settings.getData().set(p.getName() + ".amount", amount + 1);
				
				settings.saveData();
				
			}
			
		}
		/*Проверка сбора новой детали. КОНЕЦ*/
		
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
			
			/*Выбор награды после конца ивента. Если есть старт и нет награды. НАЧАЛО*/
			if ((p.hasPermission("dr.events.start")) && (!p.hasPermission("dr.events.rewardoff") && (((X >= 14) && (X <= 24)) && ((Y >= 95) && (Y <= 105)) && ((Z >= 193) && (Z <= 203))) && (!cooldown.contains(p)))) {
				
				int value = settings.getData().getInt(p.getName() + ".amount");
				
					if (value >= 50) {
						
						p.sendMessage(ChatColor.GRAY + "----------------------------------------------------------");
						p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "О, привет. Как и договаривались. Смотри, что есть.");
						p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "1 | Два комплекта брони");						
						p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "2 | Два комплекта инструментов");
						p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "3 | 36 стеков различных блоков");
						p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Выбирай что-то одно.");
						p.sendMessage(ChatColor.GRAY + "----------------------------------------------------------");
						p.sendMessage(ChatColor.YELLOW + "[Ивент] " + ChatColor.WHITE + "Чтобы забрать броню, пропиши" + ChatColor.BLUE +" /event armor");
						p.sendMessage(ChatColor.YELLOW + "[Ивент] " + ChatColor.WHITE + "Чтобы забрать инструменты, пропиши" + ChatColor.GREEN +" /event tools");
						p.sendMessage(ChatColor.YELLOW + "[Ивент] " + ChatColor.WHITE + "Чтобы забрать блоки, пропиши" + ChatColor.GRAY +" /event blocks");
						p.sendMessage(ChatColor.GRAY + "----------------------------------------------------------");
						
						cooldown.add(p);
						
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							
							public void run() { cooldown.remove(p); }
							
						}, 120);
						
						return;
						
					}
					
			}
			/*Выбор награды после конца ивента. Если есть старт и нет награды. КОНЕЦ*/
			
			/*Квест - помощи НАЧАЛО*/
			if (!p.hasPermission("dr.events.start") && ( ((X >= 14) && (X <= 24)) && ((Y >= 95) && (Y <= 105)) && ((Z >= 193) && (Z <= 203))) && (!cooldown.contains(p))) {	
				
				Random rand = new Random();
				
				int value = rand.nextInt(5);
				
				if (value == 0) {
					
					p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Эй, ты!");
					p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Мне нужна твоя помощь.");
					p.sendMessage(ChatColor.YELLOW + "[Ивент] " + ChatColor.GRAY + "Напиши /event, чтобы начать ивент");
					
				}
				
				if (value == 1) {
					
					p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Извини!");
					p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Не мог бы ты мне помочь?.");
					p.sendMessage(ChatColor.YELLOW + "[Ивент] " + ChatColor.GRAY + "Напиши /event, чтобы начать ивент");
					
				}
				
				if (value == 2) {
					
					p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Эх...");
					p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Что же делать?!");
					p.sendMessage(ChatColor.YELLOW + "[Ивент] " + ChatColor.GRAY + "Напиши /event, чтобы начать ивент");
					
				}
				
				if (value == 3) {
					
					p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "О, привет!");
					p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Поможешь мне с одним делом?");
					p.sendMessage(ChatColor.YELLOW + "[Ивент] " + ChatColor.GRAY + "Напиши /event, чтобы начать ивент");
					
				}
				
				if (value == 4) {
					
					p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Да бл*ть!");
					p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Еб*чий дирижабль!");
					p.sendMessage(ChatColor.YELLOW + "[Ивент] " + ChatColor.GRAY + "Напиши /event, чтобы начать ивент");
					
				}

				cooldown.add(p);
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					
					public void run() { cooldown.remove(p); }
					
				}, 120);
				
			}
			/*Квест - помощи КОНЕЦ*/
			
			/*Сообщения после начала инвента. НАЧАЛО*/
			if (p.hasPermission("dr.events.start") && ( ((X >= 14) && (X <= 24)) && ((Y >= 95) && (Y <= 105)) && ((Z >= 193) && (Z <= 203))) && (!cooldown.contains(p))) {
				
				int amount = settings.getData().getInt(p.getName() + ".amount");
				
				/*Если инвент закончен. НАЧАЛО*/
				if ((amount >= 50) && (p.hasPermission("dr.events.rewardoff"))) {
					
					Random rand = new Random();
					
					int value = rand.nextInt(5);
					
					if (value <= 0) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Спасибо за помощь.");}
					
					if (value == 1) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Привет дружище! Спасибо еще раз.");}
					
					if (value == 2) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Знал бы ты, как сильно я тебе благодарен.");}
					
					if (value == 3) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Мир не без добрых людей.");}
					
					if (value == 4) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Еще раз спасибо.");}
					
					cooldown.add(p);
					
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						
						public void run() { cooldown.remove(p); }
						
					}, 120);

					
				}
				/*Если инвент закончен. КОНЕЦ*/

				/*Если инвент продолжается. НАЧАЛО*/
				if ((amount < 50) && (!p.hasPermission("dr.events.rewardoff"))) {
					
					Random rand = new Random();
					
					int value = rand.nextInt(5);
					
					if (value <= 0) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Как успехи?");}
					
					if (value == 1) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Как идут дела?");}
					
					if (value == 2) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Ну... Ты скоро?");}
					
					if (value == 3) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Как же у меня болит спина...");}
					
					if (value >= 4) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Я надеюсь ты не забыл про наш уговор.");}
					
					cooldown.add(p);
					
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						
						public void run() { cooldown.remove(p); }
						
					}, 120);
					
					return;
					
				}
				/*Если инвент продолжается. КОНЕЦ*/
				
			}
			/*Сообщения после начала инвента. КОНЕЦ*/
			
		}

	}

	@EventHandler
	public void click(PlayerInteractEvent e) {

		int value = settings.getData().getInt(e.getPlayer().getName() + ".amount");
		
		Player p = e.getPlayer();
		
		/*Отменять клики если ивент пройден или не начат. НАЧАЛО*/
		if (value >= 50) {return;}	
		
		if (!p.hasPermission("dr.events.start")) {return;}
		/*Отменять клики если ивент пройден или не начат. КОНЕЦ*/
		
		/*Запустить чекер, если совпадают мир и координаты. НАЧАЛО*/
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
		/*Запустить чекер, если совпадают мир и координаты. КОНЕЦ*/
		
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		
		/*Вывод ActionBar, если продолжается ивент. Если инвент не начался, то не считывать количество. НАЧАЛО*/
		Player p = e.getPlayer();
		
		if(!p.hasPermission("dr.events.start")) {return;}
		
		int amount = settings.getData().getInt(p.getName() + ".amount");
		
		if(p.hasPermission("dr.events.start") && (amount < 50)) {
			
			String message = "&a&lСобрано деталей: " + amount + " из 50";
			
			message = message.replace("&","\u00a7");
			
			ActionBarAPI.sendActionBar(p, message);
			
		}
		/*Вывод ActionBar, если продолжается ивент. Если инвент не начался, то не считывать количество. КОНЕЦ*/
		
	}
		
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = Bukkit.getPlayer(sender.getName());
		
		String world = p.getWorld().getName();
		
		int X = (int) p.getLocation().getX();
		
		int Y = (int) p.getLocation().getY();
		
		int Z = (int) p.getLocation().getZ();		
		
		/*Команда EVENT. Вызов по радио. НАЧАЛО*/
		if (cmd.getName().equals("event") && (args.length == 0)) {
			
			/*Команда EVENT. НАЧАЛО*/
			
			/*Команда EVENT. Если рядом с NPC. НАЧАЛО*/
				if ((((X >= 14) && (X <= 24)) && ((Y >= 95) && (Y <= 105)) && ((Z >= 193) && (Z <= 203)))){
					
					if (world.compareToIgnoreCase("world") != 0) {return true;}
					
					if(!p.hasPermission("dr.events.start")) {
						
						p.sendMessage(ChatColor.GREEN + p.getName()+ ": " + ChatColor.WHITE + "Хорошо. Рассказывай в чем проблема.");
						
						String mes = "Я, собственно, командир этого дирижабля. Случилось так, что двигатель внезапно перестал работать. Причина неизвестна."
								+ 	 " Дальнейшее передвижение, как видишь, невозможно."
								+ 	 " На починку уйдет около месяца. Поэтому вылет назначен на ночь 31 марта."
								+ 	 " Как я понимаю, детали разбросаны в этих окрестностях. Тебе нужно их найти."
				   				+  	 " На борту, кстати, много всякого груза... Найдешь детали: груз твой!";
						
						p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + mes);
					
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + " " + p.getName() + " " + "add" + " " + "dr.events.start");
							
						settings.getData().set(p.getName() + ".amount", 0);
						
						settings.saveData();
						
						Bukkit.getConsoleSender().sendMessage("Игрок " + p.getName() + " начал ивент!");
						
						return true;
						
					}
					else {
						
						int amount = settings.getData().getInt(p.getName() + ".amount");
						
						String mess = "";
						
						if(amount % 10 == 1){mess=" деталь из 50";}
						
						if(amount % 10 == 2){mess=" детали из 50";}
						
						if(amount % 10 == 3){mess=" детали из 50";}
						
						if(amount % 10 == 4){mess=" детали из 50";}
						
						if((amount % 10 >= 5) || (amount % 10 == 0)){mess=" деталей из 50";}
						
						p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
						
						p.sendMessage(ChatColor.GRAY + "[Рация]" + " Командир: " + ChatColor.WHITE + "Продолжай искать детали!");
						
						p.sendMessage(ChatColor.GRAY + "[Рация]" + " Командир: " + ChatColor.WHITE + "Пока ты собрал " + amount + mess);
						
						p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");		
						
					}
					
				}				


				/*Команда EVENT. Если рядом с NPC. КОНЕЦ*/
			
				/*Команда EVENT. Если далеко от NPC. НАЧАЛО*/
				else {
					
					/*Команда EVENT. Если есть PEX. НАЧАЛО*/
					if (p.hasPermission("dr.events.start")) {
						
						int amount = settings.getData().getInt(p.getName() + ".amount");
						
						/*Команда EVENT. Если Собраны все детали. НАЧАЛО*/
						if((amount >= 50) && (!p.hasPermission("dr.events.rewardoff"))) {
							
							p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
							
							p.sendMessage(ChatColor.GRAY + "[Рация]" + " Командир: " + ChatColor.WHITE + "О, здоров! Приходи на наградой!");
							
							p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
							
							return true;
							
						}
						
						if(amount >= 50){				
							
							p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
							
							p.sendMessage(ChatColor.GRAY + "[Рация]" + " Командир: " + ChatColor.WHITE + "О, здоров! Спасибо еще раз за помощь!");
							
							p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
							
							return true;
							
						}
						/*Команда EVENT. Если Собраны все детали. КОНЕЦ*/
						
						/*Команда EVENT. Если не собраны детали. НАЧАЛО*/
						String mess = "";
						
						if(amount % 10 == 1){mess=" деталь из 50";}
						
						if(amount % 10 == 2){mess=" детали из 50";}
						
						if(amount % 10 == 3){mess=" детали из 50";}
						
						if(amount % 10 == 4){mess=" детали из 50";}
						
						if((amount % 10 >= 5) || (amount % 10 == 0)){mess=" деталей из 50";}
						
						p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
						
						p.sendMessage(ChatColor.GRAY + "[Рация]" + " Командир: " + ChatColor.WHITE + "Продолжай искать детали!");
						
						p.sendMessage(ChatColor.GRAY + "[Рация]" + " Командир: " + ChatColor.WHITE + "Пока ты собрал " + amount + mess);
						
						p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
						
						/*Команда EVENT. Если не собраны детали. КОНЕЦ*/
						
					}				
					/*Команда EVENT. Если есть PEX. КОНЕЦ*/
					
					/*Команда EVENT. Если нет PEX. НАЧАЛО*/
					if(!p.hasPermission("dr.events.start")) {
						
						p.sendMessage(ChatColor.YELLOW + "[Ивент]" + ChatColor.WHITE + " Чтобы начать инвент, нужно найти Командира дирижабля.");
						
						return true;
						
					}
					/*Команда EVENT. Если нет PEX. КОНЕЦ*/
	
				}
				/*Команда EVENT. Если далеко от NPC. КОНЕЦ*/
			

				/*Команда EVENT. Начало ивента. КОНЕЦ*/
				
				return true;
				
		}
		
			/*Команда EVENT. КОНЕЦ*/
		
		int amount = settings.getData().getInt(p.getName() + ".amount");
		
		/*Награда. НАЧАЛО*/
		
		/*Награда броня. НАЧАЛО*/
		if (cmd.getName().equals("event") && (args.length == 1) && (args[0].compareToIgnoreCase("armor") == 0) && (amount >= 50) && (!p.hasPermission("dr.events.rewardoff"))) {
			
			//Проверка инвентаря
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
				
				if (value < 5) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Хм... Куда ты собрался складывать вещи? Освободи инвентарь."); return true;}
			
			}
			else{
				
				p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
				
				p.sendMessage(ChatColor.GRAY + "[Рация]" + " Командир: " + ChatColor.WHITE + "Ну ты и странный. Как я тебе по воздуху передам вещи?");
				
				p.sendMessage(ChatColor.GRAY + "[Рация]" + " Командир: " + ChatColor.WHITE + "Я шататься туда-сюда не собираюсь. Приходи ко мне и забери их.");
				
				p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
				
				return true;
				
			}
			//Проверка инвентаря
			
			//Каска		
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
			itemmeta.setDisplayName(ChatColor.BLUE + "Каска");
			List<String> lore = new ArrayList<String>();
			
			lore.add(" ");
			lore.add(" ");
			lore.add(ChatColor.WHITE + "Эта каска защитит твою голову от внешних воздействий.");
			lore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			lore.add(" ");
			lore.add(" ");
			lore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			lore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			
			itemmeta.setLore(lore);
			
			item.setItemMeta(itemmeta);
			
			pi.addItem(item);
			
			//Каска	
			
			//Бронежилет
			item = new ItemStack(Material.DIAMOND_CHESTPLATE, 2);
			
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			item.addEnchantment(Enchantment.PROTECTION_FIRE, 4);
			item.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
			item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
			item.addEnchantment(Enchantment.MENDING, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			
			itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.BLUE + "Бронежилет");
			List<String> itemlore = new ArrayList<String>();
			
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "Этот бронежилет защищает от колото-резаных ран.");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			//Бронежилет
			
			//Штаны
			item = new ItemStack(Material.DIAMOND_LEGGINGS, 2);
			
			item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
			item.addEnchantment(Enchantment.PROTECTION_FIRE, 4);
			item.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
			item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
			item.addEnchantment(Enchantment.MENDING, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			
			itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.BLUE + "Штаны");
			itemlore = new ArrayList<String>();
			
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "Эти штаны без подворотов. Ты ж не петух?");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);			
			//Штаны
			
			//Берцы
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
			itemmeta.setDisplayName(ChatColor.BLUE + "Берцы");
			itemlore = new ArrayList<String>();
			
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "Такими берцами можно давить своих противников!");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			
			itemmeta.setLore(itemlore);
			
			item.setItemMeta(itemmeta);
			
			pi.addItem(item);	
			
			//Берцы
			
			//Элитры
			item = new ItemStack(Material.ELYTRA, 2);

			item.addEnchantment(Enchantment.MENDING, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			
			itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.BLUE + "Элитры");
			itemlore = new ArrayList<String>();
			
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "Люди не летают? Ну-ка одевай элитры!");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			
			itemmeta.setLore(itemlore);
			
			item.setItemMeta(itemmeta);
			
			pi.addItem(item);	
			
			//Элитры
			
			p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Хороший выбор. Лично я предпочел бы инструменты.");
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + " " + p.getName() + " " + "add" + " " + "dr.events.rewardoff");
			
			return true;
			
		}
		/*Награда броня. КОНЕЦ*/
		
		/*Награда блоки. НАЧАЛО*/
		if (cmd.getName().equals("event") && (args.length == 1) && (args[0].compareToIgnoreCase("blocks") == 0) && (amount >= 50) && (!p.hasPermission("dr.events.rewardoff"))) {
			
			//Проверка
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
				
				if (value < 36) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Хм... Куда ты собрался складывать вещи? Освободи инвентарь."); return true;}
			
			}
			else{
				
				p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
				
				p.sendMessage(ChatColor.GRAY + "[Рация]" + " Командир: " + ChatColor.WHITE + "Ну ты и странный. Как я тебе по воздуху передам вещи?");
				
				p.sendMessage(ChatColor.GRAY + "[Рация]" + " Командир: " + ChatColor.WHITE + "Я шататься туда-сюда не собираюсь. Приходи ко мне и забери их.");
				
				p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
				
				return true;
				
			}
			
			//Проверка
			
			//Блоки
			ItemStack item = new ItemStack(Material.IRON_ORE, 128);
			ItemMeta itemmeta = item.getItemMeta();
			List<String> itemlore = new ArrayList<String>();
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);

			item = new ItemStack (Material.GOLD_ORE, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.COAL_ORE, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.LAPIS_ORE, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.QUARTZ, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.REDSTONE_ORE, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.SPONGE, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.WHITE_WOOL, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.BOOKSHELF, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.SEA_LANTERN, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.HAY_BLOCK, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.WHITE_SHULKER_BOX, 16);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.CLAY, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.GRASS, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.SLIME_BLOCK, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.BIRCH_LOG, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.BRICK, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.OBSIDIAN, 128);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			item = new ItemStack (Material.BONE_BLOCK, 64);
			itemmeta = item.getItemMeta();
			itemlore = new ArrayList<String>();	
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			
			//Блоки
			
			p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Хороший выбор. Лично я предпочел бы броню.");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + " " + p.getName() + " " + "add" + " " + "dr.events.rewardoff");
			return true;
		}
		/*Награда блоки. КОНЕЦ*/
		
		/*Награда инструменты. НАЧАЛО*/		
		if (cmd.getName().equals("event") && (args.length == 1) && (args[0].compareToIgnoreCase("tools") == 0) && (amount >= 50) && (!p.hasPermission("dr.events.rewardoff"))) {
			//Проверка
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
				if (value < 6) {p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Хм... Куда ты собрался складывать вещи? Освободи инвентарь."); return true;}
			}
			else{
				p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
				p.sendMessage(ChatColor.GRAY + "[Рация]" + " Командир: " + ChatColor.WHITE + "Ну ты и странный. Как я тебе по воздуху передам вещи?");
				p.sendMessage(ChatColor.GRAY + "[Рация]" + " Командир: " + ChatColor.WHITE + "Я шататься туда-сюда не собираюсь. Приходи ко мне и забери их.");
				p.sendMessage(ChatColor.GRAY + "[Рация]" + " ...");
				return true;
			}
			//Проверка
			
			//Меч
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
			itemmeta.setDisplayName(ChatColor.BLUE + "Клинок");
			List<String> itemlore = new ArrayList<String>();
			
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "Сувенирный клинок");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);					
			//Меч
			
			item = new ItemStack(Material.DIAMOND_PICKAXE, 2);

			item.addEnchantment(Enchantment.MENDING, 1);
			item.addEnchantment(Enchantment.DIG_SPEED, 5);
			item.addEnchantment(Enchantment.SILK_TOUCH, 1);
			item.addEnchantment(Enchantment.DURABILITY, 3);
			item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
			item.addEnchantment(Enchantment.DIG_SPEED, 5);
			     
			itemmeta = item.getItemMeta();
			itemmeta.setDisplayName(ChatColor.BLUE + "Усиленная кирка");
			itemlore = new ArrayList<String>();
			     
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "С такой киркой и в шахту ходить не стыдно!");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
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
		    itemmeta.setDisplayName(ChatColor.BLUE + "Топор дровосека");
		    itemlore = new ArrayList<String>();
		      
		    itemlore.add(" ");
		    itemlore.add(" ");
		    itemlore.add(ChatColor.WHITE + "Топор как топор...");
		    itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
		    itemlore.add(" ");
		    itemlore.add(" ");
		    itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
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
			itemmeta.setDisplayName(ChatColor.BLUE + "Лопата");
			itemlore = new ArrayList<String>();
			       
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.WHITE + "Лопата: Digger Online Edition");
			itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
			itemlore.add(" ");
			itemlore.add(" ");
			itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
		       
			itemmeta.setLore(itemlore);
			item.setItemMeta(itemmeta);
			pi.addItem(item);
			       
			item = new ItemStack(Material.DIAMOND_HOE, 2);

	        item.addEnchantment(Enchantment.MENDING, 1);
	        item.addEnchantment(Enchantment.DURABILITY, 3);
			        
	        itemmeta = item.getItemMeta();
	        itemmeta.setDisplayName(ChatColor.BLUE + "Мотыга");
	        itemlore = new ArrayList<String>();
			        
	        itemlore.add(" ");
	        itemlore.add(" ");
	        itemlore.add(ChatColor.WHITE + "Продвинутый плуг xD");
	        itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
	        itemlore.add(" ");
	        itemlore.add(" ");
	        itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
	        itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
	        
	        itemmeta.setLore(itemlore);
	        item.setItemMeta(itemmeta);
	        pi.addItem(item);
	  
	        item = new ItemStack(Material.FISHING_ROD, 2);
			      
	        item.addEnchantment(Enchantment.DURABILITY, 3);
	      	item.addEnchantment(Enchantment.LUCK, 3);
	      	item.addEnchantment(Enchantment.LURE, 3);
			         
	        itemmeta = item.getItemMeta();
	        itemmeta.setDisplayName(ChatColor.BLUE + "Спининг");
	        itemlore = new ArrayList<String>();
			         
	        itemlore.add(" ");
	        itemlore.add(" ");
	        itemlore.add(ChatColor.WHITE + "Новороченный спининг");
	        itemlore.add(ChatColor.GOLD + "Награда от Командира дирижабля игроку " + p.getName() + ".");
	        itemlore.add(" ");
	        itemlore.add(" ");
         	itemlore.add(ChatColor.DARK_RED + "Ивент: \"Так точно, товарищ Командир!\" 2018");
			itemlore.add(ChatColor.DARK_GRAY + "vk.com/Daily_Rest_Group");
			         
			itemmeta.setLore(itemlore);
			
         	item.setItemMeta(itemmeta);
         	
         	pi.addItem(item);
         	
         	p.sendMessage(ChatColor.DARK_AQUA + "Командир: " + ChatColor.WHITE + "Хороший выбор. Лично я предпочел бы броню.");

			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user" + " " + p.getName() + " " + "add" + " " + "dr.events.rewardoff");
			
			return true;
			
		}
		/*Награда инструменты. НАЧАЛО*/
		
		/*Награда. КОНЕЦ*/
		
		/*НаградаПОВТОР. НАЧАЛО*/
		if (cmd.getName().equals("event") && (args.length == 1) && (args[0].compareToIgnoreCase("armor") == 0) && (amount >= 50) && (p.hasPermission("dr.events.rewardoff"))) {
			
			p.sendMessage(ChatColor.YELLOW + "[Ивент]" + " " + ChatColor.WHITE + "Ты уже получил награду.");
			
			return true;
		
		}
		
		if (cmd.getName().equals("event") && (args.length == 1) && (args[0].compareToIgnoreCase("blocks") == 0) && (amount >= 50) && (p.hasPermission("dr.events.rewardoff"))) {
			
			p.sendMessage(ChatColor.YELLOW + "[Ивент]" + " " + ChatColor.WHITE + "Ты уже получил награду.");
			
			return true;
		
		}
		
		if (cmd.getName().equals("event") && (args.length == 1) && (args[0].compareToIgnoreCase("tools") == 0) && (amount >= 50) && (p.hasPermission("dr.events.rewardoff"))) {
			
			p.sendMessage(ChatColor.YELLOW + "[Ивент]" + " " + ChatColor.WHITE + "Ты уже получил награду.");
			
			return true;
		
		}
		
		if (cmd.getName().equals("eventreload") && (p.hasPermission("eventadmin"))) {
			
			settings.reloadConfig();
			
			settings.reloadData();
			
			return true;
		
		}
		
		/*НаградаПОВТОР. КОНЕЦ*/
		
		return false;
	
	}
	
}
