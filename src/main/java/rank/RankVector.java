package rank;

public class RankVector {
	Float brinRank;
	Float ourRank;
	
	public RankVector(Float brinRank, Float ourRank){
		this.brinRank = brinRank;
		this.ourRank = ourRank;
	}

	public Float getBrinRank() {
		return brinRank;
	}

	public void setBrinRank(Float brinRank) {
		this.brinRank = brinRank;
	}

	public Float getOurRank() {
		return ourRank;
	}

	public void setOurRank(Float ourRank) {
		this.ourRank = ourRank;
	}
	
	@Override
	public String toString(){
		String s = "BRIN RANK: " + brinRank + "  OUR RANK: " + ourRank;
		return s;
		
	}
	

}
