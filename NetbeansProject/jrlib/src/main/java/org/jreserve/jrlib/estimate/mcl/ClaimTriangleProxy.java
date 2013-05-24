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
package org.jreserve.jrlib.estimate.mcl;

import org.jreserve.jrlib.AbstractCalculationData;
import org.jreserve.jrlib.triangle.Cell;
import org.jreserve.jrlib.triangle.claim.ClaimTriangle;

/**
 * This class proxies a given triangle, expect the 
 * {@link CalculationData#recalculate() recalculate()} an the 
 * {@link CalculationData#detach() detach()} method. Basically this class is
 * simply a {@link ClaimTriangle ClaimTriangle}, which `callsForwarded` and
 * `eventsFired` property is set to false (and can not be changed).
 * 
 * @author Peter Decsi
 * @version 1.0
 */
class ClaimTriangleProxy extends AbstractCalculationData<ClaimTriangle> implements ClaimTriangle {
    
    ClaimTriangleProxy(ClaimTriangle source) {
        super(source);
        super.setCallsForwarded(false);
        super.setEventsFired(false);
    }
    
    /**
     * Returns the triangle proxied by this instance.
     */
    ClaimTriangle getProxiedTriangle() {
        return source;
    }
    
    @Override
    public boolean isEventsFired() {
        return super.isEventsFired();
    }
    
    @Override
    public void setEventsFired(boolean eventsFired) {
    }
    
    @Override
    public boolean isCallsForwarded() {
        return super.isCallsForwarded();
    }
    
    /**
     * Calling this method does have no effect. The property will always
     * be `false`.
     */
    @Override
    public void setCallsForwarded(boolean forwardCalls) {
    }
    
    @Override
    public int getAccidentCount() {
        return source.getAccidentCount();
    }

    @Override
    public int getDevelopmentCount() {
        return source.getDevelopmentCount();
    }

    @Override
    public int getDevelopmentCount(int accident) {
        return source.getDevelopmentCount(accident);
    }

    @Override
    public double getValue(Cell cell) {
        return source.getValue(cell);
    }

    @Override
    public double getValue(int accident, int development) {
        return source.getValue(accident, development);
    }

    @Override
    public double[][] toArray() {
        return source.toArray();
    }

    /**
     * Does nothing.
     */
    @Override
    protected void recalculateLayer() {
    }
    
    /**
     * Does nothing.
     */
    @Override
    protected void fireChange() {
    }
}
