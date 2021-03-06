/*
 *  Copyright (C) 2013, Peter Decsi.
 * 
 *  This library is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public 
 *  License as published by the Free Software Foundation, either 
 *  version 3 of the License, or (at your option) any later version.
 * 
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jreserve.gui.data.api;

import org.jreserve.gui.misc.audit.event.AuditEvent;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public interface DataEvent extends AuditEvent {
    
    public DataSource getDataSource();
    
    public static interface Created extends DataEvent {}
    
    public static interface Deleted extends DataEvent {}
    
    public static interface Renamed extends DataEvent {
        public String getOldPath();
    }
    
    public static interface DataChange extends DataEvent {}
    
    public static interface DataImport extends DataChange {}
    
    public static interface DataDeletion extends DataChange {}
}
