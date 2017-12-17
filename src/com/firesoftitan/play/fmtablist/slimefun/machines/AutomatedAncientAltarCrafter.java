package com.firesoftitan.play.fmtablist.slimefun.machines;

import com.firesoftitan.play.fmtablist.slimefun.items.SFItems;
import me.mrCookieSlime.CSCoreLibPlugin.CSCoreLib;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.AdvancedMenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.InvUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.AncientAltar.Pedestals;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunBlockHandler;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.UnregisterReason;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.api.item_transport.RecipeSorter;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.*;

public abstract class AutomatedAncientAltarCrafter extends SlimefunItem {

	private static final int[] border = {0, 1, 3, 4, 5, 7, 8, 13, 14, 15, 16, 17, 50, 51, 52, 53};
	private static final int[] border_in = {9, 10, 11, 12, 13, 18, 22, 27, 31, 36, 40, 45, 46, 47, 48, 49};
	private static final int[] border_out = {23, 24, 25, 26, 32, 35, 41, 42, 43, 44};

	public static Map<String, ItemStack> recipes = new HashMap<String, ItemStack>();

	public AutomatedAncientAltarCrafter(Category category, ItemStack item, String name, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, name, recipeType, recipe);
		
		new BlockMenuPreset(name, "&6Automated Ancient Altar Crafter") {

			@Override
			public void init() {
				constructMenu(this);
			}

			@Override
			public void newInstance(final BlockMenu menu, final Block b) {
				if (!BlockStorage.hasBlockInfo(b) || BlockStorage.getBlockInfo(b, "enabled") == null || BlockStorage.getBlockInfo(b, "enabled").equals("false")) {
					menu.replaceExistingItem(6, new CustomItem(new MaterialData(Material.SULPHUR), "&7Enabled: &4\u2718", "", "&e> Click to enable this Machine"));
					menu.addMenuClickHandler(6, new MenuClickHandler() {

						@Override
						public boolean onClick(Player p, int arg1, ItemStack arg2, ClickAction arg3) {
							BlockStorage.addBlockInfo(b, "enabled", "true");
							newInstance(menu, b);
							return false;
						}
					});
				}
				else {
					menu.replaceExistingItem(6, new CustomItem(new MaterialData(Material.REDSTONE), "&7Enabled: &2\u2714", "", "&e> Click to disable this Machine"));
					menu.addMenuClickHandler(6, new MenuClickHandler() {

						@Override
						public boolean onClick(Player p, int arg1, ItemStack arg2, ClickAction arg3) {
							BlockStorage.addBlockInfo(b, "enabled", "false");
							newInstance(menu, b);
							return false;
						}
					});
				}
			}

			@Override
			public boolean canOpen(Block b, Player p) {
				return p.hasPermission("slimefun.inventory.bypass") || CSCoreLib.getLib().getProtectionManager().canAccessChest(p.getUniqueId(), b, true);
			}

			@Override
			public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
				return new int[0];
			}

