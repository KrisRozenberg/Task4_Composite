package org.rozenberg.task4.entity;

import java.util.ArrayList;
import java.util.List;

public class TextComposite implements TextComponent{
    private final TextComponentType componentType;
    private final List<TextComponent> components;

    public TextComposite(TextComponentType componentType) {
        this.componentType = componentType;
        this.components = new ArrayList<>();
    }

    @Override
    public TextComponent getTextComponentCopy() {
        TextComponent otherComponent = new TextComposite(this.componentType);
        this.components.forEach(child -> otherComponent.add(child.getTextComponentCopy()));
        return otherComponent;
    }

    @Override
    public TextComponentType getComponentType() {
        return componentType;
    }

    @Override
    public void add(TextComponent component) {
        components.add(component);
    }

    @Override
    public void remove(TextComponent component) {
        components.remove(component);
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(components);
    }

    @Override
    public int getChildrenNumber() {
        return components.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String delimiter = componentType.getDelimiter();
        if (componentType == TextComponentType.TEXT) {
            result.append("\t");
        }
        for (TextComponent component: components) {
            result.append(component.toString()).append(delimiter);
        }
        if (componentType == TextComponentType.TEXT) {
            result.delete(result.length() - 2, result.length() - 1);
        }
        if ((componentType == TextComponentType.PARAGRAPH && !components.isEmpty()) ||
                componentType == TextComponentType.SENTENCE) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString().stripTrailing();
    }
}
