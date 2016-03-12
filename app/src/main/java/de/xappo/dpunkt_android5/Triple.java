package de.xappo.dpunkt_android5;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by knoppik on 12.03.16.
 */
public class Triple implements Parcelable {

    private int x;
    private int y;
    private int z;

    public Triple(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    protected Triple(Parcel in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }

    public static final Creator<Triple> CREATOR = new Creator<Triple>() {
        @Override
        public Triple createFromParcel(Parcel in) {
            return new Triple(in);
        }

        @Override
        public Triple[] newArray(int size) {
            return new Triple[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeInt(z);
    }
}
