

/**
@author Rubén Matías Alonso Soler
Última modificación: 5 de junio de 2020
*/

package agenda;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;


public class Contacto {
private static final int CARACTERES_PALABRA = 25;
private static final int TAMANO_TOTAL_OBJETO = 35;
private static int autonumerico = 0;
private int codigo;
private String nombre;
private int edad;
private static Scanner stdin = new Scanner(System.in);

public Contacto(String nombre, int edad){
this.codigo=autonumerico+1;
autonumerico++;
this.nombre=nombre;
this.edad=edad;
}

public String toString(){
return "Código: "+getCodigo()+
        ". Nombre: "+getNombre()+". Edad: "+getEdad();
}

public static int getAutonumerico() {
    return autonumerico;
}

/**
 * @param aAutonumerico the autonumerico to set
 */
public static void setAutonumerico(int aAutonumerico) {
    autonumerico = aAutonumerico;
}
/**
 * @return the codigo
 */
public int getCodigo() {
    return codigo;
}


/**
 * @return the nombre
 */
public String getNombre() {
    return nombre;
}

/**
 * @return the edad
 */
public int getEdad() {
    return edad;
}

/**
 * @param codigo the codigo to set
 */
public void setCodigo(int codigo) {
    this.codigo = codigo;
}


/**
 * @param nombre the nombre to set
 */
public void setNombre(String nombre) {
    this.nombre=comprobarNombre(nombre);
    
     
}

/**
 * @param edad the edad to set
 */
public void setEdad(int edad) {
    this.edad = edad;
}
/**
    Función para mostrar el menú por consola
*/
public static int menu(){
   
     int opcion;

    do {
        System.out.println ("\n1. Añadir Contacto.");
        System.out.println ("2. Consultar Contacto.");
        System.out.println ("3. Modificar Contacto");
        System.out.println ("4. Borrar Contacto.");
        System.out.println ("5. Listar agenda.");
        System.out.println ("6. Salir.");
        System.out.println ("\nIntroduce opción (1...6): ");
        opcion = stdin.nextInt();
        stdin.nextLine();

    }while (opcion < 1 || opcion > 6);
    
    return opcion;
    
}
/**
    Función que extrae los caracteres del nombre y comprueba si la longitud
    de este es mayor o menor a la longitud deseada. Según sea la longitud de la palabra
    se procede a su modificación para que todos los nombres tengan el mismo tamaño
*/
public static String comprobarNombre(String nombre){
    
    int longitud = nombre.length();
    
    char[] caracteres = new char[longitud];
    
    int posicion;
    
    for (posicion=0;posicion<caracteres.length;posicion++){
        
        caracteres[posicion]=nombre.charAt(posicion);
        
    }
    
    
    if (longitud >= CARACTERES_PALABRA)
        
            nombre=crearNombreMenor(caracteres);
    
    else
            nombre=crearNombreMayor(caracteres,nombre);
        
    return nombre;
}

