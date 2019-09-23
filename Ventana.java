import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.awt.MouseInfo;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Cursor;
class Ventana extends JFrame implements ActionListener{
    
    boolean CAMBIOS_SIN_GUARDAR = false;

    Estilo estilo = new Estilo();
    //EstiloDark  estilo = new EstiloDark();
    //EstiloLight  estilo = new EstiloLight();

    Menu menu     = new Menu(estilo);//(anchoMenu, altoMenu);
    Lienzo lienzo = new Lienzo(estilo);// (anchoLienzo, altoLienzo);
    JScrollPane scroll ;
    String PATH_FILE = null;
    //String nombre = null;
    //ParStr [] recientes = null;
    String proyectName = "";
    
    int menuBarHeight;
   
    public Ventana(){
        setLayout(null);
        setJMenuBar(menu.barraMenu());
        add(menu.menuOpciones());
        add(lienzo.lienzo());
       // scroll = new JScrollPane(lienzo.lienzo());
        //add(scroll);
        setIconImage(menu.icon);
        actionSettings();
        
        /*archivo a = new archivo();
        recientes = a.load_recientes();
        System.out.println("reciente inicial: "+recientes[recientes.length-1].clave);
        */
        /*if(recientes != null){
            ParStr actual = recientes[recientes.length-1];
            proyectName = actual.clave;       
            System.out.println("direcion de actual: "+actual.valor);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           	                                                                                                                                                                                                                                                                                                                            
            list = a.loadFormatedString(actual.valor);//cargamos el last de la lista 
        }    
        else
            list = a.loadFormatedString(null);

        */
        setBounds(estilo.marginWindowsX,estilo.marginWindowsY,estilo.width,estilo.height);
        setVisible(true);
        setTitle("Editor de texto     "+proyectName);
        setBackground(estilo.background);
        setDefaultCloseOperation(EXIT_ON_CLOSE);  
        setResizable(false);

        
    }
    void actionSettings(){ 
        menu.nuevo.addActionListener(this);
        menu.botNuevo.addActionListener(this);       
        menu.abrir.addActionListener(this);
        menu.botAbrir.addActionListener(this);
        menu.guardar.addActionListener(this);
        menu.botGuardar.addActionListener(this);
        menu.guardarComo.addActionListener(this);
        menu.botGuardarComo.addActionListener(this);
        menu.botUndo.addActionListener(this);
        menu.botRedo.addActionListener(this);
        menu.botNeg.addActionListener(this);
        menu.botCur.addActionListener(this);
        menu.botSub.addActionListener(this);
        menu.botTach.addActionListener(this);
        menu.botColor.addActionListener(this);
        menu.botResal.addActionListener(this);
        menu.botPDF.addActionListener(this);
      /*  creo que son STATECHANGE o algo asi
        menu.size.addActionListener(this);
        menu.font.addActionListener(this);
        menu.search.addActionListener(this);
        */               
    }

/*
    void newReciente(String direccion){
        if(recientes == null){
            recientes = new ParStr[1];
            recientes[0] = new ParStr();
            recientes[0].valor = direccion;  
        }
        else{
            ParStr [] tmp = recientes;
            recientes = new ParStr[tmp.length+1];
            for(int i=0;i<tmp.length;i++)
                recientes[i] = tmp[i];
            recientes[tmp.length] = new ParStr();    
            recientes[tmp.length].valor = proyectName;    
            recientes[tmp.length].valor = direccion;   
        }
    }
    
*/
    private void guardar(){//TODO
        if(PATH_FILE != null){

            System.out.println("Saving file: " + PATH_FILE);
            if(lienzo.lista == null)
                System.out.println("Pre: llamda nula");
            archivo a = new archivo();
            a.proyectName = proyectName;
            a.data = lienzo.getText();//TODO texto con atributos
            a.fontSelected = lienzo.fontSelected;//TODO variables grales d lienzo
            a.sizeSelected = lienzo.sizeSelected;
            a.colorSelected = lienzo.colorSelected;
            a.spaceSelected = lienzo.spaceSelected;
            a.escritor(PATH_FILE);
            //TODO a.saveRecientes(recientes);
            CAMBIOS_SIN_GUARDAR = false;
            
                //proyectName = p.nombre;
                lienzo.new_edit();
                lienzo.init_vars();
                /*variable grales de lienzo
                public String fontSelected = "TimesRoman";
                public int sizeSelected ;
               public Color colorSelected = null;
                public float spaceSelected = 1;
                    public String prop = "";
                
                */
            
        }        
    }
    private void guardarArchivoNuevo(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//FILES_ONLY);
        
        int result = fileChooser.showSaveDialog(this);
       
        if (result == JFileChooser.APPROVE_OPTION /*&& PATH_FILE == null*/) {
            File selectedFile = fileChooser.getSelectedFile();
            PATH_FILE = selectedFile.getAbsolutePath();
            guardar();
            
        }    
    }
    void load(){//TODO
    System.out.println("Selected file: " + PATH_FILE);
        archivo arch = new archivo();
        arch.lector(PATH_FILE);
        lienzo.lista = arch.l;
        proyectName = arch.proyectName;
        lienzo.indL = arch.indL;
        lienzo.load_lista();
        setTitle(proyectName);
    }

