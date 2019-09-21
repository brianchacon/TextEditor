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
import java.awt.Dimension;
class Menu implements ActionListener{

    JMenuBar barra; 
    JMenu   file, edit, format;
    JMenuItem nuevo, abrir, guardar,guardarComo;
    JButton botNuevo, botAbrir, botGuardar, botGuardarComo, botUndo, botRedo, botNeg, botCur, botSub, botTach, botColor, botResal, botPDF;
    JComboBox <String> size, font;
    JTextField search;
    JPanel panelMenu= new JPanel();
    Image icon ;
    public Menu(Estilo e){
        //Barra
        barra = new JMenuBar(); 
        barra.setPreferredSize(new Dimension(e.widthBar,e.heightBar));
        
        file = new JMenu("File");       
        //file.setFont();
        edit = new JMenu("Edit");       
        //edit.setFont();
        format = new JMenu("Format");       
        //format.setFont();
        
        nuevo = new JMenuItem("Nuevo");
        file.add(nuevo);
        
        
        abrir = new JMenuItem("Abrir");
        abrir.addActionListener(this);
        file.add(abrir);
        
        guardar = new JMenuItem("Guardar");
        file.add(guardar);
        
        guardarComo = new JMenuItem("Guardar como..");
        file.add(guardarComo);

        barra.add(file);
        barra.add(edit);
        barra.add(format);
        //Colores de la barra y sus miembros
        barra.setBackground(e.colorBar);
        file.setBackground(e.colorBarItem);
        edit.setBackground(e.colorBarItem);
        format.setBackground(e.colorBarItem);
        nuevo.setBackground(e.colorMenuBarItem);
        abrir.setBackground(e.colorMenuBarItem);
        guardar.setBackground(e.colorMenuBarItem);
        guardarComo.setBackground(e.colorMenuBarItem);         
        //Menu iconado
        panelMenu.setLayout(null);
        panelMenu.setBounds(e.marginMenuX, e.marginMenuY, e.widthMenu, e.heightMenu);
        panelMenu.setBackground(e.colorMenu);
        //Creamos botones
        botNuevo       = new JButton();
        botAbrir       = new JButton();
        botGuardar     = new JButton();
        botGuardarComo = new JButton();
        botUndo        = new JButton();
        botRedo        = new JButton();
        botNeg         = new JButton();
        botCur         = new JButton();
        botSub         = new JButton();
        botTach        = new JButton();
        botColor       = new JButton();
        botResal       = new JButton();
        botPDF         = new JButton();
        search         = new JTextField("Search");
        size           = new JComboBox <String>();
        size.addItem("6");
        size.addItem("7");
        size.addItem("8");
        size.addItem("9");
        size.addItem("10");
        size.addItem("11");
        size.addItem("12");
        size.addItem("13");
        size.addItem("14");
        size.addItem("15");
        size.addItem("16");
        size.addItem("18");
        size.addItem("20");
        size.addItem("22");
        size.addItem("24");
        size.addItem("26");
        size.addItem("28");
        size.addItem("32");
        size.addItem("48");
        size.addItem("64");
        size.addItem("72");
        font = new JComboBox <String>();
        font.addItem("Tahoma");
        font.addItem("Verdana");
        font.addItem("Times New Roman");
    //.addItemListener(this);
    //void itemStateChanged(ItemEvent e) {(String)combo1.getSelectedItem();
        //cargar imagenes
        ImageIcon archivoNuevo = new ImageIcon("archivoNuevo.png");
        ImageIcon guardar      = new ImageIcon("guardar.png");
        ImageIcon guardarComo  = new ImageIcon("guardarComo.png");
        ImageIcon abrir        = new ImageIcon("abrir.png");
        ImageIcon undo         = new ImageIcon("undo.png");
        ImageIcon redo         = new ImageIcon("redo.png");
        ImageIcon neg          = new ImageIcon("negrita.png");
        ImageIcon cur          = new ImageIcon("cursiva.png");
        ImageIcon sub          = new ImageIcon("subrayar.png");
        ImageIcon tach         = new ImageIcon("tachar.png");
        ImageIcon colLet       = new ImageIcon("colorLetra.png");
        ImageIcon res          = new ImageIcon("resaltar.png");                                        
        ImageIcon pdf          = new ImageIcon("pdficono.png");
        icon                   = new ImageIcon(getClass().getResource("LOGO.png")).getImage();
        
        //localizamos botones de menu 
        
        int pMenu = e.marginIconLeft;
        botNuevo.setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon);
        pMenu += e.widthIcon + e.marginIconLeft;
        botAbrir.setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon);
        pMenu += e.widthIcon + e.marginIconLeft;
        botGuardar.setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon);
        pMenu += e.widthIcon + e.marginIconLeft;
        botGuardarComo.setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon);  
        pMenu += e.widthIcon + e.marginIconLeft;
        botUndo.setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon); 
        pMenu += e.widthIcon + e.marginIconLeft;
        botRedo.setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon);
        pMenu += e.widthIcon + e.marginIconLeft;
        font.setBounds(pMenu,e.marginIconTop,e.widthIcon*8, e.heightIcon);
        pMenu += e.widthIcon*8 + e.marginIconLeft;        
        size.setBounds(pMenu,e.marginIconTop,e.widthIcon*2, e.heightIcon);
        pMenu += e.widthIcon*2 + e.marginIconLeft;        
        botNeg.setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon);
        pMenu += e.widthIcon + e.marginIconLeft;
        botCur.setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon);
        pMenu += e.widthIcon + e.marginIconLeft;
        botSub .setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon);
        pMenu += e.widthIcon + e.marginIconLeft;
        botTach .setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon);
        pMenu += e.widthIcon + e.marginIconLeft;
        botColor.setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon);
        pMenu += e.widthIcon + e.marginIconLeft;
        botResal.setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon);
        pMenu += e.widthIcon + e.marginIconLeft;
        botPDF.setBounds(pMenu,e.marginIconTop,e.widthIcon, e.heightIcon);
        pMenu += e.widthIcon + e.marginIconLeft;
        search.setBounds(pMenu,e.marginIconTop,e.widthIcon*4, e.heightIcon);
        //Configuramos los botones
        botNuevo.setOpaque(false); 
        botNuevo.setContentAreaFilled(false);
        botAbrir.setOpaque(false); 
        botAbrir.setContentAreaFilled(false);
        botGuardar.setOpaque(false); 
        botGuardar.setContentAreaFilled(false);
        botGuardarComo.setOpaque(false); 
        botGuardarComo.setContentAreaFilled(false);     
        //TODO 
        botUndo.setContentAreaFilled(false);     
        botRedo.setContentAreaFilled(false);     
        botNeg.setContentAreaFilled(false);     
        botCur.setContentAreaFilled(false);     
        botSub.setContentAreaFilled(false);     
        botTach.setContentAreaFilled(false);     
        botColor.setContentAreaFilled(false);     
        botResal.setContentAreaFilled(false);     
        botPDF.setContentAreaFilled(false);     
        //Asignamos imagenes a botones
        botNuevo.setIcon(archivoNuevo);
        botAbrir.setIcon(abrir);
        botGuardar.setIcon(guardar);
        botGuardarComo.setIcon(guardarComo);
        botUndo.setIcon(undo);
        botRedo.setIcon(redo);
        botNeg.setIcon(neg);
        botCur.setIcon(cur);
        botSub.setIcon(sub);
        botTach.setIcon(tach);
        botColor.setIcon(colLet);
        botResal.setIcon(res);
        botPDF.setIcon(pdf);
        //listener
        
        //agregamos al menu
        panelMenu.add(botNuevo);
        panelMenu.add(botAbrir);
        panelMenu.add(botGuardar);
        panelMenu.add(botGuardarComo);
        panelMenu.add(botUndo);
        panelMenu.add(botRedo);
        panelMenu.add(botNeg);
        panelMenu.add(botCur);
        panelMenu.add(botSub);
        panelMenu.add(botTach);
        panelMenu.add(botColor);
        panelMenu.add(botResal);
        panelMenu.add(botPDF);
        panelMenu.add(search);
        panelMenu.add(size);
        panelMenu.add(font);
        
    }
    JMenuBar barraMenu(){
        return barra;
    }
    JPanel menuOpciones(){
        return panelMenu;
    }
    
    public void actionPerformed(ActionEvent e) {
       
        
    }
/*    private void guardar(){
        if(direccion != null){
            System.out.println("Selected file: " + direccion);
            archivo a = new archivo();
            if(list == null)
                System.out.println("Pre; llamda nula");
            a.saveFormatedString(list, proyectName, direccion);
            a.saveRecientes(recientes);
            CAMBIOS_SIN_GUARDAR = false;
        }
    }
    
    private void guardarArchivoNuevo(){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION && direccion == null) {
                File selectedFile = fileChooser.getSelectedFile();
                direccion = selectedFile.getAbsolutePath();
                guardar();
                CAMBIOS_SIN_GUARDAR = false;
            }    
    }
*/
}

