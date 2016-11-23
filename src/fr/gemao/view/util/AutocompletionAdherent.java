package fr.gemao.view.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.gemao.ctrl.adherent.AdherentCtrl;
import fr.gemao.ctrl.adherent.FamilleCtrl;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.entity.adherent.Famille;

public class AutocompletionAdherent {

	public static String ATTR_AUTO_FAMILLES = "auto_familles";

	private AutocompletionAdherent() {
	}

	/**
	 * Ajoute 1 variable à la request pour l'autocomplétion des famille.
	 * 'listNomcommune' : un tableau des nom de famille
	 * 
	 * @param request
	 *            La request à laquelle on ajoute les variables
	 * @return La request avec les variable
	 */
	public static HttpServletRequest initRequestForAutocompletionAdherent(HttpServletRequest request) {
		List<Adherent> adherents = AdherentCtrl.recupererTousAdherents();
		List<String> listAdherents = new ArrayList<>();

		for (Adherent a : adherents) {
			listAdherents.add('"' + a.getNom()+" "+ a.getPrenom() + '"');
		}
		request.setAttribute(ATTR_AUTO_FAMILLES, "coucou");
		return request;
	}
}