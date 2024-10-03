package com.rappytv.signsearch.gui.navigation;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;
import net.labymod.api.client.resources.ResourceLocation;

public class SignSearchNavigationElement extends ScreenNavigationElement {

    private final Icon icon = Icon.texture(ResourceLocation.create(
        "signsearch",
        "textures/sign_search.png"
    ));

    public SignSearchNavigationElement(SignSearchNavigationActivity activity) {
        super(activity);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("signsearch.ui.sign_search");
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public String getWidgetId() {
        return "signsearch";
    }
}
