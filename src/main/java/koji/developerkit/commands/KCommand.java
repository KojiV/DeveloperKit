package koji.developerkit.commands;

import koji.developerkit.KBase;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
            PluginDescriptionFile descriptionFile = new PluginDescriptionFile(
                    Objects.requireNonNull(
                            getPlugin().getResource("plugin.yml")
                    )
            );
            List<String> aliases = new ArrayList<>();
            if(descriptionFile.getCommands().containsKey(cmd)) {
                Object aliasList = descriptionFile.getCommands().get(cmd).get("aliases");

                if(aliasList != null) {
                    if (aliasList instanceof List) {
                        for (Object o : (List<?>) aliasList) {
                            if (o.toString().contains(":")) {
                                continue;
                            }
                            aliases.add(o.toString());
                        }
                    } else {
                        if (!aliases.toString().contains(":")) {
                            aliases.add(aliases.toString());
                        }
                    }
                }
            }

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
                    if(!aliases.isEmpty()) command.setAliases(aliases);

                    CommandMap commandMap = getCommandMap();
                    commandMap.register(getPlugin().getName(), pluginCommand);
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
