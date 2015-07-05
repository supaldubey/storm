package in.cubestack.apps.android.storm.test;

import java.util.List;

import in.cubestack.android.lib.storm.CascadeTypes;
import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Relation;
import in.cubestack.android.lib.storm.annotation.Table;

/**
 * Created by Supal on 7/5/2015.
 */
@Table(name="PIXR_COLLECTION")
public class Collection {

    @PrimaryKey
    @Column(name = "COLLECTION_ID", type = FieldType.LONG)
    protected long id;

    @Column(name = "COLLECTION_NM", type = FieldType.TEXT)
    private String collectionName;

    @Relation(joinColumn = "id", targetEntity = Photo.class, cascade = CascadeTypes.PERSIST)
    private List<Photo> photos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
