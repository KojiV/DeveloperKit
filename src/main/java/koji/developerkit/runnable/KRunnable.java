package koji.developerkit.runnable;

import koji.developerkit.KBase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.function.Consumer;

public class KRunnable extends BukkitRunnable {
    static {
        plugin = KBase.getPlugin();
    }

    private static final JavaPlugin plugin;

    public KRunnable(Consumer<KRunnable> task) {
        this.task = task;
    }

    public KRunnable(Consumer<KRunnable> task, long period) {
        this.task = task;
        this.period = period;

        cancelAfter(period);
    }

    long period = -Integer.MAX_VALUE;

    Consumer<KRunnable> task;
    HashMap<CancellationActivationType, Consumer<KRunnable>> tasksOnCancel = new HashMap<>();

    boolean isCancelled = false;

    @Override
    public void run() {
        task.accept(this);
    }

    public KRunnable cancelAfter(long period) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!isCancelled) {
                cancel(CancellationActivationType.TIME);
            }
        }, period);
        return this;
    }

    public void cancel(CancellationActivationType type) {
        isCancelled = true;
        super.cancel();

        CancellationActivationType[] typesList = new CancellationActivationType[]{
                type, CancellationActivationType.BOTH
        };

        for (CancellationActivationType types : typesList) {
            if (tasksOnCancel.getOrDefault(types, null) != null) {
                tasksOnCancel.get(types).accept(this);
            }
        }
    }

    @Override
    public void cancel() {
        cancel(CancellationActivationType.PREMATURE);
    }

    public KRunnable cancelTask(Consumer<KRunnable> taskAfter, CancellationActivationType type) {
        tasksOnCancel.put(type, taskAfter);
        return this;
    }

    public enum CancellationActivationType {
        PREMATURE,
        TIME,
        BOTH
    }
}
