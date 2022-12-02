package koji.developerkit.commands;

import koji.developerkit.KBase;
import org.bukkit.Bukkit;
import org.bukkit.command.*;

import java.util.HashMap;
import java.util.logging.Level;

public abstract class KCommand extends KBase implements CommandExecutor {

    @Override
    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    @SuppressWarnings("unchecked")
    public void registerCommand(String cmd) {
        try {
            for(Command command : PluginCommandYamlParser.parse(getPlugin())) {
                if(command.getName().equalsIgnoreCase(cmd)) {
                    CommandMap map = (CommandMap) getPrivateField(Bukkit.getPluginManager(),
                            "commandMap");
                    HashMap<String, Command> knownCommands = (HashMap<String, Command>)
                            getPrivateField(map, "knownCommands");
                    knownCommands.put(command.getName(), command);
                    for(String alias : command.getAliases()) {
                        knownCommands.put(alias, command);
                    }
                    ((PluginCommand) command).setExecutor(this);
                }
            }
        } catch(Exception e) {
            getPlugin().getLogger().log(
                    Level.WARNING,
                    "§4The command §c"+cmd+" §4couldn't be found in §cplugin.yml§4l!"
            );
        }
    }

    @SuppressWarnings("unchecked")
    public void unregisterCommand(String cmd) {
        try {
            for(Command command : PluginCommandYamlParser.parse(getPlugin())) {
                if(command.getName().equalsIgnoreCase(cmd)) {
                    CommandMap map = (CommandMap) getPrivateField(Bukkit.getPluginManager(),
                            "commandMap");
                    HashMap<String, Command> knownCommands = (HashMap<String, Command>)
                            getPrivateField(map, "knownCommands");
                    knownCommands.remove(command.getName(), command);
                    for(String alias : command.getAliases()) {
                        knownCommands.remove(alias, command);
                    }
                }
            }
        } catch(Exception e) {
            getPlugin().getLogger().log(
                    Level.WARNING,
                    "§4The command §c"+cmd+" §4couldn't be found in §cplugin.yml§4l!"
            );
        }
    }
}
