import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

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

    @EventHandler (priority = EventPriority.LOWEST)
    public void blockBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        Material material = block.getType();
        if (materials.contains(material)){
            event.setExpToDrop(xpAmount);
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event){
        Block block = event.getBlock();
        Material material = block.getType();
        if (materials.contains(material)){
            event.getPlayer().sendMessage("You aren't allowed to place this block!");
            event.setCancelled(true);
        }
    }
}
