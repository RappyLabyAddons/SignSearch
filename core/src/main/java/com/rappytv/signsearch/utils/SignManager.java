package com.rappytv.signsearch.utils;

import com.rappytv.signsearch.utils.SignManager.SignData;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.world.block.BlockPosition;
import net.labymod.api.reference.annotation.Referenceable;
import java.util.HashMap;
import java.util.Map;

@Referenceable
@SuppressWarnings("unused")
public abstract class SignManager<S extends SignData> {
    protected final Map<BlockPosition, S> signDataMap = new HashMap<>();
    protected S signData;

    public abstract void onRender(SignBlockEntity signEntity, BlockPosition position);
    public S getSignData() {
        return signData;
    }

    public abstract static class SignData {
        protected SignBlockEntity tileEntitySign;
        protected SignColor signColor = SignColor.NONE;
        protected long lastSignUpdated;

        public SignData(SignBlockEntity sign) {
            this.tileEntitySign = sign;
            this.lastSignUpdated = System.currentTimeMillis();
            this.parseSignData();
        }

        protected abstract void parseSignData();

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
