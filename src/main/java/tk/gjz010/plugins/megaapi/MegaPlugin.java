package tk.gjz010.plugins.megaapi;

import com.gmail.stefvanschiedev.customblocks.block.CustomBlock;
import com.gmail.stefvanschiedev.customblocks.events.listeners.BlockBreakEventListener;
import java.util.logging.Level;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MegaPlugin extends JavaPlugin implements Listener{
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
    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        if(e.getMessage().contains("create_custom_block")){
        ItemStack s=new ItemStack(Material.DIAMOND_BLOCK);
        CustomBlock cb=new CustomBlock(s);
        cb.place(e.getPlayer().getLocation());
        }
    }
}
