package net.seensin.springdockerswarmmanagementapi.model.service;

import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.ListImagesCmd;
import com.github.dockerjava.api.model.Image;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.seensin.springdockerswarmmanagementapi.To.CloneTo;
import net.seensin.springdockerswarmmanagementapi.To.ImageTo;
import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DockerRegistryService {

    @Autowired
    DockerConnectionProvider connection;

    public List<String> importTarImageFile(InputStream image) throws Exception {
        List<String> imagesSumm = new ArrayList<>();
        connection.getDockerClient().loadImageCmd(image).exec();
        return findAllImages(new ImageTo())
                .stream()
                .map(img -> img.getId()+" : "+img.getRepoTags())
                .collect(Collectors.toList());
    }

    public String importImageByDockerFile(File dockerFile , String tag) throws Exception{
        return connection.getDockerClient().buildImageCmd()
                .withDockerfile(dockerFile)
                .withPull(true)
                .withNoCache(true)
                .withTag(tag)
                .exec(new BuildImageResultCallback()).awaitImageId();
    }

    public void cloneImage(CloneTo clone){
        connection.getDockerClient().tagImageCmd(clone.getImageName(),clone.getNewNmae(),clone.getTag()).exec();
    }

    public void deleteImage(String imageName , Boolean force){
        connection.getDockerClient().removeImageCmd(imageName).withForce(force).exec();
    }

    public List<Image> findAllImages(ImageTo image){
        ListImagesCmd cmd = connection.getDockerClient().listImagesCmd();

        if (image.getImageNmae() != null)
            cmd = cmd.withImageNameFilter(image.getImageNmae());

        if (image.getLableMap() != null)
            cmd = cmd.withLabelFilter(image.getLableMap());

        if (image.getDangling() != null)
            cmd = cmd.withDanglingFilter(image.getDangling());

        if (image.getShowAll() != null)
            cmd = cmd.withShowAll(image.getShowAll());

        return cmd.exec();
    }

}
