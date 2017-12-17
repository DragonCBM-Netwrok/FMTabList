package com.firesoftitan.play.fmtablist.mystuff;

import com.google.common.base.Charsets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomConfiguration {
	
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	private JavaPlugin plugin;
	public CustomConfiguration(JavaPlugin Plugin, File cConfigFile)
	{
		this.plugin = Plugin;
		this.customConfigFile = cConfigFile;
		
	}
	
	public void load() {
	    customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = plugin.getResource(this.customConfigFile.getName());
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
	        customConfig.setDefaults(defConfig);
	    }
	}
	public FileConfiguration getCustomConfig() {
	    if (customConfig == null) {
	    	load();
	    }
	    return customConfig;
	}
	public boolean save()
	{
	   if (customConfig == null || customConfigFile == null) {
	       return false;
	    }
	    try {
	       getCustomConfig().save(customConfigFile);
	       return true;
	    } catch (Exception ex) {		        
	       return false;
	        
	    } 
	}
	public Object getProperty(String path)
	{
	  return this.getCustomConfig().get(path);
	}
	
	public Set<String> getKeys()
	{
		return (Set<String>) this.getCustomConfig().getKeys(true);	  
	}	
	public List<String> getKeys(String path)
	{
		Set<String> tmpAllKeys = this.getKeys();
		List<String> outKeys = new ArrayList<String>();
		for (String key : tmpAllKeys)
		{
			if (key.startsWith(path) == true)
			{
				outKeys.add(key);
			}
		}
		return outKeys;
	}
	public void setProperty(String path, Object value)
	{
	String[] subPaths = path.split("\\.");
	if (subPaths.length > 0)
	{			  
		  String CompletPath = subPaths[0];
		  for (int i = 1; i< subPaths.length; i++)
		  {
			  
			  if (this.getCustomConfig().contains(CompletPath) == false)
			  {								  
				  this.setProperty(CompletPath, subPaths[i]);
				  this.save();
			  }
			  CompletPath = CompletPath + "."  + subPaths[i];
		  }
		  path = CompletPath;
	}		
	this.getCustomConfig().set(path, value);
	this.save();
	}
	public boolean getBoolean(String path, boolean def)
	{		
		return this.getCustomConfig().getBoolean(path, def);
	}
	@SuppressWarnings("unchecked")
	public List<String> getList(String path)
	{
		return (List<String>) this.getCustomConfig().getList(path);
	}
	public void removeProperty(String path)
	{
		this.getCustomConfig().set(path, null);
		this.save();
	}
	
}
