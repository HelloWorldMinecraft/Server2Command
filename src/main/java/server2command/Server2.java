package server2command;

import org.bukkit.plugin.java.JavaPlugin;

public class Server2 extends JavaPlugin {
	public void onEnable() {
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getCommand("server2").setExecutor(new Server2Command(this));
	}
}
