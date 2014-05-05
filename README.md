# AdminTP #

AdminTP is a plugin designed to aid server administrators by allowing them to view details of and teleport to any of the following player locations:  

* Player's most recent bed  
* Player's most recent death location  
* Player's Faction home (requires Factions 2.0+)  
* Player's Essentials homes (requires Essentials)  
* Player's most recent log-in location  
* Player's most recent log-out location  

**AdminTP is fully UUID-compatible.**

## Commands ##

Alias: `/atp`

`/admintp` - show plugin help  
`/admintp <player>` - show a player's information  
`/admintp <player> <bed|death|login|logout>` - teleport to the relevant location  
`/admintp <player> ess[entials] [home_name]` - teleport to a player's Essentials home  
`/admintp <player> fac[tion]` - teleport to a player's faction home    

## Permissions ##

`admintp.use` - Access to the plugin and the `/admintp` command.

## Configuration ##

None.  The `config.yml` file is only used for saving player locations.