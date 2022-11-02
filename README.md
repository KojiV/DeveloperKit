# DeveloperKit

> The ultimate kit for making Minecraft plugins efficiently

This will explain everything you need to know for this kit.

## Overview

The Developer Kit comes with multiple classes to aid in making the creation of Minecraft plugins easier, including:

- [KBase](https://github.com/KojiV/DeveloperKit/blob/main/src/main/java/koji/developerkit/KBase.java), which is a class
  that it meant to hold a bunch of util classes. Note: it is recommended that classes that aren't extending anything
  extend this
- [KListener](https://github.com/KojiV/DeveloperKit/blob/main/src/main/java/koji/developerkit/listener/KListener.java),
  which is a class meant to be used instead
  of [Listener](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/Listener.html). This extends KBase, so all
  KBase functions are present in KListener as well
- [KCommand](https://github.com/KojiV/DeveloperKit/blob/main/src/main/java/koji/developerkit/commands/KCommand.java),
  which is a class meant to be used instead
  of [CommandExecutor](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/command/CommandExecutor.html). This extends
  KBase, so all KBase functions are present in KCommand as well
- [GUIClickableItem](https://github.com/KojiV/DeveloperKit/blob/main/src/main/java/koji/developerkit/gui/GUIClickableItem.java), which is a class meant for running an action in the run function upon clicking the item specified in the
  GUIClickableItem. This is meant to replace the need for making listeners for clicking items in a GUI.
- [KRunnable](https://github.com/KojiV/DeveloperKit/blob/main/src/main/java/koji/developerkit/runnable/KRunnable.java),
  which is a class meant to be used instead
  of [BukkitRunnable](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/scheduler/BukkitRunnable.html). This functions
  as a normal BukkitRunnable, but includes the following new features
  - The ability to cancel it after a certain amount of time
  - The ability to run a function upon the runnable cancellation
  - Uses lambda functions, making it look a lot neater and easier to read
    - task -> {} instead of @Override public void run() {}

## Known Issues
- For some reason the libraries I include are included twice, including the un-relocated stuff AND the relocated stuff (at least that happens with my personal project)

## Future Things
- Auto register listeners (with toggle for it)
- Auto register commands (with toggle for it)

## Download

#### Maven [![maven](https://img.shields.io/maven-central/v/io.github.kojiv/developerkit)](https://s01.oss.sonatype.org/content/repositories/releases/io/github/kojiv/developerkit/)

```xml
<dependency>
  <groupId>io.github.kojiv</groupId>
  <artifactId>developerkit</artifactId>
  <version>version</version>
</dependency>
```
#### Gradle

```gradle
repositories {
  mavenCentral()
}
dependencies {
  implementation("io.github.kojiv:developerkit:version") { isTransitive = false }
}
```

## Examples

#### Example 1: KRunnable

In this example, the runnable will check if the entity is dead, and cancel the runnable if it is, but no matter what it will be canceled in 10 seconds. 
```
final int[] i = {10};

World world = Bukkit.getWorlds().get(0);
Zombie zomb = world.spawn(world.getPlayers().get(0).getLocation(), Zombie.class);

new KRunnable(task -> {

  //If the zombie isn't dead
  if(!zomb.isDead()) {
  
    for(Player player : world.getPlayers()) {
      //Say to every player in the world that the zombie is still alive
      player.sendMessage(ChatColor.RED + "Zombie is still alive! You have " + i[0] + " seconds!");
    }
    
  } else {
    //Cancel it (this will cancel with the type of "Premature")
    task.cancel();
  }
  i[0]--;
}, 10 * 20L //If this time hits, then time ran out, canceling withe the type of "Time"

//Specify a task that runs on cancel
).cancelTask(task -> 
  
  world.getPlayers().forEach(p -> 
    p.sendMessage(ChatColor.GREEN + "Good job!")
  )
), KRunnable.CancellationActivationType.PREMATURE //The above function runs if the canceled type is "Premature"
  
//Specify a task that runs on cancel
).cancelTask(task -> 
  
  world.getPlayers().forEach(p ->
    p.sendMessage(ChatColor.RED + "Failed to kill the zombie!" )
  )
), KRunnable.CancellationActivationType.TIME //The above function runs if the canceled type is "Time"

//Specify a task that runs on cancel
).cancelTask(task -> 
  
  world.getPlayers().forEach(p ->
    p.sendMessage(ChatColor.GREEN + "Challenge Ended.")
  )
), KRunnable.CancellationActivationType.BOTH //The above function runs if it's canceled no matter what
  
//Actually schedule the thing
).runTaskTimer(KBase.getPlugin(), 0L, 20L); 
```

## Credits

This dev kit includes libraries from other people, specifically:

- [NBT API](https://www.spigotmc.org/resources/nbt-api.7939/) by tr7zw on Github & Spigot
- [XSeries](https://www.spigotmc.org/threads/xseries-xmaterial-xparticle-xsound-xpotion-titles-actionbar-etc.378136/) by
  CryptoMorin on Github & Spigot
