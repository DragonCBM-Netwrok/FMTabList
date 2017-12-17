package com.firesoftitan.play.fmtablist.listeners;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.earth2me.essentials.Warps;
import com.firesoftitan.play.fmtablist.FMTabList;
import com.firesoftitan.play.fmtablist.mystuff.teleportpro;
import com.firesoftitan.play.fmtablist.slimefun.items.SFItems;
import com.firesoftitan.play.fmtablist.slimefun.items.TitanTalisman;
import com.gmail.nossr50.api.PartyAPI;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.sensibletoolbox.SensibleToolboxPlugin;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBMachine;
import me.mrCookieSlime.sensibletoolbox.blocks.machines.BigStorageUnit;
import me.mrCookieSlime.sensibletoolbox.core.storage.LocationManager;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainListener implements Listener {
    
    private FMTabList plugin;
	private long elptims = 0;
	private Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
	public long Ticks = 0;
	public List<teleportpro> tpaprocter= new ArrayList<teleportpro>();
	NBTTagCompound replacesMent = null;

	private ProtocolManager protocolManager;



	//if (Thread.currentThread() != MinecraftServer.getServer().primaryThread) Thread.dumpStack();
    public MainListener(FMTabList plugin){
        this.plugin = plugin;
		this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public void registerEvents(){
        PluginManager pm = this.plugin.getServer().getPluginManager();
        pm.registerEvents(this, this.plugin);
    }

	private void onPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event)
	{


		User user = ess.getUser(event.getUniqueId());
		if (user.getMuted() || GriefPrevention.instance.dataStore.isSoftMuted(event.getUniqueId())) {

		}

	}
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event)
	{
		FMTabList.plugin.backupGui.InvEvent(event.getInventory(), (Player)event.getWhoClicked(), event.getRawSlot());
	}
	@EventHandler
	public void onPlayerLoginEvent(PlayerLoginEvent event) {
		/*Player player = event.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(FMTabList.plugin, new Runnable() {
			@Override
			public void run() {
				convertClaims(player);
			}
		}, 15);
*/
	}



	@EventHandler
	public void onChat(AsyncPlayerChatEvent event)
	{

		try {
			if (FMTabList.WhosFirst == true) {
				if (event.getMessage().toLowerCase().contains("welcome")) {

					//event.getPlayer().loadData();
					FMTabList.WhosFirst = false;
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "broadcast &d>>>>>>>>>>>>>>>>>>>>>&6Reward&d<<<<<<<<<<<<<<<<<<<<<");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "broadcast &6" + event.getPlayer().getDisplayName() + "&d was the first one to welcome the new player and get's 1 inv key!");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "broadcast &d>>>>>>>>>>>>>>>>>>>>>&6Reward&d<<<<<<<<<<<<<<<<<<<<<");
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "crate givekey " + event.getPlayer().getName() + " InventoryCrate 1");

				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	@EventHandler
	public  void onEntityDamageEvent(EntityDamageEvent event)
	{
		if (!event.isCancelled()) {
			if (event.getEntity() != null) {
				if (event.getEntity().getCustomName() != null) {
					if (event.getEntity().getCustomName().equals("Ra, god of the Sun")) {
						if (event.getEntity().getLocation().getWorld().getTime() > 0 && event.getEntity().getLocation().getWorld().getTime() < 13000) {
							event.setCancelled(true);
						}
					}
				}
				if (event.getEntity() instanceof Player)
				{
					if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID))
					{
						event.getEntity().teleport(new Location(Bukkit.getWorld("worldmain"), 0, 16.5, 0));
						event.setCancelled(true);
					}
				}
			}


		}
	}
	@EventHandler
	public  void onInventoryCloseEvent(InventoryCloseEvent event)
	{

		runVoidTalisman((Player) event.getPlayer());

	}
	@EventHandler
	public  void onPlayerPickupItemEvent(PlayerPickupItemEvent event)
	{

		runVoidTalisman(event.getPlayer());

		Player p = event.getPlayer();
		ItemStack item = event.getItem().getItemStack();

		if (item != null) {
			if (item.getItemMeta() != null) {
				if (item.getItemMeta().hasDisplayName()) {
					if (!p.hasPermission("FMTabList.email")) {
						if (item.getItemMeta().getDisplayName().contains(ChatColor.RESET + "Splash Potion of " + ChatColor.DARK_RED + ChatColor.BOLD + "DEATH")) {
							event.setCancelled(true);
						}
						if (item.getItemMeta().getDisplayName().contains(ChatColor.DARK_PURPLE + "" + ChatColor.LIGHT_PURPLE + "ALTAR " + ChatColor.DARK_AQUA + "Probe - ")) {
							event.setCancelled(true);
						}
					}
				}
			}
		}

	}
	@EventHandler
	public  void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		if (FMTabList.plugin.evtimedeath > 0)
		{
			//System.out.println("Searching");
			if (FMTabList.eventName.replace("_", " ").equalsIgnoreCase("treasure hunt")) {
				//System.out.println("Yes: " + event.getClickedBlock().getLocation().toString());
				//System.out.println( event.getClickedBlock().getLocation().toString());
				if (event.getClickedBlock() != null) {
					if (!event.getClickedBlock().getType().equals(Material.AIR)) {
						Location placing = FMTabList.treasureplacementList.getLocation(FMTabList.treasurePlacementName);
						System.out.println(placing.toString());
						if (event.getClickedBlock().getLocation().getWorld().getName().equals(placing.getWorld().getName()) && event.getClickedBlock().getLocation().getBlockX() == placing.getBlockX() && event.getClickedBlock().getLocation().getBlockY() == placing.getBlockY() && event.getClickedBlock().getLocation().getBlockZ() == placing.getBlockZ()) {
							placing.getBlock().setType(Material.AIR);
							List<ItemStack> copyme = new ArrayList<ItemStack>();
							for (String subkey : FMTabList.treasureList.getKeys(FMTabList.treasureName)) {
								event.getPlayer().getInventory().addItem(FMTabList.treasureList.getItem(FMTabList.treasureName + "." + subkey).clone());
							}

							Bukkit.broadcastMessage("" + ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.WHITE + event.getPlayer().getName() + ChatColor.RED + ChatColor.BOLD + " has just got the Treasure: " + ChatColor.WHITE + FMTabList.treasureName);
							FMTabList.plugin.evtimedeath = System.currentTimeMillis();
						}
					}
				}
			}
		}


		ItemStack IIH = event.getPlayer().getInventory().getItemInMainHand();
		ItemMeta IIHmeta = null;
		List<String> IIHLore = null;
		int linkSlot = -1;
		String linkedText = "";
		if (IIH != null) {
			if (IIH.getType() == Material.STICK)
			{
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					Location check  = event.getPlayer().getLocation();
					if (event.getClickedBlock() != null) {
						check = event.getClickedBlock().getLocation();
					}
					Claim claim = GriefPrevention.instance.dataStore.getClaimAt(check, false, null);
					if (claim != null) {
						if (!claim.isAdminClaim()) {
							if (claim.ownerID != null) {
								Long lastplayed = Bukkit.getOfflinePlayer(claim.ownerID).getLastPlayed();
								Long OffLine = System.currentTimeMillis() - lastplayed;
								int days = (int) (OffLine / (1000 * 60 * 60 * 24));
								//event.getPlayer().sendMessage();
								event.getPlayer().sendMessage(ChatColor.GREEN + "------- Officail Claim Block Data -------");
								event.getPlayer().sendMessage(ChatColor.GREEN + "Owner (Player): " + ChatColor.RED + claim.getOwnerName());
								event.getPlayer().sendMessage(ChatColor.GREEN + "Owner offline: " + ChatColor.RED + days + ChatColor.GREEN + " days.");



								if (days >= 30) {
									float price = 1000f / ((float) days / 30f);
									price = Math.round(price * 100) / 100;
									price = claim.getArea() * price;
									event.getPlayer().sendMessage(ChatColor.GREEN + "Land is for sale");
									DecimalFormat dFormat = new DecimalFormat("####,###,###.00");
									event.getPlayer().sendMessage(ChatColor.GREEN + "Price: " + ChatColor.RED + "$" + dFormat.format(price));
									event.getPlayer().sendMessage(ChatColor.GREEN + "To Buy stand in land and type " + ChatColor.WHITE + "/buyland");
									event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Note: Land gets cheap the long the owner is offline, until its free!");
								}
							}
						}
						else
						{
							if (claim.parent != null)
							{
								if (claim.managers.size() < 1) {
									float price = claim.getArea() * 10000;
									event.getPlayer().sendMessage(ChatColor.GREEN + "Land is for sale");
									DecimalFormat dFormat = new DecimalFormat("####,###,###.00");
									event.getPlayer().sendMessage(ChatColor.GREEN + "Price: " + ChatColor.RED + "$" + dFormat.format(price));
									event.getPlayer().sendMessage(ChatColor.GREEN + "To Buy stand in land and type " + ChatColor.WHITE + "/buyland");
									event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Note: Shops have rules, and players can only have 1 shop!");
								}
							}
						}
					}
				}
			}
			if (IIH.hasItemMeta()) {
				IIHmeta = IIH.getItemMeta();
			}
			if (IIHmeta != null) {
				if (IIHmeta.hasLore()) {
					IIHLore = IIHmeta.getLore();
				}
				if (IIHLore != null) {
					/*
					for (int i = 0; i < IIHLore.size(); i++) {
						if (IIHLore.get(i).startsWith(ChatColor.AQUA + "Titan.Linked: ")) {
							linkSlot = i;
							if (IIHLore.get(i).split(": ").length > 1) {
								if (!IIHLore.get(i).split(": ")[1].equals("[Not Linked]")) {

									linkedText = ChatColor.stripColor(IIHLore.get(linkSlot)).replace("Titan.Linked: ", "").replace("[", "").replace("]", "");
								}
							}
							break;
						}
					}*/
				}
			}
		}

		ItemStack changeName = IIH.clone();
		if (IIH.hasItemMeta()) {
			ItemMeta ChangeNameHmeta = IIH.getItemMeta();
			ChangeNameHmeta.setDisplayName(SFItems.WARPKEY.getItemMeta().getDisplayName());
			changeName.setItemMeta(ChangeNameHmeta);
		}
		if (SensibleToolboxPlugin.isItemSimiliar(changeName, SFItems.WARPKEY)) {
			if (IIHLore != null) {
				if ((event.getAction() == Action.LEFT_CLICK_BLOCK)) {
					if (IIH.getItemMeta().getDisplayName().equalsIgnoreCase(SFItems.WARPKEY.getItemMeta().getDisplayName()))
					{
						event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "FMT" + ChatColor.GOLD + "] " + ChatColor.RED + "You must rename key in anvil first!");
					}
					else {
						if (IIH.getItemMeta().getDisplayName().contains(" ")) {
							event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "FMT" + ChatColor.GOLD + "] " + ChatColor.RED + "You can't have spaces in the name, please change your keys name!");
						}
						else
						{
							if (IIH.getAmount() > 1) {
								event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "FMT" + ChatColor.GOLD + "] " + ChatColor.RED + "Please unstack your key, only use one at a time!");
							} else {
								Warps essWarps = FMTabList.plugin.mainListener.ess.getWarps();
								if (essWarps.getList().contains(IIH.getItemMeta().getDisplayName())) {
									event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "FMT" + ChatColor.GOLD + "] " + ChatColor.RED + "That warp name is take, please change your keys name!");
								} else {
									try {
										essWarps.setWarp(IIH.getItemMeta().getDisplayName(), event.getPlayer().getLocation().clone());
										event.getPlayer().getInventory().setItemInMainHand(null);
										event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "FMT" + ChatColor.GOLD + "] " + ChatColor.GREEN + "Your warp has been set!");
										Bukkit.broadcastMessage("" + ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD + event.getPlayer().getName() + " has just set a new warp " + ChatColor.WHITE + "/warp " + IIH.getItemMeta().getDisplayName());
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}


					}

				}
				if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) || (event.getAction() == Action.RIGHT_CLICK_AIR)) {
					event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "FMT" + ChatColor.GOLD + "] " + ChatColor.GREEN + "Left Click to set warp!");
				}
			}
			event.setCancelled(true);
		}


		if (SensibleToolboxPlugin.isItemSimiliar(IIH, SFItems.TALISMAN_VOID))
		{
			if (event.getAction() == Action.LEFT_CLICK_BLOCK)
			{
				ItemStack hand = event.getPlayer().getInventory().getItemInMainHand();
				Location clicked = event.getClickedBlock().getLocation();
				BaseSTBMachine machine = LocationManager.getManager().get(clicked, BaseSTBMachine.class);
				if (machine == null)
				{
					event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "] " + ChatColor.RED + "Can only link with BSU or HSU");
				}
				if (!(machine instanceof BigStorageUnit)) {
					event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "] " + ChatColor.RED + "Can only link with BSU or HSU");
				}
				ItemStack linked = TitanTalisman.setLinkedLocation(hand, clicked);
				if (linked != null) {
					event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "] " + ChatColor.GREEN + "Talisman linked!");
					event.getPlayer().getInventory().setItemInMainHand(linked);
				}
				event.setCancelled(true);
			}
		}

		runVoidTalisman(event.getPlayer());

	}
	public ItemStack linkBossKey(Player player, String KeyName, ItemStack IIH ) {
		ItemMeta IIHmeta = null;
		List<String> IIHLore = null;
		int linkSlot = -1;
		boolean isLinked = false;
		String linkedText = "";
		if (IIH != null) {
			if (IIH.hasItemMeta()) {
				IIHmeta = IIH.getItemMeta();
			}
			if (IIHmeta != null) {
				if (IIHmeta.hasLore()) {
					IIHLore = IIHmeta.getLore();
				}
				if (IIHLore != null) {
					for (int i = 0; i < IIHLore.size(); i++) {
						if (IIHLore.get(i).startsWith(ChatColor.AQUA + "Titan.Linked: ")) {
							linkSlot = i;
							if (IIHLore.get(i).split(": ").length > 1) {
								if (!IIHLore.get(i).split(": ")[1].equals("[Not Linked]")) {
									isLinked = true;
									linkedText = ChatColor.stripColor(IIHLore.get(linkSlot)).replace("Titan.Linked: ", "").replace("[", "").replace("]", "");
								}
							}
							break;
						}
					}
				}
			}
		}
		IIHLore.remove(linkSlot);
		IIHLore.add(linkSlot, ChatColor.AQUA + "Titan.Linked: " + KeyName);
		IIHmeta.setLore(IIHLore);
		ItemStack setIIH = IIH.clone();
		setIIH.setItemMeta(IIHmeta);
		return setIIH;
	}


	private void runVoidTalisman(Player player) {
		HashMap<Integer,ItemStack> findTally = TitanTalisman.checkFor(player, SlimefunItem.getByName("TALISMAN_VOID"));
		if (findTally != null)
		{
			findTally =  (HashMap<Integer,ItemStack>)findTally.clone();
			Inventory playerinv =  player.getInventory();
			for(Integer i:findTally.keySet())
			{
				BigStorageUnit bsu = TitanTalisman.getLinkedBSU(findTally.get(i));
				if (bsu != null)
				{
					for(int t = 0; t < playerinv.getSize(); t++) {
						if (playerinv.getItem(t) != null) {
							if (playerinv.getItem(t).getType() != Material.AIR) {
								if (bsu.getStoredItemType() != null) {
									int nInserted = bsu.insertItems(playerinv.getItem(t), BlockFace.SELF, false, player.getUniqueId());
									if (nInserted > 0) {
										ItemStack reduce = playerinv.getItem(t).clone();
										reduce.setAmount(reduce.getAmount() - nInserted);
										playerinv.setItem(t, reduce);
									}
								}
							}
						}
					}
				}
			}

		}
	}

	@EventHandler
	public void onPlayerItemDamageEvent(PlayerItemDamageEvent event) {
		if (event.getItem().getItemMeta().getLore() != null) {
			List<String> loreList = event.getItem().getItemMeta().getLore();
			for (int i = 0; i < loreList.size(); i++) {
				if (loreList.get(i).equals(ChatColor.AQUA + "Titan." + ChatColor.DARK_PURPLE + "Unbreakable")) {
					event.setCancelled(true);
					Material mat = event.getItem().getType();
					short max = mat.getMaxDurability();
					event.getItem().setDurability((short)0);
				}
			}
		}
	}
	@EventHandler
	public void onDropEvent(PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().getItemMeta() != null) {
			if (event.getItemDrop().getItemStack().getItemMeta().getLore() != null) {
				List<String> loreList = event.getItemDrop().getItemStack().getItemMeta().getLore();
				for (int i = 0; i < loreList.size(); i++) {
					if (loreList.get(i).startsWith(ChatColor.AQUA + "Titan." + ChatColor.DARK_GREEN + "Undroppable")) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	@EventHandler
	public void oneServerCommand(ServerCommandEvent event){
		plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
                try {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        setTabText(p);
                    }
                }
                catch (Exception e)
                {

                }
			}
		},2L);
	}




	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (FMTabList.plugin.checkforTitanStone(event.getItemInHand()))
		{
			event.setCancelled(true);
			if (event.getPlayer() != null) {
				event.getPlayer().sendMessage(ChatColor.RED + "[WARNING]: " + ChatColor.GOLD + "The Titan Stone Can't Be Placed On The Ground It Would Truely Kill Us All!!!");
			}
		}

	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

		if (!event.isCancelled()) {
			if (event.getDamager() != null) {
				if (event.getDamager() instanceof Player) {
					boolean shoulCancel = checkProtection(((Player) event.getDamager()));
					event.setCancelled(shoulCancel);
				}
			}
			if (event.getEntity() != null) {
				if (event.getEntity() instanceof Player) {
					boolean shoulCancel = checkProtection((Player) event.getEntity());
					event.setCancelled(shoulCancel);
				}
			}


		}
	}

	private boolean checkProtection(Player A ) {
		for(int i = 0; i<tpaprocter.size();i++) {
            teleportpro tmp = tpaprocter.get(i);
            if (System.currentTimeMillis() - tmp.timetp < 30*1000  && tmp.protect)
            {
                if ( tmp.usernameA.equals(A.getUniqueId()) || tmp.usernameB.equals(A.getUniqueId()))
                {
                    if (A != null) {

                        Long timeleft = System.currentTimeMillis() - tmp.timetp;
                        timeleft = timeleft /1000;
						timeleft = 30 - timeleft;
                        A.sendMessage(ChatColor.GREEN +"If someone is trying to kill you type "  + ChatColor.WHITE + "/spawn" + ChatColor.RED +  " There is " + ChatColor.UNDERLINE + "NO TPA Killing rule.");
                        return true;
                    }
                }
            }
        }
		return false;
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity()  != null) {
			LivingEntity entity = event.getEntity();

			if (event.getEntity() instanceof Player) {
				Player Dieing = (Player) event.getEntity();
				PlayerInventory CheckInv = Dieing.getInventory();
				Boolean saving = false;;
				if (Dieing.getGameMode().equals(GameMode.CREATIVE))
				{;
					Dieing.setLevel(0);
					Dieing.getInventory().clear();

					Dieing.kickPlayer("What happened???? You've been reported!");
					FMTabList.plugin.sendMail("HACKER NOTICE", "Death-player: " + Dieing.getName());
					return;
				}
				for (int i = 0; i < CheckInv.getSize(); i++) {
					if (FMTabList.plugin.checkforTitanStone(CheckInv.getItem(i))) {
						event.getDrops().clear();
						saving = true;
						break;
					}
				}
				if (saving == true) {
					ItemStack[] tmpSave = new ItemStack[CheckInv.getSize()];
					for (int i = 0; i < CheckInv.getSize(); i++) {
						if (CheckInv.getItem(i) != null) {
							tmpSave[i] = CheckInv.getItem(i).clone();
						}
					}
					FMTabList.plugin.playerListSaves.put(Dieing.getUniqueId(), tmpSave);
				}
				if (entity.getKiller() != null) {
					Player killer = entity.getKiller();
					int killCT = 0;
					long priceHD = 0;
					boolean bounty = false;
					if (!killer.getUniqueId().toString().equals(Dieing.getUniqueId().toString())) {
						if (FMTabList.plugin.bountyList.contains(Dieing.getUniqueId().toString())) {
							if (!FMTabList.plugin.priceCountList.containsKey(Dieing.getUniqueId().toString())) {
								FMTabList.plugin.priceCountList.put(Dieing.getUniqueId().toString(), (long)1);
								FMTabList.plugin.bountyList.setValue(Dieing.getUniqueId().toString() + "--price", (long)1);;
							}
							if (!FMTabList.plugin.killCountList.containsKey(Dieing.getUniqueId().toString())) {
								FMTabList.plugin.killCountList.put(Dieing.getUniqueId().toString(), 0);
								FMTabList.plugin.bountyList.setValue(killer.getUniqueId().toString(), 0);
							}
							killCT = FMTabList.plugin.killCountList.get(Dieing.getUniqueId().toString());
							priceHD = FMTabList.plugin.priceCountList.get(Dieing.getUniqueId().toString());
							if (priceHD > 0) {
								if (FMTabList.plugin.killCountList.containsKey(killer.getUniqueId().toString())) {
									killer.sendMessage(ChatColor.RED + "Player with a bounty can't collect bounties.");
								}
								else {
									if (FMTabList.killrecord.contains(killer.getUniqueId().toString() + "." + Dieing.getUniqueId().toString()))
									{
										long lastKilled = FMTabList.killrecord.getLong(killer.getUniqueId().toString() + "." + Dieing.getUniqueId().toString());
										long milliseconds = System.currentTimeMillis() - lastKilled;
										int days = (int) (milliseconds / (1000*60*60*24));
										if (days < 3)
										{
											killer.sendMessage(ChatColor.RED + "You must wait " + (3 - days) + " before you can collect a new bounty on " + Dieing.getName());
										}
										else
										{
											RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
											rsp.getProvider().depositPlayer(killer, priceHD);
											Bukkit.broadcastMessage(ChatColor.WHITE + killer.getName() + ChatColor.LIGHT_PURPLE + " just got " + ChatColor.RED + "$" + ChatColor.WHITE + FMTabList.format(priceHD) + ChatColor.LIGHT_PURPLE + " for killing " + ChatColor.WHITE + Dieing.getName());
											FMTabList.plugin.priceCountList.put(Dieing.getUniqueId().toString(), (long)0);
											FMTabList.plugin.bountyList.setValue(Dieing.getUniqueId().toString() + "--price", (long)0);
											bounty = true;
										}
									}
									else {
										RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
										rsp.getProvider().depositPlayer(killer, priceHD);
										Bukkit.broadcastMessage(ChatColor.WHITE + killer.getName() + ChatColor.LIGHT_PURPLE + " just got " + ChatColor.RED + "$" + ChatColor.WHITE + FMTabList.format(priceHD) + ChatColor.LIGHT_PURPLE + " for killing " + ChatColor.WHITE + Dieing.getName());
										FMTabList.plugin.priceCountList.put(Dieing.getUniqueId().toString(), (long)0);
										FMTabList.plugin.bountyList.setValue(Dieing.getUniqueId().toString() + "--price", (long)0);
										bounty = true;
									}
								}
							}
						}


						if (FMTabList.plugin.killCountList.containsKey(killer.getUniqueId().toString())) {
							killCT = FMTabList.plugin.killCountList.get(killer.getUniqueId().toString());
						}
						killCT++;
						FMTabList.plugin.killCountList.put(killer.getUniqueId().toString(), killCT);
						FMTabList.plugin.bountyList.setValue(killer.getUniqueId().toString(), killCT);
						if (!bounty) {
							priceHD = priceHD + 10000;
							FMTabList.plugin.priceCountList.put(killer.getUniqueId().toString(), priceHD);
							FMTabList.plugin.bountyList.setValue(killer.getUniqueId().toString() + "--price", priceHD);
							Bukkit.broadcastMessage(ChatColor.WHITE + killer.getName() + ChatColor.LIGHT_PURPLE + " now has a  " + ChatColor.RED + "$" + ChatColor.WHITE + FMTabList.format(priceHD) + ChatColor.LIGHT_PURPLE + " bounty!");
						}
						FMTabList.killrecord.setValue(killer.getUniqueId().toString() + "." + Dieing.getUniqueId().toString(), System.currentTimeMillis());

					}
				}
			}
		}
	}
		@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event){


		if (event.getPlayer() != null) {
			if (event.getMessage().toLowerCase().startsWith("/shopkey"))
			{
				if (event.getPlayer().hasPermission("FMTabList.email")) {
					int FESlot = event.getPlayer().getInventory().firstEmpty();
					if (FESlot > -1)
					{
						event.getPlayer().getInventory().setItem(FESlot, SFItems.SHOP_KEY.clone());
					}
				}
				event.setCancelled(true);
			}
			if (event.getPlayer().getGameMode() != null && event.getPlayer().hasPermission("FMTabList.email")) {
				if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
					if (event.getMessage().toLowerCase().startsWith("/guide") || event.getMessage().toLowerCase().startsWith("/sf guide")) {
						event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "] " + ChatColor.RED + "Can't use Slimefun in creative!");
						event.setCancelled(true);
					}
				}
			}
			if (event.getMessage().toLowerCase().startsWith("/helper") && event.getPlayer().hasPermission("FMTabList.email"))
			{

					if (FMTabList.plugin.protectedList.contains(((Player) event.getPlayer()).getUniqueId()))
					{
						FMTabList.plugin.protectedList.remove(((Player) event.getPlayer()).getUniqueId());
						event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " +ChatColor.GREEN + "You are no logger protected!");
					}
					else
					{
						FMTabList.plugin.protectedList.add(((Player) event.getPlayer()).getUniqueId());
						event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " +ChatColor.GREEN + "You are protected!");
					}
				event.setCancelled(true);

			}
			if (event.getMessage().toLowerCase().startsWith("/tps ") || event.getMessage().toLowerCase().equalsIgnoreCase("/tps") || event.getMessage().toLowerCase().startsWith("/lag ") || event.getMessage().toLowerCase().equalsIgnoreCase("/lag"))
			{
				if (!event.getPlayer().hasPermission("FMTabList.email")) {
					int tps1 = 1800;
					int tps2 = 1800;
					int tps3 = 1800;
					Random fackTPS = new Random(System.currentTimeMillis());
					tps1 += fackTPS.nextInt(300);
					tps2 += fackTPS.nextInt(300);
					tps3 += fackTPS.nextInt(300);
					float tpsf1 = (float)tps1 / 100f;
					float tpsf2 = (float)tps2 / 100f;
					float tpsf3 = (float)tps3 / 100f;
					event.getPlayer().sendMessage(ChatColor.GOLD + "TPS from last 1m, 5m, 15m: " + ChatColor.GREEN + tpsf1 + ", " + tpsf2 + ", " + tpsf3);
					event.setCancelled(true);
				}
			}
			if (event.getMessage().toLowerCase().startsWith("/party ?") || event.getMessage().toLowerCase().startsWith("/party help")) {
				Player player = event.getPlayer();
				player.sendMessage(ChatColor.RED + "Type " + ChatColor.GREEN + "/party home" + ChatColor.RED + " or " + ChatColor.GREEN + "/party shop" + ChatColor.RED + " To Teleport you to these locations");
				player.sendMessage(ChatColor.RED + "The party leader can type " + ChatColor.GREEN + "/party sethome" + ChatColor.RED + " or " + ChatColor.GREEN + "/party setshop" + ChatColor.RED + " To set the locations for your party!");
			}

			if (event.getMessage().toLowerCase().startsWith("/party sethome"))
			{

				Player player = event.getPlayer();
				String PartyName = PartyAPI.getPartyName(player);
				String where = "home";
				if (!player.getName().equals(PartyAPI.getPartyLeader(PartyName)))
				{
					player.sendMessage(ChatColor.RED + "Only the party leader (" + PartyAPI.getPartyLeader(PartyName) + ") can set the " + where);
				}
				else
				{
					FMTabList.plugin.setPartyWarps(PartyName, where, player.getLocation().add(0,3,0));
					player.sendMessage(ChatColor.GREEN + "Your parties " + where + " has been set!");
				}
				event.setCancelled(true);
			}

			if (event.getMessage().toLowerCase().startsWith("/party home")) {
				Player player = event.getPlayer();
				String PartyName = PartyAPI.getPartyName(player);
				String where = "home";
				Location loc = FMTabList.plugin.getPartyWarp(PartyName, where);
				if (loc != null) {
					player.teleport(loc);
					player.sendMessage(ChatColor.GREEN + "You are at the parties " + where);
				}
				else
				{
					player.sendMessage(ChatColor.RED + "Party has not set a " + where +" yet!");
				}
				event.setCancelled(true);
			}
			if (event.getMessage().toLowerCase().startsWith("/party setshop")) {
				Player player = event.getPlayer();
				String PartyName = PartyAPI.getPartyName(player);
				String where = "shop";
				if (!player.getName().equals(PartyAPI.getPartyLeader(PartyName)))
				{
					player.sendMessage(ChatColor.RED + "Only the party leader (" + PartyAPI.getPartyLeader(PartyName) + ") can set the " + where);
				}
				else
				{
					FMTabList.plugin.setPartyWarps(PartyName, where, player.getLocation().add(0,3,0));
					player.sendMessage(ChatColor.GREEN + "Your parties " + where + " has been set!");
				}
				event.setCancelled(true);
			}
			if (event.getMessage().toLowerCase().startsWith("/party shop")) {
				Player player = event.getPlayer();
				String PartyName = PartyAPI.getPartyName(player);
				String where = "shop";
				Location loc = FMTabList.plugin.getPartyWarp(PartyName, where);
				if (loc != null) {
					player.teleport(loc);
					player.sendMessage(ChatColor.GREEN + "You are at the parties " + where);
				}
				else
				{
					player.sendMessage(ChatColor.RED + "Party has not set a " + where +" yet!");
				}
				event.setCancelled(true);
			}
			if (event.getMessage().toLowerCase().startsWith("/party create")) {
				if (event.getMessage().contains("%"))
				{
					event.getPlayer().sendMessage(ChatColor.RED + "You can't have the char % in your party name!!");
					event.setCancelled(true);
				}
			}
			if (event.getMessage().startsWith("/listalts ")) {
				if (event.getPlayer().hasPermission("FMTabList.email")) {
					String name = event.getMessage().split(" ")[1];
					UUID theUUDI = Bukkit.getOfflinePlayer(name).getUniqueId();
					String lastIP = FMTabList.plugin.getLastIp(theUUDI);
					List<String> tmpIPList =  FMTabList.plugin.iplist.get(lastIP);
					String AltList = "";
					for (String names : tmpIPList) {
						AltList = AltList + ", " + Bukkit.getOfflinePlayer(UUID.fromString(names)).getName();
					}
					AltList = AltList.substring(2, AltList.length());
					if (event.getPlayer().hasPermission("FMTabList.email")) {
						event.getPlayer().sendMessage(ChatColor.GOLD + "Linked Accounts for " + ChatColor.GREEN +  name + ChatColor.GOLD + ": " + ChatColor.LIGHT_PURPLE + AltList);
					}
					event.setCancelled(true);
				}
			}

            if (event.getMessage().equalsIgnoreCase("/removeme"))
            {
                if (event.getPlayer().hasPermission("FMTabList.email")) {
                    FMTabList.removePlayerFromTabList(event.getPlayer());
                    Bukkit.broadcastMessage(ChatColor.YELLOW + event.getPlayer().getName() + " left the game.");
                    event.setCancelled(true);
                }
            }
            if (event.getMessage().equalsIgnoreCase("/addme"))
            {
                if (event.getPlayer().hasPermission("FMTabList.email")) {
                    FMTabList.addPlayerFromTabList(event.getPlayer());
                    event.setCancelled(true);
                }
            }
			if (event.getMessage().toLowerCase().startsWith("/tpchere ") || event.getMessage().toLowerCase().startsWith("/pm ") ||event.getMessage().toLowerCase().startsWith("/tpc ") || event.getMessage().toLowerCase().startsWith("/tpahere ") || event.getMessage().toLowerCase().startsWith("/tpa ") || event.getMessage().toLowerCase().startsWith("/t ") || event.getMessage().toLowerCase().startsWith("/w ") || event.getMessage().toLowerCase().startsWith("/msg ") || event.getMessage().toLowerCase().startsWith("/m ") || event.getMessage().toLowerCase().startsWith("/tell ") || event.getMessage().toLowerCase().startsWith("/whisper ") || event.getMessage().toLowerCase().startsWith("/msg "))
			{
				if (!event.getPlayer().hasPermission("FMTabList.email")) {
					String[] args = event.getMessage().split(" ");
					if (args.length > 1) {
						Player player = FMTabList.getPlayer(args[1]);
						if (player != null) {
							if (player.hasPermission("FMTabList.email"))
							{
								event.setCancelled(true);
							    event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]:" + ChatColor.RED + " You can't whisper or tpa mods, staff, admins, or owner. Please use /mail send name or use /r to reply to them." ); //Please use " +ChatColor.GOLD + "/ts ticket"
								event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]:" + ChatColor.RED + " Please post all bugs, problumes, ect on " + ChatColor.WHITE + "bugs.firesoftitan.com."); //Please use " +ChatColor.GOLD + "/ts ticket"
								player.sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]: " + ChatColor.GRAY +  event.getPlayer().getName() + ", tried to whisper you and was blocked! He was told to use the website. He can use /r to reply to you." );
								player.sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]msg: " + ChatColor.GRAY + event.getMessage());
							}
							else
							{
								if (FMTabList.plugin.bountyList.contains(event.getPlayer().getUniqueId().toString())) {
									if (!FMTabList.plugin.priceCountList.containsKey(event.getPlayer().getUniqueId().toString())) {
										FMTabList.plugin.priceCountList.put(event.getPlayer().getUniqueId().toString(), (long)1);
										FMTabList.plugin.bountyList.setValue(event.getPlayer().getUniqueId().toString() + "--price", (long)1);;
									}
									if (!FMTabList.plugin.killCountList.containsKey(event.getPlayer().getUniqueId().toString())) {
										FMTabList.plugin.killCountList.put(event.getPlayer().getUniqueId().toString(), 0);
										FMTabList.plugin.bountyList.setValue(event.getPlayer().getUniqueId().toString(), 0);
									}

									if (FMTabList.plugin.priceCountList != null && FMTabList.plugin.killCountList != null) {
										if (FMTabList.plugin.killCountList.containsKey(event.getPlayer().getUniqueId().toString()) && FMTabList.plugin.priceCountList.containsKey(event.getPlayer().getUniqueId().toString())) {
											int killCT = FMTabList.plugin.killCountList.get(event.getPlayer().getUniqueId().toString());
											long priceCT = FMTabList.plugin.priceCountList.get(event.getPlayer().getUniqueId().toString());
											if (killCT > 0) {
												String Amount = FMTabList.format(priceCT);

												player.sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]: " + ChatColor.RED + "Warning!!! This player kills other player, I would recommend NOT accepting TPA request from them!");
												player.sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]: " + ChatColor.RED + "--- Any thing lost (xp, items, and gear) will not be recoverd!");
												player.sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]: " + ChatColor.RED + "--- There are no TPA killing rules!");
												player.sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]: " + ChatColor.RED + "--- Bounty for killing this player is: " + ChatColor.LIGHT_PURPLE + "$" + Amount);
												player.sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]: " + ChatColor.RED + "--- player has killed: " + ChatColor.LIGHT_PURPLE + "$" + killCT);
											}
										}
									}
								}
							}
						}
						else
						{
							event.setCancelled(true);
							event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + "Server" + ChatColor.GOLD + "]:" + ChatColor.RED + " I'm sorry we can't find a player by the name of " + args[1] + ". Please try using there name, and not there nickname!");
						}

					}
				}
			}
			if (event.getMessage().equalsIgnoreCase("/yes") && FMTabList.plugin.voter.getProperty(event.getPlayer().getUniqueId().toString()) == null)
			{
				if (FMTabList.plugin.voter.getProperty("voteyes") == null)
				{
					FMTabList.plugin.voter.setProperty("voteyes", 0);
					FMTabList.plugin.voter.save();
				}
				int count = (int)FMTabList.plugin.voter.getProperty("voteyes");
				count++;
				FMTabList.plugin.voter.setProperty("voteyes", count);
				FMTabList.plugin.voter.setProperty(event.getPlayer().getUniqueId().toString(), true);
				FMTabList.plugin.voter.save();
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + " thank you for your vote!");
			}
			else
			{
				if (event.getMessage().equalsIgnoreCase("/yes"))
				{
					event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + " You can only vote once for this question!");
					event.setCancelled(true);
				}
			}
			if (event.getMessage().equalsIgnoreCase("/no") && FMTabList.plugin.voter.getProperty(event.getPlayer().getUniqueId().toString()) == null)
			{
				if (FMTabList.plugin.voter.getProperty("voteno") == null)
				{
					FMTabList.plugin.voter.setProperty("voteno", 0);
					FMTabList.plugin.voter.save();
				}
				int count = (int)FMTabList.plugin.voter.getProperty("voteno");
				count++;
				FMTabList.plugin.voter.setProperty("voteno", count);
				FMTabList.plugin.voter.setProperty(event.getPlayer().getUniqueId().toString(), true);
				FMTabList.plugin.voter.save();
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + " thank you for your vote!");
			}
			else
			{
				if (event.getMessage().equalsIgnoreCase("/no"))
				{
					event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + " You can only vote once for this question!");
					event.setCancelled(true);
				}
			}



			if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) {
				if (event.getMessage().toLowerCase().startsWith("/echest") || event.getMessage().toLowerCase().startsWith("/ec")) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "Can only use echest in survival!");
				}
			}
			if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
				if (event.getMessage().toLowerCase().startsWith("/tpa") || event.getMessage().toLowerCase().startsWith("/tpask") || event.getMessage().toLowerCase().startsWith("/tpc") || event.getMessage().toLowerCase().startsWith("/tpahere") || event.getMessage().toLowerCase().startsWith("/tpchere"))
				{

					String[] args = event.getMessage().split(" ");
					if (args.length > 1) {
						if (Bukkit.getPlayer(args[1]) != null) {
							teleportpro tmp = new teleportpro(event.getPlayer().getUniqueId(), Bukkit.getPlayer(args[1]).getUniqueId(), System.currentTimeMillis());
							tpaprocter.add(tmp);
						}
					}
				}
				if (event.getMessage().equalsIgnoreCase("/tpaccept") || event.getMessage().toLowerCase().startsWith("/tpaccept") || event.getMessage().toLowerCase().startsWith("/tpyes") || event.getMessage().equalsIgnoreCase("/tpyes"))
				{
					for(int i = 0; i<tpaprocter.size();i++)
					{
						teleportpro tmp = tpaprocter.get(i);
						if (tmp.usernameB.equals(event.getPlayer().getUniqueId()))
						{
							tmp.protect = true;
							tmp.timetp = System.currentTimeMillis();
							tpaprocter.remove(i);
							tpaprocter.add(tmp);
							Player A = Bukkit.getPlayer(tmp.usernameA);
							Player B = Bukkit.getPlayer(tmp.usernameB);
							if (A != null)
							{
								A.sendMessage(ChatColor.RED + "You are protected from damage for 30 seconds.");
							}
							if (B != null)
							{
								B.sendMessage(ChatColor.RED + "You are protected from damage for 30 seconds.");
							}
							break;
						}
					}
				}
				if (event.getMessage().equalsIgnoreCase("/tpdeny") || event.getMessage().toLowerCase().startsWith("/tpdeny") || event.getMessage().toLowerCase().startsWith("/tpno") || event.getMessage().equalsIgnoreCase("/tpno"))
				{
					for(int i = 0; i<tpaprocter.size();i++) {
						teleportpro tmp = tpaprocter.get(i);
						if (tmp.usernameB.equals(event.getPlayer().getUniqueId())) {
							tpaprocter.remove(i);
							break;
						}
					}
				}
			}
		}


		plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					setTabText(p);
				}
			}
		},2L);


	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event){

		Player p = event.getPlayer();

		String ipAddress = event.getPlayer().getAddress().toString().replace("/", "").split(":")[0];
		String AltList =  FMTabList.plugin.ipadressAltChecker(ipAddress, p);
		Collection<? extends Player> onlineList = Bukkit.getOnlinePlayers();
		for (Player onPlayer : onlineList) {
			if (onPlayer.hasPermission("FMTabList.email")) {
				onPlayer.sendMessage(ChatColor.GOLD + "Linked Accounts for " + p.getName() + ": " + ChatColor.LIGHT_PURPLE + AltList);
			}
		}



		User user= ess.getUser(p);
		user.setAfk(false);
		setTabText(p);

		PacketContainer pc = this.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

		pc.getChatComponents().write(0, WrappedChatComponent.fromText(replaceColors("&c&lFires &a&lOf &b&lTitan")))
				.write(1, WrappedChatComponent.fromText(replaceColors("&2Owner: &6Freethemice" )));
		try
		{
			this.protocolManager.sendServerPacket(event.getPlayer(), pc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if(!event.getPlayer().hasPlayedBefore()) {

			FMTabList.WhosFirst = true;

			String[] Names = FMTabList.plugin.lookupUsername(event.getPlayer().getUniqueId().toString());
			if (Names != null) {
				for (String name : Names) {
					if (!name.equals("")) {
						File tmpBackup = new File("worldmain" + File.separator + "playerdata" + File.separator + "Mybackups" + File.separator + name);
						if (tmpBackup.exists()) {
							File[] listBackups = tmpBackup.listFiles();
							long lastMod = -1;
							File tmpBackupTo = null;
							for (File tmpBackup2 : listBackups) {;
								if (tmpBackup2.isFile()) {
									if (tmpBackup2.lastModified() > lastMod) {
										lastMod = tmpBackup2.lastModified();
										tmpBackupTo = tmpBackup2;
									}
								}
							}
							if (tmpBackupTo != null) {
								FMTabList.plugin.CopyFile(tmpBackupTo, new File("worldmain" + File.separator + "playerdata" + File.separator + event.getPlayer().getUniqueId().toString() + ".dat"));
								event.getPlayer().loadData();
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "broadcast &d>>>>>>>>>>>>>>>>>>>>>&fBack Up Restored for " + event.getPlayer().getName() + "&d<<<<<<<<<<<<<<<<<<<<<");
								return;
							}
						}
					}
				}
			}
		}

	}



	private String replaceColors(String input)
	{
		ChatColor[] allcolors = ChatColor.values();
		for(int i = 0; i < allcolors.length;i++)
		{
			String Colorcode =allcolors[i].toString().substring(1);

			input = input.replace("&" + Colorcode, "" + ChatColor.getByChar(Colorcode));
		}
		return input;
	}

	public void setTabText(Player p) {

        User user = ess.getUser(p);
		String prefix = ess.getPermissionsHandler().getPrefix(p);
		String suffix = ess.getPermissionsHandler().getSuffix(p);
		prefix = replaceColors(prefix);
		prefix = prefix.replace("[", "");
		prefix = prefix.replace("]", "");
		prefix = prefix.replace(" ", "");
		String nick = user.getNickname();

		String tabName ="";
		if (FMTabList.plugin.priceCountList.containsKey(p.getUniqueId().toString()))
		{
			long money = FMTabList.plugin.priceCountList.get(p.getUniqueId().toString());
			if (money > 0)
			{
				String Amount = FMTabList.format(money);
				tabName = tabName + ChatColor.GOLD + "[" + ChatColor.RED + "$" + Amount + ChatColor.GOLD + "]";
			}
		}
		tabName = tabName + ChatColor.GOLD + "[" + ChatColor.RESET + returnAlias(p.getWorld()) + ChatColor.GOLD + "]";
		if (prefix.length() > 2) {
			tabName = tabName + ChatColor.GOLD + "[" + ChatColor.WHITE + prefix + ChatColor.GOLD + "]";
		}


		if (user.getMuted() || GriefPrevention.instance.dataStore.isSoftMuted(p.getUniqueId())) {
			tabName = ChatColor.GOLD + "[" + ChatColor.GRAY + "MUTED" + ChatColor.GOLD + "]" + tabName;
			tabName = tabName + ChatColor.GRAY + ChatColor.STRIKETHROUGH + p.getName();
		} else if (user.isJailed()) {
			tabName = ChatColor.GOLD + "[" + ChatColor.GRAY + "JAILED" + ChatColor.GOLD + "]" + tabName;
			tabName = tabName + ChatColor.GRAY + ChatColor.STRIKETHROUGH + p.getName();
		} else if (user.isAfk()) {
			tabName = ChatColor.GOLD + "[" + ChatColor.GRAY + "AFK" + ChatColor.GOLD + "]" + tabName;
			tabName = tabName + ChatColor.GRAY + p.getName();
		} else if (user.isVanished() && !p.hasPermission("FMTabList.email")) {
			tabName = ChatColor.GOLD + "[" + ChatColor.BLUE + "VANISHED" + ChatColor.GOLD + "]" + tabName;
			tabName = tabName + ChatColor.BLUE + p.getName();
		} else if (user.isHidden() && !p.hasPermission("FMTabList.email")) {
			tabName = ChatColor.GOLD + "[" + ChatColor.RED + "HIDDEN" + ChatColor.GOLD + "]" + tabName;
			tabName = tabName + ChatColor.RED + p.getName();
		} else {
			tabName = tabName + ChatColor.WHITE + p.getName();
		}
		if (!suffix.equals(""))
		{
			tabName = tabName + ChatColor.translateAlternateColorCodes('&', suffix);
		}
		if (nick != null && !nick.equals("")) {
			tabName = tabName + ChatColor.GOLD + " AKA:" + ChatColor.WHITE + user.getNickname();
		}

/*
		if (Ticks < 10) {
			Help = ChatColor.GOLD + "<" + ChatColor.AQUA + "Party" + ChatColor.GOLD + ">";
			if (Parties.getInstance() != null) {
				if (Parties.getInstance().getPlayerHandler() != null) {
					if (Parties.getInstance().getPlayerHandler().getPartyFromPlayer(p) != null) {
						Party myParty = Parties.getInstance().getPlayerHandler().getPartyFromPlayer(p);
						tabName = tabName + ChatColor.GOLD + "<" + ChatColor.AQUA + myParty.getName() + ChatColor.GOLD + ">";
					}
				}
			}

		} else if (Ticks < 15) {
			Help = ChatColor.GOLD + "[" + ChatColor.WHITE + "Where" + ChatColor.GOLD + "]";
			tabName = tabName + ChatColor.GOLD + "[" + ChatColor.RED + returnAlias(p.getWorld().getName()) + ChatColor.GOLD + "]";
		}*/

		DateFormat df = new SimpleDateFormat("hh:mm:ss aa");
		Calendar calobj = Calendar.getInstance();
		String ServerTime = ChatColor.GREEN  + "Server: " + ChatColor.WHITE + df.format(calobj.getTime()).replace(" ", " " + ChatColor.GOLD);


		p.setPlayerListName(tabName);


		long TimeToBlocks = 1000 *60 *37 - (System.currentTimeMillis() - plugin.lbtime) ;
		int seconds = (int) (TimeToBlocks / 1000) % 60 ;
		int minutes = (int) ((TimeToBlocks / (1000*60)) % 60);
		int hours   = (int) ((TimeToBlocks / (1000*60*60)) % 24);


		long TimeToEnvoy = (1000 * 60 * 60 * 1 + 1000 * 60 * 15) - (System.currentTimeMillis() - plugin.evtime) ;
		int Eseconds = (int) (TimeToEnvoy / 1000) % 60 ;
		int Eminutes = (int) ((TimeToEnvoy / (1000*60)) % 60);
		int Ehours   = (int) ((TimeToEnvoy / (1000*60*60)) % 24);

		long TimeToEnvoyDeath = (plugin.evtimedeath - System.currentTimeMillis()) ;
		int EsecondsDeath = (int) (TimeToEnvoyDeath / 1000) % 60 ;
		int EminutesDeath = (int) ((TimeToEnvoyDeath / (1000*60)) % 60);


		long TimeToCrateDeath = (plugin.lbtimedeath - System.currentTimeMillis()) ;
		int CRsecondsDeath = (int) (TimeToCrateDeath / 1000) % 60 ;
		int CRminutesDeath = (int) ((TimeToCrateDeath / (1000*60)) % 60);

		String EnvoyTime = Ehours + ":" + Eminutes + ":" + Eseconds;

		if (FMTabList.plugin.evtimedeath > 0)
		{
			FMTabList.plugin.evcountdown = EminutesDeath + ":" + EsecondsDeath;
			EnvoyTime = ChatColor.YELLOW + "Event Ends: " + ChatColor.WHITE + EminutesDeath + ":" + EsecondsDeath;
		}
		else
		{
			EnvoyTime = ChatColor.YELLOW + "Event Time: " + ChatColor.WHITE + EnvoyTime;
		}

		String CrateTime = "Crate Unlockes In: " + ChatColor.WHITE + hours + ":" + minutes + ":" + seconds + "";
		if (FMTabList.plugin.lbtimedeath > 0)
		{
			CrateTime = FMTabList.plugin.cratesName + ": "  + ChatColor.WHITE + CRminutesDeath + ":" + CRsecondsDeath;
		}

		PacketContainer pc = this.protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		if (FMTabList.plugin.voter.getProperty("voteno") == null)
		{
			FMTabList.plugin.voter.setProperty("voteno", 0);
		}
		if (FMTabList.plugin.voter.getProperty("voteyes") == null)
		{
			FMTabList.plugin.voter.setProperty("voteyes", 0);
		}

		if (((String) FMTabList.plugin.voter.getProperty("votequestion")).equalsIgnoreCase("off "))
		{
			pc.getChatComponents().write(0, WrappedChatComponent.fromText(replaceColors("&c&lFires &a&lOf &b&lTitan") + "\n" + ChatColor.WHITE + Bukkit.getOnlinePlayers().size() + "/500" + "\n" + ServerTime + "\n")).write(1, WrappedChatComponent.fromText("\n" + ChatColor.RED + ChatColor.BOLD + "No vote at this time" + ChatColor.GREEN + "\n" + CrateTime + "\n" + EnvoyTime + replaceColors("\n&2Owner: &6Freethemice")));
		}
		else {
			pc.getChatComponents().write(0, WrappedChatComponent.fromText(replaceColors("&c&lFires &a&lOf &b&lTitan") + "\n" + ChatColor.WHITE + Bukkit.getOnlinePlayers().size() + "/500" + "\n" + ServerTime + "\n")).write(1, WrappedChatComponent.fromText("\n" + ChatColor.RED + ChatColor.BOLD + "Yes: " + ChatColor.WHITE + (int) FMTabList.plugin.voter.getProperty("voteyes") + ChatColor.RED + ChatColor.BOLD + " No: " + ChatColor.WHITE + (int) FMTabList.plugin.voter.getProperty("voteno") + ChatColor.GREEN + "\n" + CrateTime+ "\n" + EnvoyTime + replaceColors("\n&2Owner: &6Freethemice")));

		}

		try
		{
			this.protocolManager.sendServerPacket(p, pc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}


	}
	public String returnAlias(World w) {
		Plugin mv = Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		if (mv == null) return w.getName();
		MultiverseCore mvc = (MultiverseCore) mv;
		MultiverseWorld mvw = mvc.getMVWorldManager().getMVWorld(w);
		if (mvw == null)
		{
			return ChatColor.GRAY + "Parkour";

		}
		try {
			return mvw.getColoredWorldString();
		}
		catch (Exception e)
		{
			return ChatColor.GRAY + "Parkour";
		}
	}
    
}
