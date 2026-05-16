package io.github.anseroville.model.Tiles;

import io.github.anseroville.model.GridPosition;
//czy ta klasa jest w ogole potrzebna?
//moze do wszystkiego poza growing zrobic jedna klase z enumem do typow
//bo nie wydaje mi sie zeby to wymagalo jakichkolwiek informacji poza typem pola
public class ShopTile extends InteractableTile{
    public ShopTile(GridPosition gridPosition) {
        super(gridPosition);
    }
}
