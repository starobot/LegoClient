package net.staro.lego.api.trait;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

// Why? Because why not.
// Btw, it literally is equal to 1.
public interface Completable {
    int COMPLETED = SINGLE_SUCCESS;

}
