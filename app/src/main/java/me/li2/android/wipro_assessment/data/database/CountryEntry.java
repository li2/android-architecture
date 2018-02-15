package me.li2.android.wipro_assessment.data.database;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by weiyi on 15/02/2018.
 * https://github.com/li2
 *
 * Sample Json: https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json
 */

public class CountryEntry {
    /**
     * title : About Canada
     * rows : {@link CountryIntroEntry}
     */

    @SerializedName("title")
    private String title;
    @SerializedName("rows")
    private List<CountryIntroEntry> intros;

    public CountryEntry(String title, List<CountryIntroEntry> intros) {
        this.title = title;
        this.intros = intros;
    }

    public String getTitle() {
        return title;
    }

    public List<CountryIntroEntry> getIntros() {
        return intros;
    }
}
