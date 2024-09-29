package net.staro.lego.manager.managers;

import net.staro.lego.Lego;
import net.staro.lego.api.trait.Jsonable;
import net.staro.lego.manager.GenericManager;

//TODO: honestly, this is retarded, but I cba to rewrite config system atm.
public class JsonableManager extends GenericManager<Jsonable> {
    @Override
    public void initialize(Lego lego) {
        register(lego.moduleManager());
        register(lego.commandManager());
        register(lego.friendManager());
    }

}
