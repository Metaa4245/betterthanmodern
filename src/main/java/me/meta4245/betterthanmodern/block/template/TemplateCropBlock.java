package me.meta4245.betterthanmodern.block.template;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public abstract class TemplateCropBlock
        extends TemplateBlock
        implements BlockTemplate {
    public TemplateCropBlock(Identifier identifier) {
        super(identifier, Material.PLANT);

        this.setTickRandomly(true);
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(getAgeProperty());
    }

    protected abstract IntProperty getAgeProperty();

    protected int getMaxAge() {
        return getAgeProperty()
                .getValues()
                .stream()
                .max(Integer::compare)
                .orElse(0);
    }

    // abstract methods or argument to constructor?
    protected abstract Item getSeedItem();

    protected abstract int getSeedCount();

    protected abstract int getProductId();

    protected abstract int getProductCount(Random random);

    protected boolean canPlantOnTop(int id) {
        return id == Block.FARMLAND.id;
    }

    private float getAvailableMoisture(World world, int x, int y, int z) {
        float moisture = 1.0F;

        int closeNZ = world.getBlockId(x, y, z - 1);
        int closePZ = world.getBlockId(x, y, z + 1);
        int closeNX = world.getBlockId(x - 1, y, z);
        int closePX = world.getBlockId(x + 1, y, z);
        int closeNXNZ = world.getBlockId(x - 1, y, z - 1);
        int closePXNZ = world.getBlockId(x + 1, y, z - 1);
        int closePXPZ = world.getBlockId(x + 1, y, z + 1);
        int closeNXPZ = world.getBlockId(x - 1, y, z + 1);

        boolean closeXIsCrop = closeNX == this.id || closePX == this.id;
        boolean closeZIsCrop = closeNZ == this.id || closePZ == this.id;
        boolean closeIsCrop = closeNXNZ == this.id
                || closePXNZ == this.id
                || closePXPZ == this.id
                || closeNXPZ == this.id;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = z - 1; j <= z + 1; j++) {
                int blockId = world.getBlockId(i, y - 1, j);

                float deltaMoisture = 0.0F;

                if (blockId == Block.FARMLAND.id) {
                    deltaMoisture = 1.0F;
                    if (world.getBlockMeta(i, y - 1, j) > 0) {
                        deltaMoisture = 3.0F;
                    }
                }

                if (i != x || j != z) {
                    deltaMoisture /= 4.0F;
                }

                moisture += deltaMoisture;
            }
        }

        if (closeIsCrop || closeXIsCrop && closeZIsCrop) {
            moisture /= 2.0F;
        }

        return moisture;
    }

    protected final void breakIfCannotGrow(World world, int x, int y, int z) {
        if (this.canGrow(world, x, y, z)) {
            return;
        }

        this.dropStacks(world, x, y, z, world.getBlockState(x, y, z), 1.0F);
        world.setBlock(x, y, z, 0);
    }

    public void applyFullGrowth(World world, int x, int y, int z) {
        BlockState state = world.getBlockState(x, y, z);
        world.setBlockState(
                x,
                y,
                z,
                state.with(getAgeProperty(), getMaxAge())
        );
        world.setBlockMeta(x, y, z, getMaxAge());
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z) {
        return super.canPlaceAt(world, x, y, z)
                && this.canPlantOnTop(world.getBlockId(x, y - 1, z));
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        super.neighborUpdate(world, x, y, z, id);
        this.breakIfCannotGrow(world, x, y, z);
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        this.breakIfCannotGrow(world, x, y, z);

        if (world.getLightLevel(x, y + 1, z) < 9) {
            return;
        }

        BlockState state = world.getBlockState(x, y, z);
        int age = state.get(getAgeProperty());

        if (age >= getMaxAge()) {
            return;
        }

        float moisture = this.getAvailableMoisture(world, x, y, z);
        if (random.nextInt((int) (100.0F / moisture)) != 0) {
            return;
        }

        world.setBlockState(x, y, z, state.with(getAgeProperty(), ++age));
    }

    public void dropStacks(
            World world,
            int x,
            int y,
            int z,
            BlockState state,
            float luck
    ) {
        int age = state.get(getAgeProperty());

        super.dropStacks(world, x, y, z, age, luck);

        if (world.isRemote) {
            return;
        }

        Item seed = getSeedItem();

        // in cases where there is no seed like carrots, potatoes
        if (seed == null) {
            return;
        }

        for (int i = 0; i < getSeedCount(); i++) {
            // should this be overridden too?
            if (world.random.nextInt(15) > age) {
                continue;
            }

            float deltaX = world.random.nextFloat() * 0.85F;
            float deltaY = world.random.nextFloat() * 0.85F;
            float deltaZ = world.random.nextFloat() * 0.85F;

            ItemEntity itemEntity = new ItemEntity(
                    world,
                    x + deltaX,
                    y + deltaY,
                    z + deltaZ,
                    new ItemStack(seed)
            );
            itemEntity.pickupDelay = 10;

            world.spawnEntity(itemEntity);
        }
    }

    @Override
    public boolean canGrow(World world, int x, int y, int z) {
        return (
                world.getBrightness(x, y, z) >= 8 || world.hasSkyLight(x, y, z)
        ) && this.canPlantOnTop(world.getBlockId(x, y - 1, z));
    }

    @Override
    public Box getCollisionShape(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    // maybe make this call something else to check if we should drop?
    public int getDroppedItemId(int blockMeta, Random random) {
        return blockMeta == getMaxAge() ? getProductId() : -1;
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return getProductCount(random);
    }
}
