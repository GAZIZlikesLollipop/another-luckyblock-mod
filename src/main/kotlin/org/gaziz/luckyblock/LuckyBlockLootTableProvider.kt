package org.gaziz.luckyblock

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture

class LuckyBlockRecipeProvider(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider>
): FabricRecipeProvider(output,registriesFuture) {
    override fun createRecipeProvider(
        registryLookup: HolderLookup.Provider,
        exporter: RecipeOutput
    ): RecipeProvider {
        return object : RecipeProvider(registryLookup, exporter) {
            override fun buildRecipes() {
                shaped(RecipeCategory.MISC, ModBlocks.BLOCK)
                    .pattern("ggg")
                    .pattern("geg")
                    .pattern("ggg")
                    .define('g',Items.GOLD_INGOT)
                    .define('e',Items.FISHING_ROD)
                    .group("multi_bench")
                    .unlockedBy(getHasName(Items.CRAFTING_TABLE), has(Items.CRAFTING_TABLE))
                    .save(exporter)
            }
        }
    }

    override fun getName(): String {
        return "LuckyBlockRecipeProvider"
    }
}