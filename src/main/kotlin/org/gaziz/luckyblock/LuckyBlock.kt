package org.gaziz.luckyblock

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour

object ModBlocks {
    private fun register(
        name: String,
        blockFactory: (BlockBehaviour.Properties) -> Block,
        settings: BlockBehaviour.Properties,
    ): Block {
        val blockKey: ResourceKey<Block> = keyOfBlock(name)
        val block: Block = blockFactory(settings.setId(blockKey))
        val itemKey: ResourceKey<Item> = keyOfItem(name);
        val item = BlockItem(block, Item.Properties().setId(itemKey).useBlockDescriptionPrefix())
        Registry.register(BuiltInRegistries.ITEM,itemKey,item)
        return Registry.register(BuiltInRegistries.BLOCK,blockKey,block);
    }
    private fun keyOfBlock(name: String): ResourceKey<Block> {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(AnotherLuckyblockMod.MOD_ID,name))
    }
    private fun keyOfItem(name: String): ResourceKey<Item> {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AnotherLuckyblockMod.MOD_ID,name))
    }
    val BLOCK = register(
        name = "lucky_block",
        settings = BlockBehaviour.Properties.of().sound(SoundType.AMETHYST),
        blockFactory = ::Block,
    )
    fun initialize() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
            .register({ creativeTab ->
                creativeTab.accept(BLOCK.asItem())
            })
    }
}
