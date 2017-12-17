package com.firesoftitan.play.fmtablist.slimefun.items;

import com.firesoftitan.play.fmtablist.FMTabList;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 12/28/2016.
 */
public class SFItems {
    public static List<ItemStack> TitanBooksCE = new ArrayList<ItemStack>();
    public static ItemStack TitanStone;
    public static ItemStack TitanBookSoulbound;
    public static ItemStack TitanBookUnbreakable;
    public static ItemStack TitanBookUndroppable;
    public static ItemStack TitanBookAll;

    public static ItemStack LuckyNuggetB = new CustomItem(Material.GOLD_NUGGET, "&eLucky Nugget", new String[] { "DAMAGE_ALL-1"}, 0);
    public static ItemStack EclipseNuggetB = new CustomItem(Material.GHAST_TEAR, "&bEclipse Nugget", new String[] { "DAMAGE_ALL-3"}, 0);
    public static ItemStack TitanNuggetB = new CustomItem(Material.REDSTONE, "&4Titan Nugget", new String[] { "DAMAGE_ALL-5"}, 0);

    public static ItemStack LuckyNugget = new CustomItem(Material.GOLD_NUGGET, "&e&lLucky Nugget", new String[] { "DAMAGE_ALL-1"}, 0);
    public static ItemStack EclipseNugget = new CustomItem(Material.GHAST_TEAR, "&b&lEclipse Nugget", new String[] { "DAMAGE_ALL-3"}, 0);
    public static ItemStack TitanNugget = new CustomItem(Material.REDSTONE, "&4&lTitan Nugget", new String[] { "DAMAGE_ALL-5"}, 0);

    public static ItemStack LuckyIngot = new CustomItem(Material.GOLD_INGOT, "&e&lLucky Ingot", new String[] { "DAMAGE_ALL-2"}, 0);
    public static ItemStack EclipseIngot = new CustomItem(Material.IRON_INGOT, "&b&lEclipse Ingot", new String[] { "DAMAGE_ALL-6"}, 0);
    public static ItemStack TitanIngot = new CustomItem(Material.CLAY_BRICK, "&4&lTitan Ingot", new String[] { "DAMAGE_ALL-10"}, 0);

    public static ItemStack LuckySword = new CustomItem(Material.GOLD_SWORD, "&e&lLucky Sword", new String[] { "DAMAGE_ALL-10", "LOOT_BONUS_MOBS-10", "FIRE_ASPECT-5", "DURABILITY-10" }, 0);
    public static ItemStack LuckyPickaxe = new CustomItem(Material.GOLD_PICKAXE, "&e&lLucky Pickaxe", new String[] { "DIG_SPEED-10", "LOOT_BONUS_BLOCKS-10", "DURABILITY-10" }, 0);
    public static ItemStack LuckyAxe = new CustomItem(Material.GOLD_AXE, "&e&lLucky Axe", new String[] { "DAMAGE_ALL-10", "DIG_SPEED-10", "LOOT_BONUS_BLOCKS-10", "DURABILITY-10" }, 0);
    public static ItemStack LuckyHelmet = new CustomItem(Material.DIAMOND_HELMET, "&e&lLucky Helmet", new String[] { "PROTECTION_ENVIRONMENTAL-10", "PROTECTION_PROJECTILE-10", "PROTECTION_EXPLOSIONS-10", "THORNS-10", "DURABILITY-10" }, 0);
    public static ItemStack LuckyChestplate = new CustomItem(Material.DIAMOND_CHESTPLATE, "&e&lLucky Chestplate", new String[] { "PROTECTION_ENVIRONMENTAL-10", "PROTECTION_PROJECTILE-10", "PROTECTION_EXPLOSIONS-10", "THORNS-10", "DURABILITY-10" }, 0);
    public static ItemStack LuckyLeggings = new CustomItem(Material.DIAMOND_LEGGINGS, "&e&lLucky Leggings", new String[] { "PROTECTION_ENVIRONMENTAL-10", "PROTECTION_PROJECTILE-10", "PROTECTION_EXPLOSIONS-10", "THORNS-10", "DURABILITY-10" }, 0);
    public static ItemStack LuckyBoots = new CustomItem(Material.DIAMOND_BOOTS, "&e&lLucky Boots", new String[] { "PROTECTION_ENVIRONMENTAL-10", "PROTECTION_PROJECTILE-10", "PROTECTION_EXPLOSIONS-10", "THORNS-10", "DURABILITY-10" }, 0);
    public static ItemStack LuckyBlock;
    public static ItemStack ZeroLuckyBlock;
    public static ItemStack UnLuckyBlock;
    public static ItemStack PandorasBox;


