package maintain;

public class Maintenance {

    private String details;
    private int totalCost;
    private int hourlyCost;

    public Maintenance() {

    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getTotalCost(){
        return totalCost;
    }

    public void setTotalCost(int totalCost){
        this.totalCost = totalCost;
    }

    public int getHourlyCost(){
        return hourlyCost;
    }

    public void setHourlyCost(int hourlyCost){
        this.hourlyCost = hourlyCost;
    }
}