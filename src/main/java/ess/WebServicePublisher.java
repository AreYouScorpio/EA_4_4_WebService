package ess;

import javax.xml.ws.Endpoint;

public class WebServicePublisher {
    public static void main(String[] args) {
        // Publikáljuk a Webservice-t a megadott URL alatt
        Endpoint.publish("http://localhost:8080/zulieferer", new ZuliefererService());
        System.out.println("Webservice läuft unter http://localhost:8080/zulieferer?wsdl");
    }
}
