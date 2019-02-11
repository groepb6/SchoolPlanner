package gui;

import javafx.scene.image.ImageView;

public class Image {
    private String name;
    private String originalName;
    private String description;
    private String folder;
    private String identifier;
    private String extension;
    private boolean hoverImage = false;
    private ImageView imageView;

    public Image(String name, String folder, String extension, String identifier, String description) {
        this.description = description;
        this.originalName = name;
        this.name = name;
        this.folder = folder;
        this.extension = extension;
        this.identifier = identifier;
        buildImageView();
    }

    private void buildImageView() {
             imageView = new ImageView("file:///"+getClass().getResource("images").getPath()+"\\"+folder+"\\"+name+extension);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void editImage(boolean hoverImage) {
        if (hoverImage != this.hoverImage) {
            this.hoverImage = hoverImage;
            if (hoverImage)
                this.name = name + "hover";
            if (!hoverImage) {
                getNormalString();
                if (getNormalString() != null)
                    this.name = getNormalString();
            }
        }
        buildImageView();
    }

    public String getNormalString() {
        if (name.contains("hover"))
            return name.substring(0, name.length() - 5);
        else return null;
    }

    public String getDescription() {
        return this.description;
    }

}
