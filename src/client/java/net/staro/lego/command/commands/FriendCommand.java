package net.staro.lego.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import lombok.SneakyThrows;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.staro.lego.Lego;
import net.staro.lego.command.LegoCommand;
import net.staro.lego.command.arguments.FriendArgument;
import net.staro.lego.command.arguments.PlayerArgument;

public class FriendCommand extends LegoCommand {
    private final PlayerArgument playerArgument;

    public FriendCommand(Lego lego) {
        super(lego, "friend", "Manage friends");
        playerArgument = new PlayerArgument(lego.mc());
    }

    @Override
    @SneakyThrows
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            lego.chat().send("Please enter the correct sequence: +player <add/del/list/clear> <nickname>");
            return COMPLETED;
        }).then(literal("add").then(argument("player", playerArgument).executes(context -> {
            String nickname;
            nickname = context.getArgument("player", String.class);
            if (!lego.friendManager().getFriends().contains(nickname)) {
                lego.friendManager().addFriend(nickname);
                lego.chat().send(Text.literal("")
                        .append(Text.literal(nickname).formatted(Formatting.BOLD).formatted(Formatting.AQUA))
                        .append(Text.literal(" is a friend now").formatted(Formatting.WHITE)));
            } else {
                lego.chat().send(Text.literal("")
                        .append(Text.literal(nickname).formatted(Formatting.BOLD).formatted(Formatting.AQUA))
                        .append(Text.literal(" is already a friend").formatted(Formatting.WHITE)));
            }
            return COMPLETED;
        }))
        ).then(literal("del").then(argument("player", new FriendArgument(lego)).executes(context -> {
            var nickname = context.getArgument("player", String.class);
            lego.friendManager().removeFriend(nickname);
            lego.chat().send(Text.literal("")
                    .append(Text.literal(nickname).formatted(Formatting.BOLD).formatted(Formatting.RED))
                    .append(Text.literal(" is not a friend anymore").formatted(Formatting.WHITE)));
            return COMPLETED;
        }))
        ).then(literal("list").executes(context -> {
            var friendList = new StringBuilder();
            var friends = lego.friendManager().getFriends();
            friendList.append(String.join(", ", friends));
            lego.chat().send(Text.literal("")
                    .append(Text.literal("Friend list: ").formatted(Formatting.BOLD).formatted(Formatting.AQUA))
                    .append(Text.literal(friendList.toString()).formatted(Formatting.WHITE)));
            return COMPLETED;
        })
        ).then(literal("clear").executes(context -> {
            lego.friendManager().getFriends().clear();
            return COMPLETED;
        })
        );
    }

}
