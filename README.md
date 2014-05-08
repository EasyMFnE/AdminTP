<center>![AdminTP](http://www.easymfne.net/images/admintp.png)</center>

<center>[Source](https://github.com/EasyMFnE/AdminTP) |
[Change Log](https://github.com/EasyMFnE/AdminTP/blob/master/CHANGES.log) |
[Feature Request](https://github.com/EasyMFnE/AdminTP/issues) |
[Bug Report](https://github.com/EasyMFnE/AdminTP/issues) |
[Donate](https://www.paypal.com/cgi-bin/webscr?hosted_button_id=457RX2KYUDY5G&item_name=AdminTP&cmd=_s-xclick)</center>

<center>**Latest Release:** v1.0 for Bukkit 1.7+</center>

## About ##

AdminTP is a small plugin with the goal of making it easier to administer servers by allowing administrators to teleport to users' personal homes, and several other locations.  This plugin integrates with [Essentials](http://dev.bukkit.org/bukkit-plugins/essentials/) and [Factions v2.0+](http://dev.bukkit.org/bukkit-plugins/factions/), but can also be used by itself.  This plugin is under continuing development, and will be updated to integrate with other plugins as the developer monitors all feedback and bug reports.

## Features ##

Administrators can teleport to the following locations:

* Player's most recent bed  
* Player's most recent death location  
* Player's Faction home (requires [Factions v2.0+](http://dev.bukkit.org/bukkit-plugins/factions/))  
* Player's Essentials homes (requires [Essentials](http://dev.bukkit.org/bukkit-plugins/essentials/))  
* Player's most recent log-in location  
* Player's most recent log-out location  

**AdminTP is fully UUID-compatible.**

## Installation ##

1. Download AdminTP jar file.
2. Move/copy to your server's `plugins` folder.
3. Restart your server.
4. [**Optional**] Grant specific user permissions (see below).

## Permissions ##

AdminTP has only one permission node:

* `admintp.use` - Allow a player to use the plugin and teleport to player locations. (Default: `op`)

## Commands ##

AdminTP has only one command, `/admintp` (Alias: `/atp`)

* `/admintp` - Show plugin help  
* `/admintp <player>` - Show a player's information  
* `/admintp <player> <bed|death|login|logout>` - Teleport to the relevant location  
* `/admintp <player> ess[entials] [home_name]` - Teleport to a player's Essentials home  
* `/admintp <player> fac[tion]` - Teleport to a player's faction home    

## Configuration ##

This plugin requires no configuration.  Player locations are saved in `plugins/AdminTP/config.yml` but it is not advised to manually edit the file, especially while the server is running.


## Bugs/Requests ##

This plugin is continually tested to ensure that it is performing correctly, but sometimes bugs can sneak in.  If you have found a bug with the plugin, or if you have a feature request, please [create an issue on Github](https://github.com/EasyMFnE/AdminTP/issues).

## Donations ##

Donating is a great way to thank the developer if you find the plugin useful for your server, and encourages work on more 100% free and open-source plugins.  If you would like to donate (any amount), there is an easily accessible link in the top right corner of this page.  Thank you!

## Privacy ##

This plugin utilizes Hidendra's **Plugin-Metrics** system.  You may opt out of this service by editing your configuration located in `plugins/Plugin Metrics`.  The following anonymous data is collected and sent to [mcstats.org](http://mcstats.org):

* A unique identifier
* The server's version of Java
* Whether the server is in online or offline mode
* The plugin's version
* The server's version
* The OS version, name, and architecture
* The number of CPU cores
* The number of online players
* The Metrics version

## License ##

This plugin is released as a free and open-source project under the [GNU General Public License version 3 (GPLv3)](http://www.gnu.org/copyleft/gpl.html).  To learn more about what this means, click that link or [read about it on Wikipedia](http://en.wikipedia.org/wiki/GNU_General_Public_License).
