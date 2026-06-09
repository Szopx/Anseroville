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
    final Texture lobbyTitleTexture;


    private final Texture helpWoodTexture;
    private final Texture ivyCornerTexture;
    //todo zamienić to na private i zrobić gettery
    final Texture gamblingTextureSelected;

     final Texture chickenTexture;
     //final Texture chickenTexture_selected;
     final Texture geeseTexture;
     //final Texture geeseTexture_selected;
     final Texture hedgehogTexture;
     //final Texture hedgehogTexture_selected;
     final Texture sheepTexture;
     //final Texture sheepTexture_selected;
     final Texture frogTexture;
     //final Texture frogTexture_selected;
     Texture titleTexture;
     Texture sidePanelTexture;
     Texture buttonTexture;
    private final Texture bridgeTexture;
    private final Texture bridgeTextureSelected;
    private final Texture playerFrontTexture;
    private final Texture playerBackTexture;
    private final Texture playerRightTexture;
    private final Texture playerLeftTexture;
    //private final Texture chickenTexture;
    private final Texture chickenTextureSelected;
    //private final Texture geeseTexture;
    private final Texture geeseTextureSelected;
    //private final Texture hedgehogTexture;
    private final Texture hedgehogTextureSelected;
    //private final Texture sheepTexture;
    private final Texture sheepTextureSelected;
    //private final Texture frogTexture;
    private final Texture frogTextureSelected;

    public AssetProvider() {
        //backgrounds
        this.titleTexture = new Texture("logo.png");
        this.lobbyTitleTexture = new Texture("logo.png");
        this.sidePanelTexture = new Texture("longpanel.png");
        this.buttonTexture = new Texture("button.png");
        this.backgroundTexture = new Texture("backgrounds/background.png");
        this.inventoryTexture = new Texture("backgrounds/inventory.png");
        this.shopInsideTexture = new Texture("backgrounds/shop.png");
        this.lobbyBackgroundTexture = new Texture("backgrounds/lobby_farm.png");
        this.springBackgroundTexture = new Texture("backgrounds/springbg.png");
        this.winterBackgroundTexture = new Texture("backgrounds/winterbg.png");
        this.autumnBackgroundTexture = new Texture("backgrounds/autumnbg.png");

        //lists
        this.plantTextures = new EnumMap<>(ItemType.class);
        this.itemTextures = new EnumMap<>(ItemType.class);

        //tiles
        this.fieldTexture = new Texture("tiles/field.png");
        this.wateredFieldTexture = new Texture("tiles/wateredfield.png");
        this.selectedFieldTexture = new Texture("tiles/field_selected.png");
        this.shopTexture = new Texture("tiles/geeseShop.png");
        this.shopTexture_selected = new Texture("tiles/geeseShop_selected.png");
        this.bridgeTexture= new Texture("tiles/bridge.png");
        this.bridgeTextureSelected= new Texture("tiles/bridge_selected.png");;



        //npcs
        this.gamblingTexture = new Texture("npcs/pidgeon/0.png");
        this.gamblingTextureSelected = new Texture("npcs/pidgeon/selected.png");
        this.chickenTexture = new Texture("npcs/chicken/0.png");
        this.chickenTextureSelected = new Texture("npcs/chicken/selected.png");;
        this.geeseTexture= new Texture("npcs/geese/0.png");
        this.geeseTextureSelected= new Texture("npcs/geese/selected.png");
        this.hedgehogTexture= new Texture("npcs/hedgehog/0.png");
        this.hedgehogTextureSelected= new Texture("npcs/hedgehog/selected.png");
        this.sheepTexture= new Texture("npcs/sheep/0.png");
        this.sheepTextureSelected= new Texture("npcs/sheep/selected.png");
        this.frogTexture= new Texture("npcs/frog/0.png");
        this.frogTextureSelected= new Texture("npcs/frog/selected.png");


        //loose
        this.questBackgroundTexture = new Texture("quests.png");
        this.coinTexture = new Texture("coin.png");
        this.helpWoodTexture = new Texture("help_wood.png");


        //player
        this.playerFrontTexture = new Texture("player/front/0.png");;
        this.playerBackTexture= new Texture("player/back/0.png");;
        this.playerRightTexture = new Texture("player/right/0.png");;
        this.playerLeftTexture = new Texture("player/left/0.png");;






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
        registerPlant(ItemType.CARROT, "plants/carrot/0.png", "plants/carrot/1.png", "plants/carrot/3.png");
        registerPlant(ItemType.CORN, "plants/corn/0.png", "plants/corn/1.png", "plants/corn/3.png");
        registerPlant(ItemType.WHEAT, "plants/wheat/0.png", "plants/wheat/1.png", "plants/wheat/3.png");
        registerPlant(ItemType.POTATO, "plants/potato/0.png", "plants/potato/1.png", "plants/potato/3.png");}
    //z jakiegoś powodu wheat jest niższy niż powinien, zamknę ten problem później todo wiem jak

    private void loadAllItems() {
        itemTextures.put(ItemType.CARROT, new Texture("plants/carrot/item.png"));
        itemTextures.put(ItemType.CORN, new Texture("plants/corn/item.png"));
        itemTextures.put(ItemType.WHEAT, new Texture("plants/wheat/item.png"));
        itemTextures.put(ItemType.POTATO, new Texture("plants/potato/item.png"));
        itemTextures.put(ItemType.CARROT_SEED, new Texture("plants/carrot/seed.png"));
        itemTextures.put(ItemType.CORN_SEED, new Texture("plants/corn/seed.png"));
        itemTextures.put(ItemType.WHEAT_SEED, new Texture("plants/wheat/seed.png"));
        itemTextures.put(ItemType.POTATO_SEED, new Texture("plants/potato/seed.png"));
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

    public Texture getTitleTexture()     { return titleTexture;     }  // title.png
    public Texture getSidePanelTexture() { return sidePanelTexture; }  // side.png
    public Texture getButtonTexture()    { return buttonTexture;    }  // button.png

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
