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
import javax.swing.text.StyledDocument;
import javax.swing.text.Document;
class  Lienzo {
    JPanel canvas = new JPanel();
   // JTextField uno, dos, tres;
    JTextPane edit = new JTextPane();
    JScrollPane scroll = new JScrollPane(edit);

    public String fontSelected = "TimesRoman";
    public int sizeSelected ;
    public Color colorSelected = null;
    public float spaceSelected = 1;
    public String prop = "";
    Estilo e;
    public String sep = "[\code]";
        
    public Lienzo(Estilo est){
    //--------------Inicializacion de atributos----------------    
        e = est;
        init_vars();
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

    void new_edit(){
        edit = new JTextPane();
    } 
    void init_vars(){
     
        fontSelected = e.textoFontStr;
        sizeSelected = e.sizeFont;
        colorSelected = e.textoColor;
        spaceSelected = e.margin_text_Above;
        prop = "";
    }
    String getText(){
        String r= "";
        try{
            r = getText2();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return r;
    }
    
    String getText2()throws BadLocationException{
        String res = "";//= edit.getStyledDocument();
        
        Element element;
        AttributeSet attrs2;
        SimpleAttributeSet attrs;
        for(int i = 0; i < edit.getStyledDocument().getLength(); i++){
            String texto = edit.getStyledDocument().getText(i,1);
            
            element = edit.getStyledDocument().getCharacterElement(i);
            attrs2 = element.getAttributes();
            //aca va el cambio del atributo
            
            attrs = new SimpleAttributeSet(attrs2);
            res += sep+attrs+texto+sep;
        }
        return res;
    }
    void load(String s){
        try{
            load_(s);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    boolean is_sep(String s, int i){
        boolean res = true;

   /*   boolean res = false;//simplest way, but no dinamic
        if(s.charAt(i) == sep.charAt(0) && s.length()>=i+sep.length() && s.charAt(i+1)==sep.charAt(1) && s.charAt(i+2)==sep.charAt(2)&& s.charAt(i+3)==sep.charAt(3)&& s.charAt(i+4)==sep.charAt(4)&& s.charAt(i+5)==sep.charAt(5)&& s.charAt(i+6)==sep.charAt(6))
            res = true;*/
        int l = 0;
        for( ;res && l<sep.length() && i+l<s.length();l++){
            if(s.charAt(i+l) != sep.charAt(l))
                res = false;
        }
        if(res && l != sep.length() && i+l>=s.length())
            res = false;
        return res;
    }
    
    void load_(String s){//TODO throws BadLocationException
    //.equals("[\code]")
        String c;
        String tmp = "";
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        new_edit();
        for(int i = 0,cp=0; i < s.length(); i++,cp++){
           
            attrs = new SimpleAttributeSet();      
            if(is_sep(s,i)){
                i += sep.length();
                int j = i;
                for ( ; j < s.length() && is_sep(s,j); j++)
                    tmp += s.charAt();
                if(is_sep(s,j)){
                    i += tmp.length()+sep.length();
                    int count = gimmeAtrib(tmp) ;
                    for(int j=0;j< count;j++)
                        attrs = set_atrib(attrs);
                }    
                else
                   i -= sep.length();     
            }
            c = ""+s.charAt(i);
            
          
            
        //---
            
        
            //String texto = edit.getStyledDocument().getText(i,1);
            
            //element = edit.getStyledDocument().getCharacterElement(i);
            //attrs2 = element.getAttributes();
            //aca va el cambio del atributo
            
            //attrs = new SimpleAttributeSet(attrs2);
            //res += sep+attrs+texto+sep;
            
            edit.getStyledDocument().insertString(cp, c, attrs);
        }
    }
    
    void changeText(int startsSel,int endsSel){
        try{
            changeText2(startsSel,endsSel);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    //{"negrita","cursiva","subrayar","tachar","color","resaltar","fuente","tamano","borrar","insert","reemplazar"}
    // equivalent to
    //operacion = {'n','k','s','T','c','r','f','t','b','i','R'}
    void changeText2(int startsSel,int endsSel) throws BadLocationException{
      //obj para aplicar atributos del texto         
        SimpleAttributeSet attrs = new SimpleAttributeSet();
                 
         System.out.println("start:"+startsSel +" end:"+endsSel+"s+e: "+(endsSel-1));
        Element element;
        for(int i = startsSel; i < endsSel; i++){
            String texto = edit.getStyledDocument().getText(i,1);
            
            element = edit.getStyledDocument().getCharacterElement(i);
            AttributeSet attrs2 = element.getAttributes();
            //aca va el cambio del atributo
            
            attrs = new SimpleAttributeSet(attrs2);
            attrs = set_atrib(attrs);
            
            edit.getStyledDocument().remove(i,1);     
            edit.getStyledDocument().insertString(i, texto, attrs);
        }
        prop = "";//por las dudas reseteamos la propiedad elegida.
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
    //System.out.println("0:Atr: "+attrs);
        Formato elem = giveMeAtr(attrs);
        if(prop.equals("negrita")){
            //System.out.println("1:Atr: "+attrs);
            StyleConstants.setBold(attrs, !elem.negrita);
           // System.out.println("2:Atr: "+attrs);
            //t[index].estilo.negrita = !elem.negrita;
            
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

                
    class Dupla{
        int cant_subGrupos = -1;
        int index = -1;
        public Dupla(int c, int i){
            index = i;
            cant_subGrupos = c;
        }
    }   
}

