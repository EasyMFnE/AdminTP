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

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

/**
 * The class that handles the "/admintp" command for the plugin.
 * 
 * @author Eric Hildebrand
 */
public class AtpCommand implements CommandExecutor {
    
    /* Preset colors for uniformity of message formatting */
    private final String color1 = ChatColor.GOLD.toString();
    private final String color2 = ChatColor.YELLOW.toString();
    private final String color3 = ChatColor.WHITE.toString();
    
    private AdminTP plugin = null;
    
    /**
     * Instantiate by getting a reference to the plugin instance and registering
     * this class to handle the '/admintp' command.
     * 
     * @param plugin
     *            Reference to AdminTP plugin instance
     */
    public AtpCommand(AdminTP plugin) {
        this.plugin = plugin;
        plugin.getCommand("admintp").setExecutor(this);
    }
    
    /**
     * Release the '/admintp' command from its ties to this class.
     */
    public void close() {
        plugin.getCommand("admintp").setExecutor(null);
    }
    
    /**
     * This method handles user commands. Usage: "/admintp"
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (args.length > 0) {
            OfflinePlayer player = parseOfflinePlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "User not found");
                return true;
            }
            
            if (args.length == 1) {
                showPlayerHomes(sender, player);
                return true;
            }
            
            if (sender instanceof Player) {
                Player user = (Player) sender;
                /* "/atp <player> bed" */
                if (args[1].equalsIgnoreCase("bed")) {
                    teleport(user, player.getBedSpawnLocation());
                }
                /* "/atp <player> death" */
                else if (args[1].equalsIgnoreCase("death")) {
                    teleport(user, plugin.getUserRecords()
                            .getLastDeathLocation(player));
                }
                /* "/atp <player> fac[tion]" */
                else if (Util.hookedFactions()
                        && args[1].toLowerCase().startsWith("fac")) {
                    teleport(user, Util.getFactionsHome(player));
                }
                /* "/atp <player> ess[entials] [home]" */
                else if (Util.hookedEssentials()
                        && args[1].toLowerCase().startsWith("ess")) {
                    if (args.length == 2) {
                        teleport(user, Util.getEssentialsHome(player));
                    } else if (Util.getEssentialsHomes(player)
                            .contains(args[2])) {
                        teleport(user, Util.getEssentialsHome(player, args[2]));
                    } else {
                        sender.sendMessage(ChatColor.RED + "Home '" + args[2]
                                + "' not found");
                    }
                }
                /* "/atp <player> login" */
                else if (args[1].equalsIgnoreCase("login")) {
                    teleport(user, plugin.getUserRecords()
                            .getLastLoginLocation(player));
                }
                /* "/atp <player> logout" */
                else if (args[1].equalsIgnoreCase("logout")) {
                    teleport(user, plugin.getUserRecords()
                            .getLastLogoutLocation(player));
                } else {
                    sender.sendMessage(ChatColor.RED
                            + "Unrecognized home type: " + args[1] + "!");
                }
                return true;
            }
        }
        
        showUsage(sender);
        return true;
    }
    
    /**
     * Checks name against all players who have joined the server. If no case-
     * insensitive match is found, return null.
     * 
     * TODO: Update to mirror changes in Bukkit as 1.8 nears/arrives.
     * 
     * @param name
     *            Name to search by
     * @return OfflinePlayer found, or null
     */
    private OfflinePlayer parseOfflinePlayer(String name) {
        for (OfflinePlayer player : plugin.getServer().getOfflinePlayers()) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }
    
    /**
     * Display a full view of all of a player's home locations.
     * 
     * @param sender
     *            User to show homes
     * @param player
     *            Player to look up homes for
     */
    private void showPlayerHomes(CommandSender sender, OfflinePlayer player) {
        sender.sendMessage(color1 + "Locations for " + player.getName() + ":");
        /* bed */
        if (player.getBedSpawnLocation() != null) {
            sender.sendMessage(color2 + "Bed: " + color3
                    + Util.toNiceString(player.getBedSpawnLocation()));
        }
        /* death */
        if (plugin.getUserRecords().getLastDeathLocation(player) != null) {
            sender.sendMessage(color2 + "Death: " + color3
                    + Util.toNiceString(plugin.getUserRecords()
                            .getLastDeathLocation(player)));
        }
        /* faction */
        if (Util.hookedFactions() && Util.getFactionsHome(player) != null) {
            sender.sendMessage(color2 + "Faction Home: " + color3
                    + Util.toNiceString(Util.getFactionsHome(player)));
        }
        /* essentials */
        if (Util.hookedEssentials()) {
            for (String home : Util.getEssentialsHomes(player)) {
                try {
                    sender.sendMessage(color2 + "Home '" + color3 + home 
                            + color2 + "': " + color3
                            + Util.toNiceString(Util.getEssentialsHome(player,
                                    home)));
                } catch (Exception e) {
                    sender.sendMessage(color2 + "Home '" + color3 + home
                            + color2 + "': " + color3 + "N/A");
                }
            }
        }
        /* login */
        if (plugin.getUserRecords().getLastLoginLocation(player) != null) {
            sender.sendMessage(color2 + "Last Login: " + color3
                    + Util.toNiceString(plugin.getUserRecords()
                            .getLastLoginLocation(player)));
        }
        /* logout */
        if (plugin.getUserRecords().getLastLogoutLocation(player) != null) {
            sender.sendMessage(color2 + "Last Logout: " + color3
                    + Util.toNiceString(plugin.getUserRecords()
                            .getLastLogoutLocation(player)));
        }
    }
    
    /**
     * Show usage information, tailored to match the available functionality
     * depending on whether the sender is a Player or not.
     * 
     * @param sender
     *            User to show usage
     */
    private void showUsage(CommandSender sender) {
        sender.sendMessage("usage:");
        sender.sendMessage(" /admintp <player> - Show a player's location info");
        if (!(sender instanceof Player)) {
            return;
        }
        sender.sendMessage(" /admintp <player> bed - " 
                + "Teleport to a player's last used bed");
        sender.sendMessage(" /admintp <player> death - " 
                + "Teleport to a player's last place of death");
        if (Util.hookedFactions()) {
            sender.sendMessage(" /admintp <player> fac[tion] - " 
                    + "Teleport to a player's Faction home");
        }
        if (Util.hookedEssentials()) {
            sender.sendMessage(" /admintp <player> ess[entials] [home_name] - " 
                    + "Teleport to an Essentials home");
        }
        sender.sendMessage(" /admintp <player> login - " 
                + "Teleport to player's last login location");
        sender.sendMessage(" /admintp <player> logout - " 
                + "Teleport to player's last logout location");
    }
    
    /**
     * Attempt to teleport a player to a location, checking for null location
     * and teleportation failures and notifying the player if detected.
     * 
     * @param player
     *            Player to teleport
     * @param location
     *            Destination
     */
    private void teleport(Player player, Location location) {
        if (location == null) {
            player.sendMessage(ChatColor.RED + "Location does not exist!");
        }
        if (!player.teleport(location, TeleportCause.PLUGIN)) {
            player.sendMessage(ChatColor.RED + "Teleporation failed!");
        }
    }
    
}
