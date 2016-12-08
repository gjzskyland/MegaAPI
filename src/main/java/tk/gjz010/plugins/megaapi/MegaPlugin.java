package tk.gjz010.plugins.megaapi;

import com.gmail.stefvanschiedev.customblocks.block.CustomBlock;
import com.gmail.stefvanschiedev.customblocks.events.listeners.BlockBreakEventListener;
import java.util.logging.Level;
import org.bukkit.Bukkit;
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
        Bukkit.getPluginManager().registerEvents(this, this);
        BlockBreakEventListener blockBreakEventListener = new BlockBreakEventListener(this);
    }

    // This code is called before the server stops and after the /reload command
    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "{0}.onDisable()", this.getClass().getName());
    }
    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        //System.out.println(e.getMessage());
        if(e.getMessage().contains("create_custom_block")){
        ItemStack s=new ItemStack(Material.SKULL);
        CustomBlock cb=new CustomBlock(s);
        Bukkit.getScheduler().runTask(this,()->cb.place(e.getPlayer().getLocation()));
        Bukkit.getScheduler().runTask(this,()->cb.setUnderlyingBlock(Material.STONE));
        }
    }
}
