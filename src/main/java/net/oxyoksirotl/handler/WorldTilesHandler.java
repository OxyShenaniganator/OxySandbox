package net.oxyoksirotl.handler;

import net.oxyoksirotl.GamePanel;
import net.oxyoksirotl.entity.TileEntity;
import org.json.simple.JSONArray;
import net.oxyoksirotl.utils.OpenSimplex2S;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WorldTilesHandler {

    GamePanel gamePanel;
    public ArrayList<TileEntity> availableTile;

    public int maxMapSize;
    public TileEntity[][] generatedMap;

    public WorldTilesHandler(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        this.availableTile = new ArrayList<>();
        this.maxMapSize = 257;
        this.generatedMap = new TileEntity[this.maxMapSize][this.maxMapSize];

        this.initTiles();

    }

    public TileEntity tileDeterminer(int seed, int x, int y) {

        double newX = x*0.03;
        double newY = y*0.03;

        if (OpenSimplex2S.noise2_ImproveX(seed, newX, newY) <= -0.6 ) return availableTile.get(0);
        else if (OpenSimplex2S.noise2_ImproveX(seed, newX, newY) <= -0.3 ) return availableTile.get(1);
        else if (OpenSimplex2S.noise2_ImproveX(seed * 2, newX, newY) <= 0.1 && Math.random() < 0.15) return availableTile.get(3);
        else if (OpenSimplex2S.noise2_ImproveX(seed * 3, newX, newY) <= 0.1 && Math.random() < 0.15) return availableTile.get(4);
        else return availableTile.get(2);

    }

    public void initTiles() {

        JSONArray tileList;
        JSONArray tileCollision;
        BufferedImage tileImg;

        tileList = (JSONArray) ResourcesHandler.loadJson("/assets/tiles/tiles.json").get("availableTiles");

        for (int i=0; i<tileList.size(); i++)
        {
            tileImg = ResourcesHandler.loadImg("/assets/tiles/" + tileList.get(i).toString() + "/" + tileList.get(i).toString() + ".png");
            tileCollision = (JSONArray) ResourcesHandler.loadJson("/assets/tiles/" + tileList.get(i).toString() + "/tileSettings.json").get("enableCollision");
            for(int j=0; j<tileImg.getHeight()/24; j++) {
                for (int k=0; k<tileImg.getWidth()/24; k++) {
                    this.availableTile.add(new TileEntity(tileImg.getSubimage(k*24,j*24,24,24), (boolean)tileCollision.get(k)));
                    System.out.println((boolean)tileCollision.get(k));
                }
            }
        }

    }

}
