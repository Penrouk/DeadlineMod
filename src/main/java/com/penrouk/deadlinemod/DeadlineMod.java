package com.penrouk.deadlinemod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(DeadlineMod.MOD_ID)
public class DeadlineMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "deadline_mod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public DeadlineMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onMobSpawn(FinalizeSpawnEvent event) {

        // Used for testing. That way we can easily see log statements
        if(event.getSpawnType() != MobSpawnType.SPAWN_EGG) {
            return;
        }

        Mob mob = event.getEntity();
        AttributeMap attributeMap = mob.getAttributes();

        int spawnDistance = (int)(Math.abs(event.getX()) + Math.abs(event.getZ()));

        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(DeadlineMod.MOD_ID, "mob_scaling");

        // Increase by 10% per 200 blocks starting at 2000 blocks away
        // 2000 blocks can be either 2000,0 : 1750,250 : 1000:1000 etc
        // Basic distance calculation to be efficient
        System.out.println("Spawn Distance: " + spawnDistance);
        double modifierAmount = ((double) Math.max(spawnDistance - 2000, 0) / 200) * 0.1;
        System.out.println("Modifer Amount: " + modifierAmount);

        if (modifierAmount >= 0) {
            AttributeModifier modifier = new AttributeModifier(id, mob.getMaxHealth() * modifierAmount, AttributeModifier.Operation.ADD_VALUE);
            AttributeInstance maxHealth = attributeMap.getInstance(Attributes.MAX_HEALTH);

            if (maxHealth != null) {
                maxHealth.addPermanentModifier(modifier);
                mob.setHealth(event.getEntity().getMaxHealth());
            }
        }
    }

    @SubscribeEvent
    public void onXpDrop(LivingExperienceDropEvent event) {
        int originalXp = event.getDroppedExperience();
        event.setDroppedExperience(originalXp * 50);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
