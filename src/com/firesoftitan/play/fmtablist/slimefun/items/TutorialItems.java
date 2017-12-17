package com.firesoftitan.play.fmtablist.slimefun.items;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Daniel on 4/22/2017.
 */
public class TutorialItems {
    public static ItemStack BEDROCK_DRILL;
    public static ItemStack LASER_CHARGE;
    static {
        try {
            BEDROCK_DRILL = new CustomItem(CustomSkull.getItem("eyJ0aW1lc3RhbXAiOjE0NjMzNDA3NTkyNDksInByb2ZpbGVJZCI6ImQ2MmI1MjJkMTVjZjQyNWE4NTFlNmNjNDRkOGJlMDg5IiwicHJvZmlsZU5hbWUiOiJKb2huMDAwNzA4IiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzQ5MjFmODhiMzViZjFlNTMxZWE0ZDUzYmU4M2ExYmE1ZGNhODgzNzQzZDE2ZDliMWVmYzY5NDQ2YjYyODk0ZDQifX19"), "&cDrill Head", new String[] { "&fA special drill which is used in a &bBedrock Drill", "&fto break bedrock into dust.", "&dThis drill head may give you 1 dust per block", "&dand will destroy the bedrock"});
            LASER_CHARGE = new CustomItem(CustomSkull.getItem("eyJ0aW1lc3RhbXAiOjE0NjM1ODgyNjYwMTgsInByb2ZpbGVJZCI6ImQ2MmI1MjJkMTVjZjQyNWE4NTFlNmNjNDRkOGJlMDg5IiwicHJvZmlsZU5hbWUiOiJKb2huMDAwNzA4IiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzI3OWY1N2M2OGI3YTkwZTBkODg1MGU3OTRhZGU1YjhlODEwZDMzOGUyNDU5ZWVlZjliYWJkNjgzMmNhMTY5YSJ9fX0="), "&cLaser Charge", new String[] { "&fThis item is necessary for a", "&bBedrock Drill &fto mine Ores.", "&dThis drill head will give you ores", "&dand will over heat the bedrock", "&dYou must use &bReactor Coolant", "&dCell &dto turn it back to bedrock."});


        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
