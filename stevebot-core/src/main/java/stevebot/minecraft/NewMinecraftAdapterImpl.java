package stevebot.minecraft;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;
import stevebot.data.blockpos.BaseBlockPos;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.items.ItemUtils;
import stevebot.data.items.wrapper.ItemStackWrapper;
import stevebot.math.vectors.vec2.Vector2d;
import stevebot.math.vectors.vec3.Vector3d;
import stevebot.player.PlayerInputConfig;

public class NewMinecraftAdapterImpl implements NewMinecraftAdapter {

    private MouseChangeInterceptor mouseChangeInterceptor = null;

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
    public void setInput(final PlayerInputConfig inputConfig, final PlayerInputConfig.InputType type, final boolean down) {
        KeyBinding binding = inputConfig.getBinding(type);
        if (binding.isKeyDown() == down) {
            return;
        }
        KeyBinding.setKeyBindState(binding.getKeyCode(), down);
        if (down) {
            KeyBinding.onTick(binding.getKeyCode());
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
            final ItemStack item = inventory.getStackInSlot(i);
            if (item != ItemStack.EMPTY && !item.isEmpty()) {
                items.add(new ItemStackWrapper(ItemUtils.getItemLibrary().getItemByMCItem(item.getItem()), item.getCount(), i));
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
        if(player != null) {
            player.setSprinting(sprint);
        }

    }

}
