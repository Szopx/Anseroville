package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.InventoryViewState;
import io.github.anseroville.viewModel.QuestViewState;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.EnumMap;
import java.util.Map;

public class QuestRenderer {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final BitmapFont rewardFont;
    private final BitmapFont itemFont;
    private Map<ItemType, Integer> requiredItems;
    private Map<ItemType, Integer> mainQuest;
    private final FarmViewModel viewModel;
    private final Texture questBackground;
    private final Map<ItemType, Texture> itemIcons;
    private final OrthographicCamera camera;
    private final Texture coin;

    public QuestRenderer(FarmViewModel viewModel, OrthographicCamera camera) {
        this.batch=new SpriteBatch();
        this.camera=camera;
        this.font=new BitmapFont();
        this.rewardFont=new BitmapFont();
        this.itemFont=new BitmapFont();
        this.font.getData().setScale(1.5f);
        this.rewardFont.getData().setScale(1.0f);
        this.itemFont.getData().setScale(1.2f);
        this.requiredItems=new EnumMap<>(ItemType.class);
        this.mainQuest=new EnumMap<>(ItemType.class);
        this.viewModel=viewModel;
        this.questBackground=new Texture("quests.png");
        this.itemIcons=new EnumMap<>(ItemType.class);
        this.itemIcons.put(ItemType.CARROT, new Texture("marchewka.png"));
        this.itemIcons.put(ItemType.POTATO, new Texture("potato.png"));
        this.itemIcons.put(ItemType.WHEAT, new Texture("wheat.png"));
        this.itemIcons.put(ItemType.CORN, new Texture("corn.png"));
        this.coin=new Texture("coin.png");
    }

    public void render() {
        batch.setProjectionMatrix(camera.combined);
        QuestViewState state=viewModel.getQuestViewState();
        InventoryViewState invState=viewModel.getInventoryViewState();

        requiredItems=state.getRequiredItems();
        mainQuest=state.getMainQuest();
        batch.begin();

        float bgWidth=420;
        float bgHeight=400;
        float topBarHeight=80;

        float drawX=camera.viewportWidth-bgWidth-15;
        float drawY=camera.viewportHeight-topBarHeight+20-bgHeight;

        batch.draw(questBackground, drawX, drawY, bgWidth, bgHeight);

        float leftMargin=30;
        float startX=drawX+leftMargin;

        float textStartX=drawX+145;
        float textStartY=drawY+bgHeight-70;

        font.draw(batch, "MAIN QUEST", textStartX, textStartY);

        float currentY=textStartY-35;

        int rewardMoney=state.getMainQuestRewardMoney();

        currentY=renderQuests(startX, currentY, mainQuest, invState, rewardMoney);

        if (!state.isActiveQuestAvailable()) {
            font.draw(batch, "YOU HAVE FINISHED", textStartX-45, currentY);
            font.draw(batch, "ALL SIDE QUESTS", textStartX-30, currentY-30);
            batch.end();
            return;
        }

        font.draw(batch, "SIDE QUEST", textStartX, currentY);

        currentY-=35;

        rewardMoney=state.getActiveQuestRewardMoney();

        renderQuests(startX, currentY, requiredItems, invState, rewardMoney);

        batch.end();
    }

    private float renderQuests(float startX, float currentY,
                               Map<ItemType, Integer> requiredItems,
                               InventoryViewState invState,
                               int rewardMoney){

        float textStartX=startX+115;

        rewardFont.draw(batch, "REWARD: ", textStartX, currentY);
        batch.draw(coin, textStartX+70, currentY-20, 30, 30);
        rewardFont.draw(batch, "x"+rewardMoney, textStartX+100, currentY);

        currentY-=40;
        float itemStartX=startX-5;

        for (Map.Entry<ItemType, Integer> requirement : requiredItems.entrySet()) {

            ItemType itemType=requirement.getKey();
            int requiredAmount=requirement.getValue();
            int currentAmount=invState.getAmount(itemType);
            Texture icon=itemIcons.get(itemType);

            if (icon!=null) {
                batch.draw(icon, itemStartX, currentY-25, 35, 35);
            }

            itemFont.draw(batch, currentAmount + "/" + requiredAmount, itemStartX+40, currentY);
            itemStartX+=100;
        }
        currentY-=45;
        return currentY;
    }

    public void dispose() {
        font.dispose();
        rewardFont.dispose();
        itemFont.dispose();
        batch.dispose();
        questBackground.dispose();
        coin.dispose();
        for (Texture texture : itemIcons.values()) {
            texture.dispose();
        }
    }

}
