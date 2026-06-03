package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.anseroville.enums.GrowingState;
import io.github.anseroville.enums.ItemType;
import java.util.EnumMap;
import java.util.Map;

public class AssetProvider {
    private final Texture background;
    private final Map<ItemType, Map<GrowingState, Texture>> plantTextures;
    private final Texture field;
    private final Texture selectedField;
    private final Texture darknessTexture;
    private final BitmapFont bigFont;
    private final BitmapFont smallestFont;

    public AssetProvider() {
        this.background = new Texture("tlo.png");
        this.plantTextures = new EnumMap<>(ItemType.class);
        this.field = new Texture("pole.png");
        this.selectedField = new Texture("pole_zaznaczone.png");

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        this.darknessTexture = new Texture(pixmap);
        pixmap.dispose();

        this.bigFont = new BitmapFont();
        this.bigFont.getData().setScale(2f);

        this.smallestFont=new BitmapFont();
        this.smallestFont.getData().setScale(1f);

        loadAllPlants();
    }

    private void registerPlant(ItemType type, String smallPath, String mediumPath, String largePath) {
        Map<GrowingState, Texture> stateMap = new EnumMap<>(GrowingState.class);
        stateMap.put(GrowingState.SMALL, new Texture(smallPath));
        stateMap.put(GrowingState.MEDIUM, new Texture(mediumPath));
        stateMap.put(GrowingState.LARGE, new Texture(largePath));

        plantTextures.put(type, stateMap);
    }

    private void loadAllPlants() {
        registerPlant(ItemType.CARROT, "marchewki_0.png", "marchewki_1.png", "marchewki_3.png");
        registerPlant(ItemType.CORN, "corn_0.png", "corn_1.png", "corn_3.png");
        registerPlant(ItemType.WHEAT, "wheat_0.png", "wheat_1.png", "wheat_3.png");
        registerPlant(ItemType.POTATO, "potato_0.png", "potato_1.png", "potato_3.png");}
    //z jakiegoś powodu wheat jest niższy niż powinien, zamknę ten problem później

    public Texture getBackground(){
        return background;
    }

    public Texture getPlantTexture(ItemType type, GrowingState state) {
        if (plantTextures.containsKey(type)) {
            return plantTextures.get(type).get(state);
        }
        return null;
    }

    public Texture getField(){
        return field;
    }

    public Texture getSelectedField(){
        return selectedField;
    }

    public Texture getDarknessTexture(){
        return darknessTexture;
    }

    public BitmapFont getBigFont(){
        return bigFont;
    }

    public BitmapFont getSmallestFont(){
        return smallestFont;
    }

    public void dispose(){
        background.dispose();
        field.dispose();
        selectedField.dispose();
        darknessTexture.dispose();

        for (Map<GrowingState, Texture> stateMap : plantTextures.values()) {
            for (Texture texture : stateMap.values()) {
                texture.dispose();
            }
        }

        bigFont.dispose();

    }
}
