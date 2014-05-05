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

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * The class that monitors and reacts to server events.
 * 
 * @author Eric Hildebrand
 */
public class PlayerListener implements Listener {
    
    private AdminTP plugin = null;
    
    /**
     * Instantiate by getting a reference to the plugin instance and registering
     * each of the defined EventHandlers.
     * 
     * @param plugin
     *            Reference to AdminTP plugin instance
     */
    public PlayerListener(AdminTP plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    /**
     * Unregister all registered EventHandlers, preventing further reactions.
     */
    public void close() {
        HandlerList.unregisterAll(this);
    }
    
    /**
     * Upon player death, save their location for later reference.
     * 
     * @param event
     *            Player's death event
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        plugin.getUserRecords().setLastDeathLocation(event.getEntity(),
                event.getEntity().getLocation());
    }
    
    /**
     * Upon player login, save their location for later reference.
     * 
     * @param event
     *            Player's join event
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getUserRecords().setLastLoginLocation(event.getPlayer(),
                event.getPlayer().getLocation());
    }
    
    /**
     * Upon player logout, save their location for later reference.
     * 
     * @param event
     *            Player's quit event
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getUserRecords().setLastLogoutLocation(event.getPlayer(),
                event.getPlayer().getLocation());
    }
    
}
