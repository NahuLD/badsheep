package me.nahu.badsheep.util;

import com.google.common.collect.Lists;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.common.model.Rect;
import org.jcodec.scale.AWTUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JcodecDecoder {
    /**
     * Get a file from a nullable path.
     * @param fileName Name of the file.
     * @param directory Optional nullable directory.
     * @return File if it exists, null otherwise.
     */
    @NotNull
    public static Optional<File> getFileInPath(@NotNull String fileName, @Nullable File directory) {
        return Optional.ofNullable(directory)
            .map(file -> new File(file, fileName));
    }

    /**
     * Get all the frames from a video.
     * @param videoFile Video file to decode frames from.
     * @param width Width of the screen.
     * @param height Height of the screen.
     * @return List of frames, with an expected size of all the frame count.
     * @throws IOException Could not load the file.
     * @throws JCodecException JCodec done fucked up.
     */
    @NotNull
    public static List<BufferedImage> getAllFramesFromVideo(@NotNull File videoFile, int width, int height) throws IOException, JCodecException {
        var frameGrab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(videoFile));
        var videoMeta = frameGrab.getVideoTrack().getMeta();

        var frameCount = videoMeta.getTotalFrames();

        List<BufferedImage> frames = Lists.newArrayListWithExpectedSize(frameCount);

        Picture picture;
        while ((picture = frameGrab.getNativeFrame()) != null) {
            frames.add(
                resize(
                    AWTUtil.toBufferedImage(picture.cropped()),
                    width,
                    height
                )
            );
        }
        return frames;
    }

    /**
     * Resize the buffered image into a fitting screen, we are forced to do it this way because
     * JCodec is an excellent and flawless piece of software that cannot even format it's own frames.
     *
     * @param image Image to resize.
     * @param width New width.
     * @param height New height.
     * @return New buffered image with the specified width and height.
     */
    @NotNull
    public static BufferedImage resize(@NotNull BufferedImage image, int width, int height) {
        var temporal = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        var newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        var g2d = newImage.createGraphics();
        g2d.drawImage(temporal, 0, 0, null);
        g2d.dispose();
        return newImage;
    }
}
