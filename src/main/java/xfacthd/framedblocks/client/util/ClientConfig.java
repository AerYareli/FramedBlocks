package xfacthd.framedblocks.client.util;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import xfacthd.framedblocks.api.model.SolidFrameMode;
import xfacthd.framedblocks.api.predicate.contex.ConTexMode;
import xfacthd.framedblocks.api.util.CamoMessageVerbosity;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.client.screen.overlay.BlockInteractOverlay;

public final class ClientConfig
{
    public static final ForgeConfigSpec SPEC;
    public static final ClientConfig INSTANCE;

    private static final String KEY_SHOW_GHOST_BLOCKS = "showGhostBlocks";
    private static final String KEY_ALT_GHOST_RENDERER = "altGhostRenderer";
    private static final String KEY_FANCY_HITBOXES = "fancyHitboxes";
    private static final String KEY_DETAILED_CULLING = "detailedCulling";
    private static final String KEY_USE_DISCRETE_UV_STEPS = "discreteUVSteps";
    private static final String KEY_CON_TEX_MODE = "conTexMode";
    private static final String KEY_CAMO_MESSAGE_VERBOSITY = "camoMessageVerbosity";
    private static final String KEY_FORCE_AO_ON_GLOWING_BLOCKS = "forceAoOnGlowingBlocks";
    private static final String KEY_SHOW_ALL_RECIPE_PERMUTATIONS_IN_EMI = "showAllRecipePermutationsInEmi";
    private static final String KEY_SOLID_FRAME_MODE = "solidFrameMode";
    private static final String KEY_SHOW_BUTTON_PLATE_OVERLAY = "showButtonPlateTypeOverlay";
    private static final String KEY_SHOW_SPECIAL_CUBE_OVERLAY = "showSpecialCubeTypeOverlay";
    private static final String KEY_STATE_LOCK_MODE = "stateLockMode";
    private static final String KEY_TOGGLE_WATERLOG_MODE = "toggleWaterlogMode";
    private static final String KEY_TOGGLE_Y_SLOPE_MODE = "toggleYSlopeMode";
    private static final String KEY_REINFORCEMENT_MODE = "reinforcedMode";
    private static final String KEY_PRISM_OFFSET_MODE = "prismOffsetMode";
    private static final String KEY_SPLIT_LINES_MODE = "splitLineMode";
    private static final String KEY_ONE_WAY_WINDOW_MODE = "oneWayWindowMode";
    private static final String KEY_FRAME_BACKGROUND_MODE = "itemFrameBackgroundMode";
    private static final String KEY_CAMO_ROTATION_MODE = "camoRotationMode";

    public static final String TRANSLATION_SHOW_GHOST_BLOCKS = translate(KEY_SHOW_GHOST_BLOCKS);
    public static final String TRANSLATION_ALT_GHOST_RENDERER = translate(KEY_ALT_GHOST_RENDERER);
    public static final String TRANSLATION_FANCY_HITBOXES = translate(KEY_FANCY_HITBOXES);
    public static final String TRANSLATION_DETAILED_CULLING = translate(KEY_DETAILED_CULLING);
    public static final String TRANSLATION_USE_DISCRETE_UV_STEPS = translate(KEY_USE_DISCRETE_UV_STEPS);
    public static final String TRANSLATION_CON_TEX_MODE = translate(KEY_CON_TEX_MODE);
    public static final String TRANSLATION_CAMO_MESSAGE_VERBOSITY = translate(KEY_CAMO_MESSAGE_VERBOSITY);
    public static final String TRANSLATION_FORCE_AO_ON_GLOWING_BLOCKS = translate(KEY_FORCE_AO_ON_GLOWING_BLOCKS);
    public static final String TRANSLATION_SHOW_ALL_RECIPE_PERMUTATIONS_IN_EMI = translate(KEY_SHOW_ALL_RECIPE_PERMUTATIONS_IN_EMI);
    public static final String TRANSLATION_SOLID_FRAME_MODE = translate(KEY_SOLID_FRAME_MODE);
    public static final String TRANSLATION_SHOW_BUTTON_PLATE_OVERLAY = translate(KEY_SHOW_BUTTON_PLATE_OVERLAY);
    public static final String TRANSLATION_SHOW_SPECIAL_CUBE_OVERLAY = translate(KEY_SHOW_SPECIAL_CUBE_OVERLAY);
    public static final String TRANSLATION_STATE_LOCK_MODE = translate(KEY_STATE_LOCK_MODE);
    public static final String TRANSLATION_TOGGLE_WATERLOG_MODE = translate(KEY_TOGGLE_WATERLOG_MODE);
    public static final String TRANSLATION_TOGGLE_Y_SLOPE_MODE = translate(KEY_TOGGLE_Y_SLOPE_MODE);
    public static final String TRANSLATION_REINFORCEMENT_MODE = translate(KEY_REINFORCEMENT_MODE);
    public static final String TRANSLATION_PRISM_OFFSET_MODE = translate(KEY_PRISM_OFFSET_MODE);
    public static final String TRANSLATION_SPLIT_LINES_MODE = translate(KEY_SPLIT_LINES_MODE);
    public static final String TRANSLATION_ONE_WAY_WINDOW_MODE = translate(KEY_ONE_WAY_WINDOW_MODE);
    public static final String TRANSLATION_FRAME_BACKGROUND_MODE = translate(KEY_FRAME_BACKGROUND_MODE);
    public static final String TRANSLATION_CAMO_ROTATION_MODE = translate(KEY_CAMO_ROTATION_MODE);

