package com.vulpeus.mod_relocator;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.io.File;
import java.io.IOException;

public class RelocatorPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        Task relocate = project.getTasks().create("relocate");
        RelocatorExtension settings = project.getExtensions().create("relocator", RelocatorExtension.class);
        relocate.doLast(task -> {
            Mods.DefaultMod mod;
            if (settings.platform.equalsIgnoreCase("fabric")) {
                mod = new Mods.FabricMod(settings.getOriginalJar(), settings.getMergedJar(), settings.getGroup(), settings.getMixins());
            } else if (settings.platform.equalsIgnoreCase("forge")) {
                mod = new Mods.ForgeMod(settings.getOriginalJar(), settings.getMergedJar(), settings.getGroup(), settings.getMixins());
            } else if (settings.platform.equalsIgnoreCase("neoforge")) {
                mod = new Mods.NeoforgeMod(settings.getOriginalJar(), settings.getMergedJar(), settings.getGroup(), settings.getMixins());
            } else if (settings.platform.equalsIgnoreCase("paper")) {
                mod = new Mods.PaperMod(settings.getOriginalJar(), settings.getMergedJar(), settings.getGroup());
            } else {
                return;
            }

            File temporary_dir = project.file("build/tmp/relocator");
            try {
                FileUtils.deleteDirectory(temporary_dir);
                FileUtils.forceMkdir(temporary_dir);
                mod.relocate(temporary_dir.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
