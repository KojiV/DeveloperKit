package koji.developerkit.runnable;

import koji.developerkit.utils.KStatic;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Consumer;

public class KRunnable extends BukkitRunnable {
    static {
        plugin = KStatic.getPlugin();
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

    private boolean cancelled = false;

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void run() {
        task.accept(this);
    }

    public KRunnable cancelAfter(long period) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!cancelled) {
                cancel(CancellationActivationType.TIME);
            }
        }, period);
        return this;
    }

    public void cancel(CancellationActivationType type) {
        cancelled = true;
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

    @NotNull
    public synchronized BukkitTask runTask() throws IllegalArgumentException, IllegalStateException {
        return super.runTask(plugin);
    }

    @NotNull
    public synchronized BukkitTask runTaskAsynchronously() throws IllegalArgumentException, IllegalStateException {
        return super.runTaskAsynchronously(plugin);
    }

    @NotNull
    public synchronized BukkitTask runTaskLater(long delay) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskLater(plugin, delay);
    }

    @NotNull
    public synchronized BukkitTask runTaskLaterAsynchronously(long delay) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskLaterAsynchronously(plugin, delay);
    }

    @NotNull
    public synchronized BukkitTask runTaskTimer(long delay, long period) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskTimer(plugin, delay, period);
    }

    @NotNull
    public synchronized BukkitTask runTaskTimerAsynchronously(long delay, long period) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskTimerAsynchronously(plugin, delay, period);
    }
}
