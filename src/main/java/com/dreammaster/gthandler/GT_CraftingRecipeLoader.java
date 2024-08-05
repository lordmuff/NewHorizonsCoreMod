package com.dreammaster.gthandler;

import static gregtech.api.enums.Mods.AE2FluidCraft;
import static gregtech.api.enums.Mods.AdventureBackpack;
import static gregtech.api.enums.Mods.AppliedEnergistics2;
import static gregtech.api.enums.Mods.BartWorks;
import static gregtech.api.enums.Mods.BuildCraftFactory;
import static gregtech.api.enums.Mods.Chisel;
import static gregtech.api.enums.Mods.Computronics;
import static gregtech.api.enums.Mods.ExtraUtilities;
import static gregtech.api.enums.Mods.Forestry;
import static gregtech.api.enums.Mods.GalacticraftCore;
import static gregtech.api.enums.Mods.GalacticraftMars;
import static gregtech.api.enums.Mods.GalaxySpace;
import static gregtech.api.enums.Mods.GoodGenerator;
import static gregtech.api.enums.Mods.GregTech;
import static gregtech.api.enums.Mods.IguanaTweaksTinkerConstruct;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.enums.Mods.OpenComputers;
import static gregtech.api.enums.Mods.OpenPrinters;
import static gregtech.api.enums.Mods.ProjectRedIllumination;
import static gregtech.api.enums.Mods.Railcraft;
import static gregtech.api.enums.Mods.TinkerConstruct;
import static gregtech.api.enums.Mods.ZTones;
import static gregtech.api.enums.OrePrefixes.screw;
import static gregtech.api.util.GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES;
import static gtPlusPlus.core.material.ALLOY.NITINOL_60;
import static gtPlusPlus.core.material.ALLOY.STELLITE;

import java.util.function.Consumer;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.dreammaster.main.NHItems;
import com.dreammaster.mantle.MantleManualRecipeRegistry;
import com.dreammaster.recipes.Recipe;
import com.github.bartimaeusnek.bartworks.common.loaders.ItemRegistry;
import com.github.bartimaeusnek.bartworks.system.material.WerkstoffLoader;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OreDictNames;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.ToolDictNames;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import ic2.core.Ic2Items;
import pers.gwyog.gtneioreplugin.plugin.block.ModBlocks;
import pers.gwyog.gtneioreplugin.util.DimensionHelper;

public class GT_CraftingRecipeLoader extends gregtech.loaders.postload.GT_CraftingRecipeLoader implements Runnable {

    private static final MantleManualRecipeRegistry MANTLE = MantleManualRecipeRegistry.getInstance();
    private static final String aTextMachineBeta = "machine.beta";
    private static final String aTextMachineAlpha = "machine.alpha";
    private static final String aTextIron1 = "X X";
    private static final String aTextIron2 = "XXX";
    private static final String aTextTConstruct = TinkerConstruct.ID;

    private static final long bits = GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE
            | GT_ModHandler.RecipeBits.DISMANTLEABLE
            | GT_ModHandler.RecipeBits.BUFFERED;
    private static final long bits2 = GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE
            | GT_ModHandler.RecipeBits.DISMANTLEABLE
            | GT_ModHandler.RecipeBits.BUFFERED
            | DELETE_ALL_OTHER_RECIPES;
    private static final long bits3 = GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.DISMANTLEABLE
            | GT_ModHandler.RecipeBits.BUFFERED;
    private static final long bits4 = GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED;
    private static final long tBitMask = GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE
            | GT_ModHandler.RecipeBits.REVERSIBLE;

    /**
     * Rolling Machine Crafting Recipe
     */
    private static boolean addRollingMachineRecipe(ItemStack aResult, Object[] aRecipe) {
        aResult = GT_OreDictUnificator.get(true, aResult);
        if (aResult == null || aRecipe == null || aResult.stackSize <= 0) return false;
        try {
            mods.railcraft.api.crafting.RailcraftCraftingManager.rollingMachine.getRecipeList()
                    .add(new ShapedOreRecipe(GT_Utility.copyOrNull(aResult), aRecipe));
        } catch (Throwable e) {
            return GT_ModHandler.addCraftingRecipe(GT_Utility.copyOrNull(aResult), aRecipe);
        }
        return true;
    }

