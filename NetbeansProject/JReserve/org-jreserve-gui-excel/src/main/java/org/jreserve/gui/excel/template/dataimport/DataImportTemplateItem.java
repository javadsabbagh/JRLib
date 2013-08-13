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
package org.jreserve.gui.excel.template.dataimport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.jreserve.jrlib.gui.data.DataType;
import org.jreserve.jrlib.gui.data.MonthDate;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class DataImportTemplateItem {
    
    @XmlElement(name="reference", required=true)
    private String reference;
    @XmlElement(name="dataType", required=true)
    private DataType dataType;
    @XmlElement(name="cummulated", required=true)
    private boolean cummulated;
    
    public DataImportTemplateItem() {
    }

    public DataImportTemplateItem(String reference, DataType dataType, boolean cummulated) {
        this.reference = reference;
        this.dataType = dataType;
        this.cummulated = cummulated;
    }
    
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
    
    public boolean isCummulated() {
        return cummulated;
    }
    
    public void setCummulated(boolean cummulated) {
        this.cummulated = cummulated;
    }
    
    @XmlRootElement(name="tableSource")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class TableDataImportTempalteItem extends DataImportTemplateItem {
        
        public TableDataImportTempalteItem() {
        }

        public TableDataImportTempalteItem(String reference, DataType dataType, boolean cummulated) {
            super(reference, dataType, cummulated);
        }
    }
    
    @XmlRootElement(name="triangleSource")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class TriangleDataImportTempalteItem extends DataImportTemplateItem {
        @XmlElement(name="startDate",required=true)
        private MonthDate startDate;
        @XmlElement(name="accidentLength",required=true)
        private int accidentLength;
        @XmlElement(name="developmentLength",required=true)
        private int developmentLength;
        
        public TriangleDataImportTempalteItem() {
        }

        public TriangleDataImportTempalteItem(String reference, DataType dataType, boolean cummulated, MonthDate startDate, int accidentLength, int developmentLength) {
            super(reference, dataType, cummulated);
            
            if(startDate == null)
                throw new NullPointerException("Start date is null!");
            this.startDate = startDate;
            
            if(accidentLength < 1)
                throw new IllegalArgumentException("Accident length is less then 1! "+accidentLength);
            this.accidentLength = accidentLength;
            
            if(developmentLength < 1)
                throw new IllegalArgumentException("Development length is less then 1! "+developmentLength);
            this.developmentLength = developmentLength;
        }

        public MonthDate getStartDate() {
            return startDate;
        }

        public void setStartDate(MonthDate startDate) {
            if(startDate == null)
                throw new NullPointerException("Start date is null!");
            this.startDate = startDate;
        }

        public int getAccidentLength() {
            return accidentLength;
        }

        public void setAccidentLength(int accidentLength) {
            if(accidentLength < 1)
                throw new IllegalArgumentException("Accident length is less then 1! "+accidentLength);
            this.accidentLength = accidentLength;
        }

        public int getDevelopmentLength() {
            return developmentLength;
        }

        public void setDevelopmentLength(int developmentLength) {
            if(developmentLength < 1)
                throw new IllegalArgumentException("Development length is less then 1! "+developmentLength);
            this.developmentLength = developmentLength;
        }
    }
    
}