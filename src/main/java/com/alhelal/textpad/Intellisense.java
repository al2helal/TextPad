/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhelal.textpad;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author alhelal
 */
public class Intellisense
{

    private Popup stageIntelliSense;
    private VBox root;
    private ListView<String> lstIntelliSense;
    private ObservableList<String> keywords = FXCollections.observableArrayList();
    /*
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    );*/
    private Boolean showing;
/*
    public Intellisense(String s)
    {
        this();
        selectItem(s);
    }*/

    public Intellisense(String keywordsPaht)
    {
        try
        {
            String line = null;
            //BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src/main/java/com/alhelal/resource/CplusKeywords")));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(keywordsPaht)));
            while ((line = bufferedReader.readLine()) != null)
            {
                keywords.addAll(line);
            }

        }
        catch (IOException io)
        {
            System.out.println(io);
        }
        keywords.addAll("hello","clear");
        stageIntelliSense = new Popup();
        root = new VBox();
        showing = false;

        lstIntelliSense = new ListView(keywords);
        keywords.sort((o1, o2) -> o1.compareToIgnoreCase(o2));


        root.getChildren().add(lstIntelliSense);
        stageIntelliSense.getContent().add(root);

    }

    public void selectItem(String s)
    {
        try
        {

            lstIntelliSense.setItems(keywords.filtered((String t) -> t.substring(0, s.length()).equals(s)));
            lstIntelliSense.getSelectionModel().select(0);
        }

        catch (Exception ex)
        {
            lstIntelliSense.setItems(keywords);
            ex.printStackTrace();
        }
    }


    public void goDown()
    {
        try
        {
            lstIntelliSense.getSelectionModel().selectNext();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    public void goUp()
    {
        try
        {
            lstIntelliSense.getSelectionModel().selectPrevious();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    public String getSelectedItem()
    {
        return lstIntelliSense.getSelectionModel().getSelectedItem();

    }

    public Popup getStage()
    {
        return stageIntelliSense;
    }

    public void show(Window owner)
    {
        stageIntelliSense.show(owner);
        showing = true;
    }

    public void hide()
    {
        stageIntelliSense.hide();
        showing = false;
    }

    public Boolean exists(String s)
    {
        if (s.length() > 0)
        {
            return keywords.filtered((String t) -> t.substring(0, s.length()).equals(s)).size() >= 1;
        }
        else
        {
            return false;
        }
    }

    public Boolean isShowing()
    {
        return showing;
    }
}
