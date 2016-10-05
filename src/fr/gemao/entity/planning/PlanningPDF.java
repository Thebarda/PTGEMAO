/**
 * 
 */
package fr.gemao.entity.planning;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.gemao.entity.Jour;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.util.DateUtil;
import fr.gemao.util.Filter;
import fr.gemao.util.Predicat;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public class PlanningPDF {

	////////////////////////////////////////////////////////////
	// Constantes de classe
	private static final int NB_LIGNE = 56;
	private static final int NB_QUART_DHEURE = 4;
	private static final int QUART_DHEURE = 15;

	////////////////////////////////////////////////////////////

	/**
	 * Méthode générant un pdf concernant un emploi du temps
	 * 
	 * @param planning
	 *            : Le planning en question
	 */
	public void createPdf(Planning planning, String path) {
		System.out.println(path);
		/**
		 * Document de base
		 */
		Document document = new Document(PageSize.A4);

		/**
		 * Initialisation du fichier pdf
		 */
		try {
			PdfWriter.getInstance(document, new FileOutputStream(path));
		} catch (DocumentException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		/**
		 * Ouverture du document
		 */
		document.open();

		Date dateExport = new Date(new java.util.Date().getTime());
		String titre = planning.getNomPlanning() + " du " + DateUtil.toFrenchDate(planning.getDateDeb()) + " au "
				+ DateUtil.toFrenchDate(planning.getDateFin()) + " (généré le " + DateUtil.toFrenchDate(dateExport)
				+ ")";
		try {

			document.add(addTitle(titre));
		} catch (DocumentException e) {
			System.err.println(e.getMessage());
		}

		/**
		 * On ajout tous les créneaux
		 */
		try {
			List<Jour> jours = DAOFactory.getInstance().getJourDAO().getLunVen();

			for (Jour jour : jours) {
				document.add(creerPlanning(planning, jour));

				document.newPage();
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		/**
		 * Fermeture du document
		 */
		document.close();
	}

	/**
	 * Méthode permettant de générer le corps du planning : le tableau
	 * 
	 * @param planning
	 *            Le planning en question
	 * @return PdfTable le tableau
	 */
	private PdfPTable creerPlanning(Planning planning, final Jour jour) {

		// Le tableau
		PdfPTable table = new PdfPTable(6);

		// Une cellule
		PdfPCell cell = new PdfPCell();

		List<String> listeSalles = DAOFactory.getInstance().getSalleDAO().getAllNomSalle();
		listeSalles.add(0, "");

		Font fontJour = new Font(FontFamily.HELVETICA, 15.0f, 0, new BaseColor(245, 245, 245));
		Font fontCellule = new Font(FontFamily.HELVETICA, 7.0f, 0, new BaseColor(245, 245, 245));
		Font fontTitre = new Font(FontFamily.HELVETICA, 11.0f, 0, new BaseColor(245, 245, 245));

		////////////////////////////////////////////////////////////
		// Paramètres généraux de la cellule
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setBorderColor(BaseColor.GRAY);
		////////////////////////////////////////////////////////////

		// La liste des créneaux
		List<Creneau> creneaux;
		
		// Gestion du décalage
		List<Integer> decalage = new ArrayList<>();

		Predicat<Creneau> predicatJour = new Predicat<Creneau>() {

			@Override
			public boolean test(Creneau t) {
				
				if (jour.getNomJour() == null || t.getHeureDeb() == null || t.getHeureFin() == null || t.getSalle() == null)
					return false;
				
				return (jour.getNomJour().equals(t.getJour().getNomJour()));
			}

			@Override
			public boolean test(Creneau t, Creneau tmp) {
				throw new UnsupportedOperationException();
			}
		};

		creneaux = Filter.filter(
				DAOFactory.getInstance().getCreneauDAO().getListCreneauxPlanning(planning.getIdPlanning()),
				predicatJour);

		for (int i = 1; i < listeSalles.size(); i++) {
			decalage.add(0);
		}

		// Le header du tableau, nom des salles
		cell.setColspan(listeSalles.size());
		cell.setFixedHeight(30.0f);
		cell.setPhrase(new Phrase(jour.getNomJour(), fontJour));
		cell.setBackgroundColor(new BaseColor(33, 150, 243));

		table.addCell(cell);

		cell.setColspan(1);
		cell.setMinimumHeight(8.0f);
		cell.setBackgroundColor(new BaseColor(100, 181, 246));

		for (String string : listeSalles) {
			cell.setPhrase(new Phrase(string, fontTitre));

			table.addCell(cell);
		}
		////////////////////////////////////////////////////////////

		cell.setBackgroundColor(BaseColor.WHITE);
		cell.setFixedHeight(8.0f);

		int compteur = 0;
		boolean trouve = false;

		////////////////////////////////////////////////////////////
		// Première boucle, on parcourt toutes les lignes
		for (int i = 0; i < NB_LIGNE; i++) {

			if ((i % NB_QUART_DHEURE) == 0) {
				cell.setPhrase(new Phrase(String.valueOf(i / NB_QUART_DHEURE + 8) + "h00", fontTitre));
				cell.setBackgroundColor(new BaseColor(100, 181, 246));
				cell.setRowspan(NB_QUART_DHEURE);

				table.addCell(cell);
			}

			////////////////////////////////////////////////////////////
			// Deuxième boucle, on parcourt toutes les colonnes
			for (int j = 1; j < listeSalles.size(); j++) {

				if (i % NB_QUART_DHEURE != 0) {
					cell.setBorderWidthTop(0.01f);
				} else {
					cell.setBorderWidthTop(1.0f);
				}

				while (decalage.get(j - 1) > 0) {
					decalage.set(j - 1, decalage.get(j - 1) - 1);
					j++;

					if (j == listeSalles.size()) {
						break;
					}
				}

				if (j == listeSalles.size()) {
					break;
				}

				compteur = 0;
				trouve = false;

				cell.setRowspan(1);
				cell.setBackgroundColor(BaseColor.WHITE);
				cell.setPhrase(new Phrase(""));

				while (compteur < creneaux.size() && !trouve) {
					Creneau c = creneaux.get(compteur);

					if (c.getHeureDeb().getHeure() == (i / NB_QUART_DHEURE) + 8
							&& c.getHeureDeb().getMinute() == (i % NB_QUART_DHEURE) * QUART_DHEURE
							&& c.getSalle() != null && c.getSalle().getNomSalle().equals(listeSalles.get(j))) {

						cell.setRowspan(c.getNbQuartDHeure());
						cell.setBackgroundColor(new BaseColor(this.hexToRGB(c.getCouleur()).getRGB()));
						cell.setPhrase(new Phrase(c.getLibelle(), fontCellule));

						decalage.set(j - 1, c.getNbQuartDHeure() - 1);

						trouve = true;
						creneaux.remove(compteur);
					}

					compteur++;
				}

				table.addCell(cell);
			}

		}

		return table;
	}

	/***
	 * Méthode permettant d'ajouter un titre au document
	 * 
	 * @param nom
	 *            Le nom en question
	 * @return Le paragraphe qui sera ajouté au document
	 */
	private Paragraph addTitle(String nom) {
		Font fontbold = FontFactory.getFont("Roboto", 10, Font.BOLD);
		Paragraph p = new Paragraph(nom, fontbold);
		p.setSpacingAfter(20);
		p.setAlignment(1); // Center

		return p;
	}

	/**
	 * Méthode permettant de convertir une couleur hexadecimale en RGB
	 * 
	 * @param colorStr
	 *            La couleur en hexa (peut être "ffffff" ou bien "#ffffff"
	 * @return Color, au format rgb
	 */
	private Color hexToRGB(String colorStr) {
		if (colorStr.charAt(0) != '#')
			colorStr = "#" + colorStr;

		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}
}
