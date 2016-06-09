package com.netflix.vms.transformer;


import com.netflix.vms.transformer.common.TransformerContext;

import java.util.Collections;
import java.util.ArrayList;
import com.netflix.vms.transformer.hollowinput.VideoGeneralHollow;
import java.util.List;
import com.netflix.vms.transformer.hollowinput.EpisodeHollow;
import com.netflix.vms.transformer.hollowinput.SeasonHollow;
import com.netflix.vms.transformer.hollowinput.ShowSeasonEpisodeHollow;
import com.netflix.vms.transformer.hollowinput.VMSHollowInputAPI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShowGrouper {
    
    private final VMSHollowInputAPI api;
    private final TransformerContext ctx;
    private final Map<Integer, Set<ShowSeasonEpisodeHollow>> displaySetsByVideoId;
    private final List<Set<TopNodeProcessGroup>> processGroups;
    
    public ShowGrouper(VMSHollowInputAPI api, TransformerContext ctx) {
        this.api = api;
        this.ctx = ctx;
        this.displaySetsByVideoId = new HashMap<>();
        this.processGroups = new ArrayList<>();
        group();
    }
    
    public Map<Integer, Set<ShowSeasonEpisodeHollow>> getGroupedShowSeasonEpisodes() {
        return displaySetsByVideoId;
    }
    
    public List<Set<TopNodeProcessGroup>> getProcessGroups() {
        return processGroups;
    }
    
    private void group() {
        findDisplaySetsByVideoId();
        expandFastlaneIds();
        findProcessGroups();
    }
    
    private void findDisplaySetsByVideoId() {
        for(ShowSeasonEpisodeHollow showSeasonEpisode : api.getAllShowSeasonEpisodeHollow()) {
            Set<ShowSeasonEpisodeHollow> groupOfSets = new HashSet<>();
            groupOfSets.add(showSeasonEpisode);
            
            for(Integer videoId : getBagOfVideoIds(groupOfSets)) {
                Set<ShowSeasonEpisodeHollow> existingSet = displaySetsByVideoId.get(videoId);
                if(existingSet != null && existingSet != groupOfSets) {
                    groupOfSets.addAll(existingSet);
                    
                    for(Integer replaceForVideoId : getBagOfVideoIds(existingSet)) {
                        displaySetsByVideoId.put(replaceForVideoId, groupOfSets);
                    }
                }
                
                displaySetsByVideoId.put(videoId, groupOfSets);
            }
        }
    }
    
    private void expandFastlaneIds() {
        if(ctx.getFastlaneIds() != null) {
            Set<Integer> expandedFastlaneIds = new HashSet<>();
            
            for(Integer i : ctx.getFastlaneIds()) {
                Set<ShowSeasonEpisodeHollow> displaySets = displaySetsByVideoId.get(i);
                if(displaySets != null && !displaySets.isEmpty())
                    expandedFastlaneIds.addAll(getBagOfVideoIds(displaySets));
                else
                    expandedFastlaneIds.add(i);
            }
            
            ctx.setFastlaneIds(expandedFastlaneIds);
        }
    }
    
    private void findProcessGroups() {
        Set<Integer> fastlaneIds = ctx.getFastlaneIds();
        Set<Integer> alreadyAddedTopNodes = new HashSet<Integer>();
        
        for(VideoGeneralHollow videoGeneral : api.getAllVideoGeneralHollow()) {
            Integer videoId = Integer.valueOf((int)videoGeneral._getVideoId());
            
            if(fastlaneIds != null && !fastlaneIds.contains(videoId))
                continue;
            
            if(!alreadyAddedTopNodes.contains(videoId) && VideoNodeType.isStandaloneOrTopNode(VideoNodeType.of(videoGeneral._getVideoType()._getValue()))) {
                Set<ShowSeasonEpisodeHollow> displaySets = displaySetsByVideoId.get(videoId);
                
                if(displaySets == null) {
                    processGroups.add(Collections.singleton(new TopNodeProcessGroup(videoId)));
                } else {
                    Map<Integer, TopNodeProcessGroup> theseGroupsByTopNode = new HashMap<>();
                    
                    theseGroupsByTopNode.put(videoId, new TopNodeProcessGroup(videoId));
                    
                    for(ShowSeasonEpisodeHollow displaySet : displaySets) {
                        int thisDisplaySetTopNode = (int)displaySet._getMovieId();
                        TopNodeProcessGroup topNodeProcessGroup = theseGroupsByTopNode.get(thisDisplaySetTopNode);
                        if(topNodeProcessGroup == null) {
                            topNodeProcessGroup = new TopNodeProcessGroup(thisDisplaySetTopNode);
                            theseGroupsByTopNode.put(thisDisplaySetTopNode, topNodeProcessGroup);
                            alreadyAddedTopNodes.add(thisDisplaySetTopNode);
                        }
                        topNodeProcessGroup.addShowSeasonEpisodeHollow(displaySet);
                    }
                    
                    Set<TopNodeProcessGroup> groups = new HashSet<TopNodeProcessGroup>();
                    
                    for(Map.Entry<Integer, TopNodeProcessGroup> entry : theseGroupsByTopNode.entrySet()) {
                        groups.add(entry.getValue());
                    }
                    
                    processGroups.add(groups);
                }
                
                alreadyAddedTopNodes.add(videoId);
            }
        }
    }

    private Set<Integer> getBagOfVideoIds(Set<ShowSeasonEpisodeHollow> showSeasonEpisodes) {
        Set<Integer> setOfIds = new HashSet<>();
        
        for(ShowSeasonEpisodeHollow showSeasonEpisode : showSeasonEpisodes) {
            setOfIds.add((int)showSeasonEpisode._getMovieId());
            
            for(SeasonHollow season : showSeasonEpisode._getSeasons()) {
                setOfIds.add((int)season._getMovieId());
                
                for(EpisodeHollow episode : season._getEpisodes()) {
                    setOfIds.add((int)episode._getMovieId());
                }
            }
        }
        
        return setOfIds;
    }
    
    public static class TopNodeProcessGroup {
        private final int topNodeId;
        private final Set<ShowSeasonEpisodeHollow> showSeasonEpisodes;
        
        public TopNodeProcessGroup(int topNodeId) {
            this.topNodeId = topNodeId;
            this.showSeasonEpisodes = new HashSet<ShowSeasonEpisodeHollow>();
        }
        
        private void addShowSeasonEpisodeHollow(ShowSeasonEpisodeHollow hierarchy) {
            this.showSeasonEpisodes.add(hierarchy);
        }
        
        public int getTopNodeId() {
            return topNodeId;
        }
        
        public Set<ShowSeasonEpisodeHollow> getShowSeasonEpisodes() {
            return showSeasonEpisodes;
        }
    }
}
