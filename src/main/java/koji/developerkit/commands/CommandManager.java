package koji.developerkit.commands;

import koji.developerkit.KBase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.util.List;
import java.util.Map;

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

    @SuppressWarnings("unchecked")
    public void unregisterCommand(Command actualCommand) {
        try {
            CommandMap commandMap = getCommandMap();
            Map<String, Command> knownCommands =
                    (Map<String, Command>) getPrivateField(commandMap, "knownCommands");

            List<String> commandNames = actualCommand.getAliases();
            commandNames.add(actualCommand.getLabel());

            commandNames.forEach(knownCommands::remove);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
