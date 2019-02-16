package gui.assistclasses;
import javafx.scene.image.ImageView;

/**
 * @author Dustin Hendriks
 * @since 02-02-2019
 *
 * The Image class enables easy finding of images.
 */

public class Image {
    private String name;
    private String originalName;
    private String description;
    private String folder;
    private String identifier;
    private String extension;
    private boolean hoverImage = false;
    private ImageView imageView;

    /**
     * The constructor of image passes the name, folder, extension, identifier and description so an image can be located and recognized later.
     * @param name Defines the _exact_ name of the image.
     * @param folder Defines the _exact_ folder of the image.
     * @param extension Defines the _exact_ extension of the image.
     * @param identifier Defines an identifier of the image (so you can recognize it later).
     * @param description Defines a description of the image (so you can show an extensive explanation).
     */

    public Image(String name, String folder, String extension, String identifier, String description) {
        this.description = description;
        this.originalName = name;
        this.name = name;
        this.folder = folder;
        this.extension = extension;
        this.identifier = identifier;
        buildImageView();
    }

    /**
     * Build an imageView of the Image so it can be retrieved and placed in a node later.
     */

    private void buildImageView() {
        imageView = new ImageView(getClass().getResource("/images/" + folder + "/" + name + extension).toString());
    }

    /**
     * @return ImageView to place in a JavaFX node object later.
     */

    public ImageView getImageView() {
        return imageView;
    }

    /**
     * @return Identifier String.
     */

    public String getIdentifier() {
        return identifier;
    }

    /**
     * If the Image needs to be changed to a hover image (only applicable for the main menu tiles) this method can be used.
     * @param hoverImage Defines true for hovering and false for the normal image.
     */

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

    /**
     * You might want to retrieve the original String name of the Image, getNormalString() removes the hover word from the string, if it exists.
     * @return Return "normal" sting without the hover keyword.
     */

    public String getNormalString() {
        if (name.contains("hover"))
            return name.substring(0, name.length() - 5);
        else return null;
    }

    /**
     * @return Description of image.
     */

    public String getDescription() {
        return this.description;
    }

}
