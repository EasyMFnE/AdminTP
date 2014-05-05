/*
 * This file is part of the AdminTP plugin by EasyMFnE.
 * 
 * AdminTP is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 * 
 * AdminTP is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for
 * a particular purpose. See the GNU General Public License for details.
 * 
 * You should have received a copy of the GNU General Public License v3 along
 * with AdminTP. If not, see <http://www.gnu.org/licenses/>.
 */
package net.easymfne.admintp;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mcstats.MetricsLite;

/**
 * Main plugin class, responsible for its own setup, logging, reloading, and
 * shutdown operations. Maintains instances of ConfigHelper, AtpCommand,
 * PlayerListener, and UserRecords.
 * 
 * @author Eric Hildebrand
 */
public class AdminTP extends JavaPlugin {
    
    /**
     * Private class for periodically saving config.yml (user records)
     * 
     * @author Eric Hildebrand
     */
    private class AutoSave extends BukkitRunnable {
        @Override
        public void run() {
            saveConfig();
        }
    }
    
    /* Instance references */
    private UserRecords userRecords = null;
    private AtpCommand atpCommand = null;
    private PlayerListener playerListener = null;
    
    /* AutoSave's running task and period */
    private BukkitTask autoSaveTask = null;
    private final int autoSavePeriod = 6000;
    
    /* Strings for fancyLog() methods */
    private final String logPrefix = ChatColor.GOLD + "[AdminTP] ";
    private final String logColor = ChatColor.YELLOW.toString();
    
    /**
     * Log a message to the console using color, with a specific logging Level.
     * If there is no console open, log the message without any coloration.
     * 
     * @param level
     *            Level at which the message should be logged
     * @param message
     *            The message to be logged
     */
    protected void fancyLog(Level level, String message) {
        if (getServer().getConsoleSender() != null) {
            getServer().getConsoleSender().sendMessage(
                    logPrefix + logColor + message);
        } else {
            getServer().getLogger().log(level,
                    ChatColor.stripColor(logPrefix + message));
        }
    }
    
    /**
     * Log a message to the console using color, defaulting to the Info level.
     * If there is no console open, log the message without any coloration.
     * 
     * @param message
     *            The message to be logged
     */
    protected void fancyLog(String message) {
        fancyLog(Level.INFO, message);
    }
    
    /**
     * @return the saved user records instance
     */
    public UserRecords getUserRecords() {
        return userRecords;
    }
    
    /**
     * Close all event handlers and command listeners, then null instances to
     * mark them for garbage collection. Displays elapsed time to console when
     * finished.
     */
    @Override
    public void onDisable() {
        long start = Calendar.getInstance().getTimeInMillis();
        fancyLog("=== DISABLE START ===");
        autoSaveTask.cancel();
        playerListener.close();
        playerListener = null;
        atpCommand.close();
        atpCommand = null;
        userRecords.close();
        userRecords = null;
        Util.close();
        saveConfig();
        fancyLog("=== DISABLE COMPLETE ("
                + (Calendar.getInstance().getTimeInMillis() - start)
                + "ms) ===");
    }
    
    /**
     * Set up the plugin by: loading config.yml (creating from default if not
     * existent), then instantiating its own UserRecords, AtpCommand, and
     * PlayerListener objects. Displays elapsed time to console when finished.
     */
    @Override
    public void onEnable() {
        long start = Calendar.getInstance().getTimeInMillis();
        fancyLog("=== ENABLE START ===");
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig();
            fancyLog("Saved default config.yml");
        }
        
        Util.init(this);
        userRecords = new UserRecords(this);
        atpCommand = new AtpCommand(this);
        playerListener = new PlayerListener(this);
        startMetrics();
        autoSaveTask = getServer().getScheduler().runTaskTimer(this,
                new AutoSave(), autoSavePeriod, autoSavePeriod);
        fancyLog("=== ENABLE COMPLETE ("
                + (Calendar.getInstance().getTimeInMillis() - start)
                + "ms) ===");
    }
    
    /**
     * Reload the configuration from disk and perform any necessary functions.
     * Displays elapsed time to console when finished.
     */
    public void reload() {
        long start = Calendar.getInstance().getTimeInMillis();
        fancyLog("=== RELOAD START ===");
        reloadConfig();
        fancyLog("=== RELOAD COMPLETE ("
                + (Calendar.getInstance().getTimeInMillis() - start)
                + "ms) ===");
    }
    
    /**
     * If possible, instantiate Metrics and connect with mcstats.org
     */
    private void startMetrics() {
        MetricsLite metrics;
        try {
            metrics = new MetricsLite(this);
            if (metrics.start()) {
                fancyLog("Metrics enabled.");
            }
        } catch (IOException e) {
            fancyLog(Level.WARNING, "Metrics exception: " + e.getMessage());
        }
    }
    
}
