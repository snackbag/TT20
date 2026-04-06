package net.snackbag.tt20.mixin;

import net.minecraft.server.MinecraftServer;
//? if >=26.1 {
/*import net.minecraft.server.level.ServerPlayer;
*///?} else {
import net.minecraft.server.network.ServerPlayerEntity;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//? if >=26.1 {
/*@Mixin(ServerPlayer.class)
*///?} else {
@Mixin(ServerPlayerEntity.class)
 //?}
public interface ServerPlayerEntityMixin {
    @Accessor("server")
    MinecraftServer getServer();
}
