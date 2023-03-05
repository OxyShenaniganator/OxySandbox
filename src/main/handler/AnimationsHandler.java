package main.handler;

import main.entity.SpawnedEntity;
import org.json.simple.JSONArray;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class AnimationsHandler {

    public AnimationsHandler() {

    }

    public static HashMap<String, ArrayList<BufferedImage>> initEntityAnimation(SpawnedEntity spawnedEntity) {

        JSONArray animationSeqArray = (JSONArray)spawnedEntity.getEntityData().get("animationSeq");
        String[] animationSeq = new String[animationSeqArray.size()];
        ArrayList<BufferedImage> animationArray = new ArrayList<>();
        HashMap<String, ArrayList<BufferedImage>> animationHash = new HashMap<>();

        if (animationSeqArray != null) {
            for (int i=0; i<animationSeqArray.size(); i++) {
                animationSeq[i] = ((String)animationSeqArray.get(i));
            }
        }

        for (int i = 0; i< spawnedEntity.getEntityRow(); i++) {
            for (int j = 0; j< spawnedEntity.getEntityCol(); j++) {
                System.out.println("[SpawnedEntity/INFO] Handling entity animations " + "["+ i + "," + j + "]" );
                animationArray.add(spawnedEntity.getEntitySprite(i,j));
            }
            System.out.println("[SpawnedEntity/INFO] Import animation: " + animationSeq[i]);
            animationHash.put(animationSeq[i], (ArrayList)animationArray.clone());
            animationArray.clear();
        }

        return animationHash;

    }

}
