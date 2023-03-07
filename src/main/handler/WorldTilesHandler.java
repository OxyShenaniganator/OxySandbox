package main.handler;

import main.GamePanel;
import main.entity.TileEntity;
import org.json.simple.JSONArray;
import utils.OpenSimplex2S;

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
        this.maxMapSize = 99;
        this.generatedMap = new TileEntity[this.maxMapSize][this.maxMapSize];

        this.initTiles();
        // this.populateTile();
        this.generateMap();

    }

    public void generateMap() {

        int x = 0;
        int y = 0;
        long seed = Math.round(Math.random()*1000000);

        if (generatedMap != null) {
            for (int i = 0; i < maxMapSize; i ++) {
                for (int j = 0; j < maxMapSize; j ++) {

                    double newI = i*0.07;
                    double newJ = j*0.07;

                    if (OpenSimplex2S.noise2_ImproveX(seed, newI, newJ) <= -0.2 ) generatedMap[x][y] = availableTile.get(0);
                    else if (OpenSimplex2S.noise2_ImproveX(seed, newI, newJ) <= 0.08 ) generatedMap[x][y] = availableTile.get(1);
                    else generatedMap[x][y] = availableTile.get(2);

                    y++;
                }
                x++;
                y=0;
            }
        }
    }

    public void initTiles() {

        JSONArray tileList;
        BufferedImage tileImg;

        tileList = (JSONArray) ResourcesHandler.loadJson("/tiles/tiles.json").get("availableTiles");

        for (int i=0; i<tileList.size(); i++)
        {
            tileImg = ResourcesHandler.loadImg("/tiles/" + tileList.get(i).toString() + ".png");
            for(int j=0; j<tileImg.getHeight()/24; j++) {
                for (int k=0; k<tileImg.getWidth()/24; k++) {
                    this.availableTile.add(new TileEntity(tileImg.getSubimage(k*24,j*24,24,24), false));
                }
            }
        }

    }

    public TileEntity getTile(int x, int y) {

        int halfMapSize = (int)Math.floor(maxMapSize/2);

        return generatedMap[x+(halfMapSize)][y+halfMapSize];
    }

}
