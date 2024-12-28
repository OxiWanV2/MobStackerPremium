package com.oxiwan.mobstackerpremium.commands;

import com.oxiwan.mobstackerpremium.MobStackerPremium;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MobStackerCommand implements CommandExecutor {

    private final MobStackerPremium plugin;

    public MobStackerCommand(MobStackerPremium plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelp(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("mobstackerpremium.reload")) {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'exécuter cette commande.");
                return true;
            }
            plugin.getConfigManager().reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Configuration rechargée !");
            return true;
        }

        if (args[0].equalsIgnoreCase("stick")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Seuls les joueurs peuvent exécuter cette commande.");
                return true;
            }
            if (!sender.hasPermission("mobstackerpremium.stick")) {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'exécuter cette commande.");
                return true;
            }
            giveStick((Player) sender);
            sender.sendMessage(ChatColor.GREEN + "Vous avez reçu le bâton de stacking !");
            return true;
        }

        if (args[0].equalsIgnoreCase("stats")) {
            sendStats(sender);
            return true;
        }

        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "=== MobStacker Premium by (OxiWan) ===");
        sender.sendMessage(ChatColor.YELLOW + "/mobstacker help - Affiche cette aide");
        sender.sendMessage(ChatColor.YELLOW + "/mobstacker reload - Recharge la configuration (Permission : mobstackerpremium.reload)");
        sender.sendMessage(ChatColor.YELLOW + "/mobstacker stick - Donne un bâton de stacking (Permission : mobstackerpremium.stick)");
        sender.sendMessage(ChatColor.YELLOW + "/mobstacker stats - Affiche les statistiques des mobs empilés");
    }

    private void sendStats(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "=== Statistiques des mobs empilés ===");

        double performanceGain = plugin.getStackManager().getPerformanceGain();
        sender.sendMessage(ChatColor.YELLOW + "Gain de performance : " + String.format("%.2f", performanceGain) + "%");

        sender.sendMessage(ChatColor.YELLOW + "Statistiques par monde :");

        plugin.getStackManager().getWorldStatistics().forEach((worldName, mobCounts) -> {
            sender.sendMessage(ChatColor.AQUA + "- Monde : " + worldName);
            mobCounts.forEach((mobType, count) ->
                    sender.sendMessage(ChatColor.GRAY + "  * " + mobType + ": " + count));
        });
    }

    private void giveStick(Player player) {
        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta meta = stick.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Bâton de stacking");
            meta.addEnchant(Enchantment.SHARPNESS, 1, true);
            stick.setItemMeta(meta);
        }

        player.getInventory().addItem(stick);
    }
}