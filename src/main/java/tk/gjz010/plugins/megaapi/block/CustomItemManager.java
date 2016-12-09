/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.gjz010.plugins.megaapi.block;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.gjz010.plugins.megaapi.MegaPlugin;
import tk.gjz010.plugins.megaapi.i18n.Locale;

/**
 *
 * @author gjz010
 */
public class CustomItemManager implements Listener{
    final private Map<Material,Map<Integer,Class<? extends CustomItem>>> customItemMap;
    public CustomItemManager(){
        customItemMap=new HashMap<>();
    }
    public Material getBase(CustomItem item){
        return item.getClass().getAnnotation(ItemOverride.class).base();
    }
    public int getDamage(CustomItem item){
        return item.getClass().getAnnotation(ItemOverride.class).damage();
    }
    public ItemStack getItemStack(CustomItem item,int amount){
        ItemStack is=new ItemStack(getBase(item),amount);
        is.setDurability((short)getDamage(item));
        ItemMeta meta=is.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_UNBREAKABLE);
        is.setItemMeta(meta);
        return is;
    }
    public ItemStack getTranslatedItemStack(CustomItem item,int amount,Locale l){
        ItemStack is=getItemStack(item,amount);
        ItemMeta meta=is.getItemMeta();
        meta.setDisplayName(item.getName(l));
        meta.setLore(Arrays.asList(item.getLores(l)));
        is.setItemMeta(meta);
        return is;
    }
    public Location getBlockLoc(Location loc){
        return loc.getBlock().getLocation().add(0.5, 0, 0.5);
    }
    public void place(CustomBlock block,Location loc){
        ArmorStand shell=(ArmorStand) loc.getWorld().spawnEntity(getBlockLoc(loc), EntityType.ARMOR_STAND);
        shell.setGravity(false);
        shell.setVisible(false);
        shell.setSmall(true);
        shell.setSilent(true);
        shell.setInvulnerable(true);
        shell.setHelmet(getItemStack(block,1));
        shell.setCustomNameVisible(false);
        shell.setCustomName(String.format("@@@@@@######mega_custom_block-%d,%d,%d",loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()));
        loc.getBlock().setType(Material.BARRIER);
    }
    @EventHandler
    public void banUsualPickup(PlayerPickupItemEvent ev){
        if(isCustomItem(ev.getItem().getItemStack())) ev.setCancelled(true);
    }
    @EventHandler
    public void handleBreak(PlayerInteractEvent ev){
        if(!ev.hasBlock()) return;
        Block b=ev.getClickedBlock();
        if(b.getType()!=Material.BARRIER) return;
        ArmorStand shell=getCustomBlockShell(b.getLocation());
        if(shell==null) return;
        PacketContainer breakAnim=ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
        breakAnim.getIntegers().write(0, ev.getPlayer().getEntityId())
                .write(1,4);
        breakAnim.getBlockPositionModifier().write(0, new BlockPosition(b.getX(),b.getY(),b.getZ()));
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(ev.getPlayer(), breakAnim);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(CustomItemManager.class.getName()).log(Level.SEVERE, null, ex);
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
    public Class<? extends CustomItem> getCustomItemClass(ItemStack is){
        if (!is.getItemMeta().isUnbreakable()) return null;
        return getCustomItemClass(is.getType(),is.getDurability());
    }
    public ArmorStand getCustomBlockShell(Location loc){
        for(Entity e:loc.getChunk().getEntities()){
            if(!e.isCustomNameVisible()
            &&e.getCustomName().equals(
            String.format("@@@@@@######mega_custom_block-%d,%d,%d",
            loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()))){
                return (ArmorStand) e;
            }
        }
        return null;
    }
    public Class<? extends CustomBlock> getCustomBlockType(Location loc){
        ArmorStand shell=getCustomBlockShell(loc);
        if(shell==null) return null;
        return (Class<? extends CustomBlock>)getCustomItemClass(shell.getHelmet());
    }
    public boolean isCustomItem(ItemStack is){
        return getCustomItemClass(is)!=null;
    }
    
}