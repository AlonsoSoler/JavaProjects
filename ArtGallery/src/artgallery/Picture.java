/**
    @author Rubén Matías Alonso Soler
    Last modification: 5 June 2020
 */
package artgallery;

import java.io.Serializable;
import java.util.Scanner;
/**
 *
 * @author Rubén Matías Alonso Soler
 */
public class Picture implements Serializable {
    private int code;
    private String title;
    private String author;
    private int year;
    private float width;
    private float height;
    private int units;
    private float price;
    public static Scanner stdin = new Scanner(System.in);
    
    public Picture(){
        code=0;
        title="";
        author="";
        year=0;
        width=0F;
        height=0F;
        units=0;
        price=0F;
    }
    
     public Picture(int code, String title, String author, int year, float width, float height, int units, float price){
        this.setCode(code);
        this.setTitle(title);
        this.setAuthor(author);
        this.setYear(year);
        this.setWidth(width);
        this.setHeight(height);
        this.setUnits(units);
        this.setPrice(price);
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * @return the units
     */
    public int getUnits() {
        return units;
    }

    /**
     * @param units the units to set
     */
    public void setUnits(int units) {
        this.units = units;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }
   
}

