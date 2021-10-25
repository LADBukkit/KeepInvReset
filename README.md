# KeepInvReset
A bukkit plugin to keep your inventory after a map reset.

## How does it work
It saves the inventory of every player when he leaves the server.
Then when he joins and the plugin detects a reset it sends a message to the player,
who can now use the `/keepinvreset` (short version is `/kir`) to choose a specified amount of items out of his old inventory.

## How does it detect a reset
Whenever the seed of the world the player joins in is different from the seed of the world he left, then the plugin detects a reset.

## Commands
`/keepinvreset` (short `/kir`) opens the inventory from which the player can choose the items.  
`/keepinvreset <number>` (short `/kir <number`) Sets the amount of items the players can choose. Needs permission **keepinvreset.number**.
