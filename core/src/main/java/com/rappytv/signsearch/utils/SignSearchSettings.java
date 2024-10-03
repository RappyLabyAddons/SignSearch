package com.rappytv.signsearch.utils;

import net.labymod.api.configuration.loader.property.ConfigProperty;

@SuppressWarnings("unused")
public class SignSearchSettings {
    private final ConfigProperty<Boolean> addonEnabled;
    private String searchString = "";
    private String blacklistString = "";
    private boolean useAdvancedOptions = false;
    private boolean filterFullServer = false;
    private boolean filterEmptyServer = false;

    public SignSearchSettings(ConfigProperty<Boolean> addonEnabled) {
        this.addonEnabled = addonEnabled;
    }

    public boolean isEnabled() {
        return this.addonEnabled.get() && !searchString.isEmpty();
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setBlacklistString(String blacklistString) {
        this.blacklistString = blacklistString;
    }

    public String getBlacklistString() {
        return this.blacklistString;
    }

    public void setUseAdvancedOptions(boolean useAdvancedOptions) {
        this.useAdvancedOptions = useAdvancedOptions;
    }

    public boolean isUsingAdvancedOptions() {
        return this.useAdvancedOptions;
    }

    public void setFilterFullServer(boolean filterFullServer) {
        this.filterFullServer = filterFullServer;
    }

    public boolean areFullServersFiltered() {
        return this.filterFullServer;
    }

    public void setFilterEmptyServer(boolean filterEmptyServer) {
        this.filterEmptyServer = filterEmptyServer;
    }

    public boolean areEmptyServersFiltered() {
        return this.filterEmptyServer;
    }
}