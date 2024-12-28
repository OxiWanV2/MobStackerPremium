package com.oxiwan.mobstackerpremium.managers;

import com.oxiwan.mobstackerpremium.MobStackerPremium;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StackManager {

    private final MobStackerPremium plugin;
    private int stackedMobCount = 0;

    public StackManager(MobStackerPremium plugin) {
        this.plugin = plugin;
    }

    public void stackNearbyEntities(LivingEntity entity) {
        if (isExcluded(entity)) return;

        List<LivingEntity> nearbyEntities = entity.getNearbyEntities(
                        plugin.getConfigManager().getStackRadius(),
                        plugin.getConfigManager().getStackRadius(),
                        plugin.getConfigManager().getStackRadius()).stream()
                .filter(e -> e instanceof LivingEntity)
                .map(e -> (LivingEntity) e)
                .toList();

        for (LivingEntity nearby : nearbyEntities) {
            if (nearby.getType() == entity.getType()) {
                stackEntities(entity, nearby);
                return;
            }
        }

        setStackSize(entity, 1);
    }

    private void stackEntities(LivingEntity entity1, LivingEntity entity2) {
        int stack1 = getStackSize(entity1);
        int stack2 = getStackSize(entity2);
        int newStackSize = stack1 + stack2;

        if (newStackSize <= plugin.getConfigManager().getMaxStackSize()) {
            setStackSize(entity1, newStackSize);
            entity2.remove();
            stackedMobCount++;
        }
    }

    public void handleEntityDeath(LivingEntity entity, EntityDeathEvent event) {
        int stackSize = getStackSize(entity);

        if (stackSize > 1) {
            setStackSize(entity, stackSize - 1);

            LivingEntity newEntity = (LivingEntity) entity.getWorld().spawnEntity(entity.getLocation(), entity.getType());
            setStackSize(newEntity, stackSize - 1);

            event.getDrops().clear();
            event.setDroppedExp(event.getDroppedExp() / stackSize);
        }
    }

    public Map<String, Map<String, Integer>> getWorldStatistics() {
        Map<String, Map<String, Integer>> stats = new HashMap<>();

        for (World world : plugin.getServer().getWorlds()) {
            Map<String, Integer> mobCounts = new HashMap<>();
            for (LivingEntity entity : world.getLivingEntities()) {
                String typeName = entity.getType().name();
                mobCounts.put(typeName, mobCounts.getOrDefault(typeName, 0) + getStackSize(entity));
            }
            stats.put(world.getName(), mobCounts);
        }

        return stats;
    }

    public double getPerformanceGain() {
        int totalEntities = plugin.getServer().getWorlds().stream()
                .mapToInt(world -> world.getLivingEntities().size())
                .sum();
        return totalEntities > 0 ? ((double) stackedMobCount / totalEntities) * 100 : 0.0;
    }

    private boolean isExcluded(LivingEntity entity) {
        return plugin.getConfigManager().getExcludedMobs().contains(entity.getType().name());
    }

    private int getStackSize(LivingEntity entity) {
        if (entity.hasMetadata("stack_size")) {
            return entity.getMetadata("stack_size").get(0).asInt();
        }
        return 1;
    }

    private void setStackSize(LivingEntity entity, int size) {
        entity.setMetadata("stack_size", new FixedMetadataValue(plugin, size));
        entity.setCustomName(ChatColor.YELLOW + entity.getType().name() + " x" + size);
        entity.setCustomNameVisible(true);
    }
}