

public interface ReturningStockAddons {

    int day = 50;

    int month = 1500;

    int quarter = 4500;

    int twoQuarters= 9000;

    int threeQuarters=13500;

    int year = 18000;


    public void doreturns(); //when called should add funds to each user

    public int calculateReturnPerStock(); //when called should return the value of each stock


    
}
