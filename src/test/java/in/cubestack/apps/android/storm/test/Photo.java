package in.cubestack.apps.android.storm.test;

import org.apache.commons.lang3.builder.ToStringBuilder;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Table;

/**
 * Created by Supal on 7/5/2015.
 */
@Table(name="PIXR_PHOTO")
public class Photo {

    @PrimaryKey
    @Column(name = "PHOTO_ID", type = FieldType.LONG)
    protected long id;


    @Column(name = "COLLECTN_ID", type = FieldType.LONG)
    private long collectionId;

    @Column(name = "PHOTO_SVR_ID", type = FieldType.LONG)
    private long photoSvrId;
    
    @Column(name = "PHOTO_URL", type = FieldType.TEXT)
    private String photoUrl;



    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public long getPhotoSvrId() {
        return photoSvrId;
    }

    public void setPhotoSvrId(long photoSvrId) {
        this.photoSvrId = photoSvrId;
    
    }
    
    @Override
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
}
