package fr.gemao.fileparser;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CSVFileParser extends Parser {

	public CSVFileParser(String filename) {
		super(filename);
	}

	/**
	 * Méthode qui lit un fichier csv
	 */
	@Override
	public Map<String, Object> read() {
		throw new RuntimeException("Not implemented yet");
	}

	/**
	 * Méthode qui écrit dans un fichier csv
	 * sous la forme :
	 * 
	 * 	clef1;clef2;[...];clefn
	 *  valeur1_1;valeur1_2;[...];valeur1_n
	 *  [...]
	 *  valeurm_1;valeurm_2;[...];valeurm_n
	 *  
	 * L'objet contenant les valeurs dans la {@link Map}
	 * doit être une {@link List} contenant des {@link String}
	 * Toutes les listes doivent avoir la même longueur
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean write(Map<String, Object> data) {
		Set<String> keyset = data.keySet();
		List<String> liste;
		int nombreLignes;
		
		liste = (List<String>) data.get(keyset.toArray()[0]);
		nombreLignes = liste.size();

		File file = null;
		try {
			file = new File(filename);
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(file);
			DataOutputStream dos = new DataOutputStream(out);
			
			// Ecriture des noms des colonnes
			for(String key : keyset){
				dos.writeChars("\""+key+"\";");
			}
			
			dos.writeChars("\n");
			
			for (int i = 0; i < nombreLignes; i++) {
				for(String key : keyset){
					liste = (List<String>) data.get(key);
					dos.writeChars("\""+ liste.get(i)+"\";");
					//System.out.println(liste.get(i));
				}
				dos.writeChars("\n");
			}

			dos.flush();
			dos.close();
		} catch (IOException e) {
			return false;
		} 
		return true;
	}

}
