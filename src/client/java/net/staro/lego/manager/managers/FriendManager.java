package net.staro.lego.manager.managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.minecraft.entity.player.PlayerEntity;
import net.staro.lego.Lego;
import net.staro.lego.api.trait.Jsonable;
import net.staro.lego.manager.GenericManager;

import java.util.List;

@Getter
public class FriendManager extends GenericManager<String> implements Jsonable {
    private final String configName = "friends.json";

    @Override
    public void initialize(Lego lego) { }

    @Override
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        getFriends().forEach(friend -> {
            array.add(friend);
            object.add("friends", array);
        });
        return object;
    }

    @Override
    public void fromJson(JsonElement element) {
        element.getAsJsonObject()
                .get("friends")
                .getAsJsonArray()
                .forEach(e ->  getFriends().add(e.getAsString()));
    }

    public boolean isFriend(String name) {
        return getFriends().stream().anyMatch(friend -> friend.equalsIgnoreCase(name));
    }

    public boolean isFriend(PlayerEntity player) {
        return this.isFriend(player.getGameProfile().getName());
    }

    public void addFriend(String name) {
        if (!getFriends().contains(name)) {
            this.getFriends().add(name);
        }
    }

    public void removeFriend(String name) {
        getFriends().removeIf(s -> s.equalsIgnoreCase(name));
    }

    public List<String> getFriends() {
        return items;
    }

}
