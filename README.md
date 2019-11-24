# Stevebot
A pathfinding bot for Minecraft.
Requires Forge for Minecraft 1.12


[![IMAGE ALT TEXT](http://img.youtube.com/vi/Q6micHhYB54/0.jpg)](https://www.youtube.com/watch?v=Q6micHhYB54 "Stevebot 1.0.0")


#### Commands

- /path \<xs> \<ys> \<ze> \<xe> \<ye> \<ze>  -  Finds a path from the first position to the second position.
    
- /path \<x> \<y> \<z> [freelook]  -  Finds a path from the current position to the given position. Enable freelook-mode with "freelook".

- /path \<dist> [freelook]  -  Finds a path 'dist'-blocks in the direction the player is looking. Enable freelook-mode with "freelook".

- /path level \<level> [freelook]  -  Finds a path to the given y-level. Enable freelook-mode with "freelook".

- /path <block> [freelook]  -  Finds a path to the nearest block of the given type (e.g. minecraft:diamond_ore). Enable freelook-mode with "freelook".

- /freelook  -  Toogle freelook-mode.

- /follow stop  -  Stop following the current path.

- /set timeout <seconds>  -  Sets the timeout for pathfinding.

- /set verbose <enable>  -  Enable/Disable verbose log-mode.

- /set keepPathRenderable <keep>  -  Keep/Discard the path-overlay after completion.

- /show chunkcache <show>  -  Show/Hide the overlay for the chunk cache.

- /show nodecache <show>  -  Show/Hide the overlay for the node cache.

- /pathstyle <style>  -  Set the style of the path-overlay (solid, pathid, actionid, actioncost or actiontype)

- /clear blockcache  -  Clears the block-cache.

- /statistics [console]  -  Displays statistics about the last pathfinding process. Optionally format for console-output.