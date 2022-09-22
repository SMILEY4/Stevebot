package stevebot.minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.blocks.BlockWrapper;
import stevebot.data.items.ItemUtils;
import stevebot.data.items.wrapper.ItemBlockWrapper;
import stevebot.data.items.wrapper.ItemStackWrapper;
import stevebot.data.items.wrapper.ItemWrapper;
import stevebot.math.vectors.vec2.Vector2d;
import stevebot.math.vectors.vec3.Vector3d;
import stevebot.pathfinding.actions.ActionCosts;
import stevebot.player.PlayerInputConfig;

public class NewMinecraftAdapterImpl implements NewMinecraftAdapter {

    class McInputBinding implements PlayerInputConfig.InputBinding {

        private final KeyBinding binding;

        public McInputBinding(final KeyBinding binding) {
            this.binding = binding;
        }

        @Override
        public int getKeyCode() {
            return binding.getKeyCode();
        }

        @Override
        public boolean isDown() {
            return binding.isKeyDown();
        }

    }

    private MouseChangeInterceptor mouseChangeInterceptor = null;

    private final Map<String, Item> items = new HashMap<>();
    private final Map<String, Block> blocks = new HashMap<>();

    public NewMinecraftAdapterImpl() {
        getMinecraft().mouseHelper = new MouseHelper() {
            @Override
            public void mouseXYChange() {
                if (mouseChangeInterceptor == null || mouseChangeInterceptor.onChange()) {
                    super.mouseXYChange();
                }
            }
        };
    }