    public static ItemStack EclipseSword = new CustomItem(Material.IRON_SWORD, "&b&lEclipse Sword", new String[] { "DAMAGE_ALL-13", "LOOT_BONUS_MOBS-13", "FIRE_ASPECT-7", "DURABILITY-13" }, 0);
    public static ItemStack EclipsePickaxe = new CustomItem(Material.IRON_PICKAXE, "&b&lEclipse Pickaxe", new String[] { "DIG_SPEED-13", "LOOT_BONUS_BLOCKS-13", "DURABILITY-13" }, 0);
    public static ItemStack EclipseAxe = new CustomItem(Material.IRON_AXE, "&b&lEclipse Axe", new String[] { "DAMAGE_ALL-13", "DIG_SPEED-13", "LOOT_BONUS_BLOCKS-13", "DURABILITY-13" }, 0);
    public static ItemStack EclipseHelmet = new CustomItem(Material.DIAMOND_HELMET, "&b&lEclipse Helmet", new String[] { "PROTECTION_ENVIRONMENTAL-13", "PROTECTION_PROJECTILE-13", "PROTECTION_EXPLOSIONS-13", "THORNS-13", "DURABILITY-13" }, 0);
    public static ItemStack EclipseChestplate = new CustomItem(Material.DIAMOND_CHESTPLATE, "&b&lEclipse Chestplate", new String[] { "PROTECTION_ENVIRONMENTAL-13", "PROTECTION_PROJECTILE-13", "PROTECTION_EXPLOSIONS-13", "THORNS-13", "DURABILITY-13" }, 0);
    public static ItemStack EclipseLeggings = new CustomItem(Material.DIAMOND_LEGGINGS, "&b&lEclipse Leggings", new String[] { "PROTECTION_ENVIRONMENTAL-13", "PROTECTION_PROJECTILE-13", "PROTECTION_EXPLOSIONS-13", "THORNS-13", "DURABILITY-13" }, 0);
    public static ItemStack EclipseBoots = new CustomItem(Material.DIAMOND_BOOTS, "&b&lEclipse Boots", new String[] { "PROTECTION_ENVIRONMENTAL-13", "PROTECTION_PROJECTILE-13", "PROTECTION_EXPLOSIONS-13", "THORNS-13", "DURABILITY-13" }, 0);

