package com.rappytv.signsearch.listeners;

import com.rappytv.signsearch.gui.settings.SignSearchSettingsActivity;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.IngameMenuInitializeEvent;

public class PauseMenuListener {

    private final Icon icon = Icon.texture(ResourceLocation.create(
        "signsearch",
        "textures/sign_search.png"
    ));
    private final SignSearchSettingsActivity activity;

    public PauseMenuListener(SignSearchSettingsActivity activity) {
        this.activity = activity;
    }

    @Subscribe
    public void onMenuOpen(IngameMenuInitializeEvent event) {
        event.addLeftButton(
            Component.translatable("signsearch.ui.title"),
            icon,
            () -> Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity)
        );
    }

}
