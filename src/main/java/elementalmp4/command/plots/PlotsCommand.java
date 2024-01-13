package main.java.elementalmp4.command.plots;

import main.java.elementalmp4.GlobalConfig;
import main.java.elementalmp4.service.GlobalConfigService;
import main.java.elementalmp4.service.PlotService;
import main.java.elementalmp4.utils.Plot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class PlotsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        List<Plot> plots = PlotService.getUserPlots(commandSender.getName());
        if (plots.isEmpty()) {
            commandSender.sendMessage(ChatColor.RED + "You don't have any plots!");
        } else {
            List<String> out = new ArrayList<>();
            for (Plot plot : plots) {
                out.add(ChatColor.GREEN.toString() + plot.getId()
                        + " " + ChatColor.YELLOW + plot.getXA() + " " + plot.getYA() + ChatColor.AQUA
                        + " > " + ChatColor.YELLOW + plot.getXB() + " " + plot.getYB() + ChatColor.AQUA
                        + " - " + ChatColor.GOLD +  plot.getWorld());
            }
            int usedArea = plots.stream().map(Plot::getPlotArea).reduce(0, Integer::sum);
            int remainingArea = GlobalConfigService.getAsInteger(GlobalConfig.PLOT_MAX_SIZE) - usedArea;
            out.add(ChatColor.GREEN + "Used: " + ChatColor.YELLOW + usedArea + " blocks" + ChatColor.GREEN + " Remaining: " + ChatColor.YELLOW + remainingArea);
            commandSender.sendMessage(String.join("\n", out));
        }
        return true;
    }
}
