package koji.developerkit.commands;

import koji.developerkit.KBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class KCommand extends KBase implements CommandExecutor {

    @Override
    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

}
