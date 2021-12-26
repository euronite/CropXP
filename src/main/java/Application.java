import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public class Application extends JavaPlugin {
    FileConfiguration config = getConfig();

    @Override
    public void onEnable(){
        ArrayList<String> xpCropList = new ArrayList<>(Arrays.asList("PUMPKIN", "MELON"));
        config.addDefault("xpCrops", xpCropList);
        config.options().copyDefaults(true);
        saveConfig();
        this.getServer().getPluginManager().registerEvents(new BlockBreak(config), this);
    }

    @Override
    public void onDisable(){}

}
