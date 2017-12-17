package com.firesoftitan.play.fmtablist.slimefun.machines;

import com.firesoftitan.play.fmtablist.slimefun.items.SFItems;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.InvUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
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
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBMachine;
import me.mrCookieSlime.sensibletoolbox.core.storage.LocationManager;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class VoidMiner extends AContainer {

    private String[] skulls = new String[4];
    private int current = 0;
    public VoidMiner(Category category, ItemStack item, String name, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, name, recipeType, recipe);
        try {
            skulls[0] = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjY5ZDM0ODI3ZDkwYTM1ZDY3YjcxOGRiYzJlNDI5YmQ5OTI0MTRiMTk3MTU1MTc4MTVhZDg2MzBiNDI0ZTlmMiJ9fX0=";
            skulls[1] = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY3MDI1NWI1YjQ0N2I2NjgyNGVlYThiMTlmMmRhNDU1NGQxMzI2ZGZlNmEyZjUxNzYwYjE4NjM3ODcyZWE5In19fQ==";
            skulls[2] = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWYyYjk1ODk5OWY4YWZiY2I0OGE2YzE2Y2Q5ZGM5ZDM1NWQwNGUzNGI2ODdjZjI1NTFmZWQ1YjMyZThmODMxIn19fQ==";
            skulls[3] = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTIyYWY2N2M1YjFlMjUyZjgzYjM2NWIzOWE2YjU4OTVkMTU3NGRhYWIyZDU3NDg0MjIzY2Y0ZTE1OWM2N2UifX19";


        }catch (Exception e)
        {

        }

    }

    @Override
    public String getInventoryTitle() {
        return "&bVoid Miner";
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.BEDROCK);
    }

    @Override
    public void registerDefaultRecipes() {}

    public abstract int getSpeed();

    public void repaint(Block block) {
        // maybe one day Bukkit will have a block set method which takes a MaterialData


        try {
            if (block.getType() != Material.SKULL) {
                block.setType(Material.SKULL);
            }
            if (block.getData() != (byte) 0x1) {
                block.setData((byte) 0x1); // Floor
            }
            Skull skullE = ((Skull) block.getState());
            if (skullE.getRotation() != BlockFace.NORTH) {
                skullE.setRotation(BlockFace.NORTH);
                skullE.update();
            }

            current++;
            if ( current >= skulls.length)
            {
                current = 0;
            }
            CustomSkull.setSkull(block, skulls[current]);
        } catch (Exception e) {
            //block.setTypeIdAndData(getMaterialData().getItemTypeId(), getMaterialData().getData(), true);
        }


    }
    @SuppressWarnings("deprecation")
    protected void tick(Block b) {
        try {
            if (ChargableBlock.isChargable(b)) {
                BaseSTBMachine machine = LocationManager.getManager().get(b.getLocation().add(0, 1, 0), BaseSTBMachine.class);
                if (machine == null) return;
                if (ChargableBlock.getCharge(b) < getEnergyConsumption()) return;
                if (machine.getCharge() < getEnergyConsumption() * 1.1f) return;
                if (b.getLocation().getBlockY() != 0 || b.getLocation().getWorld().getEnvironment() != World.Environment.NORMAL) return;
                if (!processing.containsKey(b)) {
                    boolean proccess = false;
                    for (int slot : getInputSlots()) {

                        if (SlimefunManager.isItemSimiliar(BlockStorage.getInventory(b).getItemInSlot(slot), SlimefunItems.REACTOR_COOLANT_CELL, true)) {
                            MachineRecipe r = new MachineRecipe(4 / getSpeed(), new ItemStack[]{SlimefunItems.REACTOR_COOLANT_CELL.clone()}, new ItemStack[]{new ItemStack(Material.BEDROCK)});
                            startProcess(b, slot, r);
                            proccess = true;
                            break;
                        }


                    }
                    if (!proccess) return;
                }

                //payfor it
                ChargableBlock.addCharge(b, -getEnergyConsumption());
                machine.setCharge(machine.getCharge() - getEnergyConsumption() * 1.1f);

                //play effects
                b.getLocation().getWorld().playEffect(b.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);

                //update time
                Integer timeLeft = 1001;
                if (progress.containsKey(b)) {
                    timeLeft = progress.get(b);
                }
                timeLeft = timeLeft - 1;
                progress.put(b, timeLeft);
                //update gui
                ItemStack item = getProgressBar().clone();
                item.setDurability(MachineHelper.getDurability(item, timeLeft, 1001));
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(" ");
                List<String> lore = new ArrayList<String>();
                lore.add(MachineHelper.getProgress(timeLeft, 1001));
                lore.add("");
                im.setLore(lore);
                item.setItemMeta(im);
                BlockStorage.getInventory(b).replaceExistingItem(22, item);

                //work
                MachineRecipe WhatAmIDoing= processing.get(b);
                ItemStack adding = new ItemStack(Material.ICE, 1);
                if (SlimefunManager.isItemSimiliar(WhatAmIDoing.getInput()[0], SlimefunItems.REACTOR_COOLANT_CELL, true)) {
                    if (SlimefunStartup.chance(2000, 2)) adding = SFItems.VOID_PARTICLES.clone();
                    else if (SlimefunStartup.chance(3000, 3)) adding = SFItems.BEDROCK_DUST.clone();
                    else if (SlimefunStartup.chance(3000, 1) && b.getLocation().getWorld().getName().contains("world")) adding = SFItems.VOID_PARTICLES_POSITIVE.clone();
                    else if (SlimefunStartup.chance(3000, 1) && !b.getLocation().getWorld().getName().contains("world")) adding = SFItems.VOID_PARTICLES_NEGATIVE.clone();

                    adding = adding.clone();
                    adding.setAmount(1);
                }
                if (adding.getType() != Material.ICE) {
                    pushItems(b, new ItemStack[]{adding});
                }
                repaint(b);
                //Done
                if (timeLeft <= 0 )
                {
                    progress.remove(b);
                    processing.remove(b);
                    return;
                }
            }
        }
        catch (Exception e)
        {

        }
    }
    public void startProcess(Block b, int slot, MachineRecipe r)
    {
        BlockStorage.getInventory(b).replaceExistingItem(slot, InvUtils.decreaseItem(BlockStorage.getInventory(b).getItemInSlot(slot), 1));
        processing.put(b, r);
    }
    @Override
    public String getMachineIdentifier() {
        return "VOID_MINER";
    }

}
