package org.gaziz.luckyblock

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EntitySpawnReason
import net.minecraft.world.item.*
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.storage.loot.LootParams
import net.minecraft.world.level.storage.loot.parameters.LootContextParams

class LuckyBlock(properties: Properties): Block(properties) {
    override fun getDrops(blockState: BlockState, builder: LootParams.Builder): List<ItemStack> {
        val items = BuiltInRegistries.ITEM.toList()
        val item = items[(0..BuiltInRegistries.ITEM.size()).random()]
        var stack = ItemStack(item,(1..(item.asItem().defaultMaxStackSize/2)).random())
        val player = builder.getOptionalParameter(LootContextParams.THIS_ENTITY)
        val entityData = stack.get(DataComponents.ENTITY_DATA)
        if(entityData != null) {
            var position = BlockPos(0,0,0)
            stack = ItemStack(item,0)
            val dt = builder.getOptionalParameter(LootContextParams.ORIGIN)
            if(dt != null) {
                position = BlockPos(dt.x.toInt(),dt.y.toInt(),dt.z.toInt())
            } else {
                if(player != null) {
                    position = player.blockPosition()
                }
            }
            entityData.type().spawn(
                builder.level,
                position,
                EntitySpawnReason.SPAWN_ITEM_USE
            )
        }
        return listOf(stack)
    }
}

object ModBlocks {
    private fun register(
        name: String,
        blockFactory: (BlockBehaviour.Properties) -> Block,
        settings: BlockBehaviour.Properties,
    ): Block {
        val blockKey: ResourceKey<Block> = keyOfBlock(name)
        val block: Block = blockFactory(settings.setId(blockKey))
        val itemKey: ResourceKey<Item> = keyOfItem(name)
        val item = BlockItem(block, Item.Properties().setId(itemKey).useBlockDescriptionPrefix())
        Registry.register(BuiltInRegistries.ITEM,itemKey,item)
        return Registry.register(BuiltInRegistries.BLOCK,blockKey,block)
    }
    private fun keyOfBlock(name: String): ResourceKey<Block> {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(AnotherLuckyblockMod.MOD_ID,name))
    }
    private fun keyOfItem(name: String): ResourceKey<Item> {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(AnotherLuckyblockMod.MOD_ID,name))
    }
    val BLOCK = register(
        name = "lucky_block",
        settings = BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(0.25f),
        blockFactory = ::LuckyBlock,
    )
    fun initialize() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
            .register({ creativeTab ->
                creativeTab.accept(BLOCK.asItem())
            })
    }
}