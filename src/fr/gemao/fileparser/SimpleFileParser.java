package fr.gemao.fileparser;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import fr.gemao.listeners.LoadConfigListener;

public class SimpleFileParser extends Parser{

	public SimpleFileParser(String filename) {
		super(filename);
	}

	/**
	 * Méthode qui lit un fichier de configuration
	 * simple de la forme :
	 * 
	 * 	#commentaire
	 * 	clef1 = valeur1
	 *  clef2 = valeur2
	 *  
	 * L'objet contenant les valeurs dans la {@link Map}
	 * retournée est un {@link String}
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> read() {
		InputStream is = null;
    	DataInputStream dis = null;
    	String line, key, value;
    	Map<String, Object> data = new HashMap<String, Object>();
    	
		try {
			is = LoadConfigListener.class.getResourceAsStream(filename);
			
			if(is==null){
				System.out.println("Fichier de configuration non trouvé");
			} else {
				dis = new DataInputStream(is);
				
				line = dis.readLine();
				// On parcourt le fichier ligne par ligne
				while(line!=null){
					line = line.trim();
					if(line.length()!=0 && line.charAt(0)!='#'){
						key = line.split("=")[0].trim();
						value = line.split("=")[1].trim();
						data.put(key, value);
						//System.out.println("key : "+key+", value : "+Config.params.get(key));						
					}
					line = dis.readLine();
				}
				//System.out.println("Fichier trouvé");
				dis.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Fichier de configuration non trouvé");
		} catch (IOException e) {
			System.out.println("Problème lors de la lecture du fichier de configuration");
		}
		
		return data;
	}

	@Override
	public boolean write(Map<String, Object> data) {
		throw new RuntimeException("Not implemented yet");
	}

}
