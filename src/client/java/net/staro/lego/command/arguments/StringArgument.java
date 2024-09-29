package net.staro.lego.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings("ClassCanBeRecord")
@Getter
@RequiredArgsConstructor
public class StringArgument implements ArgumentType<String> {
    private final StringType type;

    public static StringArgument word() {
        return new StringArgument(StringType.SINGLE_WORD);
    }

    public static StringArgument string() {
        return new StringArgument(StringType.QUOTABLE_PHRASE);
    }

    public static StringArgument greedyString() {
        return new StringArgument(StringType.GREEDY_PHRASE);
    }

    @Override
    public String parse(final StringReader reader) throws CommandSyntaxException {
        if (type == StringType.GREEDY_PHRASE) {
            final String text = reader.getRemaining();
            reader.setCursor(reader.getTotalLength());
            return text;
        } else if (type == StringType.SINGLE_WORD) {
            return reader.readUnquotedString();
        } else {
            return reader.readString();
        }
    }

    @Override
    public String toString() {
        return "string()";
    }

    @Override
    public Collection<String> getExamples() {
        return type.getExamples();
    }

    @Getter
    public enum StringType {
        SINGLE_WORD("word", "words_with_underscores"),
        QUOTABLE_PHRASE("\"quoted phrase\"", "word", "\"\""),
        GREEDY_PHRASE("word", "words with spaces", "\"and symbols\""),;

        private final Collection<String> examples;

        StringType(final String... examples) {
            this.examples = Arrays.asList(examples);
        }

    }
}
