
import data_structures.*;
import java.util.Iterator;

public class ProductLookup {
    DictionaryADT<String,StockItem> products;
   
    // Constructor.  There is no argument-less constructor, or default size
    public ProductLookup(int maxSize){
        products = new Hashtable<String,StockItem>(maxSize);
    }
       
     /**
      * Adds a new StockItem to the dictionary
      * @param SKU String
      * @param item StockItem
      */
    public void addItem(String SKU, StockItem item){
        products.insert(SKU, item);
    }
           
    // Returns the StockItem associated with the given SKU, if it is
    // in the ProductLookup, null if it is not.
    public StockItem getItem(String SKU){
        return products.getValue(SKU);
    }
       
    // Returns the retail price associated with the given SKU value.
    // -.01 if the item is not in the dictionary
    public float getRetail(String SKU){
        StockItem si = getItem(SKU);
        if ( si == null ) return -.01f;
        return si.getRetail();
    }
    
    // Returns the cost price associated with the given SKU value.
    // -.01 if the item is not in the dictionary
    public float getCost(String SKU){
        StockItem si = getItem(SKU);
        if ( si == null ) return -.01f;
        return si.getCost();
    }
    
    // Returns the description of the item, null if not in the dictionary.
    public String getDescription(String SKU){
        StockItem si = getItem(SKU);
        if ( si == null ) return null;
        return si.getDescription();
    }
       
    // Deletes the StockItem associated with the SKU if it is
    // in the ProductLookup.  Returns true if it was found and
    // deleted, otherwise false.  
    public boolean deleteItem(String SKU){
        return products.remove(SKU);
    }
       
    // Prints a directory of all StockItems with their associated
    // price, in sorted order (ordered by SKU).
    public void printAll(){
        Iterator<StockItem> itemIter = values();
        StockItem si = null;
        while ( itemIter.hasNext() ){
            si = itemIter.next();
            System.out.println(si.getSKU() + " " + si.getCost() );
        }
    }
    
    // Prints a directory of all StockItems from the given vendor, 
    // in sorted order (ordered by SKU).
    public void print(String vendor){
        Iterator<StockItem> it = values();
        StockItem si = null;
        System.out.println(vendor + ":");
        while ( it.hasNext() ){
            si = (StockItem) it.next();
            if ( si.getVendor().compareTo(vendor) == 0 )
                System.out.println(si.getSKU() + " " + si.getCost() 
                        + " " + si.getDescription());
        }
    }
    
    // An iterator of the SKU keys.
    public Iterator<String> keys(){
        return products.keys();
    }
    
    // An iterator of the StockItem values.    
    public Iterator<StockItem> values(){
        return products.values();
    }
}