package net.lightskin.farworld.world.type;

import java.util.Arrays;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.ToIntFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;
import io.github.opencubicchunks.cubicchunks.api.util.Coords;
import io.github.opencubicchunks.cubicchunks.api.util.CubePos;
import io.github.opencubicchunks.cubicchunks.api.world.ICube;
import io.github.opencubicchunks.cubicchunks.api.worldgen.CubeGeneratorsRegistry;
import io.github.opencubicchunks.cubicchunks.api.worldgen.CubePrimer;
import io.github.opencubicchunks.cubicchunks.api.worldgen.populator.CubePopulatorEvent;
import io.github.opencubicchunks.cubicchunks.api.worldgen.populator.ICubicPopulator;
import io.github.opencubicchunks.cubicchunks.api.worldgen.populator.event.PopulateCubeEvent;
import io.github.opencubicchunks.cubicchunks.api.worldgen.structure.ICubicStructureGenerator;
import io.github.opencubicchunks.cubicchunks.api.worldgen.structure.event.InitCubicStructureGeneratorEvent;
import io.github.opencubicchunks.cubicchunks.api.worldgen.structure.feature.CubicFeatureGenerator;
import io.github.opencubicchunks.cubicchunks.api.worldgen.structure.feature.ICubicFeatureGenerator;
import io.github.opencubicchunks.cubicchunks.cubicgen.BasicCubeGenerator;
import io.github.opencubicchunks.cubicchunks.cubicgen.CustomCubicMod;
import io.github.opencubicchunks.cubicchunks.cubicgen.common.biome.BiomeBlockReplacerConfig;
import io.github.opencubicchunks.cubicchunks.cubicgen.common.biome.CubicBiome;
import io.github.opencubicchunks.cubicchunks.cubicgen.common.biome.IBiomeBlockReplacer;
import io.github.opencubicchunks.cubicchunks.cubicgen.common.biome.SurfaceDefaultReplacer;
import io.github.opencubicchunks.cubicchunks.cubicgen.common.world.storage.IWorldInfoAccess;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomCubicWorldType;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomGeneratorSettings;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomGeneratorSettings.LakeConfig;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomGeneratorSettings.PeriodicGaussianOreConfig;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomGeneratorSettings.StandardOreConfig;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.CustomGeneratorSettings.UserFunction;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.builder.BiomeSource;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.builder.IBuilder;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.builder.NoiseSource;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.structure.CubicCaveGenerator;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.structure.CubicRavineGenerator;
import io.github.opencubicchunks.cubicchunks.cubicgen.customcubic.structure.feature.CubicStrongholdGenerator;
import io.github.opencubicchunks.cubicchunks.cubicgen.preset.wrapper.BiomeDesc;
import net.lightskin.farworld.FarWorld;
import net.lightskin.farworld.world.FarWorldBiomeProvider;
import net.lightskin.farworld.world.WorldRegister;
import net.lightskin.farworld.world.underground.Layer;
import net.lightskin.farworld.world.underground.OreEntry;
import net.lightskin.farworld.world.underground.Region;
import net.lightskin.farworld.world.underground.Section;
import net.lightskin.farworld.world.underground.biomes.TestBiome;
import net.lightskin.farworld.world.underground.gradient.PressureGradient;
import net.lightskin.farworld.world.underground.gradient.StabilityGradient;
import net.lightskin.farworld.world.underground.manager.LayerManager;
import net.lightskin.farworld.world.underground.manager.RegionManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import io.github.opencubicchunks.cubicchunks.api.worldgen.structure.event.InitCubicStructureGeneratorEvent;
import static io.github.opencubicchunks.cubicchunks.api.util.Coords.blockToLocal;

public class FarWorldTerrianGenerator extends BasicCubeGenerator{

