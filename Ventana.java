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
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.Component; 

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
            archivo a = new archivo();
            a.proyectName = proyectName;
            
            a.data = lienzo.getText();//TODO texto con atributos
            
            a.fontSelected  = lienzo.fontSelected;
            a.sizeSelected  = lienzo.sizeSelected;
            a.colorSelected = lienzo.colorSelected;
            a.spaceSelected = lienzo.spaceSelected;
            a.save(PATH_FILE);
            //TODO a.saveRecientes(recientes);
            CAMBIOS_SIN_GUARDAR = false;
            
              
            
        }        
    }
    private void guardarArchivoNuevo(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")+"/filesTests"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//FILES_ONLY);
        
        int result = fileChooser.showSaveDialog(this);
       
        if (result == JFileChooser.APPROVE_OPTION /*&& PATH_FILE == null*/) {
            File selectedFile = fileChooser.getSelectedFile();
            PATH_FILE = selectedFile.getAbsolutePath();
            guardar();
            
        }    
    }
    void load(){//TODO
        
        archivo arch = new archivo();
        
        lienzo.load(arch.load(PATH_FILE));
       
        //proyectName = arch.proyectName;
        //lienzo.fontSelected =  arch.fontSelected;
        //lienzo.sizeSelected =  arch.sizeSelected;
        //lienzo.colorSelected = arch.colorSelected;
        //lienzo.spaceSelected = arch.spaceSelected;
        //setTitle(proyectName);
        
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
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")+"/filesTests"));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                PATH_FILE = selectedFile.getAbsolutePath();
                load();
                System.out.println("File loaded: " + PATH_FILE);
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
            lienzo.colorSelected = selectedColor(this,"Color de fuente",lienzo.colorSelected);
            lienzo.changeText(lienzo.edit.getSelectionStart(),lienzo.edit.getSelectionEnd());
            CAMBIOS_SIN_GUARDAR = true;
        }
        if (e.getSource()==menu.botResal){
            System.out.println("HA sido presionado: resaltar");
            lienzo.prop = "resaltar";
            lienzo.colorSelected = selectedColor(this,"Color de resaltado",lienzo.colorSelected);
            lienzo.changeText(lienzo.edit.getSelectionStart(),lienzo.edit.getSelectionEnd());
            CAMBIOS_SIN_GUARDAR = true;
        }
/*TODO*/if (e.getSource()==menu.botPDF){System.out.println("HA sido presionado: pdf");}
    }
    //
    Color selectedColor(Component component, String title, Color initial){

        JColorChooser choose = new JColorChooser(initial);

        JDialog dialog = JColorChooser.createDialog(component, title, true, choose, null, null);

        AbstractColorChooserPanel[] panels = choose.getChooserPanels();
        for (AbstractColorChooserPanel accp : panels) {
            choose.removeChooserPanel(accp);
        }

        choose.addChooserPanel(panels[3]);

        dialog.getContentPane().add(choose);
        dialog.pack();
        dialog.setVisible(true);//.show();

        return choose.getColor();
    
    }

}
