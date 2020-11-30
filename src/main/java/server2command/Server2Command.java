package server2command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.ME1312.SubServers.Client.Bukkit.SubAPI;

public class Server2Command implements CommandExecutor {
	private Plugin plugin;
	public Server2Command(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length != 1) return false;
		if (!sender.hasPermission("bungeecord.server." + args[0])) {
			sender.sendMessage("You do not have permission for this.");
			return true;
		}
		SubAPI.getInstance().getSubServer(args[0], subserver -> {
			if (subserver == null) {
				sender.sendMessage(ChatColor.RED + "Invaid SubServer: " + args[0]);
				return;
			}

			if (subserver.isOnline()) {
				if (subserver.getGroups().contains("Thermos")) {
					((Player) sender).kickPlayer(ChatColor.GREEN + "Server has started. Reconnect via " + args[0] + ".red5684.tk:3158");
					return;
				}

				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF(args[0]);

				sender.sendMessage(ChatColor.GREEN + "Sending you now.");
				((Player) sender).sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
			} else {
				if (!subserver.isRunning()) subserver.start();
				sender.sendMessage(ChatColor.GREEN + "Server is starting. Please try again soon.");
			}
		});

		return true;
	}
}
