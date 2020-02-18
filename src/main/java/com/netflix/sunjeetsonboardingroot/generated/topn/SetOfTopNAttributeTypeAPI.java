package com.netflix.sunjeetsonboardingroot.generated.topn;

import com.netflix.hollow.api.custom.HollowSetTypeAPI;

import com.netflix.hollow.core.read.dataaccess.HollowSetTypeDataAccess;
import com.netflix.hollow.api.objects.delegate.HollowSetLookupDelegate;

@SuppressWarnings("all")
public class SetOfTopNAttributeTypeAPI extends HollowSetTypeAPI {

    private final HollowSetLookupDelegate delegateLookupImpl;

    public SetOfTopNAttributeTypeAPI(TopNAPI api, HollowSetTypeDataAccess dataAccess) {
        super(api, dataAccess);
        this.delegateLookupImpl = new HollowSetLookupDelegate(this);
    }

    public TopNAttributeTypeAPI getElementAPI() {
        return getAPI().getTopNAttributeTypeAPI();
    }

    public TopNAPI getAPI() {
        return (TopNAPI)api;
    }

    public HollowSetLookupDelegate getDelegateLookupImpl() {
        return delegateLookupImpl;
    }

}