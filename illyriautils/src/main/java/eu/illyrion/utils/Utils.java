package eu.illyrion.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.illyrion.constants.UtilsConstants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class Utils {

    /**
     * Parses a text string and replaces color placeholders with formatted text
     * components.
     *
     * @param text the text string to parse
     * @return the parsed text with color placeholders replaced by formatted text
     *         components
     */
    public static String parseColor(String text) {
        Pattern pattern = Pattern.compile(UtilsConstants.REGEX);
        Matcher matcher = pattern.matcher(text);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String color = matcher.group(1);
            String content = matcher.group(2);
            matcher.appendReplacement(sb, Component.text(content).color(TextColor.fromHexString(color)).toString());
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}