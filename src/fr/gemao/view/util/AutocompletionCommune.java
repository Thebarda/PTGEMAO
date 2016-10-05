package fr.gemao.view.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.gemao.ctrl.CommuneCtrl;
import fr.gemao.entity.Commune;

public class AutocompletionCommune {

	private AutocompletionCommune() {
	}

	/**
	 * Ajoute 2 variable à la request pour l'autocomplétion des commune.
	 * 'listNomcommune' : un tableau des nom de commune. 'dicoCOmmune' : un
	 * dicotionnaire {'NomComunne' : codePostal ...}.
	 * 
	 * @param request
	 *            La request à laquelle on ajoute les variables
	 * @return La request avec les variable
	 */
	public static HttpServletRequest initRequestForAutoCompletionCommune(HttpServletRequest request) {
		List<String> listNom = CommuneCtrl.getListNomCommune();
		ArrayList<String> listNomWithCote = new ArrayList<>();
		for (String nom : listNom) {
			listNomWithCote.add('"' + nom + '"');
		}
		request.setAttribute("listNomCommune", listNomWithCote);
		List<Commune> listCommune = CommuneCtrl.getListCommunes();
		String dicoNomCommuneCodePostal = new String("{");
		for (Commune c : listCommune) {
			if (dicoNomCommuneCodePostal.length() != 1) {
				dicoNomCommuneCodePostal += ",";
			}
			dicoNomCommuneCodePostal += '"' + c.getNomCommune() + '"' + ':' + c.getCodePostal().toString();
		}
		dicoNomCommuneCodePostal += "}";
		request.setAttribute("dicoCommune", dicoNomCommuneCodePostal);

		return request;
	}
}
