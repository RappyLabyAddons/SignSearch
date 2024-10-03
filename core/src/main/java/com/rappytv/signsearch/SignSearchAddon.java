package com.rappytv.signsearch;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class SignSearchAddon extends LabyAddon<SignSearchConfiguration> {

    @Override
    protected void enable() {

    }

    @Override
    protected Class<? extends SignSearchConfiguration> configurationClass() {
        return SignSearchConfiguration.class;
    }
}
