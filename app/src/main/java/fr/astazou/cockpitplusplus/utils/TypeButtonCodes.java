package fr.astazou.cockpitplusplus.utils;

/**
 * Created by astazou on 22/04/2017.
 *
 */
public enum TypeButtonCodes {

    Simple(1),
    SimpleWithCover(2),
    ThreePosition(3),
    RotateDec(4),
    Push(5),
    RotateDecWithZero(6),
    RotateSeveral(7);

    private final int mCode;

    TypeButtonCodes(int pCode) {
        mCode = pCode;
    }

    public int getCode() {
        return mCode;
    }
}
