package eu.illyrion.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class Utils {
    private static final Pattern PATTERN = Pattern.compile("\\{(#[a-fA-F0-9]{6})\\}([^\\{]*)");

    /**
     * Parses a text string and replaces color placeholders with formatted text
     * components.
     *
     * @param text the text string to parse
     * @return the parsed text with color placeholders replaced by formatted text
     *         components
     */
    public static String parseColor(String text) {
        Matcher matcher = PATTERN.matcher(text);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String color = matcher.group(1);
            String content = matcher.group(2);
            matcher.appendReplacement(sb, Component.text(content).color(TextColor.fromHexString(color)).toString());
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
