package stevebot;


import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import stevebot.commands.CommandSystem;
import stevebot.commands.StevebotCommands;
import stevebot.data.blocks.BlockLibrary;
import stevebot.data.blocks.BlockProvider;
import stevebot.data.blocks.BlockUtils;
import stevebot.data.items.ItemLibrary;
import stevebot.data.items.ItemUtils;
import stevebot.events.EventListener;
import stevebot.events.EventManager;
import stevebot.events.EventManagerImpl;
import stevebot.events.ModEventProducer;
import stevebot.events.PostInitEvent;
import stevebot.minecraft.MinecraftAdapter;
import stevebot.minecraft.MinecraftAdapterImpl;
import stevebot.misc.Config;
import stevebot.pathfinding.PathHandler;
import stevebot.player.PlayerCamera;
import stevebot.player.PlayerInput;
import stevebot.player.PlayerInventory;
import stevebot.player.PlayerInventoryImpl;
import stevebot.player.PlayerMovement;
import stevebot.player.PlayerMovementImpl;
import stevebot.player.PlayerUtils;
import stevebot.rendering.Renderer;

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

    private final EventListener<PostInitEvent> listenerPostInit = new EventListener<PostInitEvent>() {
        @Override
        public Class<PostInitEvent> getEventClass() {
            return PostInitEvent.class;
        }

        @Override
        public void onEvent(PostInitEvent event) {
            blockLibrary.onEventInitialize();
            itemLibrary.onEventInitialize();
        }
    };

    private final EventListener<BlockEvent.BreakEvent> listenerBreakBlock = new EventListener<BlockEvent.BreakEvent>() {
        @Override
        public Class<BlockEvent.BreakEvent> getEventClass() {
            return BlockEvent.BreakEvent.class;
        }


        @Override
        public void onEvent(BlockEvent.BreakEvent event) {
            blockProvider.getBlockCache().onEventBlockBreak(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
        }
    };

    private final EventListener<BlockEvent.PlaceEvent> listenerPlaceBlock = new EventListener<BlockEvent.PlaceEvent>() {
        @Override
        public Class<BlockEvent.PlaceEvent> getEventClass() {
            return BlockEvent.PlaceEvent.class;
        }


        @Override
        public void onEvent(BlockEvent.PlaceEvent event) {
            blockProvider.getBlockCache().onEventBlockPlace(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
        }
    };

    private final EventListener<TickEvent.RenderTickEvent> listenerRenderTick = new EventListener<TickEvent.RenderTickEvent>() {
        @Override
        public Class<TickEvent.RenderTickEvent> getEventClass() {
            return TickEvent.RenderTickEvent.class;
        }

        @Override
        public void onEvent(TickEvent.RenderTickEvent event) {
            playerCamera.onRenderTickEvent(event.phase == TickEvent.Phase.START);
        }
    };

    private final EventListener<RenderWorldLastEvent> listenerRenderWorld = new EventListener<RenderWorldLastEvent>() {
        @Override
        public Class<RenderWorldLastEvent> getEventClass() {
            return RenderWorldLastEvent.class;
        }

        @Override
        public void onEvent(RenderWorldLastEvent event) {
            renderer.onEventRender();
        }
    };

    private final EventListener<TickEvent.PlayerTickEvent> listenerPlayerTick = new EventListener<TickEvent.PlayerTickEvent>() {
        @Override
        public Class<TickEvent.PlayerTickEvent> getEventClass() {
            return TickEvent.PlayerTickEvent.class;
        }


        @Override
        public void onEvent(TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.START) {
                playerInput.onEventPlayerTick();
            }
        }
    };

    private final EventListener<ConfigChangedEvent.PostConfigChangedEvent> listenerConfigChanged = new EventListener<ConfigChangedEvent.PostConfigChangedEvent>() {
        @Override
        public Class<ConfigChangedEvent.PostConfigChangedEvent> getEventClass() {
            return ConfigChangedEvent.PostConfigChangedEvent.class;
        }


        @Override
        public void onEvent(ConfigChangedEvent.PostConfigChangedEvent event) {
            playerInput.onEventConfigChanged();
        }
    };


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        setup();
        Stevebot.eventProducer.onPreInit();
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        eventProducer.onInit();
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        eventProducer.onPostInit();
        itemLibrary.insertBlocks(blockLibrary.getAllBlocks());
        blockLibrary.insertItems(itemLibrary.getAllItems());
    }


    private void setup() {

        // minecraft
        MinecraftAdapter minecraftAdapter = new MinecraftAdapterImpl();

        // events
        eventManager = new EventManagerImpl();
        eventProducer = new ModEventProducer(eventManager);

        eventManager.addListener(listenerPostInit);
        eventManager.addListener(listenerBreakBlock);
        eventManager.addListener(listenerPlaceBlock);
        eventManager.addListener(listenerRenderTick);
        eventManager.addListener(listenerRenderWorld);
        eventManager.addListener(listenerPlayerTick);
        eventManager.addListener(listenerConfigChanged);

        // block library
        blockLibrary = new BlockLibrary(minecraftAdapter);

        // block provider
        blockProvider = new BlockProvider(minecraftAdapter, blockLibrary);

        // block utils
        BlockUtils.initialize(minecraftAdapter, blockProvider, blockLibrary);

        // item library
        itemLibrary = new ItemLibrary(minecraftAdapter);

        // item utils
        ItemUtils.initialize(itemLibrary);

        // renderer
        renderer = new Renderer(minecraftAdapter, blockProvider);

        // player camera
        playerCamera = new PlayerCamera(minecraftAdapter);

        // player input
        playerInput = new PlayerInput(minecraftAdapter);

        // player movement
        playerMovement = new PlayerMovementImpl(playerInput, playerCamera);

        // player inventory
        playerInventory = new PlayerInventoryImpl();

        // player utils
        PlayerUtils.initialize(minecraftAdapter, playerInput, playerCamera, playerMovement, playerInventory);

        // path handler
        pathHandler = new PathHandler(minecraftAdapter, eventManager, renderer);

        // commands
        StevebotCommands.initialize(new StevebotApi(pathHandler));
        CommandSystem.registerCommands();
    }

}

