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
package org.jreserve.gui.poi.read.xlsx;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.jreserve.gui.poi.read.ExcelReader;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
abstract class XlsxReader<T> extends DefaultHandler implements ExcelReader<T> {
    private final static Logger logger = Logger.getLogger(XlsxReader.class.getName());
    
    private OPCPackage pkg;
    
    @Override
    public T read(File file) throws IOException {
        try {
            pkg = OPCPackage.open(file);
            XSSFReader reader = new XSSFReader(pkg);
            readReader(reader);
            return getResult();
        } catch (Exception ex) {
            String path = file==null? null : file.getAbsolutePath();
            String msg = String.format("Unabel to read file '%s'!", path);
            logger.log(Level.SEVERE, msg, ex);
            throw new IOException(msg, ex);
        } finally {
            close();
        }
    }
    
    protected abstract void readReader(XSSFReader reader) throws Exception;
    
    protected abstract T getResult() throws Exception;
    
    private void close() {
        if(pkg != null)
            try{pkg.close();} catch (IOException ex) {}
    }
    
    protected XMLReader createParser() throws SAXException {
        return createParser(this);
    }
    
    protected static XMLReader createParser(ContentHandler contentHandler) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(contentHandler);
        return parser;
    }
}
