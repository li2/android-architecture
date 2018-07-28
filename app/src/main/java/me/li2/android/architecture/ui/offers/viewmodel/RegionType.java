package me.li2.android.architecture.ui.offers.viewmodel;

/**
 * Created by weiyi on 28/7/18.
 * https://github.com/li2
 */
public enum  RegionType {
    AUD ("AUD"),
    CNY ("CNY");

    private String codeName;

    RegionType(String codeName) {
        this.codeName = codeName;
    }

    public boolean equals(String other) {
        return codeName.equals(other);
    }

    @Override
    public String toString() {
        return codeName;
    }
}
