import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import javax.swing.text.StyleConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.BadLocationException;
class  Lienzo {
    JPanel canvas = new JPanel();
   // JTextField uno, dos, tres;
    JTextPane edit = new JTextPane();
    JScrollPane scroll = new JScrollPane(edit);

    public String fontSelected = "TimesRoman";
    public int sizeSelected ;
    public Color colorSelected = null;
    public float spaceSelected = 1;
    //-----------------Flags TODO eliminar  flags , por  paramettro de funcion changeText(char...)--------------------
    /*public boolean FONT = false;
    public boolean SIZE = false; 
    public boolean NEGRITA = false;
    public boolean CURSIVA = false;
    public boolean SUBRAYAR = false;        
    public boolean TACHAR = false;        
    public boolean COLOR_LETRA = false;        
    public boolean COLOR_FONDO = false;*/
    public String prop = "";

    Fuente [] t = emptyFormatos();
    private int IND_T=0; 
    Fuente [] aux;
    private int IND_AUX=0;
        
    public Lienzo(Estilo e){
        t[0].selIni = 0;
        t[0].selFin = 20;
        t[0].estilo = new Formato(false,false,false,false, new Color(255,255,255),new Color(0,0,0), fontSelected, 12);
        IND_T++;
        //--------------Inicializacion de atributos----------------
        fontSelected = e.textoFontStr;
        sizeSelected = e.sizeFont;
        colorSelected = e.textoColor;
        spaceSelected = e.margin_text_Above;
    //------------------------------
        canvas.setLayout(null);
        canvas.setBounds(e.marginLienzoX, e.marginLienzoY, e.widthLienzo, e.heightLienzo);
        canvas.setBackground(e.colorLienzo);
        canvas.setVisible(true); 
        /*uno  = new JTextField("uno");
        dos  = new JTextField("dos");
        tres = new JTextField("tres");
        uno.setBounds(10,30,200,30);
        dos.setBounds(10,60,200,30);
        tres.setBounds(10,90,200,30);
        canvas.add(uno);
        canvas.add(dos);
        canvas.add(tres);*/
        edit.setBounds(10,30,e.widthLienzo-200,e.heightLienzo-100);
        scroll.setBounds(10,30,e.widthLienzo-200,e.heightLienzo-100);
        //canvas.add(edit);
        canvas.add(scroll);
        
    }
    JPanel lienzo(){
        return canvas;
    }
    void changeText(int startsSel,int endsSel){
        try{
            changeText2(startsSel,endsSel);
        }
        
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    } 

    //{"negrita","cursiva","subrayar","tachar","color","resaltar","fuente","tamano","borrar","insert","reemplazar"}
    // equivalent to
    //operacion = {'n','k','s','T','c','r','f','t','b','i','R'}
    void changeText2(int startsSel,int endsSel) throws BadLocationException{
      //obj para aplicar atributos del texto         
        SimpleAttributeSet attrs = new SimpleAttributeSet();
     /* int subgrupos = haveSubgroups()//TODO debe devolver la cantidad  + los de los  lados  
                                        //creando los subgrupos
                                        /TODO toda listta debe estar ORDENADA
       */
        Dupla sub = ifExists(startsSel,endsSel); 
        if(sub.cant_subGrupos > 0){
            Formato elem;
            for(int i = sub.index;i< sub.cant_subGrupos+sub.index;i++){
                elem = t[i].estilo;
                if(elem !=null ){/*elem.selFin<=endsSel esta condicion la he usado en el pasado*/ 
                    SimpleAttributeSet attrs2 = set_atrib(attrs,elem,prop,i);
                        System.out.println("atributo2:"+attrs2);
                        System.out.println("atributo1:"+attrs);
                        System.out.println("ini:"+startsSel+"fin:"+endsSel);
                        System.out.println("elem:"+edit.getStyledDocument().getCharacterElement(startsSel));

                    String texto = edit.getStyledDocument().getText(startsSel,endsSel-startsSel);
                        System.out.println("texto:\""+texto+"\"");
                    edit.getStyledDocument().remove(startsSel,endsSel-startsSel);     
                    edit.getStyledDocument().insertString(startsSel, texto, attrs2);
                    /* StyleConstants.setBold(attrs, true);
                        if(edit == null)
                            System.out.println("nulo edit");
                     edit.getStyledDocument().insertString(startsSel, texto, attrs);*/
                }
            }
        }   
                                       //(edit.getStyledDocument().getLength(), texto, attrs);
        check_join_groups();//unimos los grupos que sean iguales y adyacentes
    }
    void check_join_groups(){}//TODO implementar