    private static final String COMMENT_OVERLAY_HIDDEN = "If set to HIDDEN, the %s overlay will be completely hidden";
    private static final String COMMENT_OVERLAY_ICON = "If set to ICON, the %s overlay will only show an icon";
    private static final String COMMENT_OVERLAY_DETAILED = "If set to DETAILED, the %s overlay will show detailed info";

    public static boolean showGhostBlocks = false;
    public static boolean altGhostRenderer = false;
    public static boolean fancyHitboxes = false;
    public static boolean detailedCulling = false;
    public static boolean useDiscreteUVSteps = false;
    public static ConTexMode conTexMode = ConTexMode.FULL_FACE;
    public static CamoMessageVerbosity camoMessageVerbosity = CamoMessageVerbosity.DEFAULT;
    public static boolean forceAoOnGlowingBlocks = false;
    public static boolean showAllRecipePermutationsInEmi = false;
    public static SolidFrameMode solidFrameMode = SolidFrameMode.DEFAULT;
    public static boolean showButtonPlateOverlay = false;
    public static boolean showSpecialCubeOverlay = false;
    public static BlockInteractOverlay.Mode stateLockMode;
    public static BlockInteractOverlay.Mode toggleWaterlogMode;
    public static BlockInteractOverlay.Mode toggleYSlopeMode;
    public static BlockInteractOverlay.Mode reinforcementMode;
    public static BlockInteractOverlay.Mode prismOffsetMode;
    public static BlockInteractOverlay.Mode splitLineMode;
    public static BlockInteractOverlay.Mode oneWayWindowMode;
    public static BlockInteractOverlay.Mode frameBackgroundMode;
    public static BlockInteractOverlay.Mode camoRotationMode;

    private final ForgeConfigSpec.BooleanValue showGhostBlocksValue;
    private final ForgeConfigSpec.BooleanValue altGhostRendererValue;
    private final ForgeConfigSpec.BooleanValue fancyHitboxesValue;
    private final ForgeConfigSpec.BooleanValue detailedCullingValue;
    private final ForgeConfigSpec.BooleanValue useDiscreteUVStepsValue;
    private final ForgeConfigSpec.EnumValue<ConTexMode> conTexModeValue;
    private final ForgeConfigSpec.EnumValue<CamoMessageVerbosity> camoMessageVerbosityValue;
    private final ForgeConfigSpec.BooleanValue forceAoOnGlowingBlocksValue;
    private final ForgeConfigSpec.BooleanValue showAllRecipePermutationsInEmiValue;
    private final ForgeConfigSpec.EnumValue<SolidFrameMode> solidFrameModeValue;
    private final ForgeConfigSpec.BooleanValue showButtonPlateOverlayValue;
    private final ForgeConfigSpec.BooleanValue showSpecialCubeOverlayValue;

