package com.netflix.vms.transformer.hollowinput;

import com.netflix.hollow.api.custom.HollowTypeAPI;
import com.netflix.hollow.api.objects.provider.HollowFactory;
import com.netflix.hollow.core.read.dataaccess.HollowTypeDataAccess;

@SuppressWarnings("all")
public class VideoRatingRatingReasonIdsHollowFactory<T extends VideoRatingRatingReasonIdsHollow> extends HollowFactory<T> {

    @Override
    public T newHollowObject(HollowTypeDataAccess dataAccess, HollowTypeAPI typeAPI, int ordinal) {
        return (T)new VideoRatingRatingReasonIdsHollow(((VideoRatingRatingReasonIdsTypeAPI)typeAPI).getDelegateLookupImpl(), ordinal);
    }

    @Override
    public T newCachedHollowObject(HollowTypeDataAccess dataAccess, HollowTypeAPI typeAPI, int ordinal) {
        return (T)new VideoRatingRatingReasonIdsHollow(new VideoRatingRatingReasonIdsDelegateCachedImpl((VideoRatingRatingReasonIdsTypeAPI)typeAPI, ordinal), ordinal);
    }

}