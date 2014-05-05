/*
 * This file is part of the AdminTP plugin by EasyMFnE.
 * 
 * AdminTP is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 * 
 * AdminTP is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for
 * a particular purpose. See the GNU General Public License for details.
 * 
 * You should have received a copy of the GNU General Public License v3 along
 * with AdminTP. If not, see <http://www.gnu.org/licenses/>.
 */
package net.easymfne.admintp;

/**
 * Enum representing the types of locations that the plugin can detect. Also
 * contains information about whether the plugin stores the location itself.
 * 
 * @author Eric Hildebrand
 */
public enum LocationType {
    
    BED(null),
    LOGIN(".login"),
    LOGOUT(".logout"),
    DEATH(".death"),
    ESSENTIALS(null),
    FACTIONS(null);
    
    private String node;
    
    /**
     * @param node
     *            node prefix to append to the player node in the config.
     */
    LocationType(String node) {
        this.node = node;
    }
    
    /**
     * @return node prefix to append to the player node in the config, or null
     *         if the location is not saved.
     */
    public String getNode() {
        return node;
    }
    
    /**
     * @return Whether the plugin manually saves the location information
     */
    public boolean isSaved() {
        return node != null;
    }
    
}
