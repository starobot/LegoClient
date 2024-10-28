package net.staro.lego.command;

import com.mojang.brigadier.arguments.ArgumentType;
import lombok.RequiredArgsConstructor;
import net.staro.lego.Lego;

@RequiredArgsConstructor
public abstract class LegoArgument<T> implements ArgumentType<T> {
    protected final Lego lego;

}
