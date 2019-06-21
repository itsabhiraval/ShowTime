package com.example.r.showtime.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVResponse implements Parcelable {
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<TV> getResults() {
        return results;
    }

    public void setResults(List<TV> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }


    public static Creator<TVResponse> getCREATOR() {
        return CREATOR;
    }

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<TV> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    protected TVResponse(Parcel in) {
        page = in.readInt();
        results = in.createTypedArrayList(TV.CREATOR);
        totalResults = in.readInt();
        totalPages = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeTypedList(results);
        dest.writeInt(totalResults);
        dest.writeInt(totalPages);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TVResponse> CREATOR = new Creator<TVResponse>() {
        @Override
        public TVResponse createFromParcel(Parcel in) {
            return new TVResponse(in);
        }

        @Override
        public TVResponse[] newArray(int size) {
            return new TVResponse[size];
        }
    };
}