	 private static final int CACHE_SIZE_2D = 16 * 16;
	    private static final int CACHE_SIZE_3D = 16 * 16 * 16;
	    private static final ToIntFunction<Vec3i> HASH_2D = (v) -> v.getX() + v.getZ() * 5;
	    private static final ToIntFunction<Vec3i> HASH_3D = (v) -> v.getX() + v.getZ() * 5 + v.getY() * 25;
	    private final Map<CustomGeneratorSettings.IntAABB, FarWorldTerrianGenerator> areaGenerators = new HashMap<>();
	    // Number of octaves for the noise function
	    private IBuilder terrainBuilder;
	    private BiomeSource biomeSource;
	    private CustomGeneratorSettings conf;
	    private final Map<Biome, ICubicPopulator> populators = new HashMap<>();
	    
		public StabilityGradient stability;
		public PressureGradient pressure;

	    private boolean fillCubeBiomes;
	    
	    //static FarWorldProvider test = new FarWorldProvider();

	    //TODO: Implement more structures
	    @Nonnull private ICubicStructureGenerator caveGenerator;
	    @Nonnull private ICubicStructureGenerator ravineGenerator;
	    @Nonnull private ICubicFeatureGenerator strongholds;

	    public FarWorldTerrianGenerator(World world, final long seed) {
	        this(world, new FarWorldBiomeProvider(world.getWorldInfo()), createSettings(), seed);
	    }

	    public FarWorldTerrianGenerator(World world, BiomeProvider biomeProvider, CustomGeneratorSettings settings, final long seed) {
	        this(world, biomeProvider, settings, seed, true);
	    }

	    private FarWorldTerrianGenerator(World world, BiomeProvider biomeProvider, CustomGeneratorSettings settings, final long seed, boolean isMainLayer) {
	        super(world);
	        //test.setWorld(world);
	        init(world, biomeProvider, settings, seed, isMainLayer);
	    }
	    
