package com.dbkynd.tiltifyrewards.config;

import com.dbkynd.tiltifyrewards.TiltifyRewards;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public Toml read;
    public final
    TomlWriter tomlWriter = new TomlWriter();
    private final File campaignFile;

    public Config() {
        String campaignFileName = "campaign.toml";
        Path configDir = FabricLoader.getInstance().getConfigDir();
        File modConfigDir = new File(configDir.toString() + "/" + TiltifyRewards.MOD_ID);
        modConfigDir.mkdir();
        File campaignFile = new File(modConfigDir + "/" + campaignFileName);

        this.campaignFile = campaignFile;

        if (!campaignFile.exists()) {
            try {
                new FileOutputStream(campaignFile, false).close();
            } catch(IOException error) {
                error.printStackTrace();
            }
            // InputStream is = TiltifyRewards.class.getClassLoader().getResourceAsStream(campaignFileName);
            // Files.copy(is, modConfigDir.toPath());
            // TODO: figure out how to copy from resource folder
        }
        this.read = new Toml().read(campaignFile);
    }

    public void write(String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        try {
            tomlWriter.write(map, campaignFile);
        } catch (IOException error) {
            error.printStackTrace();
        }
        this.read = read.read(campaignFile);
    }

    public Toml read() {
        return this.read;
    }
}
