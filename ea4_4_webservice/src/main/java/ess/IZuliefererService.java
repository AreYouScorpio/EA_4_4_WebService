package ess;

import javax.jws.WebService;

@WebService
public interface IZuliefererService {
	public String erstelleAngebot(String angebotsDaten);
	public String erteileAuftrag(String auftragsDaten);
}
