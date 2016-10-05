package fr.gemao;

import java.util.HashMap;
import java.util.Map;

public class Config {
	public static final String
		MOT_DE_PASSE = "db-passwd",
		DOSSIER_RACINE = "root-folder",
		DEFAULT_PASSWORD_SIZE = "default-password-size";
			
	public static Map<String, String> params;
	
	static{
		params = new HashMap<String, String>();
	}
	
}
