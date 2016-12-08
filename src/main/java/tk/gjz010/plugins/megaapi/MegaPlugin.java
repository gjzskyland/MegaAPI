package tk.gjz010.plugins.megaapi;

import com.gmail.stefvanschiedev.customblocks.events.listeners.BlockBreakEventListener;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public class MegaPlugin extends JavaPlugin {
    // This code is called after the server starts and after the /reload command
    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "{0}.onEnable()", this.getClass().getName());
        BlockBreakEventListener blockBreakEventListener = new BlockBreakEventListener(this);
    }

    // This code is called before the server stops and after the /reload command
    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "{0}.onDisable()", this.getClass().getName());
    }
}
