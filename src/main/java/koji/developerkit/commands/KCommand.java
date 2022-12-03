package koji.developerkit.commands;

import koji.developerkit.KBase;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.logging.Level;

public abstract class KCommand extends KBase implements CommandExecutor {

    public KCommand() {

    }

    @Override
    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    public CommandMap getCommandMap() {
        try {
            return (CommandMap) getPrivateField(Bukkit.getPluginManager(), "commandMap");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void registerCommand(String cmd) {
        try {
            for (Command command : PluginCommandYamlParser.parse(getPlugin())) {
                if (command.getName().equals(cmd)) {
                    Command pluginCommand = new Command(
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
                    CommandMap commandMap = getCommandMap();
                    commandMap.register(KBase.getPlugin().getName(),  pluginCommand);
                }
            }
        } catch (Exception e) {
            getPlugin().getLogger().log(
                    Level.WARNING,
                    "§4The command §c" + cmd + " §4couldn't be found in §cplugin.yml§4l!"
            );
        }
    }

    @SuppressWarnings("unchecked")
    public void unregisterCommand(String cmd) {
        try {
            for (Command command : PluginCommandYamlParser.parse(getPlugin())) {
                if (command.getName().equals(cmd)) {
                    CommandMap commandMap = getCommandMap();
                    HashMap<String, Command> knownCommands =
                            (HashMap<String, Command>) getPrivateField(commandMap, "knownCommands");
                    knownCommands.remove(cmd);
                    command.unregister(commandMap);
                }
            }
        } catch (Exception e) {
            getPlugin().getLogger().log(
                    Level.WARNING,
                    "§4The command §c" + cmd + " §4couldn't be found in §cplugin.yml§4l!"
            );
        }
    }
}
