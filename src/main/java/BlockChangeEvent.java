import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Directional;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BlockChangeEvent implements Listener {
    FileConfiguration config;
    ArrayList<Material> materials = new ArrayList<>();

    List<BlockFace> blockFaces = Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH);
    int xpAmount;

    public BlockChangeEvent(FileConfiguration config) {
        this.config = config;
        xpAmount = config.getInt("xpAmount");
        if (xpAmount < 1) {
            throw new IllegalArgumentException("XP value should be greater than 0. Please fix before using.");
        }
        for (String material : config.getStringList("xpCrops")) {
            if (material != null) {
                materials.add(Material.getMaterial(material));
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void blockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        if (materials.contains(material)) {
            if (material == Material.MELON || material == Material.PUMPKIN) {
                for (BlockFace direction : blockFaces) {
                    Block adjacent = block.getRelative(direction);
                    if (adjacent.getType() == Material.ATTACHED_MELON_STEM || adjacent.getType() == Material.ATTACHED_PUMPKIN_STEM) {
                        Directional stemDirection = (Directional) adjacent.getBlockData();
                        if (stemDirection.getFacing() == direction.getOppositeFace()) {
                            event.setExpToDrop(xpAmount);
                        }
                        return;
                    }
                }
            } else {
                final Ageable ageable = (Ageable) block.getBlockData();
                if (ageable.getAge() == ageable.getMaximumAge()) {
                    event.setExpToDrop(xpAmount);
                }
            }
        } else if (material == Material.ATTACHED_MELON_STEM || material == Material.ATTACHED_PUMPKIN_STEM) {
            event.setExpToDrop(xpAmount);
        }
    }
}
