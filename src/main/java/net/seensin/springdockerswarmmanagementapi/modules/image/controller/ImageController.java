package net.seensin.springdockerswarmmanagementapi.modules.image.controller;

import com.github.dockerjava.api.model.Image;
import net.seensin.springdockerswarmmanagementapi.modules.image.to.CloneTo;
import net.seensin.springdockerswarmmanagementapi.modules.image.to.ImageTo;
import net.seensin.springdockerswarmmanagementapi.common.exception.customExceptions.docker.DockerTarFileNotValidException;
import net.seensin.springdockerswarmmanagementapi.modules.image.model.service.DockerImageService;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/swarm/images")
public class ImageController {

    @Autowired
    DockerImageService service;


    @PostMapping(value = "import/tar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map> importTarImg(@RequestParam MultipartFile image,@RequestParam String host) throws Exception {
        Map responseMap = new HashMap();
        if (dockerTarValidatior(image.getInputStream()) == false)
            throw new DockerTarFileNotValidException();
        service.importTarImageFile(image.getInputStream(),host);
        responseMap.put("message", "image built successfully");
        responseMap.put("status", 200);
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping(value = "import/dockerfile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map> addImage(@RequestParam MultipartFile dockerFile , String imageName,@RequestParam String host) throws Exception {
        Map responseMap = new HashMap();
        responseMap.put("id" , service.importImageByDockerFile(convertMultiPartToFile(dockerFile),imageName,host));
        responseMap.put("message", "image built successfully");
        responseMap.put("status", 200);
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping(value = "/clone")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map> cloneImage(@RequestBody CloneTo clone,@RequestParam String host) throws Exception {
        Map responseMap = new HashMap();

        service.cloneImage(clone,host);
        responseMap.put("message", "image cloned successfully");
        responseMap.put("status", 200);
        return ResponseEntity.ok(responseMap);
    }

    @DeleteMapping(value = "/{imageName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map> deleteImage(@PathVariable String imageName,@RequestParam(defaultValue = "false") Boolean force,@RequestParam String host) throws Exception {
        System.out.println("here");
        Map responseMap = new HashMap();
        service.deleteImage(imageName,force,host);
        responseMap.put("message", "image deleted successfully");
        responseMap.put("status", 200);
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Image>> findImages(@RequestBody(required = false) ImageTo image,@RequestParam String host) throws Exception {
        if (image == null)
            image = new ImageTo();
        return ResponseEntity.ok(service.findAllImages(image,host));
    }



    private File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }

    private Boolean dockerTarValidatior(InputStream image) throws Exception {
        Boolean manifest = false , repositories = false;

        try (TarArchiveInputStream fin = new TarArchiveInputStream(image)){
            TarArchiveEntry entry;
            while ((entry = fin.getNextTarEntry()) != null) {
               if (entry.getName().equals("manifest.json")){
                   System.out.println("manifest");
                   manifest = true;
               }
                if (entry.getName().equals("repositories")){
                    System.out.println("repositories");
                    repositories = true;
                }
            }
        }catch (Exception e){
            throw new DockerTarFileNotValidException();
        }

        return (repositories & manifest);
    }

}