    SimpleAttributeSet set_atrib(SimpleAttributeSet attrs,Formato elem,String prop,int index){
    //memoria de formato actual del subgrupo
        StyleConstants.setBold(attrs, elem.negrita);
        StyleConstants.setItalic(attrs, elem.cursiva);
        StyleConstants.setUnderline(attrs, elem.subrayado);
        StyleConstants.setStrikeThrough(attrs, elem.tachado);
        StyleConstants.setBackground(attrs, elem.resaltar);
        StyleConstants.setForeground(attrs, elem.color );
        StyleConstants.setFontFamily(attrs, elem.fuente);        
        StyleConstants.setFontSize(attrs, elem.size);
    //nuevo valor del atributo del subgrupo
        if(prop.equals("negrita")){
            StyleConstants.setBold(attrs, !elem.negrita);
            t[index].estilo.negrita = !elem.negrita;
        }
        else{
            if(prop.equals("cursiva")){
                StyleConstants.setItalic(attrs, !elem.cursiva);
                t[index].estilo.cursiva = !elem.cursiva;
            }
            else{
                if(prop.equals("subrayado")){
                    StyleConstants.setUnderline(attrs, !elem.subrayado);
                    t[index].estilo.subrayado = !elem.subrayado;
                }
                else{
                    if(prop.equals("tachado")){
                        StyleConstants.setStrikeThrough(attrs, !elem.tachado);
                        t[index].estilo.tachado = !elem.tachado;
                    }
                    else{
                        if(prop.equals("resaltar")){
                            StyleConstants.setBackground(attrs, colorSelected);
                            t[index].estilo.resaltar = colorSelected;
                        }
                        else{
                            if(prop.equals("color")){
                                StyleConstants.setForeground(attrs, colorSelected);
                                t[index].estilo.color = colorSelected;
                            }
                            else{
                                if(prop.equals("fuente")){
                                    StyleConstants.setFontFamily(attrs, fontSelected);
                                    t[index].estilo.fuente = fontSelected;
                                }
                                else{
                                    if(prop.equals("size")){
                                        StyleConstants.setFontSize(attrs, sizeSelected);
                                        t[index].estilo.size = sizeSelected;
                                    }
                                    else
                                        System.out.println(">>Propiedad inexistente");
                                } 
                            } 
                        } 
                    } 
                } 
            } 
        }
        return attrs;         
    }

     Dupla ifExists(int startsSel,int endsSel){
        Dupla result = new Dupla(1, 0);
        //result = t[0];
         
        return result;
    }
    
    Fuente[] emptyFormatos(){
        Fuente[] res = new Fuente[10];
        for(int i= 0;i<res.length;i++)
            res[i]= new Fuente();
        return res;    
    }
    
    void addFormato(int ini, int fin, Formato f){
        if(t.length == IND_T ){
            Fuente [] tmp = t;
            t = new Fuente[tmp.length+10];
            for(int i= 0;i<tmp.length;i++)
                t[i]= tmp[i];
            for(int i= tmp.length;i<t.length;i++)
                t[i]= new Fuente();
        }
        t[IND_T].selIni = ini;
        t[IND_T].selFin = fin;
        t[IND_T].estilo = f;
        IND_T++;
        sortFormatos();
        
    }
    void deleteFuente(int i){
       // t[i].estilo = null;//delFormato
        t[i] = new Fuente();
        sortFormatos();
    }
    
    void modifyFormato(int ind, int ini, int fin){
        t[ind].selIni = ini;
        t[ind].selFin = fin;
        sortFormatos();
    }
    void modifyFormato(int ind,  Formato f){
        t[ind].estilo = f ;
        weHaveToClean(ind);
        sortFormatos();
    }
    void modifyFormato(int ind, int ini, int fin, Formato f){
        t[ind].selIni = ini;
        t[ind].selFin = fin;
        t[ind].estilo = f ;
        weHaveToClean(ind);
        sortFormatos();
    }
    
