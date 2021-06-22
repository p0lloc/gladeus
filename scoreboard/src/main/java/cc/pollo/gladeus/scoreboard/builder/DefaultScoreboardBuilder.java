package cc.pollo.gladeus.scoreboard.builder;

import cc.pollo.gladeus.scoreboard.ScoreboardEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Builds a scoreboard
 */
public class DefaultScoreboardBuilder implements ScoreboardBuilder {

    private final Map<String, ScoreboardEntry> entries;
    private final String translatablePrefix;

    private final FormatFunction formatFunction;

    protected static final String DEFAULT_TRANSLATABLE_PREFIX = null;
    protected static final FormatFunction DEFAULT_FORMAT =
            (key, value) -> Component.text("").append(key)
                    .append(Component.text(": ")).append(value);

    DefaultScoreboardBuilder(FormatFunction formatFunction, String translatablePrefix){
        this.entries            = new LinkedHashMap<>();
        this.formatFunction     = formatFunction;
        this.translatablePrefix = translatablePrefix;
    }

    @Override
    public ScoreboardBuilder id(String id, Component component){
        return id(id, new ScoreboardEntry(component));
    }

    @Override
    public ScoreboardBuilder id(String id, ScoreboardEntry entry){
        entries.put(id, entry);
        return this;
    }

    @Override
    public ScoreboardBuilder formattedPlain(Component key, Component value){
        String id = PlainComponentSerializer.plain().serialize(key);
        return formatted(id, key, value);
    }

    @Override
    public ScoreboardBuilder formattedTranslatable(String id, Component value){
        return formatted(id, getTranslatable(id), value);
    }

    @Override
    public ScoreboardBuilder formatted(String id, Component key, Component value){
        return id(id, formatFunction.format(key, value));
    }

    @Override
    public ScoreboardBuilder all(ScoreboardBuilder builder){
        entries.putAll(builder.getEntries());
        return this;
    }

    @Override
    public ScoreboardBuilder empty(){
        return id("BLANK" + entries.size(), ScoreboardEntry.blank());
    }

    @Override
    public String getTranslatablePrefix() {
        return translatablePrefix;
    }

    @Override
    public Map<String, ScoreboardEntry> getEntries() {
        return entries;
    }

    private Component getTranslatable(String id) {
        String translatablePrefix = getTranslatablePrefix();

        if(translatablePrefix != null)
            id = translatablePrefix + id;

        return Component.translatable(id);
    }

}