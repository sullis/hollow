package com.netflix.vms.transformer.hollowinput;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.index.AbstractHollowUniqueKeyIndex;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class CharacterElementsPrimaryKeyIndex extends AbstractHollowUniqueKeyIndex<VMSHollowInputAPI, CharacterElementsHollow> {

    public CharacterElementsPrimaryKeyIndex(HollowConsumer consumer) {
        this(consumer, true);    }

    public CharacterElementsPrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh) {
        this(consumer, isListenToDataRefresh, ((HollowObjectSchema)consumer.getStateEngine().getSchema("CharacterElements")).getPrimaryKey().getFieldPaths());
    }

    public CharacterElementsPrimaryKeyIndex(HollowConsumer consumer, String... fieldPaths) {
        this(consumer, true, fieldPaths);
    }

    public CharacterElementsPrimaryKeyIndex(HollowConsumer consumer, boolean isListenToDataRefresh, String... fieldPaths) {
        super(consumer, "CharacterElements", isListenToDataRefresh, fieldPaths);
    }

    public CharacterElementsHollow findMatch(Object... keys) {
        int ordinal = idx.getMatchingOrdinal(keys);
        if(ordinal == -1)
            return null;
        return api.getCharacterElementsHollow(ordinal);
    }

}