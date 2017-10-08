package com.netflix.vms.transformer.hollowinput;

import com.netflix.hollow.api.objects.HollowMap;
import com.netflix.hollow.core.schema.HollowMapSchema;
import com.netflix.hollow.api.objects.delegate.HollowMapDelegate;
import com.netflix.hollow.api.objects.generic.GenericHollowRecordHelper;

@SuppressWarnings("all")
public class MultiValuePassthroughMapHollow extends HollowMap<MapKeyHollow, ListOfStringHollow> {

    public MultiValuePassthroughMapHollow(HollowMapDelegate delegate, int ordinal) {
        super(delegate, ordinal);
    }

    @Override
    public MapKeyHollow instantiateKey(int ordinal) {
        return (MapKeyHollow) api().getMapKeyHollow(ordinal);
    }

    @Override
    public ListOfStringHollow instantiateValue(int ordinal) {
        return (ListOfStringHollow) api().getListOfStringHollow(ordinal);
    }

    public ListOfStringHollow get(String k0) {
        return findValue(k0);
    }

    @Override
    public boolean equalsKey(int keyOrdinal, Object testObject) {
        return GenericHollowRecordHelper.equalObject(getSchema().getKeyType(), keyOrdinal, testObject);
    }

    @Override
    public boolean equalsValue(int valueOrdinal, Object testObject) {
        return GenericHollowRecordHelper.equalObject(getSchema().getValueType(), valueOrdinal, testObject);
    }

    public VMSHollowInputAPI api() {
        return typeApi().getAPI();
    }

    public MultiValuePassthroughMapTypeAPI typeApi() {
        return (MultiValuePassthroughMapTypeAPI) delegate.getTypeAPI();
    }

}