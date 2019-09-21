import java.awt.Color;
class Formato{
    boolean negrita;
    boolean cursiva;
    boolean subrayado;
    boolean tachado;
    Color resaltar;
    Color color;
    String fuente;
    int size; 

    public Formato(){
        negrita   = false;
        cursiva   = false;
        subrayado = false;
        tachado   = false;
        resaltar  = null;
        color     = null;
        fuente    = null;
        size      = 0; 
    }

    public Formato(boolean n,boolean c,boolean s,boolean t,Color r,Color co,String f,int si){
        negrita   = n;
        cursiva   = c;
        subrayado = s;
        tachado   = t;
        resaltar  = r;
        color     = co;
        fuente    = f;
        size      = si; 
    }
}
