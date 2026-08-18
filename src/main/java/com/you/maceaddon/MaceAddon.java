package com.you.maceaddon;

import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaceAddon extends MeteorAddon {
    public static final Logger LOG = LoggerFactory.getLogger(MaceAddon.class);

    @Override
    public void onInitialize() {
        Modules.get().add(new MaceClickerModule());
        LOG.info("Mace Addon initialized!");
    }

    @Override
    public String getPackage() {
        return "com.you.maceaddon";
    }
}
