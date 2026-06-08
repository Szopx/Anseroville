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
     final Texture springBackgroundTexture;
     final Texture winterBackgroundTexture;
     final Texture autumnBackgroundTexture;


    private final Map<ItemType, Map<GrowingState, Texture>> plantTextures;
    private final Map<ItemType, Texture> itemTextures;
    private final Texture fieldTexture;
    private final Texture wateredFieldTexture;
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

    private final Texture shopTexture;
    private final Texture shopTexture_selected;
    private final Texture gamblingTexture;


    private final Texture helpWoodTexture;
    private final Texture ivyCornerTexture;
    //todo zamienić to na private i zrobić gettery

    private final Texture bridgeTexture;
    private final Texture bridgeTextureSelected;
    private final Texture playerFrontTexture;
    private final Texture playerBackTexture;
    private final Texture playerRightTexture;
    private final Texture playerLeftTexture;
    private final Texture chickenTexture;
    private final Texture chickenTextureSelected;
    private final Texture geeseTexture;
    private final Texture geeseTextureSelected;
    private final Texture hedgehogTexture;
    private final Texture hedgehogTextureSelected;
    private final Texture sheepTexture;
    private final Texture sheepTextureSelected;
    private final Texture frogTexture;
    private final Texture frogTextureSelected;

    public AssetProvider() {
        this.backgroundTexture = new Texture("background.png");
        this.plantTextures = new EnumMap<>(ItemType.class);
        this.itemTextures = new EnumMap<>(ItemType.class);
        this.fieldTexture = new Texture("tiles/field.png");
        this.wateredFieldTexture = new Texture("tiles/wateredtile.png");
        this.selectedFieldTexture = new Texture("tiles/field_selected.png");
        this.inventoryTexture = new Texture("inventory.png");
        this.questBackgroundTexture = new Texture("quests.png");
        this.coinTexture = new Texture("coin.png");
        this.shopInsideTexture = new Texture("shop.png");
        this.lobbyBackgroundTexture = new Texture("lobby_farm.jpg");

        this.shopTexture = new Texture("tiles/geeseShop.png");
        this.shopTexture_selected = new Texture("tiles/geeseShop_selected.png");
        this.gamblingTexture = new Texture("npcs/pidgeon.png");

        this.helpWoodTexture = new Texture("help_wood.png");

        this.bridgeTexture= new Texture("tiles/bridge.png");
        this.bridgeTextureSelected= new Texture("tiles/bridge_selected.png");;
        this.playerFrontTexture = new Texture("player_front.png");;
        this.playerBackTexture= new Texture("player_back.png");;
        this.playerRightTexture = new Texture("player_right.png");;
        this.playerLeftTexture = new Texture("player_left.png");;

        this.chickenTexture = new Texture("npcs/baby_chick.png");
        this.chickenTextureSelected = new Texture("npcs/babychick_selected.png");;
        this.geeseTexture= new Texture("npcs/geese.png");
        this.geeseTextureSelected= new Texture("npcs/geese_selected.png");
        this.hedgehogTexture= new Texture("npcs/hedgehog.png");
        this.hedgehogTextureSelected= new Texture("npcs/hedgehog_selected.png");
        this.sheepTexture= new Texture("npcs/sheep.png");
        this.sheepTextureSelected= new Texture("npcs/sheep_selected.png");
        this.frogTexture= new Texture("npcs/frog.png");
        this.frogTextureSelected= new Texture("npcs/frog_selected.png");

        this.springBackgroundTexture = new Texture("springbg.png");
        this.winterBackgroundTexture = new Texture("winterbg.png");
        this.autumnBackgroundTexture = new Texture("autumnbg.png");

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

    public Texture getBridgeTexture() {
        return bridgeTexture;
    }

    public Texture getBridgeTextureSelected() {
        return bridgeTextureSelected;
    }

    public Texture getPlayerFrontTexture() {
        return playerFrontTexture;
    }

    public Texture getPlayerBackTexture() {
        return playerBackTexture;
    }

    public Texture getPlayerRightTexture() {
        return playerRightTexture;
    }

    public Texture getPlayerLeftTexture() {
        return playerLeftTexture;
    }

    public Texture getChickenTexture() {
        return chickenTexture;
    }

    public Texture getChickenTextureSelected() {
        return chickenTextureSelected;
    }

    public Texture getGeeseTexture() {
        return geeseTexture;
    }

    public Texture getGeeseTextureSelected() {
        return geeseTextureSelected;
    }

    public Texture getHedgehogTexture() {
        return hedgehogTexture;
    }

    public Texture getHedgehogTextureSelected() {
        return hedgehogTextureSelected;
    }

    public Texture getSheepTexture() {
        return sheepTexture;
    }

    public Texture getSheepTextureSelected() {
        return sheepTextureSelected;
    }

    public Texture getFrogTexture() {
        return frogTexture;
    }

    public Texture getFrogTextureSelected() {
        return frogTextureSelected;
    }

    private void registerPlant(ItemType type, String smallPath, String mediumPath, String largePath) {
        Map<GrowingState, Texture> stateMap = new EnumMap<>(GrowingState.class);
        stateMap.put(GrowingState.SMALL, new Texture(smallPath));
        stateMap.put(GrowingState.MEDIUM, new Texture(mediumPath));
        stateMap.put(GrowingState.LARGE, new Texture(largePath));
        plantTextures.put(type, stateMap);
    }

    private void loadAllPlants() {
        registerPlant(ItemType.CARROT, "plants/carrot_0.png", "plants/carrot_1.png", "plants/carrot_3.png");
        registerPlant(ItemType.CORN, "plants/corn_0.png", "plants/corn_1.png", "plants/corn_3.png");
        registerPlant(ItemType.WHEAT, "plants/wheat_0.png", "plants/wheat_1.png", "plants/wheat_3.png");
        registerPlant(ItemType.POTATO, "plants/potato_0.png", "plants/potato_1.png", "plants/potato_3.png");}
    //z jakiegoś powodu wheat jest niższy niż powinien, zamknę ten problem później

    private void loadAllItems() {
        itemTextures.put(ItemType.CARROT, new Texture("plants/carrot.png"));
        itemTextures.put(ItemType.CORN, new Texture("plants/corn.png"));
        itemTextures.put(ItemType.WHEAT, new Texture("plants/wheat.png"));
        itemTextures.put(ItemType.POTATO, new Texture("plants/potato.png"));
        itemTextures.put(ItemType.CARROT_SEED, new Texture("plants/carrot_seed.png"));
        itemTextures.put(ItemType.CORN_SEED, new Texture("plants/corn_seed.png"));
        itemTextures.put(ItemType.WHEAT_SEED, new Texture("plants/wheat_seed.png"));
        itemTextures.put(ItemType.POTATO_SEED, new Texture("plants/potato_seed.png"));
        itemTextures.put(ItemType.SHIELD, new Texture("shield.png"));
        itemTextures.put(ItemType.TORCH, new Texture("lamp.png"));
        itemTextures.put(ItemType.WATERING_CAN, new Texture("wateringcan.png"));
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
    public Texture getShopTexture(){return shopTexture;}
    public Texture getGamblingTexture(){return gamblingTexture;}

    public Texture getFieldTexture(){
        return fieldTexture;
    }

    public Texture getWateredFieldTexture() {
        return wateredFieldTexture;
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
    public Texture getSelectedShopTexture() {
        return shopTexture_selected;
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
