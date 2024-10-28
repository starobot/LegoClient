package net.staro.api.trait;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

// Why? Because why not.
public interface Completable
{
    int COMPLETED = SINGLE_SUCCESS; // this is equal to 1

}
