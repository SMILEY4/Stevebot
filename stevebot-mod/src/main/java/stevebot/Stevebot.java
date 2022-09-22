package stevebot;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import stevebot.commands.CommandSystem;
import stevebot.commands.StevebotCommands;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.BlockLibraryImpl;
import stevebot.data.blocks.BlockProvider;
import stevebot.data.blocks.BlockProviderImpl;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.items.ItemLibrary;
import stevebot.data.items.ItemLibraryImpl;
import stevebot.data.items.ItemUtils;
import stevebot.events.EventManager;
import stevebot.events.EventManagerImpl;
import stevebot.events.ModEventProducer;
import stevebot.minecraft.MinecraftAdapter;
import stevebot.minecraft.MinecraftAdapterImpl;
import stevebot.misc.Config;
import stevebot.pathfinding.PathHandler;
import stevebot.player.PlayerCamera;
import stevebot.player.PlayerCameraImpl;
import stevebot.player.PlayerInput;
import stevebot.player.PlayerInputImpl;
import stevebot.player.PlayerInventory;
import stevebot.player.PlayerInventoryImpl;
import stevebot.player.PlayerMovement;
import stevebot.player.PlayerMovementImpl;
import stevebot.player.PlayerUtils;
import stevebot.rendering.Renderer;
import stevebot.rendering.RendererImpl;

@Mod(
        modid = Config.MODID,
        name = Config.NAME,
        version = Config.VERSION,
        acceptedMinecraftVersions = Config.MC_VERSION
)
public class Stevebot {

    private static EventManager eventManager;
    private static ModEventProducer eventProducer;
    private static BlockLibrary blockLibrary;
    private static BlockProvider blockProvider;
    private static ItemLibrary itemLibrary;
    private static PlayerCamera playerCamera;
    private static PlayerMovement playerMovement;
    private static PlayerInput playerInput;
    private static PlayerInventory playerInventory;
    private static Renderer renderer;
    private static PathHandler pathHandler;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        setup();
        Stevebot.eventProducer.onPreInit();
    }


    private void setup() {

        // minecraft
        MinecraftAdapter.initialize(new MinecraftAdapterImpl());

        // events
        Stevebot.eventManager = new EventManagerImpl();
        Stevebot.eventProducer = new ModEventProducer(Stevebot.eventManager);

        // block library
        Stevebot.blockLibrary = new BlockLibraryImpl();
        eventManager.addListener(Stevebot.blockLibrary.getListener());

        // block provider
        Stevebot.blockProvider = new BlockProviderImpl(Stevebot.blockLibrary);
        eventManager.addListener(Stevebot.blockProvider.getBlockCache().getListenerBreakBlock());
        eventManager.addListener(Stevebot.blockProvider.getBlockCache().getListenerPlaceBlock());

        // block utils
        BlockUtils.initialize(blockProvider, blockLibrary);

        // item library
        Stevebot.itemLibrary = new ItemLibraryImpl();
        eventManager.addListener(Stevebot.itemLibrary.getListener());

        // item utils
        ItemUtils.initialize(Stevebot.itemLibrary);

        // renderer
        Stevebot.renderer = new RendererImpl(blockProvider);
        eventManager.addListener(Stevebot.renderer.getListener());

        // player camera
        Stevebot.playerCamera = new PlayerCameraImpl();
        eventManager.addListener(Stevebot.playerCamera.getListener());

        // player input
        Stevebot.playerInput = new PlayerInputImpl();
        eventManager.addListener(Stevebot.playerInput.getPlayerTickListener());
        eventManager.addListener(Stevebot.playerInput.getConfigChangedListener());

        // player movement
        Stevebot.playerMovement = new PlayerMovementImpl(Stevebot.playerInput, Stevebot.playerCamera);

        // player inventory
        Stevebot.playerInventory = new PlayerInventoryImpl();

        // player utils
        PlayerUtils.initialize(playerInput, playerCamera, playerMovement, playerInventory);

        // path handler
        Stevebot.pathHandler = new PathHandler(Stevebot.eventManager, Stevebot.renderer);

        // commands
        StevebotCommands.initialize(Stevebot.pathHandler);
        CommandSystem.registerCommands();
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Stevebot.eventProducer.onInit();
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Stevebot.eventProducer.onPostInit();
        Stevebot.itemLibrary.insertBlocks(Stevebot.blockLibrary.getAllBlocks());
        Stevebot.blockLibrary.insertItems(Stevebot.itemLibrary.getAllItems());
    }

}

