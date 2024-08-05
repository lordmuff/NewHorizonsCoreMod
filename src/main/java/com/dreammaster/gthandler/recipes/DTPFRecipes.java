package com.dreammaster.gthandler.recipes;

import static gregtech.api.enums.Mods.*;
import static gregtech.api.recipe.RecipeMaps.plasmaForgeRecipes;
import static gregtech.api.util.GT_ModHandler.getModItem;
import static gregtech.api.util.GT_RecipeBuilder.MINUTES;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_RecipeConstants.COIL_HEAT;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.dreammaster.block.BlockList;
import com.dreammaster.gthandler.CustomItemList;
import com.dreammaster.gthandler.DTPFCalculator;
import com.dreammaster.gthandler.GT_CoreModSupport;

import goodgenerator.items.MyMaterial;
import goodgenerator.util.ItemRefer;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gtPlusPlus.core.material.ALLOY;
import gtPlusPlus.core.material.ELEMENT;
import gtPlusPlus.core.material.Particle;
import gtPlusPlus.core.recipe.common.CI;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;

public class DTPFRecipes implements Runnable {

    @Override
    public void run() {
        // Dimensionally transcendent plasma forge recipes.

        {
            // Coils
            int awakened_heat = 10800;
            int infinity_heat = awakened_heat + 900;
            int hypogen_heat = infinity_heat + 900;
            int eternal_heat = hypogen_heat + 900;

            // ------------------------------------------------------------------------------------------
            // ------------------------------------- Plasma Forge ---------------------------------------
            // ------------------------------------------------------------------------------------------

            {
                // Neutronium Smelting.

                long base_quantity = 512L;
                long tier_up_multiplier = 2L;

                DTPFCalculator neutronium = new DTPFCalculator().setBaseParallel(base_quantity)
                        .setCustomEBFinputItem(CustomItemList.RawNeutronium.get(1L))
                        .calculateGenericEBFBasedRecipe(Materials.Neutronium);

                long tier_1_quantity = 144L * base_quantity;

                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTCC.getFluid(neutronium.getCatalystAmount(0)),
                                Materials.Iron.getMolten(tier_1_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(neutronium.getResidueAmount(0)),
                                Materials.Neutronium.getMolten(tier_1_quantity))
                        .duration(neutronium.getDuration(0)).eut(neutronium.getEUt(0))
                        .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);

                long tier_2_quantity = 144L * base_quantity * tier_up_multiplier;

                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTPC.getFluid(neutronium.getCatalystAmount(1)),
                                Materials.Iron.getMolten(tier_2_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(neutronium.getResidueAmount(1)),
                                Materials.Neutronium.getMolten(tier_2_quantity))
                        .duration(neutronium.getDuration(1)).eut(neutronium.getEUt(1))
                        .metadata(COIL_HEAT, infinity_heat).addTo(plasmaForgeRecipes);

                long tier_3_quantity = 144L * base_quantity * tier_up_multiplier * tier_up_multiplier;

                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTRC.getFluid(neutronium.getCatalystAmount(2)),
                                Materials.Iron.getMolten(tier_3_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(neutronium.getResidueAmount(2)),
                                Materials.Neutronium.getMolten(tier_3_quantity))
                        .duration(neutronium.getDuration(2)).eut(neutronium.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_4_quantity = 144L * base_quantity
                        * tier_up_multiplier
                        * tier_up_multiplier
                        * tier_up_multiplier;

                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTEC.getFluid(neutronium.getCatalystAmount(3)),
                                Materials.Iron.getMolten(tier_4_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(neutronium.getResidueAmount(3)),
                                Materials.Neutronium.getMolten(tier_4_quantity))
                        .duration(neutronium.getDuration(3)).eut(neutronium.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);
            }

            {
                // Cosmic Neutronium Smelting.

                long base_quantity = 512L;
                long tier_up_multiplier = 2L;

                DTPFCalculator cosmic_neutronium_bee = new DTPFCalculator().setBaseParallel(base_quantity)
                        .setCatalystDiscount(15).setProcessingTimeDiscount(50)
                        .calculateGenericEBFBasedRecipe(Materials.CosmicNeutronium);

                int base_time = 4200;

                long tier_1_quantity = 144L * base_quantity;
                // Bee Recipes

                GT_Values.RA.stdBuilder()
                        .itemInputs(
                                GT_ModHandler.getModItem(GregTech.ID, "gt.comb", 1L, 117),
                                GT_Utility.getIntegratedCircuit(1))
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTCC.getFluid(cosmic_neutronium_bee.getCatalystAmount(0)),
                                Materials.Copper.getMolten(tier_1_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(cosmic_neutronium_bee.getResidueAmount(0)),
                                Materials.CosmicNeutronium.getMolten(tier_1_quantity))
                        .duration(cosmic_neutronium_bee.getDuration(0)).eut(cosmic_neutronium_bee.getEUt(0))
                        .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);

                long tier_2_bee_quantity = 144L * base_quantity * tier_up_multiplier;

                GT_Values.RA.stdBuilder()
                        .itemInputs(
                                GT_ModHandler.getModItem(GregTech.ID, "gt.comb", 1L, 117),
                                GT_Utility.getIntegratedCircuit(1))
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTPC.getFluid(cosmic_neutronium_bee.getCatalystAmount(1)),
                                Materials.Copper.getMolten(tier_2_bee_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(cosmic_neutronium_bee.getResidueAmount(1)),
                                Materials.CosmicNeutronium.getMolten(tier_2_bee_quantity))
                        .duration(cosmic_neutronium_bee.getDuration(1)).eut(cosmic_neutronium_bee.getEUt(1))
                        .metadata(COIL_HEAT, infinity_heat).addTo(plasmaForgeRecipes);

                long tier_3_bee_quantity = 144L * base_quantity * tier_up_multiplier * tier_up_multiplier;

                GT_Values.RA.stdBuilder()
                        .itemInputs(
                                GT_ModHandler.getModItem(GregTech.ID, "gt.comb", 1L, 117),
                                GT_Utility.getIntegratedCircuit(1))
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTRC.getFluid(cosmic_neutronium_bee.getCatalystAmount(2)),
                                Materials.Copper.getMolten(tier_3_bee_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(cosmic_neutronium_bee.getResidueAmount(2)),
                                Materials.CosmicNeutronium.getMolten(tier_3_bee_quantity))
                        .duration(cosmic_neutronium_bee.getDuration(2)).eut(cosmic_neutronium_bee.getEUt(2))
                        .metadata(COIL_HEAT, hypogen_heat).addTo(plasmaForgeRecipes);

                long tier_4_bee_quantity = 144L * base_quantity
                        * tier_up_multiplier
                        * tier_up_multiplier
                        * tier_up_multiplier;

                GT_Values.RA.stdBuilder()
                        .itemInputs(
                                GT_ModHandler.getModItem(GregTech.ID, "gt.comb", 1L, 117),
                                GT_Utility.getIntegratedCircuit(1))
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTEC.getFluid(cosmic_neutronium_bee.getCatalystAmount(3)),
                                Materials.Copper.getMolten(tier_4_bee_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(cosmic_neutronium_bee.getResidueAmount(3)),
                                Materials.CosmicNeutronium.getMolten(tier_4_bee_quantity))
                        .duration(cosmic_neutronium_bee.getDuration(3)).eut(cosmic_neutronium_bee.getEUt(3))
                        .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

                // normal ones

                DTPFCalculator cosmic_neutronium = new DTPFCalculator().setBaseParallel(base_quantity)
                        .calculateGenericEBFBasedRecipe(Materials.CosmicNeutronium);

                GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(4))
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTCC.getFluid(cosmic_neutronium.getCatalystAmount(0)),
                                Materials.Copper.getMolten(tier_1_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(cosmic_neutronium.getResidueAmount(0)),
                                Materials.CosmicNeutronium.getMolten(tier_1_quantity))
                        .duration(cosmic_neutronium.getDuration(0)).eut(cosmic_neutronium.getEUt(0))
                        .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);

