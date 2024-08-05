package com.dreammaster.scripts;

import static gregtech.api.enums.Mods.AE2FluidCraft;
import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.EternalSingularity;
import static gregtech.api.enums.Mods.GTPlusPlus;
import static gregtech.api.enums.Mods.GoodGenerator;
import static gregtech.api.enums.Mods.Minecraft;
import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.enums.Mods.OpenComputers;
import static gregtech.api.recipe.RecipeMaps.assemblerRecipes;
import static gregtech.api.recipe.RecipeMaps.circuitAssemblerRecipes;
import static gregtech.api.util.GT_ModHandler.getModItem;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.dreammaster.gthandler.CustomItemList;

import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.avaritia.crafting.ExtremeCraftingManager;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_ModHandler.RecipeBits;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

public class ScriptAE2FC implements IScriptLoader {

    @Override
    public String getScriptName() {
        return "AE2FC";
    }

    @Override
    public List<String> getDependencies() {
        return Arrays.asList(
                AE2FluidCraft.ID,
                AppliedEnergistics2.ID,
                OpenComputers.ID,
                GoodGenerator.ID,
                GTPlusPlus.ID,
                EternalSingularity.ID);
    }

    @Override
    public void loadRecipes() {
        final ItemStack AE2_INTERFACE = getModItem(AppliedEnergistics2.ID, "tile.BlockInterface", 1);
        final ItemStack AE2_PROCESS_ENG = getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 24);
        final ItemStack AE2_STORAGE_BUS = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 220);
        final ItemStack AE2_GLASS_CABLE = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 16);
        final ItemStack AE2_PROCESS_CAL = getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 23);
        final ItemStack AE2_WORK_BENCH = getModItem(AppliedEnergistics2.ID, "tile.BlockCellWorkbench", 1);
        final ItemStack AE2_PATTERN_TERM = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 340);
        final ItemStack AE2_TERM = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 380);
        final ItemStack AE2_INTERFACE_TERM = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 480);
        final ItemStack AE2_PROCESS_LOG = getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 22);
        final ItemStack AE2_QUARTZ_GLASS = getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzGlass", 1);
        final ItemStack AE2_LAMP_GLASS = getModItem(AppliedEnergistics2.ID, "tile.BlockQuartzLamp", 1);
        final ItemStack AE2_ME_CHEST = getModItem(AppliedEnergistics2.ID, "tile.BlockChest", 1, 0);
        final ItemStack AE2_ENERGY_CELL = getModItem(AppliedEnergistics2.ID, "tile.BlockEnergyCell", 1, 0);
        final ItemStack AE2_CELL_HOUSING = getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 39);
        final ItemStack AE2_CORE_ANN = getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 44);
        final ItemStack AE2_CORE_FOM = getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 43);
        final ItemStack AE2_BLANK_PATTERN = getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 52);
        final ItemStack AE2_CARD_CRAFTING = getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 53);
        final ItemStack AE2_LEVEL_MAIN = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 280);
        final ItemStack AE2_MON = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 400);
        final ItemStack AE2_CON = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 420);
        final ItemStack AE2_ITEM_WIRELESS = getModItem(
                AppliedEnergistics2.ID,
                "item.ToolWirelessTerminal",
                1,
                OreDictionary.WILDCARD_VALUE);
        final ItemStack AE2_PURE_CERTUS = new ItemStack(
                GameRegistry.findItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial"),
                1,
                10);
        final ItemStack BUCKET = new ItemStack(Items.bucket, 1);
        final ItemStack IRON_BAR = new ItemStack(Blocks.iron_bars, 1);
        final ItemStack IRON_PLATE = GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1L);
        final ItemStack LAPIS_PLATE = GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 1L);
        final ItemStack NIOBIUM_PLATE = GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NiobiumTitanium, 1L);
        final ItemStack NETHER_QUARTZ_PLATE = GT_OreDictUnificator.get(OrePrefixes.plate, Materials.NetherQuartz, 1L);
        final ItemStack LAPIS_SCREW = GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Lapis, 1L);
        final ItemStack FLUID_CORE_1 = getModItem(
                NewHorizonsCoreMod.ID,
                "item.EngineeringProcessorFluidDiamondCore",
                1);
        final ItemStack FLUID_CORE_2 = getModItem(
                NewHorizonsCoreMod.ID,
                "item.EngineeringProcessorFluidEmeraldCore",
                1);
        final ItemStack FISH = getModItem(Minecraft.ID, "fish", 1, 0);
        final ItemStack CELL_1 = getModItem(AE2FluidCraft.ID, "fluid_storage1", 1, 0);
        final ItemStack CELL_4 = getModItem(AE2FluidCraft.ID, "fluid_storage4", 1, 0);
        final ItemStack CELL_16 = getModItem(AE2FluidCraft.ID, "fluid_storage16", 1, 0);
        final ItemStack CELL_64 = getModItem(AE2FluidCraft.ID, "fluid_storage64", 1, 0);
        final ItemStack CELL_256 = getModItem(AE2FluidCraft.ID, "fluid_storage256", 1, 0);
        final ItemStack CELL_1024 = getModItem(AE2FluidCraft.ID, "fluid_storage1024", 1, 0);
        final ItemStack CELL_4096 = getModItem(AE2FluidCraft.ID, "fluid_storage4096", 1, 0);
        final ItemStack CELL_16384 = getModItem(AE2FluidCraft.ID, "fluid_storage16384", 1, 0);
        final ItemStack CELL_1M = getModItem(AE2FluidCraft.ID, "multi_fluid_storage1", 1, 0);
        final ItemStack CELL_4M = getModItem(AE2FluidCraft.ID, "multi_fluid_storage4", 1, 0);
        final ItemStack CELL_16M = getModItem(AE2FluidCraft.ID, "multi_fluid_storage16", 1, 0);
        final ItemStack CELL_64M = getModItem(AE2FluidCraft.ID, "multi_fluid_storage64", 1, 0);
        final ItemStack CELL_256M = getModItem(AE2FluidCraft.ID, "multi_fluid_storage256", 1, 0);
        final ItemStack CELL_1024M = getModItem(AE2FluidCraft.ID, "multi_fluid_storage1024", 1, 0);
        final ItemStack CELL_4096M = getModItem(AE2FluidCraft.ID, "multi_fluid_storage4096", 1, 0);
        final ItemStack CELL_16384M = getModItem(AE2FluidCraft.ID, "multi_fluid_storage16384", 1, 0);
        final ItemStack COMPONENT_1 = getModItem(AE2FluidCraft.ID, "fluid_part", 1, 0);
        final ItemStack COMPONENT_4 = getModItem(AE2FluidCraft.ID, "fluid_part", 1, 1);
        final ItemStack COMPONENT_16 = getModItem(AE2FluidCraft.ID, "fluid_part", 1, 2);
        final ItemStack COMPONENT_64 = getModItem(AE2FluidCraft.ID, "fluid_part", 1, 3);
        final ItemStack COMPONENT_256 = getModItem(AE2FluidCraft.ID, "fluid_part", 1, 4);
        final ItemStack COMPONENT_1024 = getModItem(AE2FluidCraft.ID, "fluid_part", 1, 5);
        final ItemStack COMPONENT_4096 = getModItem(AE2FluidCraft.ID, "fluid_part", 1, 6);
        final ItemStack COMPONENT_16384 = getModItem(AE2FluidCraft.ID, "fluid_part", 1, 7);
        final ItemStack AE2FC_INTERFACE = getModItem(AE2FluidCraft.ID, "fluid_interface", 1, 0);
        final ItemStack AE2FC_INTERFACE_SMALL = getModItem(AE2FluidCraft.ID, "part_fluid_interface", 1, 0);
        final ItemStack AE2FC_DISCRETIZER = getModItem(AE2FluidCraft.ID, "fluid_discretizer", 1, 0);
        final ItemStack AE2FC_ENCODER = getModItem(AE2FluidCraft.ID, "fluid_pattern_encoder", 1, 0);
        final ItemStack AE2FC_DECODER = getModItem(AE2FluidCraft.ID, "fluid_packet_decoder", 1, 0);
        final ItemStack AE2FC_BUFFER = getModItem(AE2FluidCraft.ID, "ingredient_buffer", 1, 0);
        final ItemStack AE2FC_BUFFER_LARGE = getModItem(AE2FluidCraft.ID, "large_ingredient_buffer", 1, 0);
        final ItemStack AE2FC_TERMINAL = getModItem(AE2FluidCraft.ID, "part_fluid_pattern_terminal", 1, 0);
        final ItemStack AE2FC_TERMINAL_PRO = getModItem(AE2FluidCraft.ID, "part_fluid_pattern_terminal_ex", 1, 0);
        final ItemStack AE2FC_EXPORTBUS = getModItem(AE2FluidCraft.ID, "part_fluid_export", 1, 0);
        final ItemStack AE2FC_IMPORTBUS = getModItem(AE2FluidCraft.ID, "part_fluid_import", 1, 0);
        final ItemStack AE2FC_OCEDITOR = getModItem(AE2FluidCraft.ID, "oc_pattern_editor", 1, 0);
        final ItemStack AE2FC_MAINTAIN = getModItem(AE2FluidCraft.ID, "level_maintainer", 1, 0);
        final ItemStack AE2FC_FLUID_TERMINAL = getModItem(AE2FluidCraft.ID, "part_fluid_terminal", 1, 0);
        final ItemStack AE2FC_FLUID_BUFFER = getModItem(AE2FluidCraft.ID, "fluid_buffer", 1, 0);
        final ItemStack AE2FC_FLUID_STORAGE_BUS = getModItem(AE2FluidCraft.ID, "part_fluid_storage_bus", 1, 0);
        final ItemStack AE2FC_AUTO_FILLER = getModItem(AE2FluidCraft.ID, "fluid_auto_filler", 1, 0);
        final ItemStack AE2FC_LEVEL_TERMINAL = getModItem(AE2FluidCraft.ID, "part_level_terminal", 1, 0);
        final ItemStack AE2FC_LEVEL_MAIN = getModItem(AE2FluidCraft.ID, "part_fluid_level_emitter", 1, 0);
        final ItemStack AE2FC_PORTABLE_CELL = getModItem(AE2FluidCraft.ID, "portable_fluid_cell", 1, 0);
        final ItemStack AE2FC_TANK = getModItem(AE2FluidCraft.ID, "certus_quartz_tank", 1, 0);
        final ItemStack AE2FC_WALRUS = getModItem(AE2FluidCraft.ID, "walrus", 1, 0);
        final ItemStack AE2FC_MON = getModItem(AE2FluidCraft.ID, "part_fluid_storage_monitor", 1, 0);
        final ItemStack AE2FC_CON = getModItem(AE2FluidCraft.ID, "part_fluid_conversion_monitor", 1, 0);
        final ItemStack AE2FC_FLUID_WIRELESS = getModItem(AE2FluidCraft.ID, "wireless_fluid_terminal", 1, 0);
        final ItemStack AE2FC_PATTERN_WIRELESS = getModItem(AE2FluidCraft.ID, "wireless_fluid_pattern_terminal", 1, 0);
        final ItemStack AE2FC_INTERFACE_WIRELESS = getModItem(AE2FluidCraft.ID, "wireless_interface_terminal", 1, 0);
        final ItemStack AE2FC_LEVEL_WIRELESS = getModItem(AE2FluidCraft.ID, "wireless_level_terminal", 1, 0);
        final ItemStack AE2FC_QUANTUM_CELL = getModItem(AE2FluidCraft.ID, "fluid_storage.quantum", 1, 0);
        final ItemStack AE2FC_SINGULARITY_CELL = getModItem(AE2FluidCraft.ID, "fluid_storage.singularity", 1, 0);
        final ItemStack AE2FC_FLUID_STORAGE_HOUSING = getModItem(AE2FluidCraft.ID, "fluid_storage_housing", 1, 0);
        final ItemStack AE2_P2P_ME = getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 460);
        final ItemStack AE2FC_INTERFACE_P2P = getModItem(AE2FluidCraft.ID, "part_fluid_p2p_interface", 1);
        final ItemStack AE2_ADVANCED_HOUSING = getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1, 61);
        final ItemStack T7_YOT = getModItem(GoodGenerator.ID, "yottaFluidTankCells", 1, 6);
        final ItemStack AE2FC_ADVANCED_FLUID_STORAGE_HOUSING = getModItem(
                AE2FluidCraft.ID,
                "fluid_storage_housing",
                1,
                1);
        final ItemStack AE2FC_MULTI_FLUID_STORAGE_HOUSING = getModItem(AE2FluidCraft.ID, "fluid_storage_housing", 1, 2);
        final ItemStack AE2FC_ADVANCED_MULTI_FLUID_STORAGE_HOUSING = getModItem(
                AE2FluidCraft.ID,
                "fluid_storage_housing",
                1,
                3);
        ItemStack[] mCells = new ItemStack[] { CELL_1M, CELL_4M, CELL_16M, CELL_64M, CELL_256M, CELL_1024M, CELL_4096M,
                CELL_16384M };
        ItemStack[] sCells = new ItemStack[] { CELL_1, CELL_4, CELL_16, CELL_64, CELL_256, CELL_1024, CELL_4096,
                CELL_16384 };
        ItemStack[] components = new ItemStack[] { COMPONENT_1, COMPONENT_4, COMPONENT_16, COMPONENT_64, COMPONENT_256,
                COMPONENT_1024, COMPONENT_4096, COMPONENT_16384 };

        final ItemStack nitinolPlate = GT_ModHandler.getModItem(GTPlusPlus.ID, "itemPlateNitinol60", 2L);
        final ItemStack zeronPlate = GT_ModHandler.getModItem(GTPlusPlus.ID, "itemPlateZeron100", 2L);

        // AE2FC_FLUID_STORAGE_HOUSING
        GT_ModHandler.addCraftingRecipe(
                AE2FC_FLUID_STORAGE_HOUSING,
                RecipeBits.BUFFERED | RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES,
                new Object[] { "hCW", "S-S", "WAd", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                        "plateStainlessSteel", 'A', "plateAluminium" });
        GT_ModHandler.addCraftingRecipe(
                AE2FC_FLUID_STORAGE_HOUSING,
                RecipeBits.BUFFERED,
                new Object[] { "dCW", "S-S", "WAh", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                        "plateStainlessSteel", 'A', "plateAluminium" });
        for (Map.Entry<ItemStack, ItemStack> entry : new HashMap<ItemStack, ItemStack>() {

            {
                put(CELL_1, COMPONENT_1);
                put(CELL_4, COMPONENT_4);
                put(CELL_16, COMPONENT_16);
                put(CELL_64, COMPONENT_64);
            }
        }.entrySet()) {
            GT_ModHandler.addCraftingRecipe(
                    entry.getKey(),
                    RecipeBits.BUFFERED | RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES,
                    new Object[] { "hCW", "SKS", "WAd", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                            "plateStainlessSteel", 'A', "plateAluminium", 'K', entry.getValue() });
            GT_ModHandler.addCraftingRecipe(
                    entry.getKey(),
                    RecipeBits.BUFFERED,
                    new Object[] { "dCW", "SKS", "WAh", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                            "plateStainlessSteel", 'A', "plateAluminium", 'K', entry.getValue() });
            addShapelessRecipe(entry.getKey(), AE2FC_FLUID_STORAGE_HOUSING, entry.getValue());
        }

        // AE2FC_MULTI_FLUID_STORAGE_HOUSING
        GT_ModHandler.addCraftingRecipe(
                AE2FC_MULTI_FLUID_STORAGE_HOUSING,
                RecipeBits.BUFFERED | RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES,
                new Object[] { "hCW", "S-S", "WAd", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                        "plateTungstenSteel", 'A', "plateStainlessSteel" });
        GT_ModHandler.addCraftingRecipe(
                AE2FC_MULTI_FLUID_STORAGE_HOUSING,
                RecipeBits.BUFFERED,
                new Object[] { "dCW", "S-S", "WAh", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                        "plateTungstenSteel", 'A', "plateStainlessSteel" });
        for (Map.Entry<ItemStack, ItemStack> entry : new HashMap<ItemStack, ItemStack>() {

            {
                put(CELL_1M, COMPONENT_1);
                put(CELL_4M, COMPONENT_4);
                put(CELL_16M, COMPONENT_16);
                put(CELL_64M, COMPONENT_64);
            }
        }.entrySet()) {
            GT_ModHandler.addCraftingRecipe(
                    entry.getKey(),
                    RecipeBits.BUFFERED | RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES,
                    new Object[] { "hCW", "SKS", "WAd", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                            "plateTungstenSteel", 'A', "plateStainlessSteel", 'K', entry.getValue() });
            GT_ModHandler.addCraftingRecipe(
                    entry.getKey(),
                    RecipeBits.BUFFERED,
                    new Object[] { "dCW", "SKS", "WAh", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                            "plateTungstenSteel", 'A', "plateStainlessSteel", 'K', entry.getValue() });
            addShapelessRecipe(entry.getKey(), AE2FC_MULTI_FLUID_STORAGE_HOUSING, entry.getValue());
        }

        // AE2FC_ADVANCED_FLUID_STORAGE_HOUSING
        GT_ModHandler.addCraftingRecipe(
                AE2FC_ADVANCED_FLUID_STORAGE_HOUSING,
                RecipeBits.BUFFERED | RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES,
                new Object[] { "hCW", "S-S", "WAd", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                        nitinolPlate, 'A', "plateStainlessSteel" });
        GT_ModHandler.addCraftingRecipe(
                AE2FC_ADVANCED_FLUID_STORAGE_HOUSING,
                RecipeBits.BUFFERED,
                new Object[] { "dCW", "S-S", "WAh", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                        nitinolPlate, 'A', "plateStainlessSteel" });
        for (Map.Entry<ItemStack, ItemStack> entry : new HashMap<ItemStack, ItemStack>() {

            {
                put(CELL_256, COMPONENT_256);
                put(CELL_1024, COMPONENT_1024);
                put(CELL_4096, COMPONENT_4096);
                put(CELL_16384, COMPONENT_16384);
            }
        }.entrySet()) {
            GT_ModHandler.addCraftingRecipe(
                    entry.getKey(),
                    RecipeBits.BUFFERED | RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES,
                    new Object[] { "hCW", "SKS", "WAd", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                            nitinolPlate, 'A', "plateStainlessSteel", 'K', entry.getValue() });
            GT_ModHandler.addCraftingRecipe(
                    entry.getKey(),
                    RecipeBits.BUFFERED,
                    new Object[] { "dCW", "SKS", "WAh", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                            nitinolPlate, 'A', "plateStainlessSteel", 'K', entry.getValue() });
            addShapelessRecipe(entry.getKey(), AE2FC_ADVANCED_FLUID_STORAGE_HOUSING, entry.getValue());
        }

        // AE2FC_ADVANCED_MULTI_FLUID_STORAGE_HOUSING
        GT_ModHandler.addCraftingRecipe(
                AE2FC_ADVANCED_MULTI_FLUID_STORAGE_HOUSING,
                RecipeBits.BUFFERED | RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES,
                new Object[] { "hCW", "S-S", "WAd", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S', zeronPlate,
                        'A', "plateTungstenSteel" });
        GT_ModHandler.addCraftingRecipe(
                AE2FC_ADVANCED_MULTI_FLUID_STORAGE_HOUSING,
                RecipeBits.BUFFERED,
                new Object[] { "dCW", "S-S", "WAh", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S', zeronPlate,
                        'A', "plateTungstenSteel" });
        for (Map.Entry<ItemStack, ItemStack> entry : new HashMap<ItemStack, ItemStack>() {

            {
                put(CELL_256M, COMPONENT_256);
                put(CELL_1024M, COMPONENT_1024);
                put(CELL_4096M, COMPONENT_4096);
                put(CELL_16384M, COMPONENT_16384);
            }
        }.entrySet()) {
            GT_ModHandler.addCraftingRecipe(
                    entry.getKey(),
                    RecipeBits.BUFFERED | RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES,
                    new Object[] { "hCW", "SKS", "WAd", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                            zeronPlate, 'A', "plateTungstenSteel", 'K', entry.getValue() });
            GT_ModHandler.addCraftingRecipe(
                    entry.getKey(),
                    RecipeBits.BUFFERED,
                    new Object[] { "dCW", "SKS", "WAh", 'C', "plateCertusQuartz", 'W', "screwCertusQuartz", 'S',
                            zeronPlate, 'A', "plateTungstenSteel", 'K', entry.getValue() });
            addShapelessRecipe(entry.getKey(), AE2FC_ADVANCED_MULTI_FLUID_STORAGE_HOUSING, entry.getValue());
        }

        // recursive components for those who want to do this weird stuff
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        COMPONENT_1,
                        "DCD",
                        "CEC",
                        "DCD",
                        'D',
                        "circuitPrimitive",
                        'C',
                        CustomItemList.ChargedCertusQuartzDust.get(1),
                        'E',
                        FLUID_CORE_1));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        COMPONENT_4,
                        "DCD",
                        "CPC",
                        "DCD",
                        'D',
                        "circuitBasic",
                        'C',
                        COMPONENT_1,
                        'P',
                        FLUID_CORE_1));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        COMPONENT_16,
                        "DCD",
                        "CPC",
                        "DCD",
                        'D',
                        "circuitGood",
                        'C',
                        COMPONENT_4,
                        'P',
                        FLUID_CORE_1));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        COMPONENT_64,
                        "DCD",
                        "CPC",
                        "DCD",
                        'D',
                        "circuitAdvanced",
                        'C',
                        COMPONENT_16,
                        'P',
                        FLUID_CORE_1));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        COMPONENT_256,
                        "DCD",
                        "CPC",
                        "DCD",
                        'D',
                        "circuitData",
                        'C',
                        COMPONENT_64,
                        'P',
                        FLUID_CORE_2));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        COMPONENT_1024,
                        "DCD",
                        "CPC",
                        "DCD",
                        'D',
                        "circuitElite",
                        'C',
                        COMPONENT_256,
                        'P',
                        FLUID_CORE_2));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        COMPONENT_4096,
                        "DCD",
                        "CPC",
                        "DCD",
                        'D',
                        "circuitMaster",
                        'C',
                        COMPONENT_1024,
                        'P',
                        FLUID_CORE_2));
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        COMPONENT_16384,
                        "DCD",
                        "CPC",
                        "DCD",
                        'D',
                        "circuitSuperconductor",
                        'C',
                        COMPONENT_4096,
                        'P',
                        FLUID_CORE_2));

        // Fluid Storage Bus
        GT_Values.RA.stdBuilder()
                .itemInputs(AE2_STORAGE_BUS, GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 3))
                .itemOutputs(AE2FC_FLUID_STORAGE_BUS).duration(15 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        // Big Long But: Components in Circuit Assembler
        // 1k ME Storage Component
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 2),
                        CustomItemList.ChargedCertusQuartzDust.get(2),
                        ItemList.Electric_Pump_LV.get(1),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidDiamondCore.getIS(1),
                        ItemList.Circuit_Board_Basic.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_1).fluidInputs(Materials.Lead.getMolten(288)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 2),
                        CustomItemList.ChargedCertusQuartzDust.get(2),
                        ItemList.Electric_Pump_LV.get(1),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidDiamondCore.getIS(1),
                        ItemList.Circuit_Board_Basic.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_1).fluidInputs(Materials.Tin.getMolten(144)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 2),
                        CustomItemList.ChargedCertusQuartzDust.get(2),
                        ItemList.Electric_Pump_LV.get(1),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidDiamondCore.getIS(1),
                        ItemList.Circuit_Board_Basic.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_1).fluidInputs(Materials.SolderingAlloy.getMolten(72)).duration(10 * SECONDS)
                .eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

        // 4k ME Storage Component
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 16),
                        ItemList.Electric_Pump_LV.get(2),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidDiamondCore.getIS(1),
                        ItemList.Circuit_Board_Coated_Basic.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_4).fluidInputs(Materials.Lead.getMolten(288)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 16),
                        ItemList.Electric_Pump_LV.get(2),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidDiamondCore.getIS(1),
                        ItemList.Circuit_Board_Coated_Basic.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_4).fluidInputs(Materials.Tin.getMolten(144)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 16),
                        ItemList.Electric_Pump_LV.get(2),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidDiamondCore.getIS(1),
                        ItemList.Circuit_Board_Coated_Basic.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_4).fluidInputs(Materials.SolderingAlloy.getMolten(72)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LV).addTo(circuitAssemblerRecipes);

        // 16k ME Storage Component
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 16),
                        ItemList.Electric_Pump_MV.get(1),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidDiamondCore.getIS(2),
                        ItemList.Circuit_Board_Phenolic_Good.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_16).fluidInputs(Materials.Lead.getMolten(288)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 16),
                        ItemList.Electric_Pump_MV.get(1),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidDiamondCore.getIS(2),
                        ItemList.Circuit_Board_Phenolic_Good.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_16).fluidInputs(Materials.Tin.getMolten(144)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LV, 16),
                        ItemList.Electric_Pump_MV.get(1),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidDiamondCore.getIS(2),
                        ItemList.Circuit_Board_Phenolic_Good.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_16).fluidInputs(Materials.SolderingAlloy.getMolten(72)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(circuitAssemblerRecipes);

        // 64k ME Storage Component
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 16),
                        ItemList.Electric_Pump_MV.get(2),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidDiamondCore.getIS(4),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_64).fluidInputs(Materials.Lead.getMolten(288)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_HV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 16),
                        ItemList.Electric_Pump_MV.get(2),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidDiamondCore.getIS(4),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_64).fluidInputs(Materials.Tin.getMolten(144)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_HV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 16),
                        ItemList.Electric_Pump_MV.get(2),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidDiamondCore.getIS(4),
                        ItemList.Circuit_Board_Epoxy_Advanced.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_64).fluidInputs(Materials.SolderingAlloy.getMolten(72)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_HV).addTo(circuitAssemblerRecipes);

        // 256k ME Storage Component
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 16),
                        ItemList.Electric_Pump_HV.get(1),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidEmeraldCore.getIS(1),
                        ItemList.Circuit_Board_Fiberglass_Advanced.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_256).fluidInputs(Materials.Lead.getMolten(288)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_EV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 16),
                        ItemList.Electric_Pump_HV.get(1),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidEmeraldCore.getIS(1),
                        ItemList.Circuit_Board_Fiberglass_Advanced.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_256).fluidInputs(Materials.Tin.getMolten(144)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_EV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 16),
                        ItemList.Electric_Pump_HV.get(1),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidEmeraldCore.getIS(1),
                        ItemList.Circuit_Board_Fiberglass_Advanced.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_256).fluidInputs(Materials.SolderingAlloy.getMolten(72)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_EV).addTo(circuitAssemblerRecipes);

        // 1024k ME Storage Component
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 16),
                        ItemList.Electric_Pump_HV.get(2),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidEmeraldCore.getIS(2),
                        ItemList.Circuit_Board_Multifiberglass_Elite.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_1024).fluidInputs(Materials.Lead.getMolten(288)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_IV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 16),
                        ItemList.Electric_Pump_HV.get(2),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidEmeraldCore.getIS(2),
                        ItemList.Circuit_Board_Multifiberglass_Elite.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_1024).fluidInputs(Materials.Tin.getMolten(144)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_IV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 16),
                        ItemList.Electric_Pump_HV.get(2),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidEmeraldCore.getIS(2),
                        ItemList.Circuit_Board_Multifiberglass_Elite.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_1024).fluidInputs(Materials.SolderingAlloy.getMolten(72)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_IV).addTo(circuitAssemblerRecipes);

        // 4096k ME Storage Component
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 16),
                        ItemList.Electric_Pump_EV.get(1),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidEmeraldCore.getIS(4),
                        ItemList.Circuit_Board_Wetware_Extreme.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_4096).fluidInputs(Materials.Lead.getMolten(288)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 16),
                        ItemList.Electric_Pump_EV.get(1),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidEmeraldCore.getIS(4),
                        ItemList.Circuit_Board_Wetware_Extreme.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_4096).fluidInputs(Materials.Tin.getMolten(144)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 16),
                        ItemList.Electric_Pump_EV.get(1),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidEmeraldCore.getIS(4),
                        ItemList.Circuit_Board_Wetware_Extreme.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_4096).fluidInputs(Materials.SolderingAlloy.getMolten(72)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(TierEU.RECIPE_LuV).addTo(circuitAssemblerRecipes);

        // 16384k ME Storage Component
        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.UV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 16),
                        ItemList.Electric_Pump_EV.get(2),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidEmeraldCore.getIS(8),
                        ItemList.Circuit_Board_Bio_Ultra.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_16384).fluidInputs(Materials.Lead.getMolten(288)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(500000).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.UV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 16),
                        ItemList.Electric_Pump_EV.get(2),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidEmeraldCore.getIS(8),
                        ItemList.Circuit_Board_Bio_Ultra.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_16384).fluidInputs(Materials.Tin.getMolten(144)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(500000).addTo(circuitAssemblerRecipes);

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.UV, 4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 16),
                        ItemList.Electric_Pump_EV.get(2),
                        com.dreammaster.item.ItemList.EngineeringProcessorFluidEmeraldCore.getIS(8),
                        ItemList.Circuit_Board_Bio_Ultra.get(1),
                        GT_Utility.getIntegratedCircuit(1))
                .itemOutputs(COMPONENT_16384).fluidInputs(Materials.SolderingAlloy.getMolten(72)).requiresCleanRoom()
                .duration(10 * SECONDS).eut(500000).addTo(circuitAssemblerRecipes);

        // Fluid Quantum Drive
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                (AE2FC_QUANTUM_CELL),
                "---------",
                "----a----",
                "---bdb---",
                "--bcdcb--",
                "-addedda-",
                "--bcdcb--",
                "---bdb---",
                "----a----",
                "---------",
                'a',
                "blockCosmicNeutronium",
                'b',
                "plateDenseNeutronium",
                'c',
                "circuitInfinite",
                'd',
                COMPONENT_16384,
                'e',
                AE2FC_ADVANCED_FLUID_STORAGE_HOUSING);
        // ME Digital Singularity
        ExtremeCraftingManager.getInstance().addExtremeShapedOreRecipe(
                AE2FC_SINGULARITY_CELL,
                "----a----",
                "---aba---",
                "--ecdce--",
                "-acdgdca-",
                "abdgfgdba",
                "-acdgdca-",
                "--ecdce--",
                "---aba---",
                "----a----",
                'a',
                "blockCosmicNeutronium",
                'b',
                getModItem("Avaritia", "Resource", 1, 5),
                'c',
                ItemList.Quantum_Tank_IV.get(1L),
                'd',
                COMPONENT_16384,
                'e',
                "blockInfinity",
                'f',
                getModItem("eternalsingularity", "eternal_singularity", 1),
                'g',
                T7_YOT);

        // level maintainer
        addShapedRecipe(
                AE2FC_MAINTAIN,
                NIOBIUM_PLATE,
                AE2_CARD_CRAFTING,
                NIOBIUM_PLATE,
                AE2_CORE_ANN,
                AE2_INTERFACE,
                FLUID_CORE_1,
                NIOBIUM_PLATE,
                AE2_CARD_CRAFTING,
                NIOBIUM_PLATE);
        // interface
        addShapedRecipe(
                AE2FC_INTERFACE,
                IRON_PLATE,
                LAPIS_PLATE,
                IRON_PLATE,
                FLUID_CORE_1,
                AE2_INTERFACE,
                FLUID_CORE_1,
                IRON_PLATE,
                LAPIS_PLATE,
                IRON_PLATE);
        // Fluid discretizer
        addShapedRecipe(
                AE2FC_DISCRETIZER,
                IRON_PLATE,
                BUCKET,
                IRON_PLATE,
                AE2_STORAGE_BUS,
                ItemList.Electric_Pump_EV.get(1),
                AE2_STORAGE_BUS,
                IRON_PLATE,
                AE2_PROCESS_ENG,
                IRON_PLATE);
        // Fluid encoder
        addShapedRecipe(
                AE2FC_ENCODER,
                LAPIS_PLATE,
                ItemList.Machine_HV_Assembler.get(1),
                LAPIS_PLATE,
                IRON_PLATE,
                AE2_WORK_BENCH,
                IRON_PLATE,
                IRON_PLATE,
                IRON_PLATE,
                IRON_PLATE);
        // Fluid decoder
        addShapedRecipe(
                AE2FC_DECODER,
                IRON_PLATE,
                ItemList.Machine_HV_FluidExtractor.get(1),
                IRON_PLATE,
                AE2_GLASS_CABLE,
                AE2_WORK_BENCH,
                AE2_GLASS_CABLE,
                IRON_PLATE,
                IRON_PLATE,
                IRON_PLATE);
        // Ingredient Buffer
        addShapedRecipe(
                AE2FC_BUFFER,
                IRON_PLATE,
                FLUID_CORE_1,
                IRON_PLATE,
                BUCKET,
                AE2_WORK_BENCH,
                BUCKET,
                IRON_PLATE,
                AE2_PROCESS_CAL,
                IRON_PLATE);
        // Large Ingredient Buffer
        addShapedRecipe(
                AE2FC_BUFFER_LARGE,
                NIOBIUM_PLATE,
                FLUID_CORE_1,
                NIOBIUM_PLATE,
                ItemList.Super_Tank_LV.get(1),
                AE2FC_BUFFER,
                ItemList.Super_Chest_LV.get(1),
                NIOBIUM_PLATE,
                AE2_PROCESS_CAL,
                NIOBIUM_PLATE);
        // Fluid Buffer
        addShapedRecipe(
                AE2FC_FLUID_BUFFER,
                IRON_PLATE,
                LAPIS_PLATE,
                IRON_PLATE,
                FLUID_CORE_1,
                AE2FC_BUFFER,
                FLUID_CORE_1,
                IRON_PLATE,
                LAPIS_PLATE,
                IRON_PLATE);
        // Fluid Terminal
        addShapedRecipe(
                AE2FC_FLUID_TERMINAL,
                LAPIS_PLATE,
                AE2_GLASS_CABLE,
                LAPIS_PLATE,
                AE2_CORE_FOM,
                AE2_TERM,
                AE2_CORE_ANN,
                LAPIS_PLATE,
                AE2_GLASS_CABLE,
                LAPIS_PLATE);
        // Fluid Pattern Terminal
        addShapedRecipe(
                AE2FC_TERMINAL,
                NIOBIUM_PLATE,
                FLUID_CORE_1,
                NIOBIUM_PLATE,
                BUCKET,
                AE2_PATTERN_TERM,
                BUCKET,
                NIOBIUM_PLATE,
                AE2_PROCESS_CAL,
                NIOBIUM_PLATE);
        // Fluid Processing Pattern Terminal
        addShapedRecipe(
                AE2FC_TERMINAL_PRO,
                LAPIS_PLATE,
                AE2FC_TERMINAL,
                LAPIS_PLATE,
                ItemList.Conveyor_Module_LuV.get(1),
                AE2_CORE_ANN,
                ItemList.Conveyor_Module_LuV.get(1),
                ItemList.Electric_Motor_LuV.get(1),
                ItemList.Robot_Arm_LuV.get(1),
                ItemList.Electric_Motor_LuV.get(1));
        // Level Terminal
        addShapedRecipe(
                AE2FC_LEVEL_TERMINAL,
                NIOBIUM_PLATE,
                AE2FC_LEVEL_MAIN,
                NIOBIUM_PLATE,
                AE2FC_MAINTAIN,
                AE2_TERM,
                AE2FC_MAINTAIN,
                NIOBIUM_PLATE,
                AE2_LEVEL_MAIN,
                NIOBIUM_PLATE);
        // Fluid Export Bus
        addShapedRecipe(
                AE2FC_EXPORTBUS,
                IRON_PLATE,
                LAPIS_PLATE,
                IRON_PLATE,
                LAPIS_SCREW,
                AE2_CORE_FOM,
                LAPIS_SCREW,
                NETHER_QUARTZ_PLATE,
                ItemList.Electric_Piston_LV.get(1),
                NETHER_QUARTZ_PLATE);
        // Fluid Import Bus
        addShapedRecipe(
                AE2FC_IMPORTBUS,
                IRON_PLATE,
                LAPIS_PLATE,
                IRON_PLATE,
                LAPIS_SCREW,
                AE2_CORE_ANN,
                LAPIS_SCREW,
                NETHER_QUARTZ_PLATE,
                ItemList.Electric_Piston_LV.get(1),
                NETHER_QUARTZ_PLATE);
        // Fluid Auto Filler
        addShapedRecipe(
                AE2FC_AUTO_FILLER,
                LAPIS_PLATE,
                AE2FC_FLUID_TERMINAL,
                LAPIS_PLATE,
                AE2_GLASS_CABLE,
                AE2FC_INTERFACE,
                AE2_GLASS_CABLE,
                LAPIS_PLATE,
                AE2FC_LEVEL_MAIN,
                LAPIS_PLATE);
        // Walrus
        addShapedRecipe(AE2FC_WALRUS, FISH, FISH, FISH, FISH, null, FISH, FISH, FISH, FISH);
        // Fluid Portable Cell
        addShapedRecipe(
                AE2FC_PORTABLE_CELL,
                null,
                CELL_1,
                null,
                LAPIS_SCREW,
                AE2_ME_CHEST,
                LAPIS_SCREW,
                "craftingToolScrewdriver",
                AE2_ENERGY_CELL,
                "craftingToolWrench");

        GT_Values.RA.stdBuilder()
                .itemInputs(
                        CELL_1,
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Lapis, 2),
                        AE2_ME_CHEST,
                        AE2_ENERGY_CELL)
                .itemOutputs(AE2FC_PORTABLE_CELL).duration(10 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Quartz Tank
        GT_Values.RA.stdBuilder()
                .itemInputs(GT_Utility.copyAmount(8, AE2_QUARTZ_GLASS), GT_Utility.getIntegratedCircuit(8))
                .itemOutputs(AE2FC_TANK).duration(40 * SECONDS).eut(TierEU.RECIPE_LV).addTo(assemblerRecipes);

        // Fluid Level Emitter
        GT_Values.RA.stdBuilder().itemInputs(AE2_LEVEL_MAIN, LAPIS_PLATE).itemOutputs(AE2FC_LEVEL_MAIN)
                .duration(5 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Fluid Storage Monitor
        GT_Values.RA.stdBuilder().itemInputs(AE2_MON, GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 3))
                .itemOutputs(AE2FC_MON).duration(15 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Fluid Conversion Monitor
        GT_Values.RA.stdBuilder().itemInputs(AE2_CON, GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lapis, 3))
                .itemOutputs(AE2FC_CON).duration(15 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Wireless Fluid ME Terminal
        GT_Values.RA.stdBuilder().itemInputs(AE2_ITEM_WIRELESS, AE2FC_FLUID_TERMINAL).itemOutputs(AE2FC_FLUID_WIRELESS)
                .duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Wireless Fluid Pattern Terminal
        GT_Values.RA.stdBuilder().itemInputs(AE2_ITEM_WIRELESS, AE2FC_TERMINAL).itemOutputs(AE2FC_PATTERN_WIRELESS)
                .duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Wireless Interface Terminal
        GT_Values.RA.stdBuilder().itemInputs(AE2_ITEM_WIRELESS, AE2_INTERFACE_TERM)
                .itemOutputs(AE2FC_INTERFACE_WIRELESS).duration(30 * SECONDS).eut(TierEU.RECIPE_MV)
                .addTo(assemblerRecipes);

        // Wireless Level Terminal
        GT_Values.RA.stdBuilder().itemInputs(AE2_ITEM_WIRELESS, AE2FC_LEVEL_TERMINAL).itemOutputs(AE2FC_LEVEL_WIRELESS)
                .duration(30 * SECONDS).eut(TierEU.RECIPE_MV).addTo(assemblerRecipes);

        // Interface from Small to Block and opposite
        GameRegistry.addShapelessRecipe(AE2FC_INTERFACE_SMALL, AE2FC_INTERFACE);
        GameRegistry.addShapelessRecipe(AE2FC_INTERFACE, AE2FC_INTERFACE_SMALL);
        // OC Component
        ItemStack CHIP_T1 = getModItem(OpenComputers.ID, "item", 1, 24);
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        AE2FC_OCEDITOR,
                        "IMI",
                        "CBC",
                        "IPI",
                        'I',
                        IRON_BAR,
                        'M',
                        CHIP_T1,
                        'C',
                        "oc:cable",
                        'B',
                        BUCKET,
                        'P',
                        AE2_BLANK_PATTERN));
        // Dual interface P2P
        GameRegistry.addShapelessRecipe(AE2FC_INTERFACE_P2P, AE2_P2P_ME, AE2FC_INTERFACE);
        GameRegistry.addShapelessRecipe(AE2FC_INTERFACE_P2P, AE2_P2P_ME, AE2FC_INTERFACE_SMALL);
    }
}
