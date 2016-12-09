/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.gjz010.plugins.megaapi.block;

import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.gjz010.plugins.megaapi.i18n.Locale;

/**
 *
 * @author gjz010
 */
public class CustomItemManager {
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
        meta.setDisplayName(item.getName(Locale.ENGLISH));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_UNBREAKABLE);
        meta.setLore(Arrays.asList(item.getLores(Locale.ENGLISH)));
        return is;
    }
    public Location getBlockLoc(Location loc){
        return loc.getBlock().getLocation();
    }
    public void place(CustomBlock block,Location loc){
        ArmorStand shell=(ArmorStand) loc.getWorld().spawnEntity(getBlockLoc(loc), EntityType.ARMOR_STAND);
        shell.setGravity(false);
        shell.setVisible(false);
        shell.setSmall(true);
        shell.setSilent(true);
        shell.setInvulnerable(true);
        shell.setHelmet(getItemStack(block,1));
        getBlockLoc(loc).getBlock().setType(Material.BARRIER);
    }
}