package fr.gemao.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import fr.gemao.Config;


/**
 * Classe s'occupant du cryptage des mots de passe
 * @author Benoît
 *
 */
public class Password {
	private static String SALT = "!8Z$";
	/**
	 * Méthode cryptant la chaîne passée en paramètre
	 * @param chaine : objet String non crypté, non nul
	 * @return un objet String contenant la chaîne cryptée (32 caractères)
	 */
	public static String encrypt(String chaine){
		String res = Password.md5(chaine);
		res += SALT;
		res = Password.md5(res);
		return res;
	}
	
	public static void main(String[] args) {
		System.out.println(encrypt("lapin"));
	}
	
	private static String md5(String chaine){
		if(chaine==null)
			throw new IllegalArgumentException("Can't encrypt a null string");
		
		MessageDigest m;
		String hashtext=null;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(chaine.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hashtext;
	}
	
	/**
	 * Méthode comparant deux chaines
	 * @param chaineClair : une chaîne non codée, non nulle
	 * @param chaineCryptee : une chaîne codée
	 * @return true si les deux chaînes sont identiques une fois la
	 * deuxième décodée
	 */
	public static boolean compare(String chaineClair, String chaineCryptee){
		if(chaineClair==null && chaineCryptee==null){
			return true;
		}
		
		if(chaineClair==null)
			return false;
		
		String encryptedString = encrypt(chaineClair);
		
		return encryptedString.equals(chaineCryptee);
	}
	
	/**
	 * Méthode générant un mot de passe aléatoire de
	 * 12 caractères contenant minuscules, majuscules
	 * et chiffres
	 * @return
	 */
	public static String generatePassword(){
		return generatePassword(Integer.parseInt(Config.params.get(Config.DEFAULT_PASSWORD_SIZE)));
	}
	
	public static String generatePassword(int SIZE){
		String res = "";
		char character;
		
		if(SIZE<1){
			throw new IllegalArgumentException("La taille du mot de passe doit être strictement positive");
		}
		
		for(int i=0;i<SIZE;i++){
			if(i<SIZE/3){
				character = (char)(Math.random()*26 + 'A');
			}
			else if(i<SIZE*2/3){
				character = (char)(Math.random()*26 + 'a');
			} else {
				character = (char)(Math.random()*10 + '0');
			}
			res += character;
		}
		
		return res;
	}
}
