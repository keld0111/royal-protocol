package keld0111.royalprotocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum RoyalMove implements Move {
    BOW(858),
    ;

    private int animId;
    private int gfxId;

    RoyalMove(int animId)
    {
        this(animId, -1);
    }
}