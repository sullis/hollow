package com.netflix.vms.transformer.hollowinput;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.index.AbstractHollowUniqueKeyIndex;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class CharacterArtworkSourcePrimaryKeyIndex extends AbstractHollowUniqueKeyIndex<VMSHollowInputAPI, CharacterArtworkSourceHollow> {

    public CharacterArtworkSourcePrimaryKeyIndex(HollowConsumer consumer) {
        this(consumer, true);    }

    public CharacterArtworkSourcePrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh) {
        this(consumer, isListenToDataRefresh, ((HollowObjectSchema)consumer.getStateEngine().getSchema("CharacterArtworkSource")).getPrimaryKey().getFieldPaths());
    }

    public CharacterArtworkSourcePrimaryKeyIndex(HollowConsumer consumer, String... fieldPaths) {
        this(consumer, true, fieldPaths);
    }

    public CharacterArtworkSourcePrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh, String... fieldPaths) {
        super(consumer, "CharacterArtworkSource", isListenToDataRefresh, fieldPaths);
    }

    public CharacterArtworkSourceHollow findMatch(Object... keys) {
        int ordinal = idx.getMatchingOrdinal(keys);
        if(ordinal == -1)
            return null;
        return api.getCharacterArtworkSourceHollow(ordinal);
    }

}