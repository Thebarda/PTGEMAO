package fr.gemao.view.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.gemao.ctrl.adherent.FamilleCtrl;
import fr.gemao.entity.adherent.Famille;

public class AutocompletionFamille {

	public static String ATTR_AUTO_FAMILLE = "auto_familles";

	private AutocompletionFamille() {
	}

	/**
	 * Ajoute 1 variable à la request pour l'autocomplétion des famille.
	 * 'listNomcommune' : un tableau des nom de famille
	 * 
	 * @param request
	 *            La request à laquelle on ajoute les variables
	 * @return La request avec les variable
	 */
	public static HttpServletRequest initRequestForAutocompletionFamille(HttpServletRequest request) {
		List<Famille> familles = FamilleCtrl.recupererAllFamille();
		List<String> listFamille = new ArrayList<>();

		for (Famille f : familles) {
			if (f.getNomFamille() != null) {
				listFamille.add('"' + f.getNomFamille() + '"');
			}
		}
		request.setAttribute(ATTR_AUTO_FAMILLE, listFamille);
		return request;
	}
}
