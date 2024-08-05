package com.dreammaster.chisel;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.cricketcraft.chisel.api.carving.CarvingUtils;

public class ChiselHelper {

    public static void addVariationFromStack(String groupName, ItemStack stack) {
        CarvingUtils.getChiselRegistry()
                .addVariation(groupName, Block.getBlockFromItem(stack.getItem()), stack.getItemDamage(), 99);
    }

    public static void registerOredict(String groupName, String oreName) {
        CarvingUtils.getChiselRegistry().registerOre(groupName, oreName);
    }

    public static void removeVariationStack(ItemStack stack) {
        CarvingUtils.getChiselRegistry()
                .removeVariation(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage());
    }

    public static void addGroup(String groupName) {
        CarvingUtils.getChiselRegistry().addGroup(new CarvingUtils.SimpleCarvingGroup(groupName));
    }
}
