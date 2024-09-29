package net.staro.lego;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.MinecraftClient;
import net.staro.lego.api.injection.Client;
import net.staro.lego.command.Chat;
import net.staro.lego.events.client.InitEvent;
import net.staro.lego.manager.managers.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class LegoClient implements Client {
	@Override
	public void onInitializeClient(MinecraftClient mc) {
		log.info("LEGO_LAUNCH_START");

		// _______________ Create a lego instance  _______________ //
		var configManager = new ConfigManager();
		var lego = new Lego(
				mc,
				Lego.EVENT_BUS,
				new Chat(mc),
				new ModuleManager(),
				new CommandManager(),
				new FriendManager(),
				new ServiceManager(),
				new JsonableManager(),
				configManager
		);

		// _____________ Initialize managers stuff  _____________ //
		Lego.MANAGERS.forEach(manager -> manager.initialize(lego));

		// _________________ Load up configs  __________________ //
		configManager.loadEverything(lego);

		Lego.EVENT_BUS.post(new InitEvent(lego));

		log.info("LEGO_LAUNCH_FINISH");
	}

}