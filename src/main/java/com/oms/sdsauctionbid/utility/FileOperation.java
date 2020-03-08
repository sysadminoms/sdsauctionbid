package com.oms.sdsauctionbid.utility;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.oms.sdsauctionbid.domain.FileUploadResponse;
import com.oms.sdsauctionbid.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FileOperation {

    private static final Logger LOG = LoggerFactory.getLogger(FileOperation.class);
//    private final Path fileStorageLocation;

    @Value("${aws.s3.bucket}")
    private String awsS3Bucket;

    private AmazonS3 amazonS3;

    @Autowired
    public FileOperation(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3Bucket) {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3Bucket = awsS3Bucket;
    }


    public FileUploadResponse storeFile(MultipartFile multipartFile)
    {
        FileUploadResponse fileUploadResponse = new FileUploadResponse();

        if (multipartFile == null) { return fileUploadResponse; }

        // Normalize file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        //Check FileType If Pdf OR JPG OR PNG Then Save Otherwise Return null
        if (extension.equals("pdf") || extension.equals("jpg") || extension.equals("png"))
        {

            fileUploadResponse.setFileName(fileName);
            fileUploadResponse.setFilePath(awsS3Bucket);


                // Check if the file's name contains invalid characters
                if (fileName.contains("..")) {
                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                }

                try {

                    InputStream fileData = multipartFile.getInputStream();

                    PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3Bucket, fileName,
                            fileData,new ObjectMetadata());

                    this.amazonS3.putObject(putObjectRequest);

                } catch (AmazonServiceException | IOException ex) {
                    LOG.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
                }

        }
        return fileUploadResponse;
    }


    public void deleteFile(String fileName)
    {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(awsS3Bucket, fileName));
        } catch (AmazonServiceException ex) {
            LOG.error("error [" + ex.getMessage() + "] occurred while removing [" + fileName + "] ");
        }
    }


//    public FileOperation (FileUploadProperties fileUploadProperties) {
//        this.fileStorageLocation = Paths.get(fileUploadProperties.getUploadDir())
//                .toAbsolutePath().normalize();
//        LOG.info("File Path:- " + fileStorageLocation);
//        try {
//            Files.createDirectories(this.fileStorageLocation);
//        } catch (Exception ex) {
//            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
//        }
//    }
//
//
//    public FileUploadResponse storeFile(MultipartFile file) {
//
//        LOG.info("In  class to FileUploadOperation");
//        // Normalize file name
//        FileUploadResponse fileUploadResponse = new FileUploadResponse();
//
//        if (file == null) {
//            return fileUploadResponse;
//        }
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
//
//        //Check FileType If Pdf OR JPG OR PNG Then Save Otherwise Return null
//
//        if (extension.equals("pdf") || extension.equals("jpg") || extension.equals("png")) {
//
//            fileUploadResponse.setFileName(fileName);
//            fileUploadResponse.setFilePath(fileStorageLocation.toString());
//
//            try {
//                // Check if the file's name contains invalid characters
//                if (fileName.contains("..")) {
//                    throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//                }
//
//                // Copy file to the target location (Replacing existing file with the same name)
//                Path targetLocation = this.fileStorageLocation.resolve(fileName);
//                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//                return fileUploadResponse;
//            } catch (IOException ex) {
//                throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
//            }
//
//        }
//        return fileUploadResponse;
//    }
//
//
//    public void deleteFile(String filePath) {
//
//        try {
//
//            File file = new File(filePath);
//            if (file.exists()) {
//                if (file.delete()) {
//                    LOG.info(file.getName() + " is deleted!");
//                } else {
//                    LOG.info("File Not Exist");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