                long tier_2_quantity = 144L * base_quantity * tier_up_multiplier;

                GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(4))
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTPC.getFluid(cosmic_neutronium.getCatalystAmount(1)),
                                Materials.Copper.getMolten(tier_2_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(cosmic_neutronium.getResidueAmount(1)),
                                Materials.CosmicNeutronium.getMolten(tier_2_quantity))
                        .duration(cosmic_neutronium.getDuration(1)).eut(cosmic_neutronium.getEUt(1))
                        .metadata(COIL_HEAT, infinity_heat).addTo(plasmaForgeRecipes);

                long tier_3_quantity = 144L * base_quantity * tier_up_multiplier * tier_up_multiplier;

                GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(4))
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTRC.getFluid(cosmic_neutronium.getCatalystAmount(2)),
                                Materials.Copper.getMolten(tier_3_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(cosmic_neutronium.getResidueAmount(2)),
                                Materials.CosmicNeutronium.getMolten(tier_3_quantity))
                        .duration(cosmic_neutronium.getDuration(2)).eut(cosmic_neutronium.getEUt(2))
                        .metadata(COIL_HEAT, hypogen_heat).addTo(plasmaForgeRecipes);

                long tier_4_quantity = 144L * base_quantity
                        * tier_up_multiplier
                        * tier_up_multiplier
                        * tier_up_multiplier;

                GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(4))
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTEC.getFluid(cosmic_neutronium.getCatalystAmount(3)),
                                Materials.Copper.getMolten(tier_4_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(cosmic_neutronium.getResidueAmount(3)),
                                Materials.CosmicNeutronium.getMolten(tier_4_quantity))
                        .duration(cosmic_neutronium.getDuration(3)).eut(cosmic_neutronium.getEUt(3))
                        .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);
            }

            {
                // Bedrockium.

                long base_quantity = 2048L;
                long tier_up_multiplier = 2L;

                DTPFCalculator bedrockium = new DTPFCalculator().setBaseParallel(base_quantity)
                        .calculateGenericEBFBasedRecipe(Materials.Bedrockium);

                long tier_1_quantity = 144L * base_quantity;

                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTCC.getFluid(bedrockium.getCatalystAmount(0)),
                                Materials.Steel.getMolten(tier_1_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(bedrockium.getResidueAmount(0)),
                                Materials.Bedrockium.getMolten(tier_1_quantity))
                        .duration(bedrockium.getDuration(0)).eut(bedrockium.getEUt(0))
                        .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);

                long tier_2_quantity = tier_1_quantity * tier_up_multiplier;

                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTPC.getFluid(bedrockium.getCatalystAmount(1)),
                                Materials.Steel.getMolten(tier_2_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(bedrockium.getResidueAmount(1)),
                                Materials.Bedrockium.getMolten(tier_2_quantity))
                        .duration(bedrockium.getDuration(1)).eut(bedrockium.getEUt(1))
                        .metadata(COIL_HEAT, infinity_heat).addTo(plasmaForgeRecipes);

                long tier_3_quantity = tier_2_quantity * tier_up_multiplier;

                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTRC.getFluid(bedrockium.getCatalystAmount(2)),
                                Materials.Steel.getMolten(tier_3_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(bedrockium.getResidueAmount(2)),
                                Materials.Bedrockium.getMolten(tier_3_quantity))
                        .duration(bedrockium.getDuration(2)).eut(bedrockium.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_4_quantity = tier_3_quantity * tier_up_multiplier;

                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTEC.getFluid(bedrockium.getCatalystAmount(3)),
                                Materials.Steel.getMolten(tier_4_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(bedrockium.getResidueAmount(3)),
                                Materials.Bedrockium.getMolten(tier_4_quantity))
                        .duration(bedrockium.getDuration(3)).eut(bedrockium.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);
            }

            // Hypogen v1
            GT_Values.RA.stdBuilder()
                    .fluidInputs(
                            new FluidStack(ELEMENT.STANDALONE.HYPOGEN.getFluid(), 144),
                            Materials.Neutronium.getMolten(5760L),
                            new FluidStack(ALLOY.QUANTUM.getFluid(), 5760),
                            Materials.Infinity.getMolten(1440L),
                            MaterialsUEVplus.ExcitedDTPC.getFluid(1000))
                    .fluidOutputs(
                            new FluidStack(ELEMENT.STANDALONE.HYPOGEN.getFluid(), 1584),
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000L / 4))
                    .duration(1 * MINUTES + 15 * SECONDS).eut(800_000_000).metadata(COIL_HEAT, infinity_heat)
                    .addTo(plasmaForgeRecipes);

            // Hypogen v2
            GT_Values.RA.stdBuilder()
                    .fluidInputs(
                            Materials.Neutronium.getMolten(5760L),
                            new FluidStack(ALLOY.QUANTUM.getFluid(), 5760),
                            Materials.Infinity.getMolten(1440L),
                            MaterialsUEVplus.ExcitedDTRC.getFluid(1000))
                    .fluidOutputs(
                            new FluidStack(ELEMENT.STANDALONE.HYPOGEN.getFluid(), 2880),
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000L / 2))
                    .duration(1 * MINUTES + 15 * SECONDS).eut(1_200_000_000).metadata(COIL_HEAT, hypogen_heat)
                    .addTo(plasmaForgeRecipes);

            // Hypogen v3
            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(GTPlusPlus.ID, "MU-metaitem.01", 0L, 32100))
                    .fluidInputs(
                            Materials.Neutronium.getMolten(5760L),
                            new FluidStack(ALLOY.QUANTUM.getFluid(), 5760),
                            Materials.Infinity.getMolten(1440L),
                            MaterialsUEVplus.ExcitedDTEC.getFluid(1000))
                    .fluidOutputs(
                            new FluidStack(ELEMENT.STANDALONE.HYPOGEN.getFluid(), 5760),
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000L))
                    .duration(1 * MINUTES + 15 * SECONDS).eut(1_600_000_000).metadata(COIL_HEAT, eternal_heat)
                    .addTo(plasmaForgeRecipes);

            // Hypogen v4
            GT_Values.RA.stdBuilder().itemInputs(GT_ModHandler.getModItem(GTPlusPlus.ID, "MU-metaitem.01", 0L, 32100))
                    .fluidInputs(
                            Materials.Neutronium.getMolten(5760L * 2),
                            new FluidStack(ALLOY.QUANTUM.getFluid(), 5760),
                            Materials.Infinity.getMolten(1440L),
                            MaterialsUEVplus.ExcitedDTSC.getFluid(1000))
                    .fluidOutputs(
                            new FluidStack(ELEMENT.STANDALONE.HYPOGEN.getFluid(), 5760 * 2),
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(2000L))
                    .duration(1 * MINUTES + 15 * SECONDS).eut(2_000_000_000).metadata(COIL_HEAT, eternal_heat)
                    .addTo(plasmaForgeRecipes);

            {
                // Chromatic Glass

                long base_quantity = 256L;
                long tier_up_multiplier = 2L;

                DTPFCalculator chromatic_glass = new DTPFCalculator().setBaseParallel(base_quantity)
                        .calculateNonEBFRecipe(1_966_080, 6_000);

                long tier_1_quantity = 144L * base_quantity;

                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTCC.getFluid(chromatic_glass.getCatalystAmount(0)),
                                Materials.Glass.getMolten(tier_1_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(chromatic_glass.getResidueAmount(0)),
                                new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_1_quantity))
                        .duration(chromatic_glass.getDuration(0)).eut(chromatic_glass.getEUt(0))
                        .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);

                long tier_2_quantity = tier_1_quantity * tier_up_multiplier;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTPC.getFluid(chromatic_glass.getCatalystAmount(1)),
                                Materials.Glass.getMolten(tier_2_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(chromatic_glass.getResidueAmount(1)),
                                new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_2_quantity))
                        .duration(chromatic_glass.getDuration(1)).eut(chromatic_glass.getEUt(1))
                        .metadata(COIL_HEAT, infinity_heat).addTo(plasmaForgeRecipes);

                long tier_3_quantity = tier_2_quantity * tier_up_multiplier;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTRC.getFluid(chromatic_glass.getCatalystAmount(2)),
                                Materials.Glass.getMolten(tier_3_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(chromatic_glass.getResidueAmount(2)),
                                new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_3_quantity))
                        .duration(chromatic_glass.getDuration(2)).eut(chromatic_glass.getEUt(2))
                        .metadata(COIL_HEAT, hypogen_heat).addTo(plasmaForgeRecipes);

                long tier_4_quantity = tier_3_quantity * tier_up_multiplier;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                MaterialsUEVplus.ExcitedDTEC.getFluid(chromatic_glass.getCatalystAmount(3)),
                                Materials.Glass.getMolten(tier_4_quantity))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(chromatic_glass.getResidueAmount(3)),
                                new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_4_quantity))
                        .duration(chromatic_glass.getDuration(3)).eut(chromatic_glass.getEUt(3))
                        .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);
            }

            // SpaceTime v1
            GT_Values.RA.stdBuilder().itemInputs(ItemList.EnergisedTesseract.get(1))
                    .fluidInputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(10000L),
                            Materials.Infinity.getMolten(2304L),
                            new FluidStack(ELEMENT.STANDALONE.HYPOGEN.getFluid(), 1152))
                    .fluidOutputs(MaterialsUEVplus.SpaceTime.getMolten(144L)).duration(40 * SECONDS).eut(1_000_000_000)
                    .metadata(COIL_HEAT, hypogen_heat).addTo(plasmaForgeRecipes);

            // Spacetime v2
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            ItemList.EnergisedTesseract.get(1),
                            GT_ModHandler.getModItem(GTPlusPlus.ID, "MU-metaitem.01", 0L, 32100))
                    .fluidInputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(5000L),
                            Materials.Infinity.getMolten(1152L))
                    .fluidOutputs(MaterialsUEVplus.SpaceTime.getMolten(576L)).duration(20 * SECONDS).eut(2_000_000_000)
                    .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

            // Raw Tesseract recipe
            // 16 Vertices, 24 faces and 32 edges.
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.stick, Materials.CosmicNeutronium, 8L),
                            GT_ModHandler.getModItem(GTPlusPlus.ID, "itemRodOctiron", 8L),
                            GT_ModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedstick", 8L, 10106),
                            GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Sunnarium, 8L),
                            GT_ModHandler.getModItem(GTPlusPlus.ID, "itemPlateAbyssalAlloy", 24L),
                            GT_ModHandler.getModItem(GTPlusPlus.ID, "itemScrewBotmium", 16L),
                            GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.ZPM, 1L))
                    .itemOutputs(ItemList.Tesseract.get(4)).fluidInputs(MaterialsUEVplus.ExcitedDTRC.getFluid(1000))
                    .fluidOutputs(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000 / 2))
                    .duration(40 * SECONDS).eut(32_000_000).metadata(COIL_HEAT, infinity_heat)
                    .addTo(plasmaForgeRecipes);

            // Raw Tesseract v2
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.stick, Materials.CosmicNeutronium, 12L),
                            GT_ModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedstick", 12L, 10106),
                            GT_OreDictUnificator.get(OrePrefixes.stick, MaterialsUEVplus.TranscendentMetal, 8L),
                            GT_ModHandler.getModItem(GTPlusPlus.ID, "itemPlateBotmium", 24L),
                            GT_ModHandler.getModItem(GTPlusPlus.ID, "itemScrewArcanite", 16L),
                            GT_ModHandler.getModItem(SuperSolarPanels.ID, "enderquantumcomponent", 1L))
                    .itemOutputs(ItemList.Tesseract.get(8)).fluidInputs(MaterialsUEVplus.ExcitedDTEC.getFluid(1000))
                    .fluidOutputs(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000))
                    .duration(40 * SECONDS).eut(128_000_000).metadata(COIL_HEAT, eternal_heat)
                    .addTo(plasmaForgeRecipes);

            // Raw Tesseract v3
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GT_OreDictUnificator.get(OrePrefixes.stick, MaterialsUEVplus.TranscendentMetal, 32L),
                            ALLOY.BLACK_TITANIUM.getPlate(24),
                            ALLOY.ZERON_100.getScrew(16),
                            GT_ModHandler.getModItem(GTPlusPlus.ID, "MU-metaitem.01", 1L, 32105))
                    .itemOutputs(ItemList.Tesseract.get(16)).fluidInputs(MaterialsUEVplus.ExcitedDTSC.getFluid(1000))
                    .fluidOutputs(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(2000))
                    .duration(40 * SECONDS).eut(512_000_000).metadata(COIL_HEAT, eternal_heat)
                    .addTo(plasmaForgeRecipes);

            // Transcendent metal

            long total_EU_per_ingot = 113_246_208_000L + 629_145_600L;
            long recipe_time = 180 * 20;
            DTPFCalculator transcendent_metal = new DTPFCalculator().setBaseParallel(360).setLowestCatalystTier(4)
                    .setScalingFactor(8).setHighestCatalystTier(4)
                    .calculateNonEBFRecipe(total_EU_per_ingot / recipe_time, recipe_time);

            GT_Values.RA.stdBuilder()
                    .itemInputs(ItemList.Tesseract.get(32), GT_OreDictUnificator.get("blockCosmicNeutronium", 40))
                    .itemOutputs(
                            MaterialsUEVplus.TranscendentMetal.getBlocks(40),
                            MaterialsUEVplus.TranscendentMetal.getDust(24))
                    .fluidInputs(
                            ALLOY.TITANSTEEL.getFluidStack(144 * 40 * 9),
                            Materials.CallistoIce.getMolten(144 * 20 * 9),
                            Materials.Ledox.getMolten(144 * 20 * 9),
                            Materials.Tungsten.getMolten(144 * 40 * 9),
                            MaterialsUEVplus.ExcitedDTSC.getFluid(transcendent_metal.getCatalystAmount(4)))
                    .fluidOutputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue
                                    .getFluid(transcendent_metal.getResidueAmount(4)),
                            ELEMENT.STANDALONE.CELESTIAL_TUNGSTEN.getFluidStack(144 * 20 * 9))
                    .duration(transcendent_metal.getDuration(4)).eut(transcendent_metal.getEUt(4))
                    .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

            // Quantum + Astral Titanium

            // Based on quantum abs recipe
            long total_EU_per_quantum_ingot = (long) (9_437_184_000L * 0.9);
            // Laser engraver recipe
            long total_EU_per_astral_dust = 4_718_592_000L;
            // Astral titanium ebf
            long total_EU_per_astral_ingot_ebf = 22_118_400_000L;
            // Astral titanium freezer
            long total_EU_per_astral_ingot_freezer = 1_639_710_720L;
            // 2 astral titanium blocks per 320 quantum ingots
            float astral_ingots_per_quantum_ingot = 18 / 320f;
            // Based on quantum abs including coil discount
            long recipe_time_per_quantum_ingot = (long) (600 * 20 / 10 * 0.9);
            long total_EUt = (long) (total_EU_per_quantum_ingot + astral_ingots_per_quantum_ingot
                    * (total_EU_per_astral_dust + total_EU_per_astral_ingot_ebf + total_EU_per_astral_ingot_freezer))
                    / recipe_time_per_quantum_ingot;
            DTPFCalculator quantum = new DTPFCalculator().setBaseParallel(320).setLowestCatalystTier(3)
                    .setHighestCatalystTier(4).setEUtDivisor(1.12f)
                    .calculateNonEBFRecipe(total_EUt, recipe_time_per_quantum_ingot);
            GT_Values.RA.stdBuilder().itemInputs(
                    CI.getEnergyCore(9, 0),
                    BlockList.Quantinum.getIS(2),
                    // Quantum Anomaly
                    GT_ModHandler.getModItem(GTPlusPlus.ID, "MU-metaitem.01", 2, 32105))
                    .itemOutputs(ELEMENT.STANDALONE.ASTRAL_TITANIUM.getBlock(2))
                    .fluidInputs(
                            ALLOY.BLACK_TITANIUM.getFluidStack(144 * 320 * 7 / 10),
                            Materials.Americium.getMolten(144 * 320 / 10),
                            Materials.Bismuth.getMolten(144 * 320 / 10),
                            ELEMENT.STANDALONE.HYPOGEN.getFluidStack(144 * 320 / 10),
                            Materials.Titanium.getPlasma(144 * 9 * 2 * 2),
                            MaterialsUEVplus.ExcitedDTEC.getFluid(quantum.getCatalystAmount(3)))
                    .fluidOutputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(quantum.getResidueAmount(3)),
                            ALLOY.QUANTUM.getFluidStack(144 * 320))
                    .duration(quantum.getDuration(3)).eut(quantum.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                    .addTo(plasmaForgeRecipes);

            // Quantum + Astral Titanium v2
            GT_Values.RA.stdBuilder().itemInputs(
                    CI.getEnergyCore(9, 0),
                    BlockList.Quantinum.getIS(4),
                    // Quantum Anomaly
                    GT_ModHandler.getModItem(GTPlusPlus.ID, "MU-metaitem.01", 3, 32105))
                    .itemOutputs(ELEMENT.STANDALONE.ASTRAL_TITANIUM.getBlock(4))
                    .fluidInputs(
                            ALLOY.BLACK_TITANIUM.getFluidStack(144 * 640 * 7 / 10),
                            Materials.Americium.getMolten(144 * 640 / 10),
                            Materials.Bismuth.getMolten(144 * 640 / 10),
                            ELEMENT.STANDALONE.HYPOGEN.getFluidStack(144 * 640 / 10),
                            Materials.Titanium.getPlasma(144 * 9 * 4 * 2),
                            MaterialsUEVplus.ExcitedDTSC.getFluid(quantum.getCatalystAmount(4)))
                    .fluidOutputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(quantum.getResidueAmount(4)),
                            ALLOY.QUANTUM.getFluidStack(144 * 640))
                    .duration(quantum.getDuration(4)).eut(quantum.getEUt(4)).metadata(COIL_HEAT, eternal_heat)
                    .addTo(plasmaForgeRecipes);

            // Timepiece
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            getModItem(GTPlusPlus.ID, "gtplusplus.blockcasings.5", 0, 14),
                            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.DarkIron, 1L),
                            Materials.Dilithium.getGems(32),
                            MaterialsUEVplus.Universium.getNanite(1))
                    .itemOutputs(ItemList.Timepiece.get(3))
                    .fluidInputs(
                            MaterialsUEVplus.Time.getMolten(18432L * 8),
                            MaterialsUEVplus.ExcitedDTSC.getFluid(100000L))
                    .fluidOutputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(100000L * 2),
                            MaterialsUEVplus.Space.getMolten(18432L * 8))
                    .duration(80 * SECONDS).eut(TierEU.RECIPE_MAX).metadata(COIL_HEAT, 13500).addTo(plasmaForgeRecipes);

            // Time to Space
            GT_Values.RA.stdBuilder().itemInputs(
                    // Spacetime Continuum Ripper
                    getModItem(GTPlusPlus.ID, "gtplusplus.blockcasings.5", 0, 10),
                    ItemList.EnergisedTesseract.get(2),
                    // Quantum Anomaly
                    GT_ModHandler.getModItem(GTPlusPlus.ID, "MU-metaitem.01", 16, 32105))
                    .itemOutputs(ItemList.Tesseract.get(1))
                    .fluidInputs(
                            MaterialsUEVplus.Time.getMolten(9216L * 64),
                            MaterialsUEVplus.SpaceTime.getMolten(9216L * 16),
                            MaterialsUEVplus.ExcitedDTSC.getFluid(1000L))
                    .fluidOutputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000L * 2),
                            MaterialsUEVplus.Space.getMolten(9216L * 64))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_MAX).metadata(COIL_HEAT, 13500).addTo(plasmaForgeRecipes);

            // Space to Time
            GT_Values.RA.stdBuilder().itemInputs(
                    // Spacetime Continuum Ripper
                    getModItem(GTPlusPlus.ID, "gtplusplus.blockcasings.5", 0, 10),
                    ItemList.EnergisedTesseract.get(2),
                    ItemList.Timepiece.get(16)).itemOutputs(ItemList.Tesseract.get(1))
                    .fluidInputs(
                            MaterialsUEVplus.Space.getMolten(9216L * 64),
                            MaterialsUEVplus.SpaceTime.getMolten(9216L * 16),
                            MaterialsUEVplus.ExcitedDTSC.getFluid(1000L))
                    .fluidOutputs(
                            MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1000L * 2),
                            MaterialsUEVplus.Time.getMolten(9216L * 64))
                    .duration(10 * SECONDS).eut(TierEU.RECIPE_MAX).metadata(COIL_HEAT, 13500).addTo(plasmaForgeRecipes);

            if (Avaritia.isModLoaded()) {
                // Plasma forge infinity Smelting.

                long base_time = 10_000;
                long base_quantity = 64L;

                DTPFCalculator infinity_bee = new DTPFCalculator().setBaseParallel(base_quantity)
                        .setLowestCatalystTier(2).setHighestCatalystTier(4).setCatalystDiscount(15)
                        .setProcessingTimeDiscount(50).setEUtDivisor(2).calculateNonEBFRecipe(32_000_000, base_time);

                // Bee comb catalyst recipes for infinity

                GT_Values.RA.stdBuilder()
                        .itemInputs(
                                GT_ModHandler.getModItem(Avaritia.ID, "Resource", 4L, 5),
                                GT_ModHandler.getModItem(GregTech.ID, "gt.comb", 1L, 119),
                                GT_Utility.getIntegratedCircuit(3))
                        .fluidInputs(MaterialsUEVplus.ExcitedDTSC.getFluid(infinity_bee.getCatalystAmount(4)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(infinity_bee.getResidueAmount(4)),
                                Materials.Infinity.getMolten(256L * 144L))
                        .duration(infinity_bee.getDuration(4)).eut(infinity_bee.getEUt(4))
                        .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

                GT_Values.RA.stdBuilder()
                        .itemInputs(
                                GT_ModHandler.getModItem(Avaritia.ID, "Resource", 2L, 5),
                                GT_ModHandler.getModItem(GregTech.ID, "gt.comb", 1L, 119),
                                GT_Utility.getIntegratedCircuit(3))
                        .fluidInputs(MaterialsUEVplus.ExcitedDTEC.getFluid(infinity_bee.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(infinity_bee.getResidueAmount(3)),
                                Materials.Infinity.getMolten(128L * 144L))
                        .duration(infinity_bee.getDuration(3)).eut(infinity_bee.getEUt(3))
                        .metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

                GT_Values.RA.stdBuilder()
                        .itemInputs(
                                GT_ModHandler.getModItem(Avaritia.ID, "Resource", 1L, 5),
                                GT_ModHandler.getModItem(GregTech.ID, "gt.comb", 1L, 119),
                                GT_Utility.getIntegratedCircuit(2))
                        .fluidInputs(MaterialsUEVplus.ExcitedDTRC.getFluid(infinity_bee.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(infinity_bee.getResidueAmount(2)),
                                Materials.Infinity.getMolten(64L * 144L))
                        .duration(infinity_bee.getDuration(2)).eut(infinity_bee.getEUt(2))
                        .metadata(COIL_HEAT, hypogen_heat).addTo(plasmaForgeRecipes);

                GT_Values.RA.stdBuilder()
                        .itemInputs(
                                GT_ModHandler.getModItem(Avaritia.ID, "Resource", 1L, 5),
                                GT_ModHandler.getModItem(GregTech.ID, "gt.comb", 1L, 119),
                                GT_Utility.getIntegratedCircuit(3))
                        .fluidInputs(MaterialsUEVplus.ExcitedDTRC.getFluid(infinity_bee.getCatalystAmount(2) / 64))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(infinity_bee.getResidueAmount(2) / 64),
                                Materials.Infinity.getMolten(144L))
                        .duration(infinity_bee.getDuration(2) / 128).eut(infinity_bee.getEUt(2) / 64)
                        .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);

                // normal recipes

                DTPFCalculator infinity = new DTPFCalculator().setBaseParallel(base_quantity).setLowestCatalystTier(2)
                        .setHighestCatalystTier(4).setEUtDivisor(2).calculateNonEBFRecipe(32_000_000, base_time);

                GT_Values.RA.stdBuilder()
                        .itemInputs(
                                GT_ModHandler.getModItem(Avaritia.ID, "Resource", 1L, 5),
                                GT_Utility.getIntegratedCircuit(4))
                        .fluidInputs(MaterialsUEVplus.ExcitedDTRC.getFluid(infinity.getCatalystAmount(2) / 64))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(infinity.getResidueAmount(2) / 64),
                                Materials.Infinity.getMolten(144L))
                        .duration(infinity.getDuration(2) / 128).eut(infinity.getEUt(2) / 64)
                        .metadata(COIL_HEAT, awakened_heat).addTo(plasmaForgeRecipes);

                GT_Values.RA.stdBuilder()
                        .itemInputs(
                                GT_ModHandler.getModItem(Avaritia.ID, "Resource", 1L, 5),
                                GT_Utility.getIntegratedCircuit(1))
                        .fluidInputs(MaterialsUEVplus.ExcitedDTRC.getFluid(infinity.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(infinity.getResidueAmount(2)),
                                Materials.Infinity.getMolten(64L * 144L))
                        .duration(infinity.getDuration(2)).eut(infinity.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                GT_Values.RA.stdBuilder()
                        .itemInputs(
                                GT_ModHandler.getModItem(Avaritia.ID, "Resource", 2L, 5),
                                GT_Utility.getIntegratedCircuit(4))
                        .fluidInputs(MaterialsUEVplus.ExcitedDTEC.getFluid(infinity.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(infinity.getResidueAmount(3)),
                                Materials.Infinity.getMolten(128L * 144L))
                        .duration(infinity.getDuration(3)).eut(infinity.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                GT_Values.RA.stdBuilder()
                        .itemInputs(
                                GT_ModHandler.getModItem(Avaritia.ID, "Resource", 4L, 5),
                                GT_Utility.getIntegratedCircuit(4))
                        .fluidInputs(MaterialsUEVplus.ExcitedDTSC.getFluid(infinity.getCatalystAmount(4)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue
                                        .getFluid(infinity.getResidueAmount(4)),
                                Materials.Infinity.getMolten(256L * 144L))
                        .duration(infinity.getDuration(4)).eut(infinity.getEUt(4)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                if (GalacticraftAmunRa.isModLoaded()) {
                    // Dark Matter
                    GT_Values.RA.stdBuilder()
                            .itemInputs(
                                    GT_OreDictUnificator.get(OrePrefixes.block, MaterialsUEVplus.TranscendentMetal, 16),
                                    GT_ModHandler.getModItem(Avaritia.ID, "Resource", 32L, 8),
                                    GT_Utility.copyAmount(0, Particle.getBaseParticle(Particle.HIGGS_BOSON)))
                            .fluidInputs(
                                    MaterialsUEVplus.ExcitedDTEC.getFluid(1797693L),
                                    Materials.CosmicNeutronium.getMolten(16384 * 144),
                                    MyMaterial.tairitsu.getMolten(16384 * 144),
                                    ELEMENT.STANDALONE.CELESTIAL_TUNGSTEN.getFluidStack(4096 * 144))
                            .itemOutputs(GT_ModHandler.getModItem(GalacticraftAmunRa.ID, "tile.baseBlockRock", 1L, 14))
                            .duration(80 * SECONDS)
                            .fluidOutputs(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1797693L))
                            .eut(TierEU.RECIPE_UMV).metadata(COIL_HEAT, hypogen_heat).addTo(plasmaForgeRecipes);
                }
            }

            // Quantum anomaly
            GT_Values.RA.stdBuilder()
                    .itemInputs(
                            GregtechItemList.Laser_Lens_Special.get(1),
                            new ItemStack(Particle.getBaseParticle(Particle.GRAVITON).getItem(), 4),
                            CustomItemList.MysteriousCrystalLens.get(0),
                            ItemRefer.HiC_T5.get(0))
                    .fluidInputs(MaterialsUEVplus.ExcitedDTEC.getFluid(92), Materials.Tritanium.getMolten(144))
                    .itemOutputs(GregtechItemList.Laser_Lens_Special.get(4)).duration(30 * SECONDS)
                    .fluidOutputs(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(92))
                    .eut((int) TierEU.RECIPE_UMV).metadata(COIL_HEAT, eternal_heat).addTo(plasmaForgeRecipes);

            // LuV Superconductor
            {

                long base_quantity = 8190L;
                long tier_up_multiplier = 2L;

                DTPFCalculator baseLuV = new DTPFCalculator().setBaseParallel(base_quantity)
                        .calculateGenericEBFBasedRecipe(
                                Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid);

                long tier_1_quantity = 144L * base_quantity;
                GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(6))
                        .fluidInputs(
                                Materials.Indium.getMolten(4L * tier_1_quantity / 30),
                                Materials.Tin.getMolten(2L * tier_1_quantity / 30),
                                Materials.Barium.getMolten(2L * tier_1_quantity / 30),
                                Materials.Titanium.getMolten(tier_1_quantity / 30),
                                Materials.Copper.getMolten(7L * tier_1_quantity / 30),
                                Materials.Oxygen.getPlasma(14_000L / 32 * tier_1_quantity / 30 / 144),
                                MaterialsUEVplus.ExcitedDTCC.getFluid(baseLuV.getCatalystAmount(0)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseLuV.getResidueAmount(0)),
                                Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid
                                        .getMolten(tier_1_quantity))
                        .duration(baseLuV.getDuration(0)).eut(baseLuV.getEUt(0)).metadata(COIL_HEAT, awakened_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_2_quantity = 144L * base_quantity * tier_up_multiplier;
                GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(6))
                        .fluidInputs(
                                Materials.Indium.getMolten(4L * tier_2_quantity / 30),
                                Materials.Tin.getMolten(2L * tier_2_quantity / 30),
                                Materials.Barium.getMolten(2L * tier_2_quantity / 30),
                                Materials.Titanium.getMolten(tier_2_quantity / 30),
                                Materials.Copper.getMolten(7L * tier_2_quantity / 30),
                                Materials.Oxygen.getPlasma(14_000L / 32 * tier_2_quantity / 30 / 144),
                                MaterialsUEVplus.ExcitedDTPC.getFluid(baseLuV.getCatalystAmount(1)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseLuV.getResidueAmount(1)),
                                Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid
                                        .getMolten(tier_2_quantity))
                        .duration(baseLuV.getDuration(1)).eut(baseLuV.getEUt(1)).metadata(COIL_HEAT, infinity_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_3_quantity = 144L * base_quantity * tier_up_multiplier * tier_up_multiplier;
                GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(6))
                        .fluidInputs(
                                Materials.Indium.getMolten(4L * tier_3_quantity / 30),
                                Materials.Tin.getMolten(2L * tier_3_quantity / 30),
                                Materials.Barium.getMolten(2L * tier_3_quantity / 30),
                                Materials.Titanium.getMolten(tier_3_quantity / 30),
                                Materials.Copper.getMolten(7L * tier_3_quantity / 30),
                                Materials.Oxygen.getPlasma(14_000L / 32 * tier_3_quantity / 30 / 144),
                                MaterialsUEVplus.ExcitedDTRC.getFluid(baseLuV.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseLuV.getResidueAmount(2)),
                                Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid
                                        .getMolten(tier_3_quantity))
                        .duration(baseLuV.getDuration(2)).eut(baseLuV.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_4_quantity = 144L * base_quantity
                        * tier_up_multiplier
                        * tier_up_multiplier
                        * tier_up_multiplier;
                GT_Values.RA.stdBuilder().itemInputs(GT_Utility.getIntegratedCircuit(6))
                        .fluidInputs(
                                Materials.Indium.getMolten(4L * tier_4_quantity / 30),
                                Materials.Tin.getMolten(2L * tier_4_quantity / 30),
                                Materials.Barium.getMolten(2L * tier_4_quantity / 30),
                                Materials.Titanium.getMolten(tier_4_quantity / 30),
                                Materials.Copper.getMolten(7L * tier_4_quantity / 30),
                                Materials.Oxygen.getPlasma(14_000L / 32 * tier_4_quantity / 30 / 144),
                                MaterialsUEVplus.ExcitedDTEC.getFluid(baseLuV.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseLuV.getResidueAmount(3)),
                                Materials.Tetraindiumditindibariumtitaniumheptacoppertetrakaidekaoxid
                                        .getMolten(tier_4_quantity))
                        .duration(baseLuV.getDuration(3)).eut(baseLuV.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);
            }

            // ZPM Superconductor
            {

                long base_quantity = 4095L;
                long tier_up_multiplier = 2L;

                DTPFCalculator baseZPM = new DTPFCalculator().setBaseParallel(base_quantity)
                        .calculateGenericEBFBasedRecipe(Materials.Tetranaquadahdiindiumhexaplatiumosminid);

                long tier_1_quantity = 144L * base_quantity;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Palladium.getMolten(6L * tier_1_quantity / 13),
                                Materials.Indium.getMolten(2L * tier_1_quantity / 13),
                                Materials.Osmium.getMolten(tier_1_quantity / 13),
                                Materials.Naquadah.getMolten(4L * tier_1_quantity / 13),
                                MaterialsUEVplus.ExcitedDTCC.getFluid(baseZPM.getCatalystAmount(0)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseZPM.getResidueAmount(0)),
                                Materials.Tetranaquadahdiindiumhexaplatiumosminid.getMolten(tier_1_quantity))
                        .duration(baseZPM.getDuration(0)).eut(baseZPM.getEUt(0)).metadata(COIL_HEAT, awakened_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_2_quantity = 144L * base_quantity * tier_up_multiplier;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Palladium.getMolten(6L * tier_2_quantity / 13),
                                Materials.Indium.getMolten(2L * tier_2_quantity / 13),
                                Materials.Osmium.getMolten(tier_2_quantity / 13),
                                Materials.Naquadah.getMolten(4L * tier_2_quantity / 13),
                                MaterialsUEVplus.ExcitedDTPC.getFluid(baseZPM.getCatalystAmount(1)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseZPM.getResidueAmount(1)),
                                Materials.Tetranaquadahdiindiumhexaplatiumosminid.getMolten(tier_2_quantity))
                        .duration(baseZPM.getDuration(1)).eut(baseZPM.getEUt(1)).metadata(COIL_HEAT, infinity_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_3_quantity = 144L * base_quantity * tier_up_multiplier * tier_up_multiplier;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Palladium.getMolten(6L * tier_3_quantity / 13),
                                Materials.Indium.getMolten(2L * tier_3_quantity / 13),
                                Materials.Osmium.getMolten(tier_3_quantity / 13),
                                Materials.Naquadah.getMolten(4L * tier_3_quantity / 13),
                                MaterialsUEVplus.ExcitedDTRC.getFluid(baseZPM.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseZPM.getResidueAmount(2)),
                                Materials.Tetranaquadahdiindiumhexaplatiumosminid.getMolten(tier_3_quantity))
                        .duration(baseZPM.getDuration(2)).eut(baseZPM.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_4_quantity = 144L * base_quantity
                        * tier_up_multiplier
                        * tier_up_multiplier
                        * tier_up_multiplier;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Palladium.getMolten(6L * tier_4_quantity / 13),
                                Materials.Indium.getMolten(2L * tier_4_quantity / 13),
                                Materials.Osmium.getMolten(tier_4_quantity / 13),
                                Materials.Naquadah.getMolten(4L * tier_4_quantity / 13),
                                MaterialsUEVplus.ExcitedDTEC.getFluid(baseZPM.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseZPM.getResidueAmount(3)),
                                Materials.Tetranaquadahdiindiumhexaplatiumosminid.getMolten(tier_4_quantity))
                        .duration(baseZPM.getDuration(3)).eut(baseZPM.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);
            }

            // UV Superconductor.

            {
                long base_quantity = 2043L;
                long tier_up_multiplier = 2L;

                DTPFCalculator baseUV = new DTPFCalculator().setBaseParallel(base_quantity)
                        .calculateGenericEBFBasedRecipe(Materials.Longasssuperconductornameforuvwire);

                long tier_1_quantity = 144L * base_quantity;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Samarium.getMolten(tier_1_quantity / 9),
                                Materials.Europium.getMolten(tier_1_quantity / 9),
                                Materials.Osmiridium.getMolten(3L * tier_1_quantity / 9),
                                Materials.Naquadria.getMolten(4L * tier_1_quantity / 9),
                                MaterialsUEVplus.ExcitedDTCC.getFluid(baseUV.getCatalystAmount(0)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUV.getResidueAmount(0)),
                                Materials.Longasssuperconductornameforuvwire.getMolten(tier_1_quantity))
                        .duration(baseUV.getDuration(0)).eut(baseUV.getEUt(0)).metadata(COIL_HEAT, awakened_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_2_quantity = 144L * base_quantity * tier_up_multiplier;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Samarium.getMolten(tier_2_quantity / 9),
                                Materials.Europium.getMolten(tier_2_quantity / 9),
                                Materials.Osmiridium.getMolten(3L * tier_2_quantity / 9),
                                Materials.Naquadria.getMolten(4L * tier_2_quantity / 9),
                                MaterialsUEVplus.ExcitedDTPC.getFluid(baseUV.getCatalystAmount(1)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUV.getResidueAmount(1)),
                                Materials.Longasssuperconductornameforuvwire.getMolten(tier_2_quantity))
                        .duration(baseUV.getDuration(1)).eut(baseUV.getEUt(1)).metadata(COIL_HEAT, infinity_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_3_quantity = 144L * base_quantity * tier_up_multiplier * tier_up_multiplier;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Samarium.getMolten(tier_3_quantity / 9),
                                Materials.Europium.getMolten(tier_3_quantity / 9),
                                Materials.Osmiridium.getMolten(3L * tier_3_quantity / 9),
                                Materials.Naquadria.getMolten(4L * tier_3_quantity / 9),
                                MaterialsUEVplus.ExcitedDTRC.getFluid(baseUV.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUV.getResidueAmount(2)),
                                Materials.Longasssuperconductornameforuvwire.getMolten(tier_3_quantity))
                        .duration(baseUV.getDuration(2)).eut(baseUV.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_4_quantity = 144L * base_quantity
                        * tier_up_multiplier
                        * tier_up_multiplier
                        * tier_up_multiplier;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Samarium.getMolten(tier_4_quantity / 9),
                                Materials.Europium.getMolten(tier_4_quantity / 9),
                                Materials.Osmiridium.getMolten(3L * tier_4_quantity / 9),
                                Materials.Naquadria.getMolten(4L * tier_4_quantity / 9),
                                MaterialsUEVplus.ExcitedDTEC.getFluid(baseUV.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUV.getResidueAmount(3)),
                                Materials.Longasssuperconductornameforuvwire.getMolten(tier_4_quantity))
                        .duration(baseUV.getDuration(3)).eut(baseUV.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);
            }

            // UHV Superconductor.

            {
                long base_quantity = 1008L;
                long tier_up_multiplier = 2L;

                DTPFCalculator baseUHV = new DTPFCalculator().setBaseParallel(base_quantity).setLowestCatalystTier(1)
                        .setHighestCatalystTier(4)
                        .calculateGenericEBFBasedRecipe(Materials.Longasssuperconductornameforuhvwire);

                long tier_1_quantity = 144L * base_quantity;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Draconium.getMolten(6L * tier_1_quantity / 24),
                                Materials.Americium.getMolten(6L * tier_1_quantity / 24),
                                Materials.CosmicNeutronium.getMolten(7L * tier_1_quantity / 24),
                                Materials.Tritanium.getMolten(5L * tier_1_quantity / 24),
                                MaterialsUEVplus.ExcitedDTPC.getFluid(baseUHV.getCatalystAmount(1)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUHV.getResidueAmount(1)),
                                Materials.Longasssuperconductornameforuhvwire.getMolten(tier_1_quantity))
                        .duration(baseUHV.getDuration(1)).eut(baseUHV.getEUt(1)).metadata(COIL_HEAT, infinity_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_2_quantity = 144L * base_quantity * tier_up_multiplier;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Draconium.getMolten(6L * tier_2_quantity / 24),
                                Materials.Americium.getMolten(6L * tier_2_quantity / 24),
                                Materials.CosmicNeutronium.getMolten(7L * tier_2_quantity / 24),
                                Materials.Tritanium.getMolten(5L * tier_2_quantity / 24),
                                MaterialsUEVplus.ExcitedDTRC.getFluid(baseUHV.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUHV.getResidueAmount(2)),
                                Materials.Longasssuperconductornameforuhvwire.getMolten(tier_2_quantity))
                        .duration(baseUHV.getDuration(2)).eut(baseUHV.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_3_quantity = 144L * base_quantity * tier_up_multiplier * tier_up_multiplier;
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Draconium.getMolten(6L * tier_3_quantity / 24),
                                Materials.Americium.getMolten(6L * tier_3_quantity / 24),
                                Materials.CosmicNeutronium.getMolten(7L * tier_3_quantity / 24),
                                Materials.Tritanium.getMolten(5L * tier_3_quantity / 24),
                                MaterialsUEVplus.ExcitedDTEC.getFluid(baseUHV.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUHV.getResidueAmount(3)),
                                Materials.Longasssuperconductornameforuhvwire.getMolten(tier_3_quantity))
                        .duration(baseUHV.getDuration(3)).eut(baseUHV.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_4_quantity = 144L * base_quantity * (long) Math.pow(tier_up_multiplier, 3);
                GT_Values.RA.stdBuilder()
                        .fluidInputs(
                                Materials.Draconium.getMolten(6L * tier_4_quantity / 24),
                                Materials.Americium.getMolten(6L * tier_4_quantity / 24),
                                Materials.CosmicNeutronium.getMolten(7L * tier_4_quantity / 24),
                                Materials.Tritanium.getMolten(5L * tier_4_quantity / 24),
                                MaterialsUEVplus.ExcitedDTSC.getFluid(baseUHV.getCatalystAmount(4)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUHV.getResidueAmount(4)),
                                Materials.Longasssuperconductornameforuhvwire.getMolten(tier_4_quantity))
                        .duration(baseUHV.getDuration(4)).eut(baseUHV.getEUt(4)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);
            }

            // UEV Superconductors.

            if (GTPlusPlus.isModLoaded()) {

                long base_quantity = 504L;
                long tier_up_multiplier = 2L;

                DTPFCalculator baseUEV = new DTPFCalculator().setBaseParallel(base_quantity).setLowestCatalystTier(2)
                        .setHighestCatalystTier(4).calculateGenericEBFBasedRecipe(Materials.SuperconductorUEVBase);

                long tier_1_quantity = 144L * base_quantity;

                GT_Values.RA.stdBuilder().fluidInputs(
                        new FluidStack(FluidRegistry.getFluid("molten.advancednitinol"), (int) tier_1_quantity / 12),
                        new FluidStack(FluidRegistry.getFluid("molten.celestialtungsten"), (int) tier_1_quantity / 12),
                        Materials.DraconiumAwakened.getMolten(5L * tier_1_quantity / 12),
                        Materials.Infinity.getMolten(5L * tier_1_quantity / 12),
                        Materials.Iron.getPlasma(tier_1_quantity / 12),
                        MaterialsUEVplus.ExcitedDTRC.getFluid(baseUEV.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUEV.getResidueAmount(2)),
                                Materials.SuperconductorUEVBase.getMolten(tier_1_quantity))
                        .duration(baseUEV.getDuration(2)).eut(baseUEV.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_2_quantity = 144L * base_quantity * tier_up_multiplier;

                GT_Values.RA.stdBuilder().fluidInputs(
                        new FluidStack(FluidRegistry.getFluid("molten.advancednitinol"), (int) tier_2_quantity / 12),
                        new FluidStack(FluidRegistry.getFluid("molten.celestialtungsten"), (int) tier_2_quantity / 12),
                        Materials.DraconiumAwakened.getMolten(5L * tier_2_quantity / 12),
                        Materials.Infinity.getMolten(5L * tier_2_quantity / 12),
                        Materials.Iron.getPlasma(tier_2_quantity / 12),
                        MaterialsUEVplus.ExcitedDTEC.getFluid(baseUEV.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUEV.getResidueAmount(3)),
                                Materials.SuperconductorUEVBase.getMolten(tier_2_quantity))
                        .duration(baseUEV.getDuration(3)).eut(baseUEV.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_3_quantity = 144L * base_quantity * tier_up_multiplier * tier_up_multiplier;

                GT_Values.RA.stdBuilder().fluidInputs(
                        new FluidStack(FluidRegistry.getFluid("molten.advancednitinol"), (int) tier_3_quantity / 12),
                        new FluidStack(FluidRegistry.getFluid("molten.celestialtungsten"), (int) tier_3_quantity / 12),
                        Materials.DraconiumAwakened.getMolten(5L * tier_3_quantity / 12),
                        Materials.Infinity.getMolten(5L * tier_3_quantity / 12),
                        Materials.Iron.getPlasma(tier_3_quantity / 12),
                        MaterialsUEVplus.ExcitedDTSC.getFluid(baseUEV.getCatalystAmount(4)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUEV.getResidueAmount(4)),
                                Materials.SuperconductorUEVBase.getMolten(tier_3_quantity))
                        .duration(baseUEV.getDuration(4)).eut(baseUEV.getEUt(4)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);
            }

            // UIV Superconductors.
            {

                long base_quantity = 250L;
                long tier_up_multiplier = 2L;

                DTPFCalculator baseUIV = new DTPFCalculator().setBaseParallel(base_quantity).setLowestCatalystTier(2)
                        .setHighestCatalystTier(4).calculateGenericEBFBasedRecipe(Materials.SuperconductorUIVBase);

                long tier_1_quantity = 144L * base_quantity;

                GT_Values.RA.stdBuilder().fluidInputs(
                        GT_CoreModSupport.RadoxPolymer.getMolten(4L * tier_1_quantity / 25),
                        MaterialsUEVplus.TranscendentMetal.getMolten(10L * tier_1_quantity / 25),
                        new FluidStack(FluidRegistry.getFluid("molten.rhugnor"), (int) tier_1_quantity * 6 / 25),
                        new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_1_quantity * 5 / 25),
                        Materials.Bismuth.getPlasma(tier_1_quantity / 25),
                        MaterialsUEVplus.ExcitedDTRC.getFluid(baseUIV.getCatalystAmount(2)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUIV.getResidueAmount(2)),
                                Materials.SuperconductorUIVBase.getMolten(tier_1_quantity))
                        .duration(baseUIV.getDuration(2)).eut(baseUIV.getEUt(2)).metadata(COIL_HEAT, hypogen_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_2_quantity = 144L * base_quantity * tier_up_multiplier;

                GT_Values.RA.stdBuilder().fluidInputs(
                        GT_CoreModSupport.RadoxPolymer.getMolten(4L * tier_2_quantity / 25),
                        MaterialsUEVplus.TranscendentMetal.getMolten(10L * tier_2_quantity / 25),
                        new FluidStack(FluidRegistry.getFluid("molten.rhugnor"), (int) tier_2_quantity * 6 / 25),
                        new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_2_quantity * 5 / 25),
                        Materials.Bismuth.getPlasma(tier_2_quantity / 25),
                        MaterialsUEVplus.ExcitedDTEC.getFluid(baseUIV.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUIV.getResidueAmount(3)),
                                Materials.SuperconductorUIVBase.getMolten(tier_2_quantity))
                        .duration(baseUIV.getDuration(3)).eut(baseUIV.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_3_quantity = 144L * base_quantity * tier_up_multiplier * tier_up_multiplier;

                GT_Values.RA.stdBuilder().fluidInputs(
                        GT_CoreModSupport.RadoxPolymer.getMolten(4L * tier_3_quantity / 25),
                        MaterialsUEVplus.TranscendentMetal.getMolten(10L * tier_3_quantity / 25),
                        new FluidStack(FluidRegistry.getFluid("molten.rhugnor"), (int) tier_3_quantity * 6 / 25),
                        new FluidStack(FluidRegistry.getFluid("molten.chromaticglass"), (int) tier_3_quantity * 5 / 25),
                        Materials.Bismuth.getPlasma(tier_3_quantity / 25),
                        MaterialsUEVplus.ExcitedDTSC.getFluid(baseUIV.getCatalystAmount(4)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUIV.getResidueAmount(4)),
                                Materials.SuperconductorUIVBase.getMolten(tier_3_quantity))
                        .duration(baseUIV.getDuration(4)).eut(baseUIV.getEUt(4)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);
            }

            // UMV Superconductors.
            {

                long base_quantity = 243L;
                long tier_up_multiplier = 2L;

                DTPFCalculator baseUMV = new DTPFCalculator().setBaseParallel(base_quantity).setLowestCatalystTier(3)
                        .setHighestCatalystTier(4).calculateGenericEBFBasedRecipe(Materials.SuperconductorUMVBase);

                long tier_1_quantity = 144L * base_quantity;
                GT_Values.RA.stdBuilder().fluidInputs(
                        MaterialsUEVplus.SpaceTime.getMolten(6L * tier_1_quantity / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.orundum"), (int) tier_1_quantity * 3 / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.hypogen"), (int) tier_1_quantity * 11 / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.titansteel"), (int) tier_1_quantity * 5 / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.dragonblood"), (int) tier_1_quantity * 2 / 27),
                        Materials.Oxygen.getPlasma(tier_1_quantity / 27),
                        MaterialsUEVplus.ExcitedDTEC.getFluid(baseUMV.getCatalystAmount(3)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUMV.getResidueAmount(3)),
                                Materials.SuperconductorUMVBase.getMolten(tier_1_quantity))
                        .duration(baseUMV.getDuration(3)).eut(baseUMV.getEUt(3)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);

                long tier_2_quantity = 144L * base_quantity * tier_up_multiplier;

                GT_Values.RA.stdBuilder().fluidInputs(
                        MaterialsUEVplus.SpaceTime.getMolten(6L * tier_2_quantity / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.orundum"), (int) tier_2_quantity * 3 / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.hypogen"), (int) tier_2_quantity * 11 / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.titansteel"), (int) tier_2_quantity * 5 / 27),
                        new FluidStack(FluidRegistry.getFluid("molten.dragonblood"), (int) tier_2_quantity * 2 / 27),
                        Materials.Oxygen.getPlasma(tier_2_quantity / 27),
                        MaterialsUEVplus.ExcitedDTSC.getFluid(baseUMV.getCatalystAmount(4)))
                        .fluidOutputs(
                                MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(baseUMV.getResidueAmount(4)),
                                Materials.SuperconductorUMVBase.getMolten(tier_2_quantity))
                        .duration(baseUMV.getDuration(4)).eut(baseUMV.getEUt(4)).metadata(COIL_HEAT, eternal_heat)
                        .addTo(plasmaForgeRecipes);
            }
        }
    }
}