    public static ItemStack TitanSword = new CustomItem(Material.DIAMOND_SWORD, "&4&lTitan Sword", new String[] { "DAMAGE_ALL-23", "LOOT_BONUS_MOBS-23", "FIRE_ASPECT-23", "DURABILITY-23" }, 0);
    public static ItemStack TitanPickaxe = new CustomItem(Material.DIAMOND_PICKAXE, "&4&lTitan Pickaxe", new String[] { "DIG_SPEED-23", "LOOT_BONUS_BLOCKS-23", "DURABILITY-23" }, 0);
    public static ItemStack TitanAxe = new CustomItem(Material.DIAMOND_AXE, "&4&lTitan Axe", new String[] { "DAMAGE_ALL-23", "DIG_SPEED-23", "LOOT_BONUS_BLOCKS-23", "DURABILITY-23" }, 0);
    public static ItemStack TitanHelmet = new CustomItem(Material.DIAMOND_HELMET, "&4&lTitan Helmet", new String[] { "PROTECTION_ENVIRONMENTAL-23", "PROTECTION_PROJECTILE-23", "PROTECTION_EXPLOSIONS-23", "THORNS-23", "DURABILITY-23" }, 0);
    public static ItemStack TitanChestplate = new CustomItem(Material.DIAMOND_CHESTPLATE, "&4&lTitan Chestplate", new String[] { "PROTECTION_ENVIRONMENTAL-23", "PROTECTION_PROJECTILE-23", "PROTECTION_EXPLOSIONS-23", "THORNS-23", "DURABILITY-23" }, 0);
    public static ItemStack TitanLeggings = new CustomItem(Material.DIAMOND_LEGGINGS, "&4&lTitan Leggings", new String[] { "PROTECTION_ENVIRONMENTAL-23", "PROTECTION_PROJECTILE-23", "PROTECTION_EXPLOSIONS-23", "THORNS-23", "DURABILITY-23" }, 0);
    public static ItemStack TitanBoots = new CustomItem(Material.DIAMOND_BOOTS, "&4&lTitan Boots", new String[] { "PROTECTION_ENVIRONMENTAL-23", "PROTECTION_PROJECTILE-23", "PROTECTION_EXPLOSIONS-23", "THORNS-23", "DURABILITY-23" }, 0);

    public static ItemStack ANCIENT_ALTAR_CRAFTER_BLOCK = FMTabList.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWZkMDE2NzY4OTcxNWRmMWFhNTA1NWE2M2VhNmI4YmE2NTZlMmI0YjgxZmNjYWI1M2MzZTIxMDhkODBiODFjIn19fQ==");
    public static ItemStack AUTOMATED_VANILLA_CRAFTING_CHAMBER = new CustomItem(new MaterialData(Material.WORKBENCH), "&eAutomated Vanilla Crafting Chamber", "", "&6Advanced Machine", "&8\u21E8 &e\u26A1 &710 J/Item");
    public static ItemStack ANCIENT_ALTAR_CRAFTER = new CustomItem(ANCIENT_ALTAR_CRAFTER_BLOCK, "&6Ancient Altar Crafter", "", "&6Advanced Machine", "&8\u21E8 &e\u26A1 &750 J/Item");
    public static ItemStack AUTOMATED_ANCIENT_ALTAR_CRAFTER = new CustomItem(new MaterialData(Material.WORKBENCH), "&6Automated Ancient Altar Crafter", "", "&6Advanced Machine", "&8\u21E8 &e\u26A1 &750 J/Item");
    public static ItemStack THERMAL_GENERATOR;
    public static ItemStack RAREORE_GENERATOR;
    public static ItemStack SF4_TO_STB_CONVERTER;
    public static ItemStack SF4_TO_STB_CONVERTER_2;
    public static ItemStack ELECTRIC_COBBLE_TO_DUST;
    public static ItemStack ELECTRIC_COBBLE_TO_INGOT;
    public static ItemStack ELECTRIC_COBBLE_TO_DUST_2;
    public static ItemStack ELECTRIC_COBBLE_TO_INGOT_2;
    public static ItemStack ELECTRIC_COBBLE_TO_DUST_3;
    public static ItemStack ELECTRIC_COBBLE_TO_INGOT_3;
    public static ItemStack ELECTRIC_LUCKY_BLOCK_FACTORY;
    public static ItemStack ELECTRIC_LUCKY_BLOCK_GRINDER;
    public static ItemStack CHARCOAL_FACTORY;
    public static ItemStack CHARCOAL_FACTORY_2;
    public static ItemStack CHARCOAL_FACTORY_3;
    public static ItemStack TITAN_AUTO_DISENCHANTER;
    public static ItemStack ECLIPSE_COIL;
    public static ItemStack TITAN_MOTOR;
    public static ItemStack NUGGETTOINGOT;
    public static ItemStack INGOTUP;
    public static ItemStack BEDROCKDRILL;
    public static ItemStack VOIDMINNER;
    public static ItemStack BEDROCK_DRILL;
    public static ItemStack LASER_CHARGE;
    public static ItemStack BEDROCK_DRILL_OLD;
    public static ItemStack LASER_CHARGE_OLD;
    public static ItemStack BEDROCK_DUST = new CustomItem(new ItemStack(Material.SULPHUR, 1), "&8Bedrock Dust");
    public static ItemStack VOID_PARTICLES = new CustomItem(new ItemStack(Material.SULPHUR, 1), "&8Void Particles");
    public static ItemStack VOID_PARTICLES_POSITIVE = new CustomItem(new ItemStack(Material.REDSTONE, 1), "&8Void Particles");
    public static ItemStack VOID_PARTICLES_NEGATIVE = new CustomItem(new ItemStack(Material.SUGAR, 1), "&8Void Particles");
    public static ItemStack SHOP_KEY = new CustomItem(new ItemStack(Material.TRIPWIRE_HOOK, 1), "&2Shop Key", "&7-With (/shop create # $ $) command", "&7-and holding item to sell/buy in main hand", "&7-Click Chest at spawn with key in inventory", "&7-this will rent it for 30 days.", "&7-or Click chest with key in your hand to", "&7-add 30 days to your time!");
    public static ItemStack TALISMAN_VOID = new CustomItem(Material.EMERALD, "&4Titan Talisman of the Void", 0, new String[] {"", "&rEach Talisman can send", "&ritems to a linked bsu/hsu", "&rfrom your inventory", "&rBSU/HSU can't be empty and unlocked. ", "&bTitan.Linked: [Not Linked]"});
    public static ItemStack WARPKEY = new CustomItem(new ItemStack(Material.TRIPWIRE_HOOK, 1), "&dWarp Key", "&7Will set a warp at your location!", "&Rename key to the name of the new warp.", "&7Left Click: Set warp");

