import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.AttributeSet;
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
      // printArrays();
       
        Dupla sub = null;//haveSubgroups(startsSel,endsSel); 
       
        if(/*sub.cant_subGrupos > 0*/false){
            Formato elem;
            for(int i = sub.index;i< sub.cant_subGrupos+sub.index;i++){
                elem = t[i].estilo;
                if(elem !=null ){/*elem.selFin<=endsSel esta condicion la he usado en el pasado*/ 
                    SimpleAttributeSet attrs2 = set_atrib(attrs);
                        //edit.getParagraphAttributes();
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
         System.out.println("start:"+startsSel +" end:"+endsSel);
        Element element;
        for(int i = startsSel; i < endsSel-startsSel; i++){
            String texto = edit.getStyledDocument().getText(i,1);
            
            element = edit.getStyledDocument().getCharacterElement(i);
            AttributeSet attrs2 = element.getAttributes();
            //aca va el cambio del atributo
            
            attrs = new SimpleAttributeSet(attrs2);
            attrs = set_atrib(attrs);
            
            edit.getStyledDocument().remove(i,1);     
            edit.getStyledDocument().insertString(i, texto, attrs);
        }
        
        //printArrays(); 
        
        //System.out.println("entro a 'check'");                       
       // check_join_groups();//unimos los grupos que sean iguales y adyacentes
        //System.out.println("salgo de 'check'");
    }
    
    Formato giveMeAtr(SimpleAttributeSet attrs){
        Formato elem   = new Formato();
        elem.negrita   = StyleConstants.isBold(attrs);
        elem.cursiva   = StyleConstants.isItalic(attrs);
        elem.subrayado = StyleConstants.isUnderline(attrs);
        elem.tachado   = StyleConstants.isStrikeThrough(attrs);
        elem.resaltar  = StyleConstants.getBackground(attrs);
        elem.color     = StyleConstants.getForeground(attrs);
        elem.fuente    = StyleConstants.getFontFamily(attrs);
        elem.size      = StyleConstants.getFontSize(attrs);
        
        return elem;
    }
    SimpleAttributeSet set_atrib(SimpleAttributeSet attrs){
    //memoria de formato actual del subgrupo
     /*   StyleConstants.setBold(attrs, elem.negrita);
        StyleConstants.setItalic(attrs, elem.cursiva);
        StyleConstants.setUnderline(attrs, elem.subrayado);
        StyleConstants.setStrikeThrough(attrs, elem.tachado);
        StyleConstants.setBackground(attrs, elem.resaltar);
        StyleConstants.setForeground(attrs, elem.color );
        StyleConstants.setFontFamily(attrs, elem.fuente);        
        StyleConstants.setFontSize(attrs, elem.size);*/
    //nuevo valor del atributo del subgrupo
    System.out.println("0:Atr: "+attrs);
        Formato elem = giveMeAtr(attrs);
        if(prop.equals("negrita")){
            System.out.println("1:Atr: "+attrs);
            StyleConstants.setBold(attrs, !elem.negrita);
            System.out.println("2:Atr: "+attrs);
            //t[index].estilo.negrita = !elem.negrita;
            printArrays();
        }
        else{
            if(prop.equals("cursiva")){
                StyleConstants.setItalic(attrs, !elem.cursiva);
                //t[index].estilo.cursiva = !elem.cursiva;
            }
            else{
                if(prop.equals("subrayado")){
                    StyleConstants.setUnderline(attrs, !elem.subrayado);
                    //t[index].estilo.subrayado = !elem.subrayado;
                    
                }
                else{
                    if(prop.equals("tachado")){
                        StyleConstants.setStrikeThrough(attrs, !elem.tachado);
                        //t[index].estilo.tachado = !elem.tachado;
                    }
                    else{
                        if(prop.equals("resaltar")){
                            StyleConstants.setBackground(attrs, colorSelected);
                            //t[index].estilo.resaltar = colorSelected;
                        }
                        else{
                            if(prop.equals("color")){
                                StyleConstants.setForeground(attrs, colorSelected);
                                //t[index].estilo.color = colorSelected;
                            }
                            else{
                                if(prop.equals("fuente")){
                                    StyleConstants.setFontFamily(attrs, fontSelected);
                                    //t[index].estilo.fuente = fontSelected;
                                }
                                else{
                                    if(prop.equals("size")){
                                        StyleConstants.setFontSize(attrs, sizeSelected);
                                        //t[index].estilo.size = sizeSelected;
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

    void printArrays(){
        System.out.print("  ("+t.length+")t: ");
        for(int i=0;i<IND_T;i++){
            System.out.print(t[i].selIni+":"+t[i].estilo.negrita+" ");
        }
        System.out.println("");
       System.out.print("   aux: ");
        for(int i=0;aux != null && i<IND_AUX;i++){
            if(aux[i]!=null)
                System.out.print(aux[i].selIni+":"+aux[i].estilo.negrita+" ");
        }
        System.out.println("");
    }
    //unimos los grupos que sean iguales y adyacentes
    void check_join_groups(){//debe ser llamada tras los cambios en las fuentes
        int startIn = t.length;
        boolean startb = false;
        for(int i=0;i<t.length-1 ;i++){
            if(t[i].estilo == t[i+1].estilo){
                t[i+1].selIni = t[i].selIni;
                t[i] = null;
                if(!startb){
                    startIn = i;
                    startb = true;
                } 
            }
        }
        boolean ends = false;
        boolean getOut = false;
        for(int i = startIn,j=startIn+1;!getOut && i<t.length;i++){
            ends = false;
            j=i+1;
            for(;j<t.length && t[i]==null && !ends;j++){
                if(t[j] !=  null){
                    t[i] = t[j];
                    t[j] = null;
                    ends =  true;
                }
            }
            if(j== t.length && t[i]==null)//corte: los restantes elems son nulos
                getOut= true;
        }
    }
    
    Fuente[] emptyFormatos(){
        Fuente[] res = new Fuente[10];
        for(int i= 0;i<res.length;i++)
            res[i]= new Fuente();
        return res;    
    }
     
   
    //insertion sort, almost sorted
    void sortFormatos(){//TODO debe ordenar los nulos
        for(int i= 1; i<t.length;i++){
            for(int j= i; j>0 && t[j-1].selIni > t[j].selIni;j--){
                Fuente tmp = t[j-1];
                t[j-1] = t[j];
                t[j] = tmp;
            }
        }
    }
    void sortFormatosAux(){
        for(int i= 0; i<aux.length;i++){
            for(int j= i; j>0 && aux[j-1].selIni > aux[j].selIni && aux[j].selIni != -1;j--){
                Fuente tmp = aux[j-1];
                aux[j-1] = aux[j];
                aux[j] = tmp;
            }
        }
    }
    
    Dupla haveSubgroups(int ini, int fin){
        int subGrup = 0;
        aux = new Fuente[t.length]; 
        boolean hasInit = false;
        int starts = -1;
        int ends = -1;
        for(int i = 0;i<t.length;i++){
            if(t[i].selFin >= ini && t[i].selIni<=fin){
                    aux[IND_AUX] = t[i];
                    t[i] = new Fuente(); //TODO o ponemos una fuente vacia o cambiamos el sort()                   
                    IND_AUX++;
            }
        }
        System.out.print("t1> ");
        printArrays();
        //System.out.println("entradon a 'cutAndFill'");
       /*(ini,fin)=*/ cutAndFill(ini,fin);
       //System.out.println("saliendo de 'cutAndFill'");
        //pre: debe estar cortado y rellenado
        //sortFormatos()
        //ubicar seccion de subgrupos
        //pre: debe estar cortado y ordenado    
        System.out.print("t2> ");
        printArrays();
        boolean getOut = false;
        for(int i = 0;!getOut && i<t.length;i++){
                if(!hasInit && t[i].selIni >= ini){
                    starts = i;
                    hasInit = true;
                }
                else{ 
                    if( t[i].selFin <= fin){
                        ends = i;
                        if(hasInit)//contamos solo cuando inicia
                            subGrup++;
                    }
                    else
                        getOut = true;
               }     
        }        
        System.out.println("subgrup= "+subGrup+" ind_t= "+IND_T);
        /*
        if(e.f >= i && e.i<=f)
            elem_q_los_tienen[]= e;
        */  
        
        return  (new Dupla(subGrup, starts));
    }
    void cutAndFill(int ini,int fin){
        System.out.print("t1.1> ");
        printArrays();
        cut(ini,fin);
        System.out.print("t1.2> ");
        printArrays();
       // fill(ini,fin);//USELESS
      
        insertAuxInT();
         System.out.print("t1.3> ");
        printArrays();
    }
    void checkLenAux(){
  
        if(aux.length<IND_AUX+3){
            Fuente [] tmp = aux;
            aux = new Fuente[tmp.length+(IND_AUX)+13];
            for(int i= 0;i<tmp.length;i++)
                aux[i]= tmp[i];
            for(int i= tmp.length;i<aux.length;i++)
                aux[i]= new Fuente();
        }
       
    }
    void cut(int ini,int fin){
          System.out.println("a.i= "+aux[0].selIni+"i: "+ini+" a.f= "+aux[0].selFin+" f: "+fin);
        int endsInd = aux.length;
        for(int i= 0;i<endsInd; i++){
        
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
               
               checkLenAux();
               
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

               checkLenAux();
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

               checkLenAux();
               aux[i]= left;
               aux[IND_AUX] = center;
               IND_AUX++;         
            }
        }  
        System.out.println("                 ###[[[");
        printArrays();
        sortFormatosAux();
        System.out.println("                 ]]]###");
        printArrays();
    }
    void fill(int ini, int fin){//trata de rellenar huecos, pero esto no deberia ser necesrio nunca
        
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

      //  joinEquivalent(); obvimente no usar aca haria q no se revertieran los hecho
      // ya que aun no se cambio la fuente
           

    }
    //Just join adjacent equivalent elems
    void joinEquivalent(){//NO usar es una mierdda desconectada con lo demas
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
        if(t.length-2<IND_AUX+IND_T){//2 por q los "IND_" estan en una posicion mas de lo elementos q contienen 
            Fuente [] tmp = t;
            t = new Fuente[tmp.length+(IND_AUX)+10];
            for(int i= 0;i<tmp.length;i++)
                t[i]= tmp[i];
            for(int i= tmp.length;i<t.length;i++)
                t[i]= new Fuente();
        }
        System.out.println("t.len= "+t.length+" ind_t = "+IND_T+" aux.len= "+aux.length);
        for(int i = IND_T, j=0;j<IND_AUX &&j < aux.length;i++,j++,IND_T++)
            t[i]= aux[j];

        aux =  emptyFormatos();
        IND_AUX =0;
        sortFormatos();//este sort es crucial
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
        t[i] = new Fuente();//al hacerlo nuevos los valores selIni se setean -1 y e iran a bajo en la lista
        sortFormatos();//TODO modificar sort o el borrado esto es  una mierda
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
    void weHaveToClean(int ind){//TODO esto es mierda! total mierda!
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
                
    class Dupla{
        int cant_subGrupos = -1;
        int index = -1;
        public Dupla(int c, int i){
            index = i;
            cant_subGrupos = c;
        }
    }   
}

