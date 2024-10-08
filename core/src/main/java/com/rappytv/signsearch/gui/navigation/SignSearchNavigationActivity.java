package com.rappytv.signsearch.gui.navigation;

import com.rappytv.signsearch.utils.SignSearchSettings;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget.State;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@Link("sign-search.lss")
@AutoActivity
public class SignSearchNavigationActivity extends SimpleActivity {

    private final SignSearchSettings settings;

    public SignSearchNavigationActivity(SignSearchSettings settings) {
        this.settings = settings;
    }

    @Override
    public void initialize(Parent parent) {
        super.initialize(parent);

        VerticalListWidget<Widget> content = new VerticalListWidget<>()
            .addId("input-container");

        HorizontalListWidget header = new HorizontalListWidget()
            .addId("header")
            .addId("row");

        DivWidget searchDiv = new DivWidget()
            .addId("input")
            .addId("search-wrapper");

        ComponentWidget searchLabel = ComponentWidget.i18n("signsearch.ui.label.search")
            .addId("input-label");

        TextFieldWidget searchInput = new TextFieldWidget()
            .addId("input-item");
        searchInput.setText(settings.getSearchString());
        searchInput.updateListener(settings::setSearchString);

        searchDiv.addChild(searchLabel);
        searchDiv.addChild(searchInput);

        DivWidget advancedSearchDiv = new DivWidget()
            .addId("checkbox-div");

        ComponentWidget advancedSearchLabel = ComponentWidget.i18n("signsearch.ui.label.advanced")
            .addId("checkbox-label");

        CheckBoxWidget advancedSearchCheck = new CheckBoxWidget()
            .addId("checkbox-item");
        advancedSearchCheck.setState(settings.isUsingAdvancedOptions() ? State.CHECKED : State.UNCHECKED);
        advancedSearchCheck.setPressable(() ->
            settings.setUseAdvancedOptions(advancedSearchCheck.state() == State.CHECKED)
        );

        advancedSearchDiv.addChild(advancedSearchLabel);
        advancedSearchDiv.addChild(advancedSearchCheck);

        ComponentWidget advancedOptionsLabel = ComponentWidget.i18n("signsearch.ui.label.advanced_label")
            .addId("advanced-options-label");

        DivWidget blacklistDiv = new DivWidget()
            .addId("row");

        ComponentWidget blacklistLabel = ComponentWidget.i18n("signsearch.ui.label.blacklist")
            .addId("input-label");

        TextFieldWidget blacklistInput = new TextFieldWidget()
            .addId("input-item");
        blacklistInput.setText(settings.getBlacklistString());
        blacklistInput.updateListener(settings::setBlacklistString);

        blacklistDiv.addChild(blacklistLabel);
        blacklistDiv.addChild(blacklistInput);

        DivWidget filterEmptyServersDiv = new DivWidget()
            .addId("checkbox-div");
        DivWidget filterFullServersDiv = new DivWidget()
            .addId("checkbox-div");

        ComponentWidget filterEmptyServersLabel = ComponentWidget.i18n("signsearch.ui.label.emptyFilter")
            .addId("checkbox-label");

        CheckBoxWidget filterEmptyServersCheck = new CheckBoxWidget()
            .addId("checkbox-item");
        filterEmptyServersCheck.setState(settings.areEmptyServersFiltered() ? State.CHECKED : State.UNCHECKED);
        filterEmptyServersCheck.setPressable(() ->
            settings.setFilterEmptyServer(filterEmptyServersCheck.state() == State.CHECKED)
        );

        ComponentWidget filterFullServersLabel = ComponentWidget.i18n("signsearch.ui.label.fullFilter")
            .addId("checkbox-label");

        CheckBoxWidget filterFullServersCheck = new CheckBoxWidget()
            .addId("checkbox-item");
        filterFullServersCheck.setState(settings.areFullServersFiltered() ? State.CHECKED : State.UNCHECKED);
        filterFullServersCheck.setPressable(() ->
            settings.setFilterFullServer(filterFullServersCheck.state() == State.CHECKED)
        );

        filterEmptyServersDiv.addChild(filterEmptyServersLabel);
        filterEmptyServersDiv.addChild(filterEmptyServersCheck);
        filterFullServersDiv.addChild(filterFullServersLabel);
        filterFullServersDiv.addChild(filterFullServersCheck);

        HorizontalListWidget checkBoxList = new HorizontalListWidget()
            .addId("checkbox-list");
        checkBoxList.addEntry(filterEmptyServersDiv);
        checkBoxList.addEntry(filterFullServersDiv);

        FlexibleContentWidget inputWidget = new FlexibleContentWidget()
            .addId("input-list");

        header.addEntry(searchDiv);
        header.addEntry(advancedSearchDiv);

        inputWidget.addContent(header);
        inputWidget.addContent(advancedOptionsLabel);
        inputWidget.addContent(blacklistDiv);
        inputWidget.addContent(checkBoxList);

        content.addChild(inputWidget);
        document.addChild(content);
    }
}
