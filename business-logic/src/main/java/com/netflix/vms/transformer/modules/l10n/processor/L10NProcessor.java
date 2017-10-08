package com.netflix.vms.transformer.modules.l10n.processor;

public interface L10NProcessor<K> {


    void processInput(K input);

    /**
     * Return the items added
     */
    int getItemsAdded();
}