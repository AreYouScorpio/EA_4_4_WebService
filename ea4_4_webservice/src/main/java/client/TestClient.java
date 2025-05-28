package client;

public class TestClient {
    public static void main(String[] args) {
        // 1. Webservice proxy objektum létrehozása
        ZuliefererServiceService service = new ZuliefererServiceService();
        ZuliefererService port = service.getZuliefererServicePort();

        // 2. Teszt XML kérés készítése
        String requestXml =
                "<Zulieferungsanfrage>" +
                        "<Menge>10</Menge>" +
                        "<Wunschtermin>05.06.2025</Wunschtermin>" +
                        "</Zulieferungsanfrage>";

        // 3. Webservice hívása
        String response = port.erstelleAngebot(requestXml);

        // 4. Válasz kiírása a konzolra
        System.out.println("Antwort vom Webservice:");
        System.out.println(response);
    }
}