			@Override
			public int[] getSlotsAccessedByItemTransport(BlockMenu menu, ItemTransportFlow flow, ItemStack item) {
				if (flow.equals(ItemTransportFlow.WITHDRAW)) return getOutputSlots();
				
				List<Integer> slots = new ArrayList<Integer>();
				for (int slot: getInputSlots()) {
					if (menu.getItemInSlot(slot) != null) slots.add(slot);
				}
				Collections.sort(slots, new RecipeSorter(menu));
				return ArrayUtils.toPrimitive(slots.toArray(new Integer[slots.size()]));
			}
		};
		
		registerBlockHandler(name, new SlimefunBlockHandler() {
			
			@Override
			public void onPlace(Player p, Block b, SlimefunItem item) {
				BlockStorage.addBlockInfo(b, "enabled", "false");
			}
			
			@Override
			public boolean onBreak(Player p, Block b, SlimefunItem item, UnregisterReason reason) {
				BlockMenu inv = BlockStorage.getInventory(b);
				if (inv != null) {
					for (int slot: getInputSlots()) {
						if (inv.getItemInSlot(slot) != null) b.getWorld().dropItemNaturally(b.getLocation(), inv.getItemInSlot(slot));
					}
					for (int slot: getOutputSlots()) {
						if (inv.getItemInSlot(slot) != null) b.getWorld().dropItemNaturally(b.getLocation(), inv.getItemInSlot(slot));
					}
				}
				return true;
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	protected void constructMenu(BlockMenuPreset preset) {
		for (int i: border) {
			preset.addItem(i, new CustomItem(new MaterialData(Material.STAINED_GLASS_PANE, (byte) 7), " "),
			new MenuClickHandler() {

				@Override
				public boolean onClick(Player arg0, int arg1, ItemStack arg2, ClickAction arg3) {
					return false;
				}
						
			});
		}
		for (int i: border_in) {
			preset.addItem(i, new CustomItem(new MaterialData(Material.STAINED_GLASS_PANE, (byte) 11), " "),
			new MenuClickHandler() {

				@Override
				public boolean onClick(Player arg0, int arg1, ItemStack arg2, ClickAction arg3) {
					return false;
				}
						
			});
		}
		for (int i: border_out) {
			preset.addItem(i, new CustomItem(new MaterialData(Material.STAINED_GLASS_PANE, (byte) 1), " "),
			new MenuClickHandler() {

				@Override
				public boolean onClick(Player arg0, int arg1, ItemStack arg2, ClickAction arg3) {
					return false;
				}
						
			});
		}
		
		for (int i: getOutputSlots()) {
			preset.addMenuClickHandler(i, new AdvancedMenuClickHandler() {
				
				@Override
				public boolean onClick(Player p, int slot, ItemStack cursor, ClickAction action) {
					return false;
				}

				@Override
				public boolean onClick(InventoryClickEvent e, Player p, int slot, ItemStack cursor, ClickAction action) {
					return cursor == null || cursor.getType() == null || cursor.getType() == Material.AIR;
				}
			});
		}
		try {
			preset.addItem(2, new CustomItem(SFItems.ANCIENT_ALTAR_CRAFTER_BLOCK, "&eRecipe", "", "&bPut in the Recipe you want to craft", "&6Ancient Altar Recipes ONLY"),
					new MenuClickHandler() {

						@Override
						public boolean onClick(Player arg0, int arg1, ItemStack arg2, ClickAction arg3) {
							return false;
						}

					});
		}
		catch (Exception e)
		{

		}
	}
	
	public abstract int getEnergyConsumption();
	
	public int[] getInputSlots() {
		return new int[] {19, 20, 21, 28, 29, 30, 37, 38, 39};
	}
	
	public int[] getOutputSlots() {
		return new int[] {33, 34};
	}
	
	private Inventory inject(Block b) {
		int size = BlockStorage.getInventory(b).toInventory().getSize();
		Inventory inv = Bukkit.createInventory(null, size);
		for (int i = 0; i < size; i++) {
			inv.setItem(i, new CustomItem(Material.COMMAND, " &4ALL YOUR PLACEHOLDERS ARE BELONG TO US", 0));
		}
		for (int slot: getOutputSlots()) {
			inv.setItem(slot, BlockStorage.getInventory(b).getItemInSlot(slot));
		}
		return inv;
	}
	
	public boolean fits(Block b, ItemStack[] items) {
		return inject(b).addItem(items).isEmpty();
	}
	
	public void pushItems(Block b, ItemStack[] items) {
		Inventory inv = inject(b);
		inv.addItem(items);
		
		for (int slot: getOutputSlots()) {
			BlockStorage.getInventory(b).replaceExistingItem(slot, inv.getItem(slot));
		}
	}
	
	@Override
	public void register(boolean slimefun) {
		addItemHandler(new BlockTicker() {
			
			@Override
			public void tick(Block b, SlimefunItem sf, Config data) {
				AutomatedAncientAltarCrafter.this.tick(b);
			}

			@Override
			public void uniqueTick() {
			}

			@Override
			public boolean isSynchronized() {
				return false;
			}
		});

		super.register(slimefun);
	}
	
	protected void tick(Block b) {
		if (BlockStorage.getBlockInfo(b, "enabled").equals("false")) return;
		if (ChargableBlock.getCharge(b) < getEnergyConsumption()) return;
		
		BlockMenu menu = BlockStorage.getInventory(b);
		List<ItemStack> input = new ArrayList<ItemStack>();
		ItemStack catalyst = null;
		for (int o = 0; o < 9;o++ ) {
			if (o != 4) {
				ItemStack stack = menu.getItemInSlot(this.getInputSlots()[o]);
				if (stack != null) {
					if (stack.getAmount() < 2) {
						return;
					}
				}
				input.add(stack);
			}
			else
			{
				catalyst =  menu.getItemInSlot(this.getInputSlots()[o]);
			}

		}
		if (catalyst != null && !catalyst.getType().equals(Material.AIR)) {
			ItemStack result = Pedestals.getRecipeOutput(catalyst, input);
			if (result != null && !result.getType().equals(Material.AIR)) {
				if (this.fits(b, new ItemStack[]{result})) {
					ChargableBlock.addCharge(b, -this.getEnergyConsumption());
					for (int j = 0; j < 9; j++) {
						ItemStack item = menu.getItemInSlot(this.getInputSlots()[j]);
						if (item.getAmount() < 2 ) {
							return;
						}
					}
					for (int slot: getInputSlots()) {
						if (BlockStorage.getInventory(b).getItemInSlot(slot) != null) {
							BlockStorage.getInventory(b).replaceExistingItem(slot, InvUtils.decreaseItem(BlockStorage.getInventory(b).getItemInSlot(slot), 1));
						}
					}
					this.pushItems(b, new ItemStack[]{result});

				}
			}
		}



	}
}
