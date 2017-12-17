package com.firesoftitan.play.fmtablist.sensibletoolbox.machines;

import me.mrCookieSlime.sensibletoolbox.blocks.machines.Generator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.material.MaterialData;

/**
 * Created by Daniel on 4/11/2017.
 */
public class SFtoSTBGenerater extends Generator {
    public SFtoSTBGenerater ()
    {
        super();

    }
    @Override
    public int getProgressItemSlot() {
        return 0;
    }

    @Override
    public int getProgressCounterSlot() {
        return 0;
    }

    @Override
    public ItemStack getProgressIcon() {
        return null;
    }

    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Override
    public int[] getUpgradeSlots() {
        return new int[0];
    }

    @Override
    public int getUpgradeLabelSlot() {
        return 0;
    }

    @Override
    public int getMaxCharge() {
        return 100000;
    }

    @Override
    public int getChargeRate() {
        return 1000;
    }

    @Override
    public MaterialData getMaterialData() {
        return null;
    }

    @Override
    public String getItemName() {
        return "SFtoSTBGenerater";
    }

    @Override
    public String[] getLore() {
        return new String[0];
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }
}
