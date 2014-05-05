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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.UPlayer;

/**
 * Utility class with static helper methods for detecting and interacting with
 * Essentials and Factions plugins.
 * 
 * @author Eric Hildebrand
 */
public class Util {
    
    private static Essentials essentials;
    private static Factions factions;
    
    /**
     * Null plugin references at shutdown.
     */
    protected static void close() {
        essentials = null;
        factions = null;
    }
    
    /**
     * @return Instance of Essentials plugin, if it exists
     */
    protected static Essentials getEssentials() {
        return essentials;
    }
    
    /**
     * Fetch default Essentials home for a player. Returns null if not found.
     * 
     * @param player
     *            Player to look up
     * @return Location of the home, or null if not found
     */
    protected static Location getEssentialsHome(OfflinePlayer player) {
        if (!hookedEssentials()) {
            return null;
        }
        try {
            return getEssentialsUser(player).getHome(
                    getEssentialsUser(player).getHomes().get(0));
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Fetch a specific Essentials home for a player. Returns null if not found.
     * 
     * @param player
     *            Player to look up
     * @param home
     *            Home name to look up
     * @return Location of the home, or null if not found
     */
    protected static Location getEssentialsHome(OfflinePlayer player,
            String home) {
        if (!hookedEssentials()) {
            return null;
        }
        try {
            return getEssentialsUser(player).getHome(home);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Fetch a list of Essentials homes for a player. Does not return null.
     * 
     * @param player
     *            Player to look up
     * @return List of player's home names
     */
    protected static List<String> getEssentialsHomes(OfflinePlayer player) {
        if (!hookedEssentials()) {
            return new ArrayList<String>();
        }
        try {
            return getEssentialsUser(player).getHomes();
        } catch (NullPointerException e) {
            return new ArrayList<String>();
        }
    }
    
    /**
     * Fetch a User object from Essentials based on the OfflinePlayer. Attempts
     * to fetch via UUID, but falls back to player name if running an older an
     * older version of Essentials that does not support UUID lookup.
     * 
     * NOTE: Part of the preparations for Minecraft 1.8.
     * 
     * @param player
     *            Player to look up
     * @return Player's User object from Essentials
     */
    protected static User getEssentialsUser(OfflinePlayer player) {
        if (!hookedEssentials()) {
            return null;
        }
        try {
            return getEssentials().getUser(player.getUniqueId());
        } catch (NoSuchMethodError e) {
            return getEssentials().getUser(player.getName());
        }
    }
    
    /**
     * @return Instance of Factions plugin, if it exists
     */
    protected static Factions getFactions() {
        return factions;
    }
    
    /**
     * Fetch the home of the player's Faction. Returns null when the player is
     * not a member of any faction or when their faction has no defined home.
     * 
     * @param player
     *            Player to look up
     * @return Location of the home, or null if not found.
     */
    protected static Location getFactionsHome(OfflinePlayer player) {
        if (!hookedFactions()) {
            return null;
        }
        try {
            return UPlayer.get(player).getFaction().getHome()
                    .asBukkitLocation();
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    /**
     * @return Whether Essentials has been detected and hooked
     */
    protected static boolean hookedEssentials() {
        return essentials != null;
    }
    
    /**
     * @return Whether Factions has been detected and hooked
     */
    protected static boolean hookedFactions() {
        return factions != null;
    }
    
    /**
     * Initialize and detect existence of Essentials and Factions plugins.
     * 
     * @param plugin
     *            Reference to AdminTP plugin instance
     */
    protected static void init(AdminTP plugin) {
        Plugin essentials = plugin.getServer().getPluginManager()
                .getPlugin("Essentials");
        Plugin factions = plugin.getServer().getPluginManager()
                .getPlugin("Factions");
        if (essentials instanceof Essentials) {
            Util.essentials = (Essentials) essentials;
        }
        if (factions instanceof Factions) {
            Util.factions = (Factions) factions;
        }
    }
    
    /**
     * Format a Location into a String suitable for displaying in chat. Format:
     * "worldname (x, y, z)"
     * 
     * @param location
     *            Location to display
     * @return Nicely formatted String
     */
    protected static String toNiceString(Location location) {
        return location.getWorld().getName() + "(" + location.getBlockX() + ","
                + location.getBlockY() + "," + location.getBlockZ() + ")";
    }
}
