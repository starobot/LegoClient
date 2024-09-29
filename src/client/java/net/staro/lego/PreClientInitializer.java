package net.staro.lego;

import lombok.extern.slf4j.Slf4j;
import net.staro.lego.api.event.bus.EventBus;
import net.staro.lego.api.injection.PreClient;

@Slf4j
public class PreClientInitializer implements PreClient {
    @Override
    public void onInitialize() {
        log.info("LEGO_PRE_LAUNCH_START");

        /* This can be instantiated at the client's entry point as well.
        * I just want to show that early initialization is also possible. */
        Lego.EVENT_BUS = new EventBus();

        log.info("LEGO_PRE_LAUNCH_FINISH");
    }

}
