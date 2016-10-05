package fr.gemao.entity;

import java.io.Serializable;
import java.util.Date;

public class Parametre implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long idParam;
	private float alloc2;
	private float alloc3;
	private float alloc4;
	private float alloc5;
	private float qf_min;
	private float qf_max;
	private Date dateModif;
	
	public Parametre() {
	}
	
	public Parametre(long idParam, float alloc2, float alloc3, float alloc4,
			float alloc5, float qf_min, float qf_max, Date dateModif) {
		this.idParam = idParam;
		this.alloc2 = alloc2;
		this.alloc3 = alloc3;
		this.alloc4 = alloc4;
		this.alloc5 = alloc5;
		this.qf_min = qf_min;
		this.qf_max = qf_max;
		this.dateModif = dateModif;
	}
	
	public Parametre(Parametre param){
		this.idParam = param.getIdParam();
		this.alloc2 = param.getAlloc2();
		this.alloc3 = param.getAlloc3();
		this.alloc4 = param.getAlloc4();
		this.alloc5 = param.getAlloc5();
		this.qf_min = param.getQf_min();
		this.qf_max = param.getQf_max();
		this.dateModif = param.getDateModif();
	}

	public long getIdParam() {
		return idParam;
	}


	public void setIdParam(long idParam) {
		this.idParam = idParam;
	}


	public float getAlloc2() {
		return alloc2;
	}


	public void setAlloc2(float alloc2) {
		this.alloc2 = alloc2;
	}


	public float getAlloc3() {
		return alloc3;
	}


	public void setAlloc3(float alloc3) {
		this.alloc3 = alloc3;
	}


	public float getAlloc4() {
		return alloc4;
	}


	public void setAlloc4(float alloc4) {
		this.alloc4 = alloc4;
	}


	public float getAlloc5() {
		return alloc5;
	}


	public void setAlloc5(float alloc5) {
		this.alloc5 = alloc5;
	}


	public float getQf_min() {
		return qf_min;
	}


	public void setQf_min(float qf_min) {
		this.qf_min = qf_min;
	}


	public float getQf_max() {
		return qf_max;
	}


	public void setQf_max(float qf_max) {
		this.qf_max = qf_max;
	}


	public Date getDateModif() {
		return dateModif;
	}


	public void setDateModif(Date dateModif) {
		this.dateModif = dateModif;
	}

	@Override
	public String toString() {
		return "Parametre [idParam=" + idParam + ", alloc2=" + alloc2
				+ ", alloc3=" + alloc3 + ", alloc4=" + alloc4 + ", alloc5="
				+ alloc5 + ", qf_min=" + qf_min + ", qf_max=" + qf_max
				+ ", dateModif=" + dateModif + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(alloc2);
		result = prime * result + Float.floatToIntBits(alloc3);
		result = prime * result + Float.floatToIntBits(alloc4);
		result = prime * result + Float.floatToIntBits(alloc5);
		result = prime * result + (int) (idParam ^ (idParam >>> 32));
		result = prime * result + Float.floatToIntBits(qf_max);
		result = prime * result + Float.floatToIntBits(qf_min);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parametre other = (Parametre) obj;
		if (Float.floatToIntBits(alloc2) != Float.floatToIntBits(other.alloc2))
			return false;
		if (Float.floatToIntBits(alloc3) != Float.floatToIntBits(other.alloc3))
			return false;
		if (Float.floatToIntBits(alloc4) != Float.floatToIntBits(other.alloc4))
			return false;
		if (Float.floatToIntBits(alloc5) != Float.floatToIntBits(other.alloc5))
			return false;
		if (Float.floatToIntBits(qf_max) != Float.floatToIntBits(other.qf_max))
			return false;
		if (Float.floatToIntBits(qf_min) != Float.floatToIntBits(other.qf_min))
			return false;
		return true;
	}
	
}
