package io.github.anseroville.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
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
    final Texture lobbyTitleTexture;


    private final Texture helpWoodTexture;
    final Texture gamblingTextureSelected;

    private final Animation<TextureRegion> playerFrontAnim;
    private final Animation<TextureRegion> playerBackAnim;
    private final Animation<TextureRegion> playerRightAnim;
    private final Animation<TextureRegion> playerLeftAnim;
    private final Animation<TextureRegion> chickenAnim;
    private final Animation<TextureRegion> geeseAnim;
    private final Animation<TextureRegion> hedgehogAnim;
    private final Animation<TextureRegion> sheepAnim;
    private final Animation<TextureRegion> frogAnim;
    private final Animation<TextureRegion> pidgeonAnim;
    private final Array<Texture> animationFrames;
    Texture titleTexture;
    Texture sidePanelTexture;
    Texture buttonTexture;
    Texture comix1;
    Texture comix2;
    Texture comix3;
    Texture comix4;
    Texture comix5;
    Texture comix6;
    Texture help;

    private final Texture bridgeTexture;
    private final Texture bridgeTextureSelected;
    private final Texture chickenTextureSelected;
    private final Texture geeseTextureSelected;
    private final Texture hedgehogTextureSelected;
    private final Texture sheepTextureSelected;
    private final Texture frogTextureSelected;
    private final Texture nightWithLampTexture;
    private final Texture nightWithoutLampTexture;
    Texture rabus;

    public AssetProvider() {
        //backgrounds todo dispose wszystko
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
        this.nightWithLampTexture = new Texture("backgrounds/nightWithlamp.png");
        this.nightWithoutLampTexture = new Texture("backgrounds/nightnolamp.png");

        //lists
        this.plantTextures = new EnumMap<>(ItemType.class);
        this.itemTextures = new EnumMap<>(ItemType.class);
        this.rabus = new Texture("rabus.png");
        this.comix1 = new Texture("cfrog.png");
        this.comix2 = new Texture("cchicken.png");
        this.comix3 = new Texture("chedgehog.png");
        this.comix4 = new Texture("csheep.png");
        this.comix5 = new Texture("final.png");
        this.comix6 = new Texture("end.png");
        this.help = new Texture("help.png");

        //tiles
        this.fieldTexture = new Texture("tiles/field.png");
        this.wateredFieldTexture = new Texture("tiles/wateredfield.png");
        this.selectedFieldTexture = new Texture("tiles/field_selected.png");
        this.shopTexture = new Texture("tiles/geeseShop.png");
        this.shopTexture_selected = new Texture("tiles/geeseShop_selected.png");
        this.bridgeTexture= new Texture("tiles/bridge.png");
        this.bridgeTextureSelected= new Texture("tiles/bridge_selected.png");;

        //npcs
        this.gamblingTextureSelected = new Texture("npcs/pidgeon/selected.png");
        this.animationFrames = new Array<>();
        this.playerFrontAnim = loadAnimation(0.2f, "player/front/0.png", "player/front/1.png", "player/front/2.png");
        this.playerBackAnim = loadAnimation(0.2f, "player/back/0.png", "player/back/1.png", "player/back/2.png");
        this.playerRightAnim = loadAnimation(0.2f, "player/right/0.png", "player/right/1.png", "player/right/2.png");
        this.playerLeftAnim = loadAnimation(0.2f, "player/left/0.png", "player/left/1.png", "player/left/2.png");

        this.chickenAnim = loadAnimation(0.4f, "npcs/chicken/0.png", "npcs/chicken/1.png");
        this.geeseAnim = loadAnimation(0.4f, "npcs/geese/0.png", "npcs/geese/1.png", "npcs/geese/3.png");
        this.hedgehogAnim = loadAnimation(0.4f, "npcs/hedgehog/0.png", "npcs/hedgehog/1.png", "npcs/hedgehog/2.png");
        this.sheepAnim = loadAnimation(0.4f, "npcs/sheep/0.png", "npcs/sheep/1.png", "npcs/sheep/2.png");
        this.frogAnim = loadAnimation(0.25f, "npcs/frog/0.png", "npcs/frog/1.png", "npcs/frog/2.png", "npcs/frog/3.png", "npcs/frog/4.png", "npcs/frog/5.png", "npcs/frog/6.png", "npcs/frog/7.png", "npcs/frog/8.png");
        this.pidgeonAnim = loadAnimation(1f, "npcs/pidgeon/0.png", "npcs/pidgeon/1.png");

        this.chickenTextureSelected = new Texture("npcs/chicken/selected.png");
        this.geeseTextureSelected= new Texture("npcs/geese/selected.png");
        this.hedgehogTextureSelected= new Texture("npcs/hedgehog/selected.png");
        this.sheepTextureSelected= new Texture("npcs/sheep/selected.png");
        this.frogTextureSelected= new Texture("npcs/frog/selected.png");

        //loose
        this.questBackgroundTexture = new Texture("quests.png");
        this.coinTexture = new Texture("coin.png");
        this.helpWoodTexture = new Texture("help_wood.png");


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

    private Animation<TextureRegion> loadAnimation(float frameDuration, String... paths) {
        Array<TextureRegion> frames = new Array<>();
        for (String path : paths) {
            Texture tex = new Texture(path);
            animationFrames.add(tex);
            frames.add(new TextureRegion(tex));
        }
        return new Animation<>(frameDuration, frames);
    }

    public Animation<TextureRegion> getPlayerFrontAnim() { return playerFrontAnim; }
    public Animation<TextureRegion> getPlayerBackAnim() { return playerBackAnim; }
    public Animation<TextureRegion> getPlayerRightAnim() { return playerRightAnim; }
    public Animation<TextureRegion> getPlayerLeftAnim() { return playerLeftAnim; }
    public Animation<TextureRegion> getChickenAnim() { return chickenAnim; }
    public Animation<TextureRegion> getGeeseAnim() { return geeseAnim; }
    public Animation<TextureRegion> getHedgehogAnim() { return hedgehogAnim; }
    public Animation<TextureRegion> getSheepAnim() { return sheepAnim; }
    public Animation<TextureRegion> getFrogAnim() { return frogAnim; }
    public Animation<TextureRegion> getPidgeonAnim() { return pidgeonAnim; }

    public Texture getBridgeTexture() {
        return bridgeTexture;
    }

    public Texture getBridgeTextureSelected() {
        return bridgeTextureSelected;
    }

    public Texture getChickenTextureSelected() {
        return chickenTextureSelected;
    }

    public Texture getGeeseTextureSelected() {
        return geeseTextureSelected;
    }

    public Texture getHedgehogTextureSelected() {
        return hedgehogTextureSelected;
    }

    public Texture getSheepTextureSelected() {
        return sheepTextureSelected;
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

    public Texture getSelectedShopTexture() {
        return shopTexture_selected;
    }

    public Texture getTitleTexture() {
        return titleTexture;
    }

    public Texture getSidePanelTexture() {
        return sidePanelTexture;
    }

    public Texture getButtonTexture() {
        return buttonTexture;
    }

    public Texture getNightWithLampTexture(){
        return nightWithLampTexture;
    }

    public Texture getNightWithoutLampTexture() {
        return nightWithoutLampTexture;
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
        nightWithLampTexture.dispose();
        nightWithoutLampTexture.dispose();

        for (Map<GrowingState, Texture> stateMap : plantTextures.values()) {
            for (Texture texture : stateMap.values()) {
                texture.dispose();
            }
        }

        for (Texture texture : itemTextures.values()) {
            texture.dispose();
        }

        for (Texture texture : animationFrames) { texture.dispose(); }

        bigFont.dispose();
        smallestFont.dispose();
        mediumFont.dispose();
        smallFont.dispose();
        helpWoodTexture.dispose();
    }
}
