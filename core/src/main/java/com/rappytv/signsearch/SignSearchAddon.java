package com.rappytv.signsearch;

import com.rappytv.signsearch.gui.settings.SignSearchSettingsActivity;
import com.rappytv.signsearch.listeners.KeyPressListener;
import com.rappytv.signsearch.listeners.PauseMenuListener;
import com.rappytv.signsearch.utils.SignManager;
import com.rappytv.signsearch.utils.SignSearchSettings;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class SignSearchAddon extends LabyAddon<SignSearchConfiguration> {

    private static SignSearchConfiguration config;
    private static SignManager signManager;
    private static SignSearchSettings searchSettings;
    private SignSearchSettingsActivity settingsActivity;

    @Override
    protected void enable() {
        registerSettingCategory();
        config = configuration();
        signManager = new SignManager();
        searchSettings = new SignSearchSettings(config.enabled());
        settingsActivity = new SignSearchSettingsActivity(searchSettings);
        registerListener(new KeyPressListener(this));
        registerListener(new PauseMenuListener(settingsActivity));
    }

    @Override
    protected Class<? extends SignSearchConfiguration> configurationClass() {
        return SignSearchConfiguration.class;
    }

    public static SignSearchConfiguration getConfig() {
        return config;
    }

    public static SignManager getSignManager() {
        return signManager;
    }

    public static SignSearchSettings getSearchSettings() {
        return searchSettings;
    }

    public SignSearchSettingsActivity getSettingsActivity() {
        return settingsActivity;
    }
}
