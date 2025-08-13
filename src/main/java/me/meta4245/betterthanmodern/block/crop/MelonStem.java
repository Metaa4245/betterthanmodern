package me.meta4245.betterthanmodern.block.crop;

import me.meta4245.betterthanmodern.block.template.TemplateCropBlock;
import me.meta4245.betterthanmodern.event.BlockRegistry;
import me.meta4245.betterthanmodern.event.ItemRegistry;
import me.meta4245.betterthanmodern.math.ProbabilityTable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.DirectionProperty;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.List;
import java.util.Random;

public class MelonStem extends TemplateCropBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 7);
    public static final DirectionProperty FACING = DirectionProperty.of(
            "facing",
            Direction.UP,
            Direction.WEST,
            Direction.EAST,
            Direction.NORTH,
            Direction.SOUTH
    );
    private static final List<ProbabilityTable<Integer>> seedDrops = List.of(
            // age 0
            new ProbabilityTable<>(
                    new double[]{81.3, 17.42, 1.24, 0.03},
                    new Integer[]{0, 1, 2, 3}
            ),
            // age 1
            new ProbabilityTable<>(
                    new double[]{65.1, 30.04, 4.62, 0.24},
                    new Integer[]{0, 1, 2, 3}
            ),
            // age 2
            new ProbabilityTable<>(
                    new double[]{51.2, 38.4, 9.6, 0.8},
                    new Integer[]{0, 1, 2, 3}
            ),
            // age 3
            new ProbabilityTable<>(
                    new double[]{39.44, 43.02, 15.64, 1.9},
                    new Integer[]{0, 1, 2, 3}
            ),
            // age 4
            new ProbabilityTable<>(
                    new double[]{29.13, 44.44, 22.22, 3.7},
                    new Integer[]{0, 1, 2, 3}
            ),
            // age 5
            new ProbabilityTable<>(
                    new double[]{21.6, 43.2, 28.8, 6.4},
                    new Integer[]{0, 1, 2, 3}
            ),
            // age 6
            new ProbabilityTable<>(
                    new double[]{15.17, 39.82, 34.84, 10.16},
                    new Integer[]{0, 1, 2, 3}
            ),
            // age 7
            new ProbabilityTable<>(
                    new double[]{10.16, 34.84, 39.82, 15.17},
                    new Integer[]{0, 1, 2, 3}
            )
    );

    public MelonStem(Identifier identifier) {
        super(identifier);

        setDefaultState(
                getDefaultState()
                        .with(AGE, 0)
                        .with(FACING, Direction.UP)
        );
    }

    private static boolean isMelon(World world, BlockPos pos) {
        return world.getBlockId(pos.x, pos.y, pos.z) == BlockRegistry.melon.id;
    }

    private static boolean isSuitable(World world, BlockPos pos) {
        return BlockRegistry.melon.canPlaceAt(world, pos.x, pos.y, pos.z);
    }

    private static void placeMelon(World world, BlockPos pos) {
        world.setBlock(pos.x, pos.y, pos.z, BlockRegistry.melon.id);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(FACING);
    }

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected Item getSeedItem() {
        return ItemRegistry.melonSeeds;
    }

    @Override
    protected int getSeedCount(int age) {
        return seedDrops.get(age).roll();
    }

    @Override
    protected int getProductId() {
        return -1;
    }

    @Override
    protected int getProductCount(Random random) {
        return 0;
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        this.breakIfCannotGrow(world, x, y, z);

        if (world.getLightLevel(x, y + 1, z) < 9) {
            return;
        }

        BlockState state = world.getBlockState(x, y, z);
        int age = state.get(getAgeProperty());

        BlockPos pos = new BlockPos(x, y, z);
        BlockPos east = pos.east();
        BlockPos west = pos.west();
        BlockPos north = pos.north();
        BlockPos south = pos.south();

        if (age >= getMaxAge()) {
            boolean melonExists = isMelon(world, east)
                    || isMelon(world, west)
                    || isMelon(world, north)
                    || isMelon(world, south);

            if (melonExists) {
                return;
            }

            boolean eastSuitable = isSuitable(world, east);
            boolean westSuitable = isSuitable(world, west);
            boolean northSuitable = isSuitable(world, north);
            boolean southSuitable = isSuitable(world, south);

            if (eastSuitable) {
                placeMelon(world, east);
                world.setBlockState(x, y, z, state.with(FACING, Direction.EAST));
            } else if (westSuitable) {
                placeMelon(world, west);
                world.setBlockState(x, y, z, state.with(FACING, Direction.WEST));
            } else if (northSuitable) {
                placeMelon(world, north);
                world.setBlockState(x, y, z, state.with(FACING, Direction.NORTH));
            } else if (southSuitable) {
                placeMelon(world, south);
                world.setBlockState(x, y, z, state.with(FACING, Direction.SOUTH));
            }

            return;
        }

        float moisture = this.getAvailableMoisture(world, x, y, z);
        if (random.nextInt((int) (100.0F / moisture)) != 0) {
            return;
        }

        world.setBlockState(x, y, z, state.with(getAgeProperty(), ++age));
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        super.neighborUpdate(world, x, y, z, id);

        BlockState state = world.getBlockState(x, y, z);
        Direction facing = state.get(FACING);

        if (facing == Direction.UP) {
            return;
        }

        BlockPos pos = new BlockPos(x, y, z).offset(facing);
        if (!isMelon(world, pos)) {
            world.setBlockState(x, y, z, state.with(FACING, Direction.UP));
        }
    }
}
