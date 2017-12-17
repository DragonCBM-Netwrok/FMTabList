package com.firesoftitan.play.fmtablist.slimefun;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.MenuItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.Lists.Categories;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.LockedCategory;
import me.mrCookieSlime.Slimefun.Objects.SeasonCategory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Daniel on 12/28/2016.
 */
public class CustomCategories
{
    public static final Category SLIMEFUN_TITAN = new Category(new CustomItem(new ItemStack(Material.ENCHANTED_BOOK), "&5Titan Books", new String[] { "", "&a >Click to open" }), 5);
    public static final Category SLIMEFUN_RESOURCES = new Category(new CustomItem(new ItemStack(Material.GOLD_INGOT), "&5Resources", new String[] { "", "&a >Click to open" }), 5);
    public static final Category SLIMEFUN_PARTS = new Category(new CustomItem(new ItemStack(Material.ANVIL), "&5Parts", new String[] { "", "&a >Click to open" }), 5);
    public static final SeasonCategory SLIMEFUN_BLANK = new SeasonCategory(18, 0, new MenuItem(Material.NETHER_STAR, "TRASH", 0, ChatColor.translateAlternateColorCodes('&', "&chelp &aSanta")));
    public static final Category SLIMEFUN_LUCKY = new Category(new CustomItem(new ItemStack(Material.ENCHANTMENT_TABLE), "&5Lucky Gear", new String[] { "", "&a >Click to open" }), 5);
    public static final Category SLIMEFUN_ECLIPSE = new Category(new CustomItem(new ItemStack(Material.IRON_HELMET), "&bEclipse Gear", new String[] { "", "&a >Click to open" }), 5);
    public static final Category SLIMEFUN_TITAN_GEAR = new Category(new CustomItem(new ItemStack(Material.DIAMOND_CHESTPLATE), "&4Titan Gear", new String[] { "", "&a >Click to open" }), 5);
    public static final Category SLIMEFUN_TITAN_TALISMAN = new Category(new CustomItem(new ItemStack(Material.EMERALD), "&4Titan Talisman", new String[] { "", "&a >Click to open" }), 5);
    public static Category ELECTRICITY = null;

    static {
        try {
            ELECTRICITY = new LockedCategory(new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTU4NDQzMmFmNmYzODIxNjcxMjAyNThkMWVlZThjODdjNmU3NWQ5ZTQ3OWU3YjBkNGM3YjZhZDQ4Y2ZlZWYifX19"), "&bEnergy and Electricity", "", "&a> Click to open"), 5, Categories.MACHINES_1);
        } catch (Exception e) {

        }
    }
}
