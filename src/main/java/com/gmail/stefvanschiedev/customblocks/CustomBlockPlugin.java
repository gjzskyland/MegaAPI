package com.gmail.stefvanschiedev.customblocks;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.stefvanschiedev.customblocks.events.listeners.BlockBreakEventListener;
import org.bukkit.event.Listener;

public class CustomBlockPlugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		new BlockBreakEventListener(this);
	}
}