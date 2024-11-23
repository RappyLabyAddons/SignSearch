package com.rappytv.signsearch.listeners;

import com.rappytv.signsearch.SignSearchAddon;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.input.KeyEvent.State;

public class KeyPressListener {

    private final SignSearchAddon addon;

    public KeyPressListener(SignSearchAddon addon) {
        this.addon = addon;
    }

    @Subscribe
    public void onKeyDown(KeyEvent event) {
        if(event.state() != State.PRESS
            || Laby.labyAPI().minecraft().minecraftWindow().isScreenOpened()
            || event.key() != addon.configuration().menuHotkey().get()) return;

        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(addon.getSettingsActivity());
    }

}
