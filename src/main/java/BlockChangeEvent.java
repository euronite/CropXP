import com.palmergames.bukkit.towny.event.actions.TownyBuildEvent;
import com.palmergames.bukkit.towny.event.actions.TownyDestroyEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;


public class BlockChangeEvent implements Listener {
    FileConfiguration config;
    ArrayList<Material> materials = new ArrayList<>();
    int xpAmount;

    public BlockChangeEvent(FileConfiguration config) {
        this.config = config;
        xpAmount = config.getInt("xpAmount");
        for (String material: config.getStringList("xpCrops")) {
            if (material != null){
                materials.add(Material.getMaterial(material));
            }
        }
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void blockBreak(TownyDestroyEvent event){
        Block block = event.getBlock();
        Material material = block.getType();
        if (materials.contains(material)){
            ExperienceOrb orb = block.getWorld().spawn(block.getLocation().add(0.5, 0.5, 0.5), ExperienceOrb.class);
            orb.setExperience(xpAmount);
        }
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void blockPlace(TownyBuildEvent event){
        Block block = event.getBlock();
        Material material = block.getType();
        if (materials.contains(material)){
            event.getPlayer().sendMessage("Cannot place this block");
            event.setCancelled(true);

        }
    }
}
