/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.gjz010.plugins.megaapi.block;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.bukkit.Material;

/**
 *
 * @author gjz010
 */
@Retention ( value = RetentionPolicy. RUNTIME ) 
public @interface ItemOverride {
    public Material base();
    public int damage();
}
