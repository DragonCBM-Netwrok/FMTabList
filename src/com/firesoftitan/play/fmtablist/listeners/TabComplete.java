package com.firesoftitan.play.fmtablist.listeners;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daniel on 12/2/2016.
 */
public class TabComplete implements TabCompleter
{
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List tabs = Lists.newLinkedList();

        if (args.length == 1) {
            tabs.addAll(Arrays.asList(new String[] { "enable", "disable", "load", "info", "reload", "list" }));
            return tabs;
        }

        if ((args[0].equalsIgnoreCase("enable")) || (args[0].equalsIgnoreCase("disable")) || (args[0].equalsIgnoreCase("reload")) || (args[0].equalsIgnoreCase("info"))) {
            for (Plugin pl : Bukkit.getPluginManager().getPlugins()) {
                tabs.add(pl.getName());
            }
            return tabs;
        }

        if (args[0].equalsIgnoreCase("load")) {
            for (File file : new File("plugins").listFiles()) {
                if (file.getName().endsWith(".jar")) {
                    tabs.add(file.getName());
                }
            }
            return tabs;
        }
        return tabs;
    }
}

