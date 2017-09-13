package pl.itto.packageinspector.ui.deviceinfo.data.model;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class ProductInfo {
    private String mProductName;
    private String mModelName;
    private String mPlatform;
    private String mManufacturer;

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String getModelName() {
        return mModelName;
    }

    public void setModelName(String modelName) {
        mModelName = modelName;
    }

    public String getPlatform() {
        return mPlatform;
    }

    public void setPlatform(String platform) {
        mPlatform = platform;
    }

    public String getManufacturer() {
        return mManufacturer;
    }

    public void setManufacturer(String manufacturer) {
        mManufacturer = manufacturer;
    }
}