    static {
        try {
            ECLIPSE_COIL = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2UzYmM0ODkzYmE0MWEzZjczZWUyODE3NGNkZjRmZWY2YjE0NWU0MWZlNmM4MmNiN2JlOGQ4ZTk3NzFhNSJ9fX0="), "&dEclipse Coil");
            TITAN_MOTOR = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGNiY2EwMTJmNjdlNTRkZTlhZWU3MmZmNDI0ZTA1NmMyYWU1OGRlNWVhY2M5NDlhYjJiY2Q5NjgzY2VjIn19fQ=="), "&dTitan Motor");
            THERMAL_GENERATOR = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTM0M2NlNThkYTU0Yzc5OTI0YTJjOTMzMWNmYzQxN2ZlOGNjYmJlYTliZTQ1YTdhYzg1ODYwYTZjNzMwIn19fQ=="), "&cThermal Generator", "", "&4End-Game Generator", "&8\u21E8 &e\u26A1 &78192 J Buffer", "&8\u21E8 &e\u26A1 &7500 J/s", "&bRequires:","&63x3 of lava below", "&63x3 of Air above", "&4Could Exploded!");
            RAREORE_GENERATOR = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjUzNDU5ZDYxNDM5YTNlZTNiZTgyMmU0ZmVjMWE1YzE0N2U4NzQyNjJkY2JhNjUzMmFlMjYxMzRkNmNiZDVmIn19fQ=="), "&cRare Ore Generator", "", "&4End-Game Generator", "&8\u21E8 &e\u26A1 &7256 J Buffer", "&8\u21E8 &e\u26A1 &732 J/s");
            SF4_TO_STB_CONVERTER = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTM0M2NlNThkYTU0Yzc5OTI0YTJjOTMzMWNmYzQxN2ZlOGNjYmJlYTliZTQ1YTdhYzg1ODYwYTZjNzMwIn19fQ=="), "&cSlimefun to STB Converter", "", "&4End-Game Generator", "&8\u21E8 &e\u26A1 &7100 J/s to", "&8\u21E8 &e\u26A1 &7110 SCU/s", "&bRequires:","&6STB Generator(Engine)", "&bPlaced above, with no fule (not runing)");
            SF4_TO_STB_CONVERTER_2 = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTM0M2NlNThkYTU0Yzc5OTI0YTJjOTMzMWNmYzQxN2ZlOGNjYmJlYTliZTQ1YTdhYzg1ODYwYTZjNzMwIn19fQ=="), "&cSlimefun to STB Converter", "", "&4End-Game Generator II", "&8\u21E8 &e\u26A1 &71000 J/s to", "&8\u21E8 &e\u26A1 &71100 SCU/s", "&bRequires:","&6STB Generator(Engine)", "&bPlaced above, with no fule (not runing)");
            ZeroLuckyBlock = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNiNzEwYjA4YjUyM2JiYTdlZmJhMDdjNjI5YmEwODk1YWQ2MTEyNmQyNmM4NmJlYjM4NDU2MDNhOTc0MjZjIn19fQ=="), "&rLucky Block", new String[]{"&7Luck: &r0"});
            LuckyBlock = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNiNzEwYjA4YjUyM2JiYTdlZmJhMDdjNjI5YmEwODk1YWQ2MTEyNmQyNmM4NmJlYjM4NDU2MDNhOTc0MjZjIn19fQ=="), "&rVery lucky Block", new String[]{"&7Luck: &a+80"});
            UnLuckyBlock = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjNiNzEwYjA4YjUyM2JiYTdlZmJhMDdjNjI5YmEwODk1YWQ2MTEyNmQyNmM4NmJlYjM4NDU2MDNhOTc0MjZjIn19fQ=="), "&rVery unlucky Block", new String[]{"&7Luck: &c-80"});
            PandorasBox = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZjN2RkZTUxMjg3MWJkNjA3Yjc3ZTY2MzVhZDM5ZjQ0ZjJkNWI0NzI5ZTYwMjczZjFiMTRmYmE5YTg2YSJ9fX0="), "&5Pandora\'s Box", new String[]{"&7Luck: &c&oERROR"});
            ELECTRIC_COBBLE_TO_DUST = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTU0OTQ3ZGU3ZjUyNTk4MjU1ZDZhZmVlOWQ3N2JlZmFkOWI0ZjI0YzBjNDY2M2QyOGJjZGY4YTY0NTdmMzQifX19"), "&3Electric Cobble to Dust", "", "&4End-Game Machine", "&6Has a small chance of Lucky and Eclipse Nuggets", "&8\u21E8 &7Speed: 2x", "&8\u21E8 &e\u26A1 &720 J/s");
            ELECTRIC_COBBLE_TO_INGOT = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkYmNiN2UxZmJlY2VhOWE3MzUwNDM2Y2JiZWEyYjQ5NmY3NGMyOTcyMDRmMWJiOWFjYzM4NzhkNTQyY2NiIn19fQ=="), "&3Electric Cobble to Ingot", "", "&4End-Game Machine", "&6Has a small chance of Lucky and Eclipse Ingots", "&8\u21E8 &7Speed: 2x", "&8\u21E8 &e\u26A1 &730 J/s");
            ELECTRIC_COBBLE_TO_DUST_2 = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTU0OTQ3ZGU3ZjUyNTk4MjU1ZDZhZmVlOWQ3N2JlZmFkOWI0ZjI0YzBjNDY2M2QyOGJjZGY4YTY0NTdmMzQifX19"), "&3Electric Cobble to Dust &7(&eII&7)", "", "&4End-Game Machine", "&6Has a small chance of Lucky and Eclipse Nuggets", "Gives 3 dust instead of 1", "&8\u21E8 &7Speed: 10x", "&8\u21E8 &e\u26A1 &720 J/s");
            ELECTRIC_COBBLE_TO_INGOT_2 = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkYmNiN2UxZmJlY2VhOWE3MzUwNDM2Y2JiZWEyYjQ5NmY3NGMyOTcyMDRmMWJiOWFjYzM4NzhkNTQyY2NiIn19fQ=="), "&3Electric Cobble to Ingot &7(&eII&7)", "", "&4End-Game Machine", "&6Has a small chance of Lucky and Eclipse Ingots", "Gives 3 Ingots instead of 1", "&8\u21E8 &7Speed: 10x", "&8\u21E8 &e\u26A1 &730 J/s");
            ELECTRIC_COBBLE_TO_DUST_3 = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTU0OTQ3ZGU3ZjUyNTk4MjU1ZDZhZmVlOWQ3N2JlZmFkOWI0ZjI0YzBjNDY2M2QyOGJjZGY4YTY0NTdmMzQifX19"), "&3Electric Cobble to Dust &7(&eIII&7)", "", "&4End-Game Machine", "&6Has a small chance of Lucky and Eclipse Nuggets", "Gives 5 dust instead of 1", "&8\u21E8 &7Speed: 20x", "&8\u21E8 &e\u26A1 &720 J/s");
            ELECTRIC_COBBLE_TO_INGOT_3 = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRkYmNiN2UxZmJlY2VhOWE3MzUwNDM2Y2JiZWEyYjQ5NmY3NGMyOTcyMDRmMWJiOWFjYzM4NzhkNTQyY2NiIn19fQ=="), "&3Electric Cobble to Ingot &7(&eIII&7)", "", "&4End-Game Machine", "&6Has a small chance of Lucky and Eclipse Ingots", "Gives 5 Ingots instead of 1", "&8\u21E8 &7Speed: 20x", "&8\u21E8 &e\u26A1 &730 J/s");
            TITAN_AUTO_DISENCHANTER = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjJmNzkwMTZjYWQ4NGQxYWUyMTYwOWM0ODEzNzgyNTk4ZTM4Nzk2MWJlMTNjMTU2ODI3NTJmMTI2ZGNlN2EifX19"), "&3Titan Auto Disenchanter", "", "&4End-Game Machine", "&6Rename book to Titan Enchanment Name", "&6Will remove that enchanment.","&8\u21E8 &7Speed: 1x", "&8\u21E8 &e\u26A1 &730 J/s");
            ELECTRIC_LUCKY_BLOCK_FACTORY = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI1ZDhiOWEzYTk0MjFkY2VkYjE3ZDcxZTNhODg0ZDk1ZWM1MDM4YzgzOGNlMTllZDZkOGU5NmM1YjIzZWQ3In19fQ=="), "&3Electric Lucky Block Factory", "", "&4End-Game Machine", "&6Will take any Gold Ingot", "&8\u21E8 &7Speed: 1x", "&8\u21E8 &e\u26A1 &725 J/s");
            ELECTRIC_LUCKY_BLOCK_GRINDER = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFkNTQyNGQ5OTAzOTUzODQzNTI2YTdjNDE2ODY2ZTdkNzk1MDFjODhjZTdjZGFiZWVlNTI4NGVhMzlmIn19fQ=="), "&3Electric Lucky Block Grinder", "", "&4End-Game Machine", "&6Will almost anything Lucky or Not", "&8\u21E8 &7Speed: 1x", "&8\u21E8 &e\u26A1 &730 J/s");
            CHARCOAL_FACTORY = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWE3ZDJhN2ZiYjRkMzdiNGQ1M2ZlODc3NTcxMjhlNWVmNjZlYzIzZDdmZjRmZTk5NDQ1NDZkYmM4Y2U3NzcifX19"), "&3Charcoal Factory", "", "&4End-Game Machine", "&6Needs: Bukkit of water", "&6Needs: Bukkit of lava", "&6Needs: Sapling", "&6Needs: Bone Meal", "&8\u21E8 &7Speed: 1x", "&8\u21E8 &e\u26A1 &730 J/s");
            CHARCOAL_FACTORY_2 = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWE3ZDJhN2ZiYjRkMzdiNGQ1M2ZlODc3NTcxMjhlNWVmNjZlYzIzZDdmZjRmZTk5NDQ1NDZkYmM4Y2U3NzcifX19"), "&3Charcoal Factory II", "", "&4End-Game Machine", "&6Needs: Bukkit of water", "&6Needs: Bukkit of lava", "&6Needs: Sapling", "&6Needs: Bone Meal", "&8\u21E8 &7Speed: 10x", "&8\u21E8 &e\u26A1 &730 J/s");
            CHARCOAL_FACTORY_3 = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWE3ZDJhN2ZiYjRkMzdiNGQ1M2ZlODc3NTcxMjhlNWVmNjZlYzIzZDdmZjRmZTk5NDQ1NDZkYmM4Y2U3NzcifX19"), "&3Charcoal Factory III", "", "&4End-Game Machine", "&6Needs: Bukkit of water", "&6Needs: Bukkit of lava", "&6Needs: Sapling", "&6Needs: Bone Meal", "&8\u21E8 &7Speed: 20x", "&8\u21E8 &e\u26A1 &730 J/s");
            NUGGETTOINGOT = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2RmZmQ5NWM4ZmNkYmFlZDllOWU5YmU0N2VhMWE0MWVlMTIyOWQ4OWI1YzZkNDJhMWY3YjJmN2YxMWJkMTEyMiJ9fX0="), "&3Nugget to Ingot", "", "&4End-Game Machine", "&6Works with Lucky, Eclipse, Titan", "&8\u21E8 &7Speed: 1x", "&8\u21E8 &e\u26A1 &730 J/s");
            INGOTUP = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDViZTRiYTgyODYxZWZmY2Q4ZGNjNWMyNDY5YjllZThkNGUwMjFiMDYwZGFhZjM0ODMxOWUwMjAxMTgyOCJ9fX0="), "&3Ingot Up", "", "&4End-Game Machine", "&6Works with Lucky, Eclipse", "&8\u21E8 &7Speed: 1x", "&8\u21E8 &e\u26A1 &730 J/s");
            BEDROCKDRILL = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNiOGEzMjM1NmQxYWQ5OWUzMTc5OWRiOGQ2OWQ1NTg4ZTlhZmY5MjkwYTA0NGNhOWIyMzU5Yjc1MjNlYTM5YyJ9fX0="), "&bBedrock Drill", "", "&4End-Game Machine", "&6Requires Slimefun & STB power!", "&6Place on top of bedrock", "&6Place STB machine on top, to draw power from", "&6Uses: &cDrill Head, &cLaser Charge, &bReactor Coolant Cell", "&8\u21E8 &7Speed: 1x", "&8\u21E8 &e\u26A1 &7400 J/s", "&8\u21E8 &e\u26A1 &7440 SCU/s");
            VOIDMINNER = new CustomItem(CustomSkull.getItem("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjY5ZDM0ODI3ZDkwYTM1ZDY3YjcxOGRiYzJlNDI5YmQ5OTI0MTRiMTk3MTU1MTc4MTVhZDg2MzBiNDI0ZTlmMiJ9fX0="), "&bVoid Miner", "", "&4End-Game Machine", "&6Requires Slimefun & STB power!", "&6Place at y = 0, in overworld.", "&6Place STB machine on top, to draw power from", "&6Uses: &bReactor Coolant Cell", "&8\u21E8 &7Speed: 1x", "&8\u21E8 &e\u26A1 &7700 J/s", "&8\u21E8 &e\u26A1 &7770 SCU/s");
            BEDROCK_DRILL_OLD = new CustomItem(CustomSkull.getItem("eyJ0aW1lc3RhbXAiOjE0NjMzNDA3NTkyNDksInByb2ZpbGVJZCI6ImQ2MmI1MjJkMTVjZjQyNWE4NTFlNmNjNDRkOGJlMDg5IiwicHJvZmlsZU5hbWUiOiJKb2huMDAwNzA4IiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQ5MjFmODhiMzViZjFlNTMxZWE0ZDUzYmU4M2ExYmE1ZGNhODgzNzQzZDE2ZDliMWVmYzY5NDQ2YjYyODk0ZDQifX19"), "&cDrill Head", new String[] { "&fA special drill which is used in a &4Bedrock Breaker", "&fto break bedrock into dust.", "", "&7Durability: 1024/1024" });
            LASER_CHARGE_OLD = new CustomItem(CustomSkull.getItem("eyJ0aW1lc3RhbXAiOjE0NjM1ODgyNjYwMTgsInByb2ZpbGVJZCI6ImQ2MmI1MjJkMTVjZjQyNWE4NTFlNmNjNDRkOGJlMDg5IiwicHJvZmlsZU5hbWUiOiJKb2huMDAwNzA4IiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzI3OWY1N2M2OGI3YTkwZTBkODg1MGU3OTRhZGU1YjhlODEwZDMzOGUyNDU5ZWVlZjliYWJkNjgzMmNhMTY5YSJ9fX0="), "&cLaser Charge", new String[] { "&fThis item is necessary for a", "&4Deep Depth Miner &fto mine Ores.", "", "&7Durability: 1024/1024" });
            BEDROCK_DRILL = new CustomItem(CustomSkull.getItem("eyJ0aW1lc3RhbXAiOjE0NjMzNDA3NTkyNDksInByb2ZpbGVJZCI6ImQ2MmI1MjJkMTVjZjQyNWE4NTFlNmNjNDRkOGJlMDg5IiwicHJvZmlsZU5hbWUiOiJKb2huMDAwNzA4IiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQ5MjFmODhiMzViZjFlNTMxZWE0ZDUzYmU4M2ExYmE1ZGNhODgzNzQzZDE2ZDliMWVmYzY5NDQ2YjYyODk0ZDQifX19"), "&cDrill Head", new String[] { "&fA special drill which is used in a &bBedrock Drill", "&fto break bedrock into dust.", "", "&7Durability: 1024/1024" });
            LASER_CHARGE = new CustomItem(CustomSkull.getItem("eyJ0aW1lc3RhbXAiOjE0NjM1ODgyNjYwMTgsInByb2ZpbGVJZCI6ImQ2MmI1MjJkMTVjZjQyNWE4NTFlNmNjNDRkOGJlMDg5IiwicHJvZmlsZU5hbWUiOiJKb2huMDAwNzA4IiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzI3OWY1N2M2OGI3YTkwZTBkODg1MGU3OTRhZGU1YjhlODEwZDMzOGUyNDU5ZWVlZjliYWJkNjgzMmNhMTY5YSJ9fX0="), "&cLaser Charge", new String[] { "&fThis item is necessary for a", "&bBedrock Drill &fto mine Ores.", "", "&7Durability: 1024/1024" });

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
    public static ItemStack getTitanEnchant(String Name)
    {
        if (Name == null) return null;
        Name = Name.toLowerCase().replace("titan.", "");
        if (Name.equals("")) return null;
        for(ItemStack TE: TitanBooksCE)
        {
            if (ChatColor.stripColor(TE.getItemMeta().getLore().get(0)).toLowerCase().startsWith(ChatColor.stripColor(Name).toLowerCase())) {
                return TE.clone();
            }
        }
        return null;
    }
}