 /**
    Esta función crea el nombre que se registrará cuando este sea igual a la
    longitud máxima permitida o la supere
*/
public static String crearNombreMenor(char[] caracteres){
    
     char[] caracteresNuevaPalabra = new char[CARACTERES_PALABRA];
        
     String nuevaPalabra="";
     
        for (int i=0;i<caracteresNuevaPalabra.length;i++){
            
            caracteresNuevaPalabra[i]=caracteres[i];
            
            nuevaPalabra=nuevaPalabra+caracteresNuevaPalabra[i];
        }   
        
        return nuevaPalabra;
    }

/**
    Esta función crea el nombre que se registrará cuando este no alcance
    la longitud máxima permitida
*/
public static String crearNombreMayor(char[] caracteres, String nombre){

    String nuevaPalabra = nombre;
        
        for (int i=caracteres.length;i<CARACTERES_PALABRA;i++){
            
            nuevaPalabra=nuevaPalabra+'_';
        }
    
    return nuevaPalabra;
}    

/**
    Función que pide los datos del contacto para crear el objeto y posteriormente
    lo registra en el archivo
*/
public static void anadirContacto(RandomAccessFile raf){
    
    System.out.println("Introduzca nombre:" );
    
    String nombreInput = stdin.nextLine();
        
        nombreInput=comprobarNombre(nombreInput);
    
    System.out.println("Introduzca edad: ");
    
        int edadInput = stdin.nextInt();
   
    try{
        
    if (raf.length()>0)
        
        autonumerico=localizarCodigo(raf);
                   
    Contacto contacto = new Contacto(nombreInput,edadInput);
     
    contacto.escribirObjecto(raf);
    
    
    
    }
    
    catch(IOException e){
        
        System.out.println(e.getMessage());
        
    }
}

/**
    Esta función compueba el código autonumerado para que cuando el usuario salga 
    de la aplicación y vuelva a entrar en ella el código del nuevo contacto
    no empiece en 1 en el caso de que ya existan más contactos registrados
    en el fichero. 
    Concretamente, el puntero se dirige al último registro creado y devuelve su código 
    para que el valor de este sea también el valor de la variable autonumérico
*/
public static int localizarCodigo(RandomAccessFile raf){
      
    int codigoRegistro=0;
    
             try{
                     int puntero =(int) raf.length()-TAMANO_TOTAL_OBJETO;
                    
                     raf.seek(puntero);
                     
                     codigoRegistro = raf.readInt();
                     
                }
                
             
             catch(IOException e){
                 System.out.println(e.getMessage());
             }
                
        return codigoRegistro;
}
/**
    Esta función pide el código y el nombre del contacto que se quiere buscar
    y posteriormente recorre el archivo controlando la posición del puntero
    hasta que encuentra el código y el nombre introducidos o hasta que 
    llegua al final del archivo
*/
public static void consultarContacto(RandomAccessFile raf){
    
    System.out.println("Introduzca el código del contacto");
    
        int codigo = stdin.nextInt();
    
    System.out.println("Introduzca el nombre del contacto");
    
        stdin.nextLine();
        String nombre = stdin.nextLine();
        
        nombre = comprobarNombre(nombre);
        
        boolean encontrado = false;
        
        boolean fin = false; 
            
            for (int i=0;fin==false && encontrado==false;i++){
            
                try{
                    int puntero = i*TAMANO_TOTAL_OBJETO;
                    
                    raf.seek(puntero);
                
                    int codigoArchivo = raf.readInt();
                
                    String nombreArchivo = raf.readUTF();
                
                    int edad = raf.readInt();
                        
                        if (codigo == codigoArchivo && nombre.equalsIgnoreCase(nombreArchivo)){
                       
                            encontrado=true;
                    
                            System.out.println("Código: "+codigoArchivo+". Nombre: "+nombreArchivo+". Edad: "+edad);
                        }
                    }
                
                    catch(EOFException e){
                        fin = true;
                    }
                
                    catch(IOException e){
                        System.out.println(e.getMessage());
                    }
             
            }

    
            if(encontrado==false)
        
                System.out.println("El contacto buscado no está en la agenda.");
    
}

/**
    Esta función pide el código del contacto que se quiere modificar
    y posteriormente recorre el archivo controlando la posición del puntero
    hasta que encuentra el código introducido o hasta que llegua al final del archivo. 
    Cuando encuentra el contacto se solicitan los nuevos datos 
    que sobreescriben los datos del contacto encontrado.
*/

public static void modificarContacto(RandomAccessFile raf){
    
    System.out.println("Introduzca el código del contacto que desea modificar");
    
        int codigo = stdin.nextInt();
    
        boolean encontrado = false;
    
        boolean fin = false; 
            
            for (int i=0;fin==false && encontrado==false;i++){
            
                try{
                    int puntero = i*TAMANO_TOTAL_OBJETO;
                    
                    raf.seek(puntero);
                
                    int codigoArchivo = raf.readInt();
                        
                        if (codigo == codigoArchivo){
                       
                            encontrado=true;
                    
                            System.out.println("Introduzca nuevo nombre");
                             
                            stdin.nextLine();
                                
                            String nombre = stdin.nextLine();
        
                            nombre=comprobarNombre(nombre);
                            
                            raf.writeUTF(nombre);
                                
                            System.out.println("Introduzca nueva edad");
                                 
                            raf.writeInt(stdin.nextInt());
                            
                            System.out.println("Modificación realizada con éxito.");
                        }
                    }
                
                    catch(EOFException e){
                        fin = true;
                    }
                
                    catch(IOException e){
                        System.out.println(e.getMessage());
                    }
             
            }
              
    if (encontrado==false)
        
       System.out.println("El contacto buscado no está en la agenda.");
    
}