	    private static CustomGeneratorSettings createSettings() {
	        CustomGeneratorSettings settings = new CustomGeneratorSettings();
	        {
	            settings.standardOres.list.addAll(Arrays.asList(
	                    StandardOreConfig.builder()
	                            .block(Blocks.DIRT.getDefaultState())
	                            .size(33).attempts(10).probability(1f / (256f / ICube.SIZE))
	                            .create(),
	                    StandardOreConfig.builder()
	                            .block(Blocks.GRAVEL.getDefaultState())
	                            .size(33).attempts(8).probability(1f / (256f / ICube.SIZE)).create(),

	                    StandardOreConfig.builder()
	                            .block(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE))
	                            .size(33).attempts(10).probability(256f / 80f / (256f / ICube.SIZE))
	                            .maxHeight((80f - 64f) / 64f).create(),
	                    StandardOreConfig.builder()
	                            .block(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE))
	                            .size(33).attempts(10).probability(256f / 80f / (256f / ICube.SIZE))
	                            .maxHeight((80f - 64f) / 64f).create(),
	                    StandardOreConfig.builder()
	                            .block(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE))
	                            .size(33).attempts(10).probability(256f / 80f / (256f / ICube.SIZE))
	                            .maxHeight((80f - 64f) / 64f).create(),

	                    StandardOreConfig.builder()
	                            .block(Blocks.COAL_ORE.getDefaultState())
	                            .size(17).attempts(20).probability(256f / 128f / (256f / ICube.SIZE))
	                            .maxHeight(1).create(),
	                    StandardOreConfig.builder()
	                            .block(Blocks.IRON_ORE.getDefaultState())
	                            .size(9).attempts(20).probability(256f / 64f / (256f / ICube.SIZE))
	                            .maxHeight(0).create(),
	                            /*
	                    StandardOreConfig.builder()
	                            .block(Blocks.GOLD_ORE.getDefaultState())
	                            .size(9).attempts(2).probability(256f / 32f / (256f / ICube.SIZE))
	                            .maxHeight(-0.5f).create(),
	                    StandardOreConfig.builder()
	                            .block(Blocks.REDSTONE_ORE.getDefaultState())
	                            .size(8).attempts(8).probability(256f / 16f / (256f / ICube.SIZE))
	                            .maxHeight(-0.75f).create(),
	                    StandardOreConfig.builder()
	                            .block(Blocks.DIAMOND_ORE.getDefaultState())
	                            .size(8).attempts(1).probability(256f / 16f / (256f / ICube.SIZE))
	                            .maxHeight(-0.75f)
	                            .create(),*/
	                            
	                    StandardOreConfig.builder()
	                            .block(Blocks.EMERALD_ORE.getDefaultState())
	                            .size(1).attempts(11).probability(0.5f * 256f / 28f / (256f / ICube.SIZE))
	                            .maxHeight(0)
	                            .biomes(Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE, Biomes.EXTREME_HILLS_WITH_TREES, Biomes.MUTATED_EXTREME_HILLS,
	                                    Biomes.MUTATED_EXTREME_HILLS_WITH_TREES).create(),
	                    StandardOreConfig.builder()
	                            .block(Blocks.MONSTER_EGG.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE))
	                            .size(7).attempts(7).probability(256f / 64f / (256f / ICube.SIZE))
	                            .maxHeight(-0.5f)
	                            .biomes(Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_EDGE, Biomes.EXTREME_HILLS_WITH_TREES, Biomes.MUTATED_EXTREME_HILLS,
	                                    Biomes.MUTATED_EXTREME_HILLS_WITH_TREES).create()/*,
	                    StandardOreConfig.builder()
	                            .block(Blocks.GOLD_ORE.getDefaultState())
	                            .size(20).attempts(2).probability(256f / 32f / (256f / ICube.SIZE))
	                            .minHeight(-0.5f).maxHeight(0.25f)
	                            .biomes(Biomes.MESA, Biomes.MESA_CLEAR_ROCK, Biomes.MESA_ROCK, Biomes.MUTATED_MESA, Biomes.MUTATED_MESA_CLEAR_ROCK,
	                                    Biomes.MUTATED_MESA_ROCK).create()*/
	            ));
	            for(Region[] b : RegionManager.regionMap.values()) {
	            	for(Region r : b) {
	            	for(Section a : r.regionalSections()) {
	            	//add ores that appear in all biomes in this layer
	            	if(a.parentOres() != null) //should never happen, really, but just in case
	            	for(OreEntry ore : a.parentOres()) { 
	            		settings.standardOres.list.add(StandardOreConfig.builder()
	            				.block(ore.ore)
	            				.size(ore.size)
	            				.attempts(ore.attempts)
	            				.probability(ore.probability)
	            				.biomes(a.sectionalBiomes())
	            				.maxHeight(1) //shouldn't matter but if things get wonkey check this
	            				.create()
	            				);
	            	}
	            	//literal black magic
	            	for(int i = 0; i < a.sectionalOres().values().size(); i++) {
	            		OreEntry[] special = a.sectionalOres().values().toArray(new OreEntry[a.sectionalOres().size()][])[i];
	            	for(OreEntry ore : special) {
	            		settings.standardOres.list.add(StandardOreConfig.builder()
	            				.block(ore.ore)
	            				.size(ore.size)
	            				.attempts(ore.attempts)
	            				.probability(ore.probability)
	            				.biomes((Biome) a.sectionalOres().keySet().toArray()[i])
	            				.maxHeight(1) //shouldn't matter but if things get wonkey check this
	            				.create()
	            				);
	            	}
	            	}
	            	}
	            	}
	            }
	        }

	        {
	            /*settings.periodicGaussianOres.list.addAll(Arrays.asList(
	                    PeriodicGaussianOreConfig.builder()
	                            .block(Blocks.LAPIS_ORE.getDefaultState())
	                            .size(7).attempts(1).probability(0.933307775f) //resulted by approximating triangular behaviour with bell curve
	                            .heightMean(-0.75f/*first belt at=16/).heightStdDeviation(0.11231704455f/*x64 = 7.1882908513/)
	                            .heightSpacing(3.0f/*192/)
	                            .maxHeight(-0.5f).create()
	            )); note to self, fix the *'s on inline comments*/
	        }
	        {
	           /* settings.lakes.addAll(Arrays.asList(
	                    LakeConfig.builder().setBlock(Blocks.LAVA)
	                            .setBiomes(LakeConfig.BiomeSelectionMode.EXCLUDE, new BiomeDesc[0])
	                            .setMainProbability(UserFunction.builder()
	                                    // same as vanilla for y0-127, probabilities near y=256 are very low, so don't use them
	                                    .point(0, 4 / 263f)
	                                    .point(7, 4 / 263f)
	                                    .point(8, 247 / 16306f)
	                                    .point(62, 193 / 16306f)
	                                    .point(63, 48 / 40765f)
	                                    .point(127, 32 / 40765f)
	                                    .point(128, 32 / 40765f)
	                                    .build())
	                            .setSurfaceProbability(UserFunction.builder()
	                                    // sample vanilla probabilities at y=0, 31, 63, 95, 127
	                                    .point(-1, 19921 / 326120f)
	                                    .point(0, 19921 / 326120f)
	                                    .point(31, 1332 / 40765f)
	                                    .point(63, 579 / 81530f)
	                                    .point(95, 161 / 32612f)
	                                    .point(127, 129 / 40765f)
	                                    .point(128, 129 / 40765f)
	                                    .build())
	                            .build(),
	                    LakeConfig.builder().setBlock(Blocks.WATER)
	                    //put cave biomes that are hot here, maybe
	                            .setBiomes(LakeConfig.BiomeSelectionMode.EXCLUDE, Biomes.DESERT, Biomes.DESERT_HILLS)
	                            .setMainProbability(UserFunction.builder()
	                                    // same as vanilla
	                                    .point(0, 1 / 64f)
	                                    .build())
	                            .setSurfaceProbability(UserFunction.builder()
	                                    // same as vanilla for y=0-128, probabilities get too low at 2xx heights so dont use them
	                                    .point(-1, 0.25f)
	                                    .point(0, 0.25f)
	                                    .point(128, 0.125f)
	                                    .point(129, 0.125f)
	                                    .build())
	                            .build()
	            ));*/
	        }
	        return settings;
	    }

	    public void reloadPreset(String settings) {
	        ((IWorldInfoAccess) world.getWorldInfo()).setGeneratorOptions(settings);
	        world.provider.setWorld(world);// this re-creates biome provider
	        init(world, world.getBiomeProvider(), createSettings(), world.getSeed(), true);
	    }

	    private void init(World world, BiomeProvider biomeProvider, CustomGeneratorSettings settings, long seed, boolean isMainLayer) {
	    	this.conf = settings;

	        this.populators.clear();

	        for (Biome biome : ForgeRegistries.BIOMES) {
	            CubicBiome cubicBiome = CubicBiome.getCubic(biome);
	            populators.put(biome, cubicBiome.getDecorator(conf));
	        }

	        InitCubicStructureGeneratorEvent caveEvent = new InitCubicStructureGeneratorEvent(EventType.CAVE, new CubicCaveGenerator());
	        InitCubicStructureGeneratorEvent strongholdsEvent = new InitCubicStructureGeneratorEvent(EventType.STRONGHOLD, new CubicStrongholdGenerator(conf));
	        InitCubicStructureGeneratorEvent ravineEvent = new InitCubicStructureGeneratorEvent(EventType.RAVINE, new CubicRavineGenerator(conf));

	        MinecraftForge.TERRAIN_GEN_BUS.post(caveEvent);
	        MinecraftForge.TERRAIN_GEN_BUS.post(strongholdsEvent);
	        MinecraftForge.TERRAIN_GEN_BUS.post(ravineEvent);

	        this.caveGenerator = caveEvent.getNewGen();
	        this.strongholds = (CubicFeatureGenerator) strongholdsEvent.getNewGen();
	        this.ravineGenerator = ravineEvent.getNewGen();

	        this.fillCubeBiomes = !isMainLayer;
	        this.biomeSource = new BiomeSource(world, conf.createBiomeBlockReplacerConfig(), biomeProvider, 2);
	        initGenerator(seed);
	        
	        this.areaGenerators.clear();

	        if (settings.cubeAreas != null) {
	            for (Map.Entry<CustomGeneratorSettings.IntAABB, CustomGeneratorSettings> entry : settings.cubeAreas.map) {
	                this.areaGenerators.put(entry.getKey(), new FarWorldTerrianGenerator(world, FarWorldType.makeBiomeProvider(world,
	                        entry.getValue()), entry.getValue(), seed, false));
	            }
	        }
	    }

	    private void initGenerator(long seed) {
	        Random rnd = new Random(seed);

	        IBuilder selector = NoiseSource.perlin()
	                .seed(rnd.nextLong())
	                .normalizeTo(-1, 1)
	                .frequency(conf.selectorNoiseFrequencyX, conf.selectorNoiseFrequencyY, conf.selectorNoiseFrequencyZ)
	                .octaves(conf.selectorNoiseOctaves)
	                .create()
	                .mul(conf.selectorNoiseFactor).add(conf.selectorNoiseOffset).clamp(0, 1);

	        IBuilder low = NoiseSource.perlin()
	                .seed(rnd.nextLong())
	                .normalizeTo(-1, 1)
	                .frequency(conf.lowNoiseFrequencyX, conf.lowNoiseFrequencyY, conf.lowNoiseFrequencyZ)
	                .octaves(conf.lowNoiseOctaves)
	                .create()
	                .mul(conf.lowNoiseFactor).add(conf.lowNoiseOffset);

	        IBuilder high = NoiseSource.perlin()
	                .seed(rnd.nextLong())
	                .normalizeTo(-1, 1)
	                .frequency(conf.highNoiseFrequencyX, conf.highNoiseFrequencyY, conf.highNoiseFrequencyZ)
	                .octaves(conf.highNoiseOctaves)
	                .create()
	                .mul(conf.highNoiseFactor).add(conf.highNoiseOffset);

	        IBuilder randomHeight2d = NoiseSource.perlin()
	                .seed(rnd.nextLong())
	                .normalizeTo(-1, 1)
	                .frequency(conf.depthNoiseFrequencyX, 0, conf.depthNoiseFrequencyZ)
	                .octaves(conf.depthNoiseOctaves)
	                .create()
	                .mul(conf.depthNoiseFactor).add(conf.depthNoiseOffset)
	                .mulIf(IBuilder.NEGATIVE, -0.3).mul(3).sub(2).clamp(-2, 1)
	                .divIf(IBuilder.NEGATIVE, 2 * 2 * 1.4).divIf(IBuilder.POSITIVE, 8)
	                .mul(0.2 * 17 / 64.0)
	                .cached2d(CACHE_SIZE_2D, HASH_2D);

	        IBuilder height = ((IBuilder) biomeSource::getHeight)
	                .mul(conf.heightFactor)
	                .add(conf.heightOffset);

	        double specialVariationFactor = conf.specialHeightVariationFactorBelowAverageY;
	        IBuilder volatility = ((IBuilder) biomeSource::getVolatility)
	                .mul((x, y, z) -> height.get(x, y, z) > y ? specialVariationFactor : 1)
	                .mul(conf.heightVariationFactor)
	                .add(conf.heightVariationOffset);

	        this.terrainBuilder = selector
	                .lerp(low, high).add(randomHeight2d).mul(volatility).add(height)
	                .sub(volatility.signum().mul((x, y, z) -> y))
	                .cached(CACHE_SIZE_3D, HASH_3D);
	        
	        stability = new StabilityGradient(rnd.nextLong());
	        pressure = new PressureGradient(rnd.nextLong());
	    }

	    @Override
	    public CubePrimer generateCube(int cubeX, int cubeY, int cubeZ) { // legacy method
	        return this.generateCube(cubeX, cubeY, cubeZ, new CubePrimer());
	    }

	    @Override
	    public CubePrimer generateCube(int cubeX, int cubeY, int cubeZ, CubePrimer primer) {
	        if (!areaGenerators.isEmpty()) {
	            for (CustomGeneratorSettings.IntAABB aabb : areaGenerators.keySet()) {
	                if (!aabb.contains(cubeX, cubeY, cubeZ)) {
	                    continue;
	                }
	                return areaGenerators.get(aabb).generateCube(cubeX, cubeY, cubeZ, primer);
	            }
	        }
	        generate(primer, cubeX, cubeY, cubeZ);
	        generateStructures(primer, new CubePos(cubeX, cubeY, cubeZ));
	        if (fillCubeBiomes || cubeY <= 0) {
	            fill3dBiomes(cubeX, cubeY, cubeZ, primer);
	        }
	        return primer;
	    }
	    
	    private void fill3dBiomes(int cubeX, int cubeY, int cubeZ, CubePrimer primer) {
	        int minX = cubeX * 4;
	        int minY = cubeY * 4;
	        int minZ = cubeZ * 4;
	        for (int dx = 0; dx < 4; dx++) {
	            for (int dy = 0; dy < 4; dy++) {
	                for (int dz = 0; dz < 4; dz++) {
	    	        	Layer tmp = LayerManager.getLayer(cubeY);
    	        		Region r = null;
	    	        	if(tmp != null) {
	    		        	r = RegionManager.getRegion(new CubePos(cubeX,cubeY,cubeZ), world, 
		    	        			stability.getValue(new CubePos(cubeX,cubeY,cubeZ)), pressure.getValue(new CubePos(cubeX,cubeY,cubeZ)));
	    	        		if(r != null) {
	    	        			Section s = r.getSection(cubeY);
	    	        			if(s != null) {
	    	        				primer.setBiome(dx, dy, dz, s.sectionalBiome(pressure.getValue(new CubePos(cubeX,cubeY,cubeZ))));
	    	        			}
	    	        			else
	    	        				primer.setBiome(dx, dy, dz, r.refrenceBiome());
	    	        		}
	    	        		else
	    	        			primer.setBiome(dx, dy, dz, tmp.refrenceBiome());
	    	        	}
	    	        	else
	                    primer.setBiome(dx, dy, dz,
	                            biomeSource.getBiome(minX + dx * 4, minY + dy * 4, minZ + dz * 4).getBiome());
	        	    	
	                }
	            }
	        }
	    }

	    @Override 
	    public void populate(ICube cube) {
	        if (!areaGenerators.isEmpty()) {
	            for (CustomGeneratorSettings.IntAABB aabb : areaGenerators.keySet()) {
	                if (!aabb.contains(cube.getX(), cube.getY(), cube.getZ())) {
	                    continue;
	                }
	                areaGenerators.get(aabb).populate(cube);
	                return;
	            }
	        }

	        /**
	         * If event is not canceled we will use default biome decorators and
	         * cube populators from registry.
	         **/
	        if (!MinecraftForge.EVENT_BUS.post(new CubePopulatorEvent(world, cube))) {
	        	CubicBiome cubicBiome;
	        	//TODO: figure out if we need this (and if its causing the weird generation issues)
	        	Layer tmp = LayerManager.getLayer(cube.getY());
	        	Region r = null;
	        	Section s = null;
	        	if(tmp != null) {
	        		r = RegionManager.getRegion(cube.getCoords(), world, stability.getValue(cube), pressure.getValue(cube));
	        		if(r != null) {
	        		s = r.getSection(cube.getY());
	        		if(s != null)
	        			cubicBiome = CubicBiome.getCubic(s.sectionalBiome(pressure.getValue(cube)));
	        		else
	        			cubicBiome = CubicBiome.getCubic(r.refrenceBiome());
	        		}
	        		else
	        			cubicBiome = CubicBiome.getCubic(tmp.refrenceBiome());
	        	}
	            cubicBiome = CubicBiome.getCubic(WorldRegister.muskagBiome);
	        	CubePos pos = cube.getCoords();
	            // For surface generators we should actually use special RNG with
	            // seed
	            // that depends only in world seed and cube X/Z
	            // but using this for surface generation doesn't cause any
	            // noticeable issues
	            Random rand = Coords.coordsSeedRandom(cube.getWorld().getSeed(), cube.getX(), cube.getY(), cube.getZ());

	            MinecraftForge.EVENT_BUS.post(new PopulateCubeEvent.Pre(world, rand, pos.getX(), pos.getY(), pos.getZ(), false));
	            strongholds.generateStructure(world, rand, pos);
	            populators.get(cubicBiome.getBiome()).generate(world, rand, pos, cubicBiome.getBiome());
	            MinecraftForge.EVENT_BUS.post(new PopulateCubeEvent.Post(world, rand, pos.getX(), pos.getY(), pos.getZ(), false));
	            CubeGeneratorsRegistry.generateWorld(world, rand, pos, cubicBiome.getBiome()); 
		        //implement filler block overriding now that everything is already generated
	            //(maybe check for if biome generate in section as well [if null])
	            if(s != null)
	            	s.fill(cube, pressure.getValue(cube));
	            else if(r != null)
	            	r.fill(cube);
	            else if(tmp != null)
	            	tmp.fill(cube);
	        }
	    }

	    @Override
	    public void recreateStructures(ICube cube) {
	        this.strongholds.generate(world, null, cube.getCoords());
	    }

	    @Nullable @Override
	    public BlockPos getClosestStructure(String name, BlockPos pos, boolean findUnexplored) {
	        if ("Stronghold".equals(name)) {
	            return strongholds.getNearestStructurePos((World) world, pos, true);
	        }
	        return null;
	    }

	    /**
	     * Generate the cube as the specified location
	     *
	     * @param cubePrimer cube primer to use
	     * @param cubeX cube x location
	     * @param cubeY cube y location
	     * @param cubeZ cube z location
	     */
	    private void generate(final CubePrimer cubePrimer, int cubeX, int cubeY, int cubeZ) {
	        // when debugging is enabled, allow reloading generator settings after pressing L
	        // no need to restart after applying changes.
	        // Seed it changed to some constant because world isn't easily accessible here
	        if (CustomCubicMod.DEBUG_ENABLED && FMLCommonHandler.instance().getSide().isClient() && Keyboard.isKeyDown(Keyboard.KEY_L)) {
	            initGenerator(42);
	        }

	        BlockPos start = new BlockPos(cubeX * 4, cubeY * 2, cubeZ * 4);
	        BlockPos end = start.add(4, 2, 4);
	        terrainBuilder.forEachScaled(start, end, new Vec3i(4, 8, 4),
	                (x, y, z, dx, dy, dz, v) ->
	                        cubePrimer.setBlockState(
	                                blockToLocal(x), blockToLocal(y), blockToLocal(z),
	                                getBlock(x, y, z, dx, dy, dz, v))
	        );

	    }

		/**
	     * Retrieve the blockstate appropriate for the specified builder entry
	     *
	     * @return The block state
	     */
	    private IBlockState getBlock(int x, int y, int z, double dx, double dy, double dz, double density) {
	        IBlockState block = Blocks.AIR.getDefaultState();
		    List<IBiomeBlockReplacer> replacers = biomeSource.getReplacers(x, y, z);
		    int size = replacers.size();
		    for (int i = 0; i < size; i++) {
		    	block = replacers.get(i).getReplacedBlock(block, x, y, z, dx, dy, dz, density);
		    }
	        return block;
	    }

	    public void generateStructures(CubePrimer cube, CubePos cubePos) {
	        // generate world populator
	        if (this.conf.caves) {
	        	Layer tmp = LayerManager.getLayer(cubePos.getY());
	        	if(tmp != null) {
	        		tmp.refrenceCaves().generate(world, cube, cubePos);
	        	}
	        	else
	        		this.caveGenerator.generate(world, cube, cubePos);
	        }
	        if (this.conf.ravines) {
	        	Layer tmp = LayerManager.getLayer(cubePos.getY());
	        	if(tmp != null) {
	        		tmp.refrenceRavines(conf).generate(world, cube, cubePos);
	        		tmp.special(cube, cubePos);
	        	}
	        	else
	        		this.ravineGenerator.generate(world, cube, cubePos);
	        }
	        if (this.conf.strongholds) {
	            this.strongholds.generate(world, cube, cubePos);
	        }
	    }

	    public final ICubicStructureGenerator getCaveGenerator() {
	        return caveGenerator;
	    }

	    public final ICubicFeatureGenerator getStrongholds() {
	        return strongholds;
	    }

	    public final ICubicStructureGenerator getRavineGenerator() {
	        return ravineGenerator;
	    }

	    public CustomGeneratorSettings getConfig() {
	        return conf;
	    }

	    public Map<Biome, ICubicPopulator> getPopulators() {
	        return populators;
	    }

}
