package com.rappytv.signsearch;

import com.rappytv.signsearch.core.generated.DefaultReferenceStorage;
import com.rappytv.signsearch.utils.SignManager;
import com.rappytv.signsearch.utils.SignSearchSettings;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class SignSearchAddon extends LabyAddon<SignSearchConfiguration> {

    private static SignSearchConfiguration config;
    private static SignManager<?> signManager;
    private static SignSearchSettings searchSettings;

    @Override
    protected void enable() {
        registerSettingCategory();
        config = configuration();
        signManager = ((DefaultReferenceStorage) this.referenceStorageAccessor()).signManager();
        searchSettings = new SignSearchSettings(config.enabled());
    }

    @Override
    protected Class<? extends SignSearchConfiguration> configurationClass() {
        return SignSearchConfiguration.class;
    }

    public static SignSearchConfiguration getConfig() {
        return config;
    }

    public static SignManager<?> getSignManager() {
        return signManager;
    }

    public static SignSearchSettings getSearchSettings() {
        return searchSettings;
    }
}
