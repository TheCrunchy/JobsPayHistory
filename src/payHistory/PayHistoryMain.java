package payHistory;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.gamingmesh.jobs.api.JobsPaymentEvent;

public class PayHistoryMain extends JavaPlugin {
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new PayListener(), this);
		this.getCommand("ph").setExecutor(new HistoryCommand());
	}
	private HashMap<UUID, PlayerData> players = new HashMap<UUID, PlayerData>();
	
	public class PayListener implements Listener
	{
		@SuppressWarnings("deprecation")
		@EventHandler
		public void onPlayerPaid(JobsPaymentEvent event) {
			PlayerData data;
			if (players.containsKey(event.getPlayer().getUniqueId())) {
				data = players.get(event.getPlayer().getUniqueId());
			}
			else {
				data = new PlayerData();
			}
			data.addToHistory(event.getAmount());
			players.put(event.getPlayer().getUniqueId(), data);
			
		}
	}
	public class HistoryCommand implements CommandExecutor {

        // This method is called, when somebody uses our command
        @Override
        @EventHandler
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        	if (sender instanceof Player) {
        		if (players.containsKey(((Player) sender).getUniqueId())) {
        			PlayerData data = players.get(((Player) sender).getUniqueId());
        			sender.sendMessage("§7****************************************");
        			
        			LinkedHashMap<String, String> history = new LinkedHashMap<String, String>();
        			history = data.getHistory();
        			for (Entry<String, String> entry : history.entrySet()) {
        			    String key = entry.getKey();
        			    Object value = entry.getValue();
        			    sender.sendMessage("§7" + key + " : §6" + value);
        			}
        			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        			String numberAsString = decimalFormat.format(data.getAmountEarnt());
        			sender.sendMessage("§7Total : §6" + numberAsString);
        			sender.sendMessage("§7****************************************");
        		}
        		else {
        			sender.sendMessage("§7No history to display yet.");
        		}
        	}
            return true;
        }
    }
}
