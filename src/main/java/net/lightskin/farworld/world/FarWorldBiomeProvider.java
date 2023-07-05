package net.lightskin.farworld.world;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import io.github.opencubicchunks.cubicchunks.cubicgen.common.biome.CubicBiome;
import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.world.biomes.FarWorldOverworldBiomes;
import net.lightskin.farworld.world.biomes.RegionEnforcer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Biomes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraft.world.storage.WorldInfo;

//TODO: fix biome generation, I broke it again
public class FarWorldBiomeProvider extends BiomeProvider{
	
    private ChunkGeneratorSettings field_190945_a;
    private GenLayer genBiomes;

    /** A GenLayer containing the indices into BiomeGenBase.biomeList[] */
    private GenLayer biomeIndexLayer;

    /** The biome list. */
    private final BiomeCache biomeCache;
    private final List<Biome> biomesToSpawnIn;

    protected FarWorldBiomeProvider()
    {
        this.biomeCache = new FarWorldBiomeCache(this);
        this.biomesToSpawnIn = Lists.newArrayList(Biomes.FOREST, Biomes.PLAINS, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.FOREST_HILLS, Biomes.JUNGLE, Biomes.JUNGLE_HILLS);
    }

    private FarWorldBiomeProvider(long seed, WorldType worldTypeIn, String options)
    {
        this();
        
        if (worldTypeIn == WorldType.CUSTOMIZED && !options.isEmpty())
        {
            this.field_190945_a = ChunkGeneratorSettings.Factory.jsonToFactory(options).build();
        }

        GenLayer[] agenlayer = GenLayer.initializeAllBiomeGenerators(seed, worldTypeIn, this.field_190945_a);
        this.genBiomes = new FarWorldGenLayer(seed, agenlayer[0]);
        this.biomeIndexLayer = agenlayer[1]; //before was just agenlayer[1] just in case this breaks everything-- it did
    }

    public FarWorldBiomeProvider(WorldInfo info)
    {
        this(info.getSeed(), info.getTerrainType(), info.getGeneratorOptions());
    }
    @Override
    public List<Biome> getBiomesToSpawnIn()
    {
        return this.biomesToSpawnIn;
    }

    /**
     * Returns the biome generator
     */
    @Override
    public Biome getBiome(BlockPos pos)
    {
        return this.getBiome(pos, (Biome)null);
    }
    //MARK
    @Override
    public Biome getBiome(BlockPos pos, Biome defaultBiome)
    {
    	Biome tmpa = RegionEnforcer.enforce(pos.getX(),pos.getZ());
    	if(tmpa == null)
    		return this.biomeCache.getBiome(pos.getX(), pos.getZ(), defaultBiome);
    	return tmpa;
    }

    /**
     * Return an adjusted version of a given temperature based on the y height
     */
    @Override
    public float getTemperatureAtHeight(float p_76939_1_, int p_76939_2_)
    {
        return p_76939_1_;
    }
    //MARK
    /**
     * Returns an array of biomes for the location input.
     */
    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
    {
        IntCache.resetIntCache();

        if (biomes == null || biomes.length < width * height)
        {
            biomes = new Biome[width * height];
        }

        int[] aint = this.genBiomes.getInts(x, z, width, height);

        try
        {
        	Biome tmpa = RegionEnforcer.enforce(x,z);
            for (int i = 0; i < width * height; ++i)
            {
            	if(tmpa == null)
            		biomes[i] = Biome.getBiome(aint[i], Biomes.DEFAULT);
            	else
            		biomes[i] = tmpa;
            }

            return biomes;
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
            crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(biomes.length));
            crashreportcategory.addCrashSection("x", Integer.valueOf(x));
            crashreportcategory.addCrashSection("z", Integer.valueOf(z));
            crashreportcategory.addCrashSection("w", Integer.valueOf(width));
            crashreportcategory.addCrashSection("h", Integer.valueOf(height));
            throw new ReportedException(crashreport);
        }
    }

    /**
     * Gets biomes to use for the blocks and loads the other data like temperature and humidity onto the
     * WorldChunkManager.
     */
    @Override
    public Biome[] getBiomes(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth)
    {
        return this.getBiomes(oldBiomeList, x, z, width, depth, true);
    }
    //MARK
    /**
     * Gets a list of biomes for the specified blocks.
     */
    private Biome[] cheap(Biome[] listToReuse, int width, int length, int x, int z, boolean go, Biome b) {
    	int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);
        for (int i = 0; i < width * length; ++i)
        {
        	if(!go)
        		listToReuse[i] = Biome.getBiome(aint[i], Biomes.DEFAULT);
        	else
        		listToReuse[i] = b;
        }
        return listToReuse;
    }
    @Override
    public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag)
    {
        IntCache.resetIntCache();

        if (listToReuse == null || listToReuse.length < width * length)
        {
            listToReuse = new Biome[width * length];
        }
        Biome tmpa = RegionEnforcer.enforce(x,z);
        if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0)
        {
            Biome[] abiome = this.biomeCache.getCachedBiomes(x, z);
            System.arraycopy(abiome, 0, listToReuse, 0, width * length);

            if(tmpa != null)
            	return cheap(listToReuse, width, length, x, z, true, tmpa);
            return listToReuse;
        }
        else
        {
         
            if(tmpa != null)
            	return cheap(listToReuse, width, length, x, z, true, tmpa);

            return cheap(listToReuse, width, length, x, z, false, null);
        }
    }
    //MARK
    /**
     * checks given Chunk's Biomes against List of allowed ones
     */
    @Override
    public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed)
    {
        IntCache.resetIntCache();
        int i = x - radius >> 2;
        int j = z - radius >> 2;
        int k = x + radius >> 2;
        int l = z + radius >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        int[] aint = this.genBiomes.getInts(i, j, i1, j1);

        try
        {
            for (int k1 = 0; k1 < i1 * j1; ++k1)
            {
                Biome biome = Biome.getBiome(aint[k1]);

                if (!allowed.contains(biome))
                {
                    return false;
                }
            }

            return true;
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
            crashreportcategory.addCrashSection("Layer", this.genBiomes.toString());
            crashreportcategory.addCrashSection("x", Integer.valueOf(x));
            crashreportcategory.addCrashSection("z", Integer.valueOf(z));
            crashreportcategory.addCrashSection("radius", Integer.valueOf(radius));
            crashreportcategory.addCrashSection("allowed", allowed);
            throw new ReportedException(crashreport);
        }
    }
    //MARK
    @Override
    @Nullable
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random)
    {
        IntCache.resetIntCache();
        int i = x - range >> 2;
        int j = z - range >> 2;
        int k = x + range >> 2;
        int l = z + range >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        int[] aint = this.genBiomes.getInts(i, j, i1, j1);
        BlockPos blockpos = null;
        int k1 = 0;

        for (int l1 = 0; l1 < i1 * j1; ++l1)
        {
            int i2 = i + l1 % i1 << 2;
            int j2 = j + l1 / i1 << 2;
            Biome biome = Biome.getBiome(aint[l1]);

            if (biomes.contains(biome) && (blockpos == null || random.nextInt(k1 + 1) == 0))
            {
                blockpos = new BlockPos(i2, 0, j2);
                ++k1;
            }
        }

        return blockpos;
    }

    /**
     * Calls the WorldChunkManager's biomeCache.cleanupCache()
     */
    @Override
    public void cleanupCache()
    {
        this.biomeCache.cleanupCache();
    }

}
