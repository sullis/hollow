package com.netflix.vms.transformer.hollowinput;

import com.netflix.hollow.api.custom.HollowTypeAPI;
import com.netflix.hollow.api.objects.delegate.HollowCachedDelegate;
import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class PackageMomentDelegateCachedImpl extends HollowObjectAbstractDelegate implements HollowCachedDelegate, PackageMomentDelegate {

    private final Long clipSpecRuntimeMillis;
    private final Long offsetMillis;
    private final int downloadableIdsOrdinal;
    private final Long bifIndex;
    private final int momentTypeOrdinal;
    private final Long momentSeqNumber;
    private final int tagsOrdinal;
    private PackageMomentTypeAPI typeAPI;

    public PackageMomentDelegateCachedImpl(PackageMomentTypeAPI typeAPI, int ordinal) {
        this.clipSpecRuntimeMillis = typeAPI.getClipSpecRuntimeMillisBoxed(ordinal);
        this.offsetMillis = typeAPI.getOffsetMillisBoxed(ordinal);
        this.downloadableIdsOrdinal = typeAPI.getDownloadableIdsOrdinal(ordinal);
        this.bifIndex = typeAPI.getBifIndexBoxed(ordinal);
        this.momentTypeOrdinal = typeAPI.getMomentTypeOrdinal(ordinal);
        this.momentSeqNumber = typeAPI.getMomentSeqNumberBoxed(ordinal);
        this.tagsOrdinal = typeAPI.getTagsOrdinal(ordinal);
        this.typeAPI = typeAPI;
    }

    public long getClipSpecRuntimeMillis(int ordinal) {
        if(clipSpecRuntimeMillis == null)
            return Long.MIN_VALUE;
        return clipSpecRuntimeMillis.longValue();
    }

    public Long getClipSpecRuntimeMillisBoxed(int ordinal) {
        return clipSpecRuntimeMillis;
    }

    public long getOffsetMillis(int ordinal) {
        if(offsetMillis == null)
            return Long.MIN_VALUE;
        return offsetMillis.longValue();
    }

    public Long getOffsetMillisBoxed(int ordinal) {
        return offsetMillis;
    }

    public int getDownloadableIdsOrdinal(int ordinal) {
        return downloadableIdsOrdinal;
    }

    public long getBifIndex(int ordinal) {
        if(bifIndex == null)
            return Long.MIN_VALUE;
        return bifIndex.longValue();
    }

    public Long getBifIndexBoxed(int ordinal) {
        return bifIndex;
    }

    public int getMomentTypeOrdinal(int ordinal) {
        return momentTypeOrdinal;
    }

    public long getMomentSeqNumber(int ordinal) {
        if(momentSeqNumber == null)
            return Long.MIN_VALUE;
        return momentSeqNumber.longValue();
    }

    public Long getMomentSeqNumberBoxed(int ordinal) {
        return momentSeqNumber;
    }

    public int getTagsOrdinal(int ordinal) {
        return tagsOrdinal;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

    public PackageMomentTypeAPI getTypeAPI() {
        return typeAPI;
    }

    public void updateTypeAPI(HollowTypeAPI typeAPI) {
        this.typeAPI = (PackageMomentTypeAPI) typeAPI;
    }

}