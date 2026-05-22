package io.github.anseroville.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.anseroville.enums.ItemType;
import io.github.anseroville.viewModel.FarmViewModel;

import java.util.EnumMap;
import java.util.Map;

public class QuestRenderer {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private Map<ItemType, Integer> requiredItems;
    private final FarmViewModel viewModel;

    public QuestRenderer(FarmViewModel viewModel) {
        this.batch=new SpriteBatch();
        this.font=new BitmapFont();
        this.font.getData().setScale(1.5f);
        this.requiredItems=new EnumMap<>(ItemType.class);
        this.viewModel = viewModel;
    }

    public void render(boolean isInventoryOpen){
        if (!viewModel.isActiveQuestAvailable())
        {
            return; //todo dodac komentarz jak sie skoncza questy
        }
        if(isInventoryOpen){
            return;
        }
        batch.begin();
        requiredItems= viewModel.getActiveQuestRequirements();
        float startX = Gdx.graphics.getWidth()-300;
        float startY = Gdx.graphics.getHeight()-80;
        for(Map.Entry<ItemType, Integer> requirement : requiredItems.entrySet()){
            font.setColor(1, 1, 1, 1);
            font.draw(batch, "Quests: ", startX, startY);
            ItemType itemType = requirement.getKey();
            String questText = itemType.toString().toLowerCase();
            int questAmount=requirement.getValue();
            questText+=" "+questAmount;
            font.draw(batch, questText, startX, startY-45);
        }
        batch.end();
    }

    public void dispose() {
        font.dispose();
        batch.dispose();
    }

}
