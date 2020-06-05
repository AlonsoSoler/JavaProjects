/**
    @author Rubén Matías Alonso Soler
    Last modification: 5 June 2020
 */
package artgallery;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ArtGallery {
    
    private final int CAPACITY = 80;
    private int numPictures = 0;
    private LinkedList<Picture> pictures = new LinkedList<Picture>();
    private static Scanner stdin = new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
    
     /** The menu in which the user chooses an operation. */
     public static int menu ()
    {
        int option;

        do {
            System.out.println ("\n1. Entering initial data.");
            System.out.println ("\n2. Delete a picture by code.");
            System.out.println ("\n3. Show the data introduced.");
            System.out.println ("\n4. Sale of paintings.");
            System.out.println ("\n5. Number of pictures available in the gallery.");
            System.out.println ("\n6. Save gallery's data.");
            System.out.println ("\n7. Read gallery's data.");
            System.out.println ("\n8. Exit.");
            System.out.println ("\nIntroduce option (1...8): ");
            option = stdin.nextInt();
 
        }while (option < 1 || option > 8);
        return option;
    }
     
      /**
            This function is used to finish all operations every time the user
            executes an operation if the user wants to.
       */
     
      public void confirmation(){
                
            String confirmation;
            System.out.println("Do you want to finish the gallery operations? (Yes/No)");
                
            confirmation = stdin.next();
                
            if (confirmation.equalsIgnoreCase("yes")){
                    
                System.out.println("Operations cancelled.");
                System.exit(0);
                
                }
            }
         
      /**
        In this function the user introduces the data of every picture and
        sets that data in the corresponding picture object provided that 
        there are less than 80 pictures in the gallery. When a picture is
        successfully introduced, numPictures increases.
     */
      
      public void enteringPicturesData()
    {
        if (numPictures<CAPACITY){
            System.out.println ("\nIntroduce picture " + (numPictures + 1) + ": ");
            
            System.out.println ("\nIntroduce code number of the picture: ");
            int codeNumber=stdin.nextInt();            
            stdin.nextLine();
            
            System.out.println ("\nIntroduce title: ");
            String title = stdin.nextLine();
            
            System.out.println ("\nIntroduce author: ");
            String author = stdin.nextLine();
            
            System.out.println ("\nIntroduce year of the picture: ");
            int year = stdin.nextInt();
            
            System.out.println ("\nIntroduce its width: ");
            float width = stdin.nextFloat();
            
            System.out.println ("\nIntroduce its height: ");
            float height = stdin.nextFloat();
            
            System.out.println ("\nIntroduce the units of this picture: ");
            int units = stdin.nextInt();
            
            System.out.println("\nIntroduce price");
            float price = stdin.nextFloat();
            
            Picture picture = new Picture(codeNumber,title,author,year,width,height,units,price);
            
            pictures.add(picture);
            
            numPictures++;
            
        }else{
            System.out.println("The storage is plenty of pictures, remove one if you want to add more.");
        }
       confirmation();
    }  
      
      
      /**
        The function checks if there is any picture in the gallery and if that is the case
        the user is asked to introduce the code of the picture that is wanted to be deleted.
      */
    public void deletePicture(){
     
        
        if (pictures.isEmpty() == true)
            
            System.out.println("Empty gallery");
            
        else{
            
            System.out.println("Introduce the code of the picture you want to delete");
                
            int code = stdin.nextInt();
        
            boolean found = false;
        
            for (Picture element: pictures){
            
                if (code == element.getCode()){
                
                    found = true;
                
                    pictures.remove(element);
                    
                    System.out.println("Picture deleted");
                
                    numPictures--;
                
                    break;
                }
            }
        
        if (found == false){
            
            System.out.println("The picture couldn't be found.");
        }
        
    }
    confirmation();   
        
}
    
    /**
       This function first of all checks if there is any picture and if that is the case
        the data of all the pictures in the gallery will be shown.
    */
    public void listPictures(){
        
        if (pictures.isEmpty()==true) {   
            
                System.out.println("Empty gallery.");
                
         }
        
         else{
         
            Iterator <Picture> i = pictures.iterator();
         
            while (i.hasNext())
            {
             Picture picture = i.next();
             
             System.out.println("Data of the picture with title "+picture.getTitle()+": \n");
                
                 System.out.println("Width: "+picture.getWidth());
                 System.out.println("Height: "+picture.getHeight());
                 System.out.println("Units: "+picture.getUnits());
                 System.out.println("Price: "+picture.getPrice());
                 System.out.println("");
                 
            }
         }
       confirmation();
    }
    
    /**
       Like the last functions, first checks if there is any picture in the gallery.
        If there is one at least, the program asks to the user the code of the painting
        that is wanted to buy and if that code exists, the data of that painting will be shown
        and after that the program asks to the user the units that will be bought, doing the 
        calculus needed to get the total price of the trading.
    */
    
    public void salePictures()
      {
      
          int code,ind = 0,units;
          boolean found=false;
          float money;
          
        if (pictures.isEmpty()==true) {   
          
                System.out.println("Empty gallery.");
                
            }
        
        else{
            
          System.out.println(" Introduce the code of the painting you want to buy:");
          
          code = stdin.nextInt();
          
          for (Picture picture : pictures){
              
             if (picture.getCode() == code){
                 
                 found=true;
                 
                 ind=pictures.indexOf(picture);
                } 
          }
              
              if (found==false)
                   System.out.println("The code introduced has not been found.");
          
          
              if (found==true){
                  
                 System.out.println("The code introduced belongs to the picture "+pictures.get(ind).getTitle()+
                 " and costs "+pictures.get(ind).getPrice()+" euros. There are "+pictures.get(ind).getUnits()+" units available of this picture.");
                 
                 System.out.println("Introduce the number of paintings you want to buy:");
                    
                    units=stdin.nextInt();
                    
                    if (units<=pictures.get(ind).getUnits()){
                        
                        money=pictures.get(ind).getPrice()*units;
                        System.out.println("The sale will cost "+money+" euros.");
                        
                        pictures.get(ind).setUnits(pictures.get(ind).getUnits()-units);
                        
                        System.out.println("The sale has been successfull. Now there are "+pictures.get(ind).getUnits()+" avaible pictures of this type in the gallery.");
                        
                    }
                        else{
                        
                        System.out.println("There are not enough units of this picture in the gallery.");
                        
                    }
              }
           }
        confirmation();
      }
    /**
        Function that stores the LinkedList data in a file.
    */
    public void saveGallery(){
        
        try{
            
            ObjectOutputStream outputStreamGallery = new ObjectOutputStream(new FileOutputStream("fichero"));
        
            outputStreamGallery.writeObject(pictures);
            
            System.out.println("Copy successful.");
        
            outputStreamGallery.close();
        
            }
        
            catch (IOException e){
            
                System.out.println("IOException");
            
            }
        
    }
    
    /**
        The following function catch the LinkedList object from the file and shows its content.
    */
    public void readGallery() {
        
        try{
            
        ObjectInputStream inputStreamGallery = new ObjectInputStream(new FileInputStream("fichero"));
        
        LinkedList<Picture> list = (LinkedList<Picture>) inputStreamGallery.readObject();
        
   
            for(Picture picture : list){
               
                System.out.println("Code: "+picture.getCode());
                System.out.println("Title: "+picture.getTitle());
                System.out.println("Author: "+picture.getAuthor());
                System.out.println("Year: "+picture.getYear());
                System.out.println("Width: "+picture.getWidth());
                System.out.println("Height: "+picture.getHeight());
                System.out.println("Units: "+picture.getUnits());
                System.out.println("Price: "+picture.getPrice());
                System.out.println("");
            }
            
        inputStreamGallery.close();
        
        }
        
        catch (ClassNotFoundException e){
            System.out.println("Data not found.");
        }       
        
        catch (IOException e){
            
            System.out.println("IOException");
            
        }
        
    }
     
    
    /**
        In the main method an ArtGallery object is created and throws the menu options.
    */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int option;
        
        ArtGallery gallery = new ArtGallery();
        
           
         while ( (option = menu ()) != 8)
        {
              switch (option) {
                     case 1: gallery.enteringPicturesData();
                             break;
                     case 2: gallery.deletePicture();
                             break;
                     case 3: gallery.listPictures();
                             break;
                     case 4: gallery.salePictures();
                             break;
                     case 5: System.out.println("\nNumber of pictures available:"+gallery.numPictures+"\n");
                             break;
                     case 6: gallery.saveGallery();
                             break;
                     case 7: gallery.readGallery();
                             break;
                    
              }
        }
    }
    
}
