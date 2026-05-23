package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.FarmViewModel;
import io.github.anseroville.viewModel.InventoryViewState;
import io.github.anseroville.viewModel.QuestViewState;

import java.util.EnumMap;
import java.util.Map;

public class QuestRenderer {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private Map<ItemType, Integer> requiredItems;
    private final FarmViewModel viewModel;
    private final Texture questBackground;
    private final Map<ItemType, Texture> itemIcons;

    public QuestRenderer(FarmViewModel viewModel) {
        this.batch=new SpriteBatch();
        this.font=new BitmapFont();
        this.font.getData().setScale(1.5f);
        this.requiredItems=new EnumMap<>(ItemType.class);
        this.viewModel=viewModel;
        this.questBackground=new Texture("quests.png");
        this.itemIcons=new EnumMap<>(ItemType.class);
        this.itemIcons.put(ItemType.CARROT, new Texture("marchewka.png"));
        this.itemIcons.put(ItemType.POTATO, new Texture("potato.png"));
        this.itemIcons.put(ItemType.WHEAT, new Texture("wheat.png"));
        this.itemIcons.put(ItemType.CORN, new Texture("corn.png"));
    }

    public void render() {
        QuestViewState state=viewModel.getQuestViewState();
        InventoryViewState invState=viewModel.getInventoryViewState();
        if (!state.isActiveQuestAvailable()) {
            return; //todo dodac komentarz jak sie skoncza questy
        }

        requiredItems=state.getRequiredItems();
        batch.begin();

        float bgWidth=320;
        float bgHeight=300;
        float topBarHeight=80;

        float drawX=Gdx.graphics.getWidth()-bgWidth-15;
        float drawY=Gdx.graphics.getHeight()-topBarHeight-bgHeight;

        batch.draw(questBackground, drawX, drawY, bgWidth, bgHeight);

        float textStartX=drawX+100;
        float textStartY=drawY+bgHeight-50;

        font.setColor(1, 1, 1, 1);
        font.getData().setScale(1.5f);
        font.draw(batch, "QUESTS", textStartX+5, textStartY);

        int offsetMultiplier=1;
        for (Map.Entry<ItemType, Integer> requirement : requiredItems.entrySet()) {
            ItemType itemType=requirement.getKey();
            int requiredAmount=requirement.getValue();
            int currentAmount=invState.getAmount(itemType);
            float drawHeight = textStartY - (40 * offsetMultiplier);
            Texture icon=itemIcons.get(itemType);
            if (icon!=null) {
                batch.draw(icon, textStartX-55, drawHeight-25, 35, 35);
            }
            font.draw(batch, currentAmount+"/"+requiredAmount, textStartX, textStartY-(40*offsetMultiplier));
            offsetMultiplier++;
        }

        batch.end();
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
        questBackground.dispose();
    }

}
