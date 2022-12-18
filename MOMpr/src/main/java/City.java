

public class City {
	private int id;
    private String name;
    private String numberPeople;
    
    public City(){ }
    public City(int id,String name,String numberPeople){
        this.id=id; 
        this.name = name;
        this.numberPeople = numberPeople;
        
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String numberPeople() {
        return numberPeople;
    }
    public void setID(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setnumberPeople(String numberPeople) {
        this.name = numberPeople;
    }

}
