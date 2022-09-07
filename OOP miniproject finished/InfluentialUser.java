import java.io.IOException;


public class InfluentialUser extends User{
    
    int influence; 
    
    public InfluentialUser(String name, String password, int funds) throws IOException{
        super(name, password, funds);
        influence = 20;
    }

    public InfluentialUser(String name, String password ) throws IOException{
        super(name, password);
        influence = 20;
    }



    public boolean buyStock(StockOptions stock, int amount) throws IOException {
        boolean bought = super.buyStock(stock, amount); 
        if (bought){
             stock.changeInfluence(influence);
         }
         return bought;
    }



    public boolean sellStock(StockOptions stock, int amount) throws IOException {
        boolean sold = super.sellStock(stock, amount); 
        if (sold){
             stock.changeInfluence(-influence);
         }
         return sold;
    }

    public void addInfluence(StockOptions stock){
        stock.changeInfluence(influence);

    }

    public void substractInfluence(StockOptions stock) {
        stock.changeInfluence(-influence);

    }

}
