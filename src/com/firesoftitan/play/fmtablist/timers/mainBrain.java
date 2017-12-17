package com.firesoftitan.play.fmtablist.timers;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.firesoftitan.play.fmtablist.FMTabList;
import com.firesoftitan.play.fmtablist.listeners.MainListener;
import me.badbones69.crazyenchantments.api.CEnchantments;
import me.badbones69.crazyenchantments.api.CrazyEnchantments;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import java.util.*;

/**
 * Created by Daniel on 12/20/2016.
 */
public class mainBrain implements Runnable {
    private MainListener tmp;
    public  mainBrain (MainListener Listme)
    {
        tmp = Listme;

    }

    @Override
    public void run() {
        try {

            bossWorld();

            CrateTime();

            EnvoyTime();

            VoteTime();

            TPAKiller();

            ReBirth();

            tabListUpdater();


        }
        catch (Exception e)
        {
            System.out.println("FMT: Error in Loop");
            e.printStackTrace();
        }
    }

    private void tabListUpdater() {

        tmp.Ticks++;
        for (Player p : Bukkit.getOnlinePlayers()) {
            tmp.setTabText(p);
        }
        if (tmp.Ticks >= 14)
        {
            tmp.Ticks = 0;
        }
    }

    private void bossWorld() {
        Random luck = new Random(System.currentTimeMillis());
        String worldtoSpawn = "bossworld";;
        for (Player p : Bukkit.getOnlinePlayers()) {;
            if (!p.hasPermission("fly.fly") && !p.hasPermission("FOT.fly") )
            {
                /*
                if (p.getInventory() != null)
                {
                    if (p.getInventory().getBoots() != null)
                    {
                        if (p.getInventory().getBoots().getItemMeta() != null)
                        {
                            if (p.getInventory().getBoots().getItemMeta().getLore() != null)
                            {
                                List<String> lore = p.getInventory().getBoots().getItemMeta().getLore();
                                for(int i = 0;  i < lore.size();i++)
                                {
                                    if (lore.get(i).startsWith(ChatColor.AQUA + "Titan.Wings"))
                                    {
                                        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(p.getUniqueId());
                                        playerData.
                                        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(p.getLocation(), false, playerData.lastClaim);

                                    }
                                }
                            }
                        }
                    }
                }*/
                p.setFlying(false);
                p.setAllowFlight(false);
            }
            else
            {
                if (p.hasPermission("FMTabList.email") && p.getAllowFlight() == false) {
                    p.setAllowFlight(true);
                }
            }
            if (p.hasPermission("FOT.fly"))
            {
                p.setAllowFlight(true);
            }

            FMTabList.checkItem(p, p.getInventory().getItemInMainHand());
            FMTabList.checkItem(p, p.getInventory().getHelmet());
            FMTabList.checkItem(p, p.getInventory().getChestplate());
            FMTabList.checkItem(p, p.getInventory().getLeggings());
            FMTabList.checkItem(p, p.getInventory().getBoots());
            FMTabList.checkItem(p, p.getInventory().getItemInOffHand());


            if (p.getInventory().getItemInMainHand() != null)
            {
                if (p.getInventory().getItemInMainHand().getItemMeta() != null)
                {
                    if (p.getInventory().getItemInMainHand().getType() == Material.SKULL_ITEM)
                    {
                        SkullMeta ISCS =  (SkullMeta)p.getInventory().getItemInMainHand().getItemMeta();
                        if (ISCS != null) {
                            if (ISCS.getOwner() != null) {
                                if (ISCS.getOwner().equalsIgnoreCase("cscorelib") && ISCS.hasDisplayName() == false) {
                                    String texture = CustomSkull.getTexture(p.getInventory().getItemInMainHand());
                                    SlimefunItem SFI = SlimefunItem.getByTexture(texture);
                                    if (SFI != null) {
                                        if (p.hasPermission("FMTabList.email")) {
                                            ItemStack toReplace = SFI.getItem().clone();
                                            toReplace.setAmount(p.getInventory().getItemInMainHand().getAmount());
                                            p.getInventory().setItemInMainHand(toReplace);
                                            p.sendMessage(ChatColor.LIGHT_PURPLE + "Your Slimefun block was fixed!");
                                        }
                                        //p.sendMessage(ChatColor.LIGHT_PURPLE + "Your Slimefun block was fixed!");
                                    }
                                }
                            }
                        }
                    }
                    if (p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName())
                    {
                        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().startsWith(ChatColor.AQUA + "" +ChatColor.BOLD + "Titan:"))
                        {
                            ItemStack tmpY =  p.getInventory().getItemInMainHand().clone();
                            ItemMeta tmpMet = tmpY.getItemMeta();
                            tmpMet.setDisplayName(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().replace(ChatColor.AQUA + "" +ChatColor.BOLD + "Titan:", ChatColor.AQUA + "" +ChatColor.BOLD + "Titan."));
                            tmpY.setItemMeta(tmpMet);
                            p.getInventory().setItemInMainHand(tmpY.clone());
                        }
                    }
                }
            }

            //if (!p.hasPermission("FMTabList.email"))
            {
                hackerCheck(p);
            }
/*
            Claim claim = GriefPrevention.instance.dataStore.getClaimAt(p.getLocation(), false, null);
            if (claim != null || FMTabList.plugin.protectedList.contains(p.getUniqueId())) {
                Collection<PotionEffect> tmpAPR = p.getActivePotionEffects();
                for (PotionEffect APR : tmpAPR) {
                    if (claim != null ) {
                        if (!claim.isAdminClaim() || FMTabList.plugin.protectedList.contains(p.getUniqueId())) {
                            if ((APR.getType().equals(PotionEffectType.DAMAGE_RESISTANCE) || APR.getType().equals(PotionEffectType.BLINDNESS) || APR.getType().equals(PotionEffectType.CONFUSION) || APR.getType().equals(PotionEffectType.HARM) || APR.getType().equals(PotionEffectType.POISON) || APR.getType().equals(PotionEffectType.SLOW) || APR.getType().equals(PotionEffectType.UNLUCK) || APR.getType().equals(PotionEffectType.WEAKNESS) || APR.getType().equals(PotionEffectType.WITHER))) {
                                p.removePotionEffect(APR.getType());

                            }
                            if (p.getFireTicks() > 0)
                            {
                                p.setFireTicks(0);
                            }
                        }
                    }
                    else if (FMTabList.plugin.protectedList.contains(p.getUniqueId())) {
                        if ((APR.getType().equals(PotionEffectType.DAMAGE_RESISTANCE) || APR.getType().equals(PotionEffectType.BLINDNESS) || APR.getType().equals(PotionEffectType.CONFUSION) || APR.getType().equals(PotionEffectType.HARM) || APR.getType().equals(PotionEffectType.POISON) || APR.getType().equals(PotionEffectType.SLOW) || APR.getType().equals(PotionEffectType.UNLUCK) || APR.getType().equals(PotionEffectType.WEAKNESS) || APR.getType().equals(PotionEffectType.WITHER))) {
                            p.removePotionEffect(APR.getType());;
                        }
                        if (p.getFireTicks() > 0)
                        {
                            p.setFireTicks(0);
                        }
                            p.setHealth(20);
                    }
                }
            }

*/



            for (int u = 0; u< tmp.tpaprocter.size(); u++)
                {
                    if (p.getUniqueId().equals(tmp.tpaprocter.get(u).usernameA) || p.getUniqueId().equals(tmp.tpaprocter.get(u).usernameB))
                    {
                        Long timeleft = System.currentTimeMillis() - tmp.tpaprocter.get(u).timetp;
                        timeleft = timeleft /1000;
                        timeleft = 30 - timeleft;
                        if (tmp.tpaprocter.get(u).protect == true) {
                            if (timeleft < 0) {
                                tmp.tpaprocter.remove(u);
                                return;
                            }
                            ActionBarAPI.sendActionBar(p, ChatColor.RED + "PVP disabled for " + ChatColor.WHITE + timeleft + ChatColor.RED + " more seconds.");
                        }

                    }
                }
        }
    }

    private void hackerCheck(Player p) {
       /* if (p.getGameMode() == GameMode.SURVIVAL) {
            if (p.getActivePotionEffects() != null) {
                Collection<PotionEffect> tmpAPR = p.getActivePotionEffects();
                for (PotionEffect APR : tmpAPR) {
                    if (APR.getAmplifier() > 12) {
                        punisPlayer(p,  "POSTION\n" + "postion: " + APR.getAmplifier() + "," + APR.getType().toString()  +"\n" + "player: " + p.getName());
                    }

                }
            }
        }*/
        if (p.isOp() == true && !p.getName().equals("freethemice") && !p.getName().equals("SanctifiedKnight"))
        {
            p.setOp(false);
            p.kickPlayer("You have hack the server and are banned!, This has been reported to mice!");
            Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(p.getName(), "You have hack the server and are banned!, This has been reported to mice!", null, "freethemice(auto)");
            FMTabList.plugin.sendMail("HACKER NOTICE-OPPED", p.getName() + "has opped him self!");
        }
        if (p.getInventory().getItemInMainHand() != null)
        {
            if (p.getInventory().getItemInMainHand().getItemMeta() != null)
            {
                if (p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName())
                {
                    if (!p.hasPermission("FMTabList.email")) {
                        //"ºrSplash Potion of º4ºlDEATH"
                        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.RESET + "Splash Potion of " + ChatColor.DARK_RED + ChatColor.BOLD + "DEATH")) {
                            punisPlayer(p, "DEATH\n" + "player: " + p.getName() + "\n" + "Item: " + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
                        }
                        //&5&dALTAR &3Probe - &e"
                        if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.DARK_PURPLE + "" + ChatColor.LIGHT_PURPLE + "ALTAR " + ChatColor.DARK_AQUA + "Probe - ")) {
                            punisPlayer(p, "ALTAR Probe\n" + "player: " + p.getName() + "\n" + "Item: " + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
                        }
                    }
                }

                if (p.getInventory().getItemInMainHand().getItemMeta().hasEnchants()) {
                    if (p.getInventory().getItemInMainHand().getItemMeta().getEnchants() != null) {
                        if (!p.hasPermission("FMTabList.email")) {
                            Map<Enchantment, Integer> tmpEnch = p.getInventory().getItemInMainHand().getItemMeta().getEnchants();
                            for (Enchantment key : tmpEnch.keySet()) {
                                if (tmpEnch.get(key) > 75) {
                                    punisPlayer(p, "Enchant\n" + "player: " + p.getName() + "\n" + "Item: " + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + "\n" + "Enchant: " + key + "," + tmpEnch.get(key));
                                    break;
                                }
                            }
                        }
                    }
                }
                ItemMeta metaFix = fixMaxEnchant(p.getInventory().getItemInMainHand());
                if (metaFix != null)
                {
                    p.getInventory().getItemInMainHand().setItemMeta(metaFix);
                    p.sendMessage(ChatColor.RED + "Your Titan Enchants have be reset to Max!");
                }
            }

        }
        if (p.getInventory().getItemInOffHand() != null)
        {
            if (p.getInventory().getItemInOffHand().getItemMeta() != null) {
                ItemMeta metaFix = fixMaxEnchant(p.getInventory().getItemInOffHand());
                if (metaFix != null)
                {
                    p.getInventory().getItemInOffHand().setItemMeta(metaFix);
                    p.sendMessage(ChatColor.RED + "Your Titan Enchants have be reset to Max!");
                }
                if (!p.hasPermission("FMTabList.email")) {
                    if (p.getInventory().getItemInOffHand().getItemMeta().hasDisplayName()) {
                        //"ºrSplash Potion of º4ºlDEATH"
                        if (p.getInventory().getItemInOffHand().getItemMeta().getDisplayName().contains(ChatColor.RESET + "Splash Potion of " + ChatColor.DARK_RED + ChatColor.BOLD + "DEATH")) {
                            punisPlayer(p, "DEATH\n" + "Item: " + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + "\n" + "player: " + p.getName());
                        }

                    }

                    if (p.getInventory().getItemInOffHand().getItemMeta().hasEnchants()) {
                        if (p.getInventory().getItemInOffHand().getItemMeta().getEnchants() != null) {
                            Map<Enchantment, Integer> tmpEnch = p.getInventory().getItemInOffHand().getItemMeta().getEnchants();
                            for (Enchantment key : tmpEnch.keySet()) {
                                if (tmpEnch.get(key) > 50) {
                                    punisPlayer(p, "Enchant\n" + "Item: " + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + "\n" + "Enchant: " + key + "," + tmpEnch.get(key) + "\n" + "player: " + p.getName());
                                    break;
                                }
                            }
                        }
                    }
                }
            }

        }
        ItemStack[] ArromrList = p.getInventory().getArmorContents();
        boolean fixed = false;
        for (int i = 0; i < ArromrList.length; i++)
        {
            ItemMeta metaFix = fixMaxEnchant(ArromrList[i]);
            if (metaFix != null)
            {
                ArromrList[i].setItemMeta(metaFix);
                fixed = true;
                p.sendMessage(ChatColor.RED + "Your Titan Enchants have be reset to Max!");
            }
        }
        if (fixed) {
            p.getInventory().setArmorContents(ArromrList);
        }
    }

    private ItemMeta fixMaxEnchant(ItemStack itemtobechecked) {
        if (itemtobechecked != null)
        {
            if (itemtobechecked.hasItemMeta())
            {
                if (itemtobechecked.getItemMeta().hasLore())
                {
                    List<String> lore = itemtobechecked.getItemMeta().getLore();
                    List<String> loreFix = new ArrayList<String>();
                    boolean fixed = false;
                    for (int i = 0; i < lore.size();i++) {
                        //System.out.print(i + "," + lore.get(i));
                        if (lore.get(i).startsWith(ChatColor.AQUA + "Titan."))
                        {
                            //System.out.print("in here");
                            String[] enchant = lore.get(i).replace(ChatColor.AQUA + "Titan.", "").split(" ");
                            if (enchant.length > 1)
                            {
                                try {
                                    int power = SlimefunStartup.instance.myTitanHooks.fromRoman(enchant[1]);

                                    CEnchantments cEncant = CEnchantments.valueOf(enchant[0].toUpperCase());
                                    int maxPower = CrazyEnchantments.getInstance().getMaxPower(cEncant);
                                    //System.out.print(cEncant.getCustomName() + "," + maxPower + "," + power);
                                    if (power > maxPower) {
                                        String newPower = SlimefunStartup.instance.myTitanHooks.toRoman(maxPower);
                                        loreFix.add(ChatColor.AQUA + "Titan." + enchant[0] + " " + newPower);
                                        fixed = true;
                                        continue;
                                    }
                                }
                                catch (Exception e)
                                {
                                    //System.out.print("ERROR");
                                }
                            }

                        }

                        loreFix.add(lore.get(i));
                    }
                    if (fixed)
                    {
                        ItemMeta newMeta = itemtobechecked.getItemMeta();
                        newMeta.setLore(loreFix);
                        return newMeta;
                    }
                }
            }
        }
        return null;
    }

    private void punisPlayer(Player p, String message) {
        p.setLevel(0);
        p.getInventory().clear();
        Collection<PotionEffect> tmpAPR = p.getActivePotionEffects();
        for(PotionEffect APR: tmpAPR)
        {
            p.removePotionEffect(APR.getType());
        }
        p.kickPlayer("You have been KICK for using a banned item from a hacked client!!! an email has been sent to server admin.");
        FMTabList.plugin.sendMail("HACKER NOTICE", message);
    }

    private void ReBirth()
    {
        UUID toRemove = null;
        for (UUID player: FMTabList.plugin.playerListSaves.keySet()) {
            Player Dieing=  Bukkit.getPlayer(player);
            if (Dieing != null) {
                if (Dieing.isDead() == false) {
                    PlayerInventory CheckInv = Dieing.getInventory();
                    ItemStack[] tmpSave = FMTabList.plugin.playerListSaves.get(Dieing.getUniqueId());
                    boolean firstStone = false;
                    for (int i = 0; i < CheckInv.getSize(); i++) {
                        if (FMTabList.plugin.checkforTitanStone(tmpSave[i]) && firstStone == false)
                        {
                            firstStone = true;
                            if (tmpSave[i].getAmount() > 1)
                            {
                                tmpSave[i].setAmount(tmpSave[i].getAmount() - 1);
                                CheckInv.setItem(i, tmpSave[i]);
                            }
                        }
                        else {
                            CheckInv.setItem(i, tmpSave[i]);
                        }
                    }
                    toRemove = Dieing.getUniqueId();
                    break;
                }
            }
        }
        if (toRemove != null)
        {
            FMTabList.plugin.playerListSaves.remove(toRemove);
        }


    }

    private void VoteTime() {
        if (System.currentTimeMillis() - FMTabList.plugin.vtime > 1000 *60 * 22)
        {
            FMTabList.plugin.saveConfigs();
            FMTabList.plugin.vtime = System.currentTimeMillis();
            if (FMTabList.plugin.voter.getProperty("votequestion") != null) {
                if (!((String)FMTabList.plugin.voter.getProperty("votequestion")).equalsIgnoreCase("off ")) {
                    Bukkit.broadcastMessage("" + ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "It's Vote Time !!!!");
                    Bukkit.broadcastMessage("" + ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Type /Yes or /No");
                    Bukkit.broadcastMessage(ChatColor.GOLD + "");
                    Bukkit.broadcastMessage("" + ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Question" + ChatColor.GOLD + "] " + ChatColor.GREEN + (String) FMTabList.plugin.voter.getProperty("votequestion"));
                    Bukkit.broadcastMessage("" + ChatColor.GOLD + "[" + ChatColor.DARK_RED + "Results" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD + "Yes: " + ChatColor.WHITE + (int) FMTabList.plugin.voter.getProperty("voteyes") + ChatColor.RED + ChatColor.BOLD + " No: " + ChatColor.WHITE + (int) FMTabList.plugin.voter.getProperty("voteno"));
                }
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lagg killmobs");
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lagg clear");
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lagg unloadchunks");
        }

    }
    private void EnvoyTime() {
        Random luck;



        long totalTime = 1000 * 60 * 60 * 1 + 1000 * 60 * 15;
        if (System.currentTimeMillis() - FMTabList.plugin.evtime > totalTime - 5 * 1000 && FMTabList.plugin.evcount == 3) {
            FMTabList.plugin.evcount++;
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + "Event in " + ChatColor.WHITE + "5 Seconds");
        }
        if (System.currentTimeMillis() - FMTabList.plugin.evtime > totalTime - 10 * 1000 && FMTabList.plugin.evcount == 2) {
            FMTabList.plugin.evcount++;
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + "Event in " + ChatColor.WHITE + "10 Seconds");
        }
        if (System.currentTimeMillis() - FMTabList.plugin.evtime > totalTime - 30 * 1000 && FMTabList.plugin.evcount == 1) {
            FMTabList.plugin.evcount++;
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + "Event in " + ChatColor.WHITE + "30 Seconds");

        }
        if (System.currentTimeMillis() - FMTabList.plugin.evtime > totalTime - 5 * 60 * 1000 && FMTabList.plugin.evcount == 0) {
            FMTabList.plugin.evcount++;
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + "Event in " + ChatColor.WHITE + "5 Minutes");

        }
        if (System.currentTimeMillis() - FMTabList.plugin.evtime > totalTime - 60 * 1000 && FMTabList.plugin.evcount > 0)
        {
            String chatRandom = "" + ChatColor.MAGIC + "You can't read";
            if (System.currentTimeMillis() - FMTabList.plugin.evtime > totalTime - 5 * 1000)
            {
                Random picker = new Random(System.currentTimeMillis());
                int whatone = picker.nextInt(FMTabList.eventList.getKeys().size());
                int i = 0;
                for (String key: FMTabList.eventList.getKeys())
                {
                    if (i >= whatone && !FMTabList.plugin.eventsRan.contains(key))
                    {
                        FMTabList.eventName = key; //picks event\
                        break;
                    }
                    i++;
                }
                chatRandom = FMTabList.eventName.replace("_", " ");
            }
            FMTabList.systemMessage(ChatColor.DARK_PURPLE + "Picking Event: " +    ChatColor.RED + chatRandom);
        }

        if (System.currentTimeMillis() - FMTabList.plugin.evtime > totalTime) {
            long time = 70 * 1000;
            if (FMTabList.eventName.replace("_", "").equalsIgnoreCase("treasure hunt"))
            {
                time = 3*time;
            }
            FMTabList.plugin.startEvent(FMTabList.eventName, time);
        }
        if (FMTabList.plugin.evtimedeath > 0)
        {
            FMTabList.systemMessage(ChatColor.DARK_BLUE + FMTabList.eventName.replace("_", " ") + ": " + ChatColor.RED + FMTabList.plugin.evcountdown);
            if (!FMTabList.eventName.replace("_", "").equalsIgnoreCase("envoy") && !FMTabList.eventName.replace("_", "").equalsIgnoreCase("treasure hunt")) {
                ItemStack droping = new ItemStack(Material.EXP_BOTTLE);
                Random numberR = new Random(System.currentTimeMillis());
                for (int i = 0; i < 55; i++) {
                    Random picker = new Random(System.currentTimeMillis());
                        int whatone = picker.nextInt(FMTabList.eventItems.size());
                        int j = 0;
                        for (ItemStack key : FMTabList.eventItems) {
                            if (j == whatone) {
                                droping = key.clone();
                                break;
                            }
                            j++;
                        }

                        World worldDrop = Bukkit.getWorld("worldmain");
                        if (worldDrop == null) {
                            worldDrop = Bukkit.getWorld("newhope2");
                        }
                        Location spawnLocation = new Location(worldDrop, (double) numberR.nextInt(200) - 100, (double) 50 + (i * 3), (double) numberR.nextInt(200) - 100);
                        worldDrop.dropItem(spawnLocation, droping);
                }
            }
            if (System.currentTimeMillis()  > FMTabList.plugin.evtimedeath)
            {

                Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + FMTabList.eventName.replace("_", "") + " Event is Over...");
                FMTabList.plugin.evtimedeath = 0;
                if (FMTabList.eventName.replace("_", " ").equalsIgnoreCase("treasure hunt")) {
                    Location placing = FMTabList.treasureplacementList.getLocation(FMTabList.treasurePlacementName);
                    placing.getBlock().setType(Material.AIR);
                }

                if (FMTabList.eventName.replace("_", "").equalsIgnoreCase("envoy")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "envoy stop");
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lagg clear");
            }
        }
    }


    private void CrateTime() {
        Random luck;
        if (System.currentTimeMillis() - FMTabList.plugin.lbtime > 1000 *60 *37 && FMTabList.plugin.lbtimedeath < 1)
        {
            List<String> keysAsArray = new ArrayList<String>(FMTabList.plugin.timerBlockList.keySet());
            Random r = new Random(System.currentTimeMillis());
            FMTabList.plugin.openCrateFor(keysAsArray.get(r.nextInt(keysAsArray.size())), 1000*120);

        }
        if (System.currentTimeMillis() > FMTabList.plugin.lbtimedeath && FMTabList.plugin.ranonce > 1)
        {
            FMTabList.plugin.ranonce--;
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " +ChatColor.GREEN + "Locking crate in " + FMTabList.plugin.ranonce);
            if (FMTabList.plugin.ranonce == 1) {
                FMTabList.plugin.lbtimedeath = 0;
                FMTabList.plugin.cratesHide(FMTabList.plugin.cratesName);
                Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.WHITE + ChatColor.BOLD  + FMTabList.plugin.cratesName + " Crate" + ChatColor.LIGHT_PURPLE + " has been locked, See you next time!");
                Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.DARK_PURPLE + ChatColor.BOLD  + "You can unlock anycrate, anytime, at " + ChatColor.WHITE + "http://www.firesoftitan.com");
                List<String> keysAsArray = new ArrayList<String>(FMTabList.plugin.timerBlockList.keySet());
                for(String key: FMTabList.plugin.timerBlockList.keySet())
                {
                    FMTabList.plugin.cratesHide(key);
                }
            }
        }
    }



    private void TPAKiller() {
        for (int u = 0; u< tmp.tpaprocter.size(); u++)
        {
            if (System.currentTimeMillis() - tmp.tpaprocter.get(u).timetp > 30*1000 && tmp.tpaprocter.get(u).protect == true)
            {
                tmp.tpaprocter.remove(u);
                break;
            }
            if (System.currentTimeMillis() - tmp.tpaprocter.get(u).timetp > 300*1000 && tmp.tpaprocter.get(u).protect == false)
            {
                tmp.tpaprocter.remove(u);
                break;
            }
        }
    }


}
