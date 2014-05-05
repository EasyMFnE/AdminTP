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
 * Simple extension of Exception used for catching errors when parsing locations
 * from serialized Strings.
 * 
 * @author Eric Hildebrand
 */
public class InvalidLocationException extends Exception {
    
    private static final long serialVersionUID = 2371523525090541345L;
    
    private String reason;
    
    /**
     * @param reason
     *            Underlying reason for the error
     */
    public InvalidLocationException(String reason) {
        this.reason = reason;
    }
    
    /**
     * @return The underlying reason for the error
     */
    public String getReason() {
        return reason;
    }
    
}
