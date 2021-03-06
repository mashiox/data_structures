/**
 * Matt Walther
 * 0211
 */

import java.util.Iterator;
import data_structures.*;

public class StockItem implements Comparable<StockItem> {
    String SKU;
    String description;
    String vendor;
    float cost;
    float retail;
   
     /**
      * Constructor.  Creates a new StockItem instance.  
      * @param SKU String
      * @param description String
      * @param vendor String
      * @param cost float
      * @param retail float
      */
    public StockItem(String SKU, String description, String vendor,
                     float cost, float retail){
        this.SKU = SKU;
        this.description = description;
        this.vendor = vendor;
        this.cost = cost;
        this.retail = retail;
    }
    
     /**
      * Follows the specifications of the Comparable Interface.
      * The SKU is always used for comparisons, in dictionary order.  
      * @param n StockItem
      * @return int
      */
    public int compareTo(StockItem n){
        return SKU.compareTo(n.SKU);
    }
       
     /**
      * Returns an int representing the hashCode of the SKU.
      * @return int
      */
    public int hashCode(){
        return SKU.hashCode();
    }
       
    /**
     * Returns SKU Value
     * @return String
     */
    public String getSKU(){
        return SKU;
    }
    
    /**
     * Returns description Value
     * @return String
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Returns Vendor Value
     * @return String
     */
    public String getVendor(){
        return vendor;
    }
    
    /**
     * Returns cost Value
     * @return float
     */
    public float getCost(){
        return cost;
    }
    
    /**
     * Returns retail Value
     * @return float
     */
    public float getRetail(){
        return retail;
    }
        
     /**
      * All fields in one line, in order   
      * @return String
      */
    public String toString(){
        return SKU + " " + description + " " + vendor + " " + 
                cost + " " + retail;
    }
}          