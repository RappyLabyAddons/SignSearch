package com.rappytv.signsearch.utils;

import com.rappytv.signsearch.SignSearchAddon;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.blockentity.SignBlockEntity.SignSide;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.world.block.BlockPosition;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class SignManager {
    protected final Map<BlockPosition, SignData> signDataMap = new HashMap<>();
    protected SignData signData;

    public void onRender(SignBlockEntity signEntity, BlockPosition position) {
        if (!SignSearchAddon.getConfig().enabled().get()) return;
        SignData signData = this.signDataMap.get(position);
        if (signData == null || signData.getLastSignUpdated() + 500L < System.currentTimeMillis()) {
            signData = new SignData(signEntity);
            signDataMap.put(position, signData);
        }
        this.signData = signData;
    }

    public SignData getSignData() {
        return signData;
    }

    public static class SignData {
        protected SignBlockEntity tileEntitySign;
        protected SignColor signColor = SignColor.NONE;
        protected long lastSignUpdated;

        public SignData(SignBlockEntity sign) {
            this.tileEntitySign = sign;
            this.lastSignUpdated = System.currentTimeMillis();
            this.parseSignData();
        }

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

        public SignBlockEntity getTileEntitySign() {
            return this.tileEntitySign;
        }

        public SignColor getSignColor() {
            return this.signColor;
        }

        public long getLastSignUpdated() {
            return this.lastSignUpdated;
        }

        public void setTileEntitySign(SignBlockEntity tileEntitySign) {
            this.tileEntitySign = tileEntitySign;
        }

        public void setSignColor(SignColor signColor) {
            this.signColor = signColor;
        }

        public void setLastSignUpdated(long lastSignUpdated) {
            this.lastSignUpdated = lastSignUpdated;
        }

        public enum SignColor {
            NONE(1.0f, 1.0f, 1.0f, 1.0f),
            GREEN(0.6f, 1.0f, 0.6f, 1.0f),
            RED(1.0f, 0.6f, 0.6f, 1.0f),
            ORANGE(1.0f, 1.0f, 0.6f, 1.0f),
            GRAY(0.6f, 0.6f, 0.6f, 1.0f);

            private final float red;
            private final float green;
            private final float blue;
            private final float alpha;

            SignColor(float red, float green, float blue, float alpha) {
                this.red = red;
                this.green = green;
                this.blue = blue;
                this.alpha = alpha;
            }

            public float getRed() {
                return this.red;
            }

            public float getGreen() {
                return this.green;
            }

            public float getBlue() {
                return this.blue;
            }

            public float getAlpha() {
                return this.alpha;
            }
        }
    }
}
