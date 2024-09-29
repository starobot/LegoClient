package net.staro.lego.manager.managers;

import lombok.Getter;
import net.staro.lego.Lego;
import net.staro.lego.manager.GenericManager;
import net.staro.lego.service.Service;
import net.staro.lego.service.impl.ChatMessageService;
import net.staro.lego.service.impl.FirstLaunchService;
import net.staro.lego.service.impl.KeyboardService;
import net.staro.lego.service.impl.ShutdownService;

@Getter
public class ServiceManager extends GenericManager<Service> {
    private KeyboardService keyboardService;
    private FirstLaunchService firstLaunchService;

    @Override
    public void initialize(Lego lego) {
        register(new ChatMessageService(lego));
        firstLaunchService = new FirstLaunchService(lego);
        register(firstLaunchService);
        keyboardService = new KeyboardService(lego);
        register(keyboardService);
        register(new ShutdownService(lego));
    }

}
