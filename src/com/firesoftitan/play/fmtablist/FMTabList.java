package com.firesoftitan.play.fmtablist;


import com.firesoftitan.play.fmtablist.listeners.MainListener;
import com.firesoftitan.play.fmtablist.mystuff.CustomConfiguration;
import com.firesoftitan.play.fmtablist.mystuff.InventoryGUIUtil;
import com.firesoftitan.play.fmtablist.slimefun.CustomCategories;
import com.firesoftitan.play.fmtablist.slimefun.CustomRecipeType;
import com.firesoftitan.play.fmtablist.slimefun.items.SFItems;
import com.firesoftitan.play.fmtablist.slimefun.items.TitanTalisman;
import com.firesoftitan.play.fmtablist.slimefun.machines.*;
import com.firesoftitan.play.fmtablist.timers.mainBrain;
import com.mojang.authlib.GameProfile;
import me.badbones69.crazyenchantments.api.CEnchantments;
import me.badbones69.crazyenchantments.api.CrazyEnchantments;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.World.CustomSkull;
import me.mrCookieSlime.Slimefun.Lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AGenerator;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import me.mrCookieSlime.Slimefun.api.energy.EnergyTicker;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class FMTabList extends JavaPlugin {

    public static Config timerBlock = new Config("data-storage" + File.separator + "FiresOfTitan" + File.separator  + "TimerBlockList.yml");
    public static Config TitanBooks = new Config("data-storage" + File.separator + "FiresOfTitan" + File.separator  + "TitanBooks.yml");
    public static Config altsList = new Config("data-storage" + File.separator + "FiresOfTitan" + File.separator  + "alts.yml");
    public static Config partyWarpsList = new Config("data-storage" + File.separator + "FiresOfTitan" + File.separator  + "parties.yml");
    public static Config bountyList = new Config("data-storage" + File.separator + "FiresOfTitan" + File.separator  + "bounty.yml");
    public static Config killrecord = new Config("data-storage" + File.separator + "FiresOfTitan" + File.separator  + "killrecord.yml");
    public static Config eventList = new Config("data-storage" + File.separator + "FiresOfTitan" + File.separator  + "eventlist.yml");
    public static Config treasureList = new Config("data-storage" + File.separator + "FiresOfTitan" + File.separator  + "treasurelist.yml");
    public static Config treasureplacementList = new Config("data-storage" + File.separator + "FiresOfTitan" + File.separator  + "treasureplacement.yml");
    public static Config moveList = new Config("data-storage" + File.separator + "FiresOfTitan" + File.separator  + "movelist.yml");
    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;
    public static  String eventName;
    public static  String treasureName;
    public static  String treasurePlacementName;
    public static List<ItemStack> eventItems;
    public static FMTabList plugin;
    Logger log;
    public String evcountdown = "0:0";
    public long evtime = 0;
    public long evtimedeath = 0;
    public long evcount = 0;
    public long lbtime = 0;
    public long lbtimedeath = 0;
    public long vtime = 0;
    public int ranonce = 0;
    public String cratesName = "";
    public ArrayList<String> mobList = new ArrayList<String>();
    public HashMap<UUID, ItemStack[]> playerListSaves = new HashMap<UUID, ItemStack[]>();
    public ArrayList<UUID> protectedList = new ArrayList<UUID>();
    public CustomConfiguration bosskeys;
    public File bosskeysFile;
    public CustomConfiguration voter;
    public File voterFile;
    public static boolean WhosFirst = false;
    public static Map<String, ItemStack> recipesV = new HashMap<String, ItemStack>();
    public long votecounter = 0;
    public MainListener mainListener;
    public HashMap<String, HashMap<String, Location>> partyWarps = new HashMap<String, HashMap<String, Location>>();
    public HashMap<String, List<String>> iplist = new HashMap<String, List<String>>();
    public HashMap<String, Integer> killCountList = new HashMap<String, Integer>();
    public HashMap<String, Location> timerBlockList = new HashMap<String, Location>();
    public HashMap<String, Material> timerBlockType = new HashMap<String, Material>();
    public HashMap<String, Long> priceCountList = new HashMap<String, Long>();
    public InventoryGUIUtil backupGui;
    public List<String> eventsRan = new ArrayList<String>();
    private static final NavigableMap<Long, String> suffixes = new TreeMap<> ();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
    // Dependencies Variables
    public void onDisable()
    {
        try {
            SlimefunStartup.instance.myTitanHooks.titanClose();
        }
        catch (Exception e)
        {

        }
        finally {
            saveConfigs();
            if (getIP().startsWith("209.126.104.")) {
                this.sendMail("Shutdown", "Server is Shutting down...");
            }
        }
    }
    public void saveConfigs()
    {
        try {
            FMTabList.bountyList.save();
            FMTabList.killrecord.save();
            FMTabList.eventList.save();
            FMTabList.treasureList.save();
            FMTabList.treasureplacementList.save();
            FMTabList.timerBlock.save();
            FMTabList.altsList.save();
            FMTabList.TitanBooks.save();
            FMTabList.partyWarpsList.save();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Somthing went wrong during config save!!!!!");
        }
    }
    public void onEnable(){
        plugin = this;

        if (getIP().startsWith("209.126.104.")) {
            this.sendMail("Startup", "Server is Starting up...");
        }
        this.log = getLogger();
        backupGui = new InventoryGUIUtil("Backups");

        bosskeysFile  = new File( plugin.getDataFolder()+File.separator+"BossKeys_Config.yml") ;
        bosskeys = new CustomConfiguration(this,bosskeysFile);
        bosskeys.load();

        voterFile = new File( plugin.getDataFolder()+File.separator+"_Config.yml") ;
        voter = new CustomConfiguration(this,voterFile);
        voter.load();
        mobList.add("sheep");
        mobList.add("zombie");
        mobList.add("zombie");
        mobList.add("zombie");
        mobList.add("skeleton");
        mobList.add("skeleton");
        mobList.add("skeleton");
        mobList.add("witch");
        mobList.add("witch");
        mobList.add("witch");
        mobList.add("blaze");
        mobList.add("blaze");
        mobList.add("blaze");
        mobList.add("ghast");
        mobList.add("ghast");
        mobList.add("ghast");
        mobList.add("endermite");
        mobList.add("slime");
        mobList.add("slime");
        mobList.add("slime");

        mainListener =  new MainListener(this);
        mainListener.registerEvents();

        SFItems.TitanStone = makeTitanStone();
        SFItems.TitanBookAll = makeTitanBook(0);
        SFItems.TitanBookSoulbound = makeTitanBook(1);
        SFItems.TitanBookUnbreakable = makeTitanBook(2);
        SFItems.TitanBookUndroppable = makeTitanBook(3);

        List<CEnchantments> tmpAllEnchs = CrazyEnchantments.getInstance().getEnchantments();

        List<SlimefunItem> tmpResour = SlimefunItem.list();

        List<SlimefunItem> tmpResourPicky = new ArrayList<SlimefunItem>();
        if (TitanBooks.getKeys().size() > 0) {
            for (String key : TitanBooks.getKeys()) {

                CEnchantments thisCE = CrazyEnchantments.getInstance().getFromName(key);
                if (thisCE != null) {
                    SlimefunItem ingert = SlimefunItem.getByName(TitanBooks.getString(key));
                    ItemStack type = makeTitanBookCE(thisCE);
                    SFItems.TitanBooksCE.add(type);
                    new SlimefunItem(CustomCategories.SLIMEFUN_TITAN, type, "TitanBook" + thisCE.getName(), RecipeType.ANCIENT_ALTAR, new ItemStack[]{SFItems.TitanStone, ingert.getItem(), SFItems.TitanStone, SlimefunItems.ESSENCE_OF_AFTERLIFE, new ItemStack(Material.BOOK), SlimefunItems.ESSENCE_OF_AFTERLIFE, SFItems.TitanStone, ingert.getItem(), SFItems.TitanStone}).register();
                }
            }
        }else {
            for (int i = 0; i < tmpResour.size(); i++) {
                if (tmpResour.get(i).getCategory() == Categories.RESOURCES) {
                    tmpResourPicky.add(tmpResour.get(i));
                    //TitanBooks.setValue("SFITEM_" + tmpResourPicky.size(), tmpResour.get(i).getName());
                }
            }
            for (int i = 0; i < tmpResour.size(); i++) {
                if (tmpResour.get(i).getCategory() == Categories.MISC) {
                    tmpResourPicky.add(tmpResour.get(i));
                    //TitanBooks.setValue("SFITEM_" + tmpResourPicky.size(), tmpResour.get(i).getName());
                }
            }
            for (int i = 0; i < tmpAllEnchs.size(); i++) {
                SlimefunItem ingert = tmpResourPicky.get(i);
                CEnchantments thisCE = tmpAllEnchs.get(i);
                ItemStack type = makeTitanBookCE(thisCE);
                SFItems.TitanBooksCE.add(type);
                new SlimefunItem(CustomCategories.SLIMEFUN_TITAN, type, "TitanBook" + thisCE.getName(), RecipeType.ANCIENT_ALTAR, new ItemStack[]{SFItems.TitanStone, ingert.getItem(), SFItems.TitanStone, SlimefunItems.ESSENCE_OF_AFTERLIFE, new ItemStack(Material.BOOK), SlimefunItems.ESSENCE_OF_AFTERLIFE, SFItems.TitanStone, ingert.getItem(), SFItems.TitanStone}).register();
                TitanBooks.setValue(tmpAllEnchs.get(i).getName(), ingert.getName());
            }
            TitanBooks.save();
        }
        this.iplist.clear();
        if (altsList.getKeys().size() > 0) {
            for (String key : altsList.getKeys()) {
                this.iplist.put(key.replace("%", "."), altsList.getStringList(key));
            }
        }
        this.timerBlockList.clear();
        if (timerBlock.getKeys().size() > 0) {
            for (String key : timerBlock.getKeys()) {
                if (!key.endsWith(".mat")) {
                    Location loc = timerBlock.getLocation(key);
                    Material mat = Material.getMaterial(timerBlock.getString(key + ".mat"));
                    this.timerBlockList.put(key, loc);
                    this.timerBlockType.put(key, mat);
                    this.cratesHide(key);
                }
            }
        }

        this.partyWarps.clear();
        if (partyWarpsList.getKeys().size() > 0) {
            for (String key : partyWarpsList.getKeys()) {
                String[] partyIndex = key.split("%");
                Location loc = partyWarpsList.getLocation(key);
                if (!this.partyWarps.containsKey(partyIndex[0])) {
                    this.partyWarps.put(partyIndex[0], new HashMap<String, Location>());
                }
                HashMap<String, Location> warps =  this.partyWarps.get(partyIndex[0]);
                warps.put(partyIndex[1], loc);
                this.partyWarps.put(partyIndex[0], warps);
            }
        }
        this.killCountList.clear();
        if (bountyList.getKeys().size() > 0) {
            for (String key : bountyList.getKeys()) {
                if (key.endsWith("--price"))
                {
                    this.priceCountList.put(key, bountyList.getLong(key));
                }
                else {
                    this.killCountList.put(key, bountyList.getInt(key));
                }

            }
        }

        evtime = System.currentTimeMillis() - 1000 * 60 * 60 * 1 - 1000 * 60 * 15 + 6 * 60 * 1000;
        lbtime = System.currentTimeMillis();
        vtime = System.currentTimeMillis();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new mainBrain(mainListener),25, 25);
///give freethemice 214 1 0 {display:{Name:"&fTitan Stone"},ench:[{id:34,lvl:10}]}
        registerItems();

        setupVanillaCraft();
    }
    public void openCrateFor(String CrateName, long OpenTime) {
        FMTabList.plugin.lbtime = System.currentTimeMillis();
        if (FMTabList.plugin.lbtimedeath  < 1) {
            FMTabList.plugin.lbtimedeath = System.currentTimeMillis() + OpenTime;//1000 *30;
        }

        FMTabList.plugin.ranonce = 6;
        FMTabList.plugin.cratesName = CrateName; //keysAsArray.get(r.nextInt(keysAsArray.size()));
        FMTabList.plugin.cratesShow(FMTabList.plugin.cratesName);
        Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + "It's Crate Time !!!!");
        Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.WHITE + ChatColor.BOLD  + FMTabList.plugin.cratesName + ChatColor.LIGHT_PURPLE + " Crate" +  " has been unlocked, Hurry!");
        Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.WHITE + ChatColor.BOLD  + "/warp crates");
        Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + "It's Crate Time !!!!");
    }
    public void startEvent(String eventN, long time) {
        FMTabList.eventName = eventN;
        FMTabList.plugin.evtime = System.currentTimeMillis();
        FMTabList.plugin.evtimedeath = System.currentTimeMillis() + time;
        FMTabList.plugin.evcount = 0;
        List<ItemStack> copyme = new ArrayList<ItemStack>();
        for(String subkey: FMTabList.eventList.getKeys(FMTabList.eventName))
        {
            if (FMTabList.eventList.getItem(FMTabList.eventName + "." + subkey) != null)
            {
                copyme.add(FMTabList.eventList.getItem(FMTabList.eventName + "." + subkey).clone());
            }
            else {
                System.out.println("--------------------------------------------------");
                System.out.println("Something is wrong: FMTablist line 316");
                System.out.println("Event: " + FMTabList.eventName );
                System.out.println("Item: " + subkey );
                System.out.println("--------------------------------------------------");
            }
        }
        FMTabList.eventItems = copyme;
        Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + FMTabList.eventName.replace("_", "") + " Event is NOW!!!!");
        FMTabList.systemMessage(ChatColor.DARK_BLUE + "Event: " +    ChatColor.RED + FMTabList.eventName.replace("_", " "));
        FMTabList.plugin.eventsRan.add(FMTabList.eventName);
        if (FMTabList.eventName.replace("_", " ").equalsIgnoreCase("envoy")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "envoy start");
        }
        if (FMTabList.eventName.replace("_", " ").equalsIgnoreCase("treasure hunt")) {
            Random picker = new Random(System.currentTimeMillis());
            int whatone = picker.nextInt(FMTabList.treasureList.getKeys().size());
            int i = 0;
            for (String key: FMTabList.treasureList.getKeys())
            {
                if (i >= whatone)
                {
                    FMTabList.treasureName = key;
                    break;
                }
                i++;
            }
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.RED + ChatColor.BOLD  + FMTabList.eventName.replace("_", "") + " Treasure is: " + ChatColor.WHITE + FMTabList.treasureName);
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.GRAY + "Go to " + ChatColor.WHITE + "/warp treasure" + ChatColor.RED + " or " + ChatColor.WHITE + "/warp hunt");
            Bukkit.broadcastMessage("" + ChatColor.GOLD  + "[" + ChatColor.DARK_RED + "Broadcast" + ChatColor.GOLD + "] " + ChatColor.GRAY + "First one to find the Orange Shulker Box, wins");
            picker = new Random(System.currentTimeMillis());
            whatone = picker.nextInt(FMTabList.treasureplacementList.getKeys().size());
            i = 0;
            for (String key: FMTabList.treasureplacementList.getKeys())
            {
                if (i >= whatone)
                {
                    FMTabList.treasurePlacementName = key;
                    break;
                }
                i++;
            }
            Location placing = FMTabList.treasureplacementList.getLocation(FMTabList.treasurePlacementName);
            placing.getBlock().setType(Material.ORANGE_SHULKER_BOX);

        }
    }
    public String ipadressAltChecker(String ipAddress, Player p) {
        return ipadressAltChecker(ipAddress, p.getUniqueId(), p.getName());
    }
    public String ipadressAltChecker(String ipAddress, UUID p, String pName) {
        try {
            List<String> tmpIPList = new ArrayList<String>();
            if (FMTabList.plugin.iplist.containsKey(ipAddress)) {
                tmpIPList = FMTabList.plugin.iplist.get(ipAddress);
            } else {
                String oldIp = FMTabList.plugin.getLastIp(p);
                if (!oldIp.equals("")) {
                    tmpIPList = FMTabList.plugin.iplist.get(oldIp);
                    FMTabList.plugin.iplist.put(ipAddress, tmpIPList);
                    FMTabList.plugin.iplist.remove(oldIp);
                    FMTabList.altsList.setValue(oldIp.replace(".", "%"), null);
                    FMTabList.altsList.setValue(ipAddress.replace(".", "%"), tmpIPList);
                }
            }
            if (!tmpIPList.contains(p.toString())) {
                tmpIPList.add(p.toString());
                FMTabList.plugin.iplist.put(ipAddress, tmpIPList);
                FMTabList.altsList.setValue(ipAddress.replace(".", "%"), tmpIPList);
            }

            String AltList = "";
            for (String playerUUID : tmpIPList) {
                OfflinePlayer oflinePlayer =  Bukkit.getOfflinePlayer(UUID.fromString(playerUUID));
                String altsName =  oflinePlayer.getName();
                if (oflinePlayer == null)
                {
                    String nameslist[] = FMTabList.plugin.lookupUsername(playerUUID);
                    altsName = "*" + nameslist[0];
                }
                AltList = AltList + ", " + altsName;
            }
            AltList = AltList.substring(2, AltList.length());
            return AltList;
        }
        catch (Exception e)
        {

        }
        return pName;
    }
    public Location getPartyWarp(String partyName, String warpName)
    {
        if (this.partyWarps.containsKey(partyName))
        {
            HashMap<String, Location> warpsList = this.partyWarps.get(partyName);
            if (warpsList.containsKey(warpName)) {
                Location warpLocation = warpsList.get(warpName);
                return warpLocation;
            }
        }
        return null;
    }
    public void setPartyWarps(String partyName, String warpName, Location loc)
    {
        HashMap<String, Location> warpsList = new HashMap<String, Location>();
        if (this.partyWarps.containsKey(partyName))
        {
            warpsList = this.partyWarps.get(partyName);
        }
        warpsList.put(warpName, loc);
        this.partyWarps.put(partyName, warpsList);

        this.partyWarpsList.setValue(partyName + "%" +warpName, loc);
    }
    public String getLastIp(UUID uuid)
    {
        Set<String> Iplists = this.iplist.keySet();
        for(String ipName: Iplists)
        {
            List<String> searchTmp = this.iplist.get(ipName);
            if (searchTmp != null)
            {
                if (searchTmp.contains(uuid.toString()))
                {
                    return ipName;
                }
            }
        }
        return "";
    }
    public static void checkItem(Player player, ItemStack check)
    {
        if (check != null) {

            if (check.getItemMeta() != null) {

                if (check.getItemMeta().getLore() != null) {
                    ItemMeta iM = check.getItemMeta();
                    List<String> loreList = iM.getLore();
                    //"ºrSplash Potion of º4ºlDEATH"
                    int EnchLimit = FMTabList.getEnchantLimit(player);
                    int Current = 0;
                    boolean update = false;
                    for (int i = 0; i < loreList.size(); i++) {
                        if (loreList.get(i).startsWith(ChatColor.AQUA + "Titan.")) {
                            Current++;
                            if (Current > EnchLimit) {
                                if (!loreList.get(i).startsWith(ChatColor.AQUA + "Titan." + ChatColor.GRAY)) {
                                    loreList.set(i, loreList.get(i).replace("Titan.", "Titan." + ChatColor.GRAY));
                                    update = true;
                                }
                            } else {
                                if (loreList.get(i).startsWith(ChatColor.AQUA + "Titan." + ChatColor.GRAY)) {
                                    loreList.set(i, loreList.get(i).replace("Titan." + ChatColor.GRAY, "Titan."));
                                    update = true;
                                }
                            }
                        }
                        if (loreList.get(i).equals(ChatColor.AQUA + "Titan." + ChatColor.DARK_GREEN + "Undroppable")) {
                            loreList.set(i, ChatColor.AQUA + "Titan." + ChatColor.DARK_GREEN + "Undroppable " + ChatColor.GOLD + player.getName());
                            update = true;
                        }
                        if (loreList.get(i).startsWith(ChatColor.AQUA + "Titan:")) {
                            loreList.set(i, loreList.get(i).replace("Titan:", "Titan."));
                            update = true;
                        }
                    }
                    if (update) {
                        iM.setLore(loreList);
                        check.setItemMeta(iM);
                    }
                }
            }
        }
    }
    public static void giveSlimeFunItem( Player p, String Name, int Amount)
    {
        for(int i = 0;i < Amount;i++) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sf give " + p.getName() + " " + Name.toUpperCase());
        }


    }
    public static void addPlayerFromTabList(Player p)
    {
        CraftPlayer cp = (CraftPlayer) p;
        List<EntityPlayer> test = new ArrayList<EntityPlayer>();
        test.add(cp.getHandle());

        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, test);

        try {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
            }
        }
        catch (Exception e)
        {

        }
    }
    public static void removePlayerFromTabList(Player p)
    {
        CraftPlayer cp = (CraftPlayer) p;
        List<EntityPlayer> test = new ArrayList<EntityPlayer>();
        test.add(cp.getHandle());

        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, test);

        try {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
            }
        }
        catch (Exception e)
        {

        }
    }
    public static int getEnchantLimit(Player player)
    {
        for (int i = 10; i > 0; i--)
        {
            if (player.hasPermission("CrazyEnchantments.Limit." + i))
            {
                return i;
            }
        }
        return  10;
            //CrazyEnchantments.Limit.2
    }
    public static void setupVanillaCraft()
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(SlimefunStartup.instance, new Runnable() {
            @Override
            public void run() {
                Iterator iterator2 = Bukkit.recipeIterator();
                while (iterator2.hasNext()) {
                    Recipe r = (Recipe) iterator2.next();
                    if (r instanceof ShapelessRecipe)
                    {
                        ShapelessRecipe SR = (ShapelessRecipe)r;
                        List<ItemStack> spless =  SR.getIngredientList();
                        String myName = "";
                        Boolean goodRec = false;
                        int lasto = 0;
                        for (int o = 0; o < spless.size();o++ )
                        {
                            if (spless.get(o) == null || spless.get(o).getType() == Material.AIR)
                            {
                                myName = myName + "null" + ChatColor.GRAY;
                            }
                            else {
                                goodRec = true;
                                myName = myName + spless.get(o).getType().toString() + ":" + spless.get(o).getDurability() + ChatColor.GRAY;
                            }
                            lasto = o;
                        }
                        lasto++;
                        for (int o = lasto; o < 9;o++ )
                        {
                            myName = myName + "null" + ChatColor.GRAY;
                        }
                        if (goodRec) {
                            recipesV.put(myName, r.getResult());
                        }
                    }
                    if (r instanceof ShapedRecipe)
                    {
                        ShapedRecipe SR = (ShapedRecipe)r;
                        String[] shapeS = SR.getShape();
                        Map<Character, ItemStack> MapCM = SR.getIngredientMap();
                        ItemStack[] Reci = {null, null, null, null, null, null, null, null, null};
                        String myName = "";
                        Character[] key = new Character[9];
                        int counter = 0;
                        int[] yH = {0,1,2,3,4,5,6,7,8};//{0,3,6,1,4,7,2,5,8};
                        String teShape = "";
                        for (int o = 0; o < shapeS.length;o++ )
                        {
                            shapeS[o] = shapeS[o] + "***********";
                            shapeS[o] = shapeS[o].substring(0, 3);

                            for (int p = 0; p < shapeS[o].length();p++ )
                            {
                                key[yH[counter]] = shapeS[o].charAt(p);
                                counter++;
                            }
                            teShape = teShape + shapeS[o]  + "<>";
                        }
                        for (int o = shapeS.length; o < 3;o++ )
                        {
                            String missed = "***";

                            for (int p = 0; p < missed.length();p++ )
                            {
                                key[yH[counter]] = missed.charAt(p);
                                counter++;
                            }
                            teShape = teShape + "XXX"  + "<>";
                        }
                        Short Dura = SR.getResult().getDurability();

                        boolean goodRec = false;
                        for (int o = 0; o < 9;o++ )
                        {

                            Reci[o] = MapCM.get(key[o]);

                            if (Reci[o] == null || Reci[o].getType() == Material.AIR)
                            {
                                myName = myName + "null" + ChatColor.GRAY;
                            }
                            else {
                                goodRec = true;
                                myName = myName + Reci[o].getType().toString() + ":" + Reci[o].getDurability() + ChatColor.GRAY;
                            }
                        }
                        if (goodRec) {
                            recipesV.put(myName, r.getResult());
                        }
                    }
                }
                System.out.println("[Slimefun4]: All vinilla recipes are loaded!");
            }
        }, 300);
    }
    public  String getIP()
    {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            String ip = in.readLine(); //you get the IP as a String
            return ip;
        }
        catch (Exception e)
        {
            return "";
        }
    }
    private  ItemStack makeTitanBookCE(CEnchantments theEnchant)
    {
        List<String> lore = new ArrayList<String>();

        lore.add(theEnchant.getName());
        lore.add("");
        for(int i2 = 0; i2 < theEnchant.getDiscription().size(); i2++)
        {
            lore.add(ChatColor.translateAlternateColorCodes('&', theEnchant.getDiscription().get(i2)));
        }

        ItemStack Soulbound = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta Soulboundmeta = Soulbound.getItemMeta();
        Soulboundmeta.setDisplayName(ChatColor.AQUA + "Titan Book");
        Soulboundmeta.setLore(lore);
        Soulbound.setItemMeta(Soulboundmeta);

        return Soulbound;
    }

    private  ItemStack makeTitanBook(int booktype)
    {
        List<String> lore = new ArrayList<String>();
        if (booktype == 1 || booktype == 0) {
            lore.add(ChatColor.RED + "Soulbound");
        }
        if (booktype == 2 || booktype == 0) {
            lore.add(ChatColor.DARK_PURPLE + "Unbreakable");
        }
        if (booktype == 3 || booktype == 0) {
            lore.add(ChatColor.DARK_GREEN + "Undroppable");
        }
        ItemStack Soulbound = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta Soulboundmeta = Soulbound.getItemMeta();
        Soulboundmeta.setDisplayName(ChatColor.AQUA + "Titan Book");
        Soulboundmeta.setLore(lore);
        Soulbound.setItemMeta(Soulboundmeta);

        return Soulbound;
    }
    private ItemStack makeTitanStone() {
//        getCommand("pm").setTabCompleter(new TabComplete());
        ItemStack Soulbound = new ItemStack(Material.NETHER_WART_BLOCK);
        ItemMeta Soulboundmeta =  Soulbound.getItemMeta();
        Soulboundmeta.setDisplayName(ChatColor.AQUA + "Titan Stone");
        Soulboundmeta.addEnchant(Enchantment.LUCK, 10, true);
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GOLD + "Forged in the heart of Admin Mountain.");
        Soulboundmeta.setLore(lore);
        Soulbound.setItemMeta(Soulboundmeta);
        return Soulbound;
    }
    public static ItemStack getHead(String Texture)
    {
        try
        {
            return CustomSkull.getItem(Texture);
        }catch (Exception e)
        {
            return null;
        }
    }
    public void registerItems()
    {


        ItemStack Reward =  new ItemStack(SFItems.TitanStone);
        Reward.setAmount(16);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.TitanStone, "TitanStone", RecipeType.ANCIENT_ALTAR, new ItemStack[] {SlimefunItems.POWER_CRYSTAL, SlimefunItems.POWER_CRYSTAL, SlimefunItems.POWER_CRYSTAL,SlimefunItems.POWER_CRYSTAL,SlimefunItems.ESSENCE_OF_AFTERLIFE,SlimefunItems.POWER_CRYSTAL,SlimefunItems.POWER_CRYSTAL, SlimefunItems.POWER_CRYSTAL,SlimefunItems.POWER_CRYSTAL}, Reward.clone()).register();
        Reward =  new ItemStack(SFItems.TITAN_MOTOR);
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_PARTS, SFItems.TITAN_MOTOR, "TitanMotor", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot,null,SlimefunItems.ELECTRO_MAGNET,null,SFItems.TitanIngot, SFItems.TitanIngot,SFItems.TitanIngot}, Reward.clone()).register();
        Reward =  new ItemStack(SFItems.ECLIPSE_COIL);
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_PARTS, SFItems.ECLIPSE_COIL, "EclipseCoil", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot,SFItems.EclipseIngot,SlimefunItems.ELECTRIC_MOTOR,SFItems.EclipseIngot,SFItems.EclipseIngot, SFItems.EclipseIngot,SFItems.EclipseIngot}, Reward.clone()).register();

        Reward =  new ItemStack(SFItems.TitanBookSoulbound);
        Reward.setAmount(1);
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN, SFItems.TitanBookSoulbound, "TitanBookSoulbound", RecipeType.ANCIENT_ALTAR, new ItemStack[] {SFItems.TitanStone, SFItems.VOID_PARTICLES, SFItems.TitanStone,  SFItems.VOID_PARTICLES, SlimefunItems.SOULBOUND_SWORD,  SFItems.VOID_PARTICLES,SFItems.TitanStone,  SFItems.VOID_PARTICLES, SFItems.TitanStone }, Reward).register();

        Reward =  new ItemStack(SFItems.TitanBookUnbreakable);
        Reward.setAmount(1);
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN, SFItems.TitanBookUnbreakable, "TitanBookUnbreakable", RecipeType.ANCIENT_ALTAR, new ItemStack[] {SFItems.TitanStone,  SFItems.VOID_PARTICLES_POSITIVE, SFItems.TitanStone, SFItems.VOID_PARTICLES_POSITIVE, SFItems.TitanBookSoulbound, SFItems.VOID_PARTICLES_POSITIVE,SFItems.TitanStone, SFItems.VOID_PARTICLES_POSITIVE, SFItems.TitanStone }, Reward).register();

        Reward =  new ItemStack(SFItems.TitanBookUndroppable);
        Reward.setAmount(1);
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN, SFItems.TitanBookUndroppable, "TitanBookUndroppable", RecipeType.ANCIENT_ALTAR, new ItemStack[] {SFItems.TitanStone, SFItems.VOID_PARTICLES_NEGATIVE, SFItems.TitanStone, SFItems.VOID_PARTICLES_NEGATIVE, SFItems.TitanBookSoulbound, SFItems.VOID_PARTICLES_NEGATIVE,SFItems.TitanStone, SFItems.VOID_PARTICLES_NEGATIVE, SFItems.TitanStone }, Reward).register();

        Reward =  new ItemStack(SFItems.TitanBookAll);
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN, SFItems.TitanBookAll, "TitanBookAll", RecipeType.MAGIC_WORKBENCH, new ItemStack[] {SFItems.TitanBookSoulbound, SFItems.TitanBookUnbreakable, SFItems.TitanBookUndroppable, null, null, null, null, null, null}, Reward).register();


        setupLuckySet();

        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.EclipseNugget, "EclipseNugget", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.TitanStone, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot}).register();
        setupEclipseSet();

        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.TitanNugget, "TitanNugget", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, SFItems.EclipseIngot, null, SFItems.EclipseIngot, SFItems.TitanStone, SFItems.EclipseIngot, null, SFItems.EclipseIngot, null}).register();
        setupTitanSet();

        new TitanAutoDisenchanter(CustomCategories.ELECTRICITY, SFItems.TITAN_AUTO_DISENCHANTER, "TITAN_AUTO_DISENCHANTER", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.TITAN_MOTOR, SlimefunItems.AUTO_DISENCHANTER,  SFItems.TITAN_MOTOR,  SFItems.EclipseIngot,  SFItems.EclipseIngot,  SFItems.EclipseIngot}) {

            @Override
            public int getEnergyConsumption() {
                return 30;
            }

            @Override
            public int getSpeed() {
                return 1;
            }
        }.registerChargeableBlock(true, 512);

        new ElectricLuckyBlockGrinder(CustomCategories.ELECTRICITY, SFItems.ELECTRIC_LUCKY_BLOCK_GRINDER, "ELECTRIC_LUCKY_BLOCK_GRINDER", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.LuckyIngot, SFItems.TITAN_MOTOR, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.ELECTRIC_LUCKY_BLOCK_FACTORY,  SFItems.LuckyIngot,  SFItems.LuckyIngot,  SFItems.TITAN_MOTOR,  SFItems.LuckyIngot}) {

            @Override
            public int getEnergyConsumption() {
                return 30;
            }

            @Override
            public int getSpeed() {
                return 1;
            }
        }.registerChargeableBlock(true, 512);
        new ElectricLuckyBlockFactory(CustomCategories.ELECTRICITY, SFItems.ELECTRIC_LUCKY_BLOCK_FACTORY, "ELECTRIC_LUCKY_BLOCK_FACTORY", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.LuckyIngot, SFItems.TITAN_MOTOR, SFItems.LuckyIngot, SFItems.LuckyIngot, new ItemStack(Material.DISPENSER, 1),  SFItems.LuckyIngot,  SFItems.LuckyIngot,  SFItems.TITAN_MOTOR,  SFItems.LuckyIngot}) {

            @Override
            public int getEnergyConsumption() {
                return 25;
            }

            @Override
            public int getSpeed() {
                return 1;
            }
        }.registerChargeableBlock(true, 512);

        new ElectricCobbletoDust(CustomCategories.ELECTRICITY, SFItems.ELECTRIC_COBBLE_TO_DUST, "ELECTRIC_COBLE_TO_DUST", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.LuckyIngot, SFItems.TITAN_MOTOR, SFItems.LuckyIngot, SFItems.LuckyIngot, SlimefunItems.ELECTRIC_DUST_WASHER,  SFItems.LuckyIngot,  SFItems.LuckyIngot,  SFItems.TITAN_MOTOR,  SFItems.LuckyIngot}) {

            @Override
            public int getEnergyConsumption() {
                return 20;
            }

            @Override
            public int getSpeed() {
                return 2;
            }
        }.registerChargeableBlock(true, 512);

        new ElectricCobbletoIngot(CustomCategories.ELECTRICITY, SFItems.ELECTRIC_COBBLE_TO_INGOT, "ELECTRIC_COBLE_TO_INGOT", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.LuckyIngot, SFItems.TITAN_MOTOR, SFItems.LuckyIngot, SFItems.LuckyIngot, SlimefunItems.ELECTRIC_INGOT_FACTORY,  SFItems.LuckyIngot,  SFItems.LuckyIngot,  SFItems.TITAN_MOTOR,  SFItems.LuckyIngot}) {

            @Override
            public int getEnergyConsumption() {
                return 30;
            }

            @Override
            public int getSpeed() {
                return 2;
            }
        }.registerChargeableBlock(true, 512);


        new ElectricCobbletoDust(CustomCategories.ELECTRICITY, SFItems.ELECTRIC_COBBLE_TO_DUST_2, "ELECTRIC_COBLE_TO_DUST_2", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SlimefunItems.HARDENED_METAL_INGOT, SFItems.EclipseIngot, SlimefunItems.HARDENED_METAL_INGOT, SFItems.EclipseIngot, SFItems.ELECTRIC_COBBLE_TO_DUST,  SFItems.EclipseIngot,  SlimefunItems.HARDENED_METAL_INGOT,  SFItems.EclipseIngot,  SlimefunItems.HARDENED_METAL_INGOT}) {

            @Override
            public int getEnergyConsumption() {
                return 20;
            }

            @Override
            public int getSpeed() {
                return 10;
            }
        }.registerChargeableBlock(true, 512);

        new ElectricCobbletoIngot(CustomCategories.ELECTRICITY, SFItems.ELECTRIC_COBBLE_TO_INGOT_2, "ELECTRIC_COBLE_TO_INGOT_2", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SlimefunItems.HARDENED_METAL_INGOT, SFItems.EclipseIngot, SlimefunItems.HARDENED_METAL_INGOT, SFItems.EclipseIngot, SFItems.ELECTRIC_COBBLE_TO_INGOT,  SFItems.EclipseIngot,  SlimefunItems.HARDENED_METAL_INGOT,  SFItems.EclipseIngot,  SlimefunItems.HARDENED_METAL_INGOT}) {

            @Override
            public int getEnergyConsumption() {
                return 30;
            }

            @Override
            public int getSpeed() {
                return 10;
            }
        }.registerChargeableBlock(true, 512);

        new ElectricCobbletoDust(CustomCategories.ELECTRICITY, SFItems.ELECTRIC_COBBLE_TO_DUST_3, "ELECTRIC_COBLE_TO_DUST_3", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SlimefunItems.REINFORCED_ALLOY_INGOT, SFItems.TitanIngot, SlimefunItems.REINFORCED_ALLOY_INGOT, SFItems.TitanIngot, SFItems.ELECTRIC_COBBLE_TO_DUST_2,  SFItems.TitanIngot,  SlimefunItems.REINFORCED_ALLOY_INGOT,  SFItems.TitanIngot,  SlimefunItems.REINFORCED_ALLOY_INGOT}) {

            @Override
            public int getEnergyConsumption() {
                return 20;
            }

            @Override
            public int getSpeed() {
                return 20;
            }
        }.registerChargeableBlock(true, 512);

        new ElectricCobbletoIngot(CustomCategories.ELECTRICITY, SFItems.ELECTRIC_COBBLE_TO_INGOT_3, "ELECTRIC_COBLE_TO_INGOT_3", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SlimefunItems.REINFORCED_ALLOY_INGOT, SFItems.TitanIngot, SlimefunItems.REINFORCED_ALLOY_INGOT, SFItems.TitanIngot, SFItems.ELECTRIC_COBBLE_TO_INGOT_2,  SFItems.TitanIngot,  SlimefunItems.REINFORCED_ALLOY_INGOT,  SFItems.TitanIngot,  SlimefunItems.REINFORCED_ALLOY_INGOT}) {

            @Override
            public int getEnergyConsumption() {
                return 30;
            }

            @Override
            public int getSpeed() {
                return 20;
            }
        }.registerChargeableBlock(true, 512);



        new AutomatedVanillaCraftingChamber(CustomCategories.ELECTRICITY, SFItems.AUTOMATED_VANILLA_CRAFTING_CHAMBER, "AUTOMATED_VANILLA_CRAFTING_CHAMBER", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, new ItemStack(Material.WORKBENCH), null, SlimefunItems.CARGO_MOTOR, SlimefunItems.COPPER_INGOT, SlimefunItems.CARGO_MOTOR, null, SlimefunItems.ELECTRIC_MOTOR, null}) {

            @Override
            public int getEnergyConsumption() {
                return 10;
            }
        }.registerChargeableBlock(true, 256);

        new AncientAltarCrafter(CustomCategories.ELECTRICITY, SFItems.ANCIENT_ALTAR_CRAFTER, "ANCIENT_ALTAR_CRAFTER_CHAMBER", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, SlimefunItems.ANCIENT_PEDESTAL, null, SlimefunItems.CARGO_MOTOR, SlimefunItems.ANCIENT_ALTAR, SlimefunItems.CARGO_MOTOR, SlimefunItems.ANCIENT_PEDESTAL, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.ANCIENT_PEDESTAL}) {

            @Override
            public int getEnergyConsumption() {
                return 50;
            }
        }.registerChargeableBlock(true, 256);

        new AutomatedAncientAltarCrafter(CustomCategories.ELECTRICITY, SFItems.AUTOMATED_ANCIENT_ALTAR_CRAFTER, "AUTOMATED_ANCIENT_ALTAR_CRAFTER_CHAMBER", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {null, new ItemStack(Material.WORKBENCH), null, SlimefunItems.CARGO_MOTOR, SFItems.ANCIENT_ALTAR_CRAFTER, SlimefunItems.CARGO_MOTOR, null, SlimefunItems.ELECTRIC_MOTOR, null}) {

            @Override
            public int getEnergyConsumption() {
                return 50;
            }
        }.registerChargeableBlock(true, 256);

        new CharcoalFactory(CustomCategories.ELECTRICITY, SFItems.CHARCOAL_FACTORY, "CHARCOAL_FACTORY", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.EclipseIngot, SFItems.TITAN_MOTOR, SFItems.EclipseIngot, SFItems.ECLIPSE_COIL, new ItemStack(Material.FURNACE), SFItems.ECLIPSE_COIL, SlimefunItems.GILDED_IRON, SFItems.TITAN_MOTOR, SlimefunItems.GILDED_IRON}) {

            @Override
            public void registerDefaultRecipes() {
            }

            @Override
            public int getEnergyConsumption() {
                return 30;
            }

            @Override
            public int getSpeed() {
                return 1;
            }

        }.registerChargeableBlock(true, 50);
        new CharcoalFactory(CustomCategories.ELECTRICITY, SFItems.CHARCOAL_FACTORY_2, "CHARCOAL_FACTORY_2", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.EclipseIngot, SFItems.TITAN_MOTOR, SFItems.EclipseIngot, SFItems.ECLIPSE_COIL, SFItems.CHARCOAL_FACTORY, SFItems.ECLIPSE_COIL, SlimefunItems.GILDED_IRON, SFItems.TITAN_MOTOR, SlimefunItems.GILDED_IRON}) {

            @Override
            public void registerDefaultRecipes() {
            }

            @Override
            public int getEnergyConsumption() {
                return 30;
            }

            @Override
            public int getSpeed() {
                return 10;
            }

        }.registerChargeableBlock(true, 50);
        new CharcoalFactory(CustomCategories.ELECTRICITY, SFItems.CHARCOAL_FACTORY_3, "CHARCOAL_FACTORY_3", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.EclipseIngot, SFItems.TITAN_MOTOR, SFItems.EclipseIngot, SFItems.ECLIPSE_COIL, SFItems.CHARCOAL_FACTORY_2, SFItems.ECLIPSE_COIL, SlimefunItems.GILDED_IRON, SFItems.TITAN_MOTOR, SlimefunItems.GILDED_IRON}) {

            @Override
            public void registerDefaultRecipes() {
            }

            @Override
            public int getEnergyConsumption() {
                return 30;
            }

            @Override
            public int getSpeed() {
                return 20;
            }

        }.registerChargeableBlock(true, 50);
        new NuggettoIngotFactory(CustomCategories.ELECTRICITY, SFItems.NUGGETTOINGOT, "NUGGET_TO_IGNOT", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.LuckyIngot, new ItemStack(Material.BOOKSHELF), SFItems.LuckyIngot, SFItems.LuckyIngot,  new ItemStack(Material.DISPENSER),  SFItems.LuckyIngot,  SFItems.LuckyIngot,   new ItemStack(Material.BOOKSHELF),  SFItems.LuckyIngot}) {

            @Override
            public int getEnergyConsumption() {
                return 30;
            }

            @Override
            public int getSpeed() {
                return 1;
            }
        }.registerChargeableBlock(true, 512);
        new IngotUpFactory(CustomCategories.ELECTRICITY, SFItems.INGOTUP, "INGOT_UP", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.LuckyIngot, new ItemStack(Material.WORKBENCH), SFItems.LuckyIngot, SFItems.LuckyIngot,  new ItemStack(Material.DISPENSER),  SFItems.LuckyIngot,  SFItems.LuckyIngot,   new ItemStack(Material.BOOKSHELF),  SFItems.LuckyIngot}) {

            @Override
            public int getEnergyConsumption() {
                return 30;
            }

            @Override
            public int getSpeed() {
                return 1;
            }
        }.registerChargeableBlock(true, 512);


        new VoidMiner(CustomCategories.ELECTRICITY, SFItems.VOIDMINNER, "VOID_MINER", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.TITAN_MOTOR, SFItems.BEDROCK_DUST, SFItems.TITAN_MOTOR,SFItems.BEDROCK_DUST, SFItems.ECLIPSE_COIL, SFItems.BEDROCK_DUST, SFItems.TITAN_MOTOR, SFItems.BEDROCK_DUST, SFItems.TITAN_MOTOR}) {

            @Override
            public int getEnergyConsumption() {
                return 700;
            }

            @Override
            public int getSpeed() {
                return 1;
            }
        }.registerChargeableBlock(true, 1000);
        new BedrockDrill(CustomCategories.ELECTRICITY, SFItems.BEDROCKDRILL, "BEDROCKD_RILL", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.ECLIPSE_COIL, SFItems.TitanIngot, SFItems.ECLIPSE_COIL,SFItems.TitanIngot, SFItems.TITAN_MOTOR, SFItems.TitanIngot, SFItems.ECLIPSE_COIL, SFItems.TitanIngot, SFItems.ECLIPSE_COIL}) {

            @Override
            public int getEnergyConsumption() {
                return 300;
            }

            @Override
            public int getSpeed() {
                return 1;
            }
        }.registerChargeableBlock(true, 500);
        new SlimefunToSTBConverter(CustomCategories.ELECTRICITY, SFItems.SF4_TO_STB_CONVERTER, "SF4_TO_STB", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.ECLIPSE_COIL, SFItems.LuckyIngot, SFItems.ECLIPSE_COIL,SFItems.LuckyIngot, SFItems.TitanIngot, SFItems.LuckyIngot, SFItems.ECLIPSE_COIL, SFItems.LuckyIngot, SFItems.ECLIPSE_COIL}) {

            @Override
            public int getEnergyConsumption() {
                return 100;
            }

            @Override
            public int getSpeed() {
                return 1;
            }
        }.registerChargeableBlock(true, 100);
        new SlimefunToSTBConverter(CustomCategories.ELECTRICITY, SFItems.SF4_TO_STB_CONVERTER_2, "SF4_TO_STB_2", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.ECLIPSE_COIL, SFItems.LuckyIngot, SFItems.ECLIPSE_COIL,SFItems.LuckyIngot, SFItems.SF4_TO_STB_CONVERTER, SFItems.LuckyIngot, SFItems.ECLIPSE_COIL, SFItems.LuckyIngot, SFItems.ECLIPSE_COIL}) {

            @Override
            public int getEnergyConsumption() {
                return 1000;
            }

            @Override
            public int getSpeed() {
                return 1;
            }
        }.registerChargeableBlock(true, 1000);

        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, new CustomItem(new ItemStack(Material.BEDROCK), "&8Bedrock"), "BEDROCK", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { SFItems.BEDROCK_DUST, SFItems.BEDROCK_DUST, SFItems.BEDROCK_DUST, SFItems.BEDROCK_DUST, SFItems.TitanIngot, SFItems.BEDROCK_DUST, SFItems.BEDROCK_DUST, SFItems.BEDROCK_DUST, SFItems.BEDROCK_DUST }).register();

        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.BEDROCK_DUST, "BEDROCK_DUST", CustomRecipeType.BEDROCK_DRILL, new ItemStack[] { null }).register();

        new SlimefunItem(CustomCategories.SLIMEFUN_PARTS, SFItems.BEDROCK_DRILL, "BEDROCK_DRILL", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { null, SlimefunItems.REINFORCED_PLATE, null, SlimefunItems.REINFORCED_PLATE, SFItems.LuckyNugget, SlimefunItems.REINFORCED_PLATE, null, SlimefunItems.REINFORCED_PLATE, null }, SFItems.BEDROCK_DRILL.clone() ).register();

        new SlimefunItem(CustomCategories.SLIMEFUN_PARTS, SFItems.LASER_CHARGE, "LASER_CHARGE", RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] { null, SlimefunItems.REINFORCED_ALLOY_INGOT, null, SlimefunItems.REINFORCED_ALLOY_INGOT, new ItemStack(Material.REDSTONE), SlimefunItems.REINFORCED_ALLOY_INGOT, null, SlimefunItems.REINFORCED_ALLOY_INGOT, null }, SFItems.LASER_CHARGE.clone()).register();

        new AGenerator(CustomCategories.ELECTRICITY, SFItems.RAREORE_GENERATOR, "RAREORE_GENERATOR", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SFItems.ECLIPSE_COIL, new ItemStack(Material.FURNACE), SFItems.ECLIPSE_COIL, SFItems.VOID_PARTICLES, SFItems.BEDROCK_DUST, SFItems.VOID_PARTICLES, SFItems.VOID_PARTICLES_NEGATIVE, SFItems.TITAN_MOTOR, SFItems.VOID_PARTICLES_POSITIVE}) {

            @Override
            public void registerDefaultRecipes() {
                registerFuel(new MachineFuel(32, new MaterialData(Material.DIAMOND, (byte) 0).toItemStack(1)));
                registerFuel(new MachineFuel(320, new ItemStack(Material.DIAMOND_BLOCK)));
                registerFuel(new MachineFuel(64, new MaterialData(Material.EMERALD, (byte) 0).toItemStack(1)));
                registerFuel(new MachineFuel(640, new ItemStack(Material.EMERALD_BLOCK)));
            }

            @Override
            public ItemStack getProgressBar() {
                return new ItemStack(Material.FLINT_AND_STEEL);
            }

            @Override
            public String getInventoryTitle() {
                return "&cRare Ore Generator";
            }

            @Override
            public int getEnergyProduction() {
                return 32;
            }

        }.registerUnrechargeableBlock(true, 256);

        new SlimefunItem(CustomCategories.ELECTRICITY, SFItems.THERMAL_GENERATOR, "THERMAL_GENERATOR", RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {SlimefunItems.HEATING_COIL, SlimefunItems.SOLAR_GENERATOR_4, SlimefunItems.HEATING_COIL, SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.LARGE_CAPACITOR, SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.HEATING_COIL, SlimefunItems.SOLAR_GENERATOR_4, SlimefunItems.HEATING_COIL})
                .register(true, new EnergyTicker() {

                    @Override
                    public double generateEnergy(Location l, SlimefunItem item, Config data) {
                        try {
                            if (l == null) {
                                return 0;
                            }
                            Location lavaCheck = l.clone().add(0, -1, 0);
                            Location AirCheck = l.clone().add(0, 1, 0);
                            boolean Run = true;
                            boolean explode = false;
                            if (l.getChunk().isLoaded()) {
                                try {
                                    for (int x = -1; x < 2; x++) {
                                        for (int z = -1; z < 2; z++) {
                                            if (lavaCheck.clone().add(x, 0, z).getBlock().getType() != Material.STATIONARY_LAVA) {
                                                Run = false;
                                            }
                                            if (AirCheck.clone().add(x, 0, z).getBlock().getType() != Material.AIR) {
                                                explode = true;
                                            }
                                        }
                                    }
                                } catch (Exception e) {

                                }
                            }
                            if (Run && !explode) {
                                double past = 256 * (1D - (l.getBlockY() / 100D));
                                return past;
                            } else {
                                if (explode && Run) {
                                    Bukkit.getScheduler().scheduleSyncDelayedTask(SlimefunStartup.instance, new Runnable() {

                                        @Override
                                        public void run() {
                                            AirCheck.getWorld().createExplosion(AirCheck.add(0, 7, 0).clone(), 6);
                                            BlockStorage.clearBlockInfo(l);

                                            for (int y = 0; y < 100; y++) {
                                                for (int x = -1; x < 2; x++) {
                                                    for (int z = -1; z < 2; z++) {
                                                        Location exp = l.clone().add(x, y, z);
                                                        if (BlockStorage.hasBlockInfo(exp)) {
                                                            BlockStorage.clearBlockInfo(exp);
                                                        }
                                                        AirCheck.getWorld().getBlockAt(exp).setType(Material.AIR);
                                                    }
                                                }
                                            }
                                        }
                                    }, 20);

                                }
                                return 0;
                            }
                        }
                        catch (Exception e)
                        {
                            return 0;
                        }
                    }
                    //8192
                    @Override
                    public boolean explode(Location l) {
                        return false;
                    }

                });

        ChargableBlock.registerChargableBlock("THERMAL_GENERATOR", 8192, false);

        new TitanTalisman(SFItems.TALISMAN_VOID, "TALISMAN_VOID",
                new ItemStack[] {SFItems.VOID_PARTICLES, SFItems.BEDROCK_DUST, SFItems.VOID_PARTICLES, SFItems.TitanStone, SlimefunItems.TALISMAN, SFItems.TitanStone, SFItems.VOID_PARTICLES, SFItems.BEDROCK_DUST, SFItems.VOID_PARTICLES},
                false, false, "void", new PotionEffect[0])
                .register(true);

        Slimefun.registerResearch(new Research(79001, "Thermal Power Plant", 89), SFItems.THERMAL_GENERATOR);
        Slimefun.registerResearch(new Research(79002, "Ancient Altar Crafter", 75), SFItems.ANCIENT_ALTAR_CRAFTER);
        Slimefun.registerResearch(new Research(79003, "Vanilla Auto Crafter", 25), SFItems.AUTOMATED_VANILLA_CRAFTING_CHAMBER);
        Slimefun.registerResearch(new Research(79004, "Automated Ancient Altar Crafter", 25), SFItems.AUTOMATED_ANCIENT_ALTAR_CRAFTER);


        Slimefun.registerResearch(new Research(7500, "Titan Stone", 250), new ItemStack[] { SFItems.TitanStone });
        Slimefun.registerResearch(new Research(7501, "TitanBook Soulbound", 350), new ItemStack[] { SFItems.TitanBookSoulbound });
        Slimefun.registerResearch(new Research(7502, "TitanBook Unbreakable", 400), new ItemStack[] { SFItems.TitanBookUnbreakable });
        Slimefun.registerResearch(new Research(7503, "TitanBook Undroppable", 150), new ItemStack[] { SFItems.TitanBookUndroppable });
        Slimefun.registerResearch(new Research(7504, "TitanBook All", 150), new ItemStack[] { SFItems.TitanBookAll });

        Slimefun.registerResearch(new Research(7505, "Lucky Nugget", 50), new ItemStack[] { SFItems.LuckyNugget,  SFItems.LuckyNuggetB});
        //Slimefun.registerResearch(new Research(7506, "Lucky Axe", 0), new ItemStack[] { SFItems.LuckyAxe });
        //Slimefun.registerResearch(new Research(7507, "Lucky Sword", 0), new ItemStack[] { SFItems.LuckySword });
        //Slimefun.registerResearch(new Research(7508, "Lucky Pickaxe", 0), new ItemStack[] { SFItems.LuckyPickaxe });
        //Slimefun.registerResearch(new Research(7509, "Lucky Helmet", 0), new ItemStack[] { SFItems.LuckyHelmet });
        //Slimefun.registerResearch(new Research(7510, "Lucky Chestplate", 0), new ItemStack[] { SFItems.LuckyChestplate });
        //Slimefun.registerResearch(new Research(7511, "Lucky Leggings", 0), new ItemStack[] { SFItems.LuckyLeggings });
        //Slimefun.registerResearch(new Research(7512, "Lucky Boots", 0), new ItemStack[] { SFItems.LuckyBoots });

        Slimefun.registerResearch(new Research(7513, "Eclipse Nugget", 50), new ItemStack[] { SFItems.EclipseNugget, SFItems.EclipseNuggetB });
        Slimefun.registerResearch(new Research(7514, "Eclipse Axe", 100), new ItemStack[] { SFItems.EclipseAxe });
        Slimefun.registerResearch(new Research(7515, "Eclipse Sword", 125), new ItemStack[] { SFItems.EclipseSword });
        Slimefun.registerResearch(new Research(7516, "Eclipse Pickaxe", 175), new ItemStack[] { SFItems.EclipsePickaxe });
        Slimefun.registerResearch(new Research(7517, "Eclipse Helmet", 100), new ItemStack[] { SFItems.EclipseHelmet });
        Slimefun.registerResearch(new Research(7518, "Eclipse Chestplate", 175), new ItemStack[] { SFItems.EclipseChestplate });
        Slimefun.registerResearch(new Research(7519, "Eclipse Leggings", 150), new ItemStack[] { SFItems.EclipseLeggings });
        Slimefun.registerResearch(new Research(7520, "Eclipse Boots", 100), new ItemStack[] { SFItems.EclipseBoots });

        Slimefun.registerResearch(new Research(7521, "Titan Nugget", 50), new ItemStack[] { SFItems.TitanNugget, SFItems.TitanNuggetB });
        Slimefun.registerResearch(new Research(7522, "Titan Axe", 100), new ItemStack[] { SFItems.TitanAxe });
        Slimefun.registerResearch(new Research(7523, "Titan Sword", 125), new ItemStack[] { SFItems.TitanSword });
        Slimefun.registerResearch(new Research(7524, "Titan Pickaxe", 175), new ItemStack[] { SFItems.TitanPickaxe });
        Slimefun.registerResearch(new Research(7525, "Titan Helmet", 100), new ItemStack[] { SFItems.TitanHelmet });
        Slimefun.registerResearch(new Research(7526, "Titan Chestplate", 175), new ItemStack[] { SFItems.TitanChestplate });
        Slimefun.registerResearch(new Research(7527, "Titan Leggings", 150), new ItemStack[] { SFItems.TitanLeggings });
        Slimefun.registerResearch(new Research(7528, "Titan Boots", 100), new ItemStack[] { SFItems.TitanBoots });

        Slimefun.registerResearch(new Research(7529, "Lucky Ingot", 50), new ItemStack[] { SFItems.LuckyIngot });
        Slimefun.registerResearch(new Research(7530, "Eclipse Ingot", 50), new ItemStack[] { SFItems.EclipseIngot });
        Slimefun.registerResearch(new Research(7531, "Titan Ingot", 50), new ItemStack[] { SFItems.TitanIngot });

        Slimefun.registerResearch(new Research(7532, "Electric Cobble to", 25), new ItemStack[] { SFItems.ELECTRIC_COBBLE_TO_DUST_3, SFItems.ELECTRIC_COBBLE_TO_INGOT_3,SFItems.ELECTRIC_COBBLE_TO_DUST_2, SFItems.ELECTRIC_COBBLE_TO_INGOT_2, SFItems.ELECTRIC_COBBLE_TO_DUST, SFItems.ELECTRIC_COBBLE_TO_INGOT, SFItems.ELECTRIC_LUCKY_BLOCK_FACTORY, SFItems.ELECTRIC_LUCKY_BLOCK_GRINDER });
        Slimefun.registerResearch(new Research(7536, "Titan Disenchanter", 50), new ItemStack[] { SFItems.TITAN_AUTO_DISENCHANTER });
        Slimefun.registerResearch(new Research(7537, "Titan Motor", 50), new ItemStack[] { SFItems.TITAN_MOTOR, SFItems.ECLIPSE_COIL });
        Slimefun.registerResearch(new Research(7538, "Titan Charcoal", 25), new ItemStack[] { SFItems.CHARCOAL_FACTORY, SFItems.NUGGETTOINGOT, SFItems.INGOTUP, SFItems.SF4_TO_STB_CONVERTER, SFItems.CHARCOAL_FACTORY_2, SFItems.CHARCOAL_FACTORY_3});
        Slimefun.registerResearch(new Research(7539, "Titan End Game", 125), new ItemStack[] { SFItems.BEDROCKDRILL, SFItems.VOIDMINNER });
        Slimefun.registerResearch(new Research(7540, "Titan Talisman", 50), new ItemStack[] { SFItems.TALISMAN_VOID});
        Slimefun.registerResearch(new Research(7541, "Rare Ore Power", 89), SFItems.RAREORE_GENERATOR);


    }
    private void setupTitanSet() {
        ItemStack Reward =  new ItemStack(SFItems.TitanNugget);
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.TitanNuggetB, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {null,null,SFItems.TitanAxe, SFItems.TitanBoots, SFItems.TitanChestplate, SFItems.TitanHelmet, SFItems.TitanLeggings, SFItems.TitanSword, SFItems.TitanPickaxe}).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanAxe, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget ", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanBoots, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(5);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanChestplate, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanHelmet, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanLeggings, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanSword, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.TitanNugget, "TitanNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.TitanPickaxe, null, null, null, null, null, null, null, null}, Reward).register();

        Reward = SFItems.TitanNugget.clone();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.TitanIngot, "TitanIgnot", RecipeType.COMPRESSOR,  new ItemStack[] {Reward, null, null, null, null, null, null, null, null}).register();

        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanAxe, "TitanAxe", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.TitanIngot, SFItems.TitanIngot, null, SFItems.TitanIngot, SlimefunItems.MAGNESIUM_INGOT, null, null, SlimefunItems.MAGNESIUM_INGOT, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanSword, "TitanSword", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, SFItems.TitanIngot, null, null, SFItems.TitanIngot,null, null, SlimefunItems.MAGNESIUM_INGOT, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanPickaxe, "TitanPickaxe", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, null, SlimefunItems.MAGNESIUM_INGOT, null, null, SlimefunItems.MAGNESIUM_INGOT, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanHelmet, "TitanHelmet", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, null, SFItems.TitanIngot, null, null, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanChestplate, "TitanChestplate", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.TitanIngot, null, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanLeggings, "TitanLeggings", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot, SFItems.TitanIngot,null, SFItems.TitanIngot, SFItems.TitanIngot, null, SFItems.TitanIngot}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_TITAN_GEAR, SFItems.TitanBoots, "TitanBoots", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, null, null, SFItems.TitanIngot, null, SFItems.TitanIngot, SFItems.TitanIngot, null, SFItems.TitanIngot}).register();
    }
    private void setupEclipseSet() {
        ItemStack Reward =  new ItemStack(SFItems.EclipseNugget);
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.EclipseNuggetB, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {null,null,SFItems.EclipseAxe, SFItems.EclipseBoots, SFItems.EclipseChestplate, SFItems.EclipseHelmet, SFItems.EclipseLeggings, SFItems.EclipseSword, SFItems.EclipsePickaxe}).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipseAxe, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget ", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipseBoots, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(5);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipseChestplate, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(5);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipseHelmet, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipseLeggings, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipseSword, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.EclipseNugget, "EclipseNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.EclipsePickaxe, null, null, null, null, null, null, null, null}, Reward).register();

        Reward = SFItems.EclipseNugget.clone();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.EclipseIngot, "EclipseIgnot", RecipeType.COMPRESSOR,  new ItemStack[] {Reward, null, null, null, null, null, null, null, null}).register();

        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipseAxe, "EclipseAxe", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.EclipseIngot, SFItems.EclipseIngot, null, SFItems.EclipseIngot, SlimefunItems.MAGNESIUM_INGOT, null, null, SlimefunItems.MAGNESIUM_INGOT, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipseSword, "EclipseSword", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, SFItems.EclipseIngot, null, null, SFItems.EclipseIngot,null, null, SlimefunItems.MAGNESIUM_INGOT, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipsePickaxe, "EclipsePickaxe", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, null, SlimefunItems.MAGNESIUM_INGOT, null, null, SlimefunItems.MAGNESIUM_INGOT, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipseHelmet, "EclipseHelmet", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, null, SFItems.EclipseIngot, null, null, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipseChestplate, "EclipseChestplate", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.EclipseIngot, null, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipseLeggings, "EclipseLeggings", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot, SFItems.EclipseIngot,null, SFItems.EclipseIngot, SFItems.EclipseIngot, null, SFItems.EclipseIngot}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_ECLIPSE, SFItems.EclipseBoots, "EclipseBoots", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, null, null, SFItems.EclipseIngot, null, SFItems.EclipseIngot, SFItems.EclipseIngot, null, SFItems.EclipseIngot}).register();
    }
    private void setupLuckySet() {
        ItemStack Reward = SFItems.LuckyBlock.clone();

        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.LuckyNugget.clone(), "LuckyNugget", RecipeType.COMPRESSOR,  new ItemStack[] {Reward, null, null, null, null, null, null, null, null}).register();

        Reward =  new ItemStack(SFItems.LuckyNugget);
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.LuckyNuggetB, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {null,null,SFItems.LuckyAxe, SFItems.LuckyBoots, SFItems.LuckyChestplate, SFItems.LuckyHelmet, SFItems.LuckyLeggings, SFItems.LuckySword, SFItems.LuckyPickaxe}).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckyAxe, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget ", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckyBoots, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(5);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckyChestplate, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckyHelmet, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(4);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckyLeggings, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckySword, null, null, null, null, null, null, null, null}, Reward).register();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_BLANK, SFItems.LuckyNugget, "LuckyNugget", RecipeType.ORE_CRUSHER, new ItemStack[] {SFItems.LuckyPickaxe, null, null, null, null, null, null, null, null}, Reward).register();

        Reward = SFItems.LuckyNugget.clone();
        Reward.setAmount(3);
        new SlimefunItem(CustomCategories.SLIMEFUN_RESOURCES, SFItems.LuckyIngot, "LuckyIgnot", RecipeType.COMPRESSOR,  new ItemStack[] {Reward, null, null, null, null, null, null, null, null}).register();

        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckyAxe, "LuckyAxe", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.LuckyIngot, SFItems.LuckyIngot, null, SFItems.LuckyIngot, SlimefunItems.MAGNESIUM_INGOT, null, null, SlimefunItems.MAGNESIUM_INGOT, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckySword, "LuckySword", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, SFItems.LuckyIngot, null, null, SFItems.LuckyIngot,null, null, SlimefunItems.MAGNESIUM_INGOT, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckyPickaxe, "LuckyPickaxe", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, null, SlimefunItems.MAGNESIUM_INGOT, null, null, SlimefunItems.MAGNESIUM_INGOT, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckyHelmet, "LuckyHelmet", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, null, SFItems.LuckyIngot, null, null, null}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckyChestplate, "LuckyChestplate", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.LuckyIngot, null, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckyLeggings, "LuckyLeggings", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot, SFItems.LuckyIngot,null, SFItems.LuckyIngot, SFItems.LuckyIngot, null, SFItems.LuckyIngot}).register();
        new SlimefunItem(CustomCategories.SLIMEFUN_LUCKY, SFItems.LuckyBoots, "LuckyBoots", RecipeType.MAGIC_WORKBENCH,  new ItemStack[] {null, null, null, SFItems.LuckyIngot, null, SFItems.LuckyIngot, SFItems.LuckyIngot, null, SFItems.LuckyIngot}).register();
    }
    public boolean checkforTitanStone(ItemStack itemA)
    {
        if (itemA != null) {
            if (itemA.getItemMeta() != null) {
                if (itemA.getItemMeta().hasDisplayName() && itemA.getItemMeta().hasLore()) {
                    if (itemA.getItemMeta().getDisplayName().equals(SFItems.TitanStone.getItemMeta().getDisplayName())) {
                        if (FMTabList.plugin.equalsLore(itemA.getItemMeta().getLore(), SFItems.TitanStone.getItemMeta().getLore())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean equalsLore(List<String> lore, List<String> lore2) {
        String string1 = "", string2 = "";
        for (String string: lore) {
            if (!string.startsWith(ChatColor.AQUA + "Titan.")) {
                if (!string.startsWith("&e&e&7")) string1 = string1 + "-NEW LINE-" + string;
            }
        }
        for (String string: lore2) {
            if (!string.startsWith(ChatColor.AQUA + "Titan.")) {
                if (!string.startsWith("&e&e&7")) string2 = string2 + "-NEW LINE-" + string;
            }
        }
        return string1.equals(string2);
    }
    public static Player getPlayer(String nickName)
    {
        Player player = Bukkit.getPlayer(nickName);
        if (player != null) {
            return player;
        }
        Collection<? extends Player> nickNamePlayer = Bukkit.getOnlinePlayers();
        for (Player nick: nickNamePlayer)
        {
            if ( ChatColor.stripColor(nick.getDisplayName().toLowerCase()).equalsIgnoreCase(ChatColor.stripColor(nickName.toLowerCase())))
            {
                return nick;
            }
            if (nick.getDisplayName().toLowerCase().equalsIgnoreCase(nickName.toLowerCase()))
            {
                return nick;
            }
        }
        return null;
    }
    public void sendMail(String sub, String message)
    {
        try {
            // Step1
            System.out.println("\n 1st ===> setup Mail Server Properties..");
            mailServerProperties = System.getProperties();
            mailServerProperties.put("mail.smtp.port", "587");
            mailServerProperties.put("mail.smtp.auth", "true");
            mailServerProperties.put("mail.smtp.starttls.enable", "true");
            System.out.println("Mail Server Properties have been setup successfully..");

            // Step2
            System.out.println("\n\n 2nd ===> get Mail Session..");
            getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("cgfreethemice@gmail.com"));
            //generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
            generateMailMessage.setSubject("Fires of Titan: " + sub);
            String emailBody = message +  "<br><br> Regards, <br>Fires of Titan";
            generateMailMessage.setContent(emailBody, "text/html");
            System.out.println("Mail Session has been created successfully..");

            // Step3
            System.out.println("\n\n 3rd ===> Get Session and Send mail");
            Transport transport = getMailSession.getTransport("smtp");

            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect("smtp.gmail.com", "codegreenmice", "epLKl*=.gU1}");
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();

            System.out.println("\n\n 4th ===> Mail sent!");
        }
        catch (Exception e)
        {
            System.out.println("\n\n Error ===> \n" + e.toString());
        }
    }

    public void sendChatMessage(CommandSender sender, String message)
    {
        sendChatMessage(((Player)sender), message);
    }
    public void sendChatMessage(Player player, String message)
    {
        player.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " +ChatColor.GREEN + message);
    }
    public void restoreBackup(Player player, String Backup)
    {
        restoreBackup(player, player, Backup);
    }
    public Player restoreBackup(String save, Player player, String Backup)
    {
        Player OpenNew = Bukkit.getPlayer(save);
        String lookupID = FMTabList.plugin.lookupFullUUID("farthead");
        if (OpenNew == null)
        {

            File OflinePlayer = new File("worldmain" + File.separator + "playerdata" + File.separator + lookupID + ".dat");
            File OnlinePlayer = new File("worldmain" + File.separator + "playerdata" + File.separator + player.getUniqueId().toString() + ".dat");
            if (!OflinePlayer.exists())
            {
                FMTabList.plugin.CopyFile(OnlinePlayer, OflinePlayer);
            }
            OfflinePlayer OpenNewOL = Bukkit.getOfflinePlayer(save);
            OpenNew = loadPlayer(OpenNewOL);
        }
        if (OpenNew != null)
        {
            restoreBackup(OpenNew, player, Backup);
        }
        return OpenNew;
    }
    public void restoreBackup(Player save, Player player, String Backup)
    {
        if (!Backup.endsWith(".dat"))
        {
            Backup = Backup + ".dat";
        }
        String[] Names = FMTabList.plugin.lookupUsername(player.getUniqueId().toString());
        String lookupID = save.getUniqueId().toString();
        if (Names != null) {
            for (String name : Names) {
                if (!name.equals("")) {
                    File tmpBackup = new File("worldmain" + File.separator + "playerdata" + File.separator + "Mybackups" + File.separator + name);
                    if (tmpBackup.exists()) {
                        File[] listBackups = tmpBackup.listFiles();
                        long lastMod = -1;
                        File tmpBackupTo = new File(tmpBackup, Backup);

                        if (tmpBackupTo != null) {
                            if (tmpBackupTo.exists()) {
                                FMTabList.plugin.CopyFile(tmpBackupTo, new File("worldmain" + File.separator + "playerdata" + File.separator + lookupID + ".dat"));
                                save.loadData();
                                if (save.getName().equals(player.getName())) {
                                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "broadcast &d>>>>>>>>>>>>>>>>>>>>>&fBack Up Restored for " + player.getName() + "&d<<<<<<<<<<<<<<<<<<<<<");
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    public Player loadPlayer(OfflinePlayer offline)
    {
        if ((offline == null) || (!offline.hasPlayedBefore())) {
            return null;
        }

        GameProfile profile = new GameProfile(offline.getUniqueId(), offline.getName());
        MinecraftServer server = ((CraftServer)Bukkit.getServer()).getServer();

        EntityPlayer entity = new EntityPlayer(server, server.getWorldServer(0), profile, new PlayerInteractManager(server
                .getWorldServer(0)));

        Player target = entity == null ? null : entity.getBukkitEntity();
        if (target != null)
        {
            target.loadData();
        }

        return target;
    }
    public TreeMap<String, String> makeList(String playerName)
    {
        String UUID = FMTabList.plugin.lookupUUID(playerName);
        String[] backupsw = FMTabList.plugin.lookupBackups(UUID);
        if (backupsw != null) {
            TreeMap<String, String> guiList = new TreeMap<String, String>();
            for (String back : backupsw) {
                String[] InfoSPlit = back.split(" Made On: ");
                guiList.put(InfoSPlit[0].replace(".dat", ""), InfoSPlit[1]);
            }
            return guiList;
        }
        return null;
    }
    public String[] lookupBackups(String UUID)
    {
        String[] Names = FMTabList.plugin.lookupUsername(UUID);
        String[] output = null;
        if (Names != null) {
            for (String name : Names) {
                if (!name.equals("")) {
                    File tmpBackup = new File("worldmain" + File.separator + "playerdata" + File.separator + "Mybackups" + File.separator + name);
                    if (tmpBackup.exists()) {
                        File[] listBackups = tmpBackup.listFiles();
                        output = new  String[listBackups.length];
                        int i = 0;
                        for (File tmpBackup2 : listBackups) {;
                            if (tmpBackup2.isFile()) {
                                Date date = new Date(System.currentTimeMillis());
                                DateFormat formatter = new SimpleDateFormat("MM-dd-YY HH:mm ss");
                                String dateFormatted = formatter.format(tmpBackup2.lastModified());
                                output[i] = tmpBackup2.getName() + " Made On: " +dateFormatted;
                                i++;
                            }
                        }
                    }
                }
            }
        }
        return output;
    }
    public String[] lookupUsername(String UUID)
    {
        try {
            UUID = UUID.replace("-", "");
            //[{"name":"KrisJelbring", "name":"KrisJelbring"}]
            URL oracle = new URL("https://api.mojang.com/user/profiles/" + UUID + "/names");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            String fulltext = "";
            while ((inputLine = in.readLine()) != null)
                fulltext = fulltext + inputLine;
            in.close();
            fulltext = fulltext.replace("[", "").replace("{","").replace("]", "").replace("}","").replace("\"", "").replace("name:", "");
            //name:KrisJelbring, name:KrisJelbring
            String[] splitup = fulltext.split(",");
            if (!fulltext.contains(","))
            {
                splitup = new String[]{fulltext};
            }

            return splitup;
        }
        catch (Exception e)
        {
            return  null;
        }

    }
    public void CopyFile(File afile, File bfile)
    {
        InputStream inStream = null;
        OutputStream outStream = null;

        try{


            inStream = new FileInputStream(afile);
            outStream = new FileOutputStream(bfile);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0){

                outStream.write(buffer, 0, length);

            }

            inStream.close();
            outStream.close();

            //System.out.println("File is copied successful!");

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public boolean checkUUID(UUID toCheck)
    {
        try {
            URL oracle = new URL("https://api.mojang.com/users/profiles/minecraft/" + toCheck.toString() + "/names");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            String fulltext = "";
            while ((inputLine = in.readLine()) != null)
                fulltext = fulltext + inputLine;
            in.close();
            if (fulltext.equalsIgnoreCase("{\"error\":\"Not Found\",\"errorMessage\":\"The server has not found anything matching the request URI\"}"))
            {
                return  false;
            }
            return true;
        }
        catch (Exception e)
        {
            return  false;
        }
    }
    public String lookupFullUUID(String username)
    {
        String shortUUID = lookupUUID(username);
        shortUUID =shortUUID.substring(0,8) + "-" + shortUUID.substring(8,12) + "-" + shortUUID.substring(12,16) + "-" + shortUUID.substring(16,20) + "-" + shortUUID.substring(20, 24) + shortUUID.substring(24,shortUUID.length());
        return shortUUID;
    }
    public String lookupUUID(String username)
    {
        try {
            URL oracle = new URL("https://api.mojang.com/users/profiles/minecraft/" + username + "?at=" + System.currentTimeMillis());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            String fulltext = "";
            while ((inputLine = in.readLine()) != null)
                fulltext = fulltext + inputLine;
            in.close();
            //System.out.println(fulltext);

            return fulltext.substring(7, 39);
        }
        catch (Exception e)
        {
            return  "";
        }

    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (commandHelp(sender, command)) return true;
        votecounter++;
        if (commandPermissions(sender, command, args)) return true;

        if (commandGiveTitanBook(sender, command, args)) return true;

        if (fmtCrates(sender, command, args)) return true;

        if (commandBuyLand(sender, command)) return true;

        if (commandBossKeys(sender, command, args)) return true;

        if (commandBackups(sender, command, args)) return true;

        if (commandEmail(sender, command, args)) return true;

        if (commandSetvote(sender, command, args)) return true;

        if (commandFMSF(sender, command, args)) return true;

        if (commandLBT(sender, command)) return true;

        if (commandSetEvent(sender, command, args)) return true;

        if (commandSetTreasure(sender, command, args)) return true;

        if (commandSetTreasurePlacement(sender, command, args)) return true;

        if (commandStartEvent(sender, command, args)) return true;

        if (commandRandomHead(sender, command, args)) return true;

        return true;
    }
    public boolean commandRandomHead(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("rollheads")) {
            if (!(sender instanceof Player) && args.length > 1) {
                if (args[0].equalsIgnoreCase("all")) {
                    OfflinePlayer[] AllPlayers = Bukkit.getOfflinePlayers();
                    Random r = new Random(System.currentTimeMillis());
                    int headNumber = r.nextInt(AllPlayers.length);
                    String name = AllPlayers[headNumber].getName();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "headgive " + args[1] + " " + name);
                    return true;
                }
                if (args[0].equalsIgnoreCase("ban")) {
                    Set<OfflinePlayer> AllPlayers = Bukkit.getBannedPlayers();
                    Random r = new Random(System.currentTimeMillis());
                    int headNumber = r.nextInt(AllPlayers.size());
                    int i = 0;
                    for(OfflinePlayer oP: AllPlayers)
                    {
                        if (i >= headNumber)
                        {
                            String name = oP.getName();
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "headgive " + args[1] + " " + name);
                            return true;
                        }
                        i++;
                    }
                }
                if (args[0].equalsIgnoreCase("pick")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "headgive " + args[1] + " " + args[2]);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean commandStartEvent(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("startevent")) {
            if (!(sender instanceof Player) && args.length > 1) {
                if (FMTabList.eventList.contains(args[0]))
                {
                    long time = Long.parseLong(args[1]);
                    FMTabList.plugin.startEvent(args[0], time);
                    return true;
                }
            }
            else
            {
                System.out.println("Can't find event");
            }
        }
        return false;
    }
    public boolean commandSetTreasurePlacement(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("setplacement")) {
            if (!((Player) sender).hasPermission("FMTabList.setplacement")) {
                sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "You can't do that!");
                return true;
            }
            FMTabList.treasureplacementList.setValue(System.currentTimeMillis() + "", ((Player) sender).getLocation().clone());
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "Placement Saved!!!");
            return true;
        }
        return false;
    }
    public boolean commandLBT(CommandSender sender, Command command) {
        if (command.getName().equalsIgnoreCase("lbt")) {
            if (!(sender instanceof Player)) {
                this.lbtime = 1000 * 60 * 60;
            }
            else
            {
                if (((Player)sender).hasPermission("FMTabList.lbt"))
                {
                    this.lbtime = 1000 * 60 * 60;
                }

            }
            return true;
        }
        return false;
    }

    public boolean commandFMSF(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("fmsf") && args.length > 0) {

            //give freethemice 214 1 0 {display:{Name:"&fTitan Stone"},ench:[{id:34,lvl:10}]}
            if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("all")) {
                List<String> lore = new ArrayList<String>();

                    if (sender instanceof Player) {
                        if (((Player) sender).hasPermission("FMTabList.fmsf")) {
                            int empty = ((Player) sender).getInventory().firstEmpty();
                            if (empty > -1) {
                                if (args[0].equalsIgnoreCase("1")) {
                                    ((Player) sender).getInventory().setItem(empty, SFItems.TitanBookSoulbound);
                                }
                                if (args[0].equalsIgnoreCase("2")) {
                                    ((Player) sender).getInventory().setItem(empty, SFItems.TitanBookUnbreakable);
                                }

                                if (args[0].equalsIgnoreCase("3")) {
                                    ((Player) sender).getInventory().setItem(empty, SFItems.TitanBookUndroppable);
                                }

                                if (args[0].equalsIgnoreCase("all")) {
                                    ((Player) sender).getInventory().setItem(empty, SFItems.TitanBookAll);
                                }

                            }
                        }
                    }
            }
            if (args[0].equalsIgnoreCase("4"))
            {
                if (sender instanceof Player) {
                    if (((Player) sender).hasPermission("FMTabList.fmsf")) {
                        int empty = ((Player) sender).getInventory().firstEmpty();
                        if (empty > -1) {
                            ((Player) sender).getInventory().setItem(empty, SFItems.TitanStone.clone());
                        }
                    }
                }

            }
            return true;
        }
        return false;
    }
    public static void systemMessage(String Message)
    {
        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer
                .a("{\"text\":\"" +Message + ChatColor.WHITE + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(comp, ChatMessageType.GAME_INFO);
        for(Player p: Bukkit.getOnlinePlayers())
        {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public boolean commandSetTreasure(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("settreasure")) {
            if (args.length > 0) {
                if (sender instanceof Player) {
                    if (!((Player) sender).hasPermission("FMTabList.settreasure")) {
                        sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "You can't do that!");
                        return true;
                    }
                    Player player = (Player)sender;
                    ItemStack[] eventList =  player.getInventory().getContents();

                    for (int i = 0; i < eventList.length; i++) {
                        if (eventList[i] != null) {
                            FMTabList.treasureList.setValue(args[0] + "." + i, eventList[i]);
                        }
                    }
                    sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "Treasure Saved!!!");
                }

            }
        }
        return false;
    }
    public boolean commandSetEvent(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("setevent")) {
            if (args.length > 0) {
                if (sender instanceof Player) {
                    if (!((Player) sender).hasPermission("FMTabList.setevent")) {
                        sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "You can't do that!");
                        return true;
                    }
                    Player player = (Player)sender;
                    ItemStack[] eventList =  player.getInventory().getContents();

                    for (int i = 0; i < eventList.length; i++) {
                        if (eventList[i] != null) {
                            FMTabList.eventList.setValue(args[0] + "." + i, eventList[i]);
                        }
                    }
                    sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "Event Saved!!!");
                }

            }
        }
        return false;
    }
    public boolean commandSetvote(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("setvote")) {
            if (args.length > 0) {
                if (sender instanceof Player)
                {
                    if (!((Player) sender).hasPermission("FMTabList.setvote")) {
                        sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " +ChatColor.GREEN + "You can't do that!");
                        return true;
                    }
                }
                String voteQ = "";
                for (int i = 0;i<args.length;i++)
                {
                    voteQ= voteQ + args[i] + " ";
                }
                for(String key : FMTabList.plugin.voter.getKeys())
                {
                    FMTabList.plugin.voter.setProperty(key, null);
                }
                FMTabList.plugin.voter.save();

                FMTabList.plugin.voter.setProperty("voteyes", 0);
                FMTabList.plugin.voter.setProperty("voteno", 0);
                FMTabList.plugin.voter.setProperty("votequestion", voteQ);
                FMTabList.plugin.voter.save();
                FMTabList.plugin.vtime = 1000 *60 * 22;

            }
            return true;
        }
        return false;
    }

    public boolean commandEmail(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("emailout")) {
            if (args.length > 0) {
                String sendermail = "Server";
                if (sender instanceof Player)
                {
                    if (!((Player) sender).hasPermission("FMTabList.email")) {
                        sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " +ChatColor.GREEN + "You can't do that!");
                        return true;
                    }
                    sendermail = ((Player)sender).getDisplayName();
                }
                String Messageout = "";
                for (int i = 0; i < args.length; i++)
                {
                    Messageout = Messageout + args[i] + " ";
                }
                this.sendMail(sendermail, Messageout);
                sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " +ChatColor.GREEN + "email sent to server admin.");
            }
            return true;
        }
        return false;
    }

    public boolean commandBackups(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("backups")) {
            if (args.length == 0)
            {
                sender.sendMessage(ChatColor.RED + "/backups name");
                return true;
            }
            String subCommand = args[0];

            try {
                if (sender instanceof Player) {
                    if (!((Player) sender).hasPermission("FMTabList.email")) {
                        sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "You can't do that!");
                        return true;
                    }

                    backupGui.showPostMaster((Player) sender, subCommand);
                }
            }
            catch (Exception e)
            {
                System.out.println("error");
            }
            return true;
        }
        return false;
    }

    public boolean commandBossKeys(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("givewarpkey")) {
            if ((sender instanceof Player)) {
                if (!((Player) sender).hasPermission("FMTabList.email")) {
                    sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "You can't do that!");
                    return true;
                }
            }
            if (args.length > 0) {
                Player p = Bukkit.getPlayer(args[0]);
                int empty = p.getInventory().firstEmpty();
                if (empty > -1)
                {
                    ItemStack key = SFItems.WARPKEY.clone();
                    if (args.length > 2) {
                        key = this.mainListener.linkBossKey(p, args[2], key);
                    }
                    p.getInventory().setItem(empty, key);
                }

            }
            return true;
        }
        return false;
    }

    public Boolean commandBuyLand(CommandSender sender, Command command) {
        if (command.getName().equalsIgnoreCase("buyland")) {
            if ((sender instanceof Player)) {
                Player p = (Player)sender;


                Claim claim = GriefPrevention.instance.dataStore.getClaimAt(p.getLocation(), false, null);
                if (claim != null) {
                    if (!claim.isAdminClaim()) {
                        Long lastplayed = Bukkit.getOfflinePlayer(claim.ownerID).getLastPlayed();
                        Long OffLine = System.currentTimeMillis() - lastplayed;
                        int days = (int) (OffLine / (1000 * 60 * 60 * 24));
                        if (days >= 30) {
                            float price = 1000f / ((float) days / 30f);
                            price = Math.round(price * 100) / 100;
                            price = claim.getArea() * price;
                            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
                            if (rsp == null) {
                                return false;
                            }
                            Economy econ = rsp.getProvider();

                            if (econ.getBalance(p) > price && claim.ownerID != null)
                            {
                                OfflinePlayer owner = Bukkit.getOfflinePlayer(claim.ownerID);

                                econ.withdrawPlayer(p, price);
                                if (owner != null) {
                                    econ.depositPlayer(owner, price);
                                }
                                try {
                                    GriefPrevention.instance.dataStore.changeClaimOwner(claim, p.getUniqueId());
                                } catch (Exception e) {
                                    p.sendMessage(ChatColor.RED + "Oops, something went wrong get mice!!");
                                    return true;
                                }
                                p.sendMessage(ChatColor.GREEN + "The claim is now your!");
                            }
                            else
                            {
                                p.sendMessage(ChatColor.RED + "Not enough money!");
                            }
                        }
                        else
                        {
                            p.sendMessage(ChatColor.RED + "Land not for sale!");
                        }
                    }
                    else
                    {
                        if (claim.parent != null)
                        {
                            if (claim.managers.size() < 1) {
                                float price = claim.getArea() * 10000;
                                RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
                                if (rsp == null) {
                                    return false;
                                }
                                Economy econ = rsp.getProvider();

                                if (econ.getBalance(p) > price) {
                                    try {
                                        claim.managers.add(p.getUniqueId().toString());
                                        claim.setPermission(p.getUniqueId().toString(), ClaimPermission.Build);
                                        GriefPrevention.instance.dataStore.saveClaim(claim);
                                    } catch (Exception e) {
                                        p.sendMessage(ChatColor.RED + "Oops, something went wrong get mice!!");
                                        e.printStackTrace();
                                        return true;
                                    }
                                    p.sendMessage(ChatColor.GREEN + "The claim is now your!");
                                } else {
                                    p.sendMessage(ChatColor.RED + "Not enough money!");
                                }
                            }
                            else
                            {
                                p.sendMessage(ChatColor.RED + "Land not for sale!");
                            }
                        }
                        else
                        {
                            p.sendMessage(ChatColor.RED + "Land not for sale!");
                        }
                    }
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "Really? you going to buy the whole world? Sold for $1.7 Tillion. Nice try!");
                }
            }
            return true;
        }
        return false;
    }

    public boolean fmtCrates(CommandSender sender, Command command, String[] args) {
        if (args.length > 1 && command.getName().equalsIgnoreCase("fmtcrate")) {
            if (args[0].equalsIgnoreCase("open")) {
                if (args.length > 2) {
                    FMTabList.plugin.openCrateFor(args[1], Long.parseLong(args[2]));
                }
            }
            if (args[0].equalsIgnoreCase("show")) {
                cratesShow(args[1]);
            }
            if (args[0].equalsIgnoreCase("hide")) {
                cratesHide(args[1]);
            }
            if (args[0].equalsIgnoreCase("delete")) {
                if (FMTabList.plugin.timerBlockList.containsKey(args[1]) && FMTabList.plugin.timerBlockType.containsKey(args[1])) {
                    FMTabList.plugin.timerBlockList.put(args[1], null);
                    FMTabList.plugin.timerBlockType.put(args[1], null);
                    FMTabList.plugin.timerBlock.setValue(args[1], null);
                    FMTabList.plugin.timerBlock.setValue(args[1] + ".mat", null);
                }
            }
            if (args[0].equalsIgnoreCase("set")) {
                if ((sender instanceof Player)) {
                    Player p = (Player) sender;
                    Block b = p.getTargetBlock((Set<Material>) null, 10);
                    if (b != null & b.getType() != Material.AIR) {
                        FMTabList.plugin.timerBlockList.put(args[1], b.getLocation());
                        FMTabList.plugin.timerBlockType.put(args[1], b.getType());
                        FMTabList.plugin.timerBlock.setValue(args[1], b.getLocation());
                        FMTabList.plugin.timerBlock.setValue(args[1] + ".mat", b.getType().name());
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean commandGiveTitanBook(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("givetitanbook")) {
            if (!(sender instanceof Player) || sender.isOp()) {
                if (args.length > 0) {
                    Player p = Bukkit.getPlayer(args[0]);
                    if (p != null) {
                        if (p.getInventory() != null) {
                            Random nextSlot = new Random(System.currentTimeMillis() + votecounter * 100);
                            int whichBook = nextSlot.nextInt(SFItems.TitanBooksCE.size());
                            ItemStack toGive = SFItems.TitanBooksCE.get(whichBook).clone();
                            int empty = p.getInventory().firstEmpty();
                            if (empty > 0) {
                                p.getInventory().setItem(empty, toGive);
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean commandPermissions(CommandSender sender, Command command, String[] args) {
        // player perm amount
        if (command.getName().equalsIgnoreCase("fmtpermissions")) {
            if (!(sender instanceof Player) || sender.isOp()) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("add")) {
                        if (args.length > 2) {
                            String p = args[1];
                            String perms = args[2];
                            String[] ALlPerm = new String[args.length - 2];
                            for (int i = 2; i < args.length; i ++)
                            {
                                ALlPerm[i - 2] = args[i];
                            }
                            safeAddPermissions(p, ALlPerm);
                        }
                    }
                    if (args[0].equalsIgnoreCase("increase")) {
                        if (args.length > 3) {
                            try {
                                String p = args[1];
                                String perms = args[2];
                                int amount = Integer.parseInt(args[3]);
                                /*if (!perms.endsWith("."))
                                {
                                    perms = perms + ".";
                                }*/
                                int maxcount = 1;
                                PermissionUser pUser = PermissionsEx.getUser(p);

                                List<String> pList = pUser.getPermissions("");
                                List<String> oldtoRemove = new ArrayList<String>();
                                for (String t : pList) {
                                    if (t.toLowerCase().startsWith(perms.toLowerCase())) {
                                        oldtoRemove.add(perms);
                                        try {
                                            maxcount = Math.max(maxcount, Integer.parseInt(t.toLowerCase().replace(perms.toLowerCase(), "")));
                                        } catch (Exception e2) {
                                            maxcount = Math.max(maxcount, 1);
                                        }
                                    }
                                }

                                sender.sendMessage("Permission removed from " + maxcount);
                                for(String permtoRemove: oldtoRemove)
                                {
                                    pUser.removePermission(permtoRemove);
                                }

                                maxcount = maxcount + amount;
                                maxcount = Math.max(maxcount, 1);
                                pUser.addPermission(perms + maxcount);
                                sender.sendMessage("Permission add to " + maxcount);
                            } catch (Exception e) {
                                sender.sendMessage("Didn't understand that???");
                                System.out.println(e.getMessage());
                                System.out.println(e.toString());
                                e.printStackTrace();
                                return true;
                            }
                        }
                    }

                }
                return true;
            }
        }
        return false;
    }
    public void safeAddPermissions(String p, String[] perms) {
        for (int i = 0; i < perms.length;i++)
        {
            safeAddPermissions(p, perms[i]);
        }
    }
    public void safeAddPermissions(String p, String perms) {

        PermissionUser pUser = PermissionsEx.getUser(p);

        if (!pUser.has(perms))
        {
            System.out.println(p + " has been given: " + perms);
            pUser.addPermission(perms);
        }
        else
        {
            System.out.println(p + " already has: " + perms);
        }
    }

    public boolean commandHelp(CommandSender sender, Command command) {
        if (command.getName().equalsIgnoreCase("FMT")) {
            if ((sender instanceof Player)) {
                if (!((Player) sender).hasPermission("FMTabList.email")) {
                    sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "You can't do that!");
                    return true;
                }
            }
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "/givetitanbook");
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "/setbossspawn");
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "/givebosskey");
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "/backups");
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "/emailout");
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "/setvote");
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "/fmsf");
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "/lbt");
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "/fmtcrate");
            sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.DARK_RED + "FMT" + ChatColor.GOLD + "]: " + ChatColor.GREEN + "/fmtpermissions");


            return true;
        }
        return false;
    }

    public void cratesShow(String arg) {
        if (FMTabList.plugin.timerBlockList.containsKey(arg) && FMTabList.plugin.timerBlockType.containsKey(arg)) {
            Location loc = FMTabList.plugin.timerBlockList.get(arg);
            Material mat = FMTabList.plugin.timerBlockType.get(arg);
            loc.getWorld().getBlockAt(loc).setType(mat);
        }
    }

    public void cratesHide(String arg) {
        if (FMTabList.plugin.timerBlockList.containsKey(arg) && FMTabList.plugin.timerBlockType.containsKey(arg)) {
            Location loc = FMTabList.plugin.timerBlockList.get(arg);
            loc.getWorld().getBlockAt(loc).setType(Material.AIR);
        }
    }

    private void deleteBossSpawn(CommandSender sender, String bossName) {
        try {
            String world = (String) (FMTabList.plugin.bosskeys.getProperty(bossName + ".world"));
            int X = (int) (FMTabList.plugin.bosskeys.getProperty(bossName + ".x"));
            int Y = (int) (FMTabList.plugin.bosskeys.getProperty(bossName + ".y"));
            int Z = (int) (FMTabList.plugin.bosskeys.getProperty(bossName + ".z"));
            bosskeys.setProperty(X + "_" + Y + "_" + Z + "_" + world, null);
        }
        catch (Exception e)
        {

        }
        bosskeys.setProperty(bossName + ".name", null);
        bosskeys.setProperty(bossName + ".level", null);
        bosskeys.setProperty(bossName + ".world", null);
        bosskeys.setProperty(bossName + ".x", null);
        bosskeys.setProperty(bossName + ".y", null);
        bosskeys.setProperty(bossName + ".z", null);
        bosskeys.save();
    }
}