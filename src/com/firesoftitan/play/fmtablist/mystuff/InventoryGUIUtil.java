package com.firesoftitan.play.fmtablist.mystuff;

import com.firesoftitan.play.fmtablist.FMTabList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Daniel on 9/18/2017.
 */
public class InventoryGUIUtil {
    public String nameInv;
    public InventoryGUIUtil(String _nameInv) {
        nameInv = _nameInv;

    }
    public boolean InvEvent(Inventory checkInv, Player player, int RawSlot) {
        if (checkInv.getName().startsWith("Inv: ")) {
            try {
                if (RawSlot >= 45 && RawSlot <= 53) {
                    if (RawSlot == 45) {
                        List<String> lore = checkInv.getItem(45).getItemMeta().getLore();
                        showPostMaster(player, 0, lore.get(lore.size() - 2));
                        //page Back
                    }
                    if (RawSlot == 53) {
                        List<String> lore = checkInv.getItem(53).getItemMeta().getLore();
                        Player playerRe = Bukkit.getPlayer(lore.get(lore.size() - 2));
                        if (playerRe != null) {
                            FMTabList.plugin.restoreBackup(playerRe, lore.get(lore.size() - 1));
                            player.closeInventory();
                            player.sendMessage(ChatColor.RED + "Command Ran!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Player must be online!");
                        }

                    }
                    return true;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                return true;
            }
        }
        if (checkInv.getName().startsWith("Backups, Page: ")) {
            try {
                int page = Integer.parseInt(checkInv.getName().replace("Backups, Page: ", ""));
                if (RawSlot >= 45 && RawSlot <= 53) {
                    if (RawSlot == 45) {
                        List<String> lore = checkInv.getItem(45).getItemMeta().getLore();
                        page--;
                        page = Math.max(1, page);
                        page = page - 1;
                        page = page * 45;
                        showPostMaster(player, page, lore.get(lore.size() - 2));
                        //page Back
                    }
                    if (RawSlot == 53) {
                        List<String> lore = checkInv.getItem(53).getItemMeta().getLore();
                        page++;
                        page = page - 1;
                        page = page * 45;
                        showPostMaster(player, page, lore.get(lore.size() - 2));
                        //Page Next
                    }

                } else {
                    List<String> lore = checkInv.getItem(RawSlot).getItemMeta().getLore();
                    Player playerBackup = FMTabList.plugin.loadPlayer(Bukkit.getOfflinePlayer(lore.get(lore.size() - 2)));
                    if (playerBackup != null) {
                        Player toOpen = FMTabList.plugin.restoreBackup("farthead", playerBackup, lore.get(lore.size() - 1));
                        List<ItemStack> inv = new ArrayList<ItemStack>();
                        Inventory mainInv = toOpen.getInventory();
                        for (int i = 0; i < mainInv.getSize(); i++) {
                            if (mainInv.getItem(i) != null) {
                                inv.add(mainInv.getItem(i).clone());
                            }
                            else
                            {
                                inv.add(new ItemStack(Material.AIR));
                            }
                        }
                        showPlayerInv(player, 0, inv, playerBackup.getName(), lore.get(lore.size() - 1));
                        //player.openInventory(toOpen.getInventory());
                    } else {
                        player.sendMessage(ChatColor.RED + "Error loading Player!");
                    }

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                //Even if theres an error we will cancel the click.
                return true;
            }

        }
        return false;
    }

    public ItemStack changeName(ItemStack toAdd, String Name) {
        ItemMeta IM = toAdd.getItemMeta();
        IM.setDisplayName(Name);
        toAdd.setItemMeta(IM);
        return toAdd.clone();
    }
    public void showPostMaster(Player sender, String nameLook) {
        showPostMaster(sender, 0, nameLook);
    }
    public void showPostMaster(Player sender, int StartAt, String nameLook) {
        TreeMap<String, String> GuiList = FMTabList.plugin.makeList(nameLook);
        int page = (StartAt/ 45) + 1;
        Inventory inv = Bukkit.createInventory(null, 54, "Backups, Page: " + page);
        int i = 0;
        int where = 0;
        for (String tmp : GuiList.keySet()) {
            where++;
            if (where > StartAt) {
                String pack = GuiList.get(tmp);
                ItemStack packB = new ItemStack(Material.RED_SHULKER_BOX);
                ItemMeta ITM = packB.getItemMeta();
                List<String> lore = ITM.getLore();
                if (lore == null) {
                    lore = new ArrayList<String>();
                }
                lore.add("Date: " + pack);
                lore.add(nameLook);
                lore.add(tmp);
                ITM.setLore(lore);
                packB.setItemMeta(ITM);
                inv.setItem(i, packB);
                i++;
                if (i >= 54 - 9) {
                    break;
                }
            }
        }
        ItemStack buttonN = changeName(new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA), "NEXT");
        ItemMeta ITM = buttonN.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(nameLook);
        lore.add("----");
        ITM.setLore(lore);
        buttonN.setItemMeta(ITM.clone());
        ItemStack buttonB =  changeName(buttonN.clone(), "BACK");

        inv.setItem(45, buttonB);
        for (int j = 46; j < 53; j++) {
            inv.setItem(j, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15));
        }
        inv.setItem(53,  buttonN);

        sender.openInventory(inv);
    }
    public void showPlayerInv(Player sender, int StartAt, List<ItemStack> totalList, String showings, String backupNumber) {
        int page = (StartAt/ 45) + 1;
        Inventory inv = Bukkit.createInventory(null, 54, "Inv: " + showings);
        int i = 0;
        int where = 0;
        for (ItemStack tmp : totalList) {
            where++;
            if (where > StartAt) {
                ItemStack packB = tmp.clone();
                inv.setItem(i, packB);
                i++;
                if (i >= 54 - 9) {
                    break;
                }
            }
        }
        ItemStack buttonN = changeName(new ItemStack(Material.ANVIL), "RESTORE");
        ItemMeta ITM = buttonN.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(showings);
        lore.add(backupNumber);
        ITM.setLore(lore);
        buttonN.setItemMeta(ITM.clone());
        ItemStack buttonB =  changeName(buttonN.clone(), "BACK");
        buttonB.setType(Material.MAGENTA_GLAZED_TERRACOTTA);


        inv.setItem(45, buttonB);
        for (int j = 46; j < 53; j++) {
            inv.setItem(j, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15));
        }
        inv.setItem(53,  buttonN);

        sender.openInventory(inv);
    }
}