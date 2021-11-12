package org.rozenberg.task4.entity;

import java.util.List;

public interface TextComponent {
    TextComponent getTextComponentCopy();
    TextComponentType getComponentType();
    void add(TextComponent component);
    void remove(TextComponent component);
    List<TextComponent> getChildren();
    int getChildrenNumber();
    String toString();
}
