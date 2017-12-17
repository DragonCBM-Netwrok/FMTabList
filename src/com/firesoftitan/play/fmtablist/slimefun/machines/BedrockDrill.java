package com.firesoftitan.play.fmtablist.slimefun.machines;

import com.firesoftitan.play.fmtablist.slimefun.items.*;
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
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class BedrockDrill extends AContainer {

    private String[] skulls = new String[4];
    private int current = 0;
    public BedrockDrill(Category category, ItemStack item, String name, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, name, recipeType, recipe);
        try {
            skulls[0] = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNiOGEzMjM1NmQxYWQ5OWUzMTc5OWRiOGQ2OWQ1NTg4ZTlhZmY5MjkwYTA0NGNhOWIyMzU5Yjc1MjNlYTM5YyJ9fX0=";
            skulls[1] = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQxOTI2M2Y5ZWJjOTMxN2RjNWFiYjg3OWNkNTk3MzhkNzZlMmEyM2RjMWFjYmNiOTRlOWRjMzYyZmZjNGIifX19";
            skulls[2] = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGUxMmU0NmY0ZGVkZDI3YmVjNDM3YmVjNWU4OTE0YThlZmYzMTlhNmY1NjI5NDkwNjU0ZThiMzRlOTc5OTQifX19";
            skulls[3] = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWVhZDRlOGFlOGViNDU0ZGJkOTg1YmI1OWM0YzEzNzU5NDVhYWZkMjhhNGVjY2Y3MWU4ZDU3ZmM5NzhlZjUifX19";


        }catch (Exception e)
        {

        }

    }

    @Override
    public String getInventoryTitle() {
        return "&bBedrock Drill";
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
                Block bedRock = b.getLocation().add(0, -1, 0).getBlock();
                BaseSTBMachine machine = LocationManager.getManager().get(b.getLocation().add(0, 1, 0), BaseSTBMachine.class);
                if (machine == null) return;
                if (ChargableBlock.getCharge(b) < getEnergyConsumption()) return;
                if (machine.getCharge() < getEnergyConsumption() * 1.1f) return;
                if ((bedRock.getType() != Material.BEDROCK) && bedRock.getType() != Material.MAGMA) return;
                if (!processing.containsKey(b)) {
                    boolean proccess = false;
                    for (int slot : getInputSlots()) {
                        if (bedRock.getType() == Material.BEDROCK) {
                            if (SlimefunManager.isItemSimiliar(BlockStorage.getInventory(b).getItemInSlot(slot), SFItems.BEDROCK_DRILL, true)) {
                                MachineRecipe r = new MachineRecipe(4 / getSpeed(), new ItemStack[]{SFItems.BEDROCK_DRILL.clone()}, new ItemStack[]{new ItemStack(Material.COBBLESTONE)});
                                startProcess(b, slot, r);
                                proccess = true;
                                break;
                            }
                            if (SlimefunManager.isItemSimiliar(BlockStorage.getInventory(b).getItemInSlot(slot), SFItems.BEDROCK_DRILL_OLD, true)) {
                                MachineRecipe r = new MachineRecipe(4 / getSpeed(), new ItemStack[]{SFItems.BEDROCK_DRILL_OLD.clone()}, new ItemStack[]{new ItemStack(Material.COBBLESTONE)});
                                startProcess(b, slot, r);
                                proccess = true;
                                break;
                            }
                            if (SlimefunManager.isItemSimiliar(BlockStorage.getInventory(b).getItemInSlot(slot), SFItems.LASER_CHARGE, true)) {
                                MachineRecipe r = new MachineRecipe(4 / getSpeed(), new ItemStack[]{SFItems.LASER_CHARGE.clone()}, new ItemStack[]{new ItemStack(Material.MAGMA)});
                                startProcess(b, slot, r);
                                proccess = true;
                                break;
                            }
                            if (SlimefunManager.isItemSimiliar(BlockStorage.getInventory(b).getItemInSlot(slot), SFItems.LASER_CHARGE_OLD, true)) {
                                MachineRecipe r = new MachineRecipe(4 / getSpeed(), new ItemStack[]{SFItems.LASER_CHARGE_OLD.clone()}, new ItemStack[]{new ItemStack(Material.MAGMA)});
                                startProcess(b, slot, r);
                                proccess = true;
                                break;
                            }
                        }
                        if (bedRock.getType() == Material.MAGMA) {
                            if (SlimefunManager.isItemSimiliar(BlockStorage.getInventory(b).getItemInSlot(slot), SlimefunItems.REACTOR_COOLANT_CELL, true)) {
                                MachineRecipe r = new MachineRecipe(4 / getSpeed(), new ItemStack[]{SlimefunItems.REACTOR_COOLANT_CELL.clone()}, new ItemStack[]{new ItemStack(Material.BEDROCK)});
                                startProcess(b, slot, r);
                                proccess = true;
                                break;
                            }
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
                if (progress.containsKey(bedRock)) {
                    timeLeft = progress.get(bedRock);
                }
                timeLeft = timeLeft - 1;
                progress.put(bedRock, timeLeft);
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
                if (SlimefunManager.isItemSimiliar(WhatAmIDoing.getInput()[0], SlimefunItems.REACTOR_COOLANT_CELL, true))
                {

                }
                if (SlimefunManager.isItemSimiliar(WhatAmIDoing.getInput()[0], SFItems.BEDROCK_DRILL, true) || SlimefunManager.isItemSimiliar(WhatAmIDoing.getInput()[0], SFItems.BEDROCK_DRILL_OLD, true)) {
                    adding = new ItemStack(Material.COBBLESTONE, 1);
                    if (SlimefunStartup.chance(1000, 1)) adding = SFItems.BEDROCK_DUST.clone();
                }

                if (SlimefunManager.isItemSimiliar(WhatAmIDoing.getInput()[0], SFItems.LASER_CHARGE, true) || SlimefunManager.isItemSimiliar(WhatAmIDoing.getInput()[0], SFItems.LASER_CHARGE_OLD, true)) {
                    adding = new ItemStack(Material.REDSTONE, 1);
                    if (SlimefunStartup.chance(100, 25)) adding = new ItemStack(Material.INK_SACK, 1, (short) 4);
                    else if (SlimefunStartup.chance(100, 25)) adding = new ItemStack(Material.COBBLESTONE, 1);
                    else if (SlimefunStartup.chance(100, 25)) adding = new ItemStack(Material.STONE, 1);
                    else if (SlimefunStartup.chance(100, 25)) adding = new ItemStack(Material.DIAMOND, 1);
                    else if (SlimefunStartup.chance(100, 25)) adding = new ItemStack(Material.EMERALD, 1);
                    else if (SlimefunStartup.chance(100, 25)) adding = new ItemStack(Material.CLAY_BALL, 1);
                    else if (SlimefunStartup.chance(100, 25)) adding = new ItemStack(Material.DIRT, 1);
                    else if (SlimefunStartup.chance(100, 25)) adding = new ItemStack(Material.STONE, 1, (short) 1);
                    else if (SlimefunStartup.chance(100, 25)) adding = new ItemStack(Material.STONE, 1, (short) 3);
                    else if (SlimefunStartup.chance(100, 25)) adding = new ItemStack(Material.STONE, 1, (short) 5);
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
                    bedRock.setType(processing.get(b).getOutput()[0].getType());
                    progress.remove(bedRock);
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
        return "BEDROCK_DRILL";
    }

}
