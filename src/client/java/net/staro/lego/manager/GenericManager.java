package net.staro.lego.manager;

import lombok.Getter;
import net.staro.api.Manager;
import net.staro.lego.Lego;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class GenericManager<T> implements Manager {
    protected final List<T> items = new ArrayList<>();

    protected GenericManager() {
        Lego.MANAGERS.add(this);
    }

    protected void register(T item) {
        items.add(item);
    }

}