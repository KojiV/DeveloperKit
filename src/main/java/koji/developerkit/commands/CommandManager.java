package koji.developerkit.commands;

import koji.developerkit.KBase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

public class CommandManager extends KBase {

    public CommandMap getCommandMap() {
        try {
            return (CommandMap) getPrivateField(Bukkit.getPluginManager(), "commandMap");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void registerCommand(String label, Command actualCommand) {
        getCommandMap().register(label, getPlugin().getName(), actualCommand);
    }
}
