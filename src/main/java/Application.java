import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public class Application extends JavaPlugin {
    @Override
    public void onEnable(){
        this.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
    }

    @Override
    public void onDisable(){}

}
