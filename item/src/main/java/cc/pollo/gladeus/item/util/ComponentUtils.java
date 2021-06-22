package cc.pollo.gladeus.item.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public final class ComponentUtils {

    private ComponentUtils(){}

    public static Component removeItalic(Component component){
        return component.decoration(TextDecoration.ITALIC, false);
    }

}