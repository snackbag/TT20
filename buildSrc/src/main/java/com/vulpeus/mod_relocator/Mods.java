package com.vulpeus.mod_relocator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.lucko.jarrelocator.JarRelocator;
import me.lucko.jarrelocator.Relocation;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Mods {
    private static String readStringFromZipFile(ZipFile zipFile, String path) throws IOException {
        FileHeader fileHeader = zipFile.getFileHeader(path);
        InputStream inputStream = zipFile.getInputStream(fileHeader);
        byte[] b = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len;
        while ((len = inputStream.read(b)) != -1) {
            out.write(b, 0, len);
        }
        out.close();
        inputStream.close();
        return out.toString();
    }
    public static class DefaultMod {
        File input_jar_file;
        File output_jar_file;
        String group;
        String platform;

        public DefaultMod(String platform, File input_jar_file, File output_jar_file, String group) {
            this.platform = platform;
            this.input_jar_file = input_jar_file;
            this.output_jar_file = output_jar_file;
            this.group = group;
        }

        public void relocate(Path temporary_dir) throws IOException {
            List<Relocation> rule = Collections.singletonList(new Relocation(group, platform + "." + group));
            JarRelocator relocator = new JarRelocator(this.input_jar_file, this.output_jar_file, rule);
            relocator.run();
        }
    }
    public static class MixinMod extends DefaultMod {
        List<String> mixins;
        public MixinMod(String platform, File input_jar_file, File output_jar_file, String group, List<String> mixins) {
            super(platform, input_jar_file, output_jar_file, group);
            this.mixins = mixins;
        }
        @Override
        public void relocate(Path temporary_dir) throws IOException {
            super.relocate(temporary_dir);
            ZipFile zipFile = new ZipFile(this.output_jar_file);
            Set<String> refmaps = new HashSet<>();
            for (String mixin : this.mixins) {
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(readStringFromZipFile(zipFile, mixin)).getAsJsonObject();

                if (jsonObject.get("refmap") != null) {
                    String refmap = jsonObject.get("refmap").getAsString();
                    jsonObject.addProperty("refmap", this.platform + "." + refmap);
                    refmaps.add(refmap);
                }

                String package_name = jsonObject.get("package").getAsString();
                jsonObject.addProperty("package", this.platform + "." + package_name);

                Path tmp_mixin = temporary_dir.resolve(mixin);

                Files.write(tmp_mixin, jsonObject.toString().getBytes(StandardCharsets.UTF_8));

                ZipParameters zipParameters = new ZipParameters();
                zipParameters.setFileNameInZip(this.platform + "." + mixin);
                zipFile.addFile(tmp_mixin.toFile(), zipParameters);
                zipFile.removeFile(mixin);
            }

            for (String refmap : refmaps) {
                String group = this.group.replace(".", "/");
                String txt = readStringFromZipFile(zipFile, refmap)
                        .replace(group, this.platform + "/" + group);

                Path tmp_refmap = temporary_dir.resolve(refmap);

                Files.write(tmp_refmap, txt.getBytes(StandardCharsets.UTF_8));

                ZipParameters zipParameters = new ZipParameters();
                zipParameters.setFileNameInZip(this.platform + "." + refmap);
                zipFile.addFile(tmp_refmap.toFile(), zipParameters);
                zipFile.removeFile(refmap);
            }
            zipFile.close();
        }
    }
    public static class FabricMod extends MixinMod {
        public FabricMod(File input_jar_file, File output_jar_file, String group, List<String> mixins) {
            super("fabric", input_jar_file, output_jar_file, group, mixins);
        }
        @Override
        public void relocate(Path temporary_dir) throws IOException {
            super.relocate(temporary_dir);
            ZipFile zipFile = new ZipFile(this.output_jar_file);

            String text = readStringFromZipFile(zipFile, "fabric.mod.json");
            for (String mixin : this.mixins) {
                text = text.replace(mixin, this.platform + "." + mixin);
            }
            text = text.replaceAll(this.group, this.platform + "." + this.group);

            Path fabric_mod_json = temporary_dir.resolve("fabric.mod.json");

            Files.write(fabric_mod_json, text.getBytes(StandardCharsets.UTF_8));

            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setFileNameInZip("fabric.mod.json");
            zipFile.addFile(fabric_mod_json.toFile(), zipParameters);
            zipFile.close();
        }
    }
    public static class ForgeMod extends MixinMod {
        public ForgeMod(File input_jar_file, File output_jar_file, String group, List<String> mixins) {
            super("forge", input_jar_file, output_jar_file, group, mixins);
        }
        @Override
        public void relocate(Path temporary_dir) throws IOException {
            super.relocate(temporary_dir);
            ZipFile zipFile = new ZipFile(this.output_jar_file);

            String text = readStringFromZipFile(zipFile, "META-INF/MANIFEST.MF");
            for (String mixin : this.mixins) {
                text = text.replace(mixin, this.platform + "." + mixin);
            }

            Path manifest_mf = temporary_dir.resolve("MANIFEST.MF");

            Files.write(manifest_mf, text.getBytes(StandardCharsets.UTF_8));

            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setFileNameInZip("META-INF/MANIFEST.MF");
            zipFile.addFile(manifest_mf.toFile(), zipParameters);
            zipFile.close();
        }
    }
    public static class NeoforgeMod extends MixinMod {
        public NeoforgeMod(File input_jar_file, File output_jar_file, String group, List<String> mixins) {
            super("neoforge", input_jar_file, output_jar_file, group, mixins);
        }
        @Override
        public void relocate(Path temporary_dir) throws IOException {
            super.relocate(temporary_dir);
            ZipFile zipFile = new ZipFile(this.output_jar_file);

            String text = readStringFromZipFile(zipFile, "META-INF/neoforge.mods.toml");
            for (String mixin : this.mixins) {
                text = text.replace(mixin, this.platform + "." + mixin);
            }

            Path neoforge_mods_toml = temporary_dir.resolve("neoforge.mods.toml");

            Files.write(neoforge_mods_toml, text.getBytes(StandardCharsets.UTF_8));

            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setFileNameInZip("META-INF/neoforge.mods.toml");
            zipFile.addFile(neoforge_mods_toml.toFile(), zipParameters);
            zipFile.close();
        }
    }
    public static class PaperMod extends DefaultMod {
        public PaperMod(File input_jar_file, File output_jar_file, String group) {
            super("paper", input_jar_file, output_jar_file, group);
        }
        @Override
        public void relocate(Path temporary_dir) throws IOException {
            super.relocate(temporary_dir);
            ZipFile zipFile = new ZipFile(this.output_jar_file);
            String text = readStringFromZipFile(zipFile, "plugin.yml")
                    .replace(this.group, this.platform + "." + this.group);

            Path plugin_yml = temporary_dir.resolve("plugin.yml");

            Files.write(plugin_yml, text.getBytes(StandardCharsets.UTF_8));

            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setFileNameInZip("plugin.yml");
            zipFile.addFile(plugin_yml.toFile(), zipParameters);
            zipFile.close();
        }
    }
}