    void cursorDefault(){
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==menu.nuevo || e.getSource()==menu.botNuevo){
            if(CAMBIOS_SIN_GUARDAR){
                int eleccion = JOptionPane.showConfirmDialog (null, "Guardar datos antes de salir?","Warning",JOptionPane.YES_NO_OPTION);
                if(eleccion == JOptionPane.YES_OPTION){
                    if(PATH_FILE == null)
                         guardarArchivoNuevo(); 
                    else
                         guardar();  
                }
            }
            newProyect p = new newProyect(estilo.width,estilo.height);
            if(p.getDatos()){
                proyectName = p.nombre;
                setTitle(proyectName);
                lienzo.new_edit();
                lienzo.init_vars();
                PATH_FILE = null;
                System.out.println("HA sido presionado: nuevo");
                //lienzo.repaint();
            }
        }   
        if (e.getSource()==menu.abrir || e.getSource()==menu.botAbrir){
            
            if(CAMBIOS_SIN_GUARDAR){
                int eleccion = JOptionPane.showConfirmDialog (null, "Guardar datos antes de salir?","Warning",JOptionPane.YES_NO_OPTION);
                if(eleccion == JOptionPane.YES_OPTION){
                    if(PATH_FILE == null)
                         guardarArchivoNuevo(); 
                    else
                         guardar();  
                }
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                PATH_FILE = selectedFile.getAbsolutePath();
                load();
                System.out.println("Selected file: " + PATH_FILE);
            }          
    
        }
        if (e.getSource()==menu.guardar){
            if(PATH_FILE == null)
                guardarArchivoNuevo();
            else
                guardar();    
        }
        if (e.getSource()==menu.guardarComo){
            guardarArchivoNuevo();  
        }
/*TODO*/if (e.getSource()==menu.botUndo){System.out.println("HA sido presionado: undo");/*CAMBIOS_SIN_GUARDAR = true;*/}
/*TODO*/if (e.getSource()==menu.botRedo){System.out.println("HA sido presionado: redo");/*CAMBIOS_SIN_GUARDAR = true;*/}
        if (e.getSource()==menu.botNeg){
            System.out.println("HA sido presionado: negrita");
            lienzo.prop = "negrita";
            lienzo.changeText(lienzo.edit.getSelectionStart(),lienzo.edit.getSelectionEnd());
            CAMBIOS_SIN_GUARDAR = true;
        }
        if (e.getSource()==menu.botCur){
            System.out.println("HA sido presionado: cursiva");
            lienzo.prop = "cursiva";
            lienzo.changeText(lienzo.edit.getSelectionStart(),lienzo.edit.getSelectionEnd());
            CAMBIOS_SIN_GUARDAR = true;
        }
        if (e.getSource()==menu.botSub){
            System.out.println("HA sido presionado: subrayar");
            lienzo.prop = "subrayado";
            lienzo.changeText(lienzo.edit.getSelectionStart(),lienzo.edit.getSelectionEnd());
            CAMBIOS_SIN_GUARDAR = true;
        }
        if (e.getSource()==menu.botTach){
            System.out.println("HA sido presionado: tachar");
            lienzo.prop = "tachado";
            lienzo.changeText(lienzo.edit.getSelectionStart(),lienzo.edit.getSelectionEnd());
            CAMBIOS_SIN_GUARDAR = true;
        }
        if (e.getSource()==menu.botColor){
            System.out.println("HA sido presionado: color");
            lienzo.prop = "color";
            lienzo.colorSelected = selectedColor();
            lienzo.changeText(lienzo.edit.getSelectionStart(),lienzo.edit.getSelectionEnd());
            CAMBIOS_SIN_GUARDAR = true;
        }
        if (e.getSource()==menu.botResal){
            System.out.println("HA sido presionado: resaltar");
            lienzo.prop = "resaltar";
            lienzo.colorSelected = selectedColor();
            lienzo.changeText(lienzo.edit.getSelectionStart(),lienzo.edit.getSelectionEnd());
            CAMBIOS_SIN_GUARDAR = true;
        }
/*TODO*/if (e.getSource()==menu.botPDF){System.out.println("HA sido presionado: pdf");}
    }
    //TODO este debe pedir el color de un popUp
    Color selectedColor(){
        return (new Color(255,30,30));
    }

}