    @Override
    public void run() {
        // UEV, UIV, UMV, UXV casings
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Casing_UEV.get(1L),
                GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE,
                new Object[] { "PPP", "PwP", "PPP", 'P', OrePrefixes.plate.get(Materials.Bedrockium) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Casing_UIV.get(1L),
                GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE,
                new Object[] { "PPP", "PwP", "PPP", 'P', OrePrefixes.plate.get(Materials.BlackPlutonium) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Casing_UMV.get(1L),
                GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE,
                new Object[] { "PPP", "PwP", "PPP", 'P', OrePrefixes.plate.get(MaterialsUEVplus.SpaceTime) });
        if (GoodGenerator.isModLoaded()) {
            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.Casing_UXV.get(1L),
                    GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE,
                    new Object[] { "PSP", "SwS", "PSP", 'P',
                            OrePrefixes.plate.get(MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter), 'S',
                            GT_OreDictUnificator.get("plateShirabon", 1) });
        }
        // Mine and Blade Battlegear remove recipes NBT?
        Object[] o = new Object[0];

        GT_ModHandler.addCraftingRecipe(
                ItemList.Generator_Naquadah_Mark_I.get(1L),
                bits,
                new Object[] { "UCU", "FMF", "WCW", 'M', ItemList.Hull_EV, 'F', ItemList.Field_Generator_EV, 'C',
                        OrePrefixes.circuit.get(Materials.IV), 'W', OrePrefixes.cableGt04.get(Materials.Aluminium), 'U',
                        OrePrefixes.stick.get(Materials.Uranium235) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Generator_Naquadah_Mark_II.get(1L),
                bits,
                new Object[] { "PCP", "FMF", "WCW", 'M', ItemList.Hull_IV, 'F', ItemList.Field_Generator_IV, 'C',
                        OrePrefixes.circuit.get(Materials.LuV), 'W', OrePrefixes.cableGt04.get(Materials.Tungsten), 'P',
                        OrePrefixes.stick.get(Materials.Plutonium241) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Generator_Naquadah_Mark_III.get(1L),
                bits,
                new Object[] { "NCN", "FMF", "WCW", 'M', ItemList.Hull_LuV, 'F', ItemList.Field_Generator_LuV, 'C',
                        OrePrefixes.circuit.get(Materials.ZPM), 'W', OrePrefixes.cableGt04.get(Materials.HSSG), 'N',
                        OrePrefixes.stick.get(Materials.Europium) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Generator_Naquadah_Mark_IV.get(1L),
                bits,
                new Object[] { "NCN", "FMF", "WCW", 'M', ItemList.Hull_ZPM, 'F', ItemList.Field_Generator_ZPM, 'C',
                        OrePrefixes.circuit.get(Materials.UV), 'W', OrePrefixes.cableGt04.get(Materials.Naquadah), 'N',
                        OrePrefixes.stick.get(Materials.Americium) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Generator_Naquadah_Mark_V.get(1L),
                bits,
                new Object[] { "NCN", "FMF", "WCW", 'M', ItemList.Hull_UV, 'F', ItemList.Field_Generator_UV, 'C',
                        OrePrefixes.circuit.get(Materials.UHV), 'W', OrePrefixes.cableGt04.get(Materials.ElectrumFlux),
                        'N', OrePrefixes.stick.get(Materials.NaquadahAlloy) });

        GT_ModHandler.addCraftingRecipe(
                ItemList.LargeHPSteamTurbine.get(1L),
                bits,
                new Object[] { "CPC", "PMP", "BPB", 'M', ItemList.Hull_IV, 'B',
                        OrePrefixes.pipeLarge.get(Materials.Titanium), 'C', OrePrefixes.circuit.get(Materials.IV), 'P',
                        OrePrefixes.gearGt.get(Materials.Titanium) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.LargePlasmaTurbine.get(1L),
                bits,
                new Object[] { "CPC", "PMP", "BPB", 'M', ItemList.Hull_UV, 'B',
                        OrePrefixes.pipeHuge.get(Materials.Naquadah), 'C', OrePrefixes.circuit.get(Materials.ZPM), 'P',
                        OrePrefixes.gearGt.get(Materials.NaquadahAlloy) });

        GT_ModHandler.addCraftingRecipe(
                ItemList.Super_Tank_LV.get(1L),
                bits,
                new Object[] { "DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_MV, 'M', ItemList.Casing_Tank_1, 'G',
                        OrePrefixes.plate.get(Materials.PulsatingIron), 'D', OrePrefixes.circuit.get(Materials.LV), 'P',
                        OrePrefixes.plate.get(Materials.Aluminium) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Super_Tank_MV.get(1L),
                bits,
                new Object[] { "DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_HV, 'M', ItemList.Casing_Tank_2, 'G',
                        OrePrefixes.plate.get(Materials.VibrantAlloy), 'D', OrePrefixes.circuit.get(Materials.MV), 'P',
                        OrePrefixes.plate.get(Materials.StainlessSteel) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Super_Tank_HV.get(1L),
                bits,
                new Object[] { "DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_HV, 'M', ItemList.Casing_Tank_3, 'G',
                        ItemList.Field_Generator_LV, 'D', OrePrefixes.circuit.get(Materials.HV), 'P',
                        OrePrefixes.plate.get(Materials.StainlessSteel) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Super_Tank_EV.get(1L),
                bits,
                new Object[] { "DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_EV, 'M', ItemList.Casing_Tank_4, 'G',
                        ItemList.Field_Generator_MV, 'D', OrePrefixes.circuit.get(Materials.EV), 'P',
                        OrePrefixes.plate.get(Materials.Titanium) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Super_Tank_IV.get(1L),
                bits,
                new Object[] { "DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_EV, 'M', ItemList.Casing_Tank_5, 'G',
                        ItemList.Field_Generator_HV, 'D', OrePrefixes.circuit.get(Materials.IV), 'P',
                        OrePrefixes.plate.get(Materials.Titanium) });

        GT_ModHandler.addCraftingRecipe(
                ItemList.Quantum_Tank_LV.get(1L),
                bits,
                new Object[] { "DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_IV, 'M', ItemList.Casing_Tank_6, 'G',
                        ItemList.Field_Generator_EV, 'D', OrePrefixes.circuit.get(Materials.LuV), 'P',
                        OrePrefixes.plate.get(Materials.TungstenSteel) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Quantum_Tank_MV.get(1L),
                bits,
                new Object[] { "DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_IV, 'M', ItemList.Casing_Tank_7, 'G',
                        ItemList.Field_Generator_IV, 'D', OrePrefixes.circuit.get(Materials.ZPM), 'P',
                        OrePrefixes.plate.get(Materials.HSSG) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Quantum_Tank_HV.get(1L),
                bits,
                new Object[] { "DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_LuV, 'M', ItemList.Casing_Tank_8, 'G',
                        ItemList.Field_Generator_LuV, 'D', OrePrefixes.circuit.get(Materials.UV), 'P',
                        OrePrefixes.plate.get(Materials.HSSS) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Quantum_Tank_EV.get(1L),
                bits,
                new Object[] { "DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_ZPM, 'M', ItemList.Casing_Tank_9, 'G',
                        ItemList.Field_Generator_ZPM, 'D', OrePrefixes.circuit.get(Materials.UHV), 'P',
                        OrePrefixes.plate.get(Materials.Europium) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Quantum_Tank_IV.get(1L),
                bits,
                new Object[] { "DGD", "PMP", "DUD", 'U', ItemList.Electric_Pump_UV, 'M', ItemList.Casing_Tank_10, 'G',
                        ItemList.Field_Generator_UV, 'D', OrePrefixes.circuit.get(Materials.UEV), 'P',
                        OrePrefixes.plate.get(Materials.Americium) });

        GT_ModHandler.addCraftingRecipe(
                ItemList.Super_Chest_LV.get(1L),
                bits,
                new Object[] { "DPD", "PMP", "DGD", 'M', ItemList.Automation_ChestBuffer_LV, 'G',
                        OrePrefixes.plate.get(Materials.PulsatingIron), 'D', OrePrefixes.circuit.get(Materials.LV), 'P',
                        OrePrefixes.plateDense.get(Materials.Iron) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Super_Chest_MV.get(1L),
                bits,
                new Object[] { "DPD", "PMP", "DGD", 'M', ItemList.Automation_ChestBuffer_MV, 'G',
                        OrePrefixes.plate.get(Materials.VibrantAlloy), 'D', OrePrefixes.circuit.get(Materials.MV), 'P',
                        OrePrefixes.plateDense.get(Materials.Steel) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Super_Chest_HV.get(1L),
                bits,
                new Object[] { "DPD", "PMP", "DGD", 'M', ItemList.Automation_ChestBuffer_HV, 'G',
                        ItemList.Field_Generator_LV, 'D', OrePrefixes.circuit.get(Materials.HV), 'P',
                        OrePrefixes.plateQuintuple.get(Materials.Aluminium) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Super_Chest_EV.get(1L),
                bits,
                new Object[] { "DPD", "PMP", "DGD", 'M', ItemList.Automation_ChestBuffer_EV, 'G',
                        ItemList.Field_Generator_MV, 'D', OrePrefixes.circuit.get(Materials.EV), 'P',
                        OrePrefixes.plateQuintuple.get(Materials.StainlessSteel) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Super_Chest_IV.get(1L),
                bits,
                new Object[] { "DPD", "PMP", "DGD", 'M', ItemList.Automation_ChestBuffer_IV, 'G',
                        ItemList.Field_Generator_HV, 'D', OrePrefixes.circuit.get(Materials.IV), 'P',
                        OrePrefixes.plateQuadruple.get(Materials.Titanium) });

        GT_ModHandler.addCraftingRecipe(
                ItemList.Quantum_Chest_LV.get(1L),
                bits,
                new Object[] { "DPD", "PMP", "DGD", 'M', ItemList.Automation_ChestBuffer_LuV, 'G',
                        ItemList.Field_Generator_EV, 'D', OrePrefixes.circuit.get(Materials.LuV), 'P',
                        OrePrefixes.plateQuadruple.get(Materials.TungstenSteel) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Quantum_Chest_MV.get(1L),
                bits,
                new Object[] { "DPD", "PMP", "DGD", 'M', ItemList.Automation_ChestBuffer_ZPM, 'G',
                        ItemList.Field_Generator_IV, 'D', OrePrefixes.circuit.get(Materials.ZPM), 'P',
                        OrePrefixes.plateTriple.get(Materials.HSSG) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Quantum_Chest_HV.get(1L),
                bits,
                new Object[] { "DPD", "PMP", "DGD", 'M', ItemList.Automation_ChestBuffer_UV, 'G',
                        ItemList.Field_Generator_LuV, 'D', OrePrefixes.circuit.get(Materials.UV), 'P',
                        OrePrefixes.plateTriple.get(Materials.HSSS) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Quantum_Chest_EV.get(1L),
                bits,
                new Object[] { "DPD", "PMP", "DGD", 'M', ItemList.Automation_ChestBuffer_MAX, 'G',
                        ItemList.Field_Generator_ZPM, 'D', OrePrefixes.circuit.get(Materials.UHV), 'P',
                        OrePrefixes.plateDouble.get(Materials.Europium) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Quantum_Chest_IV.get(1L),
                bits,
                new Object[] { "DPD", "PMP", "DGD", 'M', CustomItemList.Automation_ChestBuffer_UEV, 'G',
                        ItemList.Field_Generator_UV, 'D', OrePrefixes.circuit.get(Materials.UEV), 'P',
                        OrePrefixes.plate.get(Materials.Americium) });

        // Hermetic casings
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_Tank_1.get(1L),
                bits,
                new Object[] { "PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Steel), 'I',
                        OrePrefixes.pipeLarge.get(Materials.Plastic) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_Tank_2.get(1L),
                bits,
                new Object[] { "PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Aluminium), 'I',
                        OrePrefixes.pipeLarge.get(Materials.PolyvinylChloride) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_Tank_3.get(1L),
                bits,
                new Object[] { "PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.StainlessSteel), 'I',
                        OrePrefixes.pipeLarge.get(Materials.Polytetrafluoroethylene) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_Tank_4.get(1L),
                bits,
                new Object[] { "PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Titanium), 'I',
                        OrePrefixes.pipeLarge.get(Materials.StainlessSteel) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_Tank_5.get(1L),
                bits,
                new Object[] { "PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.TungstenSteel), 'I',
                        OrePrefixes.pipeLarge.get(Materials.Titanium) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_Tank_6.get(1L),
                bits,
                new Object[] { "PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Chrome), 'I',
                        OrePrefixes.pipeLarge.get(Materials.TungstenSteel) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_Tank_7.get(1L),
                bits,
                new Object[] { "PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Iridium), 'I',
                        OrePrefixes.pipeLarge.get(Materials.NiobiumTitanium) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_Tank_8.get(1L),
                bits,
                new Object[] { "PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Osmium), 'I',
                        OrePrefixes.pipeLarge.get(Materials.Enderium) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_Tank_9.get(1L),
                bits,
                new Object[] { "PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Neutronium), 'I',
                        OrePrefixes.pipeLarge.get(Materials.Naquadah) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_Tank_10.get(1L),
                bits,
                new Object[] { "PPP", "PIP", "PPP", 'P', OrePrefixes.plate.get(Materials.Bedrockium), 'I',
                        OrePrefixes.pipeLarge.get(Materials.MysteriousCrystal) });

        GT_ModHandler.addCraftingRecipe(
                ItemList.Field_Generator_LV.get(1L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE,
                new Object[] { "WCW", "CGC", "WCW", 'G', OrePrefixes.plate.get(Materials.EnderPearl), 'C',
                        OrePrefixes.circuit.get(Materials.HV), 'W', OrePrefixes.plate.get(Materials.RedSteel) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Field_Generator_MV.get(1L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE,
                new Object[] { "WCW", "CGC", "WCW", 'G', OrePrefixes.plate.get(Materials.EnderEye), 'C',
                        OrePrefixes.circuit.get(Materials.EV), 'W', OrePrefixes.plate.get(Materials.TungstenSteel) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Field_Generator_HV.get(1L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE,
                new Object[] { "WCW", "CGC", "WCW", 'G', ItemList.QuantumEye.get(1L), 'C',
                        OrePrefixes.circuit.get(Materials.IV), 'W',
                        OrePrefixes.plateDouble.get(Materials.NiobiumTitanium) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Field_Generator_EV.get(1L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE,
                new Object[] { "WCW", "CGC", "WCW", 'G', OrePrefixes.gem.get(Materials.NetherStar), 'C',
                        OrePrefixes.circuit.get(Materials.LuV), 'W', OrePrefixes.plateDouble.get(Materials.HSSG) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Field_Generator_IV.get(1L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE,
                new Object[] { "WCW", "CGC", "WCW", 'G', ItemList.QuantumStar.get(1L), 'C',
                        OrePrefixes.circuit.get(Materials.ZPM), 'W', OrePrefixes.plateTriple.get(Materials.HSSS) });

        // Solar Panels 1-8 EU. Higher tier ones are in script because they're DCT crafts
        GT_ModHandler.addCraftingRecipe(
                ItemList.Cover_SolarPanel.get(1L, o),
                GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE,
                new Object[] { "SGS", "CPC", "TRT", 'C', OrePrefixes.circuit.get(Materials.LV), 'G',
                        GT_ModHandler.getIC2Item("reinforcedGlass", 1L), 'P',
                        OrePrefixes.plateAlloy.get(Materials.Carbon), 'S', ItemList.Circuit_Silicon_Wafer, 'T',
                        OrePrefixes.wireGt01.get(Materials.RedAlloy), 'R', CustomItemList.AluminiumIronPlate });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Cover_SolarPanel_8V.get(1L, o),
                GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                        | GT_ModHandler.RecipeBits.REVERSIBLE,
                new Object[] { "GSG", "CRC", "PAP", 'C', OrePrefixes.circuit.get(Materials.MV), 'G',
                        ItemList.Cover_SolarPanel.get(1L, o), 'P', OrePrefixes.wireGt01.get(Materials.Tin), 'S',
                        ItemList.Circuit_Silicon_Wafer, 'R', OrePrefixes.plate.get(Materials.GalliumArsenide), 'A',
                        CustomItemList.ReinforcedAluminiumIronPlate });

        // LV+1st MV circuit/parts
        GT_ModHandler.addShapelessCraftingRecipe(
                ItemList.Circuit_Board_Coated.get(1, o),
                new Object[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1),
                        ItemList.IC2_Resin.get(1, o), ItemList.IC2_Resin.get(1, o) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Board_Coated.get(3, o),
                new Object[] { "RRR", "PPP", "RRR", 'P', GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1),
                        'R', ItemList.IC2_Resin.get(1, o) });
        GT_ModHandler.addShapelessCraftingRecipe(
                ItemList.Circuit_Board_Coated.get(1, o),
                new Object[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1),
                        new ItemStack(Items.slime_ball, 1), new ItemStack(Items.slime_ball, 1) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Board_Coated.get(3, o),
                new Object[] { "RRR", "PPP", "RRR", 'P', GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1),
                        'R', new ItemStack(Items.slime_ball, 1) });
        if (TinkerConstruct.isModLoaded()) {
            GT_ModHandler.addShapelessCraftingRecipe(
                    ItemList.Circuit_Board_Coated.get(1, o),
                    new Object[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1),
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "materials", 1, 36),
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "materials", 1, 36) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Board_Coated.get(3, o),
                    new Object[] { "RRR", "PPP", "RRR", 'P',
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1), 'R',
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "materials", 1, 36) });
            GT_ModHandler.addShapelessCraftingRecipe(
                    ItemList.Circuit_Board_Coated.get(1, o),
                    new Object[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1),
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 0),
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 0) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Board_Coated.get(3, o),
                    new Object[] { "RRR", "PPP", "RRR", 'P',
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1), 'R',
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 0) });
            GT_ModHandler.addShapelessCraftingRecipe(
                    ItemList.Circuit_Board_Coated.get(1, o),
                    new Object[] { GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1),
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 1),
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 1) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Board_Coated.get(3, o),
                    new Object[] { "RRR", "PPP", "RRR", 'P',
                            GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 1), 'R',
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 1) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Parts_Resistor.get(1, o),
                    new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                            OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Coal), 'R',
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "materials", 1, 36) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Parts_Resistor.get(1, o),
                    new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                            OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Carbon),
                            'R', GT_ModHandler.getModItem(TinkerConstruct.ID, "materials", 1, 36) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Parts_Resistor.get(1, o),
                    new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                            OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Charcoal),
                            'R', GT_ModHandler.getModItem(TinkerConstruct.ID, "materials", 1, 36) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Parts_Resistor.get(1, o),
                    new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                            OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Lignite),
                            'R', GT_ModHandler.getModItem(TinkerConstruct.ID, "materials", 1, 36) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Parts_Resistor.get(1, o),
                    new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                            OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Coal), 'R',
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 0) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Parts_Resistor.get(1, o),
                    new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                            OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Carbon),
                            'R', GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 0) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Parts_Resistor.get(1, o),
                    new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                            OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Charcoal),
                            'R', GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 0) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Parts_Resistor.get(1, o),
                    new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                            OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Lignite),
                            'R', GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 0) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Parts_Resistor.get(1, o),
                    new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                            OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Coal), 'R',
                            GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 1) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Parts_Resistor.get(1, o),
                    new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                            OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Carbon),
                            'R', GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 1) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Parts_Resistor.get(1, o),
                    new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                            OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Charcoal),
                            'R', GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 1) });
            GT_ModHandler.addCraftingRecipe(
                    ItemList.Circuit_Parts_Resistor.get(1, o),
                    new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                            OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Lignite),
                            'R', GT_ModHandler.getModItem(TinkerConstruct.ID, "strangeFood", 1, 1) });
        }
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Board_Coated_Basic.get(1, o),
                new Object[] { "FFF", "FCF", "FFF", 'C', ItemList.Circuit_Board_Coated.get(1, o), 'F',
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 1) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Good.get(1, o),
                new Object[] { "PAP", "CBC", "DCD", 'D', ItemList.Circuit_Parts_Diode.get(1, o), 'C',
                        Ic2Items.electronicCircuit, 'A', ItemList.IC2_Item_Casing_Steel.get(1, o), 'P',
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 1), 'B',
                        ItemList.Circuit_Board_Phenolic_Good.get(1, o) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Board_Phenolic_Good.get(1, o),
                new Object[] { "FFF", "FCF", "FFF", 'C', ItemList.Circuit_Board_Phenolic.get(1, o), 'F',
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Gold, 1) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Parts_Resistor.get(1, o),
                new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                        OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Coal), 'R',
                        ItemList.IC2_Resin.get(1, o) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Parts_Resistor.get(1, o),
                new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                        OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Carbon), 'R',
                        ItemList.IC2_Resin.get(1, o) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Parts_Resistor.get(1, o),
                new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                        OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Charcoal), 'R',
                        ItemList.IC2_Resin.get(1, o) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Parts_Resistor.get(1, o),
                new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                        OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Lignite), 'R',
                        ItemList.IC2_Resin.get(1, o) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Parts_Resistor.get(1, o),
                new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                        OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Coal), 'R',
                        new ItemStack(Items.slime_ball, 1) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Parts_Resistor.get(1, o),
                new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                        OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Carbon), 'R',
                        new ItemStack(Items.slime_ball, 1) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Parts_Resistor.get(1, o),
                new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                        OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Charcoal), 'R',
                        new ItemStack(Items.slime_ball, 1) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Parts_Resistor.get(1, o),
                new Object[] { "RPR", "FCF", " P ", 'F', OrePrefixes.wireGt01.get(Materials.Copper), 'P',
                        OrePrefixes.wireFine.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.Lignite), 'R',
                        new ItemStack(Items.slime_ball, 1) });
        GT_ModHandler.addCraftingRecipe(
                ItemList.Circuit_Parts_Vacuum_Tube.get(1, o),
                new Object[] { "WWW", "FGF", "SAS", 'G', ItemList.Circuit_Parts_Glass_Tube.get(1, o), 'F',
                        OrePrefixes.wireFine.get(Materials.Copper), 'W', OrePrefixes.wireGt01.get(Materials.Copper),
                        'S', OrePrefixes.stick.get(Materials.Steel), 'A', OrePrefixes.bolt.get(Materials.RedAlloy) });

        // Filter Machine Casing for cleanroom
        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_Vent.get(1L),
                bits,
                new Object[] { "PPP", "SSS", "MFV", 'P', CustomItemList.SteelBars.get(1, o), 'F',
                        OrePrefixes.frameGt.get(Materials.StainlessSteel), 'M', ItemList.Electric_Motor_MV, 'V',
                        OrePrefixes.rotor.get(Materials.Aluminium), 'S', ItemList.Component_Filter });

        GT_ModHandler.addCraftingRecipe(
                ItemList.Casing_Firebricks.get(1L),
                tBitMask,
                new Object[] { "BCB", "BWB", "BCB", 'B', ItemList.Firebrick.get(1), 'C',
                        OrePrefixes.dust.get(Materials.Gypsum), 'W',
                        GT_ModHandler.getModItem(NewHorizonsCoreMod.ID, "dreamcraft_Concrete_bucket", 1L) });

        // Air filter multi blocks
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Casing_AirFilter_Vent_T1.get(1L),
                bits,
                new Object[] { "ThT", "TFT", "TwT", 'T', CustomItemList.SteelBars, 'F',
                        OrePrefixes.frameGt.get(Materials.Steel) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Casing_AirFilter_Turbine_T1.get(1L),
                bits,
                new Object[] { "SSS", "RVR", "MSM", 'V', CustomItemList.Casing_AirFilter_Vent_T1, 'S',
                        OrePrefixes.stickLong.get(Materials.Steel), 'R', OrePrefixes.rotor.get(Materials.Steel), 'M',
                        ItemList.Electric_Motor_LV, 'R', OrePrefixes.screw.get(Materials.Steel) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Machine_Multi_AirFilterT1.get(1L),
                bits,
                new Object[] { "RPR", "MBM", "CGC", 'B', ItemList.Hull_LV, 'R', OrePrefixes.rotor.get(Materials.Steel),
                        'P', ItemList.Electric_Pump_LV, 'M', ItemList.Electric_Motor_LV, 'C',
                        OrePrefixes.cableGt01.get(Materials.Copper), 'G', ItemList.Casing_Turbine });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Casing_AirFilter_Vent_T2.get(1L),
                bits,
                new Object[] { "ThT", "TFT", "TwT", 'T', CustomItemList.TitaniumBars, 'F',
                        OrePrefixes.frameGt.get(Materials.Titanium) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Casing_AirFilter_Turbine_T2.get(1L),
                bits,
                new Object[] { "SSS", "RVR", "MSM", 'V', CustomItemList.Casing_AirFilter_Vent_T2, 'S',
                        OrePrefixes.stickLong.get(Materials.Titanium), 'R', OrePrefixes.rotor.get(Materials.Titanium),
                        'M', ItemList.Electric_Motor_HV, 'R', OrePrefixes.screw.get(Materials.Titanium) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Machine_Multi_AirFilterT2.get(1L),
                bits,
                new Object[] { "RPR", "MBM", "CGC", 'B', ItemList.Hull_HV, 'R',
                        OrePrefixes.rotor.get(Materials.Titanium), 'P', ItemList.Electric_Pump_HV, 'M',
                        ItemList.Electric_Motor_HV, 'C', OrePrefixes.cableGt01.get(Materials.Gold), 'G',
                        ItemList.Casing_Turbine2 });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Casing_AirFilter_Vent_T3.get(1L),
                bits,
                new Object[] { "ThT", "TFT", "TwT", 'T', CustomItemList.TungstenSteelBars, 'F',
                        OrePrefixes.frameGt.get(Materials.TungstenSteel) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Casing_AirFilter_Turbine_T3.get(1L),
                bits,
                new Object[] { "SSS", "RVR", "MSM", 'V', CustomItemList.Casing_AirFilter_Vent_T3, 'S',
                        OrePrefixes.stickLong.get(Materials.TungstenSteel), 'R',
                        OrePrefixes.rotor.get(Materials.TungstenSteel), 'M', ItemList.Electric_Motor_IV, 'R',
                        OrePrefixes.screw.get(Materials.TungstenSteel) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Machine_Multi_AirFilterT3.get(1L),
                bits,
                new Object[] { "RPR", "MBM", "CGC", 'B', ItemList.Hull_IV, 'R',
                        OrePrefixes.rotor.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_IV, 'M',
                        ItemList.Electric_Motor_IV, 'C', OrePrefixes.cableGt01.get(Materials.Tungsten), 'G',
                        ItemList.Casing_Turbine3 });

        GT_ModHandler.addCraftingRecipe(
                CustomItemList.Casing_Pyrolyse.get(1L),
                bits,
                new Object[] { "PhP", "SFS", "PwP", 'P', OrePrefixes.plate.get(Materials.Steel), 'S',
                        OrePrefixes.plate.get(Materials.Iron), 'F', ItemList.Casing_BronzePlatedBricks });

        // For making bee houses
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.WoodenCasing.get(1L),
                bits,
                new Object[] { "SSS", "UCU", "SDS", 'S', OrePrefixes.slab.get(Materials.Wood), 'D',
                        ToolDictNames.craftingToolScrewdriver, 'U', OrePrefixes.screw.get(Materials.Iron), 'C',
                        OrePrefixes.frameGt.get(Materials.Wood) });
        // For making gendustry upgrades
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.IndustryFrame.get(1L),
                bits,
                new Object[] { "PPP", "SBS", "SSS", 'P', OrePrefixes.plate.get(Materials.Palladium), 'S',
                        OrePrefixes.stick.get(Materials.Osmium), 'B', CustomItemList.IridiumBars.get(1) });

        // Magnetic Flux Exhibitor
        GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(GregTech.ID, "gt.blockmachines", 1, 358),
                bits,
                new Object[] { "CBC", "FMF", "CBC", 'M', ItemList.Machine_IV_ElectromagneticSeparator, 'B',
                        OrePrefixes.circuit.get(Materials.IV), 'C', OrePrefixes.plate.get(Materials.TungstenSteel), 'F',
                        ItemList.Conveyor_Module_IV });

        // MagTech Casing
        GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(GregTech.ID, "gt.blockcasings10", 1, 0),
                bits,
                new Object[] { "PhP", "PFP", "PwP", 'P', OrePrefixes.plate.get(Materials.TungstenSteel), 'F',
                        OrePrefixes.frameGt.get(Materials.Titanium) });

        // Electromagnet Housing
        GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(GregTech.ID, "gt.blockmachines", 1, 359),
                bits,
                new Object[] { "CFC", "FMF", "CFC", 'M', ItemList.Hatch_Input_Bus_IV, 'C',
                        OrePrefixes.plate.get(Materials.Polystyrene), 'F', CustomItemList.TungstenSteelBars });

        // TurboCan Pro
        GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(GregTech.ID, "gt.blockmachines", 1, 360),
                bits,
                new Object[] { "CFC", "AMB", "CFC", 'A', ItemList.Machine_HV_Canner, 'B',
                        ItemList.Machine_HV_FluidCanner, 'F', OrePrefixes.circuit.get(Materials.HV), 'C',
                        OrePrefixes.pipeLarge.get(Materials.Steel), 'M', ItemList.Electric_Pump_HV });

        // High Energy Laser Emitter
        GT_ModHandler.addCraftingRecipe(
                ItemList.Machine_Multi_IndustrialLaserEngraver.get(1),
                bits,
                new Object[] { "CFC", "EAE", "CEC", 'A', ItemList.Machine_IV_LaserEngraver, 'F',
                        OrePrefixes.circuit.get(Materials.LuV), 'C', NITINOL_60.getPlate(1), 'E',
                        ItemList.Emitter_IV });

        // Laser Containment Casing
        GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(GregTech.ID, "gt.blockcasings10", 1, 1),
                bits,
                new Object[] { "PhP", "PFP", "PwP", 'P', STELLITE.getPlate(1), 'F', NITINOL_60.getFrameBox(1) });

        // BM raw orbs
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.RawOrbTier1.get(1L),
                bits,
                new Object[] { "XTX", "POP", "PPP", 'X', OrePrefixes.screw.get(Materials.Aluminium), 'T',
                        ToolDictNames.craftingToolScrewdriver, 'P', OrePrefixes.plate.get(Materials.AnnealedCopper),
                        'O', CustomItemList.ReinforcedGlassLense.get(1L) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.RawOrbTier2.get(1L),
                bits,
                new Object[] { "XTX", "POP", "PPP", 'X', OrePrefixes.screw.get(Materials.StainlessSteel), 'T',
                        ToolDictNames.craftingToolScrewdriver, 'P', OrePrefixes.plate.get(Materials.RoseGold), 'O',
                        CustomItemList.ReinforcedGlassLense.get(1L) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.RawOrbTier3.get(1L),
                bits,
                new Object[] { "XTX", "POP", "PPP", 'X', OrePrefixes.screw.get(Materials.Titanium), 'T',
                        ToolDictNames.craftingToolScrewdriver, 'P', OrePrefixes.plate.get(Materials.PulsatingIron), 'O',
                        CustomItemList.ReinforcedGlassLense.get(1L) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.RawOrbTier4.get(1L),
                bits,
                new Object[] { "XTX", "POP", "PPP", 'X', OrePrefixes.screw.get(Materials.TungstenSteel), 'T',
                        ToolDictNames.craftingToolScrewdriver, 'P', OrePrefixes.plate.get(Materials.EnergeticAlloy),
                        'O', CustomItemList.ReinforcedGlassLense.get(1L) });
        if (BartWorks.isModLoaded()) GT_ModHandler.addCraftingRecipe(
                CustomItemList.RawOrbTier5.get(1L),
                bits,
                new Object[] { "XTX", "POP", "PPP", 'X', WerkstoffLoader.LuVTierMaterial.get(screw), 'T',
                        ToolDictNames.craftingToolScrewdriver, 'P', OrePrefixes.plate.get(Materials.FierySteel), 'O',
                        CustomItemList.ReinforcedGlassLense.get(1L) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.RawOrbTier6.get(1L),
                bits,
                new Object[] { "XTX", "POP", "PPP", 'X', OrePrefixes.screw.get(Materials.Iridium), 'T',
                        ToolDictNames.craftingToolScrewdriver, 'P', OrePrefixes.plate.get(Materials.Plutonium241), 'O',
                        CustomItemList.ReinforcedGlassLense.get(1L) });

        // ===================================================================================================
        // Diodes
        // ===================================================================================================

        if (BartWorks.isModLoaded()) {
            Materials[] cables = { // Cable material used in the acid gen, diode and energy distributor below
                    Materials.Bedrockium, // UHV
                    Materials.Draconium, // UEV
                    Materials.NetherStar, // UIV
                    Materials.Quantium, // UMV
                    Materials.BlackPlutonium, // UXV
                    Materials.DraconiumAwakened, // MAX
            };

            final long BITSD = GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE
                    | GT_ModHandler.RecipeBits.REVERSIBLE;

            for (int i = 9; i < GT_Values.VN.length - 1; i++) {
                try {
                    Materials cable = cables[i - 9];
                    ItemStack hull;

                    ItemStack machinehull;
                    switch (i) {
                        case 9: // UHV
                            hull = GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 1L);
                            machinehull = ItemList.Hull_MAX.get(1L);
                            break;
                        case 10: // UEV
                            hull = GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bedrockium, 1L);
                            machinehull = CustomItemList.Hull_UEV.get(1L);
                            break;
                        case 11: // UIV
                            hull = GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BlackPlutonium, 1L);
                            machinehull = CustomItemList.Hull_UIV.get(1L);
                            break;
                        case 12: // UMV
                            hull = GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Infinity, 1L);
                            machinehull = CustomItemList.Hull_UMV.get(1L);
                            break;
                        case 13: // UXV
                            hull = GT_OreDictUnificator.get(OrePrefixes.plate, MaterialsUEVplus.TranscendentMetal, 1L);
                            machinehull = CustomItemList.Hull_UXV.get(1L);
                            break;
                        default: // MAX
                            hull = GT_OreDictUnificator.get(OrePrefixes.plate, MaterialsUEVplus.SpaceTime, 1L);
                            machinehull = CustomItemList.Hull_MAXV.get(1L);
                            break;
                    }

                    GT_ModHandler.addCraftingRecipe(
                            ItemRegistry.energyDistributor[i],
                            BITSD,
                            new Object[] { "PWP", "WCW", "PWP", 'W',
                                    GT_OreDictUnificator.get(OrePrefixes.wireGt16, cable, 1L), 'P', hull, 'C',
                                    machinehull });
                    GT_ModHandler.addCraftingRecipe(
                            ItemRegistry.diode12A[i],
                            BITSD,
                            new Object[] { "WDW", "DCD", "PDP", 'D', OrePrefixes.componentCircuit.get(Materials.Diode),
                                    'W',
                                    GT_OreDictUnificator
                                            .get(i < 13 ? OrePrefixes.cableGt12 : OrePrefixes.wireGt12, cable, 1L),
                                    'P', hull, 'C', machinehull });
                    GT_ModHandler.addCraftingRecipe(
                            ItemRegistry.diode8A[i],
                            BITSD,
                            new Object[] { "WDW", "DCD", "PDP", 'D', OrePrefixes.componentCircuit.get(Materials.Diode),
                                    'W',
                                    GT_OreDictUnificator
                                            .get(i < 13 ? OrePrefixes.cableGt08 : OrePrefixes.wireGt08, cable, 1L),
                                    'P', hull, 'C', machinehull });
                    GT_ModHandler.addCraftingRecipe(
                            ItemRegistry.diode4A[i],
                            BITSD,
                            new Object[] { "WDW", "DCD", "PDP", 'D', OrePrefixes.componentCircuit.get(Materials.Diode),
                                    'W',
                                    GT_OreDictUnificator
                                            .get(i < 13 ? OrePrefixes.cableGt04 : OrePrefixes.wireGt04, cable, 1L),
                                    'P', hull, 'C', machinehull });
                    GT_ModHandler.addCraftingRecipe(
                            ItemRegistry.diode2A[i],
                            BITSD,
                            new Object[] { "WDW", "DCD", "PDP", 'D', OrePrefixes.componentCircuit.get(Materials.Diode),
                                    'W',
                                    GT_OreDictUnificator
                                            .get(i < 13 ? OrePrefixes.cableGt02 : OrePrefixes.wireGt02, cable, 1L),
                                    'P', hull, 'C', machinehull });
                    GT_ModHandler.addCraftingRecipe(
                            ItemRegistry.diode16A[i],
                            BITSD,
                            new Object[] { "WHW", "DCD", "PDP", 'H',
                                    OrePrefixes.componentCircuit.get(Materials.Inductor), 'D',
                                    OrePrefixes.componentCircuit.get(Materials.Diode), 'W',
                                    GT_OreDictUnificator
                                            .get(i < 13 ? OrePrefixes.cableGt16 : OrePrefixes.wireGt16, cable, 1L),
                                    'P', hull, 'C', machinehull });

                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }

        GT_ModHandler.addCraftingRecipe(
                CustomItemList.UnfiredClayBrick.get(8L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE,
                new Object[] { "CCC", "CFC", "CCC", 'C', new ItemStack(Items.clay_ball, 1, 0), 'F',
                        new ItemStack(NHItems.WOODEN_BRICK_FORM.get(), 1, GT_Values.W) });
        GT_ModHandler.addShapelessCraftingRecipe(
                CustomItemList.UnfiredClayBrick.get(1L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE,
                new Object[] { new ItemStack(Items.clay_ball, 1, 0),
                        new ItemStack(NHItems.WOODEN_BRICK_FORM.get(), 1, GT_Values.W) });
        if (TinkerConstruct.isModLoaded()) {
            Recipe.of(
                    new ItemStack(NHItems.WOODEN_BRICK_FORM.get(), 1, GT_Values.W),
                    ToolDictNames.craftingToolKnife,
                    GT_ModHandler.getModItem(aTextTConstruct, "blankPattern", 1L, 0))
                    .provideTo(shapelessUnremovableGtRecipes())
                    .provideTo(MANTLE.manualShapedCraftingRecipeNamed("woodenformbrick"));
            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.UnfiredSearedBrick.get(8L),
                    GT_ModHandler.RecipeBits.NOT_REMOVABLE,
                    new Object[] { "GGG", "GFG", "GGG", 'G',
                            GT_ModHandler.getModItem(aTextTConstruct, "CraftedSoil", 1L, 1), 'F',
                            new ItemStack(NHItems.WOODEN_BRICK_FORM.get(), 1, GT_Values.W) });
            Recipe.of(
                    CustomItemList.UnfiredSearedBrick.get(1L),
                    GT_ModHandler.getModItem(aTextTConstruct, "CraftedSoil", 1L, 1),
                    new ItemStack(NHItems.WOODEN_BRICK_FORM.get(), 1, GT_Values.W))
                    .provideTo(shapelessUnremovableGtRecipes())
                    .provideTo(MANTLE.manualShapedCraftingRecipeNamed("unfiredsearedbrick"));
            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.UnfiredSlimeSoulBrick.get(8L),
                    GT_ModHandler.RecipeBits.NOT_REMOVABLE,
                    new Object[] { "SSS", "SFS", "SSS", 'S',
                            GT_ModHandler.getModItem(aTextTConstruct, "CraftedSoil", 1L, 6), 'F',
                            new ItemStack(NHItems.WOODEN_BRICK_FORM.get(), 1, GT_Values.W) });
            GT_ModHandler.addShapelessCraftingRecipe(
                    CustomItemList.UnfiredSlimeSoulBrick.get(1L),
                    GT_ModHandler.RecipeBits.NOT_REMOVABLE,
                    new Object[] { GT_ModHandler.getModItem(aTextTConstruct, "CraftedSoil", 1L, 6),
                            new ItemStack(NHItems.WOODEN_BRICK_FORM.get(), 1, GT_Values.W) });
        }
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.UnfiredCokeOvenBrick.get(3L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE,
                new Object[] { "CCC", "SFS", "SSS", 'C', new ItemStack(Items.clay_ball, 1, 0), 'S',
                        GT_OreDictUnificator.get("sand", 1L), 'F',
                        new ItemStack(NHItems.WOODEN_BRICK_FORM.get(), 1, GT_Values.W) });

        GT_ModHandler.addCraftingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.QuartzSand, 1L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] { "S", "m", 'S', new ItemStack(Blocks.sand, 1, 32767) });
        GT_ModHandler.addCraftingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Flint, 1L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] { "F", "m", 'F', new ItemStack(Items.flint, 1, 0) });

        GT_ModHandler.addShapelessCraftingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 1L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] { OrePrefixes.dust.get(Materials.QuartzSand), OrePrefixes.dustTiny.get(Materials.Flint) });
        GT_ModHandler.addShapelessCraftingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 8L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] { OrePrefixes.dust.get(Materials.QuartzSand), OrePrefixes.dust.get(Materials.QuartzSand),
                        OrePrefixes.dust.get(Materials.QuartzSand), OrePrefixes.dust.get(Materials.QuartzSand),
                        OrePrefixes.dust.get(Materials.QuartzSand), OrePrefixes.dust.get(Materials.QuartzSand),
                        OrePrefixes.dust.get(Materials.QuartzSand), OrePrefixes.dust.get(Materials.QuartzSand),
                        OrePrefixes.dust.get(Materials.Flint) });

        GT_ModHandler.addCraftingRecipe(
                new ItemStack(Blocks.iron_bars, 3, 0),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE,
                new Object[] { " h ", "SSS", "SSS", 'S',
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.AnyIron, 1) });
        GT_ModHandler.addCraftingRecipe(
                CustomItemList.SteelBars.get(3L, o),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE,
                new Object[] { " h ", "SSS", "SSS", 'S',
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 1) });
        GT_ModHandler.addCraftingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.toolHeadHoe, Materials.Steel, 1L),
                GT_Proxy.tBits,
                new Object[] { "PIh", "f  ", 'P', OrePrefixes.plate.get(Materials.Steel), 'I',
                        OrePrefixes.ingot.get(Materials.Steel) });

        GT_ModHandler.addShapelessCraftingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Clay, 1L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] { ToolDictNames.craftingToolMortar,
                        new ItemStack(Blocks.stained_hardened_clay, 1, GT_Values.W) });
        GT_ModHandler.addShapelessCraftingRecipe(
                GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Clay, 1L),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED,
                new Object[] { ToolDictNames.craftingToolMortar, new ItemStack(Blocks.hardened_clay, 1) });

        GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorBatpack", 1, GT_Values.W),
                bits,
                new Object[] { "RCR", "RAR", "RTR", 'R',
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatREDischarged", 1, 0), 'C',
                        OrePrefixes.circuit.get(Materials.LV), 'A', OrePrefixes.itemCasing.get(Materials.Aluminium),
                        'T', OrePrefixes.wireGt02.get(Materials.Tin) });
        GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorAdvBatpack", 1, GT_Values.W),
                bits,
                new Object[] { "RCR", "RAR", "RTR", 'R',
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemAdvBat", 1, GT_Values.W), 'C',
                        OrePrefixes.circuit.get(Materials.MV), 'A',
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorBatpack", 1, GT_Values.W), 'T',
                        OrePrefixes.wireGt04.get(Materials.AnnealedCopper) });
        GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorEnergypack", 1, GT_Values.W),
                bits,
                new Object[] { "CSC", "EXE", "STS", 'E',
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatCrystal", 1, GT_Values.W), 'C',
                        OrePrefixes.circuit.get(Materials.HV), 'X',
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorAdvBatpack", 1, GT_Values.W), 'T',
                        OrePrefixes.wireGt08.get(Materials.Gold), 'S',
                        OrePrefixes.itemCasing.get(Materials.StainlessSteel) });
        GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemAdvBat", 1, GT_Values.W),
                bits,
                new Object[] { "WdW", "HBH", "HXH", 'W', OrePrefixes.wireGt02.get(Materials.Copper), 'H',
                        OrePrefixes.itemCasing.get(Materials.Copper), 'X', OrePrefixes.itemCasing.get(Materials.Lead),
                        'B', ItemList.Battery_Hull_MV });
        if (GalacticraftCore.isModLoaded()) GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemNightvisionGoggles", 1, GT_Values.W),
                bits,
                new Object[] { "AXA", "RBR", "SdS", 'A',
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorHeatSwitchDiamond", 1, 1), 'X',
                        OrePrefixes.screw.get(Materials.StainlessSteel), 'B',
                        OrePrefixes.bolt.get(Materials.StainlessSteel), 'R',
                        OrePrefixes.ring.get(Materials.StainlessSteel), 'S',
                        GT_ModHandler.getModItem(GalacticraftCore.ID, "item.sensorLens", 1, 0) });
        GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemTreetapElectric", 1, GT_Values.W),
                bits,
                new Object[] { "dRD", "RPB", "ECS", 'R', OrePrefixes.stickLong.get(Materials.Steel), 'D',
                        OrePrefixes.toolHeadDrill.get(Materials.Steel), 'P', ItemList.Electric_Pump_LV, 'B',
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatRE", 1, GT_Values.W), 'E',
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemRecipePart", 1, 3), 'C',
                        OrePrefixes.cableGt01.get(Materials.Tin), 'S', screw.get(Materials.Steel) });
        GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemToolHoe", 1, GT_Values.W),
                bits,
                new Object[] { "dPH", "PGB", "ECS", 'S', screw.get(Materials.Steel), 'H',
                        OrePrefixes.toolHeadHoe.get(Materials.Steel), 'G', OrePrefixes.gearGtSmall.get(Materials.Steel),
                        'B', GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemBatRE", 1, GT_Values.W), 'E',
                        GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemRecipePart", 1, 3), 'P',
                        OrePrefixes.plate.get(Materials.Steel) });

        if (OpenComputers.isModLoaded()) GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(OpenComputers.ID, "wrench", 1, 0),
                GT_Proxy.tBits,
                new Object[] { "IWI", "ICI", " I ", 'W', ToolDictNames.craftingToolWrench, 'I',
                        OrePrefixes.ingot.get(Materials.Iron), 'C',
                        GT_ModHandler.getModItem(OpenComputers.ID, "item", 1, 24) });

        if (OpenPrinters.isModLoaded()) GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(OpenPrinters.ID, "openprinter.folder", 1L, 0),
                GT_Proxy.tBits,
                new Object[] { "PGP", " P ", 'P', new ItemStack(Items.paper, 1, 0), 'G',
                        new ItemStack(Items.slime_ball, 1, 0) });

        GT_ModHandler.addCraftingRecipe(
                GT_ModHandler.getModItem(NewHorizonsCoreMod.ID, "dreamcraft_Concrete_bucket", 1, 0),
                bits4,
                new Object[] { "CBS", "CWA", " Y ", 'C', OrePrefixes.dust.get(Materials.Calcite), 'S',
                        OrePrefixes.dust.get(Materials.Stone), 'Y', OrePrefixes.dust.get(Materials.Clay), 'A',
                        OrePrefixes.dust.get(Materials.QuartzSand), 'W', new ItemStack(Items.water_bucket, 1, 0), 'B',
                        new ItemStack(Items.bucket, 1, 0) });
        GT_ModHandler.addCraftingRecipe(
                new ItemStack(Items.clay_ball, 3, 0),
                GT_Proxy.tBits,
                new Object[] { "CCC", "CBC", "CCC", 'C', OrePrefixes.dustSmall.get(Materials.Clay), 'B',
                        new ItemStack(Items.water_bucket, 1, 0) });
        if (IguanaTweaksTinkerConstruct.isModLoaded()) {
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(NewHorizonsCoreMod.ID, "dreamcraft_Concrete_bucket", 1, 0),
                    bits4,
                    new Object[] { "CBS", "CWA", " Y ", 'C', OrePrefixes.dust.get(Materials.Calcite), 'S',
                            OrePrefixes.dust.get(Materials.Stone), 'Y', OrePrefixes.dust.get(Materials.Clay), 'A',
                            OrePrefixes.dust.get(Materials.QuartzSand), 'W',
                            GT_ModHandler.getModItem(IguanaTweaksTinkerConstruct.ID, "clayBucketWater", 1, 0), 'B',
                            new ItemStack(Items.bucket, 1, 0) });
            GT_ModHandler.addCraftingRecipe(
                    new ItemStack(Items.clay_ball, 3, 0),
                    GT_Proxy.tBits,
                    new Object[] { "CCC", "CBC", "CCC", 'C', OrePrefixes.dustSmall.get(Materials.Clay), 'B',
                            GT_ModHandler.getModItem(IguanaTweaksTinkerConstruct.ID, "clayBucketWater", 1, 0) });
        }

        if (Forestry.isModLoaded()) {
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Forestry.ID, "engine", 1L, 0),
                    bits2,
                    new Object[] { "BLB", "SPS", "GCG", 'B', OrePrefixes.plate.get(Materials.StainlessSteel), 'L',
                            OrePrefixes.plate.get(Materials.Lapis), 'S',
                            OrePrefixes.spring.get(Materials.StainlessSteel), 'P', OreDictNames.craftingPiston, 'G',
                            OrePrefixes.circuit.get(Materials.LV), 'C',
                            GT_ModHandler.getModItem(Forestry.ID, "sturdyMachine", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Forestry.ID, "engine", 1L, 1),
                    bits2,
                    new Object[] { "BLB", "SPS", "GCG", 'B', OrePrefixes.plate.get(Materials.Iron), 'L',
                            OrePrefixes.plate.get(Materials.Lapis), 'S', OrePrefixes.spring.get(Materials.Iron), 'P',
                            OreDictNames.craftingPiston, 'G', OrePrefixes.gearGt.get(Materials.Iron), 'C',
                            GT_ModHandler.getModItem(Forestry.ID, "sturdyMachine", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Forestry.ID, "engine", 1L, 2),
                    bits2,
                    new Object[] { "BLB", "SPS", "GCG", 'B', OrePrefixes.plate.get(Materials.Bronze), 'L',
                            OrePrefixes.plate.get(Materials.Lapis), 'S', OrePrefixes.spring.get(Materials.Bronze), 'P',
                            OreDictNames.craftingPiston, 'G', OrePrefixes.gearGt.get(Materials.Bronze), 'C',
                            GT_ModHandler.getModItem(Forestry.ID, "sturdyMachine", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Forestry.ID, "engine", 1L, 4),
                    bits2,
                    new Object[] { "BLB", "SPS", "GCG", 'B', OrePrefixes.plate.get(Materials.Gold), 'L',
                            OrePrefixes.plate.get(Materials.Lapis), 'S', OrePrefixes.spring.get(Materials.WroughtIron),
                            'P', OreDictNames.craftingPiston, 'G', OrePrefixes.gearGt.get(Materials.WroughtIron), 'C',
                            GT_ModHandler.getModItem(Forestry.ID, "sturdyMachine", 1L, 0) });
        }

        // Rocket parts
        if ((GalacticraftCore.isModLoaded()) && (GalacticraftMars.isModLoaded()) && (GalaxySpace.isModLoaded())) {

            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.RawSDHCAlloy.get(1L),
                    bits,
                    new Object[] { "SRS", "TCD", "SHS", 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'R',
                            ToolDictNames.craftingToolScrewdriver, 'T',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualBronze", 1L, 0), 'D',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualAluminium", 1L, 0), 'C',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedCoal", 1L, 0), 'H',
                            ToolDictNames.craftingToolHardHammer });
            // fuel canisters
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(GalaxySpace.ID, "item.ModuleSmallCanister", 1L, 0),
                    bits,
                    new Object[] { "PPP", "PCP", "PPP", 'P', OrePrefixes.compressed.get(Materials.Steel), 'C',
                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.oilCanisterPartial", 1L, 1001) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(GalaxySpace.ID, "item.ModuleSmallFuelCanister", 1L, 0),
                    bits,
                    new Object[] { "SRS", "TCD", "SHS", 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'R',
                            ToolDictNames.craftingToolScrewdriver, 'T',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualBronze", 1L, 0), 'D',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedDualAluminium", 1L, 0), 'C',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.ModuleSmallCanister", 1L, 0), 'H',
                            ToolDictNames.craftingToolHardHammer });
            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.MediumFuelCanister.get(1L),
                    bits,
                    new Object[] { "SRS", "TCD", "SHS", 'S', OrePrefixes.screw.get(Materials.Titanium), 'R',
                            ToolDictNames.craftingToolScrewdriver, 'T',
                            CustomItemList.TitaniumDualCompressedPlates.get(1), 'D',
                            CustomItemList.DeshDualCompressedPlates.get(1), 'C',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.ModuleSmallFuelCanister", 1L, 0), 'H',
                            ToolDictNames.craftingToolHardHammer });
            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.LargeFuelCanister.get(1L),
                    bits,
                    new Object[] { "SRS", "TCD", "SHS", 'S', OrePrefixes.screw.get(Materials.Chrome), 'R',
                            ToolDictNames.craftingToolScrewdriver, 'T',
                            CustomItemList.QuantinumDualCompressedPlates.get(1), 'D',
                            CustomItemList.IceDualCompressedPlates.get(1), 'C',
                            CustomItemList.MediumFuelCanister.get(1), 'H', ToolDictNames.craftingToolHardHammer });
            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.ExtraLargeFuelCanister.get(1L),
                    bits,
                    new Object[] { "SRS", "TCD", "SHS", 'S', OrePrefixes.screw.get(Materials.Iridium), 'R',
                            ToolDictNames.craftingToolScrewdriver, 'T',
                            CustomItemList.MytrylDualCompressedPlates.get(1), 'D',
                            CustomItemList.MysteriousCrystalDualCompressedPlates.get(1), 'C',
                            CustomItemList.LargeFuelCanister.get(1), 'H', ToolDictNames.craftingToolHardHammer });
            // engines

            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(GalacticraftCore.ID, "item.engine", 1L, 0),
                    bits,
                    new Object[] { "SCS", "HFH", "HAH", 'S',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0), 'C',
                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.oilCanisterPartial", 1L, 1001), 'H',
                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.heavyPlating", 1L, 0), 'F',
                            ItemList.Casing_Firebox_Steel, 'A', ItemList.Cover_ActivityDetector });

            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 1L, 1),
                    bits,
                    new Object[] { "BPB", "PPP", "EPE", 'B',
                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.engine", 1L, 1), 'P',
                            GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 1L, 0), 'E',
                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.engine", 1L, 0) });

            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.HeavyDutyRocketEngineTier3.get(1L),
                    bits,
                    new Object[] { "BPB", "PPP", "EPE", 'B', CustomItemList.Tier2Booster.get(1), 'P',
                            CustomItemList.HeavyDutyPlateTier5.get(1), 'E',
                            GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 1L, 1) });
            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.HeavyDutyRocketEngineTier4.get(1L),
                    bits,
                    new Object[] { "BPB", "PPP", "EPE", 'B', CustomItemList.Tier3Booster.get(1), 'P',
                            CustomItemList.HeavyDutyPlateTier7.get(1), 'E',
                            CustomItemList.HeavyDutyRocketEngineTier3.get(1) });
            // nose cones
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(GalacticraftCore.ID, "item.noseCone", 1L, 0),
                    bits,
                    new Object[] { "SNH", "CPC", "PPP", 'N', GT_ModHandler
                            .getModItem(ProjectRedIllumination.ID, "projectred.illumination.cagelamp2.inv", 1L, 14),
                            'P', GT_ModHandler.getModItem(GalacticraftCore.ID, "item.heavyPlating", 1L, 0), 'C',
                            OrePrefixes.screw.get(Materials.StainlessSteel), 'S', ToolDictNames.craftingToolScrewdriver,
                            'H', ToolDictNames.craftingToolHardHammer });

            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(GalacticraftMars.ID, "item.heavyNoseCone", 1L, 0),
                    bits,
                    new Object[] { "SNH", "CPC", "PPP", 'N',

                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.noseCone", 1L, 0), 'P',
                            GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 1L, 0), 'C',
                            OrePrefixes.screw.get(Materials.Titanium), 'S', ToolDictNames.craftingToolScrewdriver, 'H',
                            ToolDictNames.craftingToolHardHammer });

            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.HeavyDutyNoseConeTier3.get(1L),
                    bits,
                    new Object[] { "SNH", "CPC", "PPP", 'N',
                            GT_ModHandler.getModItem(GalacticraftMars.ID, "item.heavyNoseCone", 1L, 0), 'P',
                            CustomItemList.HeavyDutyPlateTier5.get(1), 'C',
                            OrePrefixes.screw.get(Materials.TungstenSteel), 'S', ToolDictNames.craftingToolScrewdriver,
                            'H', ToolDictNames.craftingToolHardHammer });
            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.HeavyDutyNoseConeTier4.get(1L),
                    bits,
                    new Object[] { "SNH", "CPC", "PPP", 'N', CustomItemList.HeavyDutyNoseConeTier3.get(1), 'P',
                            CustomItemList.HeavyDutyPlateTier7.get(1), 'C',
                            OrePrefixes.screw.get(Materials.NaquadahAlloy), 'S', ToolDictNames.craftingToolScrewdriver,
                            'H', ToolDictNames.craftingToolHardHammer });
            // rocket fins

            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(GalacticraftCore.ID, "item.rocketFins", 1L, 0),
                    bits,
                    new Object[] { "HPF", "QPQ", "QSQ", 'P',
                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.basicItem", 1L, 9), 'Q',
                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.heavyPlating", 1L, 0), 'S',
                            ToolDictNames.craftingToolSaw, 'F', ToolDictNames.craftingToolFile, 'H',
                            ToolDictNames.craftingToolHardHammer });

            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 1L, 2),
                    bits,
                    new Object[] { "HPF", "QPQ", "QSQ", 'P',
                            GT_ModHandler.getModItem(GalacticraftMars.ID, "item.null", 1L, 3), 'Q',
                            GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 1L, 0), 'S',
                            ToolDictNames.craftingToolSaw, 'F', ToolDictNames.craftingToolFile, 'H',
                            ToolDictNames.craftingToolHardHammer });
            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.HeavyDutyRocketFinsTier3.get(1L),
                    bits,
                    new Object[] { "HPF", "QPQ", "QSQ", 'P', CustomItemList.HeavyDutyPlateTier4.get(1), 'Q',
                            CustomItemList.HeavyDutyPlateTier5.get(1), 'S',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0), 'F',
                            ToolDictNames.craftingToolFile, 'H', ToolDictNames.craftingToolHardHammer });
            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.HeavyDutyRocketFinsTier4.get(1L),
                    bits,
                    new Object[] { "HPF", "QPQ", "QSQ", 'P', CustomItemList.HeavyDutyPlateTier6.get(1), 'Q',
                            CustomItemList.HeavyDutyPlateTier7.get(1), 'S',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0), 'F',
                            ToolDictNames.craftingToolFile, 'H', ToolDictNames.craftingToolHardHammer });
            // booster

            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(GalacticraftCore.ID, "item.engine", 1L, 1),
                    bits,
                    new Object[] { "LLL", "PBP", "PVP", 'L',
                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.meteoricIronIngot", 1L, 1), 'P',
                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.heavyPlating", 1L, 0), 'B',
                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.fuelCanisterPartial", 1L, 1), 'V',
                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.airVent", 1L, 0) });

            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.Tier2Booster.get(1L),
                    bits,
                    new Object[] { "LLL", "PBP", "PVP", 'L', CustomItemList.LedoxCompressedPlate.get(1), 'P',
                            GT_ModHandler.getModItem(GalacticraftMars.ID, "item.itemBasicAsteroids", 1L, 0), 'B',
                            GT_ModHandler.getModItem(GalacticraftCore.ID, "item.engine", 1L, 1), 'V',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.Tier3Booster.get(1L),
                    bits,
                    new Object[] { "LLL", "PBP", "PVP", 'L', CustomItemList.MytrylCompressedPlate.get(1), 'P',
                            CustomItemList.HeavyDutyPlateTier5.get(1), 'B', CustomItemList.Tier2Booster.get(1), 'V',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    CustomItemList.Tier4Booster.get(1L),
                    bits,
                    new Object[] { "LLL", "PBP", "PVP", 'L', CustomItemList.BlackPlutoniumCompressedPlate.get(1), 'P',
                            CustomItemList.HeavyDutyPlateTier7.get(1), 'B', CustomItemList.Tier3Booster.get(1), 'V',
                            GT_ModHandler.getModItem(GalaxySpace.ID, "item.CompressedSDHD120", 1L, 0) });

        }

        if (Mods.GraviSuite.isModLoaded()) {
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorJetpackElectric", 1, GT_Values.W),
                    bits,
                    new Object[] { "SCS", "MBM", "EWE", 'S', OrePrefixes.itemCasing.get(Materials.StainlessSteel), 'C',
                            OrePrefixes.circuit.get(Materials.HV), 'M', ItemList.Electric_Motor_HV, 'B',
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorBatpack", 1, GT_Values.W), 'W',
                            OrePrefixes.wireGt04.get(Materials.AnnealedCopper), 'E',
                            GT_ModHandler.getModItem(Mods.GraviSuite.ID, "itemSimpleItem", 1, 6) });
            if ((BuildCraftFactory.isModLoaded()) && (AdventureBackpack.isModLoaded())) GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(IndustrialCraft2.ID, "itemArmorJetpack", 1, GT_Values.W),
                    bits,
                    new Object[] { "SXS", "TCT", "EZE", 'S', OrePrefixes.itemCasing.get(Materials.StainlessSteel), 'X',
                            OrePrefixes.circuit.get(Materials.HV), 'T',
                            GT_ModHandler.getModItem(BuildCraftFactory.ID, "tankBlock", 1, 0), 'C',
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "reactorCoolantSix", 1, 1), 'Z',
                            GT_ModHandler.getModItem(AdventureBackpack.ID, "backpackComponent", 1, 5), 'E',
                            GT_ModHandler.getModItem(Mods.GraviSuite.ID, "itemSimpleItem", 1, 6) });

            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(Mods.GraviSuite.ID, "itemSimpleItem", 1, 3));
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Mods.GraviSuite.ID, "itemSimpleItem", 1, 3),
                    new Object[] { "OCO", "XWX", "OCO", 'C', OrePrefixes.wireGt12.get(Materials.SuperconductorLuV), 'X',
                            GT_ModHandler.getModItem(Mods.GraviSuite.ID, "itemSimpleItem", 1, 2), 'O',
                            GT_ModHandler.getModItem(IndustrialCraft2.ID, "blockMachine2", 1, 1), 'W',
                            ItemList.Transformer_LuV_IV.get(1, o) });
        }

        if (Railcraft.isModLoaded()) {
            GT_Log.out.println("GT_Mod: Replacing Railcraft recipes with slightly more Oredicted variants");

            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 2L, 0),
                    bits4,
                    new Object[] { "SPS", "PdP", "SPS", 'P', OrePrefixes.plate.get(Materials.AnyIron), 'S',
                            OrePrefixes.screw.get(Materials.AnyIron) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 2L, 1),
                    bits4,
                    new Object[] { "SPS", "LdL", "SPS", 'P', OrePrefixes.plate.get(Materials.AnyIron), 'S',
                            OrePrefixes.screw.get(Materials.AnyIron), 'L',
                            new ItemStack(Blocks.glass_pane, 1, 32767) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 2L, 1),
                    bits4,
                    new Object[] { "SPS", "LdL", "SPS", 'P', OrePrefixes.plate.get(Materials.AnyIron), 'S',
                            OrePrefixes.screw.get(Materials.AnyIron), 'L',
                            GT_ModHandler.getModItem(aTextTConstruct, "GlassPane", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 2),
                    bits4,
                    new Object[] { "SPS", "BdB", "SPS", 'S', OrePrefixes.screw.get(Materials.AnyIron), 'B',
                            new ItemStack(Blocks.iron_bars, 1, 0), 'P', OrePrefixes.pipeLarge.get(Materials.Bronze) });
            // for recycling
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 3),
                    GT_ModHandler.RecipeBits.REVERSIBLE,
                    new Object[] { "PPP", " h ", "PPP", 'P', OrePrefixes.itemCasing.get(Materials.Iron) });
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 3));
            // actual
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 3),
                    bits4,
                    new Object[] { "PPP", "ShS", "PPP", 'P', OrePrefixes.itemCasing.get(Materials.Iron), 'S',
                            OrePrefixes.screw.get(Materials.AnyIron) });
            // for recycling
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 4),
                    GT_ModHandler.RecipeBits.REVERSIBLE,
                    new Object[] { "PPP", " h ", "PPP", 'P', OrePrefixes.itemCasing.get(Materials.Steel) });
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 4));
            // actual
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 4),
                    bits4,
                    new Object[] { "PPP", "ShS", "PPP", 'P', OrePrefixes.itemCasing.get(Materials.Steel), 'S',
                            OrePrefixes.screw.get(Materials.Steel) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 5),
                    bits,
                    new Object[] { "PCP", "BFB", "PUP", 'B', new ItemStack(Blocks.brick_block), 'P',
                            OrePrefixes.plate.get(Materials.AnyIron), 'U', OreDictNames.craftingIronFurnace, 'C',
                            new ItemStack(Items.cauldron, 1, 0), 'F', ItemList.Casing_Firebox_Bronze });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 6),
                    bits,
                    new Object[] { "PCP", "BFB", "PUP", 'B', CustomItemList.SteelBars, 'P',
                            OrePrefixes.plate.get(Materials.Steel), 'U', OreDictNames.craftingIronFurnace, 'C',
                            new ItemStack(Items.cauldron, 1, 0), 'F', ItemList.Casing_Firebox_Bronze });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 7),
                    bits4,
                    new Object[] { "BLB", "SPS", "GCG", 'B', OrePrefixes.plate.get(Materials.AnyCopper), 'L',
                            OrePrefixes.plate.get(Materials.Lapis), 'S', OrePrefixes.spring.get(Materials.AnyCopper),
                            'P', OreDictNames.craftingPiston, 'G', OrePrefixes.gearGt.get(Materials.AnyCopper), 'C',
                            GT_ModHandler.getModItem(Forestry.ID, "sturdyMachine", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 8),
                    bits4,
                    new Object[] { "BLB", "SPS", "GCG", 'B', OrePrefixes.plate.get(Materials.Steel), 'L',
                            OrePrefixes.plate.get(Materials.Lapis), 'S', OrePrefixes.spring.get(Materials.Steel), 'P',
                            OreDictNames.craftingPiston, 'G', OrePrefixes.gearGt.get(Materials.Steel), 'C',
                            GT_ModHandler.getModItem(Forestry.ID, "sturdyMachine", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 9),
                    bits4,
                    new Object[] { "BLB", "SPS", "GCG", 'B', OrePrefixes.plate.get(Materials.Aluminium), 'L',
                            OrePrefixes.plate.get(Materials.Lapis), 'S', OrePrefixes.spring.get(Materials.Aluminium),
                            'P', OreDictNames.craftingPiston, 'G', OrePrefixes.gearGt.get(Materials.Aluminium), 'C',
                            GT_ModHandler.getModItem(Forestry.ID, "sturdyMachine", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 10),
                    bits,
                    new Object[] { "PEP", "GOG", "OOO", 'P', OrePrefixes.plate.get(Materials.Obsidian), 'E',
                            OrePrefixes.gem.get(Materials.EnderPearl), 'O', OrePrefixes.stone.get(Materials.Obsidian),
                            'G', OrePrefixes.plate.get(Materials.Gold), });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 11),
                    bits4,
                    new Object[] { "SPS", "PRP", "SPS", 'S', OrePrefixes.screw.get(Materials.Steel), 'P',
                            OrePrefixes.plate.get(Materials.Obsidian), 'R',
                            GT_ModHandler.getModItem(ExtraUtilities.ID, "trashcan", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 2L, 13),
                    bits4,
                    new Object[] { "SPS", "PdP", "SPS", 'P', OrePrefixes.plate.get(Materials.Steel), 'S',
                            OrePrefixes.screw.get(Materials.Steel) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 2L, 14),
                    bits4,
                    new Object[] { "SPS", "LdL", "SPS", 'P', OrePrefixes.plate.get(Materials.Steel), 'S',
                            OrePrefixes.screw.get(Materials.Steel), 'L', new ItemStack(Blocks.glass_pane, 1, 32767) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 2L, 14),
                    bits4,
                    new Object[] { "SPS", "LdL", "SPS", 'P', OrePrefixes.plate.get(Materials.Steel), 'S',
                            OrePrefixes.screw.get(Materials.Steel), 'L',
                            GT_ModHandler.getModItem(aTextTConstruct, "GlassPane", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 15),
                    bits4,
                    new Object[] { "SPS", "BdB", "SPS", 'S', OrePrefixes.screw.get(Materials.Steel), 'B',
                            CustomItemList.SteelBars, 'P', OrePrefixes.pipeLarge.get(Materials.Steel) });

            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 2L, 1),
                    bits,
                    new Object[] { "CSC", "SwS", "CSC", 'S', ItemList.Casing_SolidSteel, 'C',
                            GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 4) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 1L, 2),
                    bits,
                    new Object[] { "IOI", "GEG", "IOI", 'G', OrePrefixes.plateDouble.get(Materials.Gold), 'I',
                            OrePrefixes.plate.get(Materials.Emerald), 'E', OrePrefixes.gem.get(Materials.EnderPearl),
                            'O', OrePrefixes.plate.get(Materials.Obsidian) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 4L, 3),
                    bits,
                    new Object[] { "CSC", "SHS", "CSC", 'C',
                            GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 4), 'S',
                            ItemList.Casing_Firebox_Steel, 'H', ItemList.Machine_HP_Furnace });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 1L, 5),
                    bits,
                    new Object[] { "PBP", "PwP", "LHW", 'P', OrePrefixes.plate.get(Materials.Steel), 'H',
                            GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 4), 'B',
                            new ItemStack(Blocks.iron_bars, 1, 0), 'L', new ItemStack(Items.lava_bucket, 1, 0), 'W',
                            new ItemStack(Items.water_bucket, 1, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 1L, 6),
                    bits,
                    new Object[] { "SGS", "EDE", "SGS", 'E', OrePrefixes.plate.get(Materials.Emerald), 'S',
                            OrePrefixes.plate.get(Materials.Steel), 'G', new ItemStack(Blocks.glass_pane, 1, 32767),
                            'D', new ItemStack(Blocks.dispenser, 1, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 1L, 8),
                    bits,
                    new Object[] { "GPG", "PXP", "hCf", 'P', OreDictNames.craftingPiston, 'G',
                            OrePrefixes.gear.get(Materials.Iron), 'C',
                            GT_ModHandler.getModItem(Forestry.ID, "factory2", 1L, 2), 'X',
                            GT_ModHandler.getModItem(Forestry.ID, "sturdyMachine", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 1L, 9),
                    bits,
                    new Object[] { "PBP", "PDP", "PPP", 'B', new ItemStack(Blocks.iron_bars, 1, 0), 'P',
                            OrePrefixes.plate.get(Materials.Steel), 'D', new ItemStack(Blocks.dispenser, 1, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 1L, 10),
                    bits,
                    new Object[] { " h ", "SHS", "PDP", 'S', OrePrefixes.stick.get(Materials.RedAlloy), 'H',
                            GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 1L, 9), 'D',
                            GT_ModHandler.getModItem(Railcraft.ID, "detector", 1L, 1), 'P',
                            OrePrefixes.plate.get(Materials.Steel) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 1L, 11),
                    bits,
                    new Object[] { "PCP", "CSC", "PCP", 'P', OrePrefixes.plate.get(Materials.Wood), 'S',
                            OrePrefixes.plate.get(Materials.Steel), 'C', new ItemStack(Items.golden_carrot, 1, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 1L, 13),
                    bits,
                    new Object[] { "IOI", "GEG", "IOI", 'G', OrePrefixes.plateDouble.get(Materials.Steel), 'I',
                            OrePrefixes.plate.get(Materials.Diamond), 'E', OrePrefixes.gem.get(Materials.EnderPearl),
                            'O', OrePrefixes.plateDense.get(Materials.Obsidian) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 1L, 14),
                    bits,
                    new Object[] { "PPP", "IhI", "PSP", 'P', OrePrefixes.plank.get(Materials.Wood), 'I',
                            OrePrefixes.stick.get(Materials.AnyIron), 'S', ItemList.IC2_Resin.get(1L, o) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 2L, 14),
                    bits4,
                    new Object[] { "PPP", "IhI", "PSP", 'P', OrePrefixes.plank.get(Materials.Wood), 'I',
                            OrePrefixes.stick.get(Materials.Steel), 'S',
                            GT_ModHandler.getModItem(aTextTConstruct, "slime.gel", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 2L, 14),
                    bits4,
                    new Object[] { "PPP", "IhI", "PSP", 'P', OrePrefixes.plank.get(Materials.Wood), 'I',
                            OrePrefixes.stick.get(Materials.Steel), 'S',
                            GT_ModHandler.getModItem(aTextTConstruct, "slime.gel", 1L, 1) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, aTextMachineAlpha, 4L, 14),
                    bits4,
                    new Object[] { "PPP", "IhI", "PSP", 'P', OrePrefixes.plank.get(Materials.Wood), 'I',
                            OrePrefixes.stick.get(Materials.StainlessSteel), 'S',
                            GT_ModHandler.getModItem(aTextTConstruct, "slime.gel", 1L, 2) });

            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "tool.crowbar", 1L, 0),
                    bits,
                    new Object[] { "hDS", "DSD", "SDf", 'S', OrePrefixes.stick.get(Materials.Iron), 'D', Dyes.dyeRed });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "tool.crowbar.reinforced", 1L, 0),
                    bits,
                    new Object[] { "hDS", "DSD", "SDf", 'S', OrePrefixes.stick.get(Materials.Steel), 'D',
                            Dyes.dyeRed });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "tool.whistle.tuner", 1L, 0),
                    bits | GT_ModHandler.RecipeBits.MIRRORED,
                    new Object[] { "ShS", "SSS", " Sh", 'S', OrePrefixes.stick.get(Materials.Iron) });
            GT_ModHandler.addShapelessCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "part.turbine.blade", 1L, 0),
                    bits,
                    new Object[] { GT_ModHandler.getIC2Item("steelshaft", 1) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "part.turbine.disk", 1L, 0),
                    bits,
                    new Object[] { "SSS", "SBS", "SSS", 'B', OrePrefixes.block.get(Materials.Steel), 'S',
                            GT_ModHandler.getModItem(Railcraft.ID, "part.turbine.blade", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "part.turbine.rotor", 1L, 0),
                    bits,
                    new Object[] { "SSS", " w ", 'S',
                            GT_ModHandler.getModItem(Railcraft.ID, "part.turbine.disk", 1L, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "borehead.iron", 1L, 0),
                    bits,
                    new Object[] { "GPG", "PBP", "GPG", 'B', OrePrefixes.block.get(Materials.Iron), 'G',
                            OrePrefixes.gear.get(Materials.Iron), 'P', OrePrefixes.plate.get(Materials.Iron) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "borehead.steel", 1L, 0),
                    bits,
                    new Object[] { "GPG", "PBP", "GPG", 'B', OrePrefixes.block.get(Materials.Steel), 'G',
                            OrePrefixes.gear.get(Materials.Steel), 'P', OrePrefixes.plate.get(Materials.Steel) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "borehead.diamond", 1L, 0),
                    bits,
                    new Object[] { "DD ", 'D', ItemList.Component_Grinder_Diamond });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "cart.loco.steam.solid", 1L, 0),
                    bits,
                    new Object[] { "TTh", "TTX", "BCZ", 'C', new ItemStack(Items.minecart, 1), 'X',
                            ItemList.Machine_Steel_Boiler, 'Z', new ItemStack(Items.chest_minecart, 1), 'T',
                            GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 4), 'F',
                            GT_ModHandler.getModItem(Railcraft.ID, aTextMachineBeta, 1L, 5), 'B',
                            new ItemStack(Blocks.iron_bars, 1, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "cart.loco.electric", 1L, 0),
                    bits,
                    new Object[] { "LFB", "MCM", "WTW", 'L',
                            GT_ModHandler.getModItem(Computronics.ID, "computronics.colorfulLamp", 1L, 0), 'F',
                            GT_ModHandler.getModItem(Railcraft.ID, "machine.epsilon", 1L, 0), 'B',
                            ItemList.Casing_SolidSteel, 'M', ItemList.Electric_Motor_MV, 'C',
                            GT_ModHandler.getIC2Item("coil", 1L), 'W', ItemList.Component_Minecart_Wheels_Steel, 'T',
                            new ItemStack(Items.minecart, 1, 0) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "cart.bore", 1L, 0),
                    bits,
                    new Object[] { "FCF", "BCB", "hTw", 'C', new ItemStack(Items.minecart, 1), 'T',
                            new ItemStack(Items.chest_minecart, 1), 'F', ItemList.Hull_HP, 'B',
                            ItemList.Machine_Steel_Boiler });

            addRollingMachineRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "post.metal.light.blue", 8L),
                    new Object[] { aTextIron2, " X ", aTextIron2, 'X',
                            OrePrefixes.stick.get(Materials.Aluminium).toString() });
            addRollingMachineRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "post.metal.purple", 64L),
                    new Object[] { aTextIron2, " X ", aTextIron2, 'X',
                            OrePrefixes.stick.get(Materials.Titanium).toString() });
            addRollingMachineRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "post.metal.black", 64L),
                    new Object[] { aTextIron2, " X ", aTextIron2, 'X',
                            OrePrefixes.stick.get(Materials.Tungsten).toString() });
            addRollingMachineRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "post.metal.light.blue", 8L),
                    new Object[] { aTextIron1, aTextIron2, aTextIron1, 'X',
                            OrePrefixes.stick.get(Materials.Aluminium).toString() });
            addRollingMachineRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "post.metal.purple", 64L),
                    new Object[] { aTextIron1, aTextIron2, aTextIron1, 'X',
                            OrePrefixes.stick.get(Materials.Titanium).toString() });
            addRollingMachineRecipe(
                    GT_ModHandler.getModItem(Railcraft.ID, "post.metal.black", 64L),
                    new Object[] { aTextIron1, aTextIron2, aTextIron1, 'X',
                            OrePrefixes.stick.get(Materials.Tungsten).toString() });
        }

        if (ZTones.isModLoaded()) {
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(ZTones.ID, "stoneTile", 8L, 0),
                    bits,
                    new Object[] { "SSS", "STS", "SSS", 'S', new ItemStack(Blocks.stone_slab, 1), 'T',
                            new ItemStack(Blocks.stone, 1) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(ZTones.ID, "auroraBlock", 8L, 0),
                    bits,
                    new Object[] { "GGG", "GDG", "GGG", 'G', new ItemStack(Blocks.glass, 1), 'D',
                            new ItemStack(Items.dye, 1, GT_Values.W) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(ZTones.ID, "minicharcoal", 7L, 0),
                    bits,
                    new Object[] { "T  ", "C  ", "   ", 'T', ToolDictNames.craftingToolSoftHammer, 'C',
                            OrePrefixes.dust.get(Materials.Charcoal) });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(ZTones.ID, "minicoal", 7L, 0),
                    bits,
                    new Object[] { "T  ", "C  ", "   ", 'T', ToolDictNames.craftingToolSoftHammer, 'C',
                            OrePrefixes.dust.get(Materials.Coal) });
        }

        if (Chisel.isModLoaded()) {
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(Chisel.ID, "hempcretesand", 8),
                    GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE,
                    new Object[] { "SSS", "WBW", "GGG", 'G', new ItemStack(Blocks.gravel, 1, 0), 'S',
                            GT_OreDictUnificator.get("sand", 1L), 'W', GT_OreDictUnificator.get("itemWheat", 1L), 'B',
                            ItemList.IC2_Plantball.get(1L) });
        }

        if (TinkerConstruct.isModLoaded()) {
            ItemStack aBlankCast = GT_ModHandler.getModItem(TinkerConstruct.ID, "blankPattern", 1, 1);

            // Add Casting Forms for Molds
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormPlate.getIS(),
                    new Object[] { " HF", " C ", "   ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormCasing.getIS(),
                    new Object[] { " H ", " CF", "   ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormGear.getIS(),
                    new Object[] { " H ", " C ", "  F", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormBottle.getIS(),
                    new Object[] { " H ", " C ", " F ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormIngot.getIS(),
                    new Object[] { " H ", " C ", "F  ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormBall.getIS(),
                    new Object[] { " H ", "FC ", "   ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormBlock.getIS(),
                    new Object[] { "FH ", " C ", "   ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormNuggets.getIS(),
                    new Object[] { "  H", " CF", "   ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormBuns.getIS(),
                    new Object[] { "  H", " C ", "  F", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormBread.getIS(),
                    new Object[] { "  H", " C ", " F ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormBaguette.getIS(),
                    new Object[] { "  H", " C ", "F  ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormCylinder.getIS(),
                    new Object[] { "  H", "FC ", "   ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormAnvil.getIS(),
                    new Object[] { "F H", " C ", "   ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormName.getIS(),
                    new Object[] { " FH", " C ", "   ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormArrowHead.getIS(),
                    new Object[] { "   ", " CH", "  F", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormSmallGear.getIS(),
                    new Object[] { "   ", " CH", " F ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormStick.getIS(),
                    new Object[] { "   ", " CH", "F  ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormBolt.getIS(),
                    new Object[] { "   ", "FCH", "   ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormRound.getIS(),
                    new Object[] { "F  ", " CH", "   ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormScrew.getIS(),
                    new Object[] { " F ", " CH", "   ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormRing.getIS(),
                    new Object[] { "  F", " CH", "   ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormStickLong.getIS(),
                    new Object[] { "   ", " C ", " FH", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormRotor.getIS(),
                    new Object[] { "   ", " C ", "F H", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormTurbineBlade.getIS(),
                    new Object[] { "   ", "FC ", "  H", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormBoots.getIS(),
                    new Object[] { "F  ", " C ", "  H", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormChestplate.getIS(),
                    new Object[] { " F ", " C ", "  H", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormHelmet.getIS(),
                    new Object[] { "  F", " C ", "  H", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormLeggings.getIS(),
                    new Object[] { "   ", " CF", "  H", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MarshmallowFormMold.getIS(),
                    new Object[] { "   ", " C ", "FH ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormPipeTiny.getIS(),
                    new Object[] { "   ", "FC ", " H ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormPipeSmall.getIS(),
                    new Object[] { "F  ", " C ", " H ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormPipeMedium.getIS(),
                    new Object[] { " F ", " C ", " H ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormPipeLarge.getIS(),
                    new Object[] { "  F", " C ", " H ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormPipeHuge.getIS(),
                    new Object[] { "   ", " CF", " H ", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.MoldFormDrillHead.getIS(),
                    new Object[] { "   ", " C ", " HF", 'H', ToolDictNames.craftingToolHardHammer, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });

            // Add Casting Forms for Shapes
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapePlate.getIS(),
                    new Object[] { " WF", " C ", "   ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeRod.getIS(),
                    new Object[] { " W ", " CF", "   ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeBolt.getIS(),
                    new Object[] { " W ", " C ", "  F", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeCell.getIS(),
                    new Object[] { " W ", " C ", " F ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeRing.getIS(),
                    new Object[] { " W ", " C ", "F  ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeIngot.getIS(),
                    new Object[] { " W ", "FC ", "   ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeWire.getIS(),
                    new Object[] { "FW ", " C ", "   ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeCasing.getIS(),
                    new Object[] { "  W", " CF", "   ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeTinyPipe.getIS(),
                    new Object[] { "  W", " C ", "  F", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeSmallPipe.getIS(),
                    new Object[] { "  W", " C ", " F ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeNormalPipe.getIS(),
                    new Object[] { "  W", " C ", "F  ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeLargePipe.getIS(),
                    new Object[] { "  W", "FC ", "   ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeHugePipe.getIS(),
                    new Object[] { "F W", " C ", "   ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeBlock.getIS(),
                    new Object[] { " FW", " C ", "   ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeSwordBlade.getIS(),
                    new Object[] { "   ", " CW", "  F", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapePickaxeHead.getIS(),
                    new Object[] { "   ", " CW", " F ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeShovelHead.getIS(),
                    new Object[] { "   ", " CW", "F  ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeAxeHead.getIS(),
                    new Object[] { "   ", "FCW", "   ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeHoeHead.getIS(),
                    new Object[] { "F  ", " CW", "   ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeHammerHead.getIS(),
                    new Object[] { " F ", " CW", "   ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeFileHead.getIS(),
                    new Object[] { "  F", " CW", "   ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeSawBlade.getIS(),
                    new Object[] { "   ", " C ", " FW", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeGear.getIS(),
                    new Object[] { "   ", " C ", "F W", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeBottle.getIS(),
                    new Object[] { "   ", "FC ", "  W", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeBoat.getIS(),
                    new Object[] { "F  ", " C ", "  W", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeRotor.getIS(),
                    new Object[] { " F ", " C ", "  W", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeTurbineBlade.getIS(),
                    new Object[] { "  F", " C ", "  W", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeSmallGear.getIS(),
                    new Object[] { "   ", " CF", "  W", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });
            GT_ModHandler.addCraftingRecipe(
                    com.dreammaster.item.ItemList.ShapeDrillHead.getIS(),
                    new Object[] { " F ", " C ", " W ", 'W', ToolDictNames.craftingToolWireCutter, 'F',
                            ToolDictNames.craftingToolFile, 'C', aBlankCast });

            // Add Clay Casts
            ItemStack aBlankClayCast = GT_ModHandler.getModItem(TinkerConstruct.ID, "blankPattern", 1, 3);

            GT_ModHandler.addCraftingRecipe(
                    aBlankClayCast,
                    new Object[] { "CC ", "CC ", "   ", 'C', new ItemStack(Items.clay_ball, 1) });

            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 0),
                    new Object[] { "CW ", "   ", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 1),
                    new Object[] { "C W", "   ", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 2),
                    new Object[] { "C  ", "W  ", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 3),
                    new Object[] { "C  ", " W ", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 4),
                    new Object[] { "C  ", "  W", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 5),
                    new Object[] { "C  ", "   ", "W  ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 6),
                    new Object[] { "C  ", "   ", " W ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 7),
                    new Object[] { "C  ", "   ", "  W", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 8),
                    new Object[] { "WC ", "   ", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 9),
                    new Object[] { " CW", "   ", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 10),
                    new Object[] { " C ", "W  ", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 11),
                    new Object[] { " C ", " W ", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 12),
                    new Object[] { " C ", "  W", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 13),
                    new Object[] { " C ", "   ", "W  ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 22),
                    new Object[] { " C ", "   ", " W ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 25),
                    new Object[] { " C ", "   ", "  W", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 26),
                    new Object[] { "W C", "   ", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "clayPattern", 1, 27),
                    new Object[] { " WC", "   ", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
            GT_ModHandler.addCraftingRecipe(
                    GT_ModHandler.getModItem(TinkerConstruct.ID, "Clay Cast", 1, 3),
                    new Object[] { "  C", "W  ", "   ", 'W', ToolDictNames.craftingToolKnife, 'C', aBlankClayCast });
        }

        // Add storage component recipes to pre-apply cache upgrades

        final ItemStack COMPONENT_1 = GT_ModHandler
                .getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 35);
        final ItemStack COMPONENT_4 = GT_ModHandler
                .getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 36);
        final ItemStack COMPONENT_16 = GT_ModHandler
                .getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 37);
        final ItemStack COMPONENT_64 = GT_ModHandler
                .getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 38);
        final ItemStack COMPONENT_256 = GT_ModHandler
                .getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 57);
        final ItemStack COMPONENT_1024 = GT_ModHandler
                .getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 58);
        final ItemStack COMPONENT_4096 = GT_ModHandler
                .getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 59);
        final ItemStack COMPONENT_16384 = GT_ModHandler
                .getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 1L, 60);
        final ItemStack FLUIDCOMPONENT_1 = GT_ModHandler.getModItem(AE2FluidCraft.ID, "fluid_part", 1, 0);
        final ItemStack FLUIDCOMPONENT_4 = GT_ModHandler.getModItem(AE2FluidCraft.ID, "fluid_part", 1, 1);
        final ItemStack FLUIDCOMPONENT_16 = GT_ModHandler.getModItem(AE2FluidCraft.ID, "fluid_part", 1, 2);
        final ItemStack FLUIDCOMPONENT_64 = GT_ModHandler.getModItem(AE2FluidCraft.ID, "fluid_part", 1, 3);
        final ItemStack FLUIDCOMPONENT_256 = GT_ModHandler.getModItem(AE2FluidCraft.ID, "fluid_part", 1, 4);
        final ItemStack FLUIDCOMPONENT_1024 = GT_ModHandler.getModItem(AE2FluidCraft.ID, "fluid_part", 1, 5);
        final ItemStack FLUIDCOMPONENT_4096 = GT_ModHandler.getModItem(AE2FluidCraft.ID, "fluid_part", 1, 6);
        final ItemStack FLUIDCOMPONENT_16384 = GT_ModHandler.getModItem(AE2FluidCraft.ID, "fluid_part", 1, 7);

        ItemStack[] itemComponents = new ItemStack[] { COMPONENT_1, COMPONENT_4, COMPONENT_16, COMPONENT_64,
                COMPONENT_256, COMPONENT_1024, COMPONENT_4096, COMPONENT_16384 };
        ItemStack[] fluidComponents = new ItemStack[] { FLUIDCOMPONENT_1, FLUIDCOMPONENT_4, FLUIDCOMPONENT_16,
                FLUIDCOMPONENT_64, FLUIDCOMPONENT_256, FLUIDCOMPONENT_1024, FLUIDCOMPONENT_4096, FLUIDCOMPONENT_16384 };
        long[] capacities = { 8192, 32768, 131072, 524288, 2097152, 8388608, 33554432, 134217728 };

        for (int i = 0; i <= 7; i++) {
            ItemStack modifiedHatch = ItemList.Hatch_Output_ME.get(1L);
            NBTTagCompound hatchNBT = modifiedHatch.getTagCompound();
            if (hatchNBT != null) {
                hatchNBT.setLong("baseCapacity", capacities[i]);
            } else {
                hatchNBT = new NBTTagCompound();
                hatchNBT.setLong("baseCapacity", capacities[i] * 256);
                modifiedHatch.setTagCompound(hatchNBT);
            }

            ItemStack modifiedBus = ItemList.Hatch_Output_Bus_ME.get(1L);
            NBTTagCompound busNBT = modifiedBus.getTagCompound();
            if (busNBT != null) {
                busNBT.setLong("baseCapacity", capacities[i]);
            } else {
                busNBT = new NBTTagCompound();
                busNBT.setLong("baseCapacity", capacities[i]);
                modifiedBus.setTagCompound(busNBT);
            }

            GT_ModHandler.addShapelessCraftingRecipe(
                    modifiedHatch,
                    new Object[] { ItemList.Hatch_Output_ME.get(1L), fluidComponents[i] });
            GT_ModHandler.addShapelessCraftingRecipe(
                    modifiedBus,
                    new Object[] { ItemList.Hatch_Output_Bus_ME.get(1L), itemComponents[i] });
        }

        // Pre-add planet block to EOH controller
        for (String dimAbbreviation : DimensionHelper.DimNameDisplayed) {
            ItemStack dimDisplay = new ItemStack(ModBlocks.getBlock(dimAbbreviation));
            ItemStack EOHController = com.github.technus.tectech.thing.CustomItemList.Machine_Multi_EyeOfHarmony
                    .get(1L);
            ItemStack NBTController = EOHController.copy();
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("planetBlock", dimAbbreviation);
            NBTController.setTagCompound(nbt);
            GT_ModHandler.addShapelessCraftingRecipe(NBTController, new Object[] { EOHController, dimDisplay });
        }

        // Transform EOH controller back to non-NBT one
        GT_ModHandler.addShapelessCraftingRecipe(
                com.github.technus.tectech.thing.CustomItemList.Machine_Multi_EyeOfHarmony.get(1L),
                new Object[] { com.github.technus.tectech.thing.CustomItemList.Machine_Multi_EyeOfHarmony.get(1L) });
    }

    private Consumer<Recipe> shapelessUnremovableGtRecipes() {
        return craft -> GT_ModHandler.addShapelessCraftingRecipe(
                craft.getResult(),
                GT_ModHandler.RecipeBits.NOT_REMOVABLE,
                craft.getIngredients());
    }
}
