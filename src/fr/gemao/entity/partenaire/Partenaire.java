package fr.gemao.entity.partenaire;

public class Partenaire {
	public final int id;
	
	public Partenaire(int id){
		this.id=id;
	}
	
	public Partenaire(){
		this(0);
	}

	public int getId() {
		return id;
	}
}
