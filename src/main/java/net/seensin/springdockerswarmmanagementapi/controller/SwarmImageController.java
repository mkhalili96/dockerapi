package net.seensin.springdockerswarmmanagementapi.controller;

import com.github.dockerjava.api.model.Image;
import net.seensin.springdockerswarmmanagementapi.To.CloneTo;
import net.seensin.springdockerswarmmanagementapi.To.ImageTo;
import net.seensin.springdockerswarmmanagementapi.model.service.DockerRegistryService;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/swarm/images")
public class SwarmImageController {

    @Autowired
    DockerRegistryService service;


    @PostMapping(value = "import/tar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map> importTarImg(@RequestParam MultipartFile image) throws Exception {
        Map responseMap = new HashMap();
//        dockerTarValidatior(image.getInputStream());
        service.importTarImageFile(image.getInputStream());
        responseMap.put("message", "image built successfully");
        responseMap.put("status", 200);
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping(value = "import/dockerfile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map> addImage(@RequestParam MultipartFile dockerFile , String imageName) throws Exception {
        Map responseMap = new HashMap();
        responseMap.put("id" , service.importImageByDockerFile(convertMultiPartToFile(dockerFile),imageName));
        responseMap.put("message", "image built successfully");
        responseMap.put("status", 200);
        return ResponseEntity.ok(responseMap);
    }

    @PostMapping(value = "/clone")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map> cloneImage(@RequestBody CloneTo clone) throws Exception {
        Map responseMap = new HashMap();

        service.cloneImage(clone);
        responseMap.put("message", "image cloned successfully");
        responseMap.put("status", 200);
        return ResponseEntity.ok(responseMap);
    }

    @DeleteMapping(value = "/{imageName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map> deleteImage(@PathVariable String imageName,@RequestParam(defaultValue = "false") Boolean force) throws Exception {
        System.out.println("here");
        Map responseMap = new HashMap();
        service.deleteImage(imageName,force);
        responseMap.put("message", "image deleted successfully");
        responseMap.put("status", 200);
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Image>> findImages(@RequestBody(required = false) ImageTo image) throws Exception {
        if (image == null)
            image = new ImageTo();
        return ResponseEntity.ok(service.findAllImages(image));
    }



    private File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }

    private static List<File> unTar(final InputStream inputFile) throws FileNotFoundException, IOException, ArchiveException {

        final List<File> untaredFiles = new LinkedList<File>();
        final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream("tar", inputFile);
        TarArchiveEntry entry = null;
        while ((entry = (TarArchiveEntry)debInputStream.getNextEntry()) != null) {
            untaredFiles.add(entry.getFile());
        }
        debInputStream.close();

        return untaredFiles;
    }

    public String dockerTarValidatior(InputStream image) throws Exception {
        unTar(image).stream().forEach(file -> {
            System.out.println(file.getName());
        });
        File manifest = unTar(image).stream().filter(file -> file.getName().equals("manifest.json")).findFirst().orElseThrow(Exception::new);
        JSONParser parser = new JSONParser();
        System.out.println(image.toString());
        JSONArray array = (JSONArray) parser.parse(new FileReader(manifest));
        JSONObject object = (JSONObject) array.get(0);
        System.out.println(object.get("RepoTags").toString());
        return object.get("RepoTags").toString();
    }
}
