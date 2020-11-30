package net.seensin.springdockerswarmmanagementapi.modules.image.model.service;

import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.ListImagesCmd;
import com.github.dockerjava.api.model.Image;
import net.seensin.springdockerswarmmanagementapi.modules.image.to.CloneTo;
import net.seensin.springdockerswarmmanagementapi.modules.image.to.ImageTo;
import net.seensin.springdockerswarmmanagementapi.common.DockerConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DockerImageService {

    @Autowired
    DockerConnectionProvider connection;

    public List<String> importTarImageFile(InputStream image , String host) throws Exception {
        List<String> imagesSumm = new ArrayList<>();
        connection.getDockerClientByIp(host).loadImageCmd(image).exec();
        return findAllImages(new ImageTo(),host)
                .stream()
                .map(img -> img.getId()+" : "+img.getRepoTags())
                .collect(Collectors.toList());
    }

    public String importImageByDockerFile(File dockerFile , String tag , String host) throws Exception{
        return connection.getDockerClientByIp(host).buildImageCmd()
                .withDockerfile(dockerFile)
                .withPull(true)
                .withNoCache(true)
                .withTag(tag)
                .exec(new BuildImageResultCallback()).awaitImageId();
    }

    public void cloneImage(CloneTo clone , String host){
        connection.getDockerClientByIp(host).tagImageCmd(clone.getImageName(),clone.getNewNmae(),clone.getTag()).exec();
    }

    public void deleteImage(String imageName , Boolean force , String host){
        connection.getDockerClientByIp(host).removeImageCmd(imageName).withForce(force).exec();
    }

    public List<Image> findAllImages(ImageTo image , String host){
        ListImagesCmd cmd = connection.getDockerClientByIp(host).listImagesCmd();

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
