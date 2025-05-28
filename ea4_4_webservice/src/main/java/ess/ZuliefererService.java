package ess;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@WebService
public class ZuliefererService implements IZuliefererService {

    //Hinweise zur Ausführung:
    //In der Eclipse-IDE kann auf der Grundlage der in dieser Klasse implementierten Methoden
    //ein WebService generiert werden.
    //Erstellen Sie dazu ein neues Projekt vom Typ "Andere..." > "Web Service".

    final double Stueckpreis = 37.00;

    @Override
    public String erstelleAngebot(String angebotsDaten) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(new ByteArrayInputStream(angebotsDaten.getBytes()));

            // Beispiel: Inhalt des Elements Wunschtermin lesen, Liefertermin prüfen und in das XML-Dokument einfügen
            NodeList lWunschtermine = doc.getElementsByTagName("Wunschtermin");
            if (lWunschtermine.getLength() > 0) {
                Element el = (Element) lWunschtermine.item(0);
                String sWunschtermin = el.getTextContent();
                LocalDate dWunschtermin = LocalDate.parse(sWunschtermin, dateFormatter);
                LocalDate dLiefertermin = LocalDate.now();
                dLiefertermin = dLiefertermin.plusDays(7);
                Element liefertermin = doc.createElement("Liefertermin");
                if (dLiefertermin.isAfter(dWunschtermin)) {
                    liefertermin.setTextContent(dLiefertermin.format(dateFormatter));
                } else {
                    liefertermin.setTextContent(dWunschtermin.format(dateFormatter));
                }
                doc.getDocumentElement().appendChild(liefertermin);
            }

            //Aufgabe: Inhalt des Elements Menge lesen, Gesamtpreis berechnen und in das XML-Dokument einfügen
            NodeList lMenge = doc.getElementsByTagName("Menge");
            if (lMenge.getLength() > 0) {
                Element el = (Element) lMenge.item(0);
                int menge = Integer.parseInt(el.getTextContent());
                double gesamtpreis = menge * Stueckpreis;

                Element preisElement = doc.createElement("Gesamtpreis");
                preisElement.setTextContent(String.format("%.2f", gesamtpreis));
                doc.getDocumentElement().appendChild(preisElement);
            }

            // Ausgabe XML-Dokument als String
            StringWriter writer = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.toString();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    //Aufgabe: Auftragsdaten unverändert zurückgeben
    @Override
    public String erteileAuftrag(String auftragsDaten) {
        return auftragsDaten;
    }


}