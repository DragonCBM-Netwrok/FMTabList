package com.firesoftitan.play.fmtablist.slimefun.machines;

import com.firesoftitan.play.fmtablist.slimefun.containers.BContainer;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.InvUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineHelper;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class CharcoalFactory extends BContainer {;
    public CharcoalFactory(Category category, ItemStack item, String name, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, name, recipeType, recipe);

    }

    @Override
    public String getInventoryTitle() {
        return "&bCharcoal Factory";
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
                ChargableBlock.addCharge(b, -getEnergyConsumption());

                BlockStorage.getInventory(b).replaceExistingItem(22, new CustomItem(new MaterialData(Material.STAINED_GLASS_PANE, (byte) 15), " "));
                pushItems(b, processing.get(b).getOutput());

                progress.remove(b);
                processing.remove(b);
            }
        }
        else {
            int LB = -1;
            int WB = -1;
            int SA = -1;
            int BM = -1;
            for (int slot: getInputSlots()) {
                if (SlimefunManager.isItemSimiliar(BlockStorage.getInventory(b).getItemInSlot(slot), new ItemStack(Material.LAVA_BUCKET, 1), true)) {
                    if (LB > -1)
                    {
                        BlockStorage.getInventory(b).replaceExistingItem(LB, InvUtils.decreaseItem(BlockStorage.getInventory(b).getItemInSlot(LB), 1));
                        ItemStack out =  BlockStorage.getInventory(b).getItemInSlot(slot).clone();
                        out.setAmount(out.getAmount() + 1);
                        BlockStorage.getInventory(b).replaceExistingItem(slot, out);
                    }
                    LB = slot;
                }
                if (SlimefunManager.isItemSimiliar(BlockStorage.getInventory(b).getItemInSlot(slot), new ItemStack(Material.WATER_BUCKET, 1), true)) {
                    if (WB > -1)
                    {
                        BlockStorage.getInventory(b).replaceExistingItem(WB, InvUtils.decreaseItem(BlockStorage.getInventory(b).getItemInSlot(WB), 1));
                        ItemStack out =  BlockStorage.getInventory(b).getItemInSlot(slot).clone();
                        out.setAmount(out.getAmount() + 1);
                        BlockStorage.getInventory(b).replaceExistingItem(slot, out);
                    }
                    WB = slot;
                }
                if (SlimefunManager.isItemSimiliar(BlockStorage.getInventory(b).getItemInSlot(slot), new ItemStack(Material.SAPLING, 1), true)) {
                    if (SA > -1)
                    {
                        BlockStorage.getInventory(b).replaceExistingItem(SA, InvUtils.decreaseItem(BlockStorage.getInventory(b).getItemInSlot(SA), 1));
                        ItemStack out =  BlockStorage.getInventory(b).getItemInSlot(slot).clone();
                        out.setAmount(out.getAmount() + 1);
                        BlockStorage.getInventory(b).replaceExistingItem(slot, out);
                    }
                    SA = slot;
                }
                if (SlimefunManager.isItemSimiliar(BlockStorage.getInventory(b).getItemInSlot(slot), new ItemStack(Material.INK_SACK, 1, (short)15), true)) {
                    if (BM > -1)
                    {
                        BlockStorage.getInventory(b).replaceExistingItem(BM, InvUtils.decreaseItem(BlockStorage.getInventory(b).getItemInSlot(BM), 1));
                        ItemStack out =  BlockStorage.getInventory(b).getItemInSlot(slot).clone();
                        out.setAmount(out.getAmount() + 1);
                        BlockStorage.getInventory(b).replaceExistingItem(slot, out);
                    }
                    BM = slot;
                }
            }
            if (LB > -1 && WB > -1 && SA > -1 && BM > -1) {
                Random number = new Random(System.currentTimeMillis());
                ItemStack adding = new ItemStack(Material.COAL, 10 + number.nextInt(10), (short)1);

                adding = adding.clone();

                MachineRecipe r = new MachineRecipe(4 / getSpeed(), new ItemStack[0], new ItemStack[] {adding, new ItemStack(Material.BUCKET, 2)});
                if (!fits(b, r.getOutput())) return;
                for (int slot: getInputSlots()) {
                    BlockStorage.getInventory(b).replaceExistingItem(slot, InvUtils.decreaseItem(BlockStorage.getInventory(b).getItemInSlot(slot), 1));
                }
                processing.put(b, r);
                progress.put(b, r.getTicks());
            }
        }
    }

    @Override
    public String getMachineIdentifier() {
        return "CHARCOAL_FACTORY";
    }

}
