package com.firesoftitan.play.fmtablist.slimefun.machines;

import com.firesoftitan.play.fmtablist.slimefun.items.SFItems;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.InvUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineHelper;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class TitanAutoDisenchanter extends AContainer {

	public TitanAutoDisenchanter(Category category, ItemStack item, String name, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, name, recipeType, recipe);
		SlimefunStartup.instance.myTitanHooks.allMachines.add(this);
	}

	@Override
	public String getInventoryTitle() {
		return "&5Auto-Disenchanter";
	}

	@Override
	public ItemStack getProgressBar() {
		return new ItemStack(Material.DIAMOND_CHESTPLATE);
	}

	@Override
	public void registerDefaultRecipes() {}

	@Override
	public int getEnergyConsumption() {
		return 9;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void tick(Block b) {
		if (this.isProcessing(b)) {
			int timeleft = this.progress.get(b);
			if (timeleft > 0) {
				ItemStack item = this.getProgressBar().clone();
				item.setDurability(MachineHelper.getDurability(item, timeleft, this.processing.get(b).getTicks()));
				ItemMeta im = item.getItemMeta();
				im.setDisplayName(" ");
				List<String> lore = new ArrayList<String>();
				lore.add(MachineHelper.getProgress(timeleft, this.processing.get(b).getTicks()));
				lore.add("");
				lore.add(MachineHelper.getTimeLeft(timeleft / 2));
				im.setLore(lore);
				item.setItemMeta(im);

				BlockStorage.getInventory(b).replaceExistingItem(22, item);

				if (ChargableBlock.isChargable(b)) {
					if (ChargableBlock.getCharge(b) < this.getEnergyConsumption()) return;
					ChargableBlock.addCharge(b, -this.getEnergyConsumption());
					this.progress.put(b, timeleft - 1);
				}
				else this.progress.put(b, timeleft - 1);

			}
			else {
				if (processing.get(b).getOutput() == null)
				{
					progress.remove(b);
					return;
				}
				BlockStorage.getInventory(b).replaceExistingItem(22, new CustomItem(new MaterialData(Material.STAINED_GLASS_PANE, (byte) 15), " "));
				this.pushItems(b, this.processing.get(b).getOutput());

				this.progress.remove(b);
				this.processing.remove(b);;
			}
		}
		else {
			MachineRecipe r = null;
			String TitanEnchant = "";
			slots:
			for (int slot: this.getInputSlots()) {
				ItemStack target = BlockStorage.getInventory(b).getItemInSlot(slot == this.getInputSlots()[0] ? this.getInputSlots()[1]: this.getInputSlots()[0]);
				ItemStack item = BlockStorage.getInventory(b).getItemInSlot(slot);
				if(SlimefunItem.getByItem(item) != null && !SlimefunItem.getByItem(item).isDisenchantable()) return;
				if (item != null && target != null && target.getType() == Material.BOOK) {
					int amount = 0;
					if (item.getItemMeta() == null) return;
					if (!item.getItemMeta().hasLore()) return;
					if (target.getItemMeta() == null) return;
					if (!target.getItemMeta().hasDisplayName()) return;
					int toremove = 0;
					for (int i = 0; i <item.getItemMeta().getLore().size(); i++) {
						String lore = item.getItemMeta().getLore().get(i);
						if (ChatColor.stripColor(lore).toLowerCase().startsWith(ChatColor.stripColor("titan." + target.getItemMeta().getDisplayName()).toLowerCase())) {
							amount++;
							try {
								int CurrentPower = SlimefunStartup.instance.myTitanHooks.fromRoman(lore.split(" ")[1]);
								toremove = Math.min(CurrentPower, target.getAmount());

								CurrentPower = CurrentPower - target.getAmount();
								if (CurrentPower < 1)
								{
									ItemMeta meta = item.getItemMeta();
									List<String> LORE =  meta.getLore();
									LORE.remove(i);
									meta.setLore(LORE);
									item.setItemMeta(meta);
								}
								else
								{
									ItemMeta meta = item.getItemMeta();
									List<String> LORE =  meta.getLore();
									LORE.remove(i);
									LORE.add(lore.split(" ")[0] + " " + SlimefunStartup.instance.myTitanHooks.toRoman(CurrentPower));
									meta.setLore(LORE);
									item.setItemMeta(meta);
								}
							}

							catch (Exception e)
							{
								return;
							}
							break;
						}
					}
					if (amount == 0)
					{
						Enchantment toRemove = Enchantment.getByName(target.getItemMeta().getDisplayName());
						if (toRemove != null) {
							if (item.getEnchantmentLevel(toRemove) > 0) {
								amount++;
								item.getItemMeta().removeEnchant(toRemove);
							}
						}
					}
					if (amount > 0) {
						ItemStack book = SFItems.getTitanEnchant("Titan." + target.getItemMeta().getDisplayName());
						if (book == null) return;
						ItemStack newItem = item.clone();
						book.setAmount(toremove);
						newItem.setAmount(1);
						r = new MachineRecipe(100 * amount, new ItemStack[] {target, item}, new ItemStack[] {newItem, book});
						break slots;
					}
				}
			}

			if (r != null) {
				if (!this.fits(b, r.getOutput())) return;
				for (int slot: this.getInputSlots()) {
					int toremove = r.getOutput()[1].getAmount();
					if (BlockStorage.getInventory(b).getItemInSlot(slot).getType() == Material.BOOK)
					{
						BlockStorage.getInventory(b).replaceExistingItem(slot, InvUtils.decreaseItem(BlockStorage.getInventory(b).getItemInSlot(slot), toremove));
					}
					else {
						BlockStorage.getInventory(b).replaceExistingItem(slot, InvUtils.decreaseItem(BlockStorage.getInventory(b).getItemInSlot(slot), 1));
					}
				}
				this.processing.put(b, r);
				this.progress.put(b, r.getTicks());
			}
		}
	}

	@Override
	public int getSpeed() {
		return 1;
	}

	@Override
	public String getMachineIdentifier() {
		return "AUTO_DISENCHANTER";
	}

}
