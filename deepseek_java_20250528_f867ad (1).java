package com.example.thompsonplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class ThompsonPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Thompson Plugin увімкнено!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Thompson Plugin вимкнено!");
    }

    public static ItemStack createThompson() {
        ItemStack thompson = new ItemStack(Material.IRON_HOE);
        ItemMeta meta = thompson.getItemMeta();
        
        meta.setDisplayName("§6Томпсон");
        meta.setLore(Arrays.asList("§7Легендарний пістолет-кулемет", "§7Натисни ПКМ для пострілу"));
        meta.setUnbreakable(true);
        
        thompson.setItemMeta(meta);
        return thompson;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        
        if (item.getType() == Material.IRON_HOE && item.hasItemMeta() && 
            item.getItemMeta().getDisplayName().equals("§6Томпсон")) {
            
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
                shootThompson(player);
            }
        }
    }

    private void shootThompson(Player player) {
        // Звук пострілу
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1.0f, 1.5f);
        
        // Створення снаряда (використовуємо сніжки як кулі)
        Snowball bullet = player.launchProjectile(Snowball.class);
        bullet.setVelocity(player.getLocation().getDirection().multiply(3.0)); // Швидкість кулі
        
        // Віддача
        Vector recoil = player.getLocation().getDirection().multiply(-0.1);
        player.setVelocity(player.getVelocity().add(recoil));
    }
}