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

import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

/**
 * Helper class for accessing the user data saved in config.yml.
 * 
 * @author Eric Hildebrand
 */
public class UserRecords {
    
    private AdminTP plugin;
    
    /**
     * Construct with reference back to the AdminTP plugin instance
     * 
     * @param plugin
     *            the AdminTP plugin instance
     */
    public UserRecords(AdminTP plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Null the AdminTP plugin instance at shutdown
     */
    public void close() {
        plugin = null;
    }
    
    /**
     * Parse a serialized Location back into Location form. Handles worlds that
     * have spaces in their names without any issue.
     * 
     * @param location
     *            String representing the Location
     * @return the parsed Location
     * @throws InvalidLocationException
     */
    public Location deserializeLocation(String location)
            throws InvalidLocationException {
        String[] args = location.split(" ", 6);
        double x, y, z;
        float yaw, pitch;
        World world;
        try {
            x = Double.parseDouble(args[0]);
            y = Double.parseDouble(args[1]);
            z = Double.parseDouble(args[2]);
            yaw = Float.parseFloat(args[3]);
            pitch = Float.parseFloat(args[4]);
            /* TODO: Utilize getWorld(UUID) if/when Factions/Essentials does */
            world = plugin.getServer().getWorld(args[5]);
        } catch (NumberFormatException e) {
            throw new InvalidLocationException("Failed to parse: " + location);
        }
        if (world == null) {
            throw new InvalidLocationException("Invalid world: " + args[5]);
        }
        return new Location(world, x, y, z, yaw, pitch);
    }
    
    /**
     * @param player
     *            Player to get location for
     * @return Location of the player's most recent death
     */
    public Location getLastDeathLocation(OfflinePlayer player) {
        return getSavedLocation(player, LocationType.DEATH);
    }
    
    /**
     * @param player
     *            Player to get location for
     * @return Location of the player's most recent login
     */
    public Location getLastLoginLocation(OfflinePlayer player) {
        return getSavedLocation(player, LocationType.LOGIN);
    }
    
    /**
     * @param player
     *            Player to get location for
     * @return Location of the player's most recent logout
     */
    public Location getLastLogoutLocation(OfflinePlayer player) {
        return getSavedLocation(player, LocationType.LOGOUT);
    }
    
    /**
     * @param player
     *            Player to get location for
     * @param type
     *            Type of location being requested
     * @return Location for the player and type
     */
    private Location getSavedLocation(OfflinePlayer player, LocationType type) {
        if (!type.isSaved()) {
            return null;
        }
        try {
            if (plugin.getConfig().contains(
                    player.getUniqueId() + type.getNode())) {
                return deserializeLocation(plugin.getConfig().getString(
                        player.getUniqueId() + type.getNode()));
            }
        } catch (InvalidLocationException e) {
            plugin.fancyLog(Level.WARNING,
                    "Error getting location" + type.getNode() + " for "
                            + player.getName() + "\n" + e.getReason());
        }
        return null;
    }
    
    /**
     * Convert a Location into a String for serialization and use in records.
     * Handles worlds that have spaces in their names without any issue.
     * 
     * @param location
     *            Location to serialize
     * @return Serialized location string
     */
    public String serializeLocation(Location location) {
        /*
         * Efficient, as Java internally uses StringBuilder for this expression.
         * TODO: Utilize getWorld(UUID) if/when Factions/Essentials do
         */
        return location.getX() + " " + location.getY() + " " + location.getZ()
                + " " + location.getYaw() + " " + location.getPitch() + " "
                + location.getWorld().getName();
    }
    
    /**
     * @param player
     *            Player to set location for
     * @param location
     *            Location of the player's most recent death
     */
    public void setLastDeathLocation(OfflinePlayer player, Location location) {
        setSavedLocation(player, LocationType.DEATH, location);
    }
    
    /**
     * @param player
     *            Player to set location for
     * @param location
     *            Location of the player's most recent login
     */
    public void setLastLoginLocation(OfflinePlayer player, Location location) {
        setSavedLocation(player, LocationType.LOGIN, location);
    }
    
    /**
     * @param player
     *            Player to set location for
     * @param location
     *            Location of the player's most recent logout
     */
    public void setLastLogoutLocation(OfflinePlayer player, Location location) {
        setSavedLocation(player, LocationType.LOGOUT, location);
    }
    
    /**
     * @param player
     *            player Player to set location for
     * @param type
     *            Type of location being set
     * @param location
     *            Location being set
     */
    private void setSavedLocation(OfflinePlayer player, LocationType type,
            Location location) {
        if (type.isSaved()) {
            plugin.getConfig().set(player.getUniqueId() + type.getNode(),
                    serializeLocation(location));
        }
    }
    
}
