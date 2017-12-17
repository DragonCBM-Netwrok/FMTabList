package com.firesoftitan.play.fmtablist.slimefun.machines;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineHelper;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBMachine;
import me.mrCookieSlime.sensibletoolbox.blocks.machines.Generator;
import me.mrCookieSlime.sensibletoolbox.core.storage.LocationManager;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class SlimefunToSTBConverter extends AContainer {


    public SlimefunToSTBConverter(Category category, ItemStack item, String name, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, name, recipeType, recipe);
    }

    @Override
    public String getInventoryTitle() {
        return "&bSlimefun To STB Converter";
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
        try {
            if (ChargableBlock.isChargable(b)) {

                BaseSTBMachine machine = LocationManager.getManager().get(b.getLocation().add(0, 1, 0), BaseSTBMachine.class);
                if (machine == null) return;
                if (!(machine instanceof Generator)) return;

                Generator gen = (Generator) machine;
                ItemStack item = getProgressBar().clone();
                item.setDurability(MachineHelper.getDurability(item, gen.getMaxCharge() - (int) gen.getCharge(), gen.getMaxCharge()));
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(" ");
                List<String> lore = new ArrayList<String>();
                lore.add(MachineHelper.getProgress(gen.getMaxCharge() - (int) gen.getCharge(), gen.getMaxCharge()));
                lore.add("");
                im.setLore(lore);
                item.setItemMeta(im);

                if (ChargableBlock.getCharge(b) < getEnergyConsumption()) return;
                if (gen.getCharge() >= gen.getMaxCharge()) return;
                if (gen.getCharge() + getEnergyConsumption() * 1.1f > gen.getMaxCharge()) return;

                BlockStorage.getInventory(b).replaceExistingItem(22, item);


                ChargableBlock.addCharge(b, -getEnergyConsumption());
                gen.setCharge(gen.getCharge() + getEnergyConsumption() * 1.1f);
                gen.getLocation().getWorld().playEffect(gen.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
            }
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public String getMachineIdentifier() {
        return "SF4_TO_STB";
    }

}
