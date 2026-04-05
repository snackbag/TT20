package com.vulpeus.mod_relocator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RelocatorExtension {
    String group;
    File mergedJar;
    File originalJar;
    String platform;
    String outputDir = "Merged";
    List<String> mixins = new ArrayList<>();

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public File getMergedJar() {
        return mergedJar;
    }

    public void setMergedJar(File mergedJar) {
        this.mergedJar = mergedJar;
    }

    public File getOriginalJar() {
        return originalJar;
    }

    public void setOriginalJar(File originalJar) {
        this.originalJar = originalJar;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public List<String> getMixins() {
        return mixins;
    }

    public void setMixins(List<String> mixins) {
        this.mixins = mixins;
    }
}
