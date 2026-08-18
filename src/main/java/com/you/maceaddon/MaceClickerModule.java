package com.you.maceaddon;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.settings.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class MaceClickerModule extends Module {
    private final Setting<Double> range = settings.add(new DoubleSetting.Builder()
        .name("range")
        .description("攻击范围")
        .defaultValue(4.5)
        .min(1).max(8).build()
    );

    private final Setting<Integer> delay = settings.add(new IntSetting.Builder()
        .name("delay-ms")
        .description("攻击间隔")
        .defaultValue(50)
        .min(0).max(200).build()
    );

    private long lastAttackTime = 0;

    public MaceClickerModule() {
        super(Category.Combat, "mace-clicker", "自动锁定并攻击范围内的目标");
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (!mc.player.getMainHandStack().isOf(Items.MACE)) return;

        LivingEntity target = findTarget();
        if (target == null) return;

        long now = System.currentTimeMillis();
        if (now - lastAttackTime < delay.get()) return;

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
        lastAttackTime = now;
    }

    private LivingEntity findTarget() {
        LivingEntity closest = null;
        double closestDist = Double.MAX_VALUE;

        for (LivingEntity e : mc.world.getEntitiesByClass(LivingEntity.class, 
                mc.player.getBoundingBox().expand(range.get()),
                e -> e != mc.player && e.isAlive() && !e.isDead())) {
            double dist = mc.player.distanceTo(e);
            if (dist < closestDist) {
                closestDist = dist;
                closest = e;
            }
        }
        return closest;
    }
}
