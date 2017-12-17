package com.firesoftitan.play.fmtablist.slimefun.items;

import com.firesoftitan.play.fmtablist.slimefun.CustomCategories;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.sensibletoolbox.SensibleToolboxPlugin;
import me.mrCookieSlime.sensibletoolbox.api.items.BaseSTBMachine;
import me.mrCookieSlime.sensibletoolbox.blocks.machines.BigStorageUnit;
import me.mrCookieSlime.sensibletoolbox.core.storage.LocationManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.List;

public class TitanTalisman extends SlimefunItem {

    boolean consumed;
    boolean cancel;
    PotionEffect[] effects;
    String suffix;
    int chance;

    public TitanTalisman(ItemStack item, String name, ItemStack[] recipe, boolean consumable, boolean cancelEvent, String messageSuffix, PotionEffect... effects) {
        super(CustomCategories.SLIMEFUN_TITAN_TALISMAN, item, name, RecipeType.MAGIC_WORKBENCH, recipe, new CustomItem(item, consumable ? 4: 1));
        this.consumed = consumable;
        this.cancel = cancelEvent;
        this.suffix = messageSuffix;
        this.effects = effects;
        this.chance = 100;
    }

    public TitanTalisman(ItemStack item, String name, ItemStack[] recipe, boolean consumable, boolean cancelEvent, String messageSuffix, int chance, PotionEffect... effects) {
        super(CustomCategories.SLIMEFUN_TITAN_TALISMAN, item, name, RecipeType.MAGIC_WORKBENCH, recipe, new CustomItem(item, consumable ? 4: 1));
        this.consumed = consumable;
        this.cancel = cancelEvent;
        this.suffix = messageSuffix;
        this.effects = effects;
        this.chance = chance;
    }

    public TitanTalisman(ItemStack item, String name, ItemStack[] recipe, String messageSuffix, int chance, PotionEffect... effects) {
        super(CustomCategories.SLIMEFUN_TITAN_TALISMAN, item, name, RecipeType.MAGIC_WORKBENCH, recipe, item);
        this.consumed = true;
        this.cancel = true;
        this.suffix = messageSuffix;
        this.effects = effects;
        this.chance = chance;
    }

    public PotionEffect[] getEffects()	{		return this.effects;	}
    public boolean isConsumable()	 	{		return this.consumed;	}
    public boolean isEventCancelled() 	{		return this.cancel;		}
    public String getSuffix() 			{		return this.suffix;		}
    public int getChance()				{		return this.chance;		}

    public static HashMap<Integer,ItemStack>  checkFor(Player p, SlimefunItem talisman) {
        if (talisman != null) {
            if (talisman instanceof TitanTalisman) {
                boolean message = ((TitanTalisman) talisman).getSuffix().equalsIgnoreCase("") ? false: true;
                if (SlimefunStartup.chance(100, ((TitanTalisman) talisman).getChance())) {

                    boolean pass = true;

                    for (PotionEffect effect: ((TitanTalisman) talisman).getEffects()) {
                        if (effect != null) if (p.hasPotionEffect(effect.getType())) pass = false;
                    }

                    if (pass) {
                        HashMap<Integer,ItemStack> tmpY = new HashMap<Integer, ItemStack>();

                        for (int i = 0; i < p.getInventory().getSize(); i++)
                        {
                            if (SensibleToolboxPlugin.isItemSimiliar(p.getInventory().getItem(i), talisman.getItem()))
                            {
                                if (Slimefun.hasUnlocked(p, talisman.getItem(), true)) {
                                    tmpY.put(i, p.getInventory().getItem(i));
                                }
                            }
                        }
                        if (tmpY.size() > 0) {
                            return (HashMap<Integer,ItemStack>)tmpY.clone();
                        }
                        else return null;
                    }
                    else return null;
                }
                else return null;
            }
            else return null;
        }
        else return null;
    }
    public static ItemStack setLinkedLocation(ItemStack TitanTalisman, Location linked)
    {
        BaseSTBMachine machine = LocationManager.getManager().get(linked, BaseSTBMachine.class);
        if (machine == null) return null;
        if (SensibleToolboxPlugin.isItemSimiliar(TitanTalisman, SFItems.TALISMAN_VOID) && machine instanceof BigStorageUnit) {
            ItemMeta handIM = TitanTalisman.getItemMeta();
            List<String> handIML = handIM.getLore();
            int toReplace = -1;
            for (int i = 0; i < handIML.size(); i++) {
                if (handIML.get(i).startsWith(ChatColor.AQUA + "Titan.Linked: ")) {
                    toReplace = i;
                    break;
                }
            }
            if (toReplace > -1) {
                handIML.remove(toReplace);
                handIML.add(toReplace, ChatColor.AQUA + "Titan.Linked: [" + linked.getWorld().getName() + "," + linked.getBlockX() + "," + +linked.getBlockY() + "," + linked.getBlockZ() + "]");
                handIM.setLore(handIML);
                TitanTalisman.setItemMeta(handIM);
                return TitanTalisman.clone();
            }
        }
        return null;
    }
    public static BigStorageUnit getLinkedBSU(ItemStack TitanTalisman)
    {
        Location linked = getLinkedLocation(TitanTalisman);
        if (linked != null) {
            BaseSTBMachine machine = LocationManager.getManager().get(linked, BaseSTBMachine.class);
            if (machine == null) return null;
            if (machine instanceof BigStorageUnit) {
                return (BigStorageUnit) machine;
            }
        }
        return null;
    }
    public static Location getLinkedLocation(ItemStack TitanTalisman) {
        if (SensibleToolboxPlugin.isItemSimiliar(TitanTalisman, SFItems.TALISMAN_VOID)) {
            ItemMeta handIM = TitanTalisman.getItemMeta();
            List<String> handIML = handIM.getLore();
            int toReplace = -1;
            for (int i = 0; i < handIML.size(); i++) {
                if (handIML.get(i).startsWith(ChatColor.AQUA + "Titan.Linked: ")) {
                    String location = handIML.get(i);
                    location = location.replace(ChatColor.AQUA + "Titan.Linked: ", "");
                    location = location.replace("[", "");
                    location = location.replace("]", "");
                    String[] splitL = location.split(",");
                    try {
                        Location goingOut = new Location(Bukkit.getWorld(splitL[0]), Double.parseDouble(splitL[1]), Double.parseDouble(splitL[2]), Double.parseDouble(splitL[3]));
                        return goingOut;
                    } catch (Exception e) {
                        return null;
                    }
                }
            }

        }
        return null;
    }
}