    private Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }


    private World getWorld() {
        return getMinecraft().world;
    }


    @Override
    public int getBlockId(final BaseBlockPos pos) {
        final Block block = getWorld().getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
        return Block.REGISTRY.getIDForObject(block);
    }

    @Override
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return getWorld().getChunkFromChunkCoords(chunkX, chunkZ).isLoaded();
    }

    @Override
    public Vector3d getPlayerHeadPosition() {
        final Vec3d posEyes = getMinecraft().player.getPositionEyes(1.0F);
        return new Vector3d(posEyes.x, posEyes.y, posEyes.z);
    }

    @Override
    public Vector2d getPlayerHeadPositionXZ() {
        final Vec3d posEyes = getMinecraft().player.getPositionEyes(1.0F);
        return new Vector2d(posEyes.x, posEyes.z);
    }

    @Override
    public BaseBlockPos getPlayerBlockPosition() {
        final EntityPlayerSP player = getMinecraft().player;
        if (player != null) {
            return BlockUtils.toBaseBlockPos(player.posX, player.posY, player.posZ);
        } else {
            return null;
        }
    }

    @Override
    public Vector3d getPlayerPosition() {
        final EntityPlayerSP player = getMinecraft().player;
        if (player != null) {
            return new Vector3d(player.posX, player.posY, player.posZ);
        } else {
            return null;
        }
    }

    @Override
    public Vector3d getPlayerMotion() {
        final EntityPlayerSP player = getMinecraft().player;
        if (player != null) {
            return new Vector3d(player.motionX, player.motionY, player.motionZ);
        } else {
            return null;
        }
    }

    @Override
    public void setPlayerRotation(float yaw, float pitch) {
        final EntityPlayerSP player = getMinecraft().player;
        if (player != null) {
            player.rotationPitch = pitch;
            player.rotationYaw = yaw;
        }
    }

    @Override
    public void setCameraRotation(float yaw, float pitch) {
        Entity camera = getMinecraft().getRenderViewEntity();
        if (camera != null) {
            camera.rotationYaw = yaw;
            camera.rotationPitch = pitch;
            camera.prevRotationYaw = yaw;
            camera.prevRotationPitch = pitch;
        }
    }

    @Override
    public void addCameraRotation(float yaw, float pitch) {
        EntityPlayerSP player = getMinecraft().player;
        Entity camera = getMinecraft().getRenderViewEntity();
        if (camera != null && player != null) {
            camera.rotationYaw = player.rotationYaw + yaw;
            camera.rotationPitch = player.rotationPitch + pitch;
            camera.prevRotationYaw = player.prevRotationYaw + yaw;
            camera.prevRotationPitch = player.prevRotationPitch + pitch;
        }
    }

    @Override
    public Vector3d getLookDir() {
        final EntityPlayerSP player = getMinecraft().player;
        if (player != null) {
            Vec3d lookDir = player.getLook(0f);
            return new Vector3d(lookDir.x, lookDir.y, lookDir.z);
        } else {
            return null;
        }
    }

    @Override
    public void setMouseChangeInterceptor(MouseChangeInterceptor interceptor) {
        this.mouseChangeInterceptor = interceptor;
    }

    @Override
    public float getMouseSensitivity() {
        return getMinecraft().gameSettings.mouseSensitivity;
    }

    @Override
    public boolean hasPlayer() {
        return getMinecraft().player != null;
    }

    @Override
    public float getPlayerRotationYaw() {
        return getMinecraft().player.rotationYaw;
    }

    @Override
    public float getPlayerRotationPitch() {
        return getMinecraft().player.rotationPitch;
    }

    @Override
    public double getMouseDX() {
        return Mouse.getDX();
    }

    @Override
    public double getMouseDY() {
        return Mouse.getDY();
    }

    @Override
    public GameSettings getGameSettings() {
        return getMinecraft().gameSettings;
    }

    @Override
    public void setInput(final int keyCode, final boolean down) {
        KeyBinding.setKeyBindState(keyCode, down);
        if (down) {
            KeyBinding.onTick(keyCode);
        }
    }

    @Override
    public void sendMessage(final String msg) {
        final EntityPlayerSP player = getMinecraft().player;
        if (player != null) {
            player.sendMessage(new TextComponentString(msg));
        }
    }

    @Override
    public List<ItemStackWrapper> getHotbarItems() {
        final List<ItemStackWrapper> items = new ArrayList<>();
        final InventoryPlayer inventory = getMinecraft().player.inventory;
        for (int i = 0; i < 9; i++) {
            final ItemStack itemStack = inventory.getStackInSlot(i);
            if (itemStack != ItemStack.EMPTY && !itemStack.isEmpty()) {
                final int itemId = Item.REGISTRY.getIDForObject(itemStack.getItem());
                items.add(new ItemStackWrapper(ItemUtils.getItemLibrary().getItemById(itemId), itemStack.getCount(), i));
            }
        }
        return items;
    }

    public void selectHotbarSlot(int slot) {
        final InventoryPlayer inventory = getMinecraft().player.inventory;
        inventory.currentItem = slot;
    }

    @Override
    public boolean isPlayerOnGround() {
        final EntityPlayerSP player = getMinecraft().player;
        return player.onGround;
    }

    @Override
    public float getPlayerHealth() {
        final EntityPlayerSP player = getMinecraft().player;
        return player.getHealth();
    }

    @Override
    public void setPlayerSprinting(final boolean sprint) {
        final EntityPlayerSP player = getMinecraft().player;
        if (player != null) {
            player.setSprinting(sprint);
        }
    }

    @Override
    public List<BlockWrapper> getBlocks() {
        this.blocks.clear();
        final List<BlockWrapper> blocks = new ArrayList<>();
        for (Block block : Block.REGISTRY) {
            final String name = Block.REGISTRY.getNameForObject(block).toString();
            final int id = Block.REGISTRY.getIDForObject(block);
            final boolean isNormalCube = block.getDefaultState().isNormalCube();
            blocks.add(new BlockWrapper(id, name, isNormalCube));
            this.blocks.put(name, block);
        }
        return blocks;
    }

    @Override
    public List<ItemWrapper> getItems() {
        this.items.clear();
        final List<ItemWrapper> items = new ArrayList<>();
        for (Item item : Item.REGISTRY) {
            String name = Item.REGISTRY.getNameForObject(item).toString();
            int id = Item.REGISTRY.getIDForObject(item);
            items.add(new ItemWrapper(id, name));
            this.items.put(name, item);
        }
        return items;
    }

    @Override
    public float getBreakDuration(ItemWrapper item, BlockWrapper block) {
        final Block mcBlock = blocks.get(block.getName());
        if (item == null) {
            return getBreakDuration(ItemStack.EMPTY, mcBlock.getDefaultState());
        } else {
            final Item mcItem = items.get(item.getName());
            return getBreakDuration(new ItemStack(mcItem), mcBlock.getDefaultState());
        }
    }

    @Override
    public String getBlockFacing(final BaseBlockPos position) {
        final IBlockState blockState = getWorld().getBlockState(new BlockPos(position.getX(), position.getY(), position.getZ()));
        final EnumFacing.Axis facing = blockState.getValue(BlockHorizontal.FACING).getAxis();
        return facing.getName();
    }

    @Override
    public boolean isDoorOpen(final BaseBlockPos position) {
        final IBlockState blockState = getWorld().getBlockState(new BlockPos(position.getX(), position.getY(), position.getZ()));
        return blockState.getValue(BlockDoor.OPEN);
    }

    @Override
    public boolean isBlockPassable(final BlockWrapper block, final BaseBlockPos pos) {
        return blocks.get(block.getName()).isPassable(getWorld(), new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
    }

    @Override
    public int getItemIdFromBlock(final BlockWrapper block) {
        final Item itemFromBlock = Item.getItemFromBlock(blocks.get(block.getName()));
        return Item.REGISTRY.getIDForObject(itemFromBlock);
    }

    @Override
    public int getBlockIdFromItem(final ItemBlockWrapper item) {
        final ItemBlock itemBlock = (ItemBlock) items.get(item.getName());
        return Block.REGISTRY.getIDForObject(itemBlock.getBlock());
    }

    @Override
    public PlayerInputConfig.InputBinding getKeyBinding(final PlayerInputConfig.InputType inputType) {
        GameSettings settings = getGameSettings();
        switch (inputType) {
            case WALK_FORWARD:
                return new McInputBinding(settings.keyBindForward);
            case WALK_BACKWARD:
                return new McInputBinding(settings.keyBindBack);
            case WALK_LEFT:
                return new McInputBinding(settings.keyBindLeft);
            case WALK_RIGHT:
                return new McInputBinding(settings.keyBindRight);
            case SPRINT:
                return new McInputBinding(settings.keyBindSprint);
            case SNEAK:
                return new McInputBinding(settings.keyBindSneak);
            case JUMP:
                return new McInputBinding(settings.keyBindJump);
            case PLACE_BLOCK:
                return new McInputBinding(settings.keyBindUseItem);
            case BREAK_BLOCK:
                return new McInputBinding(settings.keyBindAttack);
            case INTERACT:
                return new McInputBinding(settings.keyBindUseItem);
        }
        return null;
    }

    /**
     * @param itemStack the used item
     * @param state     the block to break
     * @return the time (in ticks) it takes to break the given block with the given item
     */
    private static float getBreakDuration(ItemStack itemStack, IBlockState state) {
        // sources:
        // https://greyminecraftcoder.blogspot.com/2015/01/calculating-rate-of-damage-when-mining.html
        // https://minecraft.gamepedia.com/Breaking
        final float blockHardness = state.getBlockHardness(null, null);
        if (blockHardness < 0) {
            return (float) ActionCosts.get().COST_INFINITE;
        }
        final float playerBreakSpeed = getDigSpeed(itemStack, state);
        final int canHarvestMod = (itemStack != null && itemStack.canHarvestBlock(state)) ? 30 : 100;
        final float dmgPerTick = ((playerBreakSpeed / blockHardness) * (1f / canHarvestMod));
        return 1f / dmgPerTick;
    }


    private static float getDigSpeed(ItemStack itemStack, IBlockState state) {
        return getDigSpeed(itemStack, state, false, 0, false, 0, 0, false, false, true);
    }


    /**
     * @param itemStack the used tool or null for hand
     *                  see {@link net.minecraft.entity.player.EntityPlayer#getDigSpeed(IBlockState, BlockPos)}
     */
    private static float getDigSpeed(
            ItemStack itemStack,
            IBlockState state,
            boolean hasEffectHaste,
            int effectHasteAmplifier,
            boolean hasEffectMiningFatigue,
            int effectMiningFatigueAmplifier,
            int efficiencyModifier,
            boolean aquaAffinityModifier,
            boolean isInsideWater,
            boolean isOnGround
    ) {

        float f = getDestroySpeed(itemStack, state);

        if (f > 1.0F) {
            int i = efficiencyModifier;
            if (i > 0 && (itemStack != null && !itemStack.isEmpty())) {
                f += (float) (i * i + 1);
            }
        }

        if (hasEffectHaste) {
            f *= 1.0F + (float) (effectHasteAmplifier + 1) * 0.2F;
        }

        if (hasEffectMiningFatigue) {
            float f1;
            switch (effectMiningFatigueAmplifier) {
                case 0: {
                    f1 = 0.3F;
                    break;
                }
                case 1: {
                    f1 = 0.09F;
                    break;
                }
                case 2: {
                    f1 = 0.0027F;
                    break;
                }
                case 3:
                default: {
                    f1 = 8.1E-4F;
                }
            }
            f *= f1;
        }

        if (isInsideWater && !aquaAffinityModifier) {
            f /= 5.0F;
        }

        if (!isOnGround) {
            f /= 5.0F;
        }

        return (f < 0 ? 0 : f);
    }


    /**
     * @param itemStack the used tool or null for hand
     * @param state     the block
     */
    private static float getDestroySpeed(ItemStack itemStack, IBlockState state) {
        float f = 1.0F;
        if (itemStack != null && !itemStack.isEmpty()) {
            f *= itemStack.getDestroySpeed(state);
        }
        return f;
    }

}
