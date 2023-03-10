package net.lightskin.farworld.world.type;

import java.lang.reflect.Field;
import java.util.ArrayList;

import io.github.opencubicchunks.cubicchunks.api.util.IntRange;
import io.github.opencubicchunks.cubicchunks.api.world.ICubicWorldType;
import io.github.opencubicchunks.cubicchunks.api.worldgen.ICubeGenerator;
import io.github.opencubicchunks.cubicchunks.cubicgen.asm.mixin.common.accessor.IBiomeProvider;
import io.github.opencubicchunks.cubicchunks.cubicgen.blue.endless.jankson.JsonGrammar;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomGeneratorSettings;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.MinimalCustomizeWorldGui;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.gui.CustomCubicGui;
import io.github.opencubicchunks.cubicchunks.cubicgen.preset.fixer.CustomGeneratorSettingsFixer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.init.Biomes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FarWorldType extends WorldType implements ICubicWorldType{

    protected FarWorldType() {
        super("FarWorld");
    }

    public static FarWorldType create() {
        return new FarWorldType();
    }

    private IBiomeProvider self() {
        return (IBiomeProvider) this;
    }
    //change custom settings for ore generation in underground biomes
    @Override public IntRange calculateGenerationHeightRange(WorldServer world) {
        CustomGeneratorSettings opts = CustomGeneratorSettings.getFromWorld(world);
        // TODO: better handling of min height
        return new IntRange(0, (int) opts.actualHeight);
    }

    @Override public boolean hasCubicGeneratorForWorld(World w) {
        // check if the class of world provider is the same as whatever the Overworld has
        // checking for WorldProviderSurface.class doesn't work in case a mod replaces the overworld WorldProvider
        // causing false negatives
        // and instanceof check fails when a world creates a custom dimension with a WorldProvider that extends WorldProviderSurface
        // creating false positives
        // this is still not an ideal solution as a mod may completely replace the overworld world provider with something
        // completely different where CWG may not even work properly, but this is the best I can come up with that covers
        // most of the cases
        // "w.provider.getDimensionType() == DimensionManager.getProviderType(0)" might be equivalent or better,
        // so it may be changed to that if any more issues are found
        return w.provider.getClass() == DimensionManager.getProvider(0).getClass();
    }

    public BiomeProvider getBiomeProvider(World world) {
        return makeBiomeProvider(world, CustomGeneratorSettings.getFromWorld(world));
    }

    public static BiomeProvider makeBiomeProvider(World world, CustomGeneratorSettings conf) {
        WorldSettings fakeSettings = new WorldSettings(world.getWorldInfo());
        ChunkGeneratorSettings.Factory fakeGenOpts = new ChunkGeneratorSettings.Factory();
        fakeGenOpts.biomeSize = conf.biomeSize;
        fakeGenOpts.riverSize = conf.riverSize;
        fakeSettings.setGeneratorOptions(fakeGenOpts.toString());
        WorldInfo fakeInfo = new WorldInfo(fakeSettings, world.getWorldInfo().getWorldName());
        fakeInfo.setTerrainType(WorldType.CUSTOMIZED);
        Biome biome = Biome.getBiomeForId(conf.biome);
        return conf.biome < 0 ? new BiomeProvider(fakeInfo) : new BiomeProviderSingle(biome == null ? Biomes.OCEAN : biome);
    }

    @Override
    public ICubeGenerator createCubeGenerator(World world) {
        return new FarWorldTerrianGenerator(world, world.getSeed());
    }

    public boolean isCustomizable() {
        return false;
    }

    private static class GenLayerDebug extends GenLayer {

        private final ArrayList<Biome> biomes;
        private int scaleBits;

        GenLayerDebug(int scaleBits) {
            super(0);
            this.scaleBits = scaleBits;

            this.biomes = new ArrayList<>();
            // use reflection to get all biomes
            for (Field fld : Biomes.class.getDeclaredFields()) {
                if (Biome.class.isAssignableFrom(fld.getType())) {
                    try {
                        Biome b = (Biome) fld.get(null);
                        if (b != null) {
                            this.biomes.add(b);
                        }

                    } catch (IllegalAccessException e) {
                        throw new Error(e);
                    }
                }
            }
        }

        @Override
        public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
            int[] biomes = IntCache.getIntCache(areaWidth * areaHeight);
            for (int offY = 0; offY < areaHeight; ++offY) {
                for (int offX = 0; offX < areaWidth; ++offX) {
                    int index = (offX + areaX) >> scaleBits;
                    index = Math.floorMod(index, this.biomes.size());
                    Biome biome = this.biomes.get(index);
                    biomes[offX + offY * areaWidth] = Biome.getIdForBiome(biome);
                }
            }
            return biomes;
        }
    }

}
