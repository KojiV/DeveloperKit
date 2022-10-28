package koji.developerkit.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Bossbar_1_9 implements IBossbar {

    HashMap<Player, BossBar> bossbars = new HashMap<>();

    public void setBar(Player p, String text, double healthPercent) {
        try {
            Method method = Bukkit.class.getMethod("createBossBar", String.class, BarColor.class, BarStyle.class);
            BossBar bossbar = (BossBar) method.invoke(null, text, BarColor.PINK, BarStyle.SOLID);
            bossbar.setProgress(1.0 / healthPercent);
            bossbar.addPlayer(p);
            bossbars.put(p, bossbar);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void removeBar(Player p) {
        if (bossbars.containsKey(p)) {
            bossbars.get(p).removePlayer(p);
            bossbars.remove(p);
        }
    }

    public void updateText(Player p, String text) {
        if (bossbars.containsKey(p)) {
            bossbars.get(p).setTitle(text);
        }
    }

    public void updateHealth(Player p, double healthPercent) {
        if (bossbars.containsKey(p)) {
            bossbars.get(p).setProgress(1.0 / healthPercent);
        }
    }

    public void updateBar(Player p, String text, double healthPercent) {
        if (bossbars.containsKey(p)) {
            BossBar bossbar = bossbars.get(p);
            bossbar.setTitle(text);
            bossbar.setProgress(1.0 / healthPercent);
        }
    }

    public void updateBarOrAdd(Player p, String text, double healthPercent) {
        if (bossbars.containsKey(p)) {
            BossBar bossbar = bossbars.get(p);
            bossbar.setTitle(text);
            bossbar.setProgress(1.0 / healthPercent);
        } else {
            setBar(p, text, healthPercent);
        }
    }
}