 /**
    Esta función pide el código del contacto que se quiere borrar
    y posteriormente recorre el archivo controlando la posición del puntero
    hasta que encuentra el código introducido o hasta que 
    llegua al final del archivo. Cuando encuentra el contacto este se sobreescribe
    de manera que su nombre es una cadena vacía y su edad es 0
*/
public static void borrarContacto(RandomAccessFile raf){
    
    System.out.println("Introduzca el código del contacto que desea borrar");
    
            int codigo = stdin.nextInt();
    
            boolean encontrado = false;
    
            boolean fin = false; 
            
            for (int i=0;fin==false && encontrado==false;i++){
            
                try{
                    int puntero = i*TAMANO_TOTAL_OBJETO;
                    
                    raf.seek(puntero);
                
                    int codigoArchivo = raf.readInt();
                    
                        if (codigo == codigoArchivo){
                            
                            encontrado=true;
                                                           
                            raf.writeUTF("                         ");
                                 
                            raf.writeInt(0);
                            
                            System.out.println("Borrado realizado con éxito.");
                                
                        }
                    }
                
                    catch(EOFException e){
                        fin = true;
                    }
                
                    catch(IOException e){
                        System.out.println(e.getMessage());
                    }
            }
            
        if (encontrado==false)
        
            System.out.println("El contacto buscado no está en la agenda.");
}

/**
    Función donde el puntero se sitúa al final del fichero y luego se registran
    en él los datos del contacto.
*/
public void escribirObjecto(RandomAccessFile raf){

             try{
                 
                 raf.seek(raf.length());
                 
                 raf.writeInt(codigo);
                 
                 raf.writeUTF(nombre);
                 
                 raf.writeInt(edad);
                 
             }
             
             catch (IOException e){
                 System.out.println(e.getMessage());
             }
         }

 /**
    Función que se encarga de listar los datos del fichero 
 */ 
public static void leerObjeto(RandomAccessFile raf){
    
             boolean fin=false;
             
             System.out.println("Lista de contactos: ");
             while (fin!=true){
             try{
                 
                int codigo = raf.readInt();
                
                String nombre = raf.readUTF();
                
                int edad = raf.readInt();
                 
                 System.out.println("Código: "+codigo+". Nombre: "+nombre+". Edad: "+edad);
             }
             catch(EOFException e){
                 fin = true;
             }
             catch(IOException e){
                 System.out.println(e.getMessage());
             }
             
         }
    }

/**
    En el main se abre el fichero y se activa el menú. Según la opción que
    elija el usuario se llevará a cabo una función diferente
*/
public static void main(String[] args) {
    // TODO code application logic here
    
     RandomAccessFile raf=null;
     
     try{
         
        raf = new RandomAccessFile("agenda","rw");
     
     }
     
     catch(FileNotFoundException e){
         
         System.out.println(e.getMessage());
         
     }
     
     int opcion;
    
     while ( (opcion = menu ()) != 7)
     {
          switch (opcion) {
                 case 1: anadirContacto(raf);
                         break;
                 case 2: consultarContacto(raf);
                         break;
                 case 3: modificarContacto(raf);
                         break;
                 case 4: borrarContacto(raf);
                         break;
                 case 5: 
                        try{
                            raf.seek(0);
                            leerObjeto(raf);
                            }
                        
                        catch(IOException e){
                                System.out.println(e.getMessage());
                            }   
                        break;
                        
                 case 6:  
                        try{
                            raf.close();
                            System.exit(0);   
                            }
                        
                        catch(IOException e){
                                System.out.println(e.getMessage());
                            }   
                 
                }
                     
            }               
                                    
    }
  
}
