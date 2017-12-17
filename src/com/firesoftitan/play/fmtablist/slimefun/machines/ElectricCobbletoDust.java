package com.firesoftitan.play.fmtablist.slimefun.machines;

import com.firesoftitan.play.fmtablist.slimefun.items.SFItems;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.InvUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineHelper;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public abstract class ElectricCobbletoDust extends AContainer {;
    public ElectricCobbletoDust(Category category, ItemStack item, String name, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, name, recipeType, recipe);

    }

    @Override
    public String getInventoryTitle() {
        return "&bElectric Cobble to Dust";
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.GOLD_SPADE);
    }

    @Override
    public void registerDefaultRecipes() {}

    public abstract int getSpeed();

    @SuppressWarnings("deprecation")
    protected void tick(Block b) {
        if (isProcessing(b)) {
            int timeleft = progress.get(b);
            if (timeleft > 0 && getSpeed() < 10) {
                ItemStack item = getProgressBar().clone();
                item.setDurability(MachineHelper.getDurability(item, timeleft, processing.get(b).getTicks()));
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(" ");
                List<String> lore = new ArrayList<String>();
                lore.add(MachineHelper.getProgress(timeleft, processing.get(b).getTicks()));
                lore.add("");
                lore.add(MachineHelper.getTimeLeft(timeleft / 2));
                im.setLore(lore);
                item.setItemMeta(im);

                BlockStorage.getInventory(b).replaceExistingItem(22, item);

                if (ChargableBlock.isChargable(b)) {
                    if (ChargableBlock.getCharge(b) < getEnergyConsumption()) return;
                    ChargableBlock.addCharge(b, -getEnergyConsumption());
                    progress.put(b, timeleft - 1);
                }
                else progress.put(b, timeleft - 1);
            }
            else if (ChargableBlock.isChargable(b)) {
                if (ChargableBlock.getCharge(b) < getEnergyConsumption()) return;
                if (processing.get(b).getOutput() == null)
                {
                    progress.remove(b);
                    return;
                }
                ChargableBlock.addCharge(b, -getEnergyConsumption());

                BlockStorage.getInventory(b).replaceExistingItem(22, new CustomItem(new MaterialData(Material.STAINED_GLASS_PANE, (byte) 15), " "));
                pushItems(b, processing.get(b).getOutput());

                progress.remove(b);
                processing.remove(b);
            }
        }
        else {
            for (int slot: getInputSlots()) {
                if (SlimefunManager.isItemSimiliar(BlockStorage.getInventory(b).getItemInSlot(slot), new ItemStack(Material.COBBLESTONE, 8), true)) {

                    ItemStack adding = SlimefunItems.IRON_DUST;
                    if (SlimefunStartup.chance(100, 25)) adding = SlimefunItems.GOLD_DUST;
                    else if (SlimefunStartup.chance(100, 25)) adding = SlimefunItems.ALUMINUM_DUST;
                    else if (SlimefunStartup.chance(100, 25)) adding = SlimefunItems.COPPER_DUST;
                    else if (SlimefunStartup.chance(100, 25)) adding = SlimefunItems.ZINC_DUST;
                    else if (SlimefunStartup.chance(100, 25)) adding = SlimefunItems.TIN_DUST;
                    else if (SlimefunStartup.chance(100, 25)) adding = SlimefunItems.MAGNESIUM_DUST;
                    else if (SlimefunStartup.chance(100, 25)) adding = SlimefunItems.LEAD_DUST;
                    else if (SlimefunStartup.chance(100, 25)) adding = SlimefunItems.SILVER_DUST;
                    else if (SlimefunStartup.chance(500, 1)) adding = SFItems.EclipseNugget;
                    else if (SlimefunStartup.chance(200, 10)) adding = SFItems.LuckyNugget;
                    adding = adding.clone();
                    adding.setAmount(1);
                    if (getSpeed() > 9)
                    {
                        adding.setAmount(3);
                    }
                    if (getSpeed() > 19)
                    {
                        adding.setAmount(5);
                    }
                    MachineRecipe r = new MachineRecipe(4 / getSpeed(), new ItemStack[0], new ItemStack[] {adding});
                    if (!fits(b, r.getOutput())) return;
                    BlockStorage.getInventory(b).replaceExistingItem(slot, InvUtils.decreaseItem(BlockStorage.getInventory(b).getItemInSlot(slot), 8));
                    processing.put(b, r);
                    progress.put(b, r.getTicks());
                    break;
                }
            }
        }
    }

    @Override
    public String getMachineIdentifier() {
        return "ELECTRIC_COBBLE_TO_DUST";
    }

}
