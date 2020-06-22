package it.polito.tdp.food.model;

public class Relazioni implements Comparable<Relazioni>{
		private String code;
		private Food f1;
		private Food f2;
		private double cal;
		public Relazioni(Food f1, Food f2, double cal) {
			super();
			code = f1.getFood_code()+"-"+f2.getFood_code();
			this.f1 = f1;
			this.f2 = f2;
			this.cal = cal;
		}
		public Food getF1() {
			return f1;
		}
		public void setF1(Food f1) {
			this.f1 = f1;
		}
		public Food getF2() {
			return f2;
		}
		public void setF2(Food f2) {
			this.f2 = f2;
		}
		public double getCal() {
			return cal;
		}
		public void setCal(double cal) {
			this.cal = cal;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((code == null) ? 0 : code.hashCode());
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
			Relazioni other = (Relazioni) obj;
			if (code == null) {
				if (other.code != null)
					return false;
			} else if (!code.equals(other.code))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "Relazioni [f1=" + f1 + ", f2=" + f2 + ", cal=" + cal + "]";
		}
		@Override
		public int compareTo(Relazioni o) {
			if (this.cal<o.getCal())
				return 1;
			if (this.cal>o.getCal())
				return -1;
			else return -(this.f1.compareTo(o.getF1()));
			
		}
		
}
