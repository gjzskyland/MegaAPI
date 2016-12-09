package tk.gjz010.plugins.megaapi;

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
    private Map<Material,Map<Integer,Class<? extends CustomItem>>> customItemMap;
    public static MegaPlugin INSTANCE;
    private CustomItemManager itemManager;
    // This code is called after the server starts and after the /reload command
    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "{0}.onEnable()", this.getClass().getName());
        customItemMap=new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, this);
        itemManager=new CustomItemManager();
        INSTANCE=this;
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
            MagicOreBlock ore=new MagicOreBlock();
            Bukkit.getScheduler().runTask(this, ()->{
                itemManager.place(ore, e.getPlayer().getLocation());
            });
        }
    }
    private void registerCustomItem(Material base,int damage,Class<? extends CustomItem> clazz){
        Map<Integer,Class<? extends CustomItem>> m;
        if(!customItemMap.containsKey(base)){
            m=new HashMap<>();
            customItemMap.put(base,m);
        }else{
            m=customItemMap.get(base);
        }
        m.put(damage, clazz);
    }
    public void registerCustomItem(Class<? extends CustomItem> clazz){
        ItemOverride meta=clazz.getAnnotation(ItemOverride.class);
        registerCustomItem(meta.base(),meta.damage(),clazz);
    }
    public Class<? extends CustomItem> getCustomItemClass(Material base,int damage){
        if(!customItemMap.containsKey(base)) return null;
        Map<Integer,Class<? extends CustomItem>> m=customItemMap.get(base);
        if(!m.containsKey(damage)) return null;
        return m.get(damage);
    }
}
