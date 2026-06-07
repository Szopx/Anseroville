package io.github.anseroville.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.InventoryViewState;
import io.github.anseroville.viewModel.QuestViewState;
import com.badlogic.gdx.graphics.OrthographicCamera;
import java.util.Map;

public class QuestRenderer {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final BitmapFont rewardFont;
    private final BitmapFont itemFont;
    private final AssetProvider assetProvider;
    private final FarmViewModel viewModel;
    private final OrthographicCamera camera;

    public QuestRenderer(FarmViewModel viewModel,
                         OrthographicCamera camera,
                         AssetProvider assetProvider,
                         SpriteBatch batch) {
        this.batch=batch;
        this.camera=camera;
        this.assetProvider=assetProvider;
        this.font=assetProvider.getMediumFont();
        this.rewardFont=assetProvider.getSmallestFont();
        this.itemFont=assetProvider.getSmallFont();
        this.viewModel=viewModel;
    }

    public void render() {
        batch.setProjectionMatrix(camera.combined);
        QuestViewState state=viewModel.getQuestViewState();
        InventoryViewState invState=viewModel.getInventoryViewState();

        Map<ItemType, Integer> requiredItems=state.getRequiredItems();
        Map<ItemType, Integer> mainQuest=state.getMainQuest();
        batch.begin();

        if (state.isGameFinished()) {
            float bgWidth = 420;
            float bgHeight = 220;
            float drawX = camera.viewportWidth - bgWidth - 15;
            float drawY = camera.viewportHeight - bgHeight - 60;

            batch.draw(assetProvider.getQuestBackgroundTexture(), drawX, drawY, bgWidth, bgHeight);
            font.draw(batch, "GAME FINISHED", drawX + 105, drawY + 140);
            font.draw(batch, "ALL LEVELS DONE", drawX + 90, drawY + 100);

            batch.end();
            return;
        }

        float bgWidth=420;
        float bgHeight=400;
        float topBarHeight=80;

        float drawX=camera.viewportWidth-bgWidth-15;
        float drawY=camera.viewportHeight-topBarHeight+20-bgHeight;

        batch.draw(assetProvider.getQuestBackgroundTexture(), drawX, drawY, bgWidth, bgHeight);

        float leftMargin=30;
        float startX=drawX+leftMargin;

        float textStartX=drawX+145;
        float textStartY=drawY+bgHeight-70;

        font.draw(batch, "MAIN QUEST", textStartX, textStartY);

        float currentY=textStartY-35;

        currentY=renderQuests(startX, currentY, mainQuest, invState, -1);

        if (!state.isActiveQuestAvailable()) {
            font.draw(batch, "YOU HAVE FINISHED", textStartX-45, currentY);
            font.draw(batch, "ALL SIDE QUESTS", textStartX-30, currentY-30);
            batch.end();
            return;
        }

        font.draw(batch, "SIDE QUEST", textStartX, currentY);

        currentY-=35;

        int rewardMoney=state.getActiveQuestRewardMoney();

        renderQuests(startX, currentY, requiredItems, invState, rewardMoney);

        batch.end();
    }

    private float renderQuests(float startX, float currentY,
                               Map<ItemType, Integer> requiredItems,
                               InventoryViewState invState,
                               int rewardMoney){

        float textStartX=startX+115;
        if(rewardMoney!=-1) {
            rewardFont.draw(batch, "REWARD: ", textStartX, currentY);
            batch.draw(assetProvider.getCoinTexture(), textStartX + 70, currentY - 20, 30, 30);
            rewardFont.draw(batch, "x" + rewardMoney, textStartX + 100, currentY);
            currentY-=40;
        }
        float itemStartX=startX-5;

        for (Map.Entry<ItemType, Integer> requirement : requiredItems.entrySet()) {

            ItemType itemType=requirement.getKey();
            int requiredAmount=requirement.getValue();
            int currentAmount=invState.getAmount(itemType);
            Texture icon=assetProvider.getItemTexture(itemType);

            if (icon!=null) {
                batch.draw(icon, itemStartX, currentY-25, 35, 35);
            }

            itemFont.draw(batch, currentAmount + "/" + requiredAmount, itemStartX+40, currentY);
            itemStartX+=100;
        }
        currentY-=45;
        return currentY;
    }

}