    private final ForgeConfigSpec.EnumValue<BlockInteractOverlay.Mode> stateLockModeValue;
    private final ForgeConfigSpec.EnumValue<BlockInteractOverlay.Mode> toggleWaterlogModeValue;
    private final ForgeConfigSpec.EnumValue<BlockInteractOverlay.Mode> toggleYSlopeModeValue;
    private final ForgeConfigSpec.EnumValue<BlockInteractOverlay.Mode> reinforcementModeValue;
    private final ForgeConfigSpec.EnumValue<BlockInteractOverlay.Mode> prismOffsetModeValue;
    private final ForgeConfigSpec.EnumValue<BlockInteractOverlay.Mode> splitLineModeValue;
    private final ForgeConfigSpec.EnumValue<BlockInteractOverlay.Mode> oneWayWindowModeValue;
    private final ForgeConfigSpec.EnumValue<BlockInteractOverlay.Mode> frameBackgroundModeValue;
    private final ForgeConfigSpec.EnumValue<BlockInteractOverlay.Mode> camoRotationModeValue;

    static
    {
        final Pair<ClientConfig, ForgeConfigSpec> configSpecPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = configSpecPair.getRight();
        INSTANCE = configSpecPair.getLeft();
    }

    public ClientConfig(ForgeConfigSpec.Builder builder)
    {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);

        builder.push("general");
        showGhostBlocksValue = builder
                .comment("Whether ghost blocks are shown when you are holding a framed block")
                .translation(TRANSLATION_SHOW_GHOST_BLOCKS)
                .define(KEY_SHOW_GHOST_BLOCKS, true);
        altGhostRendererValue = builder
                .comment("If true, an alternate renderer will be used for the placement preview. May solve issues with certain shaders")
                .translation(TRANSLATION_ALT_GHOST_RENDERER)
                .define(KEY_ALT_GHOST_RENDERER, false);
        fancyHitboxesValue = builder
                .comment("Whether certain framed blocks should show fancy hitboxes")
                .translation(TRANSLATION_FANCY_HITBOXES)
                .define(KEY_FANCY_HITBOXES, true);
        detailedCullingValue = builder
                .comment("If false only full block faces of framed blocks will be culled, if true all outer faces of famed blocks can be culled")
                .translation(TRANSLATION_DETAILED_CULLING)
                .define(KEY_DETAILED_CULLING, true);
        useDiscreteUVStepsValue = builder
                .comment("If true, the UV remapping will use discrete steps to avoid floating point errors")
                .translation(TRANSLATION_USE_DISCRETE_UV_STEPS)
                .define(KEY_USE_DISCRETE_UV_STEPS, true);
        conTexModeValue = builder
                .comment(
                        "Configures how detailed connected textures are supported.",
                        "Use anything above FULL_EDGE at your own risk (performance impact, unexpected behaviour)!",
                        "If NONE, all connected textures support is disabled",
                        "If FULL_FACE, connected textures are supported on full faces",
                        "If FULL_EDGE, connected textures are supported as above and on faces whose connecting edge covers the full block width",
                        "If DETAILED, connected textures are supported as above and on most faces when interacting with other framed blocks"
                )
                .translation(TRANSLATION_CON_TEX_MODE)
                .defineEnum(KEY_CON_TEX_MODE, ConTexMode.FULL_FACE);
        camoMessageVerbosityValue = builder
                .comment(
                        "Configures the verbosity of messages displayed when a block cannot be used as a camo",
                        "If NONE, no message will be shown",
                        "If DEFAULT, a message will be shown when the block has a BlockEntity and isn't explicitly allowed or the block is explicitly disallowed",
                        "If DETAILED, a message will be shown as above or when a block is non-solid and not explicitly allowed"
                )
                .translation(TRANSLATION_CAMO_MESSAGE_VERBOSITY)
                .defineEnum(KEY_CAMO_MESSAGE_VERBOSITY, CamoMessageVerbosity.DEFAULT);
        forceAoOnGlowingBlocksValue = builder
                .comment(
                        "If true, ambient occlusion is applied to framed blocks which glow from applied glowstone dust.",
                        "If false, the vanilla behavior of disabling AO for light-emitting blocks is used"
                )
                .translation(TRANSLATION_FORCE_AO_ON_GLOWING_BLOCKS)
                .define(KEY_FORCE_AO_ON_GLOWING_BLOCKS, true);
        showAllRecipePermutationsInEmiValue = builder
                .comment("If true, all possible recipes of the Framing Saw will be added to EMI, else only the permutations using the Framed Cube will be added")
                .comment("This setting only has an effect when EMI is installed")
                .translation(TRANSLATION_SHOW_ALL_RECIPE_PERMUTATIONS_IN_EMI)
                .define(KEY_SHOW_ALL_RECIPE_PERMUTATIONS_IN_EMI, true);
        solidFrameModeValue = builder
                .comment(
                        "Configures in which cases a framed block without a camo gets a solid model",
                        "If NEVER, the default frame texture will always be used",
                        "If DEFAULT, certain blocks will use the default frame texture with a solid background texture",
                        "If ALWAYS, all blocks will use the default frame texture with a solid background texture"
                )
                .translation(TRANSLATION_SOLID_FRAME_MODE)
                .defineEnum(KEY_SOLID_FRAME_MODE, SolidFrameMode.DEFAULT);
        showButtonPlateOverlayValue = builder
                .comment(
                        "If enabled, non-wooden buttons and pressure plates will show a material overlay when a camo is applied",
                        "Requires resource reload to take effect"
                )
                .translation(TRANSLATION_SHOW_BUTTON_PLATE_OVERLAY)
                .define(KEY_SHOW_BUTTON_PLATE_OVERLAY, true);
        showSpecialCubeOverlayValue = builder
                .comment(
                        "If enabled, special cube blocks will show a type overlay when a camo is applied",
                        "Requires resource reload to take effect"
                )
                .translation(TRANSLATION_SHOW_SPECIAL_CUBE_OVERLAY)
                .define(KEY_SHOW_SPECIAL_CUBE_OVERLAY, true);
        builder.pop();

