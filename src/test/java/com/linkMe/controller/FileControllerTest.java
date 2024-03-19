package com.linkMe.controller;

import com.linkMe.AbstractTestClass;
import com.linkMe.models.File;
import com.linkMe.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class FileControllerTest extends AbstractTestClass {

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateFile() {
        File file = getSampleFile();
        when(fileService.createFile(any(File.class), anyString())).thenReturn(file);
        File createdFile = fileController.createFile(file, sampleString);
        assertEquals(file, createdFile);
        verify(fileService, times(1)).createFile(file, sampleString);
    }

    @Test
    public void testGetFilesByUserName() throws Exception {
        List<File> fileList = new ArrayList<>();
        fileList.add(getSampleFile());
        fileList.add(getSampleFile());
        when(fileService.findAllByUserName(anyString())).thenReturn(fileList);
        List<File> retrievedFiles = fileController.getFilesByUserName(sampleString);
        assertEquals(fileList, retrievedFiles);
        verify(fileService, times(1)).findAllByUserName(sampleString);
    }

    @Test
    public void testGetFileContentById() throws Exception {
        when(fileService.findById(anyString(), anyInt())).thenReturn(sampleString);
        ResponseEntity<String> responseEntity = fileController.getFileContentById(sampleString, 1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sampleString, responseEntity.getBody());
        verify(fileService, times(1)).findById(sampleString, 1);
    }

    @Test
    public void testGetFileContentById_Exception() throws Exception {
        when(fileService.findById(anyString(), anyInt())).thenThrow(new RuntimeException());
        ResponseEntity<String> responseEntity = fileController.getFileContentById("username", 1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(fileService, times(1)).findById("username", 1);
    }

    @Test
    public void testShareFileById() throws Exception {
        doNothing().when(fileService).shareFileById(sampleInteger);
        fileController.shareFileById(sampleInteger);
        verify(fileService, times(1)).shareFileById(sampleInteger);
    }

    @Test
    public void testShareFileById_Exception() throws Exception {
        doThrow(new Exception()).when(fileService).shareFileById(sampleInteger);
        assertThrows(Exception.class, () -> {fileController.shareFileById(sampleInteger);});
        verify(fileService, times(1)).shareFileById(sampleInteger);
    }
}
