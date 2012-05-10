package fr.noogotte.usefulCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UsefulCommand {
	
	private static UsefulCommandsMain plugin;
	
	public static void gamemode(CommandSender sender) {
		Player p = (Player)sender;
		if (p.getGameMode() == GameMode.CREATIVE) {	        
			p.setGameMode(GameMode.SURVIVAL);	        
			p.sendMessage(ChatColor.AQUA + "Votre gamemode est maintenant en " +          
					ChatColor.GOLD + "SURVIVAL");
		} else {
			p.setGameMode(GameMode.CREATIVE);       
			p.sendMessage(ChatColor.AQUA + "Votre gamemode est maintenant en " +  
        		ChatColor.GOLD + "CREATIVE");      
		}	
	}
	
	public static void sun(CommandSender sender) {
		Player p = (Player)sender;
		p.getWorld().setStorm(false);
		String w = p.getWorld().getName();
		Bukkit.broadcastMessage(ChatColor.RED + p.getName() + ChatColor.DARK_GREEN + " a mis le beau temps " + "dans " + ChatColor.YELLOW + w);
	}
	
	public static void storm(CommandSender sender) {
		Player p = (Player)sender;
		p.getWorld().setStorm(true);
		String w = p.getWorld().getName();
		Bukkit.broadcastMessage(ChatColor.RED + p.getName() + ChatColor.DARK_GREEN + " a mis la pluie " + "dans " + ChatColor.YELLOW + w);
	}
	
	public static void day(CommandSender sender) {
		Player p = (Player)sender;
		String w = p.getWorld().getName();
		p.getWorld().setTime(20*60);
		Bukkit.broadcastMessage(ChatColor.RED + p.getName() + ChatColor.DARK_GREEN + " a mis le jour dans " + ChatColor.GREEN + w);
	}
	public static void id(CommandSender sender) {
		Player p = (Player)sender;
		
		if (p.getItemInHand().getType() == Material.AIR) {
			p.sendMessage(ChatColor.RED + "Votre main est vide");
		} else if (p.getItemInHand().getType() == Material.WOOL || p.getItemInHand().getType() == Material.LOG || p.getItemInHand().getType() == Material.INK_SACK ||p.getItemInHand().getType() == Material.LEAVES){
			p.sendMessage(ChatColor.GOLD + "Vous tenez " + ChatColor.RED + p.getItemInHand().getType() +
					ChatColor.GOLD +" son ID est " + ChatColor.RED + p.getItemInHand().getData());
		} else if (p.getItemInHand().getType() == Material.INK_SACK){
			p.sendMessage(ChatColor.GOLD + "Vous tenez " + ChatColor.RED + p.getItemInHand().getType() +
					ChatColor.GOLD +" son ID est " + ChatColor.RED + p.getItemInHand().getData());
		} else {
			p.sendMessage(ChatColor.GOLD + "Vous tenez " + ChatColor.RED + p.getItemInHand().getType() +
					ChatColor.GOLD +" son ID est " + ChatColor.RED + p.getItemInHand().getTypeId());			
		}
	}
	
	public static void clear(CommandSender sender) {
		Player p = (Player)sender;
		Inventory in = p.getInventory();		
			for (int i = 0; i<= 39; i++) {		   
				in.setItem(i, null);
			}
			p.sendMessage(ChatColor.RED + "Votre inventaire a été vidé!");
	}
	
	public static void heal(CommandSender sender) {
		Player p = (Player)sender;
		if (p.getGameMode() == GameMode.CREATIVE) {
			p.sendMessage("Vous n'avez pas de vie, vous étes en CREATIF");
		} else {			
			p.setFoodLevel(20);			
			p.setHealth(20);			
			p.sendMessage(ChatColor.GOLD + "Vous avez été soigné et nourris");
		} 
	}
		
	public static void tphere(CommandSender sender, String[] args) {	
		Player p = (Player)sender;
		if(args.length == 0) {
			p.sendMessage(ChatColor.RED + "Il manque un nom de joueur !");
		} else if (args.length == 1) {
			Player target = Bukkit.getPlayer(args[0]);
			if(target == null) {
				p.sendMessage(ChatColor.RED + "Le joueure " + ChatColor.GOLD + args[0] + ChatColor.RED +" n'existe pas !");
			} else {
			target.teleport(p);
			p.sendMessage(ChatColor.AQUA + "Vous avez téléportez " + ChatColor.GOLD + target.getName() + "" + ChatColor.AQUA + "à vous");
			}
		}
	}
	
	public static void suicide(CommandSender sender) {
		Player p = (Player)sender;
		p.setHealth(0);
		Bukkit.broadcastMessage(ChatColor.RED + "" + p.getName() + ChatColor.GOLD + " s'est suicidé");
	}
	
	public static void tp(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		Player target1 = Bukkit.getPlayer(args[0]);
		Player target2 = Bukkit.getPlayer(args[1]);
		if(args.length == 0 || args.length == 1) {
			p.sendMessage(ChatColor.RED + "Il manque le nom de deux joueures !");
		} else if(args.length == 2){
			if(target1 == null || target2 == null) {
				p.sendMessage(ChatColor.RED + "Le joueure " + ChatColor.GOLD + args[0]+ ChatColor.RED + " ou "
			+  ChatColor.GOLD +  args[1] + ChatColor.RED +" n'existe pas !");
			} else {
				target1.teleport(target2);
				p.sendMessage(ChatColor.AQUA + "Vous avez tp " + ChatColor.GOLD + args[0] + ChatColor.AQUA + " � " + ChatColor.GOLD + args[1]);
			}
		}
	}
	
	public static void potion(CommandSender sender, String[] args) {			
	
		Player p = (Player)sender;	
	
	
		Player target = Bukkit.getPlayer(args[1]);
    
		if (args.length == 0 || args.length == 1) {
			
			p.sendMessage(ChatColor.RED + "Il manque le nom d'une potion !");
	
		} else if(args.length == 2) {					
		
			if (args[0].equals("confuse")) {						
			
				if(target == null) {										
			
					p.sendMessage(ChatColor.RED + "Le joueure : " + ChatColor.GOLD + args[1] + " n'existe pas !");							
			
				} else {				
				
					PotionEffect confuse = new PotionEffect(PotionEffectType.CONFUSION, 20*50, 20*50);				
				
					target.addPotionEffect(confuse);
				
					p.sendMessage(ChatColor.AQUA + "Vous avez mis en confusion " + ChatColor.GOLD + target.getName());
			
				}
		
			} else if (args[0].equals("poison")) {								
				
				if(target == null) {														
					
					p.sendMessage(ChatColor.RED + "Le joueure : " + ChatColor.GOLD + args[1] + " n'existe pas !");									
				
				} else {				
					PotionEffect poison = new PotionEffect(PotionEffectType.POISON, 20*50, 20*50);											
					target.addPotionEffect(poison);					
					p.sendMessage(ChatColor.AQUA + "Vous avez empoisonés " + ChatColor.GOLD + target.getName());
				}
			
			} else if (args[0].equals("slow")) {		
												
					if(target == null) {															
						p.sendMessage(ChatColor.RED + "Le joueure : " + ChatColor.GOLD + args[1] + " n'existe pas !");											
					} else {																
						PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 20*50, 20*50);																		
						target.addPotionEffect(slow);				
						p.sendMessage(ChatColor.AQUA + "Vous avez ralentis " + ChatColor.GOLD + target.getName());				
				}	
			} else if (args[0].equals("harm")) {
				if(target == null) {															
					p.sendMessage(ChatColor.RED + "Le joueure : " + ChatColor.GOLD + args[1] + " n'existe pas !");											
				} else {																
					PotionEffect harm = new PotionEffect(PotionEffectType.HARM, 15, 0);																
					target.addPotionEffect(harm);				
					p.sendMessage(ChatColor.AQUA + "Vous avez blessés " + ChatColor.GOLD + target.getName());	
				}
			}
		}
	}
	
	public static void spawnmob(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		if(args[0].equals("ender-dragon")) {
			p.getWorld().spawnCreature(p.getLocation(), EntityType.ENDER_DRAGON);
		} else if(args[0].equals("giant")) {
			p.getWorld().spawnCreature(p.getLocation(), EntityType.GIANT);
		} else if (args[0].equals("iron-golem")) {
			p.getWorld().spawnCreature(p.getLocation(), EntityType.IRON_GOLEM);
		} else if (args[0].equals("snow-golem")) {
			p.getWorld().spawnCreature(p.getLocation(), EntityType.SNOWMAN);
		} else if (args[0].equals("")) {
			
		}
	}
}
	

