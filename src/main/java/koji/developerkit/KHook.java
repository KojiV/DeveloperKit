package koji.developerkit;

import org.bukkit.plugin.java.JavaPlugin;

public class KHook extends JavaPlugin {
    private static KHook hook;

    public static KHook getHook() {
        return hook;
    }

    @Override
    public void onEnable() {
        hook = this;
    }

    @Override
    public void onDisable() {}
}
