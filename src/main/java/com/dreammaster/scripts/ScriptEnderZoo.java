package com.dreammaster.scripts;

import gregtech.api.enums.GT_Values;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

import static gregtech.api.util.GT_ModHandler.getModItem;

public class ScriptEnderZoo implements IScriptLoader{

    public ScriptEnderZoo() {
        scriptName.append("EnderZoo");
        dependencies.addAll(Arrays.asList("EnderZoo"));
    }
    @Override
    public void loadRecipes() {
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
                        getModItem("minecraft", "tnt", 1),
                        getModItem("EnderZoo", "confusingDust", 4)},
                GT_Values.NF,
                getModItem("EnderZoo", "blockConfusingCharge", 1),
                400, 16);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
                        getModItem("minecraft", "tnt", 1),
                        getModItem("EnderZoo", "enderFragment", 4)},
                GT_Values.NF,
                getModItem("EnderZoo", "blockEnderCharge", 1),
                400, 16);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
                        getModItem("EnderZoo", "blockConfusingCharge", 1),
                        getModItem("EnderZoo", "blockEnderCharge", 1)},
                GT_Values.NF,
                getModItem("EnderZoo", "blockConcussionCharge", 2),
                400, 30);
    }
}