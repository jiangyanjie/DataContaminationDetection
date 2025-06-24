/**
 * $HeadURL$
 */
package ch.goatbrain.smooks.csvreader;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

/**
 * TODO
 *
 * @author Michael Buchli <michael.buchli@wuerth-itensis.com>
 * @copyright (c) 2007-2014, Wuerth ITensis AG
 * @created 30.01.2014 13:47:10
 *
 * @$Revision$
 *
 * @$LastChangedBy$
 * @$LastChangedDate$
 * @$Id$
 */
public abstract class AbstractCsvDataClass {

    private static final Logger LOG = Logger.getLogger(AbstractCsvDataClass.class.getName());
    protected char separatorChar = '|';
    protected String csvFields;

    public char getSeparatorChar() {
        return separatorChar;
    }

    public void setSeparatorChar(char separatorChar) {
        this.separatorChar = separatorChar;
    }

    public String getCsvFields() {
        return csvFields;
    }

    public void setCsvFields(String csvFields) {
        this.csvFields = csvFields;
    }

    public int getNofFields() {
        return StringUtils.countMatches(csvFields, String.valueOf(",")) + 1;
    }

    protected abstract boolean mandatoryFieldsAvailable(boolean recordIsValid);

    protected abstract boolean fieldContentIsValid(boolean recordIsValid);

    public boolean isValid() {
        boolean recordIsValid = false;
        recordIsValid = mandatoryFieldsAvailable(recordIsValid);
        //recordIsValid = fieldContentIsValid(recordIsValid);
        return recordIsValid;
    }

    protected boolean fieldHasContent(String fieldContent) {
        return fieldHasContent(fieldContent, "");
    }

    protected boolean fieldHasContent(String fieldContent, String fieldName) {
        boolean fieldHasContent = false;
        if (fieldContent != null && fieldContent.length() > 0) {
            fieldHasContent = true;
        } else {
            LOG.log(Level.OFF, "!!!!!!!!!!!!eRRoR!!!!!!!!!!!!!!!!!: Mandatory Field " + fieldName + " has NO content!");
        }
        return fieldHasContent;
    }
}
