Simple scoreboard library, anti-flicker support but nothing too fancy. 

Creating a scoreboard is as simple as this:  
```java

int coins = 0;

Scoreboard scoreboard = new Scoreboard("Cool title", 
        ScoreboardBuilder.create()
            .set("coins", new ScoreboardEntry(String.valueOf(coins), false))
            .set("players", new ScoreboardEntry(String.valueOf(Bukkit.getOnlinePlayers().size()), false)));

```

Apply it to a player:  
```java

ScoreboardManager scoreboardManager = ...
Player player = ...
        
scoreboardManager.setScoreboard(player, scoreboard);
```

Modify an entry:

```java

private ScoreboardManager manager = ...

@EventHandler
public void onPlayerJoin(PlayerJoinEvent event){
    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, (Runnable) () ->
        Bukkit.getOnlinePlayers().forEach(this::updateOnlinePlayersEntry), 1);
}

private void updateOnlinePlayersEntry(Player player){
    Scoreboard scoreboard = manager.getScoreboard(player);
    ScoreboardEntry entry = scoreboard.getEntry("players");
    entry.update(String.valueOf(Bukkit.getOnlinePlayers().size()));
}
```


# To-Do  
- General way to do scoreboard styling, currently you need your own system if you want to have the same style for every entry.
- Load scoreboard from config / localization in one "list"
- Animation support / ticking scoreboard : Currently entries are updated only when needed, by the plugin. If we want animation support, the board would have to be ticking. This will need some investigation.
- Add support for changing entry type, you might want to use normal text instead of localized one.