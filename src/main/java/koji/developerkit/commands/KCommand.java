package koji.developerkit.commands;

import koji.developerkit.KBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

public abstract class KCommand extends KBase implements CommandExecutor {

    public KCommand() {

    }

    @Override
    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    public BukkitCommand getCommand(String cmd) {
        for (Command command : PluginCommandYamlParser.parse(getPlugin())) {
            if (command.getName().equals(cmd)) {
                return new BukkitCommand(
                        command.getName(),
                        command.getDescription(),
                        command.getUsage(),
                        command.getAliases()
                ) {

                    @Override
                    public boolean execute(
                            @NotNull CommandSender sender,
                            @NotNull String commandLabel,
                            @NotNull String[] args
                    ) {
                        if (testPermission(sender)) {
                            return onCommand(sender, this, commandLabel, args);
                        } else {
                            return false;
                        }
                    }
                };
            }
        }
        return null;
    }
}
