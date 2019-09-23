import java.io.BufferedWriter;//Escribe la informacion en el archivo o sea lo graba en el disco duro.
import java.io.File;//Comprueva si existe el archivo.
import java.io.FileWriter;//Indica el archivo en el que se va a escribir.
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.awt.EventQueue;
public class archivo{
    String dire = null;
    String last = "recientes.txt";
    //private ParStr [] recientes;
    String proyectName = "";
    
    public String fontSelected = "";
    public int sizeSelected = 8;
    public Color colorSelected = null;
    public float spaceSelected = 1;
    
    public 
    data = null;
    
    public archivo(){
             
    }  
    public archivo(String direccion){
        dire = direccion;
    }
    void save(String direccion, String data){//aca se pondra el parseo del string
        saveString(direccion, data);
    }
    String load(String direccion){//aca se pondra el parseo del string
        return loadString(direccion);
    }

   /* ParStr [] strToParStr(String []linea, int len){
        ParStr [] result = null;

        return result;
    }
    ParStr [] load_recientes(){
        ParStr [] result = null;
        try {
            File f = new File (last);
            FileReader fr = new FileReader (f);
            BufferedReader br = new BufferedReader(fr);
            String len = br.readLine();
            System.out.println("Valor de len: "+ len);
            if(len != null){
                String linea[] = new String[Integer.parseInt(len)*2];
                result = new ParStr[Integer.parseInt(len)] ;
                for(int i=0, j=0; i<linea.length && (linea[i]=br.readLine())!=null ;i++){
                    if(i%2==0){
                        result[j] = new ParStr();
                        result[j].clave = linea[i];
                    }
                    else{
                        result[j].valor = linea[i];
                        j++;
                    }    
                }
            }            
            fr.close();            
 
        }catch(Exception e){
    	  e.printStackTrace();
    	}   
        return result;
    }
    void saveRecientes(ParStr [] recientes){
        if(recientes != null){
            try{
                File  f = new File(last);
                FileWriter fw = new FileWriter(f);
                String res = "";
                res += recientes.length + "\r\n";
                for(int i=0;i < recientes.length;i++){
                    res += recientes[i].clave + "\r\n";
                    res += recientes[i].valor + "\r\n";
                }
                fw.write(res );
                BufferedWriter bw = new BufferedWriter(fw);
                bw.close();
        	}catch(Exception e){
        	    e.printStackTrace();
        	}
        }
    }*/
    //'\'
    void saveString(String direccion, String data){
        if(data != null){
            if(direccion !=null)
                dire = direccion;
            try{
                File  f = new File(dire);
                FileWriter fw = new FileWriter(f);              
                fw.write(data);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.close();
        	}catch(Exception e){
        	  e.printStackTrace();
        	}
        }           
    }

    String loadString(String direccion){
        String result = null;
        if(direccion !=null)
            dire = direccion;   
        if(dire != null){
            try {
                File f = new File (dire);
                if(f.exists()){
                    FileReader fr = new FileReader (f);
                    BufferedReader br = new BufferedReader(fr);
                    while((result+=br.readLine())!=null){}
                    fr.close();  
                }  
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
    
       

}
