package fr.gemao.view.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.gemao.ctrl.AdresseCtrl;
import fr.gemao.entity.Adresse;

public class AutocompletionAdresse {

	public static String ATTR_LIST_RUE = "auto_list_rue";
	public static String ATTR_LIST_COMPL = "auto_list_compl";

	private AutocompletionAdresse() {
	}

	/**
	 * Ajoute 2 variable à la request pour l'autocomplétion des adresses.
	 * "auto_list_rue" : un tableau des nom de rue. "auto_list_compl" : un
	 * tableau des Informations Complémantaire.
	 * 
	 * @param request
	 *            La request à laquelle on ajoute les variables
	 * @return La request avec les variable
	 */
	public static HttpServletRequest initRequestForAutoCompletionAdresse(
			HttpServletRequest request) {
		List<Adresse> adresses = AdresseCtrl.getListAdresses();

		List<String> list_rues = new ArrayList<>();
		List<String> list_compl = new ArrayList<>();
		for (Adresse adr : adresses) {
			if (adr.getNomRue() != null) {
				if (!list_rues.contains('"' + adr.getNomRue() + '"')) {
					list_rues.add('"' + adr.getNomRue() + '"');
				}
			}
			if (adr.getInfoCompl() != null) {
				if (!list_compl.contains('"' + adr.getInfoCompl() + '"')) {
					list_compl.add('"' + adr.getInfoCompl() + '"');
				}
			}
		}

		request.setAttribute(ATTR_LIST_RUE, list_rues);
		request.setAttribute(ATTR_LIST_COMPL, list_compl);

		return request;
	}
}
