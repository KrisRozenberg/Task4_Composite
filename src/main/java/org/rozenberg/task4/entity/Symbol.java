package org.rozenberg.task4.entity;

import java.util.List;

public class Symbol implements TextComponent{
    private final TextComponentType componentType;
    private final char symbolValue;

    public Symbol(char symbolValue, TextComponentType componentType) {
        this.symbolValue = symbolValue;
        this.componentType = componentType;
    }

    @Override
    public TextComponent getTextComponentCopy() {
        return new Symbol(this.symbolValue, this.componentType);
    }

    @Override
    public TextComponentType getComponentType() {
        return componentType;
    }

    @Override
    public void add(TextComponent component) {
        throw new UnsupportedOperationException("Can't add component to leaf component");
    }

    @Override
    public void remove(TextComponent component) {
        throw new UnsupportedOperationException("Can't add component to leaf component");
    }

    @Override
    public List<TextComponent> getChildren() {
        throw new UnsupportedOperationException("Can't get children of leaf component");
    }

    @Override
    public int getChildrenNumber() {
        throw new UnsupportedOperationException("Can't get children number for leaf component");
    }

    @Override
    public String toString() {
        return String.valueOf(symbolValue);
    }
}
