package com.dangugly.game.TileManager;

import com.dangugly.game.graphics.Sprite;
import com.dangugly.game.util.Camera;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class TileManager {

    public static ArrayList<TileMap> tm;
    public Camera cam;

    public TileManager(){
        tm = new ArrayList<TileMap>();
    }

    public TileManager(String path, Camera cam){
        tm = new ArrayList<TileMap>();
        addTileMap(path, 64, 64, cam);
    }

    private void addTileMap(String path, int blockWidth, int blockHeight, Camera cam){
        this.cam = cam;
        String imagePath;

        int width = 0;
        int height =  0;
        int tileWidth;
        int tileHeight;
        int tileCount;
        int tileColumns;
        int layers = 0;
        Sprite sprite;

        String[] data = new String[10];

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element eElement = (Element) node;

            imagePath = eElement.getAttribute("name");
            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
            tileCount = Integer.parseInt(eElement.getAttribute("tilecount"));
            tileColumns = Integer.parseInt(eElement.getAttribute("columns"));

            sprite = new Sprite("tile/" + imagePath + ".png", tileWidth, tileHeight);

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();

            for (int i = 0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                if (i <= 0) {
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }

                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();

                if(i >= 1){
                    tm.add(new TileMapNorm(data[i],sprite,width,height,blockWidth,blockHeight,tileColumns));
                } else {
                    tm.add(new TileMapObj(data[i],sprite,width,height,blockWidth,blockHeight,tileColumns));
                }
            }

            cam.setLimit(width * blockWidth, height * blockHeight);

        } catch (Exception e) {
            System.out.println("ERROR: Tilemanager: Can not read tilemap");
            e.printStackTrace();
        }
    }

    public void render(Graphics2D g){
        for (int i =0; i<tm.size(); i++){
            if(cam == null) return;
            tm.get(i).render(g, cam.getBounds());
        }
    }
}
