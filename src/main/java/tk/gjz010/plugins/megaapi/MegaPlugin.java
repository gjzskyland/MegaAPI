package tk.gjz010.plugins.megaapi;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import tk.gjz010.plugins.megaapi.block.CustomItem;
import tk.gjz010.plugins.megaapi.block.CustomItemManager;
import tk.gjz010.plugins.megaapi.block.ItemOverride;
import tk.gjz010.plugins.megaapi.block.test.MagicOreBlock;

public class MegaPlugin extends JavaPlugin implements Listener{
    
    public static MegaPlugin INSTANCE;
    private CustomItemManager itemManager;
    private ProtocolManager protocolManager;
    // This code is called after the server starts and after the /reload command
    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "{0}.onEnable()", this.getClass().getName());
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(itemManager, this);
        itemManager=new CustomItemManager();
        protocolManager=ProtocolLibrary.getProtocolManager();
        INSTANCE=this;
    }

    // This code is called before the server stops and after the /reload command
    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "{0}.onDisable()", this.getClass().getName());
    }
    public CustomItemManager getItemManager(){
        return itemManager;
    }
    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        //System.out.println(e.getMessage());
        if(e.getMessage().contains("create_custom_block")){
            MagicOreBlock ore=new MagicOreBlock();
            Bukkit.getScheduler().runTask(this, ()->{
                itemManager.place(ore, e.getPlayer().getLocation());
            });
        }
    }
}
