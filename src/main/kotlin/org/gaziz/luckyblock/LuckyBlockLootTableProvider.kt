package org.gaziz.luckyblock

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
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

            }
        }
    }

    override fun getName(): String {
        return "LuckyBlockRecipeProvider"
    }
}