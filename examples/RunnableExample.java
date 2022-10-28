import koji.developerkit.KBase;
import koji.developerkit.runnable.KRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import java.util.concurrent.atomic.AtomicBoolean;

public class RunnableExample {
  
    /**
      In this one, it will check if the entity is dead and cancel if it is, otherwise, it will do it anyways after 10 seconds
    **/
    public static void example1() {
        final int[] i = {10};

        World world = Bukkit.getWorlds().get(0);
        Zombie zomb = world.spawn(world.getPlayers().get(0).getLocation(), Zombie.class);

        AtomicBoolean killed = new AtomicBoolean(false);
        new KRunnable(task -> {
            if(!zomb.isDead()) {
                for(Player player : world.getPlayers()) {
                    player.sendMessage(ChatColor.RED + "Zombie is still alive! You have " + i[0] + " seconds!");
                }
            } else {
                killed.set(true);
                task.cancel();
            }
            i[0]--;
        }, 10 * 20L, task -> {
            for(Player player : world.getPlayers()) {
                if(killed.get()) {
                    player.sendMessage(ChatColor.GREEN + "Good job!");
                } else {
                    player.sendMessage(ChatColor.RED + "Failed to kill the zombie!");
                }
            }
        }).runTaskTimer(KBase.getPlugin(), 0, 20L);
    }
}
