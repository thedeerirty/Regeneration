package com.afg.regeneration;

import com.afg.regeneration.client.animation.PlayerRenderHandler;
import com.afg.regeneration.items.ChameleonArch;
import com.afg.regeneration.sounds.SoundReg;
import com.afg.regeneration.superpower.Timelord;
import com.afg.regeneration.traits.negative.*;
import com.afg.regeneration.traits.positive.*;
import lucraft.mods.lucraftcore.abilities.Ability;
import lucraft.mods.lucraftcore.superpower.Superpower;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.reflect.Field;

/**
 * Created by AFlyingGrayson on 8/7/17
 */

@Mod(modid = Regeneration.MODID, name = "Regeneration", version = Regeneration.VERSION, dependencies = "required-after:lucraftcore@[1.12-1.2.0,)")
@Mod.EventBusSubscriber
public class Regeneration
{
	public static final String MODID = "g-regen", VERSION = "1.0";

	public static final Timelord timelord = new Timelord();

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (event.getSide().equals(Side.CLIENT)){
			MinecraftForge.EVENT_BUS.register(new PlayerRenderHandler());
		}
	}

	@GameRegistry.ObjectHolder(MODID)
	public static class Items
	{
		public static final Item chameleonArch = null;
	}

	@SubscribeEvent
	public static void onRegisterSuperpower(RegistryEvent.Register<Superpower> e)
	{
		e.getRegistry().register(timelord);
	}

	@SubscribeEvent
	public static void onRegisterAbility(RegistryEvent.Register<Ability.AbilityEntry> e)
	{
		//Positive

		e.getRegistry().register(new Ability.AbilityEntry(Bouncy.class, new ResourceLocation(MODID, "bouncy")));
		e.getRegistry().register(new Ability.AbilityEntry(Lucky.class, new ResourceLocation(MODID, "lucky")));
		e.getRegistry().register(new Ability.AbilityEntry(Quick.class, new ResourceLocation(MODID, "quick")));
		e.getRegistry().register(new Ability.AbilityEntry(Spry.class, new ResourceLocation(MODID, "spry")));
		e.getRegistry().register(new Ability.AbilityEntry(Strong.class, new ResourceLocation(MODID, "strong")));
		e.getRegistry().register(new Ability.AbilityEntry(Sturdy.class, new ResourceLocation(MODID, "sturdy")));
		e.getRegistry().register(new Ability.AbilityEntry(ThickSkinned.class, new ResourceLocation(MODID, "thickSkinned")));
		e.getRegistry().register(new Ability.AbilityEntry(Tough.class, new ResourceLocation(MODID, "tough")));

		//Negative

		e.getRegistry().register(new Ability.AbilityEntry(Clumsy.class, new ResourceLocation(MODID, "clumsy")));
		e.getRegistry().register(new Ability.AbilityEntry(Flimsy.class, new ResourceLocation(MODID, "flimsy")));
		e.getRegistry().register(new Ability.AbilityEntry(Frail.class, new ResourceLocation(MODID, "frail")));
		e.getRegistry().register(new Ability.AbilityEntry(Rigid.class, new ResourceLocation(MODID, "rigid")));
		e.getRegistry().register(new Ability.AbilityEntry(Slow.class, new ResourceLocation(MODID, "slow")));
		e.getRegistry().register(new Ability.AbilityEntry(Unhealthy.class, new ResourceLocation(MODID, "unhealthy")));
		e.getRegistry().register(new Ability.AbilityEntry(Unlucky.class, new ResourceLocation(MODID, "unlucky")));
		e.getRegistry().register(new Ability.AbilityEntry(Weak.class, new ResourceLocation(MODID, "weak")));
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) throws Exception
	{
		event.getRegistry().register(new ChameleonArch());
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) throws Exception
	{
		for (Field f : Items.class.getDeclaredFields())
		{
			Item item = (Item) f.get(null);
			ModelResourceLocation loc = new ModelResourceLocation(item.getRegistryName(), "inventory");
			ModelLoader.setCustomModelResourceLocation(item, 0, loc);
		}
	}

	@SubscribeEvent
	public void loot(LootTableLoadEvent e)
	{
		if (e.getName().toString().toLowerCase().contains("minecraft:chests/"))
		{
			LootPool pool = e.getTable().getPool("main");
			LootCondition[] chance = { new RandomChance(0.5F) };
			LootFunction[] count = { new SetCount(chance, new RandomValueRange(1.0F, 1.0F)) };
			LootEntryItem item = new LootEntryItem(Items.chameleonArch, 10, 1, count, chance, "symbol_" + Items.chameleonArch.getUnlocalizedName());
			pool.addEntry(item);
		}
	}
}
