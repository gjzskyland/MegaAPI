/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.gjz010.plugins.megaapi.block;

import org.bukkit.Material;
import tk.gjz010.plugins.megaapi.i18n.Locale;

/**
 *
 * @author gjz010
 */
public abstract class CustomItem{
    public abstract String getName(Locale l);
    public abstract String[] getLores(Locale l);
}
