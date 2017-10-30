package pl.itto.packageinspector.filechooser.model;

import java.io.File;

/**
 * Created by PL_itto on 10/28/2017.
 */

public class FolderItem {
    private String title;
    private File file;
    private boolean isParent = false;

    public FolderItem(String title, File file, boolean isParent) {
        this.title = title;
        this.file = file;
        this.setParent(isParent);
    }

    public FolderItem(FolderItem item, boolean isParent) {
        if (item != null) {
            this.title = item.getTitle();
            this.file = item.getFile();
        }
        this.setParent(isParent);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }
}