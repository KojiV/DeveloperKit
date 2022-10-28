package koji.developerkit.bossbar;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;

public class Bossbar {

    private static final IBossbar versionBossbar =
            XMaterial.supports(9) ? new Bossbar_1_9() : new Bossbar_1_8();

    public static void setBar(Player p, String text, double healthPercent) {
        versionBossbar.setBar(p, text, healthPercent);
    }

    public static void removeBar(Player p) {
        versionBossbar.removeBar(p);
    }

    public static void updateText(Player p, String text) {
        versionBossbar.updateText(p, text);
    }

    public static void updateHealth(Player p, double healthPercent) {
        versionBossbar.updateHealth(p, healthPercent);
    }

    public static void updateBar(Player p, String text, double healthPercent) {
        versionBossbar.updateBar(p, text, healthPercent);
    }

    public static void updateBarOrAdd(Player p, String text, double healthPercent) {
        versionBossbar.updateBarOrAdd(p, text, healthPercent);
    }
}