    //Limpia los elem que tengan valores iniciales o equivalentes al default
    void weHaveToClean(int ind){
        boolean clean = false;
        Formato f = t[ind].estilo;
        if(t[ind].selIni == -1)
            clean = true;
        else if(t[ind].selFin == -1)
            clean = true;
        else if( f.negrita == false && f.cursiva == false && f.subrayado == false && f.tachado == false && f.resaltar == null && f.color == null && f.fuente == null && f.size == 0)
            clean = true;
        if (clean == true)  
            deleteFuente(ind);       
    }
    //insertion sort, almost sorted
    void sortFormatos(){
        for(int i= 0; i<t.length;i++){
            for(int j= i; j>0 && t[j-1].selIni > t[j].selIni;j--){
                Fuente tmp = t[j-1];
                t[j-1] = t[j];
                t[j] = tmp;
            }
        }
    }
    void sortFormatosAux(){
        for(int i= 0; i<aux.length;i++){
            for(int j= i; j>0 && aux[j-1].selIni > aux[j].selIni;j--){
                Fuente tmp = aux[j-1];
                aux[j-1] = aux[j];
                aux[j] = tmp;
            }
        }
    }
    int searchFormato(){
        int ind = -1;
        
        return ind;
    }
    int haveSubgroups(int ini, int fin){
        int subGrup = 0;
        aux = new Fuente[t.length]; 
        boolean hasInit = false;
        int starts = -1;
        int ends = -1;
        for(int i = 0;i<t.length;i++){
            if(t[i].selFin >= ini && t[i].selIni<=fin){
                    aux[IND_AUX] = t[i];
                    t[i] = null;                    
                    IND_AUX++;
            }
        }
       /*(ini,fin)=*/ cutAndFill(ini,fin);
        //pre: debe estar cortado y rellenado
        //sortFormatos()
        //ubicar seccion de subgrupos
        //pre: debe estar cortado y ordenado    
        for(int i = 0;i<t.length;i++){
                if(!hasInit && t[i].selIni >= ini){
                    starts = i;
                    hasInit = true;
                } 
                if( t[i].selFin <= fin){
                    ends = i;
                }
        }        
        subGrup++;
        /*
        if(e.f >= i && e.i<=f)
            elem_q_los_tienen[]= e;
        */  
        return subGrup;
    }
    void cutAndFill(int ini,int fin){
        cut(ini,fin);
        fill(ini,fin);
        insertAuxInT();
    }
    void cut(int ini,int fin){
        for(int i= 0;i<aux.length; i++){
            if(aux[i].selIni < ini && aux[i].selFin > fin){
               Fuente left   = new Fuente();
               Fuente center = new Fuente();
               Fuente right  = new Fuente();
               
               left.selIni = aux[i].selIni;
               left.selFin = ini-1;
               left.estilo = aux[i].estilo;

               center.selIni = ini;
               center.selFin = fin;
               center.estilo = aux[i].estilo;

               right.selIni = fin+1;
               right.selFin = aux[i].selFin;
               right.estilo = aux[i].estilo;
               
               aux[i]= left;
               aux[IND_AUX] = center;
               IND_AUX++;
               aux[IND_AUX] = right;
               IND_AUX++;         
            }
            else if( aux[i].selFin > fin){
               Fuente center = new Fuente();
               Fuente right  = new Fuente();

               center.selIni = aux[i].selIni;
               center.selFin = fin;
               center.estilo = aux[i].estilo;

               right.selIni = fin+1;
               right.selFin = aux[i].selFin;
               right.estilo = aux[i].estilo;

               aux[i]= center;
               aux[IND_AUX] = right;
               IND_AUX++;         
            }

            else if(aux[i].selIni < ini){
               Fuente left   = new Fuente();
               Fuente center = new Fuente();
               
               left.selIni = aux[i].selIni;
               left.selFin = ini-1;
               left.estilo = aux[i].estilo;

               center.selIni = ini;
               center.selFin = aux[i].selFin;
               center.estilo = aux[i].estilo;

               aux[i]= left;
               aux[IND_AUX] = center;
               IND_AUX++;         
            }
        } 
        sortFormatosAux();
    }
    void fill(int ini, int fin){
        
        int id = ini;
        int i= 0;
        for(boolean ends = false;!ends && i<aux.length && aux[i].selIni<=fin;i++){
            if(aux[i].selIni >=id){
                id = aux[i].selFin;
                ends = true;
            }
        }
        
        for(;i<aux.length && aux[i].selIni<=fin;i++){
            if(aux[i].selIni ==id || aux[i].selIni == id+1)
                id = aux[i].selFin;
            else{
                Fuente nuevo = new Fuente();
                nuevo.selIni =  id+1;
                nuevo.selFin =  aux[i].selFin;
                aux[IND_AUX]= nuevo;
                IND_AUX++;
                id = aux[i].selFin;
            }    
        }

        joinEquivalent();
           

    }
    //Just join adjacent equivalent elems
    void joinEquivalent(){
        for(int i=0;i<aux.length-1 ;i++){
            if(aux[i].estilo == aux[i+1].estilo){
                aux[i+1].selIni = aux[i].selIni;
                aux[i] = null;
            }
        }
        boolean ends = false;
        for(int i = 0;i<aux.length;i++){
            ends = false;
            for(int j=i+1;j<aux.length && aux[i]==null && !ends;j++){
                if(aux[j] !=  null){
                    aux[i] = aux[j];
                    aux[j] = null;
                    ends =  true;
                }
            }
        }
    }
    
    void insertAuxInT(){
        if(t.length-2<IND_AUX+IND_T){
            Fuente [] tmp = t;
            t = new Fuente[tmp.length+(IND_AUX+IND_AUX)-tmp.length+10];
            for(int i= 0;i<tmp.length;i++)
                t[i]= tmp[i];
            for(int i= tmp.length;i<t.length;i++)
                t[i]= new Fuente();
        }
       
        for(int i = IND_T, j=0;j < aux.length;i++,j++,IND_T++)
            t[i]= aux[j];

        aux = null;
        sortFormatos();
    }
    
        /*TODO
        add f vacio t
        add funcion para agregar elem a t[]
        add fun para eliminar elem de t[]
        add fun para modif elem
        add fun para checkear si alguno elem esta todo en valor iniciales, sacarlo (llamada en modif)
        add f  para ordenar (llamada en cada una de las anteriores)
                add f buscar elem en t
                add f buscar sub grupos en t
                */
    class Dupla{
        int cant_subGrupos = -1;
        int index = -1;
        public Dupla(int c, int i){
            index = i;
            cant_subGrupos = c;
        }
    }   
}

