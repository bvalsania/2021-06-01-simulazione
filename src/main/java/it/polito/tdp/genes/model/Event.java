package it.polito.tdp.genes.model;

public class Event implements Comparable<Event> {
	
	
		private int mese;
		private int ing;
		
		@Override
		public int compareTo(Event o) {
			// TODO Auto-generated method stub
			return this.mese-o.mese;
		}

		public Event(int mese, int ing) {
			super();
			this.mese = mese;
			this.ing = ing;
		}

		public int getMese() {
			return mese;
		}

		public void setMese(int mese) {
			this.mese = mese;
		}


		public int getIng() {
			return ing;
		}

		public void setIng(int ing) {
			this.ing = ing;
		}
		
		
}
