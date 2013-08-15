package com.mlj.retrovideo.domain.repository;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class SearchFileRepository {

    private final static String TMP_DIR = "/tmp/";

    public List<String> getIdsFrom(String searchFile) {
        File idFile = getFile(searchFile);
        FileInputStream fis = null;
        BufferedReader br = null;
        String line;
        List<String> result = newArrayList();
        try {
            fis = new FileInputStream(idFile);
            br = new BufferedReader(new InputStreamReader(fis));
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            throw new IllegalStateException(format("Could not read searchFile [%s]", idFile.getAbsolutePath()) ,e);
        } finally  {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(fis);
        }
        return result;
    }

    public String createSearchFile(List<String> ids) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        File tempFile = null;
        String filename = UUID.randomUUID().toString();
        String searchFileName = TMP_DIR + filename;
        try {
            tempFile = new File(searchFileName);
            fileWriter = new FileWriter(tempFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (String id : ids) {
                bufferedWriter.write(id);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(format("Could not write out search file [%s]", searchFileName), e);
        } finally {
            IOUtils.closeQuietly(bufferedWriter);
            IOUtils.closeQuietly(fileWriter);
        }
        return filename;
    }

    private File getFile(String searchFile) {
        String searchFileName = TMP_DIR + searchFile;
        File idFile = new File(searchFileName);
        if (!idFile.exists()) {
            throw new IllegalStateException(format("Cannot find searchFile [%s]", searchFileName));
        }
        return idFile;
    }

}
