/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.gjz010.plugins.megaapi.block.test;

import org.bukkit.Material;
import tk.gjz010.plugins.megaapi.block.CustomBlock;
import tk.gjz010.plugins.megaapi.block.ItemOverride;
import tk.gjz010.plugins.megaapi.i18n.Locale;

/**
 *
 * @author gjz010
 */
@ItemOverride(base=Material.DIAMOND_HOE,damage=1)
public class MagicOreBlock extends CustomBlock{

    @Override
    public String getName(Locale l) {
        return "魔矿石";
    }

    @Override
    public String[] getLores(Locale l) {
        String lores[]={"Magic Ore that is good."};
        return lores;
    }
    
}
