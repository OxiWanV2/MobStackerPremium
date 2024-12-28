package com.oxiwan.mobstackerpremium.listeners;

import com.oxiwan.mobstackerpremium.MobStackerPremium;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EntityListener implements Listener {

    private final MobStackerPremium plugin;

    public EntityListener(MobStackerPremium plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL ||
                event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            plugin.getStackManager().stackNearbyEntities(event.getEntity());
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        plugin.getStackManager().handleEntityDeath(entity, event);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item != null && item.getType() == Material.STICK &&
                item.getItemMeta() != null &&
                ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Bâton de stacking")) {

            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                double radius = plugin.getConfigManager().getStackRadius();

                player.getNearbyEntities(radius, radius, radius).stream()
                        .filter(entity -> entity instanceof LivingEntity)
                        .map(entity -> (LivingEntity) entity)
                        .forEach(plugin.getStackManager()::stackNearbyEntities);

                player.sendMessage(ChatColor.GREEN + "Les mobs proches ont été empilés !");
            }
        }
    }
}