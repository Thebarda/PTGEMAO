package fr.gemao.listeners;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import fr.gemao.Config;
import fr.gemao.fileparser.SimpleFileParser;

/**
 * Application Lifecycle Listener implementation class LoadConfigListener
 *
 */
@WebListener
public class LoadConfigListener implements ServletContextListener {

	/**
	 * Default constructor. 
	 */
	public LoadConfigListener() {
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0)  { 
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent contextEvent)  {
		// Récupération des données du fichier
		Map<String, Object> filedata = new SimpleFileParser("../config.txt").read();

		// Transformation de la Map<String, Object> en Map<String, String>
		Map<String, String> res = new HashMap<String, String>();
		for(String key : filedata.keySet()){
			res.put(key, (String)filedata.get(key));
		}

		// Ajout dans la Map contenue dans la classe Config
		Config.params.putAll(res);
	}

}
