package koji.developerkit.bossbar;

import org.bukkit.entity.Player;

public interface IBossbar {
    public void setBar(Player p, String text, double healthPercent);

    public void removeBar(Player p);

    public void updateText(Player p, String text);

    public void updateHealth(Player p, double healthPercent);

    public void updateBar(Player p, String text, double healthPercent);

    public void updateBarOrAdd(Player p, String text, double healthPercent);
}