/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import xml.InvoiceRow;
import xml.XmlElement;

/**
 *
 * @author annisall@cs
 */
public class CsvConvert {

    private String fileName;
    private ArrayList<XmlElement> elements;
    private String[] wanted;
    private ArrayList<ArrayList<String>> details;
    private ArrayList<ArrayList<String>> inrows;
    private ArrayList<String> finals;
    private File file;

    public CsvConvert(ArrayList<XmlElement> list) {
        elements = list;
        inrows = new ArrayList<ArrayList<String>>();
        details = new ArrayList<ArrayList<String>>();
        Calendar calendar = new GregorianCalendar();
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(calendar.getTime());
        fileName = "Invoice_" + date + ".csv";
        file = new File(fileName);

        wanted = new String[]{"InvoiceDate", "InvoiceTypeCode", "InvoiceFreeText", "BuyerOrganisationName", "BuyerStreetName",
            "BuyerTownName", "BuyerPostCodeIdentifier", "BuyerPartyIdentifier", "CountryCode", "DeliveryOrganisationName", "DeliveryStreetName", "DeliveryTownName",
            "DeliveryPostCodeIdentifier", "CountryCode", "ArticleIdentifier", "ArticleName", "OrderedQuantity", "UnitPriceAmount", "RowVatRatePercent"};
    }

    public void getElements() {
        for (int i = 0; i < elements.size(); i++) {
            XmlElement elem = elements.get(i);
            iterateElements(elem);

        }
    }

    public String generateCsv() throws IOException {
        FileWriter writer = new FileWriter(file);
        writeDetails(writer);
        writer.append("\n");
        writeInrows(writer);
        writer.flush();
        writer.close();
        return fileName;

    }

    private void addMissings(FileWriter writer, ArrayList<ArrayList<String>> list, int times) throws IOException {
        for (int i = 0; i < times; i++) {
            writer.append(";");

        }

    }

    private void addToRightArray(XmlElement elem, int k) {
        if (elem.getClass().equals(InvoiceRow.class)) {
            inrows.add(elem.getWantedElements().get(wanted[k]));
        } else {
            details.add(elem.getWantedElements().get(wanted[k]));

        }
    }

    private void iterateElements(XmlElement elem) {
        for (int j = 0; j < elem.getWantedElements().size(); j++) {
            for (int k = 0; k < wanted.length; k++) {
                if (elem.getWantedElements().containsKey(wanted[k]) && !details.contains(elem.getWantedElements().get(wanted[k])) && !inrows.contains(elem.getWantedElements().get(wanted[k]))) {
                    addToRightArray(elem, k);
                }

            }
        }
    }

    private void writeDetails(FileWriter writer) throws IOException {
        for (int j = 0; j < 5; j++) {
            writer.append(details.get(j).get(0) + ";");
        }
        for (int i = 0; i < details.size(); i++) {
            writer.append(details.get(i).get(0) + ";");

        }
        addMissings(writer, details, 25 - (details.size() + 5));
    }
    

    private void writeInrows(FileWriter writer) throws IOException {
        for (int i = 0; i < inrows.size(); i++) {
            for (int j = 0; j < inrows.get(i).size(); j++) {
                writer.append(inrows.get(i).get(j) + ";");
                if (i == 4) {
                    for (int k = 0; k < 25 - i; k++) {
                        writer.append(";");

                    }
                    writer.append("\n");
                }
            }
        }
        addMissings(writer, inrows, 21);
    }
}
