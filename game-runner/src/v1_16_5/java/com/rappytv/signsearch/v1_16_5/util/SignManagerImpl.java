package com.rappytv.signsearch.v1_16_5.util;

import com.rappytv.signsearch.SignSearchAddon;
import com.rappytv.signsearch.utils.SignManager;
import com.rappytv.signsearch.utils.SignSearchSettings;
import com.rappytv.signsearch.v1_16_5.util.SignManagerImpl.SignData;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.blockentity.SignBlockEntity.SignSide;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.world.block.BlockPosition;
import net.labymod.api.models.Implements;

@Implements(SignManager.class)
public class SignManagerImpl extends SignManager<SignData> {

    public void onRender(SignBlockEntity tileEntitySign, BlockPosition position) {
        if (!SignSearchAddon.getConfig().enabled().get()) return;
        SignData signData = this.signDataMap.get(position);
        if (signData == null || signData.getLastSignUpdated() + 500L < System.currentTimeMillis()) {
            signData = new SignData(tileEntitySign);
            signDataMap.put(position, signData);
        }
        this.signData = signData;
    }

    public static class SignData extends SignManager.SignData {
        public SignData(SignBlockEntity sign) {
            super(sign);
        }

        @Override
        protected void parseSignData() {
            boolean searchFound;
            StringBuilder fullString = new StringBuilder();
            String[] lines = new String[8];
            for(SignSide side : SignSide.values()) {
                try {
                    Component[] components = this.tileEntitySign.getLines(side);

                    for (int i = 0; i < 4; i++) {
                        String line;
                        Component component = components[i];
                        if (component == null || (line = ((TextComponent) component).getText()) == null) continue;
                        fullString.append(line);
                        lines[i] = line;
                    }
                } catch (Exception ignored) {}
            }
            if (fullString.isEmpty()) return;

            SignSearchSettings settings = SignSearchAddon.getSearchSettings();
            fullString = new StringBuilder(fullString.toString().toLowerCase());
            String searchString = settings.getSearchString().toLowerCase();
            searchFound = searchString.isEmpty() || fullString.toString().contains(searchString);
            if (!searchFound && searchString.contains(",")) {
                for (String word : searchString.split(",")) {
                    if (!fullString.toString().contains(word)) continue;
                    searchFound = true;
                }
            }
            if(settings.isUsingAdvancedOptions()) {
                boolean blacklistFound = !settings.getBlacklistString().isEmpty() && fullString.toString()
                    .contains(settings.getBlacklistString().toLowerCase());
                Integer currentUserCount = this.getUserCount(lines, true);
                Integer maxUserCount = this.getUserCount(lines, false);
                if (searchFound && !blacklistFound) {
                    boolean isFull = settings.areFullServersFiltered() && maxUserCount != null && currentUserCount != null && currentUserCount >= maxUserCount;
                    boolean isEmpty = settings.areEmptyServersFiltered() && currentUserCount != null && currentUserCount == 0;
                    this.signColor = !isEmpty && !isFull ? SignColor.GREEN : isEmpty ? SignColor.GRAY : SignColor.ORANGE;
                } else this.signColor = SignColor.RED;
            } else {
                this.signColor = searchFound ? SignColor.GREEN : SignColor.RED;
            }
        }

        private Integer getUserCount(String[] lines, boolean pre) {
            for (String line : lines) {
                if (line == null || !line.contains("/")) continue;
                String[] parts = line.split("/");
                if (parts.length <= (pre ? 0 : 1)) continue;
                String result = parts[pre ? 0 : 1].replaceAll(" ", "");
                return result.matches("^-?\\d+$") ? Integer.parseInt(result) : null;
            }
            return null;
        }
    }

}
