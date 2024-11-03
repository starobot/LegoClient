package net.staro.lego.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.util.crash.CrashReport;
import net.staro.lego.Lego;
import net.staro.lego.LegoClient;
import net.staro.lego.events.client.ShutdownEvent;
import net.staro.lego.events.tick.GameloopEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {
	/**
	 * Client's custom entry point.
	 * Not using fabric's default client initialization due to several reasons which im too lazy to explain.
	 * This is better, trust me bro!
	 */
	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setOverlay(Lnet/minecraft/client/gui/screen/Overlay;)V"))
	public void initializeClientHook(RunArgs args, CallbackInfo ci) {
		new LegoClient().onInitializeClient(MinecraftClient.class.cast(this));
	}

	@ModifyVariable(method = "render", ordinal = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;runTasks()V", shift = At.Shift.AFTER))
	private int runTickHook(int i) {
		GameloopEvent.INSTANCE.setTicks(i);
		Lego.EVENT_BUS.post(GameloopEvent.INSTANCE);
		return GameloopEvent.INSTANCE.getTicks();
	}

	@Inject(method = "stop", at = @At("HEAD"))
	public void stop(CallbackInfo info) {
		Lego.EVENT_BUS.post(new ShutdownEvent());
	}

	@Inject(method = "printCrashReport(Lnet/minecraft/client/MinecraftClient;Ljava/io/File;Lnet/minecraft/util/crash/CrashReport;)V", at = @At(value = "HEAD"))
	private static void printCrashReportHook(MinecraftClient client, File runDirectory, CrashReport crashReport, CallbackInfo ci) {
		Lego.EVENT_BUS.post(new ShutdownEvent());
	}

	@Inject(method = "run", at = @At("RETURN"))
	private void runHook(CallbackInfo ci) {
		Lego.EVENT_BUS.post(new ShutdownEvent());
	}

}