        builder.push("overlay");
        stateLockModeValue = builder
                .comment(COMMENT_OVERLAY_HIDDEN.formatted("State Lock"))
                .comment(COMMENT_OVERLAY_ICON.formatted("State Lock"))
                .comment(COMMENT_OVERLAY_DETAILED.formatted("State Lock"))
                .translation(TRANSLATION_STATE_LOCK_MODE)
                .defineEnum(KEY_STATE_LOCK_MODE, BlockInteractOverlay.Mode.DETAILED);
        toggleWaterlogModeValue = builder
                .comment(COMMENT_OVERLAY_HIDDEN.formatted("Toggle Waterloggable"))
                .comment(COMMENT_OVERLAY_ICON.formatted("Toggle Waterloggable"))
                .comment(COMMENT_OVERLAY_DETAILED.formatted("Toggle Waterloggable"))
                .translation(TRANSLATION_TOGGLE_WATERLOG_MODE)
                .defineEnum(KEY_TOGGLE_WATERLOG_MODE, BlockInteractOverlay.Mode.DETAILED);
        toggleYSlopeModeValue = builder
                .comment(COMMENT_OVERLAY_HIDDEN.formatted("Toggle Slope Face"))
                .comment(COMMENT_OVERLAY_ICON.formatted("Toggle Slope Face"))
                .comment(COMMENT_OVERLAY_DETAILED.formatted("Toggle Slope Face"))
                .translation(TRANSLATION_TOGGLE_Y_SLOPE_MODE)
                .defineEnum(KEY_TOGGLE_Y_SLOPE_MODE, BlockInteractOverlay.Mode.DETAILED);
        reinforcementModeValue = builder
                .comment(COMMENT_OVERLAY_HIDDEN.formatted("Reinforcement"))
                .comment(COMMENT_OVERLAY_ICON.formatted("Reinforcement"))
                .comment(COMMENT_OVERLAY_DETAILED.formatted("Reinforcement"))
                .translation(TRANSLATION_REINFORCEMENT_MODE)
                .defineEnum(KEY_REINFORCEMENT_MODE, BlockInteractOverlay.Mode.DETAILED);
        prismOffsetModeValue = builder
                .comment(COMMENT_OVERLAY_HIDDEN.formatted("Prism Offset"))
                .comment(COMMENT_OVERLAY_ICON.formatted("Prism Offset"))
                .comment(COMMENT_OVERLAY_DETAILED.formatted("Prism Offset"))
                .translation(TRANSLATION_PRISM_OFFSET_MODE)
                .defineEnum(KEY_PRISM_OFFSET_MODE, BlockInteractOverlay.Mode.DETAILED);
        splitLineModeValue = builder
                .comment(COMMENT_OVERLAY_HIDDEN.formatted("Collapsible Block Split Line"))
                .comment(COMMENT_OVERLAY_ICON.formatted("Collapsible Block Split Line"))
                .comment(COMMENT_OVERLAY_DETAILED.formatted("Collapsible Block Split Line"))
                .translation(TRANSLATION_SPLIT_LINES_MODE)
                .defineEnum(KEY_SPLIT_LINES_MODE, BlockInteractOverlay.Mode.DETAILED);
        oneWayWindowModeValue = builder
                .comment(COMMENT_OVERLAY_HIDDEN.formatted("One-Way Window"))
                .comment(COMMENT_OVERLAY_ICON.formatted("One-Way Window"))
                .comment(COMMENT_OVERLAY_DETAILED.formatted("One-Way Window"))
                .translation(TRANSLATION_ONE_WAY_WINDOW_MODE)
                .defineEnum(KEY_ONE_WAY_WINDOW_MODE, BlockInteractOverlay.Mode.DETAILED);
        frameBackgroundModeValue = builder
                .comment(COMMENT_OVERLAY_HIDDEN.formatted("Item Frame Background"))
                .comment(COMMENT_OVERLAY_ICON.formatted("Item Frame Background"))
                .comment(COMMENT_OVERLAY_DETAILED.formatted("Item Frame Background"))
                .translation(TRANSLATION_FRAME_BACKGROUND_MODE)
                .defineEnum(KEY_FRAME_BACKGROUND_MODE, BlockInteractOverlay.Mode.DETAILED);
        camoRotationModeValue = builder
                .comment(COMMENT_OVERLAY_HIDDEN.formatted("Camo Rotation"))
                .comment(COMMENT_OVERLAY_ICON.formatted("Camo Rotation"))
                .comment(COMMENT_OVERLAY_DETAILED.formatted("Camo Rotation"))
                .translation(TRANSLATION_CAMO_ROTATION_MODE)
                .defineEnum(KEY_CAMO_ROTATION_MODE, BlockInteractOverlay.Mode.DETAILED);
        builder.pop();
    }

    private static String translate(String key)
    {
        return Utils.translateConfig("client", key);
    }

    @SubscribeEvent
    public void onConfigReloaded(ModConfigEvent event)
    {
        if (event.getConfig().getType() == ModConfig.Type.CLIENT && event.getConfig().getSpec() == SPEC)
        {
            showGhostBlocks = showGhostBlocksValue.get();
            altGhostRenderer = altGhostRendererValue.get();
            fancyHitboxes = fancyHitboxesValue.get();
            detailedCulling = detailedCullingValue.get();
            useDiscreteUVSteps = useDiscreteUVStepsValue.get();
            conTexMode = conTexModeValue.get();
            camoMessageVerbosity = camoMessageVerbosityValue.get();
            forceAoOnGlowingBlocks = forceAoOnGlowingBlocksValue.get();
            showAllRecipePermutationsInEmi = showAllRecipePermutationsInEmiValue.get();
            solidFrameMode = solidFrameModeValue.get();
            showButtonPlateOverlay = showButtonPlateOverlayValue.get();
            showSpecialCubeOverlay = showSpecialCubeOverlayValue.get();

            stateLockMode = stateLockModeValue.get();
            toggleWaterlogMode = toggleWaterlogModeValue.get();
            toggleYSlopeMode = toggleYSlopeModeValue.get();
            reinforcementMode = reinforcementModeValue.get();
            prismOffsetMode = prismOffsetModeValue.get();
            splitLineMode = splitLineModeValue.get();
            oneWayWindowMode = oneWayWindowModeValue.get();
            frameBackgroundMode = frameBackgroundModeValue.get();
            camoRotationMode = camoRotationModeValue.get();
        }
    }
}