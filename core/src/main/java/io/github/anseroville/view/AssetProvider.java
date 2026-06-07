package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.anseroville.enums.GrowingState;
import io.github.anseroville.enums.ItemType;
import java.util.EnumMap;
import java.util.Map;

public class AssetProvider {
    private final Texture backgroundTexture;
    private final Map<ItemType, Map<GrowingState, Texture>> plantTextures;
    private final Map<ItemType, Texture> itemTextures;
    private final Texture fieldTexture;
    private final Texture selectedFieldTexture;
    private final Texture darknessTexture;
    private final Texture inventoryTexture;
    private final Texture questBackgroundTexture;
    private final Texture coinTexture;
    private final Texture shopInsideTexture;
    private final BitmapFont bigFont;
    private final BitmapFont smallestFont;
    private final BitmapFont mediumFont;
    private final BitmapFont smallFont;
    private final Texture lobbyBackgroundTexture;

    private final Texture helpWoodTexture;
    private final Texture ivyCornerTexture;

    public AssetProvider() {
        this.backgroundTexture = new Texture("background.png");
        this.plantTextures = new EnumMap<>(ItemType.class);
        this.itemTextures = new EnumMap<>(ItemType.class);
        this.fieldTexture = new Texture("field.png");
        this.selectedFieldTexture = new Texture("field_selected.png");
        this.inventoryTexture = new Texture("inventory.png");
        this.questBackgroundTexture = new Texture("quests.png");
        this.coinTexture = new Texture("coin.png");
        this.shopInsideTexture = new Texture("1780525878689.png");
        this.lobbyBackgroundTexture = new Texture("lobby_farm.jpg");

        this.helpWoodTexture = new Texture("help_wood.png");

        if (Gdx.files.internal("ivy_corner.png").exists()) {
            this.ivyCornerTexture = new Texture("ivy_corner.png");
        } else {
            this.ivyCornerTexture = null;
        }

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        this.darknessTexture = new Texture(pixmap);
        pixmap.dispose();

        this.bigFont = new BitmapFont();
        this.bigFont.getData().setScale(2f);

        this.smallestFont=new BitmapFont();
        this.smallestFont.getData().setScale(1f);

        this.mediumFont=new BitmapFont();
        this.mediumFont.getData().setScale(1.5f);

        this.smallFont=new BitmapFont();
        this.smallFont.getData().setScale(1.2f);

        loadAllPlants();
        loadAllItems();
    }

    private void registerPlant(ItemType type, String smallPath, String mediumPath, String largePath) {
        Map<GrowingState, Texture> stateMap = new EnumMap<>(GrowingState.class);
        stateMap.put(GrowingState.SMALL, new Texture(smallPath));
        stateMap.put(GrowingState.MEDIUM, new Texture(mediumPath));
        stateMap.put(GrowingState.LARGE, new Texture(largePath));
        plantTextures.put(type, stateMap);
    }

    private void loadAllPlants() {
        registerPlant(ItemType.CARROT, "carrot_0.png", "carrot_1.png", "carrot_3.png");
        registerPlant(ItemType.CORN, "corn_0.png", "corn_1.png", "corn_3.png");
        registerPlant(ItemType.WHEAT, "wheat_0.png", "wheat_1.png", "wheat_3.png");
        registerPlant(ItemType.POTATO, "potato_0.png", "potato_1.png", "potato_3.png");}
    //z jakiegoś powodu wheat jest niższy niż powinien, zamknę ten problem później

    private void loadAllItems() {
        itemTextures.put(ItemType.CARROT, new Texture("carrot.png"));
        itemTextures.put(ItemType.CORN, new Texture("corn.png"));
        itemTextures.put(ItemType.WHEAT, new Texture("wheat.png"));
        itemTextures.put(ItemType.POTATO, new Texture("potato.png"));
        itemTextures.put(ItemType.CARROT_SEED, new Texture("carrot_seed.png"));
        itemTextures.put(ItemType.CORN_SEED, new Texture("corn_seed.png"));
        itemTextures.put(ItemType.WHEAT_SEED, new Texture("wheat_seed.png"));
        itemTextures.put(ItemType.POTATO_SEED, new Texture("potato_seed.png"));
        itemTextures.put(ItemType.SHIELD, new Texture("shield.png"));
        itemTextures.put(ItemType.TORCH, new Texture("lamp.png"));
    }

    public Texture getBackgroundTexture(){
        return backgroundTexture;
    }

    public Texture getPlantTexture(ItemType type, GrowingState state) {
        if (plantTextures.containsKey(type)) {
            return plantTextures.get(type).get(state);
        }
        return null;
    }

    public Texture getItemTexture(ItemType type) {
        if (itemTextures.containsKey(type)) {
            return itemTextures.get(type);
        }
        return null;
    }

    public Texture getFieldTexture(){
        return fieldTexture;
    }

    public Texture getSelectedFieldTexture(){
        return selectedFieldTexture;
    }

    public Texture getDarknessTexture(){
        return darknessTexture;
    }

    public Texture getInventoryTexture(){
        return inventoryTexture;
    }

    public Texture getQuestBackgroundTexture(){
        return questBackgroundTexture;
    }

    public Texture getCoinTexture(){
        return coinTexture;
    }

    public Texture getShopInsideTexture(){
        return shopInsideTexture;
    }

    public BitmapFont getBigFont(){
        return bigFont;
    }

    public BitmapFont getSmallestFont(){
        return smallestFont;
    }

    public BitmapFont getMediumFont(){
        return mediumFont;
    }

    public BitmapFont getSmallFont(){
        return smallFont;
    }

    public Texture getLobbyBackgroundTexture() {
        return lobbyBackgroundTexture;
    }

    public Texture getHelpWoodTexture() {
        return helpWoodTexture;
    }

    public Texture getIvyCornerTexture() {
        return ivyCornerTexture;
    }

    public void dispose(){
        backgroundTexture.dispose();
        fieldTexture.dispose();
        selectedFieldTexture.dispose();
        darknessTexture.dispose();
        inventoryTexture.dispose();
        questBackgroundTexture.dispose();
        coinTexture.dispose();
        shopInsideTexture.dispose();
        lobbyBackgroundTexture.dispose();

        for (Map<GrowingState, Texture> stateMap : plantTextures.values()) {
            for (Texture texture : stateMap.values()) {
                texture.dispose();
            }
        }

        for (Texture texture : itemTextures.values()) {
            texture.dispose();
        }

        bigFont.dispose();
        smallestFont.dispose();
        mediumFont.dispose();
        smallFont.dispose();
        helpWoodTexture.dispose();

        if (ivyCornerTexture != null) {
            ivyCornerTexture.dispose();
        }
    }